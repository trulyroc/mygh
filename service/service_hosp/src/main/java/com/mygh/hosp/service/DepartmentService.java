package com.mygh.hosp.service;


import com.mygh.model.hosp.Department;
import com.mygh.vo.hosp.DepartmentQueryVo;
import com.mygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void save(Map<String, Object> paramMap);

    /**
     * 查询科室接口
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    /**
     * 删除科室接口
     * @param hoscode
     * @param depcode
     */
    void remove(String hoscode, String depcode);

    //根据医院编号，查询所有科室列表
    List<DepartmentVo> findDeptTree(String hoscode);

    //根据科室编号和医院编号，查询科室名称
    String getDepName(String hoscode, String depcode);

    //根据科室编号和医院编号，查询科室
    Department getDepartment(String hoscode, String depcode);
}
