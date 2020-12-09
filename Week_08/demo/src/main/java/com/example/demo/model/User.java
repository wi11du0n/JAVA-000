package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.service.UserService;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@TableName("user")
@Data
public class User {

    @TableId(value = "id")
    private Long id;

    private String username;

    private String nickName;

    private String mobile;

    private String email;

    private String password;

    private String salt;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    public static User build() {
        User u = new User();
        String uuid = UUID.randomUUID().toString();
        u.setUsername(uuid);
        u.setNickName(uuid);
        u.setMobile(uuid);
        u.setEmail(uuid);
        u.setPassword(uuid);
        u.setSalt(uuid);
        return u;

    }
}
