package infra;

import exceptions.PersistOperationException;

public class RepositoryFactory
{
	public static RepositoryProvider create() {
		return new DataRepository();
	}
	
	public static RepositoryProvider load() throws PersistOperationException {
		return DataRepository.load();
	}
	
	@ForTestsOnly
	public static void reset() {
		DataRepository.reset();
	}
}
