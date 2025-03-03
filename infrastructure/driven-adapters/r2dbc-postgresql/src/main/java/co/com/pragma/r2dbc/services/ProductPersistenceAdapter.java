package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.model.franchise.spi.IProductPersistencePort;
import co.com.pragma.r2dbc.mappers.IProductEntityMapper;
import co.com.pragma.r2dbc.repositories.IProductRepository;
import reactor.core.publisher.Mono;

public class ProductPersistenceAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    public ProductPersistenceAdapter(IProductRepository productRepository, IProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public Mono<Product> save(Product product) {
        return productRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Product> findByName(String productName) {
        return productRepository.findByName(productName)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Product> findById(Long productId) {
        return productRepository.findById(productId)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long productId) {
        return productRepository.deleteById(productId);
    }

    @Override
    public Mono<Product> updateStock(Product product) {
        return productRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toModel);
    }
}
