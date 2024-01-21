//Programmer: John Le
package view;

import java.io.FileNotFoundException;

import model.Boggle;

public class BoggleConsole {

	public static void main(String[] args) throws FileNotFoundException {

		Boggle theGame = new Boggle();

		theGame.start();
	}
}
