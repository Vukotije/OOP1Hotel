package storage;

import java.util.HashMap;
import entitiesClasses.Employee;

public class EmployeeData {
	
    private HashMap<String, Employee> employeesMap;
    
    public EmployeeData() {
    	employeesMap = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employeesMap.put(employee.getId(), employee);
    }
    
	public Employee getEmployee(String id) {
		return employeesMap.get(id);
	}
	
	public HashMap<String, Employee> getEmployeesMap() {
		return employeesMap;
	}

	public void removeEmployee(String id) {
		employeesMap.remove(id);
	}
}