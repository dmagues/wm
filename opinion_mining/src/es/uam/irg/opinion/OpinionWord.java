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
	
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		OpinionWord test = (OpinionWord)obj;
		return this.pos == test.pos &&
				(this.word == test.word || (this.word != null && this.word.equals(test.word)));
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + this.pos;
		hash = 31 * hash + (null == this.word ? 0 : this.word.hashCode());
		return hash;
	}
	

}
