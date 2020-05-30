package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Razred koji modelira čvor sintaksnog stabla pri parsiranju.
 * Svaki čvor ima metodu za dodavanje čvorova djece, vraćanje broja te dohvaćanja djece.
 * Omogućuje parsiranje u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public abstract class Node {
	
	/**
	 * Kolekcija u koju se spremaju djeca čvorovi.
	 */
	private ArrayIndexedCollection children;

	/**
	 * Metoda za dodavanje novog čvora među djecu.
	 * Ako čvor još nema nijedno dijete, kolekcija se inicijalizira.
	 * Ukoliko se pokuša dodati null vrijednost, baca se {@link NullPointerException}.
	 * 
	 * @param child dijete kojeg treba dodati
	 * @throws NullPointerException ako se pokuša dodati null vrijednost
	 */
	public void addChildNode(Node child) {
		if(children == null) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Metoda koja vraća broj djece.
	 *
	 * @return broj djece
	 */
	public int numberOfChildren() {
		if(children == null) return 0;
		return children.size();
	}
	
	/**
	 * Metoda koja vraća dijete s određenog indeksa.
	 * Ako se pokuša pristupiti indeksu kojeg nema, baca se {@link IndexOutOfBoundsException}.
	 * Ako još nema nije nijednog djeteta, baca se {@link NullPointerException}.
	 * 
	 * @param index indeks djeteta kojeg želimo dohvatiti
	 * @return dijete
	 * @throws IndexOutOfBoundsException ako je indeks van raspona
	 * @throws NullPointerException ako još nema nijednog djeteta.
	 */
	public Node getChild(int index) {
		Objects.checkIndex(0, children.size());
		return (Node)children.get(index);
	}

	/**
	 * Metoda koja nad danim visitorom poziva metodu za obradu podataka čvora.
	 * 
	 * @param visitor visitor
	 */
	public abstract void accept(INodeVisitor visitor);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else {
			for (int i = 0; i < children.size(); i++) {
				if(!children.get(i).equals(other.getChild(i))) 
					return false;
			}
		}
		return true;
	}
	
	
	
}
