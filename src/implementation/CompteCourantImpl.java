package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.Icompte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompteCourantImpl implements Icompte {
    private static final String ADD_COMPTE_COURANT = "INSERT INTO Comptes (numero, sold, dateCreation, etat, client_code, employe_matricule) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_COMPTE_COURANT_TABLE = "INSERT INTO ComptesCourants (numeroCompte, decouvert) VALUES (?, ?)";
    private static final String DELETE_COMPTE = "DELETE FROM Comptes WHERE numero = ?";
    private static final String SEARCH_BY_CLIENT = "SELECT * FROM Comptes WHERE client_code = ?";
    private static final String SEARCH_BY_CLIENT_DECOUVERT = "SELECT decouvert FROM ComptesCourants WHERE numeroCompte = ?";
    private static final String UPDATE_STATUS_COMPTE = "UPDATE Comptes SET etat = ? WHERE numero = ?";
    private static final String LIST_COMPTE = "SELECT c.numero, c.sold, c.dateCreation, c.etat, cc.decouvert " + "FROM Comptes c " + "LEFT JOIN ComptesCourants cc ON c.numero = cc.numeroCompte";
    private static final String UPDATE_COMPTE = "UPDATE Comptes " + "SET sold = ?, dateCreation = ?, etat = ?, client_code = ?, employe_matricule = ? " + "WHERE numero = ?";

    private static final String GET_COMPTE_BYNUMBER = "SELECT * FROM Comptes WHERE numero = ?";








    @Override
    public Compte Add(Compte compte) {
        if (compte instanceof CompteCourant compteCourant) {
            Connection connection = DatabaseConnection.getConn();

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMPTE_COURANT)) {
                preparedStatement.setString(1, compteCourant.getNumero());
                preparedStatement.setDouble(2, compteCourant.getSold());
                preparedStatement.setDate(3, new java.sql.Date(compteCourant.getDateCreation().getTime()));
                preparedStatement.setString(4, compteCourant.getEtat().name());
                preparedStatement.setString(5, compteCourant.getClient().getCode());
                preparedStatement.setString(6, compteCourant.getEmploye().getMatricule());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    try (PreparedStatement compteCourantStatement = connection.prepareStatement(ADD_COMPTE_COURANT_TABLE)) {
                        compteCourantStatement.setString(1, compteCourant.getNumero());
                        compteCourantStatement.setDouble(2, compteCourant.getDecouvert());
                        compteCourantStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    return compteCourant;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
    public List<Compte> ShowList() {


        return null;
    }

    public static Compte GetByNumero(String numero) {
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


    @Override
    public List<Compte> FilterByStatus(EtatCompte etat) {

        return null;
    }




    @Override
    public List<Compte> FilterByDCreation(Date dateCreation) {
        return null;
    }


}
