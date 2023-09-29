package interfeces;

import java.util.List;

public interface IBase<T> {
    T Add(T entity);
    boolean Delete(String code);
    List<T> ShowList();
}

