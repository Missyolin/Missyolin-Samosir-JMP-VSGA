package com.ifs21008.mydeardiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ListActivity extends AppCompatActivity {

    ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Mendapatkan daftar semua catatan
        List<String> notesList = getAllNotes();

        // Menghubungkan ListView dari layout dengan kode Java
        ListView listView = findViewById(R.id.listView);

        // Membuat adapter untuk menampilkan daftar catatan
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);

        // Mengatur adapter ke ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Mendapatkan nama file yang diklik
            String fileName = notesList.get(position);

            // Membuat Intent untuk membuka NoteDetailActivity
            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        });

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });
    }

    private List<String> getAllNotes() {
        List<String> notesList = new ArrayList<>();
        File[] files = getFilesDir().listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        FileInputStream fis = new FileInputStream(file);

                        // Tambahkan isi catatan ke dalam daftar
                        notesList.add(file.getName());

                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return notesList;
    }

}
