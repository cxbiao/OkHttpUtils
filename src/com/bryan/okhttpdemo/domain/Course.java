package com.bryan.okhttpdemo.domain;

import java.io.Serializable;

public class Course implements Serializable{
	public int id;
	public int timelength;
	public String title;

	public Course(int id, int timelength, String title) {
		this.id = id;
		this.timelength = timelength;
		this.title = title;
	}

	public Course() {
		
	}

}
