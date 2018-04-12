/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean addWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        boolean addChocolate = ((CheckBox) findViewById(R.id.chocolate_topping_checkbox)).isChecked();
        int price = calculatePrice(addWhippedCream, addChocolate);
        String name = ((EditText) findViewById(R.id.name_text_box)).getText().toString();
        String priceMessage = createOrderSummary(price, addWhippedCream, addChocolate, name);

        //Sending intent...
        Intent sendOrderSummary = new Intent(Intent.ACTION_SENDTO);
        sendOrderSummary.setData(Uri.parse("mailto:"));
        sendOrderSummary.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + name);
        sendOrderSummary.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (sendOrderSummary.resolveActivity(getPackageManager()) != null) {
            startActivity(sendOrderSummary);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(getApplicationContext(), "Maximum allowed quantity reached", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), "Minimum allowed quantity reached.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = quantity * 5;

        if (addWhippedCream)
            price += 1;
        if (addChocolate)
            price += 2;

        return price;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     *
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        priceMessage += "\nThank you!";

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}