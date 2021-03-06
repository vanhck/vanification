package de.vanhck;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import de.vanhck.data.Score;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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

    public static Label makeBold(Label label) {
        return new Label("<b>" + label.getValue() + "</b>", ContentMode.HTML);
    }

    public static double getEndScore(Collection<Score> scores) {
        double endScore = 0;

        for (Score score : scores) {
            endScore+=score.getScore();
        }
        int dayCount = getDayCount(scores);
        return endScore/ (dayCount*100);
    }

    public static double getScoreFromTime(Collection<Score> scores, Date from) {
        Collection<Score> scoresInTime = new HashSet<>();

        for (Score score : scores) {
            if (score.getDate() != null && score.getDate().after(from)) {
                scoresInTime.add(score);
            }
        }
        return getEndScore(scoresInTime);
    }

    public static double getLastScore(Collection<Score> scores) {
        Score endScore = null;
        Date date = new Date(0);

        for (Score score : scores) {
            if (score.getDate() != null && score.getDate().after(date)) {
                date = score.getDate();
                endScore = score;
            }
        }
        Collection<Score> scores1 = new HashSet<>();
        scores1.add(endScore);
        return getEndScore(scores1);
    }

    public static int getDayCount(Collection<Score> scores) {
        return (int) scores.stream().map(score -> score.getDate().getMonth()*30+score.getDate().getDate()).distinct().count();
    }
}

