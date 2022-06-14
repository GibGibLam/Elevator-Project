package com.fdmgroup.elevator_project;

import static org.junit.Assert.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fdmgroup.elevator_project.properties.Properties;
import com.fdmgroup.elevator_project.validator.ElevatorValidator;


public class ElevatorValidatorTest 
{
	 ElevatorValidator elevatorValidator;
	 Properties properties;
    
    @BeforeEach
    public void init() {
    	properties = new Properties();
    	elevatorValidator = new ElevatorValidator();
    }
    
	
	@Test
    public void test_IfIsNumericReturnsFalse_IfNullIsPassed(){ 
        assertFalse(elevatorValidator.isNumeric(null));
    }
	
	@Test
    public void test_IfIsNumericReturnsFalse_IfNonNumericIsPassed(){ 
        assertFalse(elevatorValidator.isNumeric("noob"));
    }
	
	@Test
    public void test_IfIsNumericReturnsTrue_IfNumericIsPassed(){ 
        assertTrue(elevatorValidator.isNumeric("3"));
    }
	
	@Test
    public void test_IfcheckStartFloorReturnsFalse_IfNullArePassed(){ 
        assertFalse(elevatorValidator.checkStartFloor(null));
    }
	
	@Test
    public void test_IfcheckStartFloorReturnsFalse_IfNonNumericArePassed(){ 
        assertFalse(elevatorValidator.checkStartFloor("Two"));
    }
	
	@Test
    public void test_IfcheckStartFloorReturnsTrue_IfNumericArePassed(){ 
        assertTrue(elevatorValidator.checkStartFloor("2"));
    }
	
	@Test
    public void test_IfcheckEndFloorReturnsFalse_IfNullArePassed(){ 
        assertFalse(elevatorValidator.checkEndFloor(null));
    }
	
	@Test
    public void test_IfcheckEndFloorReturnsFalse_IfNonNumericArePassed(){ 
        assertFalse(elevatorValidator.checkEndFloor("Two"));
    }
	
	@Test
    public void test_IfcheckEndFloorReturnsTrue_IfNumericArePassed(){ 
        assertTrue(elevatorValidator.checkEndFloor("2"));
    }
	
	@Test
    public void test_IfcheckMidDelimiterReturnsFalse_IfZerosArePassed(){ 
        assertFalse(elevatorValidator.checkMidDelimiter("0"));
    }
	
	@Test
    public void test_IfcheckMidDelimiterReturnsTrue_IfColonArePassed(){ 
        assertTrue(elevatorValidator.checkMidDelimiter(":"));
    }
	
	@Test
    public void test_IfcheckSideDelimiterReturnsFalse_IfZerosArePassed(){ 
        assertFalse(elevatorValidator.checkSideDelimiter("0"));
    }
	
	@Test
    public void test_IfcheckSideDelimiterReturnsTrue_IfCommaArePassed(){ 
        assertTrue(elevatorValidator.checkSideDelimiter(","));
    }
}
