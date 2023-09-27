package dto;

import java.util.Date;
import java.util.List;

public class Client extends Personne{
    private String code ;
    List<Compte> comptes ;

    public Client(String code, String nom, String prenom, Date dateN, String tel, String adress, List<Compte> comptes) {
        super(nom, prenom, dateN, tel, adress);
        setCode(code);
        setComptes(comptes);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "code='" + code + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", dateN=" + getDateN() +
                ", tel='" + getTel() + '\'' +
                ", adress='" + getAdress() + '\'' +
                ", comptes=" + comptes +
                '}';
    }

}
