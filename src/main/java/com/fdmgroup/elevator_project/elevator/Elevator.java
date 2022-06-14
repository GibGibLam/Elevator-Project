package com.fdmgroup.elevator_project.elevator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableSet;

import com.fdmgroup.elevator_project.controller.ElevatorLogic;
import com.fdmgroup.elevator_project.logger.ElevatorLogger;

public class Elevator implements Runnable {

	private int elevatorId;
	private int currentFloor;
	private ElevatorMode elevatorMode;
	private NavigableSet<Integer> stoppedFloors;
	private Map<ElevatorMode, NavigableSet<Integer>> stoppedFloorsMap;

	public Elevator(int elevatorId) {
		this.elevatorId = elevatorId;
		setElevatorMode(ElevatorMode.Stationary);
		setCurrentFloor(1);
		setStoppedFloorsMap(new LinkedHashMap<>());
	}

	public int getElevatorId() {
		return elevatorId;
	}

	public void setElevatorId(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public ElevatorMode getElevatorMode() {
		return elevatorMode;
	}

	public void setElevatorMode(ElevatorMode elevatorMode) {
		this.elevatorMode = elevatorMode;
	}

	public Map<ElevatorMode, NavigableSet<Integer>> getStoppedFloorsMap() {
		return stoppedFloorsMap;
	}

	public void setStoppedFloorsMap(Map<ElevatorMode, NavigableSet<Integer>> stoppedFloorsMap) {
		this.stoppedFloorsMap = stoppedFloorsMap;
	}

	@Override
	public String toString() {
		return "Elevator [elevatorId=" + elevatorId + ", currentFloor=" + currentFloor + ", elevatorMode="
				+ elevatorMode + "]";
	}

	/**
	 * This method serves to move the elevator.
	 * 
	 * @param elevator
	 */
	public void moveElevator() {
		synchronized (this) {
			ElevatorLogic elevatorLogic = new ElevatorLogic();
			for (ElevatorMode e : stoppedFloorsMap.keySet()) {
				stoppedFloors = stoppedFloorsMap.get(e);
				Integer currentFloor;
				Integer nextFloor;
				// Once the elevator starts moving, check for the next floor and remove current
				// floor from current stoppedFloors list.
				while (!stoppedFloors.isEmpty()) {
					if (e.equals(ElevatorMode.Ascend)) {
						currentFloor = stoppedFloors.pollFirst();
						nextFloor = stoppedFloors.higher(currentFloor);
					} else if (e.equals(ElevatorMode.Descend)) {
						currentFloor = stoppedFloors.pollLast();
						nextFloor = stoppedFloors.lower(currentFloor);
					} else {
						return;
					}
					setCurrentFloor(currentFloor);
					setElevatorMode(e);
					//Create intermediate floors if there is a next floor.
					if (nextFloor != null) {
						createFloorsInBetween(currentFloor, nextFloor);
					} else {
						setElevatorMode(ElevatorMode.Stationary);
						elevatorLogic.updateElevator(this);
					}
					ElevatorLogger.logger.info("Elevator:" + this.getElevatorId() + " | Status: " + this.getElevatorMode() + " | Floor: " + this.getCurrentFloor());
					try {
						Thread.sleep(100);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
						ElevatorLogger.logger.error(ie.getMessage());
					}
				}
			}
		}
	}

	/**
	 * This method generates the floors required for the elevator to traverse from
	 * its source to its intended destination
	 * 
	 * @param startFloor
	 * 
	 * @param endFloor
	 * 
	 */
	public synchronized void createFloorsInBetween(int startFloor, int endFloor) {
		if (startFloor == endFloor || Math.abs(startFloor - endFloor) == 1) {
			return;
		}
		int i = -1;
		if (endFloor - startFloor > 0) {
			i = 1;
		}
		while (startFloor != endFloor) {
			startFloor += i;
			if (!stoppedFloors.contains(startFloor)) {
				stoppedFloors.add(startFloor);
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					wait();
				}
				moveElevator();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				ElevatorLogger.logger.error(e.getMessage());
			}
		}
	}

}
