package test;

import static java.sql.DriverManager.getConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Testing {
	private static final String url = "jdbc:hsqldb:file:C:\\Users\\dell\\Desktop\\PPA\\hsqldb\\data\\dbquiz";

	@BeforeAll
    public static void prepareDatabase() {
        try (Connection c = getConnection(url, "SA", "")) {
            c.createStatement().execute("CREATE TABLE TABELKA (ID VARCHAR(255), NICK VARCHAR(255), SCORE INT, TRIAL INT, QUIZNAME VARCHAR(255))");
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO TABELKA (ID, NICK, SCORE, TRIAL, QUIZNAME) VALUES (?, ?, ?, ?, ?)")) {
            	ps.setString(1, "Historia1");
            	ps.setString(2, "Koleœ");
            	ps.setInt(3, 4);
            	ps.setInt(4, 8);
                ps.setString(5, "Historia");
                ps.execute();
                
                ps.setString(1, "Historia2");
            	ps.setString(2, "Kasia");
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
            	ps.setString(2, "Jelcz");
            	ps.setInt(3, 7);
            	ps.setInt(4, 9);
                ps.setString(5, "Neuropsychologia");
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void destroyDatabase() {
        try (Connection c = getConnection(url, "SA", "")) {
            c.createStatement().execute("DROP TABLE TABELKA");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testConnection() {
        try (Connection c = getConnection(url, "SA", "")) {
            c.createStatement().executeQuery("SELECT * FROM TABELKA");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEntries() {
        try (Connection c = getConnection(url, "SA", "")) {
            int cnt = 0;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT * FROM TABELKA")) {
                while (rs.next()) {
                    cnt++;
                }
            }
            Assertions.assertEquals(cnt, 4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
	public void testQuizEntries() {
		String quizname = "Historia";
		int cnt = 0;
		Connection connection = null;
		
		try {
        	connection = DriverManager.getConnection(url);
        	
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM TABELKA WHERE ID LIKE ?")) {
            	ps.setString(1, quizname+"%");
            	
            	ResultSet ResultSet =  ps.executeQuery();
            	while (ResultSet.next()) {
            		cnt++;
                }
            } 
         Assertions.assertEquals(cnt, 2);
        }catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		
	}
    @Test
	public void testId() {
		int cnt = 0;
		String quizname = "Historia";
		Connection connection = null;
		
		try {
        	connection = DriverManager.getConnection(url);
            
            try (ResultSet ResultSet = connection.createStatement().executeQuery("SELECT * FROM TABELKA WHERE ID LIKE '"+quizname+"%'")) {
            	while (ResultSet.next()) {
            		cnt++;
                }
            }   
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
		
		Assertions.assertEquals(quizname+Integer.toString((cnt+1)), "Historia3");
	}
}
