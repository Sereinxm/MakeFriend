# 知音集结系统

## Java8特性：

1. stream / parallelStream 流式处理
2. Optional 可选类，避免多层 if-else 嵌套

## Swagger + Knife4j 接口文档整合

官方文档：https://doc.xiaominfo.com/docs/quick-start

生产环境屏蔽接口文档：application-prod.yml添加

```yaml
knife4j:
  # 开启生产环境屏蔽
  production: true
```

Knife4j：学习原理

## EasyExcel

https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E5%AF%B9%E8%B1%A1

入门案例

## 根据`标签名列表`获取`用户列表`

前端整合axios

## 分布式session

单机登录改为分布式登录

>  后台，启动两个实例port：8080  8081 
>
> 在8080 登陆后，可以获取登录态
>
> 在8081 获取当前用户，显示未登录，经过调试发现是因为jseesionID发生改变，所以后端无法获取登录用户信息

设置session的范围

> 两个域名：
>
> - aaa.xing.com
> - bbb.xing.com
>
> 可以设置domain为xing.com，那么这两个域名都可以共享该cookie

使用redis实现分布式session，实现单点登录

> 即把数据集中到一个地方存储
>
> 1. quickredis管理工具
> 2. 引入redis
> 3. 引入session-redis
> 4. 修改session=》store-type

jwt 和 session 实现单点登录的区别：

> https://zhuanlan.zhihu.com/p/108999941

## 列表组件抽象

查询结果页用户列表，和首页推荐用户列表，可以抽象出一个公共组件

## 数据库插入100w数据

如果现在要模拟查询100W用户，来测试查询接口的性能，那么如何导入这些假数据？

要保证可控，所以使用可视化界面就不太适合，另外SQL语句适合少量数据插入

- 方法一：for循环插入

  > 每次sql连接建立和关闭花费大量时间，浪费性能

- 方法二：批量插入MyBatis-Plus自带的 saveBatch 方法（9 sec in 10w）

  > 预编译优化，只编译一次sql，所以快，但是本质还是for循环一条一条插入

- 方法三：mybatis plus 分批插入 + java8 异步并发 CompletableFuture.runAsync()，分批插入10w数据（4 sec in 10 w）

  ```java
  private ExecutorService executorService = new ThreadPoolExecutor(40, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
  
  void doConcurrencyInsertUsers() {
          StopWatch stopWatch = new StopWatch();
          stopWatch.start();
          // 每批插入1k数据
          int batchSize = 1000;
          int j = 0;
          List<CompletableFuture<Void>> futureList = new ArrayList<>();
          for (int i = 0; i < 100; i++) {//100个异步任务，100*1k = 10w条数据
              List<User> userList = new ArrayList<>();
              while(true) {
                  j++;
                  if (j % batchSize == 0) { //够一批数量了
                      break;
                  }
              }
              // 异步执行
              CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                  System.out.println("threadName: " +Thread.currentThread().getName());
                  userService.saveBatch(userList, batchSize);
              }, executorService);
              futureList.add(future);//将该异步任务对象添加进集合
          }
          CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();//等待所有的异步任务完成
          // 4 sec 10 万条
          stopWatch.stop();
          System.out.println(stopWatch.getTotalTimeMillis());
      }
  ```

## 主页推荐接口	

- 用户列表一次性展示太多，不合理，进行分页展示
- 分页展示，100w 数据，查询接口 2 sec，太慢了，（缓存优化至600ms）
- 将分页数据预加载缓存到redis，定时更新缓存（定时任务）
- 多个机器都要执行任务吗？（分布式锁，同一时间只有一个机器执行定时任务，防止重复执行）

## 缓存主页推荐数据

分布式缓存：

- redis
- memcached

单机缓存：

- Caffeine（Java 内存缓存，高性能）

## Java 操作 Redis

### Spring Data Redis：

Spring Data：通用的数据访问框架，定义了一组 crud 接口

还可以操作：mysql，redis，jpa

> 1. 引入依赖
> 2. 配置 redis 地址

### 操作方式对比：

1. 如果你用Spring开发，并且没有过多的定制化要求选Spring Data Redis
2. 如果你没有用Spring，并且追求简单，没有过多的性能要求，可以用Jedis + Jedis Pool
3. 如果你的项目不是Spring，并且追求高性能，高定制化，可以用lettuce。支持异步、连接池（技术大牛使用）
4. 如果你的项目是分布式的，需要用到一些分布特性（比如分布式锁，分布式集合），推荐使用Redisson

## 解决redisTemplate存入redis出现乱码问题

```java
@Configuration
public class RedisConfig {
   @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate redisTemplateInit() {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

```

设置过期时间，redis内存使用不能无限增加！

## 缓存预热

意义：

