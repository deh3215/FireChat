package com.example.deh3215.firechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //User user;
    private final String TAG="FireBase";
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference reference_contacts;
    EditText et1, et2;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.editText1);
        et2 = (EditText)findViewById(R.id.editText2);
        //===========write data==============
        //myRef.setValue("Hello, World!");
        //writeNewUser("1234", "Jimmy", 46);
//        String[] id={"1", "2"};
//        User[] users = new User[id.length];
//        for(int i=0;i<users.length;i++)
//            users[i] = new User();
//
//        users[0].setName("Jimmy");
//        users[0].setAge(46);
//
//        users[1].setName("Tommy");
//        users[1].setAge(23);
//
//        for(int i=0;i<users.length;i++)
//            writeNewUser((i+1), users[i].getName(), users[i].getAge());
        reference_contacts = FirebaseDatabase.getInstance().getReference("users");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    //adapter.add(ds.child("name").getValue().toString());
                    User user = ds.getValue(User.class);
                    //count++;
                    Log.d("Name", user.getName());
                    Log.d("Age", ""+user.getAge());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                //String value = dataSnapshot.getValue(String.class);
//                //Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }
    //一次寫進一個Object物件資料
    private void writeNewUser(int userId, String name, int age) {
        User user = new User(name, age);
        reference_contacts.child(""+userId).setValue(user);
    }

    private ArrayList<User> list = new ArrayList<>();

    public void onClick(View v)   {
        switch(v.getId()) {
            case R.id.button2:
                //user = new User();
                String name = et1.getText().toString();
                String age = et2.getText().toString();
//                if (!name.equals(""))
//                    user.setName(name);
//                if (!age.equals(""))
//                    user.setAge(Integer.valueOf(age));
                //Log.d("DATA", user.getName()+" "+user.getAge());
                writeNewUser(4, name, Integer.valueOf(age));
                break;
            case R.id.button3:

                break;
        }
    }


}