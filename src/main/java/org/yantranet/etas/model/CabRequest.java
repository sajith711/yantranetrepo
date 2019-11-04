package org.yantranet.etas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "cabrequest")
@Table(name = "cabrequest")
@DynamicUpdate
public class CabRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "requestid")
	private long requestid;
	
	@Column(name = "sourcelocation")
	private String sourcelocation;
	
	@Column(name = "datetimeofjourney")
	private String datetimeofjourney;
	
	@Column(name = "employeeid")
	private long employeeid;
	
	@Column(name = "requestcreationdate")
	private String requestcreationdate;
	
	@Column(name = "cabid")
	private long cabid;
	
	@Column(name = "requeststatus")
	private String requeststatus;
	
	@Column(name = "bookingid")
	private String bookingid;

	public long getRequestid() {
		return requestid;
	}

	public void setRequestid(long requestid) {
		this.requestid = requestid;
	}

	public String getSourcelocation() {
		return sourcelocation;
	}

	public void setSourcelocation(String sourcelocation) {
		this.sourcelocation = sourcelocation;
	}

	public String getDatetimeofjourney() {
		return datetimeofjourney;
	}

	public void setDatetimeofjourney(String datetimeofjourney) {
		this.datetimeofjourney = datetimeofjourney;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}

	public String getRequestcreationdate() {
		return requestcreationdate;
	}

	public void setRequestcreationdate(String requestcreationdate) {
		this.requestcreationdate = requestcreationdate;
	}

	public long getCabid() {
		return cabid;
	}

	public void setCabid(long cabid) {
		this.cabid = cabid;
	}

	public String getRequeststatus() {
		return requeststatus;
	}

	public void setRequeststatus(String requeststatus) {
		this.requeststatus = requeststatus;
	}

	public String getBookingid() {
		return bookingid;
	}

	public void setBookingid(String bookingid) {
		this.bookingid = bookingid;
	}

	@Override
	public String toString() {
		return "CabRequest [requestid=" + requestid + ", sourcelocation=" + sourcelocation + ", datetimeofjourney="
				+ datetimeofjourney + ", employeeid=" + employeeid + ", requestcreationdate=" + requestcreationdate
				+ ", cabid=" + cabid + ", requeststatus=" + requeststatus + ", bookingid=" + bookingid + "]";
	}
	
}
