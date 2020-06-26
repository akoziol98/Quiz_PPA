package application;
	
import static java.sql.DriverManager.getConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import questionManager.PlayerDatabase;
import questionManager.QuestionDatabase;


public class Main extends Application {
	private static QuestionDatabase questionDatabase = new QuestionDatabase();
	private static PlayerDatabase playerDatabase = new PlayerDatabase();
	@Override
	public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        Parent root = loader.load();
        StartController ctrl = loader.getController();
        primaryStage.setTitle("Kwestionariusze");
        primaryStage.setScene(new Scene(root, 350, 250));
        primaryStage.getScene().getStylesheets().add("application/DarkTheme.css");
        primaryStage.show();
        ctrl.setStage(primaryStage);
    }
	
	public static PlayerDatabase getPlayerDatabase() {
		return playerDatabase;
		}
	public static QuestionDatabase getQuestionDatabase() {
		return questionDatabase;
		}
	
	public static void main(String[] args) {
		Connection connection = null;
		final String url = "jdbc:hsqldb:file:C:\\Users\\dell\\Desktop\\PPA\\hsqldb\\data\\dbquiz";
        
        try (Connection c = getConnection(url, "SA", "")) {
        	
        	DatabaseMetaData dbm = c.getMetaData();
        	ResultSet tables = dbm.getTables(null, null, "QUESTIONSTABLE", null);
        	if (tables.next()) {
        	}
        	else {
        		getQuestionDatabase().prepareDatabase();
        		getQuestionDatabase().updateDatabase();
        	}
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection c = getConnection(url, "SA", "")) {
        	DatabaseMetaData dbm = c.getMetaData();        	
        	ResultSet tables = dbm.getTables(null, null, "PLAYERTABLE", null);
        	if (tables.next()) {
        	}
        	else {
        		getPlayerDatabase().prepareDatabase();
        		getPlayerDatabase().updateDatabase();
        		
        	}
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
		launch(args);
	}
}
