package hu.webuni.catalog.mapper;

import hu.webuni.catalog.api.model.HistoryDataPriceDto;
import hu.webuni.catalog.model.HistoryData;
import hu.webuni.catalog.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryDataPriceMapper {
    List<HistoryDataPriceDto> map(List<HistoryData<Product>> productList);

    @Mapping(target = "price", source = "hdData.data.price")
    @Mapping(target = "date", source = "hdData.date")
    HistoryDataPriceDto map(HistoryData<Product> hdData);
}
