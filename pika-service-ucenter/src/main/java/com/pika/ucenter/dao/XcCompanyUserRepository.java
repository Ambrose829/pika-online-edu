package com.pika.ucenter.dao;

import com.pika.framework.domain.ucenter.PikaCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface XcCompanyUserRepository extends JpaRepository<PikaCompanyUser, String> {
    PikaCompanyUser findXcCompanyUserByUserId(String userId);
}
