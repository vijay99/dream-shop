package com.vijay.vz.dreamshop.repository;

import com.vijay.vz.dreamshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
