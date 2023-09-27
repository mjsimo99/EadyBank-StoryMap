package dto;

import java.util.Date;

public abstract class Personne {
    protected String nom;
    protected String prenom;
    protected Date dateN;
    protected String tel;
    protected String adress;

    public Personne(String nom, String prenom, Date dateN, String tel, String adress) {

        setNom(nom);
        setPrenom(prenom);
        setDateN(dateN);
        setTel(tel);
        setAdress(adress);
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public Date getDateN() {
        return dateN;
    }

    public void setDateN(Date dateN) {
        this.dateN = dateN;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", Prenom='" + prenom + '\'' +
                ", dateN=" + dateN +
                ", tel='" + tel + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}

