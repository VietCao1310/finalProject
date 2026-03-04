package UI;

import Entities.Employee;
import Entities.FullTimeEmployee;
import Entities.PartTimeEmployee;
import manager.EmployeeManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

    private EmployeeManager employeeManager;

    public EmployeeMenu(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("------ EMPLOYEE MANAGEMENT ------");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. View All Employees");
            System.out.println("6. Back");
            System.out.print("Choose: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    updateEmployee(sc);
                    break;
                case 3:
                    deleteEmployee(sc);
                    break;
                case 4:
                    searchEmployee(sc);
                    break;
                case 5:
                    viewAllEmployees();
                    break;
            }

            System.out.println();

        } while (choice != 6);
    }

    //ADD
    private void addEmployee(Scanner sc) {

        try {
            System.out.println("----------- ADD EMPLOYEE -----------");
            System.out.print("Employee ID: ");
            String id = sc.nextLine();
            System.out.print("Full Name: ");
            String name = sc.nextLine();
            System.out.print("Department: ");
            String dept = sc.nextLine();
            System.out.print("Job Title: ");
            String job = sc.nextLine();
            System.out.print("Type (Full-time / Part-time): ");
            String typeInput = sc.nextLine().trim().toLowerCase();
            System.out.print("Date of Joining (dd/MM/yyyy): ");
            String dateStr = sc.nextLine();
            java.time.LocalDate date =
                    java.time.LocalDate.parse(dateStr,
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("Basic Salary: ");
            double salary = Double.parseDouble(sc.nextLine());

            System.out.println();
            System.out.println("[1] Save   [2] Cancel");
            System.out.print("Choose: ");
            int confirm = Integer.parseInt(sc.nextLine());

            if (confirm != 1) {
                System.out.println("Operation cancelled.");
                return;
            }

            Employee emp;
            if (typeInput.equalsIgnoreCase("full-time")) {
                emp = new FullTimeEmployee(id, name, dept, job, date, salary);
            } else if (typeInput.equalsIgnoreCase("part-time")) {
                emp = new PartTimeEmployee(id, name, dept, job, date, salary);
            } else {
                System.out.println("Invalid employee type.");
                return;
            }

            employeeManager.addEmployee(emp);

            System.out.println();
            System.out.println("Employee added successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //UPDATE
    private void updateEmployee(Scanner sc) {

        try {
            System.out.println("----------- UPDATE EMPLOYEE -----------");
            System.out.print("Enter Employee ID to update: ");
            String id = sc.nextLine();
            Employee emp = employeeManager.findById(id);

            if (emp == null) {
                System.out.println("Employee not found.");
                return;
            }
            System.out.println();
            System.out.println("Current Information:");
            System.out.println("Name: " + emp.getFullName());
            System.out.println("Department: " + emp.getDepartment());
            System.out.println("Job Title: " + emp.getJobTitle());
            System.out.println("Basic Salary: " +
                    String.format("%,.0f", emp.getBasicSalary()));
            System.out.println();

            System.out.print("Enter new Department (leave blank to skip): ");
            String newDept = sc.nextLine();

            System.out.print("Enter new Job Title (leave blank to skip): ");
            String newJob = sc.nextLine();

            System.out.print("Enter new Basic Salary (leave blank to skip): ");
            String salaryInput = sc.nextLine();

            String finalDept = newDept.isEmpty() ? emp.getDepartment() : newDept;
            String finalJob = newJob.isEmpty() ? emp.getJobTitle() : newJob;
            double finalSalary = salaryInput.isEmpty()
                    ? emp.getBasicSalary()
                    : Double.parseDouble(salaryInput);

            System.out.println();
            System.out.println("[1] Update   [2] Cancel");
            System.out.print("Choose: ");
            int confirm = Integer.parseInt(sc.nextLine());

            if (confirm != 1) {
                System.out.println("Operation cancelled.");
                return;
            }

            employeeManager.updateEmployee(
                    id,
                    emp.getFullName(),
                    finalDept,
                    finalJob,
                    finalSalary
            );

            System.out.println();
            System.out.println("Employee updated successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DELETE 
    private void deleteEmployee(Scanner sc) {

        try {

            System.out.println("----------- DELETE EMPLOYEE -----------");

            System.out.print("Enter Employee ID to delete: ");
            String id = sc.nextLine();

            Employee emp = employeeManager.findById(id);

            if (emp == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println();
            System.out.println("Employee Information:");
            System.out.println("Name: " + emp.getFullName());
            System.out.println("Department: " + emp.getDepartment());
            System.out.println("Job Title: " + emp.getJobTitle());
            System.out.println("Basic Salary: " +
                    String.format("%,.0f", emp.getBasicSalary()));
            System.out.println("Status: " + (emp.isActive() ? "Active" : "Inactive"));
            System.out.println();

            if (!emp.isActive()) {
                System.out.println("Employee already inactive.");
                return;
            }

            System.out.println("[1] Delete   [2] Cancel");
            System.out.print("Choose: ");
            int confirm = Integer.parseInt(sc.nextLine());

            if (confirm != 1) {
                System.out.println("Operation cancelled.");
                return;
            }

            employeeManager.deactivateEmployee(id);

            System.out.println();
            System.out.println("Employee deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //SEARCH
    private void searchEmployee(Scanner sc) {

        int choice;

        do {
            System.out.println("------ SEARCH EMPLOYEE ------");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Name");
            System.out.println("3. Search by Department");
            System.out.println("4. Search by Job Title");
            System.out.println("5. Back");
            System.out.print("Choose: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    Employee emp = employeeManager.findById(id);

                    if (emp != null) {
                        printOne(emp);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    printList(employeeManager.searchByName(sc.nextLine()));
                    break;

                case 3:
                    System.out.print("Enter Department: ");
                    printList(employeeManager.searchByDepartment(sc.nextLine()));
                    break;

                case 4:
                    System.out.print("Enter Job Title: ");
                    printList(employeeManager.searchByJobTitle(sc.nextLine()));
                    break;
            }

            System.out.println();

        } while (choice != 5);
    }
  
    //VIEW ALL
    private void viewAllEmployees() {

        System.out.println("----------- EMPLOYEE LIST -----------");

        List<Employee> list = employeeManager.getAllEmployees();

        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                "ID", "Name", "Department", "Job Title", "Salary", "Status");

        System.out.println("--------------------------------------------------------------------------");

        for (Employee e : list) {

            System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                    e.getId(),
                    e.getFullName(),
                    e.getDepartment(),
                    e.getJobTitle(),
                    String.format("%,.0f", e.getBasicSalary()),
                    e.isActive() ? "Active" : "Inactive");
        }
    }
   
    //HELPER 
    private void printList(List<Employee> list) {

        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                "ID", "Name", "Department", "Job Title", "Salary", "Status");
        System.out.println("--------------------------------------------------------------------------");

        for (Employee e : list) {

            System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                    e.getId(),
                    e.getFullName(),
                    e.getDepartment(),
                    e.getJobTitle(),
                    String.format("%,.0f", e.getBasicSalary()),
                    e.isActive() ? "Active" : "Inactive");
        }
    }
    
    
    private void printOne(Employee e) {

        System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                "ID", "Name", "Department", "Job Title", "Salary", "Status");

        System.out.println("--------------------------------------------------------------------------");

        System.out.printf("%-8s %-20s %-15s %-20s %-12s %-10s%n",
                e.getId(),
                e.getFullName(),
                e.getDepartment(),
                e.getJobTitle(),
                String.format("%,.0f", e.getBasicSalary()),
                e.isActive() ? "Active" : "Inactive");
    }
}