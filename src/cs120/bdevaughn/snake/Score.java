package cs120.bdevaughn.snake;

/**
 * This class will be used to hold onto the high scores and the names associated with the scores.
 * @author Bryce DeVaughn
 */
public class Score{

	private int score;
	private String name;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		super.toString();
		StringBuffer sB = new StringBuffer("");
		sB.append(name);
		sB.append(": ");
		sB.append(score);
		String rtnString = sB.toString();
		return rtnString;
	}

	public String toWrite(char c){
		String writeStr = name + c + score;
		return writeStr;
	}
}
