package com.fdmgroup.elevator_project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fdmgroup.elevator_project.elevator.ElevatorRequest;
import com.fdmgroup.elevator_project.properties.Properties;
import com.fdmgroup.elevator_project.validator.ElevatorValidator;

public class InputLogic {
	Properties properties = new Properties();
	ElevatorValidator val = new ElevatorValidator();
	private Map<Integer, ElevatorRequest> requestMap = new HashMap<>();

	public static void main(String[] args) {

	}

	/**
	 * This method returns a string array of delimiters based on the user input.
	 * 
	 * @param input: String
	 * @return String[]
	 */
	public String[] getDelimiter(String input) {
		String[] strArray = input.replaceAll("[0-9a-zA-Z\\s]+", "").split("");
		return strArray;

	}

	/**
	 * This method obtains a list of floors based on the user input.
	 * 
	 * @param input: String
	 * @return : List<String>
	 */

	public List<String> getFloor(String input) {

		String[] strArray = input.replaceAll(" ", "").split(properties.getSideDelimiter());
		List<String> strList = new ArrayList<>();
		for (int i = 0; i < strArray.length; i++) {
			strList.add(strArray[i].split(properties.getMidDelimiter())[0]);
			strList.add(strArray[i].split(properties.getMidDelimiter())[1]);
		}
		return strList;

	}

	/**
	 * This method returns a request map for each src:dest pair, consisting of an
	 * assigned request id and details pertaining to it.
	 * 
	 * @param input: String
	 * @return : Map<Integer, ElevatorRequest
	 */

	public Map<Integer, ElevatorRequest> generateRequestMap(String input) {
		String[] strArray = input.replaceAll(" ", "").split(properties.getSideDelimiter());

		int j = 1;
		for (int i = 0; i < strArray.length; i++) {
			String[] strArray2 = strArray[i].split(properties.getMidDelimiter());
			ElevatorRequest request = new ElevatorRequest(Integer.parseInt(strArray2[0]),
					Integer.parseInt(strArray2[1]));
			requestMap.put(j, request);
			j++;
		}
		return requestMap;
	}

}
