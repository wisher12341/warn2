

import com.warn.sms.SMSParam;
import com.warn.sms.SMSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/spring-*.xml"})
public class SMSUtilTest {

    @Autowired
    private SMSUtil smsUtil;


    @Test
    public void sendMsg() throws Exception {
        SMSParam smsParam=new SMSParam();
        smsParam.setOldMan("oldman");
        smsParam.setOldName("name");
        smsParam.setTime("123");
        smsParam.setWarnType("type");
        smsUtil.sendMsg("18817836286",smsParam);
    }

}
