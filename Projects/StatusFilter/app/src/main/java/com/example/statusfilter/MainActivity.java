package com.example.statusfilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;
    ArrayList<Users> userList;
    private boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userList = new ArrayList<>();
        userList.add(new Users("Murad", 1, 1986080326));
        userList.add(new Users("Jack", 3, 1887486520));
        userList.add(new Users("James", 2, 1655279653));
        userList.add(new Users("Ben", 1, 1962109930));
        userList.add(new Users("William", 4, 1976080326));
        setFilterAdapter();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.filter);
        item.setChecked(isChecked);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                if (isChecked){
                    Collections.sort(userList, (o1, o2) -> {
                        if (o1.getStatusCode() == o2.getStatusCode()) {
                            return Long.compare(o2.getTimeStamp(), o1.getTimeStamp());
                        }
                        return Integer.compare(o2.getStatusCode(), o1.getStatusCode());
                    });
                }else {
                    Collections.sort(userList, (o1, o2) -> {
                        if (o1.getStatusCode() == o2.getStatusCode()) {
                            return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
                        }
                        return Integer.compare(o1.getStatusCode(), o2.getStatusCode());
                    });
                }
                adapter.notifyDataSetChanged();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFilterAdapter() {
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(this, userList);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}