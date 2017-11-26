package infra;

import exceptions.PersistOperationException;

public class RepositoryFactory
{
	public static RepositoryProvider create()
	{
		RepositoryProvider provider = DataRepository.getInstance();
		return provider == null ? new DataRepository() : provider;
	}
	
	public static RepositoryProvider load() throws PersistOperationException
	{
		RepositoryProvider provider = DataRepository.getInstance();
		return provider == null ? DataRepository.load() : provider;
	}
	
	@ForTestsOnly
	public static void reset() {
		DataRepository.reset();
	}
}
