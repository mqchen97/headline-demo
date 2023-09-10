package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println("user = " + user);
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("/checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("/regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        System.out.println("result = " + result);
        return result;
    }
}
