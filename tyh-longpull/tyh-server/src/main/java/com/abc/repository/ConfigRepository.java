package com.abc.repository;

import com.abc.bean.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Description: ConfigRepository
 * @Author: 青衣醉
 * @Date: 2022/9/5 5:26 下午
 */
public interface ConfigRepository extends JpaRepository<ConfigEntity,Integer> {
    // 将配置信息设置为不启用
    @Modifying
    @Query("update ConfigEntity c set c.isActive = 0 where c.dataId= :dataId")
    int makeConfigIsActive(@Param("dataId") String dataId);

    void deleteByDataIdAndIsActive(@Param("dataId") String dataId,@Param("isActive") boolean isActive);

    ConfigEntity findByDataIdAndIsActive(@Param("dataId") String dataId,@Param("isActive") boolean isActive);
}
