package com.example.gestiondestaches;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {

    EditText titleInput , dateInput , descriptionInput ;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        titleInput = findViewById(R.id.title);
        descriptionInput=findViewById(R.id.description);
        dateInput = findViewById(R.id.date);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBtask MyDB = new DBtask(new DatabaseHelper(AddTaskActivity.this));
                if (!titleInput.getText().toString().isEmpty()) {
                    MyDB.insert(new Task(titleInput.getText().toString()
                            ,descriptionInput.getText().toString()
                            ,dateInput.getText().toString()
                            ,"incomplet"));
                }else {
                    Toast.makeText(AddTaskActivity.this,"Title input is empty",Toast.LENGTH_SHORT)
                            .show();
                }
                titleInput.setText("");
                descriptionInput.setText("");
                dateInput.setText("");

                // Retour à l'écran principal
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
