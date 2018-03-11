package it.polito.tdpProvaFinale.baionettaUpdater;

import java.io.IOException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.polito.tdpProvaFinale.BaionettaUpdater.model.Model;

public class HourlyJob implements org.quartz.Job {

    public HourlyJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	Model model = new Model();
    	try {
			model.update();
		} catch (IOException e) {
			System.err.println("Error executing hourly jobs");
		}
    }
}
