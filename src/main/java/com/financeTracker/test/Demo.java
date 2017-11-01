package com.financeTracker.test;

import com.financeTracker.threads.PlannedPaymentService;
import com.financeTracker.threads.TaskInitializer;

public class Demo {
	public static void main(String[] args) {
		//EmailSender.sendSimpleEmail("nikolovblagoy@gmail.com", "zdr", "bepce");
//		TaskInitializer ti = new TaskInitializer();
//        ti.setDaemon(true);
//        ti.start();
        
        PlannedPaymentService pps = new PlannedPaymentService();
        pps.run();
	}
}
