package com.pika.manage_media.dao;

import com.pika.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
