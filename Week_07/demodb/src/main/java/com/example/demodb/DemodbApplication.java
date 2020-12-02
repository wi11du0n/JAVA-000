package com.example.demodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootApplication
public class DemodbApplication {

    public static final int COUNT = 1000_000;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemodbApplication.class, args);
    }

    @Bean
    public void test() {
        saveData();
    }


    private void saveData() {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= COUNT; i++) {
            jdbcTemplate.execute(
                    "INSERT INTO `jmall`.`user` (`username`, `nike_name`, `mobile`, `email`, `password`, `salt`) " +
                            "VALUES ("+ i + ", '1', '1', '1', '1', '1');");
        }
        long endTime = System.currentTimeMillis();
        log.info("jdbc template 单条执行耗时: {}", (endTime - startTime) / 1000);
    }
}
