package lk.ijse.finalproject.model;

import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.HallDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallModel {

    public static List<HallDTO> getAllHalls() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<HallDTO> halls = new ArrayList<>();

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT HallID, Name, Capacity FROM hall";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HallDTO hall = new HallDTO(
                    resultSet.getString("HallID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Capacity")
                );
                halls.add(hall);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
        return halls;
    }
}
