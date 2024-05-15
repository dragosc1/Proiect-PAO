package Classes.Repositories;

import java.util.List;

public interface GenericCRUDRepository<T> {
    void insert(T data);
    List<T> retrieveAll(Class<T> clazz);
    T retrieveOneId(Class<T> clazz, int id);
    void update(T oldData, T newData);
    void delete(T data);
    void deleteAll(Class<?> clazz);
}