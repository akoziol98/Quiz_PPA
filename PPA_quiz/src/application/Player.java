package application;

public class Player {
    private String nick = null;
    private Integer score = null;
    private Integer trial = null;
    private String quizname = null;
    private StartController startController = new StartController();
 
    Player(String nick, int score, int trial, String quizname) {
        this.nick = nick;
        this.score = score;
        this.trial = trial;
        this.quizname = quizname;
    }
    public StartController getStartController() {
    	return startController;
    }

	public String getNick() {
		return nick;
	}

	public int getScore() {
		return score;
	}

	public int getTrial() {
		return trial;
	}

	public String getQuizname() {
		return quizname;
	}

	public void setPlayerNick(String nick) {
		getStartController().setPlayerNick(nick);
		
	}
   

        
}