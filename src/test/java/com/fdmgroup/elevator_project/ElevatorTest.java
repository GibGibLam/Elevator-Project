package com.fdmgroup.elevator_project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fdmgroup.elevator_project.controller.ElevatorController;
import com.fdmgroup.elevator_project.controller.InputLogic;
import com.fdmgroup.elevator_project.elevator.ElevatorRequest;
import com.fdmgroup.elevator_project.properties.Properties;
import com.fdmgroup.elevator_project.readfile.FileReaderCommand;

class ElevatorTest {
	
	private ElevatorController controller;
	
	@BeforeEach
	public void init() {
		controller = new ElevatorController();
	}
	
	@Test
	public void test_ValidateInput_ReturnsFalseIfEmptyStringIsPassed() {
		assertFalse(controller.validateInput(""));
	}
	
	@Test
	public void test_ValidateInput_ReturnsFalseIfNonNumericStartFloorsArePassed() {
		assertFalse(controller.validateInput("Five:3"));
	}
	
	@Test
	public void test_ValidateInput_ReturnsFalseIfNonNumericEndFloorsArePassed() {
		assertFalse(controller.validateInput("5:Three"));
	}
	
	@Test
	public void test_ValidateInput_ReturnsFalseIfWrongMidDelimiterArePassed() {
		assertFalse(controller.validateInput("5,3"));
	}

	@Test
	public void test_ValidateInput_ReturnsFalseIfWrongSideDelimiterArePassed() {
		assertFalse(controller.validateInput("5:3-4:5"));
	}
	
	@Test
	public void test_ValidateInput_ReturnsFalseIfSameFloorsArePassed() {
		assertFalse(controller.validateInput("5:5"));
	}
	
	@Test
	public void test_ValidateInput_ReturnsTrueIfCorrectInput() {
		assertTrue(controller.validateInput("1:5, 2:10, 8:3"));
	}
	
	@Test
	public void test_getDelimiter_ReturnsStringArray() {
		InputLogic logic = new InputLogic();
		String[] arr = {":", ",", ":"};
		assertArrayEquals(arr,logic.getDelimiter("4:7, 2:10"));
	}
	
	@Test
	public void test_getFloor_ReturnsListOfString() {
		InputLogic logic = new InputLogic();
		List<String> list = new ArrayList<>();
		list.add("4");
		list.add("7");
		list.add("2");
		list.add("10");
		assertEquals(list,logic.getFloor("4:7, 2:10"));
	}
	
	@Test
	public void test_generateRequestMap_ReturnsMapOfIntegerandElevatorRequest() {
		InputLogic logic = new InputLogic();
		Map<Integer, ElevatorRequest> testMap = new HashMap<>();
		assertEquals(testMap.getClass() ,logic.generateRequestMap("4:7, 2:10").getClass());
	}
	
	@Test
	public void test_ifReadFile_ReturnsAMapOfString() {
		FileReaderCommand fileReader = new FileReaderCommand();
		Map<String, String> map = new HashMap<>();
		map.put("numFloor", "20");
		map.put("numElevator", "5");
		map.put("midDelimiter", ":");
		map.put("sideDelimiter", ",");
		
		assertEquals(map, fileReader.readFile());

	}
	
	@Test
	public void test_ifGetNumFloorIsCalled_ReturnsIntegerTwenty() {
		Properties properties = new Properties();
		assertEquals(20, properties.getNumFloor());
	}
	
	@Test
	public void test_ifGetNumElevatorIsCalled_ReturnsIntegerFive() {
		Properties properties = new Properties();
		assertEquals(5, properties.getNumElevator());
	}
	
	@Test
	public void test_ifGetMidDelimiterIsCalled_ReturnsStringColon() {
		Properties properties = new Properties();
		assertEquals(":", properties.getMidDelimiter());
	}
	
	@Test
	public void test_ifGetSideDelimiterIsCalled_ReturnsStringComma() {
		Properties properties = new Properties();
		assertEquals(",", properties.getSideDelimiter());
	}
	
	
	
	
	
	
}
