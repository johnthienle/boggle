package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.DiceTray;

/**
 * Grader tests for BoggleTray. Just tests word searches. We are not seeking
 * words in a dictionary, just strings for now.
 * 
 * @author mercer, michaels, John Le
 * @version 1.2
 */

public class DiceTrayTest {

	private char[][] longWords = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
			{ 'S', 'S', 'E', 'N' } };

	private char[][] longWords2 = { { 'A', 'Q', 'I', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
			{ 'S', 'S', 'E', 'N' } };

	private DiceTray tray = new DiceTray(longWords);
	private DiceTray tray2 = new DiceTray(longWords2);

	@Test
	public void testForSmallStringsNotRealWords() {
		// We are not looking for words in a dictionary now, just strings.
		//
		// searchBoard must return false for strings < length() of 3
		// asserts can take a string argument that prints when the assert fails.
		//
		assertFalse(tray.found(""));
		assertFalse(tray.found("TS")); // Not a word, but the sequence exists
		assertTrue(tray.found("TMI"));
		assertTrue(tray.found("aBs")); // Case insensitive
		assertTrue(tray.found("AbS"));
	}

	@Test
	public void testFound2() {
		assertTrue(tray.found("sent"));
		assertTrue(tray.found("SENT"));
		assertTrue(tray.found("minded"));
		assertTrue(tray.found("teen"));
		assertTrue(tray.found("dibtd"));
	}

	@Test
	public void testForLongerStrings() {
		assertTrue(tray.found("NTMINDED")); // Not a word, but the sequence exists
		assertTrue(tray.found("ABSENTMINDEDNESS"));
	}

	// Add many many more test cases
	@Test
	public void testFound3() {
		assertTrue(tray.found("SSeN"));
		assertTrue(tray.found("AmNSDTES"));
		assertFalse(tray.found("AmNSDTESEnDNEe"));
		assertTrue(tray.found("ABSEN"));
		assertTrue(tray.found("meD"));
		assertTrue(tray.found("IMTDS"));
		assertTrue(tray.found("NDSS"));
		assertFalse(tray.found("MEM"));
		assertTrue(tray.found("SDS"));
	}

	@Test
	public void testQ() {
		assertTrue(tray2.found("Quit"));

	}
}