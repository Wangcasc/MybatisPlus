package com.itheima.mp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.Address;
import com.itheima.mp.domain.dto.UserQueryDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //继承MP官方提供的ServiceImpl类，实现自定义UserService接口
    //ServiceImpl需要指定两个泛型，第一个是基于BaseMapper衍生的接口，第二个是要操作的实体类
    //因为之前已经在UserMapper接口中继承了BaseMapper接口，所以这里直接用于指定ServiceImpl的泛型


    @Transactional //事务注解 保证事务中数据的一致性
    @Override
    public void deductionBalance(Long id, Integer money) {
        //// 根据ID查询用户 直接自己调用自己的方法 继承自ServiceImpl中的方法
        User user = this.getById(id);
        // 判断用户是否存在 如果存在，还需要判断状态是否正常 如果正常，还需要判断余额是否充足
        if (user != null && user.getStatus() == UserStatus.NORMAL && user.getBalance() >= money) {
            // 扣减用户余额
            //user.setBalance(user.getBalance() - money);
            // 更新用户
            //this.updateById(user);

            //使用lamdaUpdate方法
            boolean update = this.lambdaUpdate()
                    .set(User::getBalance, user.getBalance() - money) //跟新余额
                    .eq(user.getBalance()-money==0, User::getStatus, UserStatus.FROZEN) //余额为0，状态改为2 这里使用MP的枚举类转换
                    .eq( User::getId, id)
                    .eq( User::getBalance, user.getBalance()) //乐观锁 防止并发
                    .update();

        }
        else {
            throw new RuntimeException("用户不存在或状态异常或余额不足");
        }

    }

    @Override
    public List<UserVO> listUsersByComplexCondition(UserQueryDTO userQueryDTO) {
        // 根据复杂条件查询用户
        // 之前的方法就是使用MybatisPlus的QueryWrapper
        //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq( userQueryDTO.getName() != null, "name", userQueryDTO.getName());
        //queryWrapper.ge("balance", userQueryDTO.getMinBalance());
        //queryWrapper.le("balance", userQueryDTO.getMaxBalance());
        //List<User> users = this.list(queryWrapper);
        ////po对象转换为vo对象
        //List<UserVO> userVOList = new ArrayList<>();
        //for (User user : users) {
        //    UserVO userVO = new UserVO();
        //    BeanUtils.copyProperties(user, userVO);
        //    userVOList.add(userVO);
        //}
        //return userVOList;

        //现在可以直接使用IService提供的lambdaQuery方法
        List<User> users = this.lambdaQuery()
                .like(userQueryDTO.getName() != null, User::getUsername, userQueryDTO.getName())
                .eq(userQueryDTO.getStatus() != null, User::getStatus, userQueryDTO.getStatus())
                .ge(User::getBalance, userQueryDTO.getMinBalance())
                .le(User::getBalance, userQueryDTO.getMaxBalance())
                .list();

        List<UserVO> userVOList = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        // 根据ID查询用户
        User user = this.getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 查询用户的地址列表
        List<Address> addressList = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        //po对象转换为vo对象
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        if (addressList != null && !addressList.isEmpty()) {
            List<AddressVO> addressVOList = new ArrayList<>();
            for (Address address : addressList) {
                AddressVO addressVO = new AddressVO();
                BeanUtils.copyProperties(address, addressVO);
                addressVOList.add(addressVO);
            }
            userVO.setAddressList(addressVOList);
        }

        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        // 根据ID批量查询用户
        List<User> users = this.listByIds(ids);
        if (users == null || users.isEmpty()) {
            throw new RuntimeException("用户不存在");
        }
        // po对象转换为vo对象
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            // 查询用户的地址列表
            List<Address> addressList = Db.lambdaQuery(Address.class)
                    .eq(Address::getUserId, user.getId())
                    .list();
            if (addressList != null && !addressList.isEmpty()) {
                List<AddressVO> addressVOList = new ArrayList<>();
                for (Address address : addressList) {
                    AddressVO addressVO = new AddressVO();
                    BeanUtils.copyProperties(address, addressVO);
                    addressVOList.add(addressVO);
                }
                userVO.setAddressList(addressVOList);
            }
            userVOList.add(userVO);
        }
        return userVOList;
    }


}

