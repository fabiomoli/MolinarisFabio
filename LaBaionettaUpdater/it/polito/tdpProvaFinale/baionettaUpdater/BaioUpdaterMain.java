package it.polito.tdpProvaFinale.baionettaUpdater;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import it.polito.tdpProvaFinale.BaionettaUpdater.model.Model;

public class BaioUpdaterMain {

	public static void main(String[] args) {

		Model model = new Model();


		Timer timer = new Timer ();
		TimerTask hourlyTask = new TimerTask () {
		    @Override
		    public void run () {
				try {
					model.update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		};
		// schedule the task to run starting now and then every hour...
		timer.schedule (hourlyTask, 0l, 1000*60*60);

	}

}
