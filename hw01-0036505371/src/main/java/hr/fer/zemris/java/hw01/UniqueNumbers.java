package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji služi za modeliranje binarnog stabla i njegovih jednostavnih
 * operacija.
 * 
 * @author Maja Radočaj
 *
 */
public class UniqueNumbers {

	/**
	 * String koji označava kraj rada.
	 */
	private static final String END = "kraj";
	/**
	 * Pomoćna struktura podataka koja modelira jedan čvor stabla. Svaki čvor ima
	 * lijevu granu, desnu i svoju vrijednost. Na lijevu stranu dodaju se čvorovi s
	 * manjom vrijednošću od trenutnog čvora, a na desnu sa većom.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	public static class TreeNode {
		TreeNode left, right;
		int value;
	}

	/**
	 * Glavna metoda. Korisnik dodaje nove elemente stable sve dok ne unese ključnu
	 * riječ "kraj".
	 * 
	 * @param args argumenti naredbenog retka (ne koristimo ih)
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.printf("Unesite broj > ");
			if(sc.hasNextInt()) {
				int number = sc.nextInt();
				if(containsValue(root, number)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					root = addNode(root, number);
					System.out.println("Dodano.");
				}
			} else {
				String line = sc.next();
				if(line.equals(END)) {
					if(root == null) {
						sc.close();
						return;
					}
					break;
				} else {
					System.out.printf("'%s' nije cijeli broj.\n", line);
				}
			}
		}

		sc.close();
		System.out.printf("Ispis od najmanjeg: ");
		write(root, "uzlazno");
		System.out.printf("\nIspis od najvećeg: ");
		write(root, "silazno");
	}

	/**
	 * Metoda za sortiran ispis binarnog stabla. Ovisno o tipu, stablo se ispisuje
	 * uzlazno ili silazno.
	 * 
	 * @param root početni čvor
	 * @param type uzlazno ili silazno
	 */
	private static void write(TreeNode root, String type) {
		if(type.equals("uzlazno")) {
			if(root.left != null) {
				write(root.left, "uzlazno");
			}
			System.out.printf("%d ", root.value);
			if(root.right != null) {
				write(root.right, "uzlazno");
			}
		} else {
			if(root.right != null) {
				write(root.right, "silazno");
			}
			System.out.printf("%d ", root.value);
			if(root.left != null) {
				write(root.left, "silazno");
			}
		}

	}

	/**
	 * Metoda koja provjerava postoji li u stablu neki određeni element.
	 * 
	 * @param root   početni čvor stabla
	 * @param number provjeravana vrijednost
	 * @return true ako se number nalazi u stablu, false ako ne
	 */
	public static boolean containsValue(TreeNode root, int number) {
		if(root == null) return false;
		if(root.value == number) return true;
		if(root.value > number) {
			return containsValue(root.left, number);
		}
		if(root.value < number) {
			return containsValue(root.right, number);
		}
		return false;
	}

	/**
	 * Metoda koja vraća veličinu stabla (broj elemenata u njemu).
	 * 
	 * @param root početni čvor
	 * @return broj elemenata u stablu
	 */
	public static int treeSize(TreeNode root) {
		int size = 0;
		if(root == null)
			return 0;
		if(root.left == null && root.right == null) {
			return size + 1;
		}
		return treeSize(root.left) + treeSize(root.right) + 1;
	}

	/**
	 * Metoda koja dodaje novi element u stablo.
	 * 
	 * @param root     početni čvor
	 * @param newValue vrijednost novog elementa
	 * @return početni čvor
	 */
	public static TreeNode addNode(TreeNode root, int newValue) {
		if(root == null) {
			root = new TreeNode();
			root.value = newValue;
			root.left = null;
			root.right = null;
			return root;
		} else {
			if(root.value > newValue) {
				root.left = addNode(root.left, newValue);
			}
			if(root.value < newValue) {
				root.right = addNode(root.right, newValue);
			}
		}
		
		return root;
	}

}
