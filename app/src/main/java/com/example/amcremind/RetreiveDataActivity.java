package com.example.amcremind;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetreiveDataActivity extends AppCompatActivity {

    ListView myListview;
    List<Items> itemsList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data);

        myListview = findViewById(R.id.myListView);
        itemsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for (DataSnapshot sd : snapshot.getChildren()) {
                    Items item = sd.getValue(Items.class);
                    itemsList.add(item);
                }
                ListAdapter adapter = new ListAdapter(RetreiveDataActivity.this, itemsList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}