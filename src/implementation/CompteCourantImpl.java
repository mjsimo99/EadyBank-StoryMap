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
import java.util.Optional;

public class CompteCourantImpl implements Icompte {
    private static final String INSERT_COMPTE_COURANT = "INSERT INTO Comptes (numero, sold, dateCreation, etat, client_code, employe_matricule) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_COMPTE_COURANT_TABLE = "INSERT INTO ComptesCourants (numeroCompte, decouvert) VALUES (?, ?)";
    private static final String DELETE_COMPTE = "DELETE FROM Comptes WHERE numero = ?";
    private static final String SEARCH_BY_CLIENT = "SELECT * FROM Comptes WHERE client_code = ?";
    private static final String SEARCH_BY_CLIENT_DECOUVERT = "SELECT decouvert FROM ComptesCourants WHERE numeroCompte = ?";
    private static final String UPDATE_STATUS_COMPTE = "UPDATE Comptes SET etat = ? WHERE numero = ?";
    private static final String LIST_COMPTE = "SELECT c.numero, c.sold, c.dateCreation, c.etat, cc.decouvert " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesCourants cc ON c.numero = cc.numeroCompte";
    private static final String UPDATE_COMPTE = "UPDATE Comptes " +
            "SET sold = ?, dateCreation = ?, etat = ?, client_code = ?, employe_matricule = ? " +
            "WHERE numero = ?";
    private static final String GET_COMPTE_BY_NUMBER = "SELECT * FROM Comptes WHERE numero = ?";
    private static final String SEARCH_BY_OPERATION = "SELECT c.numero, c.sold, c.dateCreation, c.etat, cc.decouvert " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesCourants cc ON c.numero = cc.numeroCompte " +
            "INNER JOIN Operations ao ON c.numero = ao.compte_numero " +
            "WHERE ao.type = ?";
    private static final String FILTER_BY_STATUS = "SELECT * FROM Comptes WHERE etat = ? ORDER BY etat DESC";
    private static final String FILTER_BY_DATE_CREATION = "SELECT c.numero, c.sold, c.dateCreation, c.etat, cc.decouvert " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesCourants cc ON c.numero = cc.numeroCompte " +
            "WHERE c.dateCreation = ?";

