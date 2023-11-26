package Main.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizGameController {
    @FXML
    private JFXButton startGame;

    @FXML
    private JFXButton startQuiz;

    @FXML
    public void initialize() {
        startGame.setOnMouseClicked(event -> loadPage("/Main/Interface/game.fxml"));
        startQuiz.setOnMouseClicked(event -> loadPage("/Main/Interface/Quiz/startquiz.fxml"));
    }

    private void loadPage(String fxmlFile) {
        try {
            // Get the current stage
            Stage stage = (Stage) startGame.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}