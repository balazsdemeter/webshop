package hu.webuni.catalog.web;

import hu.webuni.catalog.api.CatalogControllerApi;
import hu.webuni.catalog.api.model.CategoryDto;
import hu.webuni.catalog.api.model.HistoryDataPriceDto;
import hu.webuni.catalog.api.model.ProductDto;
import hu.webuni.catalog.mapper.HistoryDataPriceMapper;
import hu.webuni.catalog.model.HistoryData;
import hu.webuni.catalog.model.Product;
import hu.webuni.catalog.service.CategoryService;
import hu.webuni.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CatalogController implements CatalogControllerApi {

    private final NativeWebRequest request;
    private final MethodArgumentResolverHelper resolverHelper;

    private final CategoryService categoryService;
    private final ProductService productService;
    private final HistoryDataPriceMapper historyDataPriceMapper;

    public void configPageable(@SortDefault("id") Pageable pageable) {}

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return CatalogControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.create(categoryDto));
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        return ResponseEntity.ok(productService.create(productDto));
    }

    @Override
    public ResponseEntity<ProductDto> modifyProduct(Long id, ProductDto productDto) {
        return ResponseEntity.ok(productService.modify(id, productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<HistoryDataPriceDto>> getPriceHistory(Long id) {
        List<HistoryData<Product>> products = productService.getHistory(id);
        return ResponseEntity.ok(historyDataPriceMapper.map(products));
    }

    @Override
    public ResponseEntity<List<ProductDto>> search(Integer minPrice, Integer maxPrice, String productName,
                                                   String categoryName, Integer page, Integer size, List<String> sort) {
        Pageable pageable = resolverHelper.createPageable(this.getClass(), "configPageable", request);
        return ResponseEntity.ok(productService.search(minPrice, maxPrice, productName, categoryName, pageable));
    }
}