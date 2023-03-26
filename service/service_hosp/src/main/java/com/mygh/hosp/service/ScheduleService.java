package com.mygh.hosp.service;

import com.mygh.model.hosp.Schedule;
import com.mygh.vo.hosp.ScheduleOrderVo;
import com.mygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    /**
     * 上传排班接口
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询排班接口
     * @param page
     * @param limit
     * @param scheduleQueryVo
     * @return
     */
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    /**
     * 删除排班
     * @param hoscode
     * @param hosScheduleId
     */
    void remove(String hoscode, String hosScheduleId);

    //根据医院编号和科室编号，查询排班规则数据
    Map<String, Object> getScheduleRule(long page, long limit, String hoscode, String depcode);

    //根据医院编号、科室编号和工作日期，查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    //获取可预约排班数据
    Map<String,Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    //根据排班id获取排班数据
    Schedule getScheduleById(String scheduleId);

    //根据排班id获取预约下单数据
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    //更新排班数据 用于mp
    void update(Schedule schedule);
}
