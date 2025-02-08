package com.itheima.mp.controller;

import com.itheima.mp.domain.dto.UserFormDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/users")
public class UserController {

    @ApiOperation("保存用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        // 保存用户 
    }
}
