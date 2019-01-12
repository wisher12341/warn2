package com.warn.dwr;

import com.warn.controller.SystemController;
import com.warn.dto.DwrData;
import com.warn.dto.Urgency;
import com.warn.dto.Warn;
import com.warn.entity.HouseMarker;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.directwebremoting.Browser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/4/4.
 */
@Component
public class Remote {


//    public static Map<String,String> user=new HashMap<String,String>();

    //实现 筛选 发送推送
//    public void getData(String userCode) {
//        noticeNewOrder("123",userCode);
//    }

    // 得到推送信息并推送  预警推送
    public static void noticeNewOrder(final DwrData dwrData) {

        Runnable run = new Runnable(){
            private ScriptBuffer script = new ScriptBuffer();
            public void run() {
                //设置要调用的 js及参数
                script.appendCall("warn",dwrData);
//                script.appendCall("warn" ,type, obj);
                //得到所有ScriptSession
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                //遍历每一个ScriptSession
                for (ScriptSession scriptSession : sessions){
                    scriptSession.addScript( script);
                }
            }
        };
        Browser. withAllSessions(run);

//        WebContext wctx = WebContextFactory.get();
//        //得到当前页面的session
//        ScriptSession scriptSession = wctx.getScriptSession();
//        //设置session属性值 用户code
//        scriptSession.setAttribute("usercode", userCode);
//        String currentPage = "/main";
//        ScriptBuffer script = new ScriptBuffer();
//        script.appendScript("InitMsgBox(").appendData(fileName)
//                .appendScript(");");
//        //得到登录此页面的scriptSession的集合
//        Collection<ScriptSession> pages = wctx.getScriptSessionsByPage(currentPage);
//        for (ScriptSession session: pages) {
//            if(session.getAttribute("usercode")!=null){
//                String usercode=(String)session.getAttribute("usercode");
//                System.out.println("sessionattri:"+usercode );
//                session.addScript(script);
//            }
//        }
    }

    //地图更新推送
    public static void noticeMap() {

        Runnable run = new Runnable(){
            private ScriptBuffer script = new ScriptBuffer();
            public void run() {
                //设置要调用的 js及参数
                script.appendCall("mapUpdate");
                //得到所有ScriptSession
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                //遍历每一个ScriptSession
                for (ScriptSession scriptSession : sessions){
                    scriptSession.addScript( script);
                }
            }
        };
        Browser. withAllSessions(run);

    }
}
