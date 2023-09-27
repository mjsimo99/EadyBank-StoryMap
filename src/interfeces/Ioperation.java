package interfeces;

import dto.Operation;

import java.util.Date;
import java.util.List;

public interface Ioperation {
    Operation Add(Operation operation);
    List<Operation> SearchByNumber(String numero);
    boolean Delete(String numero);
}
