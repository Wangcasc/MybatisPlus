package com.itheima.mp.service;

import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    // 测试新增
    @Test
    void testSave() {
        User user = new User();
        user.setUsername("Luchal");
        user.setPassword("123");
        user.setPhone("18688990019");
        user.setBalance(2000);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\",\"gender\": \"female\"}");
        user.setInfo(new UserInfo(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    // 测试查询
    @Test
    void testGetById() {
        User user = userService.getById(1L);
        System.out.println("user = " + user);
    }

    // 测试批量查询
    @Test
    void testQueryByIds() {
        userService.listByIds(List.of(1L, 2L, 3L, 4L)).forEach(System.out::println);
    }

    // 测试批量新增
    private User createUser( int i) {
        User user = new User();
        user.setUsername("User" + i);
        user.setPassword("123");
        user.setPhone("18688990019" + i);
        user.setBalance(2000);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\",\"gender\": \"female\"}");
        user.setInfo(new UserInfo(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

    @Test
    void testSaveBatch() {
        // 先用for循环保存1000条数据
        // 开始时间
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            userService.save(createUser(i));
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("for循环保存1000条数据耗时：" + (end - start) + "ms");

        // 再用saveBatch保存1000条数据 每10个数据为一个批次 保存一次
        // 开始时间
        start = System.currentTimeMillis();


    }
}