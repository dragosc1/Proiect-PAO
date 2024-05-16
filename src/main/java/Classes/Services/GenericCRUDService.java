// GenericCRUDService.java
package Classes.Services;

import Classes.Repositories.GenericCRUDRepository;
import Classes.RepositoriesImplementation.GenericCRUDRepositoryImplementation;

import java.util.List;

public class GenericCRUDService<T> {
    private GenericCRUDRepository<T> repository;
    private static GenericCRUDService<?> instance;

    private GenericCRUDService() {
       this.repository = new GenericCRUDRepositoryImplementation<>();
    }

    public static synchronized <T> GenericCRUDService<T> getInstance() {
        if (instance == null) {
            instance = new GenericCRUDService<>();
        }
        return (GenericCRUDService<T>) instance;
    }

    public void insert(T data) {
        repository.insert(data);
    }

    public List<T> retrieveAll(Class<T> clazz) {
        return repository.retrieveAll(clazz);
    }

    public T retrieveOneId(Class<T> clazz, int id) {
        return repository.retrieveOneId(clazz, id);
    }

    public void update(T oldData, T newData) {
        repository.update(oldData, newData);
    }

    public void delete(T data) {
        repository.delete(data);
    }

    public void deleteAll(Class<?> clazz) {
        repository.deleteAll(clazz);
    }

    public void openConnection() {
        repository.openConnection();
    }

    public void closeConnection() {
        repository.closeConnection();
    }
}