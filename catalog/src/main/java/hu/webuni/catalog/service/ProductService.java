package hu.webuni.catalog.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import hu.webuni.catalog.api.model.CategoryDto;
import hu.webuni.catalog.api.model.ProductDto;
import hu.webuni.catalog.mapper.ProductMapper;
import hu.webuni.catalog.model.Category;
import hu.webuni.catalog.model.HistoryData;
import hu.webuni.catalog.model.Product;
import hu.webuni.catalog.model.QCategory;
import hu.webuni.catalog.model.QProduct;
import hu.webuni.catalog.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ProductDto create(ProductDto productDto) {
        Product product = productMapper.dtoToProduct(productDto);
        CategoryDto categoryDto = productDto.getCategory();
        if (categoryDto != null) {
            Category category = categoryService.findById(categoryDto.getId());
            product.setCategory(category);
        }

        productRepository.save(product);
        return productMapper.productToDto(product);
    }

    @Transactional
    public ProductDto modify(long id, ProductDto productDto) {
        Product oldProduct = getProduct(id);
        Product newProduct = productMapper.dtoToProduct(productDto);
        newProduct.setId(oldProduct.getId());
        productRepository.save(newProduct);
        return productMapper.productToDto(newProduct);
    }

    @Transactional
    public void delete(long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    private Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Cacheable("productResults")
    public List<HistoryData<Product>> getHistory(long id) {
        return AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList()
                .stream()
                .map(o -> {
                    Object[] objArray = (Object[]) o;
                    DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) objArray[1];
                    Product product = (Product) objArray[0];
                    product.getCategory();

                    return new HistoryData<>(
                            product,
                            (RevisionType) objArray[2],
                            revisionEntity.getId(),
                            revisionEntity.getRevisionDate()
                    );
                }).toList();
    }

    public List<ProductDto> search(Integer minPrice, Integer maxPrice, String productName, String categoryName, Pageable pageable) {
        JPQLQuery<Product> query = new JPAQuery<>(em);
        QProduct qProduct = QProduct.product;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (minPrice != null) {
            booleanBuilder.and(qProduct.price.goe(minPrice));
        }

        if (maxPrice != null) {
            booleanBuilder.and(qProduct.price.loe(maxPrice));
        }

        if (productName != null) {
            booleanBuilder.and(qProduct.name.startsWithIgnoreCase(productName));
        }

        if (categoryName != null) {
            BooleanExpression categoryStartsWith = qProduct.category.name.startsWithIgnoreCase(categoryName);
            booleanBuilder.and(categoryStartsWith);
        }

        query.select(qProduct)
                .from(qProduct)
                .join(qProduct.category, QCategory.category);

        Querydsl querydsl = new Querydsl(em, (new PathBuilderFactory()).create(Product.class));
        List<Product> products = querydsl.applyPagination(pageable, query).where(booleanBuilder).fetch();

        return productMapper.productsToDtos(products);
    }
}