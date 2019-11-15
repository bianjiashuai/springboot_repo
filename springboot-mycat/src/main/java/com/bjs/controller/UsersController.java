package com.bjs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjs.mapper.UsersMapper;
import com.bjs.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description
 * @Date 2019-11-09 14:31:51
 * @Author BianJiashuai
 */
@RestController
public class UsersController {

    @Autowired
    UsersMapper usersMapper;

    @PostMapping("users.add")
    public ResponseEntity add(@RequestBody Users users) {
        int row = usersMapper.insert(users);
        return ResponseEntity.ok(row);
    }

    @GetMapping("users.find.all.page/{page}/{size}")
    public ResponseEntity findAllPage(@PathVariable int page, @PathVariable int size) {
        IPage<Users> usersIPage = usersMapper.selectPage(new Page<>(page, size), null);
        return ResponseEntity.ok(usersIPage);
    }
}
