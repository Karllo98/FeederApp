package pl.karllo.feederapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class NewScheduleActivity extends AppCompatActivity {

    private TimePickerDialog picker;
    private EditText textTime;
    private SeekBar seekBarAmount;
    private Button buttonSave;
    private int fieldHour;
    private int fieldMinute;
    private int fieldAmount;
//    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
//    private ScheduleInfo scheduleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        initialize();

        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(NewScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                fieldHour = sHour;
                                fieldMinute = sMinute;
                                textTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] split = fileContent.split(":");
//                String hour = employeeNameEdt.getText().toString();
                ScheduleInfo scheduleInfo = new ScheduleInfo();
                scheduleInfo.setFeedingHour(fieldHour);
                scheduleInfo.setFeedingMinute(fieldMinute);
                scheduleInfo.setAmount(fieldAmount);
                new FirebaseHelper().addSchedule(scheduleInfo, new FirebaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ScheduleInfo> schedules, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewScheduleActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fieldAmount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

//    private void addData(int hour, int minute, int amount) {
        // below 3 lines of code is used to set
        // data in our object class.
//        scheduleInfo.setFeedingHour(hour);
//        scheduleInfo.setFeedingMinute(minute);
//        scheduleInfo.setAmount(amount);

        // we are use add value event listener method
        // which is called with database reference.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
//                String key = databaseReference.push().getKey();
//                databaseReference.child(key).setValue(scheduleInfo);

                // after adding this data we are showing toast message.
//                Toast.makeText(NewScheduleActivity.this, "data added", Toast.LENGTH_SHORT).show();
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
//                Toast.makeText(NewScheduleActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void initialize() {
        textTime = findViewById(R.id.textTime);
        seekBarAmount = findViewById(R.id.seekBarAmount);
        buttonSave = findViewById(R.id.buttonUpdate);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("ScheduleInfo");
//        scheduleInfo = new ScheduleInfo();
    }
}