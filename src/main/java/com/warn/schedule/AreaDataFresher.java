package com.warn.schedule;

import com.warn.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AreaDataFresher {
    @Autowired
    StatisticService statisticService;

    @Scheduled(cron = "0 0 8-20 * * ?")
    public void updateArea(){
        statisticService.getStatisticData(48);
        statisticService.getStatisticData(43);
    }


}
