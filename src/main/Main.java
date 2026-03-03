package main;

import DAO.EmployeeDAO;
import DAO.AttendanceDAO;

import manager.EmployeeManager;
import manager.AttendanceManager;
import manager.SalaryManager;
import manager.ReportManager;

import UI.MainMenu;

public class Main {

    public static void main(String[] args) {

        // ===== DAO Layer =====
        EmployeeDAO employeeDAO = new EmployeeDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        // ===== Manager Layer =====
        EmployeeManager employeeManager =
                new EmployeeManager(employeeDAO);

        AttendanceManager attendanceManager =
                new AttendanceManager(attendanceDAO, employeeManager);

        SalaryManager salaryManager =
                new SalaryManager(employeeManager, attendanceManager);

        ReportManager reportManager =
                new ReportManager(employeeManager, attendanceManager);

        // ===== Start Main Menu =====

        MainMenu mainMenu = new MainMenu(employeeManager,
                     attendanceManager,
                     salaryManager,
                     reportManager);
        
        mainMenu.show();
    }
}