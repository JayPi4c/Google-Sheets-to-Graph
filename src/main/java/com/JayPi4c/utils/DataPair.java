package com.JayPi4c.utils;

import java.util.Date;

public class DataPair {

	public static final Date STARTDATE = new Date(Long.parseLong(PropertyHelper.getProperty("graph.startdate")));
	public static final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

	long unixtimeStamp;
	String datePart;
	Date date;
	double costs = 0;
	public double summedCosts = 0;
	double averageCosts = 0;
	int daysPast;

	public DataPair(long unixtimeStamp, double costs) {
		this.unixtimeStamp = unixtimeStamp;
		date = new Date(unixtimeStamp);
		datePart = date.toString().substring(4, 10);
		this.costs = costs;
		long diff = date.getTime() - STARTDATE.getTime();
		daysPast = (int) (diff / MILLIS_IN_DAY);
	}

	public void addToSummedCosts(double previousCosts) {
		summedCosts = costs + previousCosts;
		averageCosts = summedCosts / daysPast;
	}

	void print() {
		System.out.printf("%s, %s, %s\n", datePart, costs, averageCosts);
	}

}
