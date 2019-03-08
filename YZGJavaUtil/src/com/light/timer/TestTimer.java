package com.light.timer;

import java.util.Date;
import java.util.TimerTask;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

public class TestTimer {
	public static void main(String[] args) {
//		Timer timer = new Timer();
//		timer.schedule(new MyTimer("111111"), 1000, 1000);
//		timer.schedule(new MyTimer("222222"), 2000, 2000);
//		
//		System.out.println("main thread");
		
//		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
//		service.scheduleAtFixedRate(new MyScheduledExecutor("aaaaa"), 1, 1, TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(new MyScheduledExecutor("bbbbb"), 2, 2, TimeUnit.SECONDS);
//		System.out.println("main thread");
		
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.getJobDataMap().put("name", "quartz job");
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}

class MyTimer extends TimerTask{
	
	private String timerName = "default";
	public MyTimer() {
		// TODO Auto-generated constructor stub
	}
	public MyTimer(String name) {
		// TODO Auto-generated constructor stub
		this.timerName = name;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(timerName);
	}
}

class MyScheduledExecutor implements Runnable{
	
	private String name = "executor default";
	
	public MyScheduledExecutor() {
		// TODO Auto-generated constructor stub
	}
	
	public MyScheduledExecutor(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(name);
	}
	
}


class MyQuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println(arg0.getJobDetail().getJobDataMap().get("name"));
		System.out.println(new Date().toString());
	}
	
}


