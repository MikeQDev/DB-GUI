package edu.easternct.CSC342.sample;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
	
public class Skill {
	
	String skill_Id;
	String skill_description;

	
	public Skill() {
		
	}
	/**
	 * @param skill_Id
	 */
	public Skill(String skill_Id) {
		this.skill_Id = skill_Id;
		
	}
	public String getSkillId() {
		return skill_Id;
	}
	public void setSkillId(String skill_Id) {
		this.skill_Id = skill_Id;
	}
	public String getSkillDescription() {
		return skill_description;
	}
	public void setSkillDescription(String skill_description) {
		this.skill_description = skill_description;
	}
	
	public String toString() {
		return ("Id: " + getSkillId() + "Description: " + getSkillDescription() );
	}

	
}
