package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import questionManager.QuestionDatabase;

public class QuizWindowController {
	private RadioButton selectedRadioButton = null;
	private Stage stage;
	private String toogleGroupValue = null;
	private StartController startController = new StartController();
	private QuestionDatabase questionDatabase = new QuestionDatabase();
	@FXML private ToggleGroup group; 
	@FXML private Label question;
	@FXML private RadioButton a1, a2, a3;
	@FXML private Button nextButton;
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
	public StartController getStartController() {
		return startController;
		}
	public QuestionDatabase getQuestionDatabase() {
		return questionDatabase;
		}
    public void initialize() {
    		nextButton.setDisable(true); 
    		question.setText(getStartController().getPytanie()+". "+getQuestionDatabase().getQuestion(getStartController().getPytanie(), getStartController().getQuizname()));
    		question.setId("question-text");
    		a1.setText(getQuestionDatabase().getAnswer1(getStartController().getPytanie(), getStartController().getQuizname()));
    		a2.setText(getQuestionDatabase().getAnswer2(getStartController().getPytanie(), getStartController().getQuizname()));
    		a3.setText(getQuestionDatabase().getAnswer3(getStartController().getPytanie(), getStartController().getQuizname()));
    	
    }
    public void click(ActionEvent actionEvent) throws IOException {
    	nextButton.setDisable(false);
    }
    public void pressNext(ActionEvent actionEvent) throws IOException {
    	selectedRadioButton = null;
    	toogleGroupValue = null;
    	selectedRadioButton = (RadioButton) group.getSelectedToggle();
    	toogleGroupValue = selectedRadioButton.getText();
    	if (toogleGroupValue == getQuestionDatabase().getCorrect(getStartController().getPytanie(), getStartController().getQuizname())) {
    		getStartController().addScore();
    		
    	}
    	getStartController().addPytanie();
    	if (getStartController().getPytanie() > getQuestionDatabase().getQuestionNumber(getStartController().getQuizname())) {
    		getStartController().setPytanie(getStartController().getPytanie()-1);
    		Stage stage = (Stage) nextButton.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreView.fxml"));
    		Parent root = null;
    		try {
    			root = loader.load();
    		} catch (IOException e) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setContentText("Input/output exception: " + e.getMessage());
    			alert.show();
    		}
    		stage.setTitle("Quiz");
    		stage.setScene(new Scene(root, 615, 525));
    		stage.show();
    		stage.centerOnScreen();
    		ScoreViewController sc = loader.getController();
    	} else {
    		Stage stage = (Stage) nextButton.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizWindowView.fxml"));
    		Parent root = null;
    		try {
    			root = loader.load();
    		} catch (IOException e) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setContentText("Input/output exception: " + e.getMessage());
    			alert.show();
    		}
    		stage.setTitle("Quiz");
    		stage.setScene(new Scene(root, 350, 300));
    		stage.show();
    		stage.centerOnScreen();
    		QuizWindowController sc = loader.getController();
    	}

    }
    
}
