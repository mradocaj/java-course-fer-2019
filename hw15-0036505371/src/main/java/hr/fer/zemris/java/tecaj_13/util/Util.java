package hr.fer.zemris.java.tecaj_13.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Ovaj razred nudi pomoćne statičke metode za pretvorbu broja iz heksadekadskog zapisa u bajtni zapis i obratno.
 * 
 * @author Maja Radočaj
 *
 */
public class Util {

	/**
	 * Metoda koja prima String koji predstavlja heksadekadski zapis nekih podataka.
	 * Zapis se pretvara u niz bajtova koji se na kraju vraća u obliku polja bajtova.
	 * Ukoliko primljeni argument nije valjanog oblika, baca se iznimka.
	 * 
	 * @param keyText heksadekadski zapis
	 * @return bajtni zapis
	 * @throws IllegalArgumentException ako primljeni argument nije valjani heksadekadski zapis
	 * @throws NullPointerException ako je predani argument <code>null</code>
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText);
		String text = keyText.trim().toLowerCase();
		if(text.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid argument. Length of given argument shouldn't be odd.");
		}
		
		byte[] result = new byte[text.length() / 2];
		char[] characters = text.toCharArray();
		
		for(int i = 0; i < characters.length; i = i + 2) {
			if(!isValidHex(characters[i], characters[i + 1])) {
				throw new IllegalArgumentException("Invalid hex number given.");
			}
			result[i / 2] = (byte) ((Character.digit(characters[i], 16) << 4) + Character.digit(characters[i + 1], 16));
		}
		
		return result;
	}

	/**
	 * Metoda koja prima polje bajtova i vraća njegov heksadekadski zapis.
	 * 
	 * @param bytes polje bajtova
	 * @return heksadekadski zapis polja
	 * @throws NullPointerException ako je predani argument <code>null</code>
	 */
	public static String bytetohex(byte[] bytes) {		
		Objects.requireNonNull(bytes);
		String hexDigits = "0123456789abcdef";
		char[] result = new char[bytes.length * 2];
		
		for(int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xFF;
			result[i * 2] = hexDigits.charAt(value >>> 4);
			result[i * 2 + 1] = hexDigits.charAt(value & 0x0F);
		}
		
		return new String(result);
	}
	
	/**
	 * Metoda za kriptiranje lozinke pomoću SHA-1.
	 * 
	 * @param password lozinka
	 * @return digest kriptirana lozinka
	 */
	public static String cryptPassword(String password) {
		if(password.equals("")) return "";
		
		byte[] buffer = password.getBytes();
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		sha.update(buffer, 0, buffer.length);
	
		byte[] digest = sha.digest();
		
		return bytetohex(digest);
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li trenutni broj validni heksadekadski zapis jednog bajta.
	 * 
	 * @param first prva znamenka broja
	 * @param second druga znamenka broja
	 * @return <code>true</code> ako su znamenke validne (0-9 i A-F), <code>false</code> ako nisu
	 */
	private static boolean isValidHex(char first, char second) {
		if(!Character.isDigit(first) && !(first >= 'a' && first <= 'f')) return false;
		if(!Character.isDigit(second) && !(second >= 'a' && second <= 'f')) return false;
		return true;
	}
	
}
