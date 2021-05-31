package com.pika.ucenter.service;

import com.pika.framework.domain.ucenter.PikaCompanyUser;
import com.pika.framework.domain.ucenter.XcUser;
import com.pika.framework.domain.ucenter.ext.PikaUserExt;
import com.pika.ucenter.dao.PikaCompanyUserRepository;
import com.pika.ucenter.dao.PikaUserRepository;
import com.pika.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class UserService {

    @Autowired
    PikaUserRepository pikaUserRepository;

    @Autowired
    XcUserRepository xcUserRepository;

    @Autowired
    PikaCompanyUserRepository pikaCompanyUserRepository;

    //根据账号查询xcUser信息
    public XcUser findXcUserByUsername(String username){
//        return pikaUserRepository.findByUsername(username);
        return  xcUserRepository.findXcUserByUsername(username);
    }

    //根据账号查询用户信息
    public PikaUserExt getUserExt(String username){
        //根据账号查询xcUser信息
        XcUser xcUser = this.findXcUserByUsername(username);
        if(xcUser == null){
            return null;
        }
        //用户id
        String userId = xcUser.getId();
        //根据用户id查询用户所属公司id
        PikaCompanyUser xcCompanyUser = pikaCompanyUserRepository.findByUserId(userId);
        //取到用户的公司id
        String companyId = null;
        if(xcCompanyUser!=null){
            companyId = xcCompanyUser.getCompanyId();
        }
        PikaUserExt xcUserExt = new PikaUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);
        xcUserExt.setCompanyId(companyId);
        return xcUserExt;

    }

}
