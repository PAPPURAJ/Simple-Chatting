package com.blogspot.prajbtc.simplechatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    FirebaseDatabase database;
    DatabaseReference myRef,hisref;
    String TAG="===MSG===";
    MediaPlayer mediaPlayer;
    RecyclerView recyclerView;
   MyAdapter myAdapter;
   SharedPreferences sharedPreferences;
   Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.et);
        recyclerView=findViewById(R.id.recy);
        sharedPreferences=this.getSharedPreferences("Profile",MODE_PRIVATE);

        if(sharedPreferences.getString("id","-1")=="-1"){
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(R.layout.account_dialog);
            final AlertDialog alertDialog=builder.create();
            alertDialog.show();
            alertDialog.findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a=((EditText)alertDialog.findViewById(R.id.phone)).getText().toString();
                    if(a.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please input the number!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    sharedPreferences.edit().putString("id",a).apply();
                    menu.findItem(R.id.id).setTitle("Hi "+a);
                    alertDialog.dismiss();
                }
            });
        }



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new MyAdapter(this,new ArrayList<Item>());

        recyclerView.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        String r=sharedPreferences.getString("id","-1");
        if(r!="-1")
        myRef = database.getReference(r);

         mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.tone);


    }

    public void set(View view){
        String temp=((EditText)findViewById(R.id.receiverPhnEt)).getText().toString();
        if(temp.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Input is blank!",Toast.LENGTH_SHORT).show();
            return;
        }

        hisref = database.getReference(temp);
        addToFire();
        Toast.makeText(getApplicationContext(),"Receiver set!",Toast.LENGTH_SHORT).show();
    }




    public void send(View view){
        String temp=editText.getText().toString();
        if(temp.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Input is blank!",Toast.LENGTH_SHORT).show();
            return;
        }

        writeToFire(temp);
        myAdapter.addItem(new Item(true,temp)); editText.setText("");
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.setAdapter(myAdapter);
    }




    void addToFire(){
        hisref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mediaPlayer.start();
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                myAdapter.addItem(new Item(false,value));
                recyclerView.clearOnChildAttachStateChangeListeners();
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    public void writeToFire(String s){
        myRef.setValue(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        this.menu=menu;  menu.findItem(R.id.id).setTitle("Hi "+sharedPreferences.getString("id",null));
        return true;
    }
int i=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(i++%2==0){
            menu.findItem(R.id.id).setTitle("Hi "+sharedPreferences.getString("id",null));
        }
        else
            menu.findItem(R.id.id).setTitle("Hi user");
        return true;
    }
}