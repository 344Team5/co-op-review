package dao;

import java.util.List;

public interface IDao<T> {
    List<T> getAll();
    T create();
    T get(int id);
    void update(T object);
    void delete(T object);
}
