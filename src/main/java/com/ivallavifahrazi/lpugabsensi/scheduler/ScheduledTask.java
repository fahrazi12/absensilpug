package com.ivallavifahrazi.lpugabsensi.scheduler;

import com.ivallavifahrazi.lpugabsensi.bean.CheckMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    CheckMail checkMail;
    @Scheduled(cron = "0 0 8 * * *")
    public void checkMail(){
        checkMail.checkerMail();
    }
}
