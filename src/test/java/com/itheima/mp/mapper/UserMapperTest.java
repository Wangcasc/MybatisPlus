package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

// MyBatisPlus 测试
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(new UserInfo(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //userMapper.saveUser(user);
        //使用MyBatisPlus
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.queryUserById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateUser(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteUser(5L);
    }



    //案例
    @Test
    void testSelectByQueryWrapper() {
        //创建条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置条件 from不用写
        wrapper.select("id", "username", "info")
                .like("username", "o")
                .ge("balance", 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    void testUpdateByQueryWrapper() {
        //设置要更新的数据
        User user = new User();
        user.setBalance(10000);
        //创建条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置条件 from不用写
        wrapper.eq("username", "Jack");
        userMapper.update(user, wrapper);
        //查询是否更新成功
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("username", "Jack");
        User user1 = userMapper.selectOne(wrapper1);
        System.out.println(user1);
    }

    @Test
    void testUpdateWrapper() {

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        //wrapper.in("id", 1, 2, 3, 4)
        //        .setSql("balance = balance + 1000");
        //userMapper.update(null, wrapper);

        //或者使用集合
        wrapper.in("id", List.of(1, 2, 3, 4))
                .setSql("balance = balance + 1000");
        userMapper.update(null, wrapper);

    }
}