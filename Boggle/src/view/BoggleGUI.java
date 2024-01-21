//Programmer: John Le

package view;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Boggle;
import model.DiceTray;

public class BoggleGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Boggle boggle = new Boggle();
	private Boolean gameEnd = false;
	private String resultString = "";

	// text boxes
	private TextArea diceTrayField = new TextArea();
	private TextArea attemptsField = new TextArea("ATTEMPTS");
	private TextArea resultsField = new TextArea("RESULTS");

	// buttons
	private Button newGameButton = new Button("New Game");
	private Button endGameButton = new Button("Stop Game");

	// panes
	private GridPane diceTrayPane = new GridPane();
	private BorderPane attemptPane = new BorderPane();
	private BorderPane resultsPane = new BorderPane();
	private HBox buttons = new HBox();

	// labels
	private Label attemptLabel = new Label("Enter Attempts Below:");
	private Label resultLabel = new Label("Results:");

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(diceTrayPane, 1275, 500);
		initializeGame();
		layoutGUI();
		Font diceTrayFont = Font.font("Courier New", FontWeight.BOLD, 45);
		Font attemptsFont = Font.font("Chalkboard", FontWeight.SEMI_BOLD, 20);
		diceTrayField.setFont(diceTrayFont);
		attemptsField.setFont(attemptsFont);
		stage.setScene(scene);
		stage.setTitle("Boggle");

		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeGame();
			}
		});

		endGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					endGame();
				} catch (FileNotFoundException e) {
					;
				}
			}
		});

		stage.show();
	}

	private void layoutGUI() {
		diceTrayPane.setHgap(30);
		diceTrayPane.setVgap(30);

		// dicetray section
		buttons.setSpacing(20);
		buttons.getChildren().addAll(newGameButton, endGameButton);
		diceTrayPane.add(buttons, 1, 1);
		diceTrayField.setPrefHeight(385);
		diceTrayField.setPrefWidth(385);
		diceTrayPane.add(diceTrayField, 1, 2);

		// attempts section
		attemptPane.setLeft(attemptLabel);
		diceTrayPane.add(attemptPane, 2, 1);
		attemptsField.setPrefHeight(385);
		attemptsField.setPrefWidth(385);
		diceTrayPane.add(attemptsField, 2, 2);

		// results section
		resultsPane.setLeft(resultLabel);
		diceTrayPane.add(resultsPane, 3, 1);
		resultsField.setPrefHeight(385);
		resultsField.setPrefWidth(385);
		diceTrayPane.add(resultsField, 3, 2);

	}

	private void initializeGame() {
		DiceTray game = new DiceTray();
		boggle.diceTray = game;
		boggle.diceTray.randomize();
		boggle.setScore(0);
		boggle.rightWords.clear();
		boggle.wrongWords.clear();
		gameEnd = false;
		resultString = "";
		resultsField.setWrapText(true);
		diceTrayField.setText(boggle.diceTray.toStringGUI());
		attemptsField.setText("");
		resultsField.setText("");
		diceTrayField.setEditable(false);
		attemptsField.setEditable(true);
		resultsField.setEditable(false);
	}

	@SuppressWarnings("unused")
	private void endGame() throws FileNotFoundException {
		if (!gameEnd) {
			gameEnd = true;
			attemptsField.setEditable(false);
			String[] guesses = null;
			String attempts = attemptsField.getText();
			guesses = attempts.split("\\s+");
			for (String words : guesses) {
				boggle.guessWord(words);
			}
			boggle.calculateScore(boggle.rightWords);
			Scanner dictionary = new Scanner(new File("BoggleWords.txt"));
			List<String> wordList = new ArrayList<String>();
			for (int i = 0; dictionary.hasNextLine() != false; i++) {
				String word = dictionary.nextLine();
				if (boggle.diceTray.found(word)) {
					if (!boggle.rightWords.contains(word)) {
						wordList.add(word);
					}
				}
			}

			int wordCount = 0;
			for (String words : wordList) {
				resultString = resultString + words.toLowerCase() + " ";
			}
			resultsField.setText("Your Score: " + boggle.getScore() + "\n\n" + "Words you found: \n"
					+ boggle.rightWords() + "\n\n" + "Incorrect Words: \n" + boggle.wrongWords() + "\n\n"
					+ "You could have found " + wordList.size() + " more words:" + "\n" + resultString);
		}
	}


}
