package calculator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PriceFetcher {

    /*Instance variables for the average prices*/
    private double gasE10, gas98E, diesel;

    public boolean fetch() {
        try {
            setPrices(getAveragePrices());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*Parses the html source code of the given site and returns the line we are interested in which contains the average prices for the fuel*/
    private String getAveragePrices()  throws IOException{
        /*The site that I use to get the average prices (Finland) from.*/
        URL theURL = new URL("https://www.polttoaine.net/");
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

        gasE10 = Double.parseDouble(sArray[1].substring(2,7));
        gas98E = Double.parseDouble(sArray[2].substring(2,7));
        diesel = Double.parseDouble(sArray[3].substring(2,7));
    }

    /*Takes a string which in this case will contain the text of a JRadioButton (the selected one) and returns the average price of that fuel*/
    public double getAvgPrice(String fuel) {
        if (fuel.equals("98E"))
            return gas98E;
        else if (fuel.equals("95E10"))
            return gasE10;
        else if (fuel.equals("Diesel"))
            return diesel;
        return -1;
    }
}