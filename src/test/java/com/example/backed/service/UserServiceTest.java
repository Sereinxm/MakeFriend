package com.example.backed.service;

import com.example.backed.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * 用户服务测试
 * **/

@SpringBootTest
class UserServiceTest {


    @Resource
    private UserService userService;


    @Test
    void testAddUser(){
        User user = new User();

        user.setUsername("31dsa23");
        user.setUserAccount("123456");
        user.setAvatarUrl("https://i.postimg.cc/c1pGgkZh/16070652274615705.jpg");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123456");
        user.setEmail("123456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }
    @Test
    void userRegister(){

        String userAccount="caocao";
        String userPassword="123456789";
        String checkPassword="123456789";
        String planetCode="1";
        long result= userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu";
        userPassword="123456";
        checkPassword="123456";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yupi";
        userPassword="123456";
        checkPassword="123456";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu545";
        userPassword="123456";
        checkPassword="123456";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu65445";
        userPassword="123456";
        checkPassword="123456321";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu78pi";
        userPassword="123456";
        checkPassword="123456";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="wangping";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="2";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);



    }
}