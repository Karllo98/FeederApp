package pl.karllo.feederapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private FloatingActionButton floatingButton;
//    private DatabaseReference dbRef;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initialize();

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, NewScheduleActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initialize() {
        floatingButton = findViewById(R.id.floatingButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        new FirebaseHelper().readSchedules(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ScheduleInfo> schedules, List<String> keys) {
//                findViewById(R.id.scheduleProgressBar).setVisibility(View.GONE);
                new RecyclerViewConfiguration().setConfig(recyclerView, ScheduleActivity.this, schedules, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}