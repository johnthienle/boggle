//Programmer: John Le

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import model.Boggle;

class BoggleTest {

	private char[][] longWords = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
			{ 'S', 'S', 'E', 'N' } };
	private char[][] longWords2 = { { 'S', 'E', 'N', 'D' }, { 'Z', 'U', 'P', 'S' }, { 'Z', 'L', 'E', 'D' },
			{ 'S', 'S', 'E', 'N' } };

	Boggle theGame = new Boggle();
	Boggle theGame2 = new Boggle();
	Boggle theGame3 = new Boggle();

	@Test
	void testScore() throws FileNotFoundException {
		theGame2.setDiceTray(longWords);
		theGame2.guessWord("Absent");
		theGame2.guessWord("Sent");
		theGame2.guessWord("Abs");
		theGame2.guessWord("Absentmindedness");
		theGame2.guessWord("PoPPY");
		theGame2.calculateScore(theGame2.getRightWords());
		assertEquals(16, theGame2.getScore());
	}

	@Test
	void testScore2() throws FileNotFoundException {
		theGame3.setDiceTray(longWords2);
		theGame3.guessWord("puzzled");
		theGame3.guessWord("sends");
		theGame3.calculateScore(theGame3.getRightWords());
		assertEquals(7, theGame3.getScore());
	}

	@Test
	void wholeGameTest() throws FileNotFoundException {
		theGame.start();
	}

}
