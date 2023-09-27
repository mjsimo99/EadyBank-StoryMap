package implementation;

import dto.Client;
import dto.Compte;
import dto.CompteEpargne;
import dto.EtatCompte;
import dto.Operation;
import helper.DatabaseConnection;
import interfeces.Icompte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompteEpargneImpl implements Icompte {
    private static final String ADD_COMPTE_EPARGNE = "INSERT INTO Comptes (numero, sold, dateCreation, etat, client_code, employe_matricule) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_COMPTE_EPARGNE_TABLE = "INSERT INTO ComptesEpargnes (numeroCompte, tauxInteret) VALUES (?, ?)";
    private static final String SEARCH_BY_CLIENT = "SELECT * FROM Comptes WHERE client_code = ?";
    private static final String SEARCH_BY_CLIENT_TAUX = "SELECT tauxInteret FROM ComptesEpargnes WHERE numeroCompte = ?";
    private static final String DELETE_COMPTE = "DELETE FROM Comptes WHERE numero = ?";
    private static final String UPDATE_STATUS_COMPTE = "UPDATE Comptes SET etat = ? WHERE numero = ?";
    private static final String LIST_COMPTE = "SELECT c.numero, c.sold, c.dateCreation, c.etat, ce.tauxInteret " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesEpargnes ce ON c.numero = ce.numeroCompte";
    private static final String UPDATE_COMPTE = "UPDATE Comptes " +
            "SET sold = ?, dateCreation = ?, etat = ?, client_code = ?, employe_matricule = ? " +
            "WHERE numero = ?";
    private static final String GET_COMPTE_BY_NUMBER = "SELECT * FROM Comptes WHERE numero = ?";



    public static Compte GetByNumero(String numero) {
        Connection connection = DatabaseConnection.getConn();
        Compte compte = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPTE_BY_NUMBER);
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fetchedNumero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                Date dateCreation = resultSet.getDate("dateCreation");
                String etatStr = resultSet.getString("etat");

                compte = new CompteEpargne(fetchedNumero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, 0.0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compte;
    }

    @Override
    public Compte Add(Compte compte) {
        return null;
    }

    @Override
    public List<Compte> SearchByClient(Client client) {
        return null;
    }

    @Override
    public boolean Delete(String numero) {
        return false;
    }

    @Override
    public Compte UpdateStatus(Compte compte) {
        return null;
    }

    @Override
    public List<Compte> FilterByStatus(EtatCompte etat) {
        return null;
    }

    @Override
    public List<Compte> ShowList() {
        return null;
    }

    @Override
    public List<Compte> FilterByDCreation(Date dateCreation) {
        return null;
    }

    @Override
    public Compte Update(Compte compte) {
        return null;
    }

    @Override
    public List<Compte> SearchByOperation(Operation operation) {
        return null;
    }
}
