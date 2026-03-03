package Entities;

import java.time.LocalDate;

public class PartTimeEmployee extends Employee{
    private static final double OVERTIME_RATE = 50000;
    private static final double ABSENCE_DEDUCTION = 100000;

    public PartTimeEmployee(String id, String fullName, String department, String jobTitle, LocalDate dateOfJoining, double basicSalary) {
        super(id, fullName, department, jobTitle, dateOfJoining, basicSalary);
    }

    @Override
    public double calculateSalary(int overtimeHours, int absentDays) {
        if(!isActive()) {
            throw new IllegalStateException("Cannot calculate salary for inactive employee!");
        }
        
        double overtimPay = overtimeHours * OVERTIME_RATE;
        double deduction = absentDays * ABSENCE_DEDUCTION;
        
        return basicSalary + overtimPay - deduction;
    }

    @Override
    public String getType() {
        return "Part-Time";
    }
    
    
    
}
