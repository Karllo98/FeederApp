package pl.karllo.feederapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatisticsActivity extends AppCompatActivity {

    private TextView textInfo, textFoodAmount;
    private DatabaseReference dbRef;
    private int foodAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initialize();



        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StatsValue statsValue = dataSnapshot.getValue(StatsValue.class);
                    foodAmount += statsValue.amount;
                    System.out.println(statsValue.amount);
                    System.out.println(foodAmount);
                    textFoodAmount.setText(String.valueOf(foodAmount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void initialize() {
        textInfo = findViewById(R.id.textInfo);
        textFoodAmount = findViewById(R.id.textFoodAmount);
        dbRef = FirebaseDatabase.getInstance().getReference().child("statsTable");
    }
}