package org.fossasia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.fossasia.activities.EventDetailsActivity;
import org.fossasia.adapters.ScheduleAdapter;
import org.fossasia.db.DatabaseManager;
import org.fossasia.model.FossasiaEvent;

import java.util.ArrayList;

/**
 * Created by Abhishek on 20/02/15.
 */
public class ScheduleListFragment extends SmoothListFragment {

    private ArrayList<FossasiaEvent> events;
    private String track;

    public static Fragment newInstance(int day, String track) {
        Fragment fragment = new ScheduleListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DAY", day);
        bundle.putString("TRACK", track);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int day = getArguments().getInt("DAY") + 13;
        track = null;
        track = getArguments().getString("TRACK");
        DatabaseManager dbManager = DatabaseManager.getInstance();
        if (track != null) {
            events = dbManager.getEventsByDateandTrack(day + "/03/2015", track);
        } else {
            events = dbManager.getEventsByDate(day + "/03/2015");
        }
        setListAdapter(new ScheduleAdapter(getActivity(), events));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        long idNew = (long) v.getTag();
        Toast.makeText(getActivity(), "Position: " + idNew, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), EventDetailsActivity.class);
        intent.putExtra("event", events.get(position));
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (track != null) {
            setEmptyText("No event on this day related to " + track);
        } else {
            setEmptyText("No event on this day");
        }
    }
}
