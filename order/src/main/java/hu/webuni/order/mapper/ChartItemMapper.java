package hu.webuni.order.mapper;

import hu.webuni.order.dto.ChartItemDto;
import hu.webuni.order.model.ChartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChartItemMapper {
    ChartItemDto chartItemToDto(ChartItem chart);
    ChartItem dtoToChartItem(ChartItemDto chartDto);
    List<ChartItemDto> chartToDtos(List<ChartItem> charts);
    List<ChartItem> dtosToChart(List<ChartItemDto> chartDtos);
}