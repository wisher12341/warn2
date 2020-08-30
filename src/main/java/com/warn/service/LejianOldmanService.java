package com.warn.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * 乐健老人系统
 */
@Service
public class LejianOldmanService {

    /**
     * 报警
     * @param gatewayId 网关id
     * @param type 报警类型 1紧急报警
     * @param content 报警内容
     */
    public void alarm(String gatewayId,String type,String content){
        new RestTemplate().exchange("http://47.103.137.244:89/oldman/alarm?" +
                        "gatewayId="+gatewayId+"&type="+type+"&content="+content,
                HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()),String.class, new HashMap<>());
    }
}
