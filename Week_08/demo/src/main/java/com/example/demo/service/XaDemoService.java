package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class XaDemoService {
    @Autowired
    private UserMapper userMapper;

    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public List<User> addTenUser() {
        List<User> list = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            User u = User.build();
            userMapper.insert(u);
            list.add(u);
        }
        return list;
    }

    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public void addTenUserWithError(List<User> list) {
        for (int i = 0; i < 10; i++) {
            User u = User.build();
            userMapper.insert(u);
            if (i == 9) {
                throw new RuntimeException("test xa transaction.");
            }
            list.add(u);
        }
    }
}
