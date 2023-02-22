package pl.edu.wszib.library.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.wszib.library.entity.User;

public class AuthenticatorTest {
    Authenticator authenticator = Authenticator.getInstance();

    @Test
    public void nullAuthenticatorParameter() {
        User testUser = new User();
        Assertions.assertDoesNotThrow(() -> authenticator.authenticate(testUser));
    }
}
