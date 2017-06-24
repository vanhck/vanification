package de.vanhck;

import de.vanhck.data.Score;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;

public class Util {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /** Computes a salted PBKDF2 hash of given plaintext password
     suitable for storing in a database.
     Empty passwords are not supported. */
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /** Checks whether given plaintext password corresponds
     to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }

    public static double getEndScore(Collection<Score> scores) {
        double endScore = 0;
        double endCourse = 0;

        for (Score score : scores) {
            endCourse += score.getCourse();
            endScore += score.getScore();
        }

        return endCourse == 0 ? 0 : endScore / endCourse;
    }

    public static double getScoreFromTime(Collection<Score> scores, Date from) {
        double endScore = 0;
        double endCourse = 0;

        for (Score score : scores) {
            if (score.getDate() != null && score.getDate().after(from)) {
                endCourse += score.getCourse();
                endScore += score.getScore();
            }
        }
        return endCourse == 0 ? 0 : endScore / endCourse;
    }

    public static double getLastScore(Collection<Score> scores) {
        double endScore = 0;
        Date date = new Date(0);

        for (Score score : scores) {
            if (score.getDate() != null && score.getDate().after(date)) {
                date = score.getDate();
                endScore = score.getScore() / score.getCourse();
            }
        }
        return endScore;
    }
}
