package Entities;

import java.time.LocalDate;

public class FullTimeEmployee extends Employee{
    private static final double OVERTIME_RATE = 80000;
    private static final double ABSENCE_DEDUCTION = 100000;

    public FullTimeEmployee(String id, String fullName, String department, String jobTitle, LocalDate dateOfJoining, double basicSalary) {
        super(id, fullName, department, jobTitle, dateOfJoining, basicSalary);
    }

    @Override
    public double calculateSalary(int overtimeHours, int absentDays) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot calculate salary for inactive employee!");
        }
        double overtimePay = overtimeHours * OVERTIME_RATE;
        double deduction = absentDays * ABSENCE_DEDUCTION;
        
        return basicSalary + overtimePay - deduction;
    }

    @Override
    public String getType() {
        return "Full-Time";
    }
    
    
    
}
