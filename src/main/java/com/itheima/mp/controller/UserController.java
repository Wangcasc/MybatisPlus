package com.itheima.mp.controller;

import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/users")
//@AllArgsConstructor 没必要为所有的字段生成构造方法
@RequiredArgsConstructor // 为所有的final常量字段生成构造方法
public class UserController {

    //@Autowired  //字段注入
    //private UserService userService;

    private final UserService userService;
    //public UserController(UserService userService) {
    //    this.userService = userService;
    //}

    @ApiOperation("保存用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        // 保存用户
        User user = new User();
        // 将DTO对象的属性拷贝到PO对象中
        BeanUtils.copyProperties(userFormDTO, user);
        // 调用serviceAPI保存用户
        userService.save(user);


    }
}
