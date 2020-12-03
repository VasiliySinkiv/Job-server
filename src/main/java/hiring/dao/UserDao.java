package hiring.dao;

import hiring.exception.ServerException;
import hiring.model.User;

public interface UserDao {
    String insert(User user) throws ServerException;

    void delete(User user, String token);

    String login(String login, String password) throws ServerException;

    void logout(User user, String token);
}
