package hr.fer.zemris.java.hw01;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testna klasa za klasu UniqueNumbers.
 * 
 * @author Maja Radočaj
 *
 */
class UniqueNumbersTest {

	@Test
	public void testNodeAdding() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 13);
		assertNotNull(glava);
		assertEquals(glava.value, 13);
		assertNull(glava.left);
		assertNull(glava.right);
	}
	
	@Test
	public void testTreeSize() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 46);
		glava = UniqueNumbers.addNode(glava, 73);
		glava = UniqueNumbers.addNode(glava, 12);
		glava = UniqueNumbers.addNode(glava, 46);
		glava = UniqueNumbers.addNode(glava, 59);
		
		assertEquals(4, UniqueNumbers.treeSize(glava));
	}
	
	@Test
	public void testContains() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 46);
		glava = UniqueNumbers.addNode(glava, 12);
		
		assertTrue(UniqueNumbers.containsValue(glava, 46));
		assertTrue(UniqueNumbers.containsValue(glava, 12));
		assertFalse(UniqueNumbers.containsValue(glava, 64));
	}

	
}
