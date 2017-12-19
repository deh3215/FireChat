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
    DatabaseReference reference_contacts;
    EditText et1, et2;
    ListView lv;

    private final String TAG="FireBase";
    private final String NAME="name";
    private final String AGE="age";
    static long count=0;
    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        lv = findViewById(R.id.listView);
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
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    HashMap<String,String> item = new HashMap<>();
                    item.put( NAME, ds.child(NAME).getValue().toString());
                    item.put( AGE,ds.child(AGE).getValue().toString());
                    user = ds.getValue(User.class);
                    adapter.notifyDataSetChanged();
                    list.add(item);
                }
                count = dataSnapshot.getChildrenCount();
                //Log.d("Count", "count="+count+" getKey="+dataSnapshot.getKey());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setAdapter(adapter);
    }
    //一次寫進一個Object物件資料
    private void writeNewUser(long userId, String name, int age) {
        user = new User(name, age);
        reference_contacts.child(""+userId).setValue(user);
    }

//    private void removeUser(long userId, String name, int age)   {
//         reference_contacts.child(""+userId).removeValue();
//    }

    public void onClick(View v)   {
        switch(v.getId()) {
            case R.id.button2:
                String name = et1.getText().toString();
                String age = et2.getText().toString();
                if (!name.equals("") && !age.equals(""))
                    writeNewUser(count+1, name, Integer.valueOf(age));
                adapter.notifyDataSetChanged();

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