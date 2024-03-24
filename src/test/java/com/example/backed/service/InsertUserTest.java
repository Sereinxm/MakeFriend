package com.example.backed.service;
import com.example.backed.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SpringBootTest
public class InsertUserTest {
    @Resource
    private UserService userService;

    /**
     * 随机插入tag
     * @return
     */
    public static String getRandomTagsWithQuotes() {
        List<String> tags = Arrays.asList("java", "c++", "javascript", "c", "c#", "rust", "golang","男","女","大一" ,"大二","跑步", "遛狗", "游泳", "撸铁", "乒乓球", "篮球", "足球", "排球", "羽毛球", "打电动", "唱歌", "跳舞", "睡觉");

        Collections.shuffle(tags);

        List<String> selectedTags = tags.subList(0, 3);

        List<String> quotedTags = selectedTags.stream()
                .map(tag -> "\"" + tag + "\"")
                .collect(Collectors.toList());

        return "[" + String.join(", ", quotedTags) + "]";
    }
    /**
     *
     * 随机插入头像链接
     */
    public static String chooseRandomAvatar() {
        String[] avatarUrls = {
        "https://img2.imgtp.com/2024/03/24/n2BsWNr3.png",
        "https://img2.imgtp.com/2024/03/24/YqRzzwno.png",
        "https://img2.imgtp.com/2024/03/24/rmJE6rx9.png",
        "https://img2.imgtp.com/2024/03/24/kp7299LC.png",
        "https://img2.imgtp.com/2024/03/24/ZkDmklYP.png",
        "https://img2.imgtp.com/2024/03/24/tU3W7uI4.png",
        "https://img2.imgtp.com/2024/03/24/9eW7pmZb.png",
        "https://img2.imgtp.com/2024/03/24/SeeyqAUf.png",
        "https://img2.imgtp.com/2024/03/24/7c68bWaW.png",
        "https://img2.imgtp.com/2024/03/24/uVrUwvAS.png",
        "https://img2.imgtp.com/2024/03/24/7O6uypGX.png",
        "https://img2.imgtp.com/2024/03/24/YNrsSpGT.png",
        "https://img2.imgtp.com/2024/03/24/RnsEZuRP.png",
        "https://img2.imgtp.com/2024/03/24/H2AEtlYT.png",
        "https://img2.imgtp.com/2024/03/24/wihYaXrh.png"
        };
        Random random = new Random();
        return avatarUrls[random.nextInt(avatarUrls.length)];
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
            user.setUsername("1234");
            user.setUserAccount("1234");
            user.setAvatarUrl(chooseRandomAvatar());
            user.setGender(0);
            user.setUserPassword("8673e816988e8ed0b910669251ef5782");
            user.setPhone("123456798");
            user.setEmail("dsafadss@shajk.com");
            user.setTags(getRandomTagsWithQuotes());
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("126");
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());
    }

    /**
     * 并发插入数据
     */
    @Test
    public void doConcurrencyInsertUser() {
        StopWatch stopWatch =new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        // 分十组
        int j = 0;
        //批量插入数据的大小
        int batchSize = 5000;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM/batchSize; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("假serein");
                user.setUserAccount("daffscwdas");
                user.setAvatarUrl(chooseRandomAvatar());
                user.setGender(0);
                user.setUserPassword("8673e816988e8ed0b910669251ef5782");
                user.setPhone("123456798");
                user.setEmail("dsafadss@shajk.com");
                user.setTags(getRandomTagsWithQuotes());
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("126");
                userList.add(user);
                if (j % batchSize == 0 ){
                    break;
                }
            }
            //异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
                System.out.println("ThreadName：" + Thread.currentThread().getName());
                userService.saveBatch(userList,batchSize);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());
    }
}
