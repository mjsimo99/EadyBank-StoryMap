package interfeces;

import dto.Affectation;
import dto.Employe;
import dto.Mission;

import java.util.List;

public interface Iaffectation {
    Affectation CreateNewAffectation(Affectation affectation);
    boolean DeleteAffectation(Affectation affectation);
    List<Affectation> getAssignmentHistoryByMatricule(String matricule);
    int[] getAffectationStatistics();
}
