package com.JayPi4c;

import java.util.Date;

public class DataPair implements Comparable<DataPair> {

	public static final Date STARTDATE = new Date(1567209600000L);

	long unixtimeStamp;
	String datePart;
	Date date;
	double costs = 0;
	double summedCosts = 0;
	double averageCosts = 0;
	int daysPast;

	public DataPair(long unixtimeStamp, double costs) {
		this.unixtimeStamp = unixtimeStamp;
		date = new Date(unixtimeStamp);
		datePart = date.toString().substring(4, 10);
		this.costs = costs;
		long diff = date.getTime() - STARTDATE.getTime();
		daysPast = (int) (diff / (1000 * 60 * 60 * 24));
	}

	void addToSummedCosts(double previousCosts) {
		summedCosts = costs + previousCosts;
		averageCosts = summedCosts / daysPast;
	}

	@Override
	public int compareTo(DataPair o) {
		return (int) (o.unixtimeStamp - unixtimeStamp);
	}

	void print() {
		System.out.printf("%s, %s, %s\n", datePart, costs, averageCosts);
	}

}
