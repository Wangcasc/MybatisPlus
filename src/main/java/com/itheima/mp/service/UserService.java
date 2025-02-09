package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;

public interface UserService extends IService<User> {

    void deductionBalance(Long id, Integer money);
    //继承MP官方提供的IService接口
}
