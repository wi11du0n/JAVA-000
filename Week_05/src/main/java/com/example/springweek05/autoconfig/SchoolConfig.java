package com.example.springweek05.autoconfig;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "school.config")
public class SchoolConfig {
    private Integer studentId;
    private String  studentName;

}
