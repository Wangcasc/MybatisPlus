package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.dto.UserQueryDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {

    void deductionBalance(Long id, Integer money);

    List<UserVO> listUsersByComplexCondition(UserQueryDTO userQueryDTO);

    UserVO queryUserAndAddressById(Long id);

    List<UserVO> queryUserAndAddressByIds(List<Long> ids);
    //继承MP官方提供的IService接口
}
