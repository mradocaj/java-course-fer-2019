package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.*;
import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testConstrucor() {
		Vector2D vector = new Vector2D(12.0, 13.0);
		assertNotNull(vector);
	}
	
	@Test
	void testGetX() {
		Vector2D vector = new Vector2D(12.0, 13.0);
		assertEquals(12.0, vector.getX());
	}
	
	@Test
	void testGetY() {
		Vector2D vector = new Vector2D(12.0, 13.0);
		assertEquals(13.0, vector.getY());
	}

	@Test
	void testTranslateNull() {
		Vector2D vector = new Vector2D(5.0, 5.0);
		assertThrows(NullPointerException.class, () -> vector.translate(null));
	}
	
	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(5.0, 5.0);
		Vector2D vector2 = new Vector2D(2.0, 3.0);
		vector.translate(vector2);
		assertEquals(new Vector2D(7.0, 8.0), vector);
	}
	
	@Test
	void testTranslatedNull() {
		Vector2D vector = new Vector2D(5.0, 5.0);
		assertThrows(NullPointerException.class, () -> vector.translated(null));
	}
	
	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(5.0, 5.0);
		Vector2D vector2 = new Vector2D(2.0, 3.0);
		Vector2D vector3 = vector.translated(vector2);
		assertEquals(new Vector2D(7.0, 8.0), vector3);
	}
	
	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		vector.rotate(PI / 4);
		assertEquals(new Vector2D(0.0, sqrt(2)), vector);
	}
	
	@Test
	void testRotateAgain() {
		Vector2D vector = new Vector2D(3.0, 3.0);
		vector.rotate(PI);
		assertEquals(new Vector2D(-3, -3), vector);
	}
	
	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		assertEquals(new Vector2D(-1.0, 1.0), vector.rotated(PI / 2));
	}
	
	@Test
	void testScale() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		vector.scale(5);
		assertEquals(new Vector2D(5, 5), vector);
	}
	
	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		assertEquals(new Vector2D(13, 13), vector.scaled(13));
	}
	
	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		assertEquals(vector, vector.copy());
	}
}
