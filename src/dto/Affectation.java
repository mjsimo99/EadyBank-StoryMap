package dto;

public class Affectation {
    private Employe employe;
    private Mission mission;
    private String nom;
    private String description;

    public Affectation(Employe employe, Mission mission,  String nom, String description) {
        setEmploye(employe);
        setMission(mission);
        setNom(nom);
        setDescription(description);
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "employe=" + employe +
                ", mission=" + mission +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
