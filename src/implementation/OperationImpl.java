package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.Ioperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationImpl implements Ioperation {
    private static final String ADD_OPERATION = "INSERT INTO operations (numero, datecreation, montant, type, employe_matricule, compte_numero) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_BY_NUMBER = "SELECT * FROM operations WHERE numero=?";
    private static final String DELETE_OPERATION = "DELETE FROM operations WHERE numero=?";
    private static final String GET_EMPLOYE_BY_MATRICULE = "SELECT * FROM employes WHERE matricule=?";
    private static final String GET_COMPTE_EPARGNE = "SELECT * FROM ComptesEpargnes WHERE numeroCompte = ?";

    private static final String GET_COMPTE_COYRANT = "SELECT * FROM ComptesCourants WHERE numeroCompte = ?";


    public Compte getCompteByNumero(String numero) {
        Connection connection = DatabaseConnection.getConn();
        try {
            String queryCourant = "SELECT * FROM ComptesCourants WHERE numeroCompte = ?";
            PreparedStatement preparedStatementCourant = connection.prepareStatement(GET_COMPTE_COYRANT);
            preparedStatementCourant.setString(1, numero);
            ResultSet resultSetCourant = preparedStatementCourant.executeQuery();

            if (resultSetCourant.next()) {
                CompteCourant compteCourant = new CompteCourant(
                        resultSetCourant.getString("numeroCompte"),
                        0.0,
                        null,
                        null,
                        null,
                        null,
                        null,
                        resultSetCourant.getDouble("decouvert")
                );
                return compteCourant;
            }

            String queryEpargne = "SELECT * FROM ComptesEpargnes WHERE numeroCompte = ?";
            PreparedStatement preparedStatementEpargne = connection.prepareStatement(GET_COMPTE_EPARGNE);
            preparedStatementEpargne.setString(1, numero);
            ResultSet resultSetEpargne = preparedStatementEpargne.executeQuery();

            if (resultSetEpargne.next()) {
                CompteEpargne compteEpargne = new CompteEpargne(
                        resultSetEpargne.getString("numeroCompte"),
                        0.0,
                        null,
                        null,
                        null,
                        null,
                        null,
                        resultSetEpargne.getDouble("tauxInteret")
                );
                return compteEpargne;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving account by numero: " + e.getMessage(), e);
        }
        return null;
    }

    public Employe getEmployeByMatricule(String matricule) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_EMPLOYE_BY_MATRICULE)) {
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employe employe = new Employe(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateN"),
                        resultSet.getString("tel"),
                        resultSet.getString("adress"),
                        resultSet.getString("matricule"),
                        resultSet.getDate("dateRecrutement"),
                        resultSet.getString("emailAdresse"),
                        null,
                        null,
                        null
                );
                return employe;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Operation Add(Operation operation) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_OPERATION)) {
            preparedStatement.setString(1, operation.getNumero());
            preparedStatement.setDate(2, new java.sql.Date(operation.getDateCreation().getTime()));
            preparedStatement.setDouble(3, operation.getMontant());
            preparedStatement.setString(4, operation.getType().toString());
            preparedStatement.setString(5, operation.getEmploye().getMatricule());
            preparedStatement.setString(6, operation.getCompte().getNumero());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return operation;
    }

    @Override
    public List<Operation> SearchByNumber(String numero) {
        return null;
    }

    @Override
    public boolean Delete(String numero) {
        return false;
    }
}
