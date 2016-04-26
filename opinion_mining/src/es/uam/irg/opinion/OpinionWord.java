/**
 * Copyright 2016 
 * Daniel Magües
 * Web Mining at Universidad Autonoma de Madrid
 *
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * the current software. If not, see <http://www.gnu.org/licenses/>.
 */
package es.uam.irg.opinion;
/**
 * OpinionWord
 *
 * Represent a word and a POS code  
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 *   
 */
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
