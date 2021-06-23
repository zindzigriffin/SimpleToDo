package com.example.simpetodo;

import android.content.ClipData;
import org.apache.commons.io.FileUtils;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;
public class MainActivity extends AppCompatActivity {
    /*Define a list of strings called items */
    List<String> items;

    /* add a member variable for each view */
    Button button2;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Define each member variable by using findViewById.
        findViewById is a method that finds the View by the ID that it is given */
        button2 = findViewById(R.id.button2);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        //Calls the loadItems method
        loadItems();
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //Notify the adapter that an item has been removed
                itemsAdapter.notifyItemRemoved(position);
                //Toast contains a quick little message to the user. So when the user removes an item
                //from the list the message "Item was removed" will pop up.
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                //Calls the saveItems method
                saveItems();

            }
        };
        //Construct our items adapter
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        //put things in a vertical manner
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //Add item to the model
                items.add(todoItem);
                //Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                //Prints a message to the user when the user adds an item to the todo list
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                // Calls the saveItems method
                saveItems();
            }
        });
    }
    // This method gets the data file.
    private File getDataFile(){
        return new File(getFilesDir(), "data.text");
    }
    //This function will load items by reading everyline of the data line
    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            e.printStackTrace();
            Log.e("MainActivity", "Error reading item", e);
            items = new ArrayList<>();
        }

    }
    //This function saves items by writing them into a data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e){
            e.printStackTrace();
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}