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

        return affectation;
    }

    @Override
    public boolean DeleteAffectation(Affectation affectation) {
        return false;
    }
}