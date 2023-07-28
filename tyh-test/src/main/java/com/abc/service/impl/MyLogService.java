package com.abc.service.impl;

import com.abc.bean.SysLogEntity;
import com.abc.repository.SysLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: SysLogService
 * @Author: 青衣醉
 * @Date: 2022/8/9 2:06 下午
 */
@Service
@Slf4j
public class MyLogService {

    @Autowired
    SysLogRepository sysLogRepository;
    public void saveLog(SysLogEntity sysLogEntity){
        log.info ("保存系统日志 :{}",sysLogEntity.toString ());
        sysLogRepository.save (sysLogEntity);
    }
}
