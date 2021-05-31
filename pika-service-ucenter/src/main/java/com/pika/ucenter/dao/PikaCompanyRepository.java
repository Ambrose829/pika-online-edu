package com.pika.ucenter.dao;

import com.pika.framework.domain.ucenter.PikaCompany;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PikaCompanyRepository extends JpaRepository<PikaCompany, String> {
}
