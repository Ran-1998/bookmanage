package com.book.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.book.quartz.BorrowQuartz;

/**
 * @author Ran
 * @date 2020年4月3日
 */
@Configuration
public class QuartConfig {
	
	  @Bean public JobDetail borrowJobDetail() { //指定job 
		  return JobBuilder.newJob(BorrowQuartz.class).withIdentity("borrowQuartz").storeDurably().build(); }
	  
	  @Bean
	  public Trigger orderTrigger() {
		  
		  CronScheduleBuilder scheduleBuilder=CronScheduleBuilder.cronSchedule("0 0/30 * * * ?");
			return TriggerBuilder
					.newTrigger()
					.forJob(borrowJobDetail())
					.withIdentity("borrowQuartz")	//2.任务
					.withSchedule(scheduleBuilder).build();
	  }
	 
	
	
}
