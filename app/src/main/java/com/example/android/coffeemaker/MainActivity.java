package com.example.android.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaring Variables
    int numberOfCoffeeOrdered;
    CheckBox chocolate, cream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfCoffeeOrdered = 1;
        chocolate = findViewById(R.id.chocolate_box);
        cream = findViewById(R.id.cream_box);
    }


    //Submit Order method
    public void submitOrder(View view){
        // Calculating the price
        int total = calculate_price(numberOfCoffeeOrdered);

        // Creating Order Summary
        EditText editText = findViewById(R.id.name_of_user);
        String name_of_user = editText.getText().toString();
        String order_summary = create_order_summary(total, name_of_user);

        //Sending Intent
        sendIntent(order_summary, name_of_user);

    }

    //Calculating Price
    public int calculate_price(int number){
        int price_of_one_coffee = 5;
        if(chocolate.isChecked())
            price_of_one_coffee += 2;
        if(cream.isChecked())
            price_of_one_coffee += 1;
        return number * price_of_one_coffee;
    }

    //Display Quantity
    @SuppressLint("SetTextI18n")
    private void display_quantity(int number){
        TextView textView  = findViewById(R.id.quantity_text_view);
        textView.setText(Integer.toString(number));
    }


    //Creating Order Summary
    private String create_order_summary(int final_price, String name_of_user){
        String message = getString(R.string.name)  + name_of_user;
        message += "\n" + getString(R.string.add_chocolate) + ": " + (chocolate.isChecked()? getString(R.string.yes) : getString(R.string.no));
        message += "\n" + getString(R.string.add_cream) + ": " + (cream.isChecked()? getString(R.string.yes) : getString(R.string.no));
        message += "\n" + getString(R.string.total) + final_price;
        message+= getString(R.string.thank_you);
        return message;
    }

    //Sending Intent method
    private void sendIntent(String order_summary, String name_of_user){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "coffee_order@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_for) + name_of_user);
        intent.putExtra(Intent.EXTRA_TEXT, order_summary);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "No App Installed", Toast.LENGTH_SHORT).show();
        }
    }


    //Increment method
    public void increment(View view){
        if(numberOfCoffeeOrdered <10)
            display_quantity(numberOfCoffeeOrdered+=1);
    }

    //Decrement method
    public void decrement(View view){
        if(numberOfCoffeeOrdered>1)
            display_quantity(numberOfCoffeeOrdered-=1);
    }

}