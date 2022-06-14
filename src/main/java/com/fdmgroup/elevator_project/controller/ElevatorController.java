package com.fdmgroup.elevator_project.controller;

import java.util.List;
import java.util.Map;
import com.fdmgroup.elevator_project.elevator.ElevatorRequest;
import com.fdmgroup.elevator_project.logger.ElevatorView;
import com.fdmgroup.elevator_project.validator.ElevatorValidator;

public class ElevatorController {

	/**
	 * This method runs the process to validate all the delimiter and the floors.
	 * 
	 * @param input: String
	 * 
	 * @return boolean
	 */
	public boolean validateInput(String input) {
		InputLogic inputLogic = new InputLogic();
		ElevatorValidator validator = new ElevatorValidator();
		String[] delimiters = inputLogic.getDelimiter(input);
		int j = 0;
		// if validation returns false, increase count of j.
		for (int i = 0; i < delimiters.length; i++) {
			if (i % 2 == 0)
				if (!validator.checkMidDelimiter(delimiters[i]))
					j++;
				else if (i % 2 != 0)
					if (!validator.checkSideDelimiter(delimiters[i]))
						j++;
		}
		if (j > 0) {
			return false;
		} else {
			List<String> floors = inputLogic.getFloor(input);
			int k = 0;
			// if validation returns false, increase count of k
			for (int i = 0; i < floors.size(); i++) {
				if (i % 2 == 0) {
					// increase count of k if startFloor is equals to the endFloor or startFloor
					// fails validation.
					if (!validator.checkStartFloor(floors.get(i)) || floors.get(i).equals(floors.get(i + 1)))
						k++;
				} else if (i % 2 != 0)
					if (!validator.checkEndFloor(floors.get(i)))
						k++;
			}
			if (k > 0) {
				return false;
			} else
				return true;
		}
	}

	public ElevatorController() {
		ElevatorLogic elevatorLogic = new ElevatorLogic();
		elevatorLogic.initializeElevator();
	}

	/**
	 * This method serves to run the process for all the methods to select the
	 * elevator.
	 * 
	 * 
	 * @param input: String
	 */
	public void processInput(String input) {
		InputLogic inputLogic = new InputLogic();
		ElevatorLogic elevatorLogic = new ElevatorLogic();
		Map<Integer, ElevatorRequest> requestMap = inputLogic.generateRequestMap(input);
		for (ElevatorRequest e : requestMap.values()) {
			elevatorLogic.selectElevator(e);
		}
	}

}
