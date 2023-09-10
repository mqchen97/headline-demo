package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author admin
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2023-09-09 23:53:04
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(wrapper);

        if (loginUser == null) {
            Result result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
            System.out.println("result = " + result);
            return result;
        }

        if(!StringUtils.isEmpty(loginUser.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap();
            data.put("token", token);
            Result result = Result.ok(data);
            System.out.println("result = " + result);
            return result;
        }else {
            Result result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
            System.out.println("result = " + result);
            return result;
        }
    }

    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if(expiration){
            // token过期， 以未登录看待
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        Long userId = jwtHelper.getUserId(token);
        User user = userMapper.selectById(userId);
        System.out.println("user = " + user);
        user.setUserPwd("");
        Map data = new HashMap<>();
        data.put("loginUser", user);
        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(wrapper);
        if (count == 0) {
            return Result.ok(null);
        }else{
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count != 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        int insert = userMapper.insert(user);
        if(insert == 1){
            return Result.ok(null);
        }else {
            return Result.build(null, ResultCodeEnum.COMMON_ERROR);
        }
    }
}




