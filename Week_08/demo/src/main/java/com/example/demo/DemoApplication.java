package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.XaDemoService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

        UserService userService = ctx.getBean(UserService.class);

        User u = User.build();
        userService.save(u);
        User getU = userService.getById(u.getId());
        log.info("username is equal: {}", u.getUsername().equals(getU.getUsername()));
        log.info("user in db: {}", getU);

        u.setEmail("test@t.t");
        userService.updateById(u);

        runXaDemo(ctx);
    }

    public static void runXaDemo(ConfigurableApplicationContext ctx) {

        XaDemoService service = ctx.getBean(XaDemoService.class);
        List<User> list = service.addTenUser();
        log.info("xa user list: {}", list);

        try {
            list = new ArrayList<>();
            service.addTenUserWithError(list);
        } catch (Exception e) {
            log.warn("xa rollback", e);
        }

        UserService userService = ctx.getBean(UserService.class);
        User u = userService.getById(list.get(0).getId());
        log.info("user must be null: {}", u);
    }

}
