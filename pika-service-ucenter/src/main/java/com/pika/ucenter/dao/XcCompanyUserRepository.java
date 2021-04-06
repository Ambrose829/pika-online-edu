package com.pika.ucenter.dao;

import com.pika.framework.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser, String> {
    XcCompanyUser findXcCompanyUserByUserId(String userId);
}
