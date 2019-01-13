package com.warn.service.impl;

import com.warn.dao.ThresholdDao;
import com.warn.dao.WarnHistoryDao;
import com.warn.dto.DwrData;
import com.warn.dto.PageHelper;
import com.warn.entity.WarnData;
import com.warn.service.WarnHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by admin on 2017/4/27.
 */
@Service
public class WarnHistoryServiceImpl implements WarnHistoryService {

    @Autowired
    WarnHistoryDao warnHistoryDao;
    @Autowired
    ThresholdDao thresholdDao;

    @Override
    public void addWarnHistory(DwrData dwrData) {
        WarnData warnData=new WarnData();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateNowStr = sdf.format(d);
        warnData.setTimeW(dateNowStr);

        switch (dwrData.getType()){
            case "urgency":
                warnData.setTypeW("紧急报警");
                warnData.setOid(dwrData.getUrgency().getOldMan().getOid());
                warnData.setOldName(dwrData.getUrgency().getOldMan().getOldName());
                //@区分大标题 %区分小标题
                String data="老人信息：" +
                        "%老人ID："+dwrData.getUrgency().getOldMan().getOid()+
                        "%老人姓名："+ dwrData.getUrgency().getOldMan().getOldName()+
                        "%老人电话："+ dwrData.getUrgency().getOldMan().getOldPhone()+
                        "%老人住址："+ dwrData.getUrgency().getOldMan().getOldAddress()+
                        "@报警设备信息：" +
                        "%设备ID："+dwrData.getUrgency().getEquip().getEid()+
                        "%设备所在房间："+dwrData.getUrgency().getRoom().getRoomName();
                warnData.setDataW(data);
                warnData.setReadW("否");
                break;
            case "warn_move":
                warnData.setTypeW("行为预警");
                warnData.setOid(dwrData.getWarn().getOldMan().getOid());
                warnData.setOldName(dwrData.getWarn().getOldMan().getOldName());
                String data_warn="老人信息：" +
                        "%老人ID：" + dwrData.getWarn().getOldMan().getOid() +
                        "%老人姓名：" + dwrData.getWarn().getOldMan().getOldName() +
                        "%老人电话：" + dwrData.getWarn().getOldMan().getOldPhone() +
                        "%老人住址：" + dwrData.getWarn().getOldMan().getOldAddress() +
                        "@行为信息：" +
                        "%预警级别：" + dwrData.getWarn().getWarnLevel() +
                        "%已经不动：" + dwrData.getWarn().getNoMoveTime() + " 分钟" +
                        "%所处房间：" + dwrData.getWarn().getRoom().getRoomName() +
                        "%最初不动的时间：" + dwrData.getWarn().getTime() +
                        "%是否在该房间的生活规律模型中：" + (dwrData.getWarn().getInTime().equals("true") ? "在%模型所在时间段：" + dwrData.getWarn().getTimes() +
                        "%规律类型：" + (dwrData.getWarn().getFlag().equals("a") ?"活动" : ((dwrData.getWarn().getFlag().equals("r")) ? "休息" : "活动、休息"))  :
                        "不在");
                warnData.setDataW(data_warn);
                warnData.setReadW("否");
                break;
            case "warn_position":
                warnData.setTypeW("行为预警");
                warnData.setOid(dwrData.getWarn().getOldMan().getOid());
                warnData.setOldName(dwrData.getWarn().getOldMan().getOldName());
                String data_position="老人信息：" +
                        "%老人ID：" + dwrData.getWarn().getOldMan().getOid() +
                        "%老人姓名：" + dwrData.getWarn().getOldMan().getOldName() +
                        "%老人电话：" + dwrData.getWarn().getOldMan().getOldPhone() +
                        "%老人住址：" + dwrData.getWarn().getOldMan().getOldAddress() +
                        "@行为信息：" +
                        "%预警级别：" + dwrData.getWarn().getWarnLevel() +
                        "%已经不动：" + dwrData.getWarn().getNoMoveTime() + " 分钟" +
                        "%所处房间：" + dwrData.getWarn().getRoom().getRoomName() +"-"+dwrData.getWarn().getPositon()+
                        "%最初不动的时间：" + dwrData.getWarn().getTime() +
                        "%是否在该房间的生活规律模型中：" + (dwrData.getWarn().getInTime().equals("true") ? "在%模型所在时间段：" + dwrData.getWarn().getTimes() +
                        "%规律类型：" + (dwrData.getWarn().getFlag().equals("a") ?"活动" : ((dwrData.getWarn().getFlag().equals("r")) ? "休息" : "活动、休息"))  :
                        "不在");
                warnData.setDataW(data_position);
                warnData.setReadW("否");
                break;
            case "warn_wendu":
                warnData.setTypeW("温度预警");
                warnData.setOid(dwrData.getWarn_wendu().getOldMan().getOid());
                warnData.setOldName(dwrData.getWarn_wendu().getOldMan().getOldName());
                String data_wendu="老人信息：" +
                        "%老人ID：" + dwrData.getWarn_wendu().getOldMan().getOid()+ "" +
                        "%老人姓名：" + dwrData.getWarn_wendu().getOldMan().getOldName() +
                        "%老人电话：" + dwrData.getWarn_wendu().getOldMan().getOldPhone() +
                        "%老人住址：" + dwrData.getWarn_wendu().getOldMan().getOldAddress() +
                        "@温度信息：" +
                        "%报警房间：" + dwrData.getWarn_wendu().getThreshold_wendu().getRoom().getRoomName() +
                        "%当前温度：" + dwrData.getWarn_wendu().getWendu() +
                        "%该房间温度阈值：" + dwrData.getWarn_wendu().getThreshold_wendu().getwThreshold() ;
                warnData.setDataW(data_wendu);
                warnData.setReadW("否");
                break;
            case "warn_light":
                warnData.setTypeW("光强预警");
                warnData.setOid(dwrData.getWarn_light().getOldMan().getOid());
                warnData.setOldName(dwrData.getWarn_light().getOldMan().getOldName());
                String data_light="老人信息：" +
                        "%老人ID：" + dwrData.getWarn_light().getOldMan().getOid()+ "" +
                        "%老人姓名：" + dwrData.getWarn_light().getOldMan().getOldName() +
                        "%老人电话：" + dwrData.getWarn_light().getOldMan().getOldPhone() +
                        "%老人住址：" + dwrData.getWarn_light().getOldMan().getOldAddress() +
                        "@光强信息：" +
                        "%报警房间：" + dwrData.getWarn_light().getThreshold_light().getRoom().getRoomName() +
                        "%当前光强：" + dwrData.getWarn_light().getLight() +
                        "%起止时间：" + dwrData.getWarn_light().getTime() +
                        "%当前持续时间：" + dwrData.getWarn_light().getValue() +
                        "分钟%该房间光强阈值：" + dwrData.getWarn_light().getThreshold_light().getlThreshold() +
                        "%检测时间段：" + dwrData.getWarn_light().getThreshold_light().getTimes()+
                        "%持续超过：" + dwrData.getWarn_light().getThreshold_light().getContinueTime()+" 分钟报警";
                warnData.setDataW(data_light);
                warnData.setReadW("否");
                break;
            case "outdoor_nocome":
                warnData.setTypeW("未归预警");
                warnData.setOid(dwrData.getOutdoor().getOldMan().getOid());
                warnData.setOldName(dwrData.getOutdoor().getOldMan().getOldName());
                String data_out="老人信息：" +
                        "%老人ID：" + dwrData.getOutdoor().getOldMan().getOid()+ "" +
                        "%老人姓名：" + dwrData.getOutdoor().getOldMan().getOldName() +
                        "%老人电话：" + dwrData.getOutdoor().getOldMan().getOldPhone() +
                        "%老人住址：" + dwrData.getOutdoor().getOldMan().getOldAddress() +
                        "@未归信息：" +
                        "%出门时间："+dwrData.getOutdoor().getOut()+
                        "%出门未归阈值："+dwrData.getOutdoor().getThreshold_out().getNoComeThreshold()+"分钟";
                warnData.setDataW(data_out);
                warnData.setReadW("否");
        }
        warnHistoryDao.addWarnHistory(warnData);
        dwrData.setId(warnData.getWdid());
    }


    @Override
    public long getNoReadSum() {
        return warnHistoryDao.getNoReadSum();
    }

    @Override
    public void messageRead(Integer wdid) {
        warnHistoryDao.messageRead(wdid);
    }


    @Override
    public List<WarnData> datagridWarnData(PageHelper page, WarnData warnData) {
        page.setStart((page.getPage() - 1) * page.getRows());
        page.setSort("wdid");
        page.setOrder("desc");
        return warnHistoryDao.datagridWarnData(page,warnData);
    }

    @Override
    public Long getDatagridTotal(WarnData warnData) {
        return warnHistoryDao.getDatagridTotal(warnData);
    }

    @Override
    public String getMessageByWdid(Integer wdid) {
        return warnHistoryDao.getMessageByWdid(wdid);
    }

    @Override
    public void urgencyRead(Integer wdid) {
        warnHistoryDao.messageRead(wdid);
    }
}
