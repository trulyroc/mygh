package com.mygh.hosp.controller.api;

import com.mygh.common.exception.MyghException;
import com.mygh.common.helper.HttpRequestHelper;
import com.mygh.common.result.Result;
import com.mygh.common.result.ResultCodeEnum;
import com.mygh.common.utils.MD5;
import com.mygh.hosp.service.DepartmentService;
import com.mygh.hosp.service.HospitalService;
import com.mygh.hosp.service.HospitalSetService;
import com.mygh.hosp.service.ScheduleService;
import com.mygh.model.hosp.Department;
import com.mygh.model.hosp.Hospital;
import com.mygh.model.hosp.Schedule;
import com.mygh.vo.hosp.DepartmentQueryVo;
import com.mygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private HospitalSetService hospitalSetService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ScheduleService scheduleService;

    /**
     * 删除排班
     * @param request
     * @return
     */
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号 和 排班编号
        String hosScheduleId = (String) paramMap.get("hosScheduleId");
        String hoscode = (String) paramMap.get("hoscode");

        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    /**
     * 查询排班接口
     * @param request
     * @return
     */
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //科室编号
        String depcode = (String) paramMap.get("depcode");

        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ?1:Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ?1:Integer.parseInt((String) paramMap.get("limit"));

        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);

    }

    /**
     * 上传排班接口
     * @param request
     * @return
     */
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);
        return Result.ok();
    }

    /**
     * 删除科室接口
     * @param request
     * @return
     */
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号和科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    /**
     * 查询科室接口
     * @param request
     * @return
     */
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ?1:Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ?1:Integer.parseInt((String) paramMap.get("limit"));

        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 上传科室接口
     * @param request
     * @return
     */
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取传递过来的医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }


    /**
     * 查询医院
     * @param request
     * @return
     */
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取传递过来的医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2.根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);

    }

    /**
     * 上传医院接口
     * @param request
     * @return
     */
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2.根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new MyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //传输过程中 "+" 转换为了 " " ，因此要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

        //调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
