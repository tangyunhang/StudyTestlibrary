package com.abc.service.impl;

import com.abc.annotation.aop.MyAfter;
import com.abc.annotation.aop.MyBefore;
import com.abc.bean.Product;
import com.abc.repository.ShopRepository;
import com.abc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: ShopServiceImpl
 * @Author: 青衣醉
 * @Date: 2022/7/15 3:27 下午
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    public ShopRepository shopRepository;

    @Autowired
    public ApplicationContext applicationContext;
    @Override
    public boolean saveProduct(Product product) {
        return Optional.ofNullable (shopRepository.save (product))
                .isPresent ();
    }

    @Override
    public void deleteProductById(Integer id) {
        if (shopRepository.existsById (id)) {
            shopRepository.deleteById (id);
        }
    }

    @MyBefore("com.abc.service.impl.TestCstomAopService.strengthenBeforewc")
    //@MyAfter("com.abc.service.impl.TestCstomAopService.strengthenAfter")
    @Override
    public List<Product> listAllProducts() {
        return shopRepository.findAll ();
    }

    @MyBefore("com.abc.service.impl.TestCstomAopService.strengthenBefore")
    @MyAfter("com.abc.service.impl.TestCstomAopService.strengthenAfter")
    @Override
    public Product getProduct(String name) {
        if (shopRepository.existsByName (name)){
            return shopRepository.findByName (name);
        }
        return Product.builder ().build ();
    }


}
