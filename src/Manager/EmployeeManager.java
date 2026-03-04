package manager;

import DAO.EmployeeDAO;
import Entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private List<Employee> employees;
    private EmployeeDAO employeeDAO;

    public EmployeeManager(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
        this.employees = employeeDAO.loadEmployees();
    }

    // CREATE - Add Employee (BR1: Unique ID)
    public void addEmployee(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }

        if (findById(employee.getId()) != null) {
            throw new IllegalArgumentException("Employee ID already exists.");
        }

        employees.add(employee);
        employeeDAO.saveEmployees(employees);
    }

    // SEARCH - Find by ID
    public Employee findById(String id) {

        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }

        return null;
    }

    // READ - Get All Employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    // UPDATE - Update Employee Info
    public void updateEmployee(String id,
                               String fullName,
                               String department,
                               String jobTitle,
                               double basicSalary) {

        Employee e = findById(id);

        if (e == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        if (!e.isActive()) {
            throw new IllegalStateException("Cannot update inactive employee.");
        }

        e.setFullName(fullName);
        e.setDepartment(department);
        e.setJobTitle(jobTitle);
        e.setBasicSalary(basicSalary);
        employeeDAO.saveEmployees(employees);
    }

    // DELETE
    public void deactivateEmployee(String id) {

        Employee e = findById(id);

        if (e == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        if (!e.isActive()) {
            throw new IllegalStateException("Employee already inactive.");
        }

        e.deactivate();

        employeeDAO.saveEmployees(employees);
    }

    // SEARCH BY NAME 
    public List<Employee> searchByName(String keyword) {

        List<Employee> result = new ArrayList<>();

        for (Employee e : employees) {
            if (e.getFullName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }

        return result;
    }

    // SEARCH BY DEPARTMENT
    public List<Employee> searchByDepartment(String department) {

        List<Employee> result = new ArrayList<>();

        for (Employee e : employees) {
            if (e.getDepartment().equalsIgnoreCase(department)) {
                result.add(e);
            }
        }

        return result;
    }

    // SEARCH BY JOB TITLE
    public List<Employee> searchByJobTitle(String jobTitle) {

        List<Employee> result = new ArrayList<>();

        for (Employee e : employees) {
            if (e.getJobTitle().equalsIgnoreCase(jobTitle)) {
                result.add(e);
            }
        }

        return result;
    }
}