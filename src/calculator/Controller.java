package calculator;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button calcBtn;

    @FXML
    private ComboBox<String> fuelTypeCombo;

    @FXML
    private TextField consumptionValue;
    @FXML
    private TextField personsValue;
    @FXML
    private TextField distanceValue;
    @FXML
    private TextField fuelPriceValue;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private RadioButton manualPriceRadio;
    @FXML
    private RadioButton autoPriceRadio;

    @FXML
    private Label feedbackLabel;

    private PriceFetcher fetcher;

    //Is run when the view is created.
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //Options for fuel types. Select the first one by default
        fuelTypeCombo.getItems().setAll("95E10", "98E", "Diesel");
        fuelTypeCombo.getSelectionModel().selectFirst();

        //Add both radio buttons to the same group in order to only be able to select one. Auto default selected
        manualPriceRadio.setToggleGroup(toggleGroup);
        autoPriceRadio.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(autoPriceRadio);

        //Force the field to be decimal numbers only
        consumptionValue.textProperty().addListener(decimalListener(consumptionValue));
        distanceValue.textProperty().addListener(decimalListener(distanceValue));
        fuelPriceValue.textProperty().addListener(decimalListener(fuelPriceValue));

        //Force the field to be of integer value
        personsValue.textProperty().addListener(integerListener(personsValue));

        //Fetches the daily average prices
        fetcher = new PriceFetcher();

        //If failed to fetch, disable auto and force manual
        if (!fetcher.fetch()) {
            autoPriceRadio.setDisable(true);
            toggleGroup.selectToggle(manualPriceRadio);
        }
    }

    /**
     * Is executed when the calculate button is clicked.
     * Calculates the per person price or gives an appropriate feedback message.
     */
    public void handleCalculateBtn() {
        if (fieldsValidated()) {
            double price;
            //If auto mode is selected
            if (toggleGroup.getSelectedToggle() == autoPriceRadio) {
                price = fetcher.getAvgPrice(fuelTypeCombo.getSelectionModel().getSelectedItem());
            }
            else
                price = textFieldToDouble(fuelPriceValue);

            double result = Math.round((((textFieldToDouble(distanceValue) / 100.0)
                    * textFieldToDouble(consumptionValue) * price /
                    textFieldToDouble(personsValue))) * 100.0) / 100.0;

            feedbackLabel.setText(String.format("Each person pays %.2f €", result));
        }
        else
            feedbackLabel.setText("Please fill all the fields. If you are using manual fuel price, please also " +
                    "enter that.");
    }

    /**
     * Determines whether all required fields have been filled.
     * @return true if all fields are filled
     */
    private boolean fieldsValidated() {
        String consumption = consumptionValue.getText();
        String distance = distanceValue.getText();
        String persons = personsValue.getText();
        if(consumption.length() > 0 && distance.length() > 0 && persons.length() > 0) {
            if(toggleGroup.getSelectedToggle() == manualPriceRadio) {
                return fuelPriceValue.getText().length() > 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Converts the value of a textfield into a double.
     * @param field The TextField to convert to a double
     * @return the converted double
     */
    private double textFieldToDouble(TextField field) {
        return Double.parseDouble(field.getText().replace(",", "."));
    }


    /**
     * Returns a ChangeListener that prevents users from inputting anything other than decimal values
     * Both comma and dot are accepted as decimal pointer.
     * @param field The TextField onto which the listener will be set
     * @return the ChangeListener
     */
    private ChangeListener<String> decimalListener(TextField field) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.replace(",", ".").matches("\\d*(\\.\\d*)?")) {
                field.setText(oldValue);
            }
        };
    }

    /**
     * Returns a ChangeListener that prevents users from inputting anything other than integers
     * @param field The TextField onto which the listener will be set
     * @return the ChangeListener
     */
    private ChangeListener<String> integerListener(TextField field) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.replace(",", ".").matches("\\d*")) {
                field.setText(oldValue);
            }
        };
    }

}
