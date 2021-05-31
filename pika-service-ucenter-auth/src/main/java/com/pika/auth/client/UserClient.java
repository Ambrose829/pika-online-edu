package com.pika.auth.client;


import com.pika.framework.client.PikaServiceList;
import com.pika.framework.domain.ucenter.ext.PikaUserExt;
import com.pika.framework.domain.ucenter.request.LoginRequest;
import com.pika.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(PikaServiceList.PIKA_SERVICE_UCENTER)
public interface UserClient {

    /**
     * 根据用户名获得用户信息和用户扩展信息
     * @param username
     * @return
     */
    @GetMapping("/ucenter/getuserext/{username}")
    public PikaUserExt getUserext(@PathVariable("username") String username);


    /**
     * 用户注册
     * @param loginRequest
     * @return
     */
    @PostMapping("/ucenter/register")
    public ResponseResult register(@RequestBody LoginRequest loginRequest);

}