1. 系统用户量很多，但是每天新增用户量不多，所以就可以先做缓存预热，对于数据的一致性要求没有那么高，另外可以一定程度上保护数据库。

2. 另外，缓存预热也可以让第一个用户加载速度加快，提高用户体验

缺点：

1. 增加开发成本（额外的开发、设计）
2. 预热的时机不对，可能导致缓存的数据不对，或者缓存的是老数据
3. 需要占用额外的空间（如果预热过多数据，导致redis空间不足，所以需要预热那些热点数据）

怎么缓存预热：

1. 定时任务
2. 手动触发

注意点：

1. 缓存的数据量不能太大，要预留缓存空间
2. 缓存数据的周期（一天）

实现：用定时任务，每天刷新缓存的用户推荐列表

Spring Scheduler

## 控制定时任务的执行

为什么？

> 要控制定时任务在同一时间只有一个服务器能执行，否则会浪费资源，另外可能会出现脏数据，比如重复插入

怎么做：

> 1. 将主程序和定时任务程序分离,只在一个服务器上运行定时任务
>
> 2. 每个服务器都执行定时任务,但是执行前要判断ip,是否符合配置,如果不符合,那么直接返回.成本最低,但是ip可能是不固定的,把ip写的太死了
>
> 3. 动态配置,代码无需重启,只有ip符合配置的服务器才真实执行业务逻辑.
>
>    - 数据库
>    - redis
>    - 配置中心(Nacos , Apollo, Spring Cloud Config)
>
>    问题:服务器多了,ip不可控,仍需要人工修改
>
> 4. 分布式锁,增加成本,但是不用手动配置

注意:只要是单机,就会出现单机故障

为什么需要分布式锁:

> 1. 资源有限的情况下,要控制同一时间内,只有某些服务器能访问到资源
> 2. 单机的synchronized,并发包的类,只对单个jvm有效,不能控制其他服务器

核心思想: 

> 先来的人把数据改成自己的标识(服务器ip), 后来的人发现标识已存在, 就抢锁失败, 继续等待

实现方式：

1. MySQL数据库：select for update行级锁（最简单）

2. 乐观锁

3. **Redis实现**：内存数据库，速度快。支持setnx，lua脚本支持原子性操作

   setnx: set if not exists如果不存在，则设置；只有设置成功才会返回true

4. Zookeeper实现（不推荐）

注意事项:

> 1. 用完锁要释放,腾地方
>
> 2. 锁一定要加过期时间, 
>
>    没来得及释放锁，因系统故障无法执行释放锁的命令,导致其它线程都无法获得锁，造成死锁。
>
> 3. 程序执行40s,而锁30过期,也会出现多个线程同时执行
>
>    > 1. 可能出现连锁效应: 释放掉别人的锁,
>    >
>    > 解决: 释放锁的时候检查锁的标识,如果不是自己的标识,就不释放
>    >
>    > 2. 锁过期时间 续期（看门狗机制）开启一个监听线程，如果方法还没执行完，就帮你重置redis锁的过期时间

> 4. 释放锁的时候检查锁的标识,如果不是自己的标识,就不释放: 可能先判断出是自己的锁, 但这时锁过期了, 最后仍然释放了别人的锁
>
>    解决: 使用lua 脚本, 保证 判断+ 释放 是一个原子操作
>
> 5. 如果redis是集群，如果分布式锁的数据不同步怎么办？
>
>    > 

## Redisson 实现分布式锁

Redisson 是一个 java 操作 Redis 的客户端，实现了很多java里支持的集合。可以让开发者像使用本地集合一样使用Redis,完全感知不到Redis 的存在

引入：https://github.com/redisson/redisson#quick-start

RedisonConfig

定时任务添加分布式锁

tryLock(waitTime, expireTime, unit)

> 如果waitTime设置为0，那么只抢一次锁，抢不到就放弃
>
> 注意释放锁要写在 finally 中
>
> expireTime如果设置为-1，那么会触发看门狗机制

Redissson看门狗机制

> redisson 中提供的续期机制

开一个监听线程，如果方法还没有执行完，就会帮你重置 redis 锁的过期时间

原理：

1. 监听当前线程，默认过期时间是30s，每过10s，就会重置过期时间为 30s 
2. 如果线程挂掉（debug模式也被当做服务器宕机），则不会续期



## 组队功能

### 数据库表

