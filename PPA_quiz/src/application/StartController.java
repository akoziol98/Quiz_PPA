package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class StartController {
	private static int pytanie = 1, score = 0, trial = 1;
	private static String quizname = null, playerNick = null;
	private static Stage stage;
	@FXML private Label explain, welcome;
	@FXML private Button scoreButton;
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public String getPlayerNick() {
		return playerNick;
	}

	
    public void setPlayerNick(String playerNick) {
		StartController.playerNick = playerNick;
	}

	public int getTrial() {
		return trial;
	}

	public void setTrial(int trial) {
		StartController.trial = trial;
	}

    public int getPytanie() {
    	return pytanie;
    }
    

    public void setPytanie(int pytanie) {
		StartController.pytanie = pytanie;
	}

	public void addPytanie() {
    	pytanie++;
    }
    public void addScore() {
    	score++;
    }
    public void addTrial() {
    	trial++;
    }
    public int getScore() {
    	return score;
    }
    
    public void setScore(int score) {
		StartController.score = score;
	}

	public String getQuizname() {
		return quizname;
	}

	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}
	public void initialize() {
    	welcome.setText("Kwestionariusze");
    	welcome.setId("welcome-text");
    	explain.setText("Wybierz jeden z dostêpnych quizów");
    }
	
	public void goToQuiz1(ActionEvent actionEvent) {
    	setQuizname("Historia");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("QuizWindowView.fxml")), 350.0, 300.0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goToNeuro(ActionEvent actionEvent) {
    	setQuizname("Neuropsychologia");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("QuizWindowView.fxml")), 350.0, 300.0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
