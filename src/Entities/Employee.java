package Entities;

import Enums.EmployeeStatus;
import java.time.LocalDate;

public abstract class Employee {

    protected final String id;
    protected String fullName;
    protected String department;
    protected String jobTitle;
    protected final LocalDate dateOfJoining;
    protected double basicSalary;
    protected EmployeeStatus status;

    // CONSTRUCTOR
    public Employee(String id,
                    String fullName,
                    String department,
                    String jobTitle,
                    LocalDate dateOfJoining,
                    double basicSalary) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }

        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

        if (dateOfJoining == null) {
            throw new IllegalArgumentException("Date of joining cannot be null.");
        }

        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary must be >= 0.");
        }

        this.id = id;
        this.fullName = fullName;
        this.department = department;
        this.jobTitle = jobTitle;
        this.dateOfJoining = dateOfJoining;
        this.basicSalary = basicSalary;
        this.status = EmployeeStatus.ACTIVE; // default
    }

    // ABSTRACT
    public abstract double calculateSalary(int overtimeHours, int absentDays);
    public abstract String getType();

    // GETTERS
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return department; }
    public String getJobTitle() { return jobTitle; }
    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public double getBasicSalary() { return basicSalary; }
    public EmployeeStatus getStatus() { return status; }

    // CHECK ACTIVE
    public boolean isActive() {
        return status == EmployeeStatus.ACTIVE;
    }

    // SETTERS
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary must be >= 0.");
        }
        this.basicSalary = basicSalary;
    }

    // ACTIVATE / DEACTIVATE
    public void activate() {
        this.status = EmployeeStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = EmployeeStatus.INACTIVE;
    }

    @Override
    public String toString() {
        return id + " | " +
               fullName + " | " +
               department + " | " +
               jobTitle + " | " +
               basicSalary + " | " +
               status + " | " +
               getType();
    }
}