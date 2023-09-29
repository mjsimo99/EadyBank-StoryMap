package interfeces;

import dto.Affectation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Iaffectation {
    Optional<Affectation> createNewAffectation(Affectation affectation);

    boolean DeleteAffectation(Affectation affectation);

    List<Affectation> getAssignmentHistoryByMatricule(String matricule);

    HashMap<String, Integer> getAffectationStatistics();
}