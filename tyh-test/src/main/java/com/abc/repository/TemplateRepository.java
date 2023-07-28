package com.abc.repository;

import com.abc.bean.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository
        extends JpaRepository<Template, Long> {

    // 根据ID查询出所有券模板
    Template findByCode(String code);

    // IN查询 + 分页支持的语法
    Page<Template> findAllByIdIn(List<Long> Id, Pageable page);

    // 将优惠券设置为不可用
    // @Modifying
    // @Query("update CouponTemplate c set c.available = 0 where c.id = :id")
    // int makeCouponUnavailable(@Param("id") Long id);
}
