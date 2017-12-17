package com.example.deh3215.firechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    User user;
    private final String TAG="FireBase";
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();//first node

        //myRef.setValue("Hello, World!");
        //writeNewUser("1234", "Jimmy", 46);
        String[] id={"1", "2"};
        User[] users = new User[id.length];
        for(int i=0;i<users.length;i++)
            users[i] = new User();

        users[0].setName("Jimmy");
        users[0].setAge(46);

        users[1].setName("Tommy");
        users[1].setAge(23);

        for(int i=0;i<users.length;i++)
            writeNewUser((i+1), users[i].getName(), users[i].getAge());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    //一次寫進一個Object物件資料
    private void writeNewUser(int userId, String name, int age) {
        User user = new User(name, age);
        myRef.child("users").child(""+userId).setValue(user);
    }
}
