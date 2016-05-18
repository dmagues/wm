package com.wm.movies.parser.dependency;

import com.wm.movies.parser.pos.TaggedWord;

public class Dependency {
	private TaggedWord gov;
	private TaggedWord dep;
	private String relation;
	/**
	 * @return the gov
	 */
	public TaggedWord getGov() {
		return gov;
	}
	/**
	 * @param gov the gov to set
	 */
	public void setGov(TaggedWord gov) {
		this.gov = gov;
	}
	/**
	 * @return the dep
	 */
	public TaggedWord getDep() {
		return dep;
	}
	/**
	 * @param dep the dep to set
	 */
	public void setDep(TaggedWord dep) {
		this.dep = dep;
	}
	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String toString()
	{
		String s ="";
		s += String.format("\n\t%s gov: %s dep: %s",this.relation, this.gov, this.dep);
		return s;
	}
	
	
	
}
