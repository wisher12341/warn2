package com.warn.service;

import com.warn.dto.PageHelper;
import com.warn.entity.Log;
import com.warn.entity.Serve;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
public interface ServeService {

    void beginServe(Serve serve);

    void endServe(Serve serve);
}
