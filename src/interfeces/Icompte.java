package interfeces;

import dto.Client;
import dto.Compte;
import dto.EtatCompte;
import dto.Operation;

import java.util.Date;
import java.util.List;

public interface Icompte {
    Compte Add(Compte compte);
    List<Compte> SearchByClient(Client client);
    boolean Delete(String numero);
    Compte UpdateStatus(Compte compte);
    List<Compte> FilterByStatus(EtatCompte etat);
    List<Compte> ShowList();
    List<Compte> FilterByDCreation(Date dateCreation);
    Compte Update(Compte compte);
    List<Compte> SearchByOperation(Operation operation);





}
//gerniriques