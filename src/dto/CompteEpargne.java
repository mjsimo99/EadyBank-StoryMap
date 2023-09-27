package dto;

import java.util.Date;
import java.util.List;

public class CompteEpargne extends Compte{
    private double tauxInteret;//double

    public CompteEpargne(String numero, double sold, Date dateCreation, EtatCompte etat, Client client, Employe employe, List<Operation> operations, double tauxInteret) {
        super(numero, sold, dateCreation, etat, client, employe, operations);
        setTauxInteret(tauxInteret);
    }



    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    @Override
    public String toString() {
        return "CompteEpargne{" +
                "tauxInteret=" + tauxInteret +
                ", numero='" + numero + '\'' +
                ", sold=" + sold +
                ", dateCreation=" + dateCreation +
                ", etat=" + etat +
                ", client=" + client +
                ", employe=" + employe +
                ", operations=" + operations +
                '}';
    }
}
