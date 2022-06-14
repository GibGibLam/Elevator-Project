package com.fdmgroup.elevator_project.readfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fdmgroup.elevator_project.logger.ElevatorLogger;

public class FileReaderCommand implements IFileReaderCommand{
	
	@Override
	public Map<String, String> readFile() {
		Map<String, String> elevatorProperties = new HashMap<>();
		List<String> lines = new ArrayList<>();
		String fileName = "src/main/resources/elevator.properties";
		try {
			Reader reader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = bufferedReader.readLine();
			while(line != null) {
				
				lines.add(line);
				line = bufferedReader.readLine();
			}
		
			for (String s: lines) {
				String[] arr = s.split(" ");
				elevatorProperties.put(arr[0], arr[1]);
			}
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			ElevatorLogger.logger.error(e.getMessage());
			
		}
		return elevatorProperties;
	}
}
