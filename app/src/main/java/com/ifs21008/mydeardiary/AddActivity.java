package com.ifs21008.mydeardiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSave;
    ImageButton btnHome;
    TextInputEditText tiFileName, tiNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setTitle("Add Notes");

        btnSave = findViewById(R.id.btnSave);
        tiFileName = findViewById(R.id.tiFileName).findViewById(R.id.textInput1);
        tiNotes = findViewById(R.id.tiNotes).findViewById(R.id.textInput2);
        btnHome = findViewById(R.id.btnHome);

        btnSave.setOnClickListener(this);
        btnHome.setOnClickListener(this);
    }

    void addFile() {
        String fileName = String.valueOf(tiFileName.getText());
        String notes = String.valueOf(tiNotes.getText());

        File file = new File(getFilesDir(), fileName);
        FileOutputStream outputStream = null;

        if(fileName.equals("") || notes.equals("")){
            Toast.makeText(this, "All field is mandatory!", Toast.LENGTH_LONG).show();
        } else{
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, true);
                outputStream.write(notes.getBytes());
                outputStream.flush();
                outputStream.close();

                Toast.makeText(this, "Successfully adding your new notes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSave ){
            addFile();
        }else if(v.getId() == R.id.btnHome){
            startActivity(new Intent(AddActivity.this, MainActivity.class));
        }
    }
}