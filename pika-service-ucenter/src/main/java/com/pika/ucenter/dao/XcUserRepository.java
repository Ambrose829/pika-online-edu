package com.pika.ucenter.dao;

import com.pika.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface XcUserRepository extends JpaRepository<XcUser, String> {

        XcUser findXcUserByUsername(String username);

}
