package com.pika.framework.domain.ucenter.ext;

import com.pika.framework.domain.ucenter.XcMenu;
import com.pika.framework.domain.ucenter.XcUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class PikaUserExt extends XcUser {

    //权限信息
    private List<XcMenu> permissions;

    //企业信息
    private String companyId;
}
