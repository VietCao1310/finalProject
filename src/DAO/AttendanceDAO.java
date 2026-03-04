package DAO;

import Entities.Attendance;
import Enums.AttendanceStatus;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    private final String FILE_PATH = "attendance.txt";

    // ================= LOAD =================
    public List<Attendance> loadAttendance() {

        List<Attendance> list = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return list; // chưa có file thì trả list rỗng
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length != 4) continue;

                String employeeId = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                AttendanceStatus status = AttendanceStatus.valueOf(parts[2]);
                int overtimeHours = Integer.parseInt(parts[3]);

                Attendance attendance = new Attendance(
                        employeeId,
                        date,
                        status,
                        overtimeHours
                );

                list.add(attendance);
            }

        } catch (IOException e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        }

        return list;
    }

    // ================= SAVE =================
    public void saveAttendance(List<Attendance> attendanceList) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Attendance a : attendanceList) {

                bw.write(
                        a.getEmployeeId() + "," +
                        a.getDate() + "," +
                        a.getStatus().name() + "," +
                        a.getOvertimeHours()
                );

                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error loading attendance: " + e.getMessage());        }
    }
}