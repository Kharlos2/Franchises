package co.com.pragma.usecase.franchise;


import co.com.pragma.model.franchise.api.IProductServicePort;
import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.BranchProduct;
import co.com.pragma.model.franchise.models.DeleteResponse;
import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IBranchProductPersistencePort;
import co.com.pragma.model.franchise.spi.IProductPersistencePort;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final IBranchPersistencePort branchPersistencePort;
    private final IBranchProductPersistencePort branchProductPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, IBranchPersistencePort branchPersistencePort, IBranchProductPersistencePort branchProductPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.branchPersistencePort = branchPersistencePort;
        this.branchProductPersistencePort = branchProductPersistencePort;
    }

    @Override
    public Mono<Product> save(Product product) {
        return branchPersistencePort.findById(product.getBranchId())
                .hasElement()
                .flatMap(existBranch -> {
                    if (Boolean.FALSE.equals(existBranch)){
                        return Mono.error(new CustomException(ExceptionsEnum.BRANCH_NOT_FOUND));
                    }
                    return productPersistencePort.findByName(product.getName())
                            .hasElement()
                            .flatMap(existProduct -> {
                                if(Boolean.TRUE.equals(existProduct)){
                                    return Mono.error(new CustomException(ExceptionsEnum.ALREADY_EXIST_PRODUCT));
                                }

                                Mono<Product> productMono = productPersistencePort.save(product);

                                productMono.map(productSaved ->
                                        branchProductPersistencePort.save(new BranchProduct(product.getBranchId(), productSaved.getId()))
                                );

                                return productMono;
                            });

                });
    }

    @Override
    public Mono<DeleteResponse> deleteProductById(Long productId) {
        return productPersistencePort.findById(productId)
                .hasElement()
                .flatMap(exist -> {
                    if (Boolean.FALSE.equals(exist)){
                        return Mono.error(new CustomException(ExceptionsEnum.PRODUCT_NOT_FOUNT));
                    }
                    productPersistencePort.deleteById(productId);
                    return  Mono.just(new DeleteResponse("Producto eliminado correctamente"));
                });
    }

    @Override
    public Mono<Product> updateStock(Product product) {
        return productPersistencePort.findById(product.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setStock(product.getStock());
                    return productPersistencePort.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.PRODUCT_NOT_FOUNT)));
    }

}
