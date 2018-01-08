package com.example.android.justjava;

import android.support.v7.app.AppCompatActivity;
/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     * @param price to update the price
     * @param addWhippedCream is whether or not user wants whipped cream
     * @param addChocochips is whether or not user wants Chocochips
     * @param addWaffles is whether or not user wants Waffles
     * @param addCinnamon is whether or not user wants Cinnamon
     * @param addNutella is whether or not user wants Nutella
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocochips, boolean addWaffles,
                                      boolean addCinnamon, boolean addNutella) {
        String priceMessage  = getString(R.string.Name_submit) + name;
        priceMessage += "\n" + getString(R.string.Qty) + quantity;
        priceMessage += "\n" + getString(R.string.Coffee) + calculatePriceRaw();
        priceMessage += "\n" + getString(R.string.ToppingsPrice) + toppings(addWhippedCream, addChocochips, addWaffles, addCinnamon, addNutella);
        priceMessage += "\n" + getString(R.string.Tax) + quantity*150/10;
        priceMessage += "\n" + getString(R.string.Total) + calculatePriceTotal(addWhippedCream, addChocochips, addWaffles, addCinnamon, addNutella);
        priceMessage += "\n" + getString(R.string.ThankYou);
        return priceMessage;
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_text_field);
        String name = nameField.getText().toString();

        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCheckBox.isChecked();
        displayMessage("Toppings: " + 20);

        CheckBox chocochipsCheckBox = (CheckBox) findViewById(R.id.chocochips_checkbox);
        boolean hasChocochips = chocochipsCheckBox.isChecked();
        displayMessage("Toppings: " + 20);

        CheckBox wafflesCheckBox = (CheckBox) findViewById(R.id.waffles_checkbox);
        boolean hasWaffles = wafflesCheckBox.isChecked();
        displayMessage("Toppings: " + 20);

        CheckBox cinnamonCheckBox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckBox.isChecked();
        displayMessage("Toppings: " + 20);

        CheckBox nutellaCheckBox = (CheckBox) findViewById(R.id.Nutella_checkbox);
        boolean hasNutella = nutellaCheckBox.isChecked();
        displayMessage("Toppings: " + 20);


        int price = calculatePriceTotal(hasWhippedCream, hasChocochips, hasWaffles, hasCinnamon, hasNutella);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocochips, hasWaffles, hasCinnamon, hasNutella);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);




        displayMessage(priceMessage);

        Toast.makeText(this, getString(R.string.Toast_Updated), Toast.LENGTH_SHORT).show();
    }}


    private int calculatePriceRaw() {

        return quantity*150;
    }

    private int toppings(boolean addWhippedCream, boolean hasChocochips, boolean hasWaffles, boolean hasCinnamon, boolean hasNutella){
        int toppings = 0;
        if(addWhippedCream){
            toppings = toppings + 10;
        }

        if(hasChocochips){
            toppings = toppings + 20;
        }

        if(hasWaffles){
            toppings = toppings + 15;
        }

        if(hasCinnamon){
            toppings = toppings + 15;
        }

        if(hasNutella){
            toppings = toppings + 20;
        }

        return toppings*quantity;

    }

    /**
     * @param addWhippedCream is whether or not user wants whipped cream
     * @param hasChocochips is whether or not user wants Chocochips
     * @param hasWaffles is whether or not user wants Waffles
     * @param hasCinnamon is whether or not user wants Cinnamon
     * @param hasNutella is whether or not user wants Nutella
     * @return text summary
     */


    private int calculatePriceTotal(boolean addWhippedCream, boolean hasChocochips, boolean hasWaffles, boolean hasCinnamon, boolean hasNutella) {
        int basePrice = 165;

        if(addWhippedCream){
            basePrice = basePrice + 10;
        }

        if(hasChocochips){
            basePrice = basePrice + 20;
        }

        if(hasWaffles){
            basePrice = basePrice + 15;
        }

        if(hasCinnamon){
            basePrice = basePrice + 15;
        }

        if(hasNutella){
            basePrice = basePrice + 20;
        }

        return quantity*basePrice;

    }


    /**
     * This method displays the given quantity value on the screen.
     */

    public void qtyIncrement(View view) {
        if(quantity<100){
            quantity = quantity + 1;
        }
        if(quantity>=100){
            Toast.makeText(this, getString(R.string.Toast_MaxLim), Toast.LENGTH_SHORT).show();
        }
        display(quantity);

    }

    public void qtyDecrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        }
            display(quantity);
        if(quantity<=1){
            Toast.makeText(this, getString(R.string.Toast_MinLim), Toast.LENGTH_SHORT).show();
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}