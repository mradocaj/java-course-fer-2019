package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import static hr.fer.zemris.java.hw06.crypto.Util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program koji omogućava korisniku da kriptira ili dekriptira određeni
 * dokument, kao i da provjeri pomoću SHA-256 file degest je li dokument sigurno
 * stigao do korisnika. Program se pokreće uz argumente naredbenog retka.
 * Podržane su tri naredbe: <code>checksha</code>, <code>encrypt</code> i
 * <code>decrypt</code>. Uz <code>checksha</code> se očekuje još jedan argument,
 * a uz <code>encrypt</code> ili <code>decrypt</code> još dva.
 * 
 * @author Maja Radočaj
 *
 */
public class Crypto {

	/**
	 * Očekivana duljina ključa za kriptiranje i dekriptiranje.
	 */
	private static int LENGTH = 32;
	/**
	 * Naredba za checksha.
	 */
	private static final String CHECKSHA = "checksha";
	/**
	 * Naredba za kriptiranje.
	 */
	private static final String ENCRYPT = "encrypt";
	/**
	 * Naredba za dekriptiranje.
	 */
	private static final String DECRYPT = "decrypt";

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("One command and one or two arguments expected.");
			return;
		}

		if(args[0].equals(CHECKSHA)) {
			if(args.length != 2) {
				System.out.println("One more argument expected after checksha.");
				return;
			}

			checksha(args[1]);

		} else if(args[0].equals(ENCRYPT) || args[0].equals(DECRYPT)) {
			if(args.length != 3) {
				System.out.println("Two more arguments expected after command.");
				return;
			}

			encryptOrDecrypt(args[0], args[1], args[2]);

		} else {
			System.out.println("Invalid command.");
			return;
		}
	}

	/**
	 * Pomoćna metoda koja odrađuje kriptiranje ili dekriptiranje te ispisuje
	 * rezultat.
	 * 
	 * @param command  naredba (encrypt ili decrypt)
	 * @param original path originalnog dokumenta (kojeg treba
	 *                 kriptirati/dekriptirati)
	 * @param result   path dokumenta koji nastaje kao rezultat
	 *                 kriptiranja/dekriptiranja
	 */
	private static void encryptOrDecrypt(String command, String original, String result) {
		Path originalPath = Paths.get(original);
		Path resultPath = Paths.get(result);

		Scanner sc = new Scanner(System.in);
		System.out.printf("Please provide password as hex-encoded "
				+ "text (16 bytes, i.e. 32 hex-digits):\n> ");
		String keyText = sc.next().trim();
		
		System.out.printf("Please provide initialization vector as "
				+ "hex-encoded text (32 hex-digits):\n> ");
		String ivText = sc.next().trim();
		sc.close();

		if(keyText.length() != LENGTH || ivText.length() != LENGTH) {
			System.out.println("Invalid key or IV.");
			System.exit(-1);
		}

		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(command.equals(ENCRYPT) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, 
					keySpec, paramSpec);
			makeFile(cipher, originalPath, resultPath);
			System.out.println(String.format("%s completed. Generated file %s based on file %s.\n",
					command.equals(ENCRYPT) ? "Encryption" : "Decryption", result, original));

		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException ex) {
			System.out.println("Error.");
			System.exit(-1);
		}
	}

	/**
	 * Pomoćna metoda koja stvara novi dokument kao rezultat kriptiranja ili
	 * dekriptiranja.
	 * 
	 * @param cipher       alat za kriptiranje/dekriptiranje
	 * @param originalPath path dokumenta kojeg treba kriptirati/dekriptirati
	 * @param resultPath   path dokumenta koji nastaje kriptiranjem/dekriptiranjem
	 */
	private static void makeFile(Cipher cipher, Path originalPath, Path resultPath) {
		try(InputStream stream = Files.newInputStream(originalPath);
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(resultPath))) {
			byte[] buffer = new byte[4096];

			while(true) {
				int r = stream.read(buffer);
				if(r < 1)
					break;
				os.write(cipher.update(buffer, 0, r));
			}

			os.write(cipher.doFinal());
		} catch(IOException | BadPaddingException | IllegalBlockSizeException ex) {
			System.out.println("Error.");
			System.exit(-1);
		}

	}

	/**
	 * Pomoćna metoda koja provjerava je li neki dokument preuzet bez greške i
	 * izmjena.
	 * 
	 * @param argument String reprezentacija patha dokumenta kojeg treba provjeriti
	 */
	private static void checksha(String argument) {
		Path path = Paths.get(argument);
		System.out.printf("Please provide expected sha-256 digest for " + argument + ":\n> ");

		Scanner sc = new Scanner(System.in);
		String expected = sc.next();
		sc.close();

		try {
			byte[] result = digestResult(path);
			if(bytetohex(result).equals(expected)) {
				System.out.println("Digesting completed. Digest of " + argument + " matches "
						+ "expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + argument + " does not match the "
						+ "expected digest. Digest was: " + bytetohex(result));
			}
		} catch(NoSuchAlgorithmException e) {
			System.out.println("Error.");
			System.exit(-1);
		}
	}

	/**
	 * Pomoćna metoda koja računa i vraća 256-bitovni digest koji je karakterističan
	 * za neki dokument.
	 * 
	 * @param path path do dokumenta čiji digest treba računati
	 * @return rezultat digesta
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] digestResult(Path path) throws NoSuchAlgorithmException {
		try(InputStream stream = Files.newInputStream(path)) {
			byte[] buffer = new byte[4096];
			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			while(true) {
				int r = stream.read(buffer);
				if(r < 1)
					break;
				sha.update(buffer, 0, r);
			}

			return sha.digest();
		} catch(IOException ex) {
			System.out.println("Cannot resolve path.");
			System.exit(-1);
		}

		return null;
	}

}
