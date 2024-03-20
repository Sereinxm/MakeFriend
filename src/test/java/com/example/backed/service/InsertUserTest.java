package com.example.backed.service;

import com.example.backed.mapper.UserMapper;
import com.example.backed.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class InsertUserTest {
    @Resource
    private UserService userService;
    public static String getRandomTags() {
        List<String> tags = Arrays.asList("java", "c++", "javascript", "c", "c#", "rust", "golang", "跑步", "遛狗", "游泳", "撸铁", "乒乓球", "篮球", "足球", "排球", "羽毛球", "打电动", "唱歌", "跳舞", "睡觉");

        Collections.shuffle(tags);

        List<String> selectedTags = tags.subList(0, 3);

        return "[" + String.join(", ", selectedTags) + "]";
    }

    /**
     * 循环插入用户
     */
    @Test
    public void doInsertUser() {
        StopWatch stopWatch =new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        List<User> userList=new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假serein");
            user.setUserAccount("daffwdas");
            user.setAvatarUrl("https://img2.imgtp.com/2024/03/20/5xb02TLk.png");
            user.setGender(0);
            user.setUserPassword("123456789");
            user.setPhone("123456798");
            user.setEmail("dsafadss@shajk.com");
            user.setTags(getRandomTags());
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("126");
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());
    }
}