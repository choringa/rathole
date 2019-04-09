package co.com.indi.rathole.rathole.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.com.indi.rathole.rathole.Adapters.CustomLogEntrysAdapter;
import co.com.indi.rathole.rathole.R;
import co.com.indi.rathole.rathole.Utils.SQLiteManager.LogDictionaryOpenHelper;


public class LogFragment extends Fragment {

    private RecyclerView recyclerView;
    public LogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        recyclerView = view.findViewById(R.id.log_fragment_recycler_view);
        LogDictionaryOpenHelper logDictionaryOpenHelper = new LogDictionaryOpenHelper(getContext());
        List<String> logEntrys = logDictionaryOpenHelper.readFromDBAllLogEntrys();
        CustomLogEntrysAdapter customLogEntrysAdapter = new CustomLogEntrysAdapter(logEntrys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(customLogEntrysAdapter);
        return view;
    }

}
