package com.pika.ucenter.dao;

import com.pika.framework.domain.ucenter.PikaCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PikaCompanyUserRepository extends JpaRepository<PikaCompanyUser,String> {
    //根据用户id查询该用户所属的公司id
    PikaCompanyUser findByUserId(String userId);
}
