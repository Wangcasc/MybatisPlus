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


    @Override
    public void deductionBalance(Long id, Integer money) {
        // 根据ID查询用户 直接自己调用自己的方法 继承自ServiceImpl中的方法
        User user = this.getById(id);
        // 判断用户是否存在 如果存在，还需要判断状态是否正常 如果正常，还需要判断余额是否充足
        if (user != null && user.getStatus() == 1 && user.getBalance() >= money) {
            // 扣减用户余额
            user.setBalance(user.getBalance() - money);
            // 更新用户
            this.updateById(user);

        }
        else {
            throw new RuntimeException("用户不存在或状态异常或余额不足");
        }

    }


}

