package com.fdmgroup.elevator_project.properties;

import java.util.Map;

import com.fdmgroup.elevator_project.readfile.FileReaderCommand;


public class Properties {

	private Map<String, String> elevatorProperties;
	private FileReaderCommand fileReader = new FileReaderCommand();

	public int getNumFloor() {
		elevatorProperties = fileReader.readFile();
		return Integer.parseInt(elevatorProperties.get("numFloor"));
	}

	public int getNumElevator() {
		elevatorProperties = fileReader.readFile();
		return Integer.parseInt(elevatorProperties.get("numElevator"));
	}

	public String getMidDelimiter() {
		elevatorProperties = fileReader.readFile();
		return elevatorProperties.get("midDelimiter");
	}

	public String getSideDelimiter() {
		elevatorProperties = fileReader.readFile();
		return elevatorProperties.get("sideDelimiter");
	}

}
