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
