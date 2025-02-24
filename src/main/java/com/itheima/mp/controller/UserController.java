package com.itheima.mp.controller;

import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.dto.UserQueryDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // 删除用户
        userService.removeById(id);
    }

    @ApiOperation("根据ID查询用户接口")
    @GetMapping("/{id}")
    public UserVO getUserById(@PathVariable Long id) {
        // 根据ID查询用户
        //User user = userService.getById(id);
        ////po对象转换为vo对象
        //UserVO userVO = new UserVO();
        //BeanUtils.copyProperties(user, userVO);
        //return userVO;

        return userService.queryUserAndAddressById(id);
    }

    @ApiOperation("根据ID批量查询用户接口")
    @GetMapping
    public List<UserVO> listUsersByIds(@RequestParam List<Long> ids) {
        //// 根据ID批量查询用户
        //List<User> users = userService.listByIds(ids);
        ////po对象转换为vo对象
        //List<UserVO> userVOList = new ArrayList<>();
        //for (User user : users) {
        //    UserVO userVO = new UserVO();
        //    BeanUtils.copyProperties(user, userVO);
        //    userVOList.add(userVO);
        //}
        //return userVOList;

        return userService.queryUserAndAddressByIds(ids);
    }

    @ApiOperation("根据ID扣减用户余额")
    @PutMapping("/{id}/deduction/{money}")
    public void deductionBalance(@PathVariable Long id, @PathVariable Integer money) {
        //// 根据ID查询用户
        //User user = userService.getById(id);
        //// 判断用户是否存在 如果存在，还需要判断状态是否正常 如果正常，还需要判断余额是否充足
        //if (user != null && user.getStatus() == 1 && user.getBalance() >= money) {
        //    // 扣减用户余额
        //    user.setBalance(user.getBalance() - money);
        //    // 更新用户
        //    userService.updateById(user);
        //}
        //else {
        //    throw new RuntimeException("用户不存在或状态异常或余额不足");
        //}

        //需要遵循数据库业务逻辑在Mapper层实现
        userService.deductionBalance(id, money);
    }


    @ApiOperation("根据复杂条件查询用户")
    @GetMapping("/list")
    public List<UserVO> listUsersByComplexCondition(UserQueryDTO userQueryDTO) {
        // 根据复杂条件查询用户
        List<UserVO> userVOList = userService.listUsersByComplexCondition(userQueryDTO);
        return userVOList;
    }


}
