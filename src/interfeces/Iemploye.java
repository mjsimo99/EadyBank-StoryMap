package interfeces;

import dto.Employe;

import java.util.Date;
import java.util.List;

public interface Iemploye extends Ipersonne {
    List<Employe> SearchByMatricule(String matricule);
    boolean Delete(String marticule);
    List<Employe> ShowList();
    List<Employe> SearchByDateR(Date dateRecrutement);
    Employe Update(Employe employe);

}
