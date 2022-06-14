package com.fdmgroup.elevator_project.elevator;

public class ElevatorRequest {
	
	private int startFloor;
	private int endFloor;
	
	public ElevatorRequest(int startFloor, int endFloor) {
		this.startFloor = startFloor;
		this.endFloor = endFloor;
	}

	public int getStartFloor() {
		return startFloor;
	}

	public int getEndFloor() {
		return endFloor;
	}

	@Override
	public String toString() {
		return "ElevatorRequest [startFloor=" + startFloor + ", endFloor=" + endFloor + "]";
	}

}
