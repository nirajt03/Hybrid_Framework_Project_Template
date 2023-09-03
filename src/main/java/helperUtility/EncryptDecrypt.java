package helperUtility;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import encryptionUtilities.AESEncryptDecryptUtil;
import encryptionUtilities.AESEncryptDecryptUtil.KeySize;

/**
 * Encrypt Decrypt
 * @author Niraj Tiwari
 */
public class EncryptDecrypt {

    private static SecretKey key;
	private static IvParameterSpec ivParameterSpec = AESEncryptDecryptUtil.generateIv();
	private static String algorithm = "AES/CBC/PKCS5Padding";
	
	static {
		 try {
			key = AESEncryptDecryptUtil.generateKey(KeySize.OneTwentyEight);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Failed to generate Secret Key : "+ e.getMessage());
		}
	}
	
	/**
	 * encrypt String
	 * @param textToBeEncrypted
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encryptString(String textToBeEncrypted) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		
		return AESEncryptDecryptUtil.encrypt(algorithm, textToBeEncrypted, key, ivParameterSpec);
	}
	
	/**
	 * decrypt String
	 * @param textToBeDecrypted
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decryptString(String textToBeDecrypted) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		
		return AESEncryptDecryptUtil.decrypt(algorithm, textToBeDecrypted, key, ivParameterSpec);
	}
	

}
