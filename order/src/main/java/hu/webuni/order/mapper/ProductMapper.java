package hu.webuni.order.mapper;

import hu.webuni.order.dto.ProductDto;
import hu.webuni.order.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto productDto);
}