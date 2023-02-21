package pl.edu.wszib.library;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.engine.Authenticator;

public class HashGenerator {
    private static final Authenticator a = Authenticator.getInstance();
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("gimo" + a.getSeed()));
    }
}
