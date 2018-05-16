package it.polito.tdpProvaFinale.baionettaUpdater;

//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

//import it.polito.tdpProvaFinale.BaionettaUpdater.model.Model;

public class BaioUpdaterMain {

	public static void main(String[] args) {
		/*
		 * Model model = new Model();
		 *
		 * Timer timer = new Timer (); TimerTask hourlyTask = new TimerTask () {
		 *
		 * @Override public void run () { try { model.update(); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } } }; // schedule
		 * the task to run starting now and then every hour... timer.schedule
		 * (hourlyTask, 0l, 1000*60*60);
		 */
		// Grab the Scheduler instance from the Factory
		Scheduler scheduler = null;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			System.err.println("Error executing hourly jobs 01");
		}

		// define the job and tie it to our MyJob class
		JobDetail job = newJob(HourlyJob.class).withIdentity("job1", "group1").build();

		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(simpleSchedule().withIntervalInHours(1).repeatForever()).build();

		// Tell quartz to schedule the job using our trigger
		try {
			scheduler.scheduleJob(job, trigger);
			System.out.println("v2.1");
		} catch (SchedulerException e) {
			System.err.println("Error executing hourly jobs 02");
		}
	}

}
