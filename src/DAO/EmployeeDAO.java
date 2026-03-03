package DAO;

import Entities.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class EmployeeDAO {
    private final String FILE_PATH = "employees.txt";
    
    public void saveEmployees(List<Employee> employees) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

                for (Employee e : employees) {
                    writer.write(
                            e.getId() + "," +
                            e.getFullName() + "," +
                            e.getDepartment() + "," +
                            e.getJobTitle() + "," +
                            e.getDateOfJoining() + "," +
                            e.getBasicSalary() + "," +
                            e.getClass().getSimpleName() + "," +
                            e.isActive()
                    );
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving employees: " + e.getMessage());
            }
        }
    
    public List<Employee> loadEmployees() {
        
        List<Employee> employees = new ArrayList<>();
        
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return employees;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            
            while((line = reader.readLine()) != null) {
                String[] word = line.split(",");
                
                String id = word[0];
                String name = word[1];
                String department = word[2];
                String jobTitle = word[3];
                LocalDate date = LocalDate.parse(word[4]);
                double salary = Double.parseDouble(word[5]);
                String type = word[6];
                boolean active = Boolean.parseBoolean(word[7]);
                
                Employee emp;
                
                if(type.equals("FullTimeEmployee")) {
                    emp = new FullTimeEmployee(id, name, department, jobTitle, date, salary);
                } else if(type.equals("PartTimeEmployee")) {
                    emp = new PartTimeEmployee(id, name, department, jobTitle, date, salary);
                } else {
                    throw new IllegalArgumentException("Unknown employee type: " + type);
                }
                
                if (!active) {
                    emp.deactivate();
                }
                employees.add(emp);
            }
        } catch (IOException e) {
            System.out.println("Error loading employees: "+e.getMessage());
        }
        return employees;
    }
    
}
