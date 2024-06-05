package main;

import managerClasses.HandleManagers;

public class Main {

	public static void main(String[] args) {
		
		HandleManagers.clearData();
		
		TestScenario.kontrolnaTacka2();
				
		HandleManagers.writeData();
	}

}
