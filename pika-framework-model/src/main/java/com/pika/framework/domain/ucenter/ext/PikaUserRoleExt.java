package com.pika.framework.domain.ucenter.ext;

import com.pika.framework.domain.ucenter.XcUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Pika
 * @description
 */
@Data
@ToString
public class PikaUserRoleExt extends XcUser {
    List<String> roleIdList;
}
