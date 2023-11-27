package Main.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;

public class GameController {
    private HashMap<String, String> dictionary = new HashMap<>();
    private ArrayList<String> words = new ArrayList<>();
    private Text timeValue = new Text();
    private Button startButton = new Button("Start");
    private Button stopButton = new Button("Stop");
    private Text result = new Text();
    private int seconds = 0;
    private int winCount = 0;
    private Button firstCard = null;
    private String firstCardValue = null;
    private boolean canOpen = true;

    @FXML
    private Button firstcard, secondcard, thirdcard, fourthcard, fifthcard, sixthcard, seventhcard, eighthcard, ninethcard, tenthcard, eleventhcard, twelfthcard, thirteenth, fourteenthcard, fifteenthcard, sixteenthcard;

    @FXML
    private Label timerLabel;



    /*public class CheckFilePermissions {
        public static void main(String[] args) {
            File file = new File("/Data/Dictionary.txt");

            if (file.exists()) {
                System.out.println("Can Execute: " + file.canExecute());
                System.out.println("Can Read: " + file.canRead());
                System.out.println("Can Write: " + file.canWrite());
            } else {
                System.out.println("File does not exist.");
            }
        }
    }*/

    public GameController() {
        // Load the dictionary
        try {
            // Use a relative path to the file
            File file = new File("C:\\Users\\USER\\work_IU\\BTL_OOP_\\src\\main\\resources\\Data\\Dictionary.txt");
            if (!file.exists()) {
                System.out.println("File does not exist: " + file.getAbsolutePath());
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" : ");
                dictionary.put(parts[0], parts[1]);
                words.add(parts[0]);
                words.add(parts[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initializer();
    }

    private void initializer() {
        // Reset values
        seconds = 0;
        winCount = 0;
        generateRandom();
        matrixGenerator();
    }

    private void generateRandom() {
        // Generate a random set of card values based on the items array
        Collections.shuffle(words);
        words = new ArrayList<>(words.subList(0, 16));
    }

    private void matrixGenerator() {
        // Assign words to buttons and add event handlers
        firstcard.setText(words.get(0));
        firstcard.setOnMouseClicked(event -> cardClicked(firstcard, words.get(0)));
        secondcard.setText(words.get(0));
        secondcard.setOnMouseClicked(event -> cardClicked(secondcard, words.get(0)));
        thirdcard.setText(words.get(0));
        thirdcard.setOnMouseClicked(event -> cardClicked(thirdcard, words.get(0)));
        fourthcard.setText(words.get(0));
        fourthcard.setOnMouseClicked(event -> cardClicked(fourthcard, words.get(0)));
        fifthcard.setText(words.get(0));
        fifthcard.setOnMouseClicked(event -> cardClicked(fifthcard, words.get(0)));
        sixthcard.setText(words.get(0));
        sixthcard.setOnMouseClicked(event -> cardClicked(sixthcard, words.get(0)));
        seventhcard.setText(words.get(0));
        seventhcard.setOnMouseClicked(event -> cardClicked(seventhcard, words.get(0)));
        eighthcard.setText(words.get(0));
        eighthcard.setOnMouseClicked(event -> cardClicked(eighthcard, words.get(0)));
        ninethcard.setText(words.get(0));
        ninethcard.setOnMouseClicked(event -> cardClicked(ninethcard, words.get(0)));
        tenthcard.setText(words.get(0));
        tenthcard.setOnMouseClicked(event -> cardClicked(tenthcard, words.get(0)));
        eleventhcard.setText(words.get(0));
        eleventhcard.setOnMouseClicked(event -> cardClicked(eleventhcard, words.get(0)));
        twelfthcard.setText(words.get(0));
        twelfthcard.setOnMouseClicked(event -> cardClicked(twelfthcard, words.get(0)));
        thirteenth.setText(words.get(0));
        thirteenth.setOnMouseClicked(event -> cardClicked(thirteenth, words.get(0)));
        fourteenthcard.setText(words.get(0));
        fourteenthcard.setOnMouseClicked(event -> cardClicked(fourteenthcard, words.get(0)));
        fifteenthcard.setText(words.get(0));
        fifteenthcard.setOnMouseClicked(event -> cardClicked(fifteenthcard, words.get(0)));
        sixteenthcard.setText(words.get(0));
        sixteenthcard.setOnMouseClicked(event -> cardClicked(sixteenthcard, words.get(0)));
    }

    private void cardClicked(Button card, String word) {
        if (!canOpen || card.getStyleClass().contains("matched")) {
            return;
        }

        card.getStyleClass().add("flipped");

        if (firstCard == null) {
            firstCard = card;
            firstCardValue = word;
        } else {
            if (dictionary.get(word).equals(firstCardValue) || dictionary.get(firstCardValue).equals(word)) {
                firstCard.getStyleClass().add("matched");
                card.getStyleClass().add("matched");
                firstCard = null;
                winCount++;

                if (winCount == 8) {
                    result.setText("You Won\nMoves: " + winCount);
                    //stopGame();
                }
            } else {
                canOpen = false;
                Button tempFirst = firstCard;
                firstCard = null;

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    tempFirst.getStyleClass().remove("flipped");
                    card.getStyleClass().remove("flipped");
                    canOpen = true;
                }).start();
            }
        }
    }
}
