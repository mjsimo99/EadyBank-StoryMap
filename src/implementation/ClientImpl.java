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
        List<Client> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_CODE)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateN"),
                        resultSet.getString("tel"),
                        resultSet.getString("adress"),
                        null
                );
                resultList.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public boolean Delete(String code) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT)) {
            preparedStatement.setString(1, code);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Client> Showlist() {
        List<Client> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_CLIENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateN"),
                        resultSet.getString("tel"),
                        resultSet.getString("adress"),
                        null
                );
                resultList.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public List<Client> SearchByPrenom(String prenom) {
        List<Client> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_PRENOM)) {
            preparedStatement.setString(1, prenom);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateN"),
                        resultSet.getString("tel"),
                        resultSet.getString("adress"),
                        null // List<Compte> comptes is not retrieved from the database in this example
                );
                resultList.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public Client Update(Client client) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setDate(3, new java.sql.Date(client.getDateN().getTime()));
            preparedStatement.setString(4, client.getTel());
            preparedStatement.setString(5, client.getAdress());
            preparedStatement.setString(6, client.getCode());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return client;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Personne Add(Personne personne) {
        if (personne instanceof Client) {
            Client client = (Client) personne;
            Connection connection = DatabaseConnection.getConn();
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT)) {
                preparedStatement.setString(1, client.getCode());
                preparedStatement.setString(2, client.getNom());
                preparedStatement.setString(3, client.getPrenom());
                preparedStatement.setDate(4, new java.sql.Date(client.getDateN().getTime()));
                preparedStatement.setString(5, client.getTel());
                preparedStatement.setString(6, client.getAdress());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return personne;
    }
}
