package com.example.springweek05;

import com.example.springweek05.autoconfig.School;
import com.example.springweek05.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringWeek05Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringWeek05Application.class, args);
        User u = ctx.getBean(User.class);
        System.out.println(u);

        School school = (School) ctx.getBean("mySchool");
        System.out.println(school);
    }

}
