package com.mygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mygh.model.user.Patient;

import java.util.List;

public interface PatientService extends IService<Patient> {
    //获取就诊人的列表
    List<Patient> findAllUserId(Long userId);

    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}
