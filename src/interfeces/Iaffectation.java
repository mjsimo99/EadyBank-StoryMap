package interfeces;

import dto.Affectation;
import dto.Employe;
import dto.Mission;

public interface Iaffectation {
    Affectation CreateNewAffectation(Affectation affectation);
    boolean DeleteAffectation(Affectation affectation);

}
