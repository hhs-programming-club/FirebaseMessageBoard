package com.hhsprogramming.firebasemessageboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private  ArrayList<String> messages=new ArrayList<String>();

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


        //READING DATABASE


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("FISH:" + dataSnapshot.child("messages").getChildrenCount());
                for(DataSnapshot s: dataSnapshot.child("messages").getChildren()){
                    messages.add(s.child("name").getValue().toString()+ ": " + s.child("message").getValue().toString());
                    updateView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("1", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        database.addValueEventListener(postListener);

        //END OF DATABASE READING
    }

    private void updateView() {
        TextView t = (TextView) findViewById(R.id.messages_view);
        StringBuilder sb = new StringBuilder();
        for(String s: messages){
            sb.append(s + "\n");
        }
        t.setText(sb.toString());
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
