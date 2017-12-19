package com.example.deh3215.firechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    User user;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference reference_contacts;
    EditText et1, et2;
    ListView lv;

    private final String TAG="FireBase";
    private final String NAME="name";
    private final String AGE="age";
    static long count=0;
    //ArrayAdapter<String> adapter;
    SimpleAdapter adapter;
    //private ArrayList<User> list = new ArrayList<>();
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.editText1);
        et2 = (EditText)findViewById(R.id.editText2);
        lv = (ListView)findViewById(R.id.listView);
        //===========write data==============
//        myRef.setValue("Hello, World!");
//        writeNewUser(1234, "Jimmy", 46);
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
//        adapter = new ArrayAdapter<User>(this,
//                        android.R.layout.simple_list_item_1,
//                        android.R.id.text1);

        adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_2,
                new String[] { NAME, AGE },
                new int[] { android.R.id.text1, android.R.id.text2 } );

        reference_contacts = FirebaseDatabase.getInstance().getReference("users");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    HashMap<String,String> item = new HashMap<String,String>();
                    item.put( NAME, ds.child(NAME).getValue().toString());
                    item.put( AGE,ds.child(AGE).getValue().toString());
                    //adapter.add(ds.child("name").getValue().toString());
                    user = ds.getValue(User.class);
                    list.add(item);
                    lv.setAdapter(adapter);
                }
                count = dataSnapshot.getChildrenCount();
                Log.d("Count", "count="+count+" getKey="+dataSnapshot.getKey());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //一次寫進一個Object物件資料
    private void writeNewUser(long userId, String name, int age) {
        User user = new User(name, age);
        reference_contacts.child(""+userId).setValue(user);
    }

    private void removeUser(long userId, String name, int age)   {
         //User user = new User(name, age);
         //reference_contacts.child(""+userId).remove();
    }

    public void onClick(View v)   {
        switch(v.getId()) {
            case R.id.button2:
                String name = et1.getText().toString();
                String age = et2.getText().toString();
                if (!name.equals("") && !age.equals(""))
                    writeNewUser(count+1, name, Integer.valueOf(age));
                et1.setText("");
                et2.setText("");
                break;
            case R.id.button4:
                String str = et1.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("users").orderByChild("name").equalTo(str);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                        lv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                break;
        }
    }
}