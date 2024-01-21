//Programmer: John Le

package model;

import java.io.*;
import java.util.*;

public class Boggle {

	public DiceTray diceTray;
	public HashSet<String> rightWords = new HashSet<>();
	public HashSet<String> wrongWords = new HashSet<>();
	int totalScore;

	@SuppressWarnings("unused")
	public void start() throws FileNotFoundException {
		DiceTray game = new DiceTray();
		diceTray = game;
		String blankLine = "\n";
		System.out.println("Play one game of boggle " + blankLine);
		System.out.println(diceTray.toString());
		System.out.println("Enter words or ZZ to quit:" + blankLine);
		String[] guesses = null;
		totalScore = 0;
		Scanner input = new Scanner(System.in);
		while (guesses == null || !guesses[0].equals("ZZ")) {
			String guess = input.nextLine();
			guesses = guess.split(" ");
			for (String words : guesses) {
				if (guesses[0].equals("ZZ")) {
					;
				} else {
					guessWord(words);
				}
			}
		}
		input.close();
		String temp = "";

		calculateScore(rightWords);

		System.out.println("\nYour Score: " + totalScore);
		System.out.println("\nWords you found:");
		System.out.println("================");
		System.out.println("\n" + rightWords());

		System.out.println("\nIncorrect words:");
		System.out.println("================");
		System.out.println("\n" + wrongWords());

		Scanner dictionary = new Scanner(new File("BoggleWords.txt"));
		List<String> wordList = new ArrayList<String>();
		temp = "";
		for (int i = 0; dictionary.hasNextLine() != false; i++) {
			String word = dictionary.nextLine();
			if (diceTray.found(word)) {
				if (!rightWords.contains(word)) {
					wordList.add(word);
				}
			}
		}

		int wordCount = 0;
		for (String words : wordList) {
			wordCount++;
			if (wordCount == 15) {
				temp = temp + "\n";
				wordCount = 0;
			}
			temp = temp + words.toLowerCase() + " ";
		}

		System.out.println("\nYou could have found these " + wordList.size() + " words:");
		System.out.println("===========================================================");
		System.out.println("\n" + temp);
	}

	public void setDiceTray(char[][] charList) {
		DiceTray game = new DiceTray(charList);
		diceTray = game;
	}

	public void guessWord(String words) throws FileNotFoundException {
		if (isInWords(words.toLowerCase(), new Scanner(new File("BoggleWords.txt")))) {
			if (diceTray.found(words)) {
				rightWords.add(words.toLowerCase());
			}
		} else {
			wrongWords.add(words);
		}
	}

	public HashSet<String> getRightWords() {
		return rightWords;
	}

	public void setScore(int number) {
		totalScore = number;
	}

	public int getScore() {
		return totalScore;
	}

	public String rightWords() {
		String temp = "";
		for (String words : rightWords) {
			temp = temp + words.toLowerCase() + " ";
		}
		return temp;
	}

	public String wrongWords() {
		String temp = "";
		for (String words : wrongWords) {
			temp = temp + words.toLowerCase() + " ";
		}
		return temp;
	}

	public void calculateScore(HashSet<String> rightWords) {
		for (String words : rightWords) {
			if (words.length() >= 8) {
				totalScore += 11;
			} else if (words.length() == 7) {
				totalScore += 5;
			} else if (words.length() == 6) {
				totalScore += 3;
			} else if (words.length() == 5) {
				totalScore += 2;
			} else if (words.length() == 4 || words.length() == 3) {
				totalScore += 1;
			}
		}

	}

	public boolean isInWords(String word, Scanner dictionary) {
		List<String> wordList = new ArrayList<String>();
		for (int i = 0; dictionary.hasNextLine() != false; i++) {
			wordList.add(dictionary.nextLine());
			if (wordList.get(i).equals(word)) {
				return true;
			}
		}
		return false;
	}

}
