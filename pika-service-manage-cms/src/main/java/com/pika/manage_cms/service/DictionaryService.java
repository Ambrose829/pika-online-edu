package com.pika.manage_cms.service;

import com.pika.framework.domain.system.SysDictionary;
import com.pika.manage_cms.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pika
 * @create 2020/11/9
 * @description
 */
@Service
public class DictionaryService {
    @Autowired
    SysDicthinaryRepository sysDicthinaryRepository;
    /**
     *
     * @param type
     * @return
     */
    public SysDictionary getByType(String type) {
        if (type == null) {
            return null;
        }
        return sysDicthinaryRepository.findBydType(type);
    }
}
