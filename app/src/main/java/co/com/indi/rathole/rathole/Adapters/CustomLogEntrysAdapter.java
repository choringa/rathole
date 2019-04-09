package co.com.indi.rathole.rathole.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.com.indi.rathole.rathole.R;

public class CustomLogEntrysAdapter extends RecyclerView.Adapter<CustomLogEntrysAdapter.LogEntryViewHolder> {

    private List<String> logEntrys;

    public CustomLogEntrysAdapter(List<String> logEntrys) {
        this.logEntrys = logEntrys;
    }

    @NonNull
    @Override
    public LogEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_entry_recycler_item, parent, false);
        return new LogEntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LogEntryViewHolder holder, int position) {
        holder.logEntryText.setText(logEntrys.get(position));
    }

    @Override
    public int getItemCount() {
        return logEntrys.size();
    }

    public class LogEntryViewHolder extends RecyclerView.ViewHolder {
        public TextView logEntryText;
        public LogEntryViewHolder(View view){
            super(view);
            logEntryText = view.findViewById(R.id.log_entry_recycler_item_date_text);
        }
    }
}
