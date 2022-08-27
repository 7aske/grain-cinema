import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * Utility password encode that matches the default PasswordEncoder in Grain
 * framework.
 */
public class Encode {
	public static void main(String... args) throws NoSuchAlgorithmException {
		if (args.length == 0)  System.exit(1);
		Encode encoder = new Encode();
		System.out.println(encoder.encode(args[0]));
	}

	private final MessageDigest digest = MessageDigest.getInstance("SHA-256");

	public Encode() throws NoSuchAlgorithmException {
	}

	public String encode(String password) throws NoSuchAlgorithmException  {
		byte[] encodedHash = this.digest.digest(password.getBytes(StandardCharsets.UTF_8));
		return new String(Base64.getEncoder().encode(encodedHash));
	}

	public boolean matches(String password, String hashed) throws NoSuchAlgorithmException {
		return hashed.equals(this.encode(password));
	}
}

