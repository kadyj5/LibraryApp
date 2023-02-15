package pl.edu.wszib.library.engine;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.entity.User;

import java.util.Optional;

public class Authenticator {
    private final UserDAO userDAO = UserDAO.getInstance();
    private final String seed = "Mp@eI&1LEqCJ71HQQV0N1j2zqItr4&1W7*F";
    private User loggedUser = null;
    private static final Authenticator instance = new Authenticator();

    private Authenticator(){}
    public void authenticate(User user) {
        Optional<User> userBox = Optional.ofNullable(this.userDAO.findByLogin(user.getLogin()));
        if(userBox.isPresent()) {
            User userFromDB = userBox.get();
            if(userFromDB.getPassword().equals(
                    DigestUtils.md5Hex(user.getPassword() + this.seed))) {
                        this.loggedUser = userFromDB;
            }
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public String getSeed() {
        return seed;
    }

    public static Authenticator getInstance() {
        return instance;
    }
}
