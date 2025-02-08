package com.itheima.mp.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //继承MP官方提供的ServiceImpl类，实现自定义UserService接口
    //ServiceImpl需要指定两个泛型，第一个是基于BaseMapper衍生的接口，第二个是要操作的实体类
    //因为之前已经在UserMapper接口中继承了BaseMapper接口，所以这里直接用于指定ServiceImpl的泛型



}

