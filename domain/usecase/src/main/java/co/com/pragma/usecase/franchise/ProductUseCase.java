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
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.BRANCH_NOT_FOUND)))
                .flatMap(branch ->
                        productPersistencePort.findByName(product.getName())
                                .flatMap(existingProduct -> Mono.error(new CustomException(ExceptionsEnum.ALREADY_EXIST_PRODUCT)))
                                .then(Mono.defer(() -> productPersistencePort.save(product)))
                )
                .flatMap(productSaved ->
                        branchProductPersistencePort.save(new BranchProduct(product.getBranchId(), productSaved.getId()))
                                .then(Mono.defer(() -> Mono.just(productSaved)))
                );
    }

    @Override
    public Mono<DeleteResponse> deleteProductById(Long productId) {
        return productPersistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.PRODUCT_NOT_FOUNT)))
                .flatMap(product -> productPersistencePort.deleteById(productId)
                        .thenReturn(new DeleteResponse("Producto eliminado correctamente"))
                );
    }

    @Override
    public Mono<Product> updateStock(Product product) {
        return productPersistencePort.findById(product.getId())
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.PRODUCT_NOT_FOUNT))) // Primero manejamos si no existe
                .flatMap(existingProduct -> {
                    existingProduct.setStock(product.getStock());
                    return productPersistencePort.save(existingProduct);
                });
    }


}
