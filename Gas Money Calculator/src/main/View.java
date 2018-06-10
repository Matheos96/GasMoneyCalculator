/*
 * View.java
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

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/*The viewable content of the application.*/

public class View extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel, infoPanel, finalPanel;
	private JButton submit;
	private GridBagConstraints constraints;
	private PriceFetcher pf;
	private JLabel feedbackLabel;
	private JTextField persons, fuelCon, tripLength;
	private ButtonGroup bGroup;
	private JRadioButton gasE10Radio, gas98ERadio, dieselRadio;
	 
	
	public View(PriceFetcher p) {
		pf = p;
		
		/*Initializing*/
		this.panel = new JPanel();
		this.constraints = new GridBagConstraints();
		this.bGroup = new ButtonGroup();
		panel.setLayout(new GridBagLayout());
		
		/*Initial constraints*/
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(5,5,5,5);
		constraints.anchor = GridBagConstraints.EAST;
		
		/*Fuel Consumption JComponents*/
		panel.add(new JLabel("Fuel Consumption (l/100km): "), constraints);
		constraints.gridx = 2;
		fuelCon = new JTextField(5);
		panel.add(fuelCon, constraints);
		
		/*Trip Length JComponents*/
		constraints.gridx = 3;
		panel.add(new JLabel("Trip length (km): "), constraints);
		constraints.gridx = 4;
		tripLength = new JTextField(5);
		panel.add(tripLength, constraints);
		
		/*Persons JComponents*/
		constraints.gridy = 2;
		constraints.gridx = 1;
		panel.add(new JLabel("Split between (persons): "), constraints);
		constraints.gridx = 2;
		persons = new JTextField(2);
		panel.add(persons, constraints);
		
		/*Fuel Type JComponents*/
		constraints.gridx = 3;
		panel.add(new JLabel("Fuel Type: "), constraints);
		
		constraints.gridx = 4;
		gasE10Radio = new JRadioButton("95E10");
		bGroup.add(gasE10Radio);
		panel.add(gasE10Radio, constraints);
		
		constraints.gridx = 5;
		gas98ERadio = new JRadioButton("98E");
		bGroup.add(gas98ERadio);
		panel.add(gas98ERadio, constraints);
		
		constraints.gridx = 6;
		dieselRadio = new JRadioButton("Diesel");
		bGroup.add(dieselRadio);
		panel.add(dieselRadio, constraints);
		
		/*Initializing a new JPanel for the info text in the middle*/
		this.infoPanel = new JPanel();

		/*Creates the info label, makes it italic and adds it to the panel*/
		JLabel info = new JLabel("Gas prices used are yesterday's averages from Polttoaine.net");
		info.setFont(new Font(info.getFont().getName(),Font.ITALIC,info.getFont().getSize()));
		infoPanel.add(info);
		
		/*The last JPanel used. Contains the button and the feedback text.*/
		this.finalPanel = new JPanel();
		finalPanel.setLayout(new GridBagLayout());
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 1;
		this.submit = new JButton("Calculate!");
		submit.addActionListener(this);
		finalPanel.add(submit, constraints);
		
		feedbackLabel = new JLabel("Click \"Calculate!\" to calculate how much each person have to pay!"); 
		constraints.gridy = 2;
		
		finalPanel.add(feedbackLabel,constraints);
		
		/*Copyright label*/
		constraints.gridy = 3;
		JLabel copyright = new JLabel("Copyright \u00a9 2018 Matheos Mattsson");
		copyright.setFont(new Font(info.getFont().getName(),Font.ITALIC,info.getFont().getSize()));
		finalPanel.add(copyright, constraints);
		
		
		
		
		
		setSize(650, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gas Money Calculator");
		add(panel, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.CENTER);
		add(finalPanel, BorderLayout.SOUTH);
		getRootPane().setDefaultButton(submit);
		setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*If the user used comma, replace with point*/
		String fuelConsumption = fuelCon.getText().trim().replace(",", ".");
		String noPersons = persons.getText().trim().replace(",", ".");
		String length = tripLength.getText().trim().replace(",", ".");
		
		double fuelDouble, lengthDouble, answer, avgPrice;
		int personsInt;
		
		try {
			fuelDouble = Double.parseDouble(fuelConsumption);
			lengthDouble  = Double.parseDouble(length);
			personsInt = Integer.parseInt(noPersons);
			avgPrice = pf.getAvgPrice(Logic.getSelected(bGroup));
			answer = Logic.calculatePrice(avgPrice, personsInt, fuelDouble, lengthDouble);
			feedbackLabel.setText("You will have to pay "+Math.round(answer*100.0)/100.0+"€ each :)");
			
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			feedbackLabel.setText("Something went wrong. Make sure you only have numbers (decimal point/comma is allowed) in the fields.");
		}
		
	}

}
