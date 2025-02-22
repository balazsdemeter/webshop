package hu.webuni.catalog.mapper;

import hu.webuni.catalog.api.model.HistoryDataProductDto;
import hu.webuni.catalog.api.model.ProductDto;
import hu.webuni.catalog.model.HistoryData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {
    List<HistoryDataProductDto> mapProductDtoList(List<HistoryData<ProductDto>> productDtos);
}