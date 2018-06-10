/*
 * Run.java
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

import java.awt.EventQueue;

/*Simply runs the application. No More, no less.*/

public class Run {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
	     {  @Override     
	        public void run()
	         {       
	    	 	View view = new View(new PriceFetcher());
	 			view.setVisible(true);
	            
	         }
	     });

	}

}
