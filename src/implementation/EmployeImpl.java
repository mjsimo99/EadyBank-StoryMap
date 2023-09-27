package implementation;

import dto.Employe;
import dto.Personne;
import helper.DatabaseConnection;
import interfeces.Iemploye;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeImpl implements Iemploye {

    private static final String ADD_EMPLOYE = "INSERT INTO Employes (matricule, dateRecrutement, emailAdresse, nom, prenom, dateN, tel, adress) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_BY_MATRICULE = "SELECT * FROM Employes WHERE matricule=?";
    private static final String DELETE_EMPLOYE = "DELETE FROM Employes WHERE matricule=?";
    private static final String SHOW_ALL_EMPLOYEES = "SELECT * FROM Employes";
    private static final String SEARCH_BY_DATE_RECRUTEMENT = "SELECT * FROM Employes WHERE dateRecrutement=?";
    private static final String UPDATE_EMPLOYE = "UPDATE Employes SET dateRecrutement=?, emailAdresse=?, nom=?, prenom=?, dateN=?, tel=?, adress=? WHERE matricule=?";


    @Override
    public List<Employe> SearchByMatricule(String matricule) {
        return null;
    }

    @Override
    public boolean Delete(String marticule) {
        return false;
    }

    @Override
    public List<Employe> ShowList() {
        return null;
    }

    @Override
    public List<Employe> SearchByDateR(Date dateRecrutement) {
        return null;
    }

    @Override
    public Employe Update(Employe employe) {
        return null;
    }

    @Override
    public Personne Add(Personne personne) {
        if (personne instanceof Employe) {
            Employe employe = (Employe) personne;
            Connection connection = DatabaseConnection.getConn();
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPLOYE)) {
                preparedStatement.setString(1, employe.getMatricule());
                preparedStatement.setDate(2, new java.sql.Date(employe.getDateRecrutement().getTime()));
                preparedStatement.setString(3, employe.getEmailAdresse());
                preparedStatement.setString(4, employe.getNom());
                preparedStatement.setString(5, employe.getPrenom());
                preparedStatement.setDate(6, new java.sql.Date(employe.getDateN().getTime()));
                preparedStatement.setString(7, employe.getTel());
                preparedStatement.setString(8, employe.getAdress());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return personne;
    }
}
