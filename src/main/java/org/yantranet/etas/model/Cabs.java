package org.yantranet.etas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Cabs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cabid")
	private long cabid;
	
	@Column(name = "registrationnumber")
	private String registrationnumber;
	
	@Column(name = "driverid")
	private String driverid;
	
	@Column(name = "cabstatus")
	private String cabsatus;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "vacancy")
	private int vacancy;
	
	@Column(name = "sourcelocation")
	private String sourcelocation;
	
	@Column(name = "timeslot")
	private String timeslot;

	public long getCabid() {
		return cabid;
	}

	public void setCabid(long cabid) {
		this.cabid = cabid;
	}

	public String getRegistrationnumber() {
		return registrationnumber;
	}

	public void setRegistrationnumber(String registrationnumber) {
		this.registrationnumber = registrationnumber;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getCabsatus() {
		return cabsatus;
	}

	public void setCabsatus(String cabsatus) {
		this.cabsatus = cabsatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getVacancy() {
		return vacancy;
	}

	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}

	public String getSourcelocation() {
		return sourcelocation;
	}

	public void setSourcelocation(String sourcelocation) {
		this.sourcelocation = sourcelocation;
	}

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}

	@Override
	public String toString() {
		return "Cabs [cabid=" + cabid + ", registrationnumber=" + registrationnumber + ", driverid=" + driverid
				+ ", cabsatus=" + cabsatus + ", comments=" + comments + ", vacancy=" + vacancy + ", sourcelocation="
				+ sourcelocation + ", timeslot=" + timeslot + "]";
	}
	
}
