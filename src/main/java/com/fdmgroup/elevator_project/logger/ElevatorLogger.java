package com.fdmgroup.elevator_project.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fdmgroup.elevator_project.client.ElevatorApp;

public class ElevatorLogger {
	public static Logger logger = LogManager.getLogger(ElevatorApp.class);
	

}
