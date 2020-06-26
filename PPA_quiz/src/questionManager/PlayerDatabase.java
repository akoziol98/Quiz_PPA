package questionManager;

import static java.sql.DriverManager.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.Player;

public class PlayerDatabase {
	private static final String url = "jdbc:hsqldb:file:C:\\Users\\dell\\Desktop\\PPA\\hsqldb\\data\\dbquiz";
	Connection connection = null;
	
	public void prepareDatabase() {
        try (Connection c = getConnection(url, "SA", "")) {
            c.createStatement().execute("CREATE TABLE PLAYERTABLE (ID VARCHAR(255), NICK VARCHAR(255), SCORE INT, TRIAL INT, QUIZNAME VARCHAR(500))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public void updateDatabase() {
		try (Connection c = DriverManager.getConnection(url, "SA", "")) {
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO PLAYERTABLE (ID, NICK, SCORE, TRIAL, QUIZNAME) VALUES (?, ?, ?, ?, ?)")) {
            	ps.setString(1, "Historia1");
            	ps.setString(2, "Geniusz");
            	ps.setInt(3, 4);
            	ps.setInt(4, 8);
                ps.setString(5, "Historia");
                ps.execute();
                
                ps.setString(1, "Historia2");
            	ps.setString(2, "Ada");
            	ps.setInt(3, 2);
            	ps.setInt(4, 2);
                ps.setString(5, "Historia");
                ps.execute();
                
                ps.setString(1, "Neuropsychologia1");
            	ps.setString(2, "Lolo");
            	ps.setInt(3, 5);
            	ps.setInt(4, 1);
                ps.setString(5, "Neuropsychologia");
                ps.execute();
                
                ps.setString(1, "Neuropsychologia2");
            	ps.setString(2, "Gracz");
            	ps.setInt(3, 7);
            	ps.setInt(4, 9);
                ps.setString(5, "Neuropsychologia");
                ps.execute();
 
            }

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
    }

	public void addToDatabase(String id, Player p) {
		try (Connection c = DriverManager.getConnection(url, "SA", "")) {
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO PLAYERTABLE (ID, NICK, SCORE, TRIAL, QUIZNAME) VALUES (?, ?, ?, ?, ?)")) {
            	ps.setString(1, id);
            	ps.setString(2, p.getNick());
            	ps.setInt(3, p.getScore());
            	ps.setInt(4, p.getTrial());
                ps.setString(5, p.getQuizname());
                ps.execute();
            }

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
    }
	public int getPlayerNumber(String quizname) {
		int cnt = 0;
		Connection connection = null;
		try {
        	connection = DriverManager.getConnection(url);
            
        	try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PLAYERTABLE WHERE ID LIKE ?")) {
            	ps.setString(1, quizname+"%");
            	
            	ResultSet ResultSet =  ps.executeQuery();
            	while (ResultSet.next()) {
            		cnt++;
                }
            }   
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		return cnt;
	}

	public String getNick(String quizname, int id) {
		Connection connection = null;
		String q = null;
		try {
        	connection = DriverManager.getConnection(url);
        	String sqlquery = ("SELECT * FROM PLAYERTABLE WHERE ID=?");
    		PreparedStatement ps = connection.prepareStatement(sqlquery);
    		ps.setString(1, quizname+id);
        	ResultSet ResultSet =  ps.executeQuery();
     
        	while(ResultSet.next()) {
        		q = ResultSet.getString("NICK");
        	} 
        
        		ResultSet.close();
            
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		return q;
        
	}

	public int getScore(String quizname, int id) {
		Connection connection = null;
		int q = 0;
		try {
        	connection = DriverManager.getConnection(url);
        	String sqlquery = ("SELECT * FROM PLAYERTABLE WHERE ID=?");
    		PreparedStatement ps = connection.prepareStatement(sqlquery);
    		ps.setString(1, quizname+id);
        	ResultSet ResultSet =  ps.executeQuery();
     
            while(ResultSet.next()) {
            	q = ResultSet.getInt("SCORE");
            } 
        
            ResultSet.close();
            
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		return q;
	}

	public int getTrial(String quizname, int id) {
		Connection connection = null;
		int q = 0;
		try {
        	connection = DriverManager.getConnection(url);
        	String sqlquery = ("SELECT * FROM PLAYERTABLE WHERE ID=?");
    		PreparedStatement ps = connection.prepareStatement(sqlquery);
    		ps.setString(1, quizname+id);
        	ResultSet ResultSet =  ps.executeQuery();
     
            while(ResultSet.next()) {
            	q = ResultSet.getInt("TRIAL");
            } 
        
            ResultSet.close();
           
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		return q;
	}

	public String getQuizname(String quizname, int id) {
		Connection connection = null;
		String q = null;
		try {
        	connection = DriverManager.getConnection(url);
        	String sqlquery = ("SELECT * FROM PLAYERTABLE WHERE ID=?");
    		PreparedStatement ps = connection.prepareStatement(sqlquery);
    		ps.setString(1, quizname+id);
        	ResultSet ResultSet =  ps.executeQuery();
     
            while(ResultSet.next()) {
            	q = ResultSet.getString("QUIZNAME");
            } 
        
            ResultSet.close();
           
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		return q;
	}
	
	public void destroyDatabase() {
		try (Connection c = getConnection(url, "SA", "")) {
            c.createStatement().execute("DROP TABLE PLAYERTABLE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	
}
