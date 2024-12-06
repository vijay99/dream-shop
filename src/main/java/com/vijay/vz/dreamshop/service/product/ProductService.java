package com.vijay.vz.dreamshop.service.product;

import com.vijay.vz.dreamshop.exceptions.ProductNotFoundException;
import com.vijay.vz.dreamshop.exceptions.ResourceNotFoundException;
import com.vijay.vz.dreamshop.model.Category;
import com.vijay.vz.dreamshop.model.Product;
import com.vijay.vz.dreamshop.repository.CategoryRepository;
import com.vijay.vz.dreamshop.repository.ProductRepository;
import com.vijay.vz.dreamshop.request.AddProductRequest;
import com.vijay.vz.dreamshop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //Check if the category is found in db.If yes,set it as new product category.
        //If no, then save it as a new category,then set as the new product category.

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet( () -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
                request.setCategory(category);
                return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
      return  new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete,() -> {throw new ProductNotFoundException("Product not found");});

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map( existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(() ->new ResourceNotFoundException("Product not found"));

    }
    private Product updateExistingProduct(Product existingProduct,ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
