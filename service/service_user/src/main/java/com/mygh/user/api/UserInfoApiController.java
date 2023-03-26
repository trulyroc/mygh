package com.mygh.user.api;

import com.mygh.common.result.Result;
import com.mygh.common.utils.AuthContextHolder;
import com.mygh.model.user.UserInfo;
import com.mygh.user.service.UserInfoService;
import com.mygh.vo.user.LoginVo;
import com.mygh.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    //用户手机号登录接口
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request){
        //传入参数  ①用户id   ②认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    //获取用户id信息接口
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }


}
