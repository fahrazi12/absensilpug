package com.ivallavifahrazi.lpugabsensi;

import com.ivallavifahrazi.lpugabsensi.scheduler.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class LpugabsensiApplication {

//	@Autowired ScheduledTask scheduledTask;
//	@PostConstruct
//	public void runSchedule(){
//		scheduledTask.checkMail();
//	}
	public static void main(String[] args) {
		SpringApplication.run(LpugabsensiApplication.class, args);
	}

}
