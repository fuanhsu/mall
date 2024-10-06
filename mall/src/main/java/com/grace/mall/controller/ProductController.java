package com.grace.mall.controller;

import com.grace.mall.constant.ProductCategory;
import com.grace.mall.dto.ProductRequest;
import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.ProductUpdateRequest;
import com.grace.mall.model.Product;
import com.grace.mall.service.ProductService;
import com.grace.mall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        ProductRequestParam productRequestParam = new ProductRequestParam();
        productRequestParam.setCategory(category);
        productRequestParam.setSearch(search);
        productRequestParam.setSort(sort);
        productRequestParam.setOrderBy(orderBy);
        productRequestParam.setLimit(limit);
        productRequestParam.setOffset(offset);

        List<Product>  productList = productService.getProducts(productRequestParam);
        Integer count = productService.countProduct(productRequestParam);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        Product product = productService.updateProduct(productId, productUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
