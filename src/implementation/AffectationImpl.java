package implementation;

import dto.Affectation;
import dto.Employe;
import dto.Mission;
import helper.DatabaseConnection;
import interfeces.Iaffectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AffectationImpl implements Iaffectation {
    private static final String INSERT_AFFECTATION = "INSERT INTO Affectations (employe_matricule, mission_code, nom, description) VALUES (?, ?, ?, ?)";
    private static final String DELETE_AFFECTATION = "DELETE FROM Affectations WHERE employe_matricule = ? AND mission_code = ?";

    private static final String SELECT_AFFECTATIONS_BY_MATRICULE =
            "SELECT * FROM Affectations WHERE employe_matricule = ?";
    @Override
    public Affectation CreateNewAffectation(Affectation affectation) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AFFECTATION)) {
            preparedStatement.setString(1, affectation.getEmploye().getMatricule());
            preparedStatement.setString(2, affectation.getMission().getCode());
            preparedStatement.setString(3, affectation.getNom());
            preparedStatement.setString(4, affectation.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating affectation failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectation;
    }

    @Override
    public boolean DeleteAffectation(Affectation affectation) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AFFECTATION)) {
            preparedStatement.setString(1, affectation.getEmploye().getMatricule());
            preparedStatement.setString(2, affectation.getMission().getCode());
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Affectation> getAssignmentHistoryByMatricule(String matricule) {
        List<Affectation> assignmentHistory = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AFFECTATIONS_BY_MATRICULE)) {
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String missionCode = resultSet.getString("mission_code");
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");

                Employe employe = new EmployeImpl().getEmployeById(matricule);

                Mission mission = new MissionImpl().getCodeMission(missionCode);

                Affectation affectation = new Affectation(employe, mission, nom, description);
                assignmentHistory.add(affectation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return assignmentHistory;
    }
    @Override
    public int[] getAffectationStatistics() {

        Connection connection = DatabaseConnection.getConn();
        String sqlTotalAffectations = "SELECT COUNT(*) AS TotalAffectations FROM Affectations";
        String sqlTotalEmployees = "SELECT COUNT(DISTINCT employe_matricule) AS TotalEmployees FROM Affectations";
        String sqlTotalMissions = "SELECT COUNT(DISTINCT mission_code) AS TotalMissions FROM Affectations";

        try (PreparedStatement preparedStatementTotalAffectations = connection.prepareStatement(sqlTotalAffectations);
             PreparedStatement preparedStatementTotalEmployees = connection.prepareStatement(sqlTotalEmployees);
             PreparedStatement preparedStatementTotalMissions = connection.prepareStatement(sqlTotalMissions)) {

            ResultSet resultSetTotalAffectations = preparedStatementTotalAffectations.executeQuery();
            ResultSet resultSetTotalEmployees = preparedStatementTotalEmployees.executeQuery();
            ResultSet resultSetTotalMissions = preparedStatementTotalMissions.executeQuery();

            int totalAffectations = resultSetTotalAffectations.next() ? resultSetTotalAffectations.getInt("TotalAffectations") : 0;
            int totalEmployees = resultSetTotalEmployees.next() ? resultSetTotalEmployees.getInt("TotalEmployees") : 0;
            int totalMissions = resultSetTotalMissions.next() ? resultSetTotalMissions.getInt("TotalMissions") : 0;

            return new int[] { totalAffectations, totalEmployees, totalMissions };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






}