    @Override
    public Optional<Compte> Add(Compte compte) {
        if (compte instanceof CompteCourant compteCourant) {
            try (Connection connection = DatabaseConnection.getConn();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPTE_COURANT)) {
                preparedStatement.setString(1, compteCourant.getNumero());
                preparedStatement.setDouble(2, compteCourant.getSold());
                preparedStatement.setDate(3, new java.sql.Date(compteCourant.getDateCreation().getTime()));
                preparedStatement.setString(4, compteCourant.getEtat().name());
                preparedStatement.setString(5, compteCourant.getClient().getCode());
                preparedStatement.setString(6, compteCourant.getEmploye().getMatricule());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    try (PreparedStatement compteCourantStatement = connection.prepareStatement(INSERT_COMPTE_COURANT_TABLE)) {
                        compteCourantStatement.setString(1, compteCourant.getNumero());
                        compteCourantStatement.setDouble(2, compteCourant.getDecouvert());
                        compteCourantStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    return Optional.of(compteCourant);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> SearchByClient(Client client) {
        List<Compte> compteList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_CLIENT)) {
            preparedStatement.setString(1, client.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                Date dateCreation = resultSet.getDate("dateCreation");
                String etatStr = resultSet.getString("etat");
                EtatCompte etat = EtatCompte.valueOf(etatStr);

                try (PreparedStatement decouvertStatement = connection.prepareStatement(SEARCH_BY_CLIENT_DECOUVERT)) {
                    decouvertStatement.setString(1, numero);
                    ResultSet decouvertResultSet = decouvertStatement.executeQuery();
                    float decouvert = 0;

                    if (decouvertResultSet.next()) {
                        decouvert = decouvertResultSet.getFloat("decouvert");
                    }

                    CompteCourant compteCourant = new CompteCourant(numero, sold, dateCreation, etat, client, null, null, decouvert);
                    compteList.add(compteCourant);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public boolean Delete(String numero) {
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement deleteComptesStatement = connection.prepareStatement(DELETE_COMPTE)) {
            deleteComptesStatement.setString(1, numero);
            int rowsDeleted = deleteComptesStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Compte> UpdateStatus(Compte compte) {
        if (compte instanceof CompteCourant compteCourant) {
            try (Connection connection = DatabaseConnection.getConn();
                 PreparedStatement updateCompteStatusStatement = connection.prepareStatement(UPDATE_STATUS_COMPTE)) {
                updateCompteStatusStatement.setString(1, compteCourant.getEtat().name());
                updateCompteStatusStatement.setString(2, compteCourant.getNumero());

                int rowsUpdated = updateCompteStatusStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return Optional.of(compteCourant);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> ShowList() {
        List<Compte> compteList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(LIST_COMPTE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                Date dateCreation = resultSet.getDate("dateCreation");
                String etatStr = resultSet.getString("etat");
                EtatCompte etat = EtatCompte.valueOf(etatStr);
                double decouvert = resultSet.getDouble("decouvert");

                Compte compte = new CompteCourant(numero, sold, dateCreation, etat, null, null, null, decouvert);
                compteList.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    public static Compte GetByNumero(String numero) {
        Connection connection = DatabaseConnection.getConn();
        Compte compte = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPTE_BY_NUMBER)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fetchedNumero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                Date dateCreation = resultSet.getDate("dateCreation");
                String etatStr = resultSet.getString("etat");
                EtatCompte etat = EtatCompte.valueOf(etatStr);

                compte = new CompteCourant(fetchedNumero, sold, dateCreation, etat, null, null, null, 0.0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compte;
    }

    @Override
    public Optional<Compte> Update(Compte compte) {
        if (compte instanceof CompteCourant compteCourant) {
            try (Connection connection = DatabaseConnection.getConn();
                 PreparedStatement updateCompteStatement = connection.prepareStatement(UPDATE_COMPTE)) {
                updateCompteStatement.setDouble(1, compteCourant.getSold());
                updateCompteStatement.setDate(2, new java.sql.Date(compteCourant.getDateCreation().getTime()));
                updateCompteStatement.setString(3, compteCourant.getEtat().name());
                updateCompteStatement.setString(4, compteCourant.getClient().getCode());
                updateCompteStatement.setString(5, compteCourant.getEmploye().getMatricule());
                updateCompteStatement.setString(6, compteCourant.getNumero());

                int rowsUpdated = updateCompteStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return Optional.of(compteCourant);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> SearchByOperation(Operation operation) {
        List<Compte> compteList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_OPERATION)) {
            preparedStatement.setString(1, operation.getType().name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String numero = resultSet.getString("numero");
                    double sold = resultSet.getDouble("sold");
                    Date dateCreation = resultSet.getDate("dateCreation");
                    String etatStr = resultSet.getString("etat");
                    double decouvert = resultSet.getDouble("decouvert");

                    Compte compte = new CompteCourant(numero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, decouvert);
                    compteList.add(compte);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public List<Compte> FilterByStatus(EtatCompte etat) {
        List<Compte> compteList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_STATUS)) {
            preparedStatement.setString(1, etat.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String numero = resultSet.getString("numero");
                    double sold = resultSet.getDouble("sold");
                    Date dateCreation = resultSet.getDate("dateCreation");
                    String etatStr = resultSet.getString("etat");

                    Compte compte = new CompteCourant(numero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, 0.0);
                    compteList.add(compte);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public List<Compte> FilterByDCreation(Date dateCreation) {
        List<Compte> compteList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_DATE_CREATION)) {
            preparedStatement.setDate(1, new java.sql.Date(dateCreation.getTime()));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String numero = resultSet.getString("numero");
                    double sold = resultSet.getDouble("sold");
                    Date creationDate = resultSet.getDate("dateCreation");
                    String etatStr = resultSet.getString("etat");
                    double decouvert = resultSet.getDouble("decouvert");

                    Compte compte = new CompteCourant(numero, sold, creationDate, EtatCompte.valueOf(etatStr), null, null, null, decouvert);
                    compteList.add(compte);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }
}