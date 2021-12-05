package pl.karllo.feederapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:ss");
    GraphView graphView;
    LineGraphSeries graphSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        initialize();

        graphView.getGridLabelRenderer().setNumHorizontalLabels(4);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {

                if (isValueX)
                    return simpleDate.format(new Date((long) value));
                else
                    return super.formatLabel(value, isValueX);
            }
        });
    }

    private void initialize() {
        graphView = findViewById(R.id.graph);

        graphSeries = new LineGraphSeries();
        graphView.addSeries(graphSeries);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("statsTable");
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dataPoints = new DataPoint[(int) snapshot.getChildrenCount()];
                int i = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StatsValue statsValue = dataSnapshot.getValue(StatsValue.class);

                    dataPoints[i] = new DataPoint(statsValue.getTimeValue(), statsValue.getAmount());
                    i++;
                }

                graphSeries.resetData(dataPoints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}