package implementation;

import dto.Affectation;
import helper.DatabaseConnection;
import interfeces.Iaffectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AffectationImpl implements Iaffectation {
    private static final String INSERT_AFFECTATION = "INSERT INTO Affectations (employe_matricule, mission_code, nom, description) VALUES (?, ?, ?, ?)";
    private static final String DELETE_AFFECTATION = "DELETE FROM Affectations WHERE employe_matricule = ? AND mission_code = ?";

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
    }
}