package com.fdmgroup.elevator_project.validator;

import com.fdmgroup.elevator_project.logger.ElevatorLogger;
import com.fdmgroup.elevator_project.properties.Properties;

public class ElevatorValidator {

	private Properties properties;
	
	
	/**
	 * Checks if floor input is numeric
	 * @param floor:String
	 * @return boolean
	 */

	public boolean isNumeric(String floor) {
		if (floor == null) {
			ElevatorLogger.logger.error("No input for floor is found.");
			return false;
		}
		try {
			int i = Integer.parseInt(floor);
		} catch (NumberFormatException e) {
			ElevatorLogger.logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if start floor is within the range of floors and greater than 0.
	 * @param startFloor:String
	 * @return boolean
	 */

	public boolean checkStartFloor(String startFloor) {
		properties = new Properties();
		if (isNumeric(startFloor)) {
			int stFloor = Integer.parseInt(startFloor);
			if (stFloor > 0 && stFloor <= properties.getNumFloor())
				return true;
		}
		ElevatorLogger.logger.error("Invalid input");
		return false;
	}
	
	
	/**
	 * Checks if end floor is within the range of floors and greater than 0.
	 * @param endFloor: String
	 * @return boolean
	 */
	public boolean checkEndFloor(String endFloor) {
		properties = new Properties();
		if (isNumeric(endFloor)) {
			int edFloor = Integer.parseInt(endFloor);
			if (edFloor > 0 && edFloor <= properties.getNumFloor())
				return true;
		}
		ElevatorLogger.logger.error("Invalid input");
		return false;
	}
	/**
	 * Checks if the delimiter in src:dest is equal to that of the properties 
	 * @param midDelimiter: String
	 * @return boolean
	 */

	public boolean checkMidDelimiter(String midDelimiter) {
		properties = new Properties();
		if (midDelimiter.equals(properties.getMidDelimiter()))
			return true;
		ElevatorLogger.logger.error("Invalid input");
		return false;
	}
	
	/**
	 * Checks if the delimiter between requests is equal to that of the properties 
	 * @param sideDelimiter: String
	 * @return boolean
	 */
	
	public boolean checkSideDelimiter(String sideDelimiter) {
		properties = new Properties();
		if (sideDelimiter.equals(properties.getSideDelimiter())) {
			return true;
		}
		ElevatorLogger.logger.error("Invalid input");
		return false;
	}

}
