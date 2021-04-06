package com.pika.ucenter.service;

import com.pika.framework.domain.ucenter.XcCompanyUser;
import com.pika.framework.domain.ucenter.XcUser;
import com.pika.framework.domain.ucenter.ext.XcUserExt;
import com.pika.ucenter.dao.PikaCompanyUserRepository;
import com.pika.ucenter.dao.PikaUserRepository;
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
    PikaCompanyUserRepository pikaCompanyUserRepository;

    //根据账号查询xcUser信息
    public XcUser findXcUserByUsername(String username){
        return pikaUserRepository.findByUsername(username);
    }

    //根据账号查询用户信息
    public XcUserExt getUserExt(String username){
        //根据账号查询xcUser信息
        XcUser xcUser = this.findXcUserByUsername(username);
        if(xcUser == null){
            return null;
        }
        //用户id
        String userId = xcUser.getId();
        //根据用户id查询用户所属公司id
        XcCompanyUser xcCompanyUser = pikaCompanyUserRepository.findByUserId(userId);
        //取到用户的公司id
        String companyId = null;
        if(xcCompanyUser!=null){
            companyId = xcCompanyUser.getCompanyId();
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);
        xcUserExt.setCompanyId(companyId);
        return xcUserExt;

    }

}
