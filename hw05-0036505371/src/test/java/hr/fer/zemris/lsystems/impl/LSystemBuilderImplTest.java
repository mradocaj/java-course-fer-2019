package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystemBuilder;

class LSystemBuilderImplTest {

	LSystemBuilder builder;
	
	@BeforeEach
	void init() {
		 builder = new LSystemBuilderImpl();
	}
	
	@Test
	void testGenerateDepth0() {
		builder = builder.setAxiom("F")
						.registerProduction('F', "F+F--F+F");
		assertEquals("F", builder.build().generate(0));
	}
	
	@Test
	void testGenerateDepth1() {
		builder = builder.setAxiom("F")
						.registerProduction('F', "F+F--F+F");
		assertEquals("F+F--F+F", builder.build().generate(1));
	}
	
	@Test
	void testGenerateDepth2() {
		builder = builder.setAxiom("F")
						.registerProduction('F', "F+F--F+F");
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", builder.build().generate(2));
	}
	
	@Test
	void testGeneratePlant1Depth2() {
		builder = builder.setAxiom("GF")
						.registerProduction('F', "F[+F]F[-F]F");
		assertEquals("GF[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F", builder.build().generate(2));
	}

	@Test
	void testGeneratePlant2Depth1() {
		builder = builder.setAxiom("GF")
						.registerProduction('F', "FF+[+F-F-F]-[-F+F+F]");
		assertEquals("GFF+[+F-F-F]-[-F+F+F]", builder.build().generate(1));
	}
	
	@Test
	void testConfigureFromText() {
		String[] data = new String[] {
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};	
		
		assertDoesNotThrow(() -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongCommand() {
		String[] data = new String[] {"command draw"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongAngle() {
		String[] data = new String[] {"angle ž"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongAngle2() {
		String[] data = new String[] {"angle 8 9"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongOrigin() {
		String[] data = new String[] {"origin 8 9 9"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongUnitLength() {
		String[] data = new String[] {"unitLength g"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongUnitLengthDegreeScaler() {
		String[] data = new String[] {"unitLengthDegreeScaler 8/4/3"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongAxiom() {
		String[] data = new String[] {"axiom"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
	
	@Test
	void testConfigureFromTextWrongProduction() {
		String[] data = new String[] {"production FF F+F+F"};	
		assertThrows(IllegalArgumentException.class, () -> builder.configureFromText(data));
	}
}

