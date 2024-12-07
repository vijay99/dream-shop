package com.vijay.vz.dreamshop.service.product;

import com.vijay.vz.dreamshop.dto.ProductDto;
import com.vijay.vz.dreamshop.model.Product;
import com.vijay.vz.dreamshop.request.AddProductRequest;
import com.vijay.vz.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);


    List<ProductDto> getConvertedProducts(List<Product> productList);

    ProductDto convertToDto(Product product);
}
