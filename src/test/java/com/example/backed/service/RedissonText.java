package com.example.backed.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RedissonText {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test(){
        //list,数据存储在本地JVM中
        List<String> list =new ArrayList<>();
        list.add("serein");
        System.out.println("List"+list.get(0));
        list.remove(0);
        //数据存在redis的内存中
        RList<String> rList =redissonClient.getList("test-list");
        rList.add("serein");
        System.out.println("rList:"+rList.get(0));
        rList.remove(0);
        //map
        RMap<Object, Object> map1 = redissonClient.getMap("test-map");
        map1.put("serein", 10);
        map1.get("serein");
        map1.remove("serein");
        //set
        //stack
    }
}
