package com.fdmgroup.elevator_project.client;

import java.util.Scanner;

import com.fdmgroup.elevator_project.controller.ElevatorController;
import com.fdmgroup.elevator_project.gui.ElevatorFrameView;
import com.fdmgroup.elevator_project.logger.ElevatorLogger;
import com.fdmgroup.elevator_project.properties.Properties;
import com.fdmgroup.javadoc_demo.Calculator;
import com.fdmgroup.javadoc_demo.TraineeGradeCalculator;

/**
 * The {@link ElevatorApp} is an application that computes the movement of
 * elevators based on the user input, through multi-threading.
 * 
 * @author Gibson Lam, Ryan Low, Rachel Lee
 * @version 0.0.1-SNAPSHOT
 * @since 2021-07-08
 * 
 *
 */
public class ElevatorApp {
	public static void main(String[] args) {
		ElevatorController controller = new ElevatorController();
		ElevatorLogger.logger.info("Welcome to Gibson's elevator");
		Properties properties = new Properties();
		ElevatorFrameView view = new ElevatorFrameView(properties.getNumFloor());
		Scanner scanner = new Scanner(System.in);
		String input;
		while (true) {
			do {
				ElevatorLogger.logger.info("Enter your source and destination in the respective format: src:dest");
				input = scanner.nextLine();
			} while (!controller.validateInput(input));
			controller.processInput(input);

		}

	}

}
