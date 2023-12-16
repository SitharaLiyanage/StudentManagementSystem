package lk.ijse.finalproject.model;

import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.ClassScheduleDTO;
import lk.ijse.finalproject.DTO.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassScheduleModel {
    public static boolean saveClassSchedule(ClassScheduleDTO scheduleDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO classschedule (TutorID, ClassID, hall_id, Date, time) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, scheduleDTO.getTutorID());
            preparedStatement.setString(2, scheduleDTO.getClassID());
            preparedStatement.setString(3, scheduleDTO.getHallID());
            preparedStatement.setString(4, scheduleDTO.getDate());
            preparedStatement.setString(5, scheduleDTO.getTime());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static List<StudentDTO> getStudentsByClassID(String classID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StudentDTO> students = new ArrayList<>();

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT s.StudentID, s.class, s.Name, s.email, s.Grade, s.gender " +
                    "FROM student s " +
                    "JOIN registration r ON s.StudentID = r.student_id " +
                    "WHERE r.class_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                StudentDTO student = new StudentDTO(
                        resultSet.getString("StudentID"),
                        resultSet.getString("class"), // Assuming class is the class_id in your DTO
                        resultSet.getString("Name"),
                        resultSet.getString("email"),
                        resultSet.getString("Grade"),
                        resultSet.getString("gender")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return students;
    }

}
