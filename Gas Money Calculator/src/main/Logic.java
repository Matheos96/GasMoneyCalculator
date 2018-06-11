/*
 * Logic.java
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

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
//
public class Logic {
	
	/*Calculates and returns the price each person have to pay, based on given arguments*/
	public static double calculatePrice(double fuelPrice, int ppl, double consumption, double length) {
		return ((length/100.0)*consumption*fuelPrice)/ppl;
		
	}
	
	/*Takes a buttongroup (in this case of JRadioButtons) and returns the text of the selected button*/
	public static String getSelected(ButtonGroup group) {
		Enumeration<AbstractButton> elements = group.getElements();
	    while (elements.hasMoreElements()) {
	      AbstractButton button = (AbstractButton)elements.nextElement();
	      if (button.isSelected()) {
	        return button.getText();
	      }
	    }
	    return ""; 
	}

}
