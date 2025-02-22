package hu.webuni.catalog.mapper;

import hu.webuni.catalog.api.model.ProductDto;
import hu.webuni.catalog.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto productDto);

    List<ProductDto> productsToDtos(List<Product> products);
    List<Product> dtosToProducts(List<ProductDto> productDtos);
}