package infra;

public interface RepositoryProvider {
	UserRepository getUserRepository();
    WallRepository getWallRepository();
}
