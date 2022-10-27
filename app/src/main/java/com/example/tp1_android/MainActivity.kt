package com.example.tp1_android

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    lateinit var textFirstName: EditText;
    lateinit var textPrenom: EditText;
    lateinit var textAdress: EditText;
    lateinit var sizeSelect: Spinner;
    lateinit var ingredientSelect: Spinner;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textFirstName = findViewById(R.id.textName)
        textPrenom = findViewById(R.id.textPrenom)
        textAdress = findViewById(R.id.textPostalAdress)
        sizeSelect = findViewById(R.id.spinner)
        ingredientSelect = findViewById(R.id.spinner2)
        val size = resources.getStringArray(R.array.PizzaSizes)
        val ingredients = resources.getStringArray(R.array.Toppings)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, size
        )
        val adapter2 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, ingredients
        )
        sizeSelect.adapter = adapter;
        ingredientSelect.adapter = adapter2;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(view: View) {
        if (!valid())
            Toast
                .makeText(
                    this,
                    "Fill form pls alo",
                    Toast.LENGTH_SHORT
                )
                .show();

        sendEmail(
            "temp@gmail.com",
            "Order : " + LocalDateTime.now()
                ,
            toString()
        )
    }

    private fun valid(): Boolean {
        println(sizeSelect.selectedItem.toString());
        return textFirstName.text.length > 0 && textAdress.text.length > 0 && textPrenom.text.length > 0 && sizeSelect.selectedItem.toString().length > 0 && ingredientSelect.selectedItem.toString().length > 0;
    }

    override fun toString(): String {
        return "First name : " + textFirstName.text + "/n" + "Last name : " + textPrenom.text + "/n" + "Adress : " + textAdress + "/n" + "Pizza size : " + sizeSelect.selectedItem.toString() + "Toppings" + ingredientSelect.selectedItem.toString();
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "message/rfc822"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }
}