package infra;

import exceptions.PersistOperationException;

public interface RepositoryPersistence {
    void persist() throws PersistOperationException;
}
