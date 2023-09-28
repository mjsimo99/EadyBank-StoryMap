package implementation;

import dto.Mission;
import helper.DatabaseConnection;
import interfeces.Imission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MissionImpl implements Imission {

    private static final String ADD_MISSION = "INSERT INTO Missions (code, nom, description) VALUES (?, ?, ?)";
    private static final String DELETE_MISSION = "DELETE FROM Missions WHERE code=?";
    private static final String SHOW_ALL_MISSIONS = "SELECT * FROM Missions";


    @Override
    public Mission Add(Mission mission) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_MISSION)) {
            preparedStatement.setString(1, mission.getCode());
            preparedStatement.setString(2, mission.getNome());
            preparedStatement.setString(3, mission.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mission;
    }


    @Override
    public boolean Delete(String code) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MISSION)) {
            preparedStatement.setString(1, code);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Mission> ShowList() {
        return null;
    }

    @Override
    public List<Mission> MisiionHistory() {
        return null;
    }

    @Override
    public List<Mission> MissionStatistic() {
        return null;
    }

    public Mission getCodeMission(String code) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Missions WHERE code=?")) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Mission(
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}