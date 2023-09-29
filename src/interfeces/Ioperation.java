package interfeces;

import dto.Operation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Ioperation {
    Optional<Operation> Add(Operation operation);
    Optional<List<Operation>> SearchByNumber(String numero);
    boolean Delete(String numero);
}
