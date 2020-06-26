package application;

import java.io.IOException;
import com.thoughtworks.xstream.XStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import questionManager.PlayerDatabase;
import questionManager.QuestionDatabase;

public class ScoreViewController {
	protected XStream serializer;
	private static Stage stage;
	private static QuestionDatabase questionDatabase = new QuestionDatabase();
	private static PlayerDatabase playerDatabase = new PlayerDatabase();
	private static StartController startController = new StartController();
	@FXML private TableView<Player> tableView;
	@FXML private VBox vbox;
	@FXML private TableColumn trial;
	@FXML private Label result, pytanie;
	@FXML protected TableView<Player> nickTableView;
	@FXML private Button saveButton, retakeButton, exitButton;
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	
	public PlayerDatabase getPlayerDatabase() {
		return playerDatabase;
		}
	public StartController getStartController() {
		return startController;
		}
	public QuestionDatabase getQuestionDatabase() {
		return questionDatabase;
		}

	public Stage getStage() {
        return stage;
    }
    public void initialize() {
    	saveButton.setDisable(true);
    	if (getStartController().getTrial() == 1) {
    		result.setText("Twój wynik to: " + getStartController().getScore()+"/"+getStartController().getPytanie()+" w "+getStartController().getTrial()+" próbie.");
    	}
    	else{
    		result.setText("Twój wynik to: " + getStartController().getScore()+"/"+getStartController().getPytanie()+" w "+getStartController().getTrial()+" próbach.");
    	}
    	result.setId("welcome-text");
    	pytanie.setText("Czy chcesz go zapisaæ?");
    	nickTableView.setId("table-label");
    	nickTableView.getItems().add(new Player("Wpisz nick", 0, 0, null));
    	TableColumn<Player, String> nickColumn = (TableColumn<Player, String>) nickTableView.getColumns().get(0);
        nickColumn.setCellValueFactory(new PropertyValueFactory<>("Nick"));
        nickColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nickColumn.setOnEditCommit(edit -> {
            Player editedNick = nickTableView.getEditingCell().getTableView().getItems().get(nickTableView.getEditingCell().getRow());
            editedNick.setPlayerNick(edit.getNewValue());
            saveButton.setDisable(false);
            
        });
        	
            nickTableView.setEditable(true);
            nickTableView.getSelectionModel().cellSelectionEnabledProperty().setValue(true);
            nickTableView.setOnMouseClicked(click -> {
                if (click.getClickCount() > 1) {
                    editFocusedCell();
                }

            }
           );
            TableColumn<Player, String> column1 = (TableColumn<Player, String>) tableView.getColumns().get(0);
            column1.setCellValueFactory(new PropertyValueFactory<>("nick"));
            column1.setCellFactory(TextFieldTableCell.forTableColumn());

            TableColumn<Player, Integer> column2 = (TableColumn<Player, Integer>) tableView.getColumns().get(1);
            column2.setCellValueFactory(new PropertyValueFactory<>("score"));
            column2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            
            TableColumn<Player, Integer> column3 = (TableColumn<Player, Integer>) tableView.getColumns().get(2);
            column3.setCellValueFactory(new PropertyValueFactory<>("trial"));
            column3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


            TableColumn<Player, String> column4 = (TableColumn<Player, String>) tableView.getColumns().get(3);
            column4.setCellValueFactory(new PropertyValueFactory<>("quizname"));
            column4.setCellFactory(TextFieldTableCell.forTableColumn());

            for (int i = 1; i < (getPlayerDatabase().getPlayerNumber(getStartController().getQuizname())+1); i++) {
            	Player q = new Player(getPlayerDatabase().getNick(getStartController().getQuizname(),i) , getPlayerDatabase().getScore(getStartController().getQuizname(),i)  ,  getPlayerDatabase().getTrial(getStartController().getQuizname(),i)   ,   getPlayerDatabase().getQuizname(getStartController().getQuizname(), i));
            	dodaj(q);
            }
          }
    
    
    private void editFocusedCell() {
        TablePosition<Player, ?> focusedCell = nickTableView.focusModelProperty().get().focusedCellProperty().get();
        nickTableView.edit(focusedCell.getRow(), focusedCell.getTableColumn());
}
    public void retake(ActionEvent actionEvent) throws IOException {
    	getStartController().addTrial();
    	getStartController().setPytanie(1);
    	getStartController().setScore(0);
		Stage stage = (Stage) retakeButton.getScene().getWindow();
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
    public void saveScore(ActionEvent actionEvent) {
    	Player p = new Player(getStartController().getPlayerNick(), getStartController().getScore(), getStartController().getTrial(), getStartController().getQuizname());
    	String id = getStartController().getQuizname()+Integer.toString((getPlayerDatabase().getPlayerNumber(getStartController().getQuizname())+1));
    	getPlayerDatabase().addToDatabase(id, p);
    	dodaj(p);
		saveButton.setDisable(true);
    }

    public void dodaj(Player p) {
    	  tableView.getItems().add(p);
    }
    public void usun(ActionEvent actionEvent) {
    	getQuestionDatabase().destroyDatabase();
    	getPlayerDatabase().destroyDatabase();
    	Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    public void exitApp(ActionEvent actionEvent) {
    	getQuestionDatabase().destroyDatabase();
    	Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
