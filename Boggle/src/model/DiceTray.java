package model;
import java.util.*;

/**
 * Model the tray of dice in the game Boggle. A DiceTray can be constructed with
 * a 4x4 array of characters for testing.
 *
 * @author John Le
 */
public class DiceTray {

	private char[][] theBoard;
	private char[][] trail;
	public static final char tried = '.';
	public static final char partOfWord = '@';
	public static final int sizeOfBoard = 4;
	private String search;
	private int index;
	private char[][] dice = { { 'L', 'R', 'Y', 'T', 'T', 'E' }, { 'A', 'N', 'A', 'E', 'E', 'G' },
			{ 'A', 'F', 'P', 'K', 'F', 'S' }, { 'Y', 'L', 'D', 'E', 'V', 'R' }, { 'V', 'T', 'H', 'R', 'W', 'E' },
			{ 'I', 'D', 'S', 'Y', 'T', 'T' }, { 'X', 'L', 'F', 'R', 'R', 'I' }, { 'Z', 'N', 'R', 'N', 'H', 'L' },
			{ 'E', 'G', 'H', 'W', 'N', 'E' }, { 'O', 'A', 'T', 'T', 'O', 'W' }, { 'H', 'C', 'P', 'O', 'A', 'S' },
			{ 'N', 'M', 'I', 'H', 'U', 'Q' }, { 'S', 'E', 'O', 'T', 'I', 'S' }, { 'M', 'T', 'O', 'I', 'C', 'U' },
			{ 'E', 'N', 'S', 'I', 'E', 'U' }, { 'O', 'B', 'B', 'A', 'O', 'J' } };

	/**
	 * Construct a DiceTray object using a hard-coded 2D array of chars. One is
	 * provided in the given unit test. You can create others.
	 */
	public DiceTray(char[][] newBoard) {
		theBoard = newBoard;
	}

	public DiceTray() {
		char[][] tempBoard = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
				{ 'S', 'S', 'E', 'N' } };
		HashSet<Integer> nonUsedLists = new HashSet<>();
		int row = 0;
		int column = 0;
		for (int i = 0; i < 16; i++) {
			nonUsedLists.add(i);
		}
		for (int i = 0; i < 16; i++) {
			Random rand = new Random();
			int randomDice = rand.nextInt(nonUsedLists.size());
			char randomLetter = dice[randomDice][rand.nextInt((5 - 0) + 1)];
			nonUsedLists.remove(randomDice);
			if (i == 4 || i == 8 || i == 12) {
				row++;
				column = 0;
			}
			tempBoard[row][column] = randomLetter;
			column++;

		}

		theBoard = tempBoard;
	}

	/**
	 * Return true if attempt can be found on the DiceTray following the rules of
	 * Boggle, like a die can only be used once.
	 * 
	 * @param attempt A word that may be in the DiceTray by connecting consecutive
	 *                letters
	 *
	 * @return True if search is found in the DiceTray or false if not. You need not
	 *         check the dictionary now.
	 */
	public boolean found(String attempt) {
		if (attempt.length() < 3) {
			return false;
		}
		search = attempt.toUpperCase().trim();
		boolean found = false;
		for (int row = 0; row < sizeOfBoard; row++) {
			for (int col = 0; col < sizeOfBoard; col++) {
				if (theBoard[row][col] == search.charAt(0)) {
					tempArray();
					found = search(row, col);
					if (found) {
						return true;
					}
				}
			}
		}
		return found;
	}

	private void tempArray() {
		trail = new char[sizeOfBoard][sizeOfBoard];
		for (int row = 0; row < sizeOfBoard; row++) {
			for (int col = 0; col < sizeOfBoard; col++) {
				trail[row][col] = ':';
			}
		}
		index = 0;
	}

	public void randomize() {
		char[][] tempBoard = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
				{ 'S', 'S', 'E', 'N' } };
		HashSet<Integer> nonUsedLists = new HashSet<>();
		int row = 0;
		int column = 0;
		for (int i = 0; i < 16; i++) {
			nonUsedLists.add(i);
		}
		for (int i = 0; i < 16; i++) {
			Random rand = new Random();
			int randomDice = rand.nextInt(nonUsedLists.size());
			char randomLetter = dice[randomDice][rand.nextInt((5 - 0) + 1)];
			nonUsedLists.remove(randomDice);
			if (i == 4 || i == 8 || i == 12) {
				row++;
				column = 0;
			}
			tempBoard[row][column] = randomLetter;
			column++;

		}

		theBoard = tempBoard;
	}

	private boolean search(int row, int col) {
		boolean found = false;
		if (isValid(row, col)) {
			trail[row][col] = tried;
			if (theBoard[row][col] == 'Q') {
				index += 2;
			} else {
				index++;
			}
			if (index >= search.length()) {
				found = true;
			} else {
				found = search(row - 1, col - 1);
				if (!found) {
					found = search(row - 1, col);
				}
				if (!found) {
					found = search(row - 1, col + 1);
				}
				if (!found) {
					found = search(row, col - 1);
				}
				if (!found) {
					found = search(row, col + 1);
				}
				if (!found) {
					found = search(row + 1, col - 1);
				}
				if (!found) {
					found = search(row + 1, col);
				}
				if (!found) {
					found = search(row + 1, col + 1);
				}
				if (!found) {
					trail[row][col] = '.';
					index--;
				}
			}
			if (found) {
				trail[row][col] = theBoard[row][col];
			}
		}
		return found;
	}

	private boolean isValid(int row, int col) {
		return (row >= 0 && row < sizeOfBoard && col >= 0 && col < sizeOfBoard && trail[row][col] != tried
				&& theBoard[row][col] == search.charAt(index));
	}

	public String toString() {
		String temp = "";
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (theBoard[row][col] == ('Q')) {
					temp = temp + "Qu" + " ";
				} else {
					temp = temp + " " + theBoard[row][col] + " ";
				}
			}
			System.out.println(temp);
			temp = "";
		}
		return temp;
	}

	public String toStringGUI() {
		String temp = "";
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (theBoard[row][col] == ('Q')) {
					temp = temp + "Qu" + " ";
				} else {
					temp = temp + " " + theBoard[row][col] + " ";
				}
			}
			temp += "\n";
		}
		return temp;
	}

}