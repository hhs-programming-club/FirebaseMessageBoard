package com.hhsprogramming.firebasemessageboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When postBtn is clicked, call saveMessage()
        Button btn = (Button) findViewById(R.id.postBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage(v);
            }
        });
    }

    public void saveMessage(View v) {
        EditText nameField = (EditText) findViewById(R.id.nameField);
        EditText msgField = (EditText) findViewById(R.id.messageField);

        // Get actual text from the text fields
        String name = nameField.getText().toString();
        String msg = msgField.getText().toString();

        // Write a message to the database
        Message message = new Message(name, msg);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("messages").push().setValue(message);
    }
}
