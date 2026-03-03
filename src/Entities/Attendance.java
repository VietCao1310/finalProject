package Entities;

import java.time.LocalDate;

public class Attendance {

    private String employeeId;
    private LocalDate date;
    private AttendanceStatus status;
    private int overtimeHours;

    // ================= CONSTRUCTOR =================
    public Attendance(String employeeId,
                      LocalDate date,
                      AttendanceStatus status,
                      int overtimeHours) {

        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null.");
        }

        if (overtimeHours < 0) {
            throw new IllegalArgumentException("Overtime hours must be >= 0.");
        }

        // Nếu không phải PRESENT thì overtime phải = 0
        if (status != AttendanceStatus.PRESENT && overtimeHours > 0) {
            throw new IllegalArgumentException("Only PRESENT employees can have overtime hours.");
        }

        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
        this.overtimeHours = overtimeHours;
    }

    // ================= GETTERS =================

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    // ================= SETTERS =================

    public void setStatus(AttendanceStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        // Nếu đổi sang ABSENT hoặc LEAVE thì overtime phải reset
        if (status != AttendanceStatus.PRESENT) {
            this.overtimeHours = 0;
        }

        this.status = status;
    }

    public void setOvertimeHours(int overtimeHours) {

        if (overtimeHours < 0) {
            throw new IllegalArgumentException("Overtime hours must be >= 0.");
        }

        if (this.status != AttendanceStatus.PRESENT) {
            throw new IllegalStateException("Cannot set overtime for non-present status.");
        }

        this.overtimeHours = overtimeHours;
    }

    // ================= toString =================

    @Override
    public String toString() {
        return employeeId + " | " +
               date + " | " +
               status + " | OT: " +
               overtimeHours;
    }
}