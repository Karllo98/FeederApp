package pl.karllo.feederapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewConfiguration {
    private Context context;
    private ScheduleAdapter scheduleAdapter;
    public void setConfig(RecyclerView recyclerView, Context mContext, List<ScheduleInfo> scheduleInfos, List<String> keys) {
        context = mContext;
        scheduleAdapter = new ScheduleAdapter(scheduleInfos,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(scheduleAdapter);
    }

    class ScheduleView extends RecyclerView.ViewHolder{
        private TextView textViewHour;
        private TextView textViewSeparator;
        private TextView textViewMinute;
        private TextView textViewAmount;
        private String key;

        public ScheduleView(ViewGroup parent){
            super(LayoutInflater.from(context).inflate(R.layout.schedule_list, parent, false));

            textViewHour = itemView.findViewById(R.id.textViewHour);
            textViewSeparator = itemView.findViewById(R.id.textViewSeparator);
            textViewMinute = itemView.findViewById(R.id.textViewMinute);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScheduleDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("feedingHour", String.valueOf(textViewHour));
                    intent.putExtra("feedingMinute", String.valueOf(textViewMinute));
                    intent.putExtra("amount", String.valueOf(textViewAmount));

                    context.startActivity(intent);
                }
            });
        }

        public void bind(ScheduleInfo scheduleInfo, String key) {
            textViewHour.setText(String.valueOf(scheduleInfo.getFeedingHour()));
            textViewSeparator.setText(":");
            textViewMinute.setText(String.valueOf(scheduleInfo.getFeedingMinute()));
            textViewAmount.setText(String.valueOf(scheduleInfo.getAmount()));
            this.key = key;
        }
    }

    class ScheduleAdapter extends RecyclerView.Adapter<ScheduleView> {
        private List<ScheduleInfo> scheduleInfoList;
        private List<String> keys;

        public ScheduleAdapter(List<ScheduleInfo> scheduleInfoList, List<String> keys) {
            this.scheduleInfoList = scheduleInfoList;
            this.keys = keys;
        }

        @Override
        public ScheduleView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ScheduleView(parent);
        }

        @Override
        public void onBindViewHolder(ScheduleView holder, int position) {
            holder.bind(scheduleInfoList.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {
            return scheduleInfoList.size();
        }
    }
}
