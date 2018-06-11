/*
 * PriceFetcher.java
 *
 * v1.0
 *
 * 2018-06-10
 * 
 * This code may be used and modified by anyone. However, commercial purposes will need to contact the author for approval.
 * 
 * Full source: https://github.com/Matheos96/GasMoneyCalculator
 * 
 * Copyright Matheos Mattsson 2018
 * http://matmatt.esy.es
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PriceFetcher {
	
	/*The site that I use to get the average prices (Finland) from.*/
	private final String SITE = "https://www.polttoaine.net/";
	
	/*Instance variables for the average prices*/
	private double GasE10, Gas98E, Diesel;
	
	/*Constructor runs the methods*/
	public PriceFetcher() {
		try {
			setPrices(getAveragePrices());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*Parses the html source code of the given site and returns the line we are interested in which contains the average prices for the fuel*/
	private String getAveragePrices()  throws MalformedURLException, IOException{
		URL theURL = new URL(SITE);
		URLConnection con = theURL.openConnection();
		con.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line = "";
		
		while ((line = reader.readLine()) != null) {
			if (line.contains("class=\"Keskihinnat\"")) {
				line = reader.readLine();
				break;
			}
		}
		
		return line;
		
	}
	
	/*Takes the interesting line and takes the info from that line and sets the instance variables for the current average fuel prices*/
	private void setPrices(String line) {
		String[] sArray = line.split("Hinnat");
		
		GasE10 = Double.parseDouble(sArray[1].substring(2,7));
		Gas98E = Double.parseDouble(sArray[2].substring(2,7));
		Diesel = Double.parseDouble(sArray[3].substring(2,7));
	}
	
	/*Takes a string which in this case will contain the text of a JRadioButton (the selected one) and returns the average price of that fuel*/
	public double getAvgPrice(String fuel) {
		if (fuel.equals("98E"))
			return Gas98E;
		else if (fuel.equals("95E10")) 
			return GasE10;
		else if (fuel.equals("Diesel"))
			return Diesel;
		else if (fuel.startsWith("Manually"))
			return -1.0;
		return -2.0;
	}

}
