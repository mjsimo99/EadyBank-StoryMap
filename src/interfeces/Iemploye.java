package interfeces;

import dto.Employe;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Iemploye extends Ipersonne {
    Optional<List<Employe>> SearchByMatricule(String matricule);
    boolean Delete(String marticule);
    Optional<List<Employe>> ShowList();
    Optional<List<Employe>> SearchByDateR(Date dateRecrutement);
    Optional<Employe> Update(Employe employe);

}
