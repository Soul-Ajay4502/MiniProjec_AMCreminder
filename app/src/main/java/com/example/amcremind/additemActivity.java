package com.example.amcremind;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class additemActivity extends AppCompatActivity {

    private EditText itemname,itemren,itemprice,itemloc,itemtot;
    public static TextView resulttextview,itemid;
    Button  additemtodatabase,scan;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        //  databaseReferencecat = FirebaseDatabase.getInstance().getReference("Items");
        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        itemname = findViewById(R.id.name);
        itemren= findViewById(R.id.ren);
        itemprice = findViewById(R.id.p1);
        itemid= findViewById(R.id.pid);
        itemloc=findViewById(R.id.loc);
        itemtot=findViewById(R.id.tot);


        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });
    }
    public  void additem(){
        String itemnameValue = itemname.getText().toString();
        String itemrenValue = itemren.getText().toString();
        String itempriceValue = itemprice.getText().toString();
        String itemidValue = itemid.getText().toString();
        String itemlocValue=itemloc.getText().toString();
        String itemtotValue=itemtot.getText().toString();


        if(!TextUtils.isEmpty(itemidValue)&&!TextUtils.isEmpty(itemtotValue)&&!TextUtils.isEmpty(itemlocValue)&&!TextUtils.isEmpty(itemnameValue)&&!TextUtils.isEmpty(itemrenValue)&&!TextUtils.isEmpty(itempriceValue)){

            Items items = new Items(itemidValue,itemnameValue,itemrenValue,itempriceValue,itemlocValue,itemtotValue);
            databaseReference.child(itemidValue).setValue(items);
            itemren.setText("");
            itemname.setText("");
            itemid.setText("");
            itemprice.setText("");
            itemloc.setText("");
            itemtot.setText("");

            Toast.makeText(additemActivity.this,itemnameValue+" Added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(additemActivity.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
        }
    }
}
