package com.warn.service;

import com.warn.dto.Count;
import com.warn.dto.SysSetDTO;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Menu;
import com.warn.entity.Role;
import com.warn.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */
public interface SetService {

//    void smsSysSwitch(Integer openSys);
//
//    void smsSwitch();

    SysSetDTO getSysAllSet();

//    void setSendSMSTime(int time);

    void setAccessBatabaseTime(int time);

    void timerOpen(Integer timerOpen);

    void setDown(int time, String name);
}
