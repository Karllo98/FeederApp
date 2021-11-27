package pl.karllo.feederapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.List;

public class ScheduleDetailsActivity extends AppCompatActivity {

    private EditText textTime;
    private SeekBar seekBarAmount;
    private Button buttonUpdate;
    private Button buttonDelete;

    private String key;
    private String feedingHour;
    private String feedingMinute;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);
        initialize();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScheduleDetailsActivity.this, "toast", Toast.LENGTH_LONG).show();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseHelper().deleteSchedule(key, new FirebaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ScheduleInfo> schedules, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(ScheduleDetailsActivity.this, "Schedule has been deleted", Toast.LENGTH_LONG).show();
//                        finish(); return;
                    }
                });
            }
        });
    }

    private void initialize() {
        key = getIntent().getStringExtra("key");
        feedingHour = getIntent().getStringExtra("feedingHour");
        feedingMinute = getIntent().getStringExtra("feedingMinute");
        amount = getIntent().getStringExtra("amount");

        textTime = findViewById(R.id.textTime);
        seekBarAmount = findViewById(R.id.seekBarAmount);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
    }
}