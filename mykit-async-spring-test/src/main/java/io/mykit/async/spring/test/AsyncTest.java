/**
 * Copyright 2018-2118 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.async.spring.test;

import io.mykit.async.spring.annotation.Async;
import io.mykit.async.spring.core.AsyncCallable;
import io.mykit.async.spring.core.AsyncFutureCallback;
import io.mykit.async.spring.template.AsyncTemplate;
import io.mykit.async.spring.test.entity.User;
import io.mykit.async.spring.test.service.TeacherService;
import io.mykit.async.spring.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liuyazhuang
 * @version 1.0.0
 * @date 2018/9/9 22:30
 * @description 异步调用测试入口
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class AsyncTest {

    private final static Logger logger = LoggerFactory.getLogger(AsyncTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 测试异步执行调用
     */
    @Test
    public void testAsyncAnnotation() {
        User user1 = userService.addUser(new User(34, "李一"));
        User user2 = userService.addUser(new User(35, "李二"));
        logger.info("异步任务已执行");
        logger.info("执行结果  任务1：{}  任务2：{}", user1.getName(), user2.getName());
    }

    /**
     * 测试获取名字
     */
    @Test
    public void testGetName(){
        String name = userService.getName();
        logger.info(name);
    }

    /**
     * 测试获取不同对象
     */
    @Test
    public void testGetUser(){
        User user = userService.getUser();
        logger.info("name===>>>" + user.getName() + ", age===>>>" + user.getAge());
    }

    /**
     * 测试本类另一个标注为@Async的方法
     */
    @Test
    public void testCreateUser(){
        User user = createUser();
        logger.info("name===>>>" + user.getName() + ", age===>>>" + user.getAge());
    }

    /**
     * 测试调用同步方法，同步方法内调用另一个异步方法
     */
    @Test
    public void testGetSyncUser(){
        User user = userService.getSyncUser();
        logger.info("name===>>>" + user.getName() + ", age===>>>" + user.getAge());
    }

    /**
     * 测试异步调用返回为void的方法
     */
    @Test
    public void testPrintUser(){
        logger.info("执行打印方法...");
        userService.printUser();
    }

    /**
     * 测试一个类中标注了@Async的方法调用另一个类中标注了@Async的方法
     */
    @Test
    public void testAddTeacherUser(){
        logger.info("开始调用...");
        userService.addTeacherUser(new User(20, "王五"));
        logger.info("结束调用...");

    }

    /**
     * 测试同时调用两个标注了@Async的方法
     */
    @Test
    public void testJoinUser(){
        logger.info("开始调用...");
        User user = userService.getUser();
        logger.info("调用异步获取用户信息的方法返回数据为===>>>name:{}, age:{}", user.getName(), user.getAge());
        userService.printUser();
        logger.info("结束调用");
    }

    @Async
    private User createUser(){
        logger.info("正在创建用户...");
        return new User(20, "张三");
    }

    /**
     * 测试编程式异步
     */
    @Test
    public void testAsyncSaveUser() {
        User user = new User();
        user.setName("张三");
        user.setAge(18);

        UserService service = AsyncTemplate.buildProxy(this.userService, 1000);
        service.addUser(user);
        logger.info("调用结束");
    }

    /**
     * 测试异步事件编程
     */
    @Test
    public void testAsyncTemplate() {

        AsyncTemplate.submit(new AsyncCallable<User>() {

            @Override
            public User doAsync() {
                return teacherService.addTeacher(new User(12, "李三"));
            }
        }, new AsyncFutureCallback<User>() {
            @Override
            public void onSuccess(User user) {
                logger.info("添加用户成功：{}", user.getName());
            }

            @Override
            public void onFailure(Throwable t) {
                logger.info("添加用户失败：{}", t);
            }
        });

        logger.info("调用结束");
    }
}
