
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     *
     */
    public void submitOrder(View view) {
        //int price = quantity * 5;

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean haswhippedCream = whippedCreamCheckbox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);

        String customerName;

        customerName = nameField.getText().toString();


        int price = calculatePrice(haswhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, haswhippedCream, hasChocolate, customerName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);

        //Set Email Type to plain text
        //intent.setType("message/rfc822");


        //Setup the data component of the intent

        String email = Uri.encode("mailto:&subject=Coffee Order") + "&body=" + Uri.encode(priceMessage);

        //Add string message to Email Intent


        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, customerName));

        //Add Body of Email
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        //intent.putExtra(Intent.EXTRA_TEXT, message);


        //Check if there is an app to handle the intent and if so run the activity

        if (intent.resolveActivity(getPackageManager()) != null) {

            startActivity(intent);
        }




    }

    /**
     * Calculates the total of the order.
     *  @param  hasChocolate whether the user has selected Chocolate
     *  @param hasWhippedCream whether the user has selected Whipped Cream
     *  @return total price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;


        // If whipped cream is selected add $1 to the price
        if (hasWhippedCream) {

            basePrice += 1;

        }

        // If chocolate is selected add $2 to the price

        if (hasChocolate) {

            basePrice += 2;

        }


        int price = quantity * basePrice;

        return price;

    }

    /**
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants to add whipped cream topping
     * @param addChocolate    is whether or not the user wants to add a chocolate topping
     * @return String summary of the order
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String customerName) {


        String priceMessage = getString(R.string.order_summary_name, customerName);
        priceMessage += "\n" + getString(R.string.quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;


    }

    /**
     * This method is called when the plus button is clicked.
     */

    public void increment(View view) {


        quantity = quantity + 1;

        if (quantity > 100) {

            quantity = 100;

            Toast.makeText(getApplicationContext(), "Cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {


        quantity = quantity - 1;
        if (quantity < 1) {

            quantity = 1;

            Toast.makeText(getApplicationContext(), "Must order a minimum of  1 cup of coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int number) {

        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);

        quantityTextView.setText("" + number);

    }





}