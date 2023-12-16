package lk.ijse.finalproject.model;

import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.RegistrationDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationModel {
    public static boolean saveRegistration(RegistrationDTO registrationDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO registration (class_id, student_id, RegFees, Remark, Date) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, registrationDTO.getClassId());
            preparedStatement.setString(2, registrationDTO.getStudentId());
            preparedStatement.setDouble(3, registrationDTO.getRegFees());
            preparedStatement.setString(4, registrationDTO.getRemark());
            preparedStatement.setString(5, registrationDTO.getDate());

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
}
