package com.abc.repository;

import com.abc.bean.SysLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLogEntity,Integer> {
}
