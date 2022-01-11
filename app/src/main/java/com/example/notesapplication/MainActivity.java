package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    static ArrayList <String> notes;
    static ArrayAdapter <String> arrayAdapter;
    SharedPreferences sharedPreferences;
     @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notesapplication", Context.MODE_PRIVATE);
        ListView listView = findViewById(R.id.listView);
          notes = new ArrayList<String>();
        notes.add("Example note");
        HashSet<String>set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (set==null){
            notes.add("Example note");
        }
        else {
            notes= new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   intent = new Intent(getApplicationContext(),SecondActivity.class);
                   intent.putExtra("noteId",i);
                   startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemToBeDeleted =i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete a note")
                        .setMessage("Are you sure you want to delete the note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToBeDeleted);
                                arrayAdapter.notifyDataSetChanged();
                                HashSet<String> set = new HashSet<String>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

    }
}