1. team

   ```sql
   create table team
   (
       id           bigint auto_increment comment 'id'
           primary key,
       name     varchar(256)                                                                                      null comment '队伍名称',
       description    varchar(1024)  null comment '队伍描述',
       userId           bigint  comment '创建人id' ,
       password varchar(512)                                                                                      null comment '加密队伍的入队密码',
       type   int           default 0                                                                           null comment '队伍类型 0-公开 1-私密 2-加密',
       maxNum int  null comment '最大人数',
       expireTime   datetime   null comment '过期时间',
       createTime   datetime      default CURRENT_TIMESTAMP                                                           null comment '创建时间',
       updateTime   datetime      default CURRENT_TIMESTAMP                                                           null on update CURRENT_TIMESTAMP comment '更新时间',
       isDelete     int           default 0                                                                           not null comment '是否删除'
   )
       comment '用户表';
   ```

   

2. user_team

   > 两个关系：
   >
   > 1. 一个用户加入了哪些队伍
   > 2. 一个队伍有哪些用户

   ```sql
   create table user_team
   (
       id           bigint auto_increment comment 'id' primary key,
       userId           bigint  comment '用户id' ,
       teamId           bigint  comment '队伍id' ,
       joinTime   datetime   null comment '加入时间',
       createTime   datetime      default CURRENT_TIMESTAMP                                                           null comment '创建时间',
       updateTime   datetime      default CURRENT_TIMESTAMP                                                           null on update CURRENT_TIMESTAMP comment '更新时间',
       isDelete     int           default 0                                                                           not null comment '是否删除'
   )
       comment '用户-队伍关系表';
   ```



### crud

#### 创建队伍

> 1. 请求参数是否为空
> 2. 是否登录，未登录，不允许创建
> 3. 校验信息
>    1. 队伍人数>1 且 <=20
>    2. 队伍标题 <=20
>    3. 描述 <=512
>    4. type 是否为公开(int) 不传默认为0（公开）
>    5. 如果 type 是加密，一定要有密码，且长度 <=32
>    6. 超时时间 > 当前时间
>    7. 校验用户最多创建 5 个队伍
> 4. 插入队伍信息到队伍表
> 5. 插入 用户-队伍 信息 到关系表
>
> 注意：
>
> - 校验type，创建enum类
>
> - 4、5、要保证数据一致性，所以添加事务
> - 测试事务是否生效

为什么需要请求参数包装类？

1. 请求参数名称 / 类型和实体类不一样
2. 有一些参数用不到，如果要自动生成接口文档，会增加理解成本
3. 有些字段不能返回前端，需要隐藏
4. 或者有些字段，某些方法是不关心的

测试添加队伍

expireTime

> console.log(JSON.stringify(new Date()))

#### 修改队伍信息

1. 请求参数是否为空
2. 队伍是否存在
3. 只有管理员，或者队长可以修改
4. 如果用户传入的新值和老值一致，那么就不用update，减少数据库使用次数
5. 如果队伍状态改为加密，必须要有密码

#### 查询队伍列表

1. 请求参数中取出队伍名称等，作为查询条件
2. 不展示已经过期的队伍
3. 只有admin才能查看加密还有非公开的房间
4. 关联查询已加入队伍的用户信息

#### 用户加入队伍

1. 最多加入 5 个
2. 队伍必须存在，只能加入未满的，未过期的队伍
3. 不能加入自己的队伍，不能重复加入已加入的队伍
4. 不能加入私有的队伍
5. 如果队伍是加密的，必须密码匹配
6. 新增 队伍 - 用户 关联信息

#### 获取用户已加入的队伍

请求参数：当前登录用户id

查询 队伍-用户 关系表

#### 用户可以退出队伍

请求参数： 队伍 id

1.  校验请求参数
2. 队伍是否存在
3. 我是否已加入该队伍
4. 如果队伍
   - 只剩一人，队伍解散（删除team_user记录，和team记录）
   - 还有其他人
     - 如果自己是队长，那么设置第二早加入队伍的用户为队长（根据加入时间，或者id来判断）
     - 如果自己不是队长，就自己退出队伍

#### 队长解散队伍

请求参数：队伍id

1.  校验请求参数
2.  队伍是否存在
3. 我是否是该队伍的队长
4. 删除所有 用户-队伍 关联信息
5. 删除队伍

## 随机匹配相似的用户

### 推荐算法：

标签推荐算法：

> 编辑距离算法：
>
> - 最小编辑距离：字符串 1 需要通过最少多少次增删改才能变成 字符串 2

余弦相似度算法：

> 带权重计算，

### 代码实现：

比如针对id为1的用户

1. 查出所有用户列表 select （id , tags） where tags is not null 加快查询效率
2. 遍历用户列表，
   1. 获取用户标签json字符串，使用Gson转为标签列表
   2. 使用编辑距离算法计算两个标签列表的最小编辑距离
   3. 使用zset，保存结果，最小编辑距离作为score，用户id作为value
3. 存入redis

进行匹配时，先查询redis

## 上线

nginx:配置文件

```
location / {
            # 用于配合 browserHistory使用
            try_files $uri $uri/index.html /index.html;
      }
```

