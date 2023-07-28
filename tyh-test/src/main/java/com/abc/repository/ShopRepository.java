package com.abc.repository;

import com.abc.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);

    boolean existsByName(String name);
}
