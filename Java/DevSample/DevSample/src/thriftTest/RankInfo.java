package thriftTest;

import java.io.Serializable;

public class RankInfo implements Serializable{

private static final long serialVersionUID = -2068951734606731869L;
	
	private String name;
	private String rank;
	private String score;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

}
