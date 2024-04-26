package com.ifs21008.mydeardiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDelete, btnEdit;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Your Diary Detail");

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);

        btnBack = findViewById(R.id.btnHome);
        btnBack.setOnClickListener(this);

        // Mendapatkan nama file dari intent
        String fileName = getIntent().getStringExtra("fileName");

        // Mendapatkan TextView dari layout
        TextInputEditText tiFileName = findViewById(R.id.tiFileName).findViewById(R.id.tiFileNameDisplay);
        TextInputEditText tiNotesContents = findViewById(R.id.tiNotes).findViewById(R.id.tiNotesContentDisplay);

        // Mengatur nama file ke TextView
        tiFileName.setText(fileName);

        // Membaca dan menampilkan isi file catatan
        String notesContent = readNotesFromFile(fileName);
        tiNotesContents.setText(notesContent);
    }

    void deleteNotes() {
        TextInputEditText tiFileName = findViewById(R.id.tiFileName).findViewById(R.id.tiFileNameDisplay);
        String fileName = String.valueOf(tiFileName.getText());
        File file = new File(getFilesDir(), fileName);
        if(file.exists()) {
            boolean isDeleted = file.delete();
            if (isDeleted) {
                Toast.makeText(this, "File deleted successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListActivity.class));
            } else {
                Toast.makeText(this, "Failed to delete file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_LONG).show();
        }
    }

    void editFile() {
        TextInputEditText tiFileName = findViewById(R.id.tiFileName).findViewById(R.id.tiFileNameDisplay);
        TextInputEditText tiNotesContents = findViewById(R.id.tiNotes).findViewById(R.id.tiNotesContentDisplay);

        // Get the original filename and modified filename
        String originalFileName = getIntent().getStringExtra("fileName");
        String modifiedFileName = String.valueOf(tiFileName.getText());

        // Get the modified notes content
        String modifiedNotesContent = String.valueOf(tiNotesContents.getText());

        // Check if filename has been modified
        if (!originalFileName.equals(modifiedFileName)) {
            // Create a new file with the modified filename
            File newFile = new File(getFilesDir(), modifiedFileName);

            // Delete the original file if it exists
            File originalFile = new File(getFilesDir(), originalFileName);
            if (originalFile.exists()) {
                originalFile.delete();
            }

            // Write the modified notes content to the new file
            try {
                FileOutputStream fos = new FileOutputStream(newFile);
                fos.write(modifiedNotesContent.getBytes());
                fos.close();
                Toast.makeText(this, "successfull edit your file name", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DetailActivity.this, ListActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to edit your file name", Toast.LENGTH_LONG).show();
            }
        } else {
            // Write the modified notes content to the original file
            try {
                FileOutputStream fos = openFileOutput(originalFileName, MODE_PRIVATE);
                fos.write(modifiedNotesContent.getBytes());
                fos.close();
                Toast.makeText(this, "successfull edit your content", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DetailActivity.this, ListActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to edit your content", Toast.LENGTH_LONG).show();
            }
        }
    }



    private String readNotesFromFile(String fileName) {
        StringBuilder content = new StringBuilder();

        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }

            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDelete ){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Are you sure to delete this diary?");
            builder.setTitle("Delete Confirmation!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                deleteNotes();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if(v.getId() == R.id.btnEdit){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Are you sure to edit this diary?");
            builder.setTitle("Edit Confirmation!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                editFile();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if(v.getId() == R.id.btnHome){
            startActivity(new Intent(DetailActivity.this, ListActivity.class));
        }
    }
}
