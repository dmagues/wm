package es.uam.irg.opinion;

public class OpinionWord {
	private String word;
	private int pos;
	public OpinionWord(String w, int p) {
		this.word = w;
		this.pos = p;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public String toString()
	{
		return this.word;
	}

}
