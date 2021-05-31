package com.pika.ucenter.controller;

import com.pika.api.ucenter.UcenterControllerApi;
import com.pika.framework.domain.ucenter.ext.PikaUserExt;
import com.pika.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/getuserext/{username}")
    public PikaUserExt getUserext(@PathVariable("username") String username) {
        System.out.println(username);
        return userService.getUserExt(username);
    }
}
