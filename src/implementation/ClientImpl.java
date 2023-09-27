package implementation;

import dto.Client;
import dto.Personne;
import helper.DatabaseConnection;
import interfeces.Iclient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientImpl implements Iclient {

    private static final String SEARCH_BY_CODE = "SELECT * FROM Clients WHERE code=?";
    private static final String DELETE_CLIENT = "DELETE FROM Clients WHERE code=?";
    private static final String SHOW_ALL_CLIENTS = "SELECT * FROM Clients";
    private static final String SEARCH_BY_PRENOM = "SELECT * FROM Clients WHERE prenom=?";
    private static final String UPDATE_CLIENT = "UPDATE Clients SET nom=?, prenom=?, dateN=?, tel=?, adress=? WHERE code=?";
    private static final String ADD_CLIENT = "INSERT INTO Clients (code, nom, prenom, dateN, tel, adress) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public List<Client> SearchByCode(String code) {

        return null;
    }

    @Override
    public boolean Delete(String code) {

        return false;
    }

    @Override
    public List<Client> Showlist() {

        return null;
    }

    @Override
    public List<Client> SearchByPrenom(String prenom) {

        return null;
    }

    @Override
    public Client Update(Client client) {

        return client;
    }

    @Override
    public Personne Add(Personne personne) {

        return personne;
    }
}
