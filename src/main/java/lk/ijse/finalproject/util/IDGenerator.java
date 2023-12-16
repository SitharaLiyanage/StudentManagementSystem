package lk.ijse.finalproject.util;

import lk.ijse.finalproject.DB.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IDGenerator {

    // Method to generate IDs for tables with a specified prefix
    public static String generateID(String tableName, String prefix, int idLength) throws SQLException, ClassNotFoundException {
        String newID = "";

        // Get Connection Object using DBConnection.getInstance().getConnection()
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection != null) {
            try {
                // SQL query to get the last ID from the specified table
                String getLastIDQuery = "SELECT MAX(RIGHT(" + tableName + "ID, " + (idLength - prefix.length()) + ")) AS max_id FROM " + tableName;

                PreparedStatement preparedStatement = connection.prepareStatement(getLastIDQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                int lastNumber = 0;

                // If there is an existing ID, extract the last number
                if (resultSet.next()) {
                    lastNumber = resultSet.getInt("max_id");
                }

                // Increment the number part by 1
                lastNumber++;

                // Format the new ID
                String formatString = "%0" + (idLength - prefix.length()) + "d";
                newID = prefix + String.format(formatString, lastNumber);

                // Close resources
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newID;
    }
}
