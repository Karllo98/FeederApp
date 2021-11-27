package pl.karllo.feederapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<ScheduleInfo> schedules = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<ScheduleInfo> schedules, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ScheduleInfo");
    }

    public void readSchedules(final DataStatus dataStatus) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schedules.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    ScheduleInfo schedule = keyNode.getValue(ScheduleInfo.class);
                    schedules.add(schedule);
                }
                dataStatus.DataIsLoaded(schedules,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addSchedule(ScheduleInfo scheduleInfo, final DataStatus dataStatus){
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(scheduleInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateSchedule(String key, ScheduleInfo scheduleInfo, final DataStatus dataStatus){
        databaseReference.child(key).setValue(scheduleInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();

            }
        });
    }

    public void deleteSchedule(String key, final DataStatus dataStatus){
        databaseReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }
}
