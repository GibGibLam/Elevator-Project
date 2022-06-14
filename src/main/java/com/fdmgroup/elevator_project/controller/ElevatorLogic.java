package com.fdmgroup.elevator_project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.fdmgroup.elevator_project.elevator.Elevator;
import com.fdmgroup.elevator_project.elevator.ElevatorMode;
import com.fdmgroup.elevator_project.elevator.ElevatorRequest;
import com.fdmgroup.elevator_project.properties.Properties;

public class ElevatorLogic {

	private static List<Elevator> elevatorList = new ArrayList<>();
	// Key - Elevator_ID Value - Elevator
	private static Map<Integer, Elevator> AscendMap = new HashMap<>();
	private static Map<Integer, Elevator> DescendMap = new HashMap<>();

	/**
	 * This method initializes the elevators and starts a thread for each.
	 * 
	 */

	public void initializeElevator() {
		Properties properties = new Properties();
//		elevatorList = new ArrayList<>();
		for (int i = 0; i < properties.getNumElevator(); i++) {
			Elevator elevator = new Elevator(i);
			Thread thread = new Thread(elevator);
			thread.start();
			updateElevator(elevator);
			elevatorList.add(elevator);
		}
	}

	public List<Elevator> getElevatorList() {
		return elevatorList;
	}

	/**
	 * This method runs process of finding the direction of elevator needed as well
	 * as find the elevator that best suits the request
	 * 
	 * @param elevatorRequest
	 * 
	 * @return elevator
	 */

	public synchronized Elevator selectElevator(ElevatorRequest elevatorRequest) {

		Elevator elevator = null;
		ElevatorMode elevatorMode = findElevatorDirection(elevatorRequest);
		int startFloor = elevatorRequest.getStartFloor();
		int endFloor = elevatorRequest.getEndFloor();

		elevator = searchElevator(elevatorMode, startFloor, endFloor);
//		notifyAll();
		synchronized (elevator) {
			elevator.notify();
		}
		return elevator;

	}

	/**
	 * This method computes finds the elevator directions based on the start and end
	 * floors, before assigning them to an elevator mode.
	 * 
	 * @param ElevatorRequest : elevatorRequest
	 * 
	 * @return : ElevatorMode
	 */

	public ElevatorMode findElevatorDirection(ElevatorRequest elevatorRequest) {
		ElevatorMode elevatorMode = null;

		int startFloor = elevatorRequest.getStartFloor();
		int endFloor = elevatorRequest.getEndFloor();

		if (endFloor - startFloor > 0) {
			elevatorMode = ElevatorMode.Ascend;
		} else if (endFloor - startFloor < 0) {
			elevatorMode = ElevatorMode.Descend;
		} else if (endFloor == startFloor)
			elevatorMode = ElevatorMode.Stationary;

		return elevatorMode;
	}

	/**
	 * This method searches for the nearest elevator that best serves request
	 * 
	 * @param elevatorMode
	 * 
	 * @param startFloor
	 * 
	 * @param endFloor
	 * 
	 * @return elevator
	 * 
	 */

	public Elevator searchElevator(ElevatorMode elevatorMode, int startFloor, int endFloor) {

		Elevator elevator = null;
		// Key - Distance, Value - ElevatorId, TreeMap will sort based on the shortest
		// distance.
		TreeMap<Integer, Integer> nearestElevatorMap = new TreeMap<>();
		if (elevatorMode.equals(ElevatorMode.Ascend)) {
			for (Entry<Integer, Elevator> m : AscendMap.entrySet()) {
				Elevator elevatorTemp = m.getValue();
				int dist = startFloor - elevatorTemp.getCurrentFloor();
				if (elevatorTemp.getElevatorMode().equals(ElevatorMode.Ascend) && dist < 0) {
					continue;
				} else {
					nearestElevatorMap.put(Math.abs(dist), elevatorTemp.getElevatorId());
				}

				int selectedElvId = nearestElevatorMap.firstEntry().getValue();
				elevator = AscendMap.get(selectedElvId);

			}
		} else if (elevatorMode.equals(ElevatorMode.Descend)) {
			for (Entry<Integer, Elevator> m : DescendMap.entrySet()) {
				Elevator elevatorTemp = m.getValue();
				int dist = startFloor - elevatorTemp.getCurrentFloor();
				//
				if (elevatorTemp.getElevatorMode().equals(ElevatorMode.Descend) && dist < 0) {
					continue;
				} else {
					nearestElevatorMap.put(Math.abs(dist), elevatorTemp.getElevatorId());
				}

				int selectedElvId = nearestElevatorMap.firstEntry().getValue();
				elevator = DescendMap.get(selectedElvId);

			}
		}
        //Obtains the direction from elevator's current floor to the passenger's location.
		ElevatorRequest newRequest = new ElevatorRequest(elevator.getCurrentFloor(), startFloor);
		ElevatorMode elevatorDirection = findElevatorDirection(newRequest);
        // Add floors that elevator stopped by, excluding intermediate floors
		if (elevator.getCurrentFloor() != startFloor) {
			//This is to handle the request from elevator's current floor to the passenger's starting floor.
			NavigableSet<Integer> stoppedFloors = elevator.getStoppedFloorsMap().get(elevatorDirection);
			if (stoppedFloors == null) {
				stoppedFloors = new ConcurrentSkipListSet<>();
			}

			stoppedFloors.add(elevator.getCurrentFloor());
			stoppedFloors.add(startFloor);
			elevator.getStoppedFloorsMap().put(elevatorDirection, stoppedFloors);
		}
		//Handling the original passenger request
		NavigableSet<Integer> stoppedFloors2 = elevator.getStoppedFloorsMap().get(elevatorMode);
		if (stoppedFloors2 == null) {
			stoppedFloors2 = new ConcurrentSkipListSet<Integer>();
		}

		stoppedFloors2.add(startFloor);
		stoppedFloors2.add(endFloor);
		elevator.getStoppedFloorsMap().put(elevatorMode, stoppedFloors2);

		return elevator;
	}

	/**
	 * This method appends the elevators to their respective maps based on their elevator directions.
	 * 
	 * @param Elevator: elevator
	 */
	public void updateElevator(Elevator elevator) {
		if (elevator.getElevatorMode().equals(ElevatorMode.Ascend)) {
			AscendMap.put(elevator.getElevatorId(), elevator);
			DescendMap.remove(elevator.getElevatorId());
		} else if (elevator.getElevatorMode().equals(ElevatorMode.Descend)) {
			DescendMap.put(elevator.getElevatorId(), elevator);
			AscendMap.remove(elevator.getElevatorId());
		} else if (elevator.getElevatorMode().equals(ElevatorMode.Stationary)) {
			DescendMap.put(elevator.getElevatorId(), elevator);
			AscendMap.put(elevator.getElevatorId(), elevator);
		}
	}

}
