package com.softworks.origami.velibfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.softworks.origami.velibfinder.AccessModel.StationFetcher;
import com.softworks.origami.velibfinder.Components.StationListAdapter;
import com.softworks.origami.velibfinder.Models.Records;
import com.softworks.origami.velibfinder.Models.Station;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private StationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new StationListAdapter(this, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        StationFetcher.getInstance().getStations();

        StationFetcher.getInstance().stationList.subscribe( stations -> {
            mAdapter.setStations(stations);
            mAdapter.original = Station.copy(stations);
            findViewById(R.id.loading).setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        super.onCreateOptionsMenu(menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.members:
                showMembers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        /*
        displayedStations.records.clear();
        for (Records record : mAdapter.getStations().records)
        {
            String address =  record.fields.getAddress();
            if (address.toLowerCase().contains(query.toLowerCase()))
                displayedStations.records.add(record);

        }
        mAdapter.setStations(displayedStations);
        */
        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Station displayedStations = mAdapter.getStations();
        if (displayedStations != null)
        {
            displayedStations.records.clear();
            for (Records record : mAdapter.original.records)
            {
                String address =  record.fields.address;
                if (address.toLowerCase().contains(newText.toLowerCase()))
                    displayedStations.records.add(record);
            }
            mAdapter.setStations(displayedStations);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("pos", itemPosition);
        startActivity(intent);
    }


    private void showMembers()
    {
        Intent intent = new Intent(this, MembersActivity.class);
        startActivity(intent);
    }
}
