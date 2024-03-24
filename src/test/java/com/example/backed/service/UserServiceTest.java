package com.example.backed.service;

import com.example.backed.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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

        String userAccount="caoc1aaeo";
        String userPassword="123456789";
        String checkPassword="123456789";
        String planetCode="1";
        long result= userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);


        userAccount="yutrtre";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="22";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);


        userAccount="yu59pi";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="23";

        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);


        userAccount="yu545";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="12";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);


        userAccount="yu65445";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="22";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);
        ;

        userAccount="yu78dspi";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="32";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);


        userAccount="wangpdsing";
        userPassword="123456789";
        checkPassword="123456789";
        planetCode="42";
        result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);
    }
    /*
     * 获取tag测试
     */
    @Test
    public void searchUserByTags(){
        List<String> tagNameList = Arrays.asList("Java","Python");
        List<User>users=userService.searchUserByTags(tagNameList);
        Assert.assertNotNull(users);

    }

}