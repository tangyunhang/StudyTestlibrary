package com.abc.service;

import com.abc.bean.Product;

import java.util.List;

public interface ShopService {
    boolean saveProduct(Product product);

    void deleteProductById(Integer Id);

    List<Product> listAllProducts();

    Product getProduct(String name);
}
