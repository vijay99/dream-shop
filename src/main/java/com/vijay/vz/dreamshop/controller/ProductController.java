package com.vijay.vz.dreamshop.controller;

import com.vijay.vz.dreamshop.dto.ProductDto;
import com.vijay.vz.dreamshop.exceptions.AlreadyExistsException;
import com.vijay.vz.dreamshop.exceptions.ResourceNotFoundException;
import com.vijay.vz.dreamshop.model.Product;
import com.vijay.vz.dreamshop.request.AddProductRequest;
import com.vijay.vz.dreamshop.request.ProductUpdateRequest;
import com.vijay.vz.dreamshop.response.ApiResponse;
import com.vijay.vz.dreamshop.service.product.IProductService;
import com.vijay.vz.dreamshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
@EnableMethodSecurity(prePostEnabled = true)
public class ProductController {

    private final IProductService productService;


    @GetMapping("product/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success",productDtos));
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("product/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest theProduct) {
        try {
            Product product = productService.addProduct(theProduct);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        try {
            Product product = productService.updateProduct(request, id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Successfully product deleted by id " + id, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> productList = productService.getProductsByBrandAndName(brand, name);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found with provided brand and name", null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(productList);
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductByCategoryAndName(@RequestParam String category, @RequestParam String name) {
        try {
            List<Product> productList = productService.getProductsByBrandAndName(category, name);
            List<ProductDto> productDtos=productService.getConvertedProducts(productList);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found with provided ,category and name", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String category, @RequestParam String name) {
        try {
            List<Product> productList = productService.getProductsByName(name);
            List<ProductDto> productDtos=productService.getConvertedProducts(productList);

            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{brand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<Product> productList = productService.getProductsByBrand(brand);
            List<ProductDto> productDtos=productService.getConvertedProducts(productList);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> productList = productService.getProductsByCategory(category);
            List<ProductDto> productDtos=productService.getConvertedProducts(productList);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/count/by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductCountByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Success", "Product count is :" + productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

