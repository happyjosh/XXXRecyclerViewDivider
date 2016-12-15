package com.jph.xxxrecyclerviewdivider.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jph.xxxrecyclerviewdivider.LinearItemDecoration;

/**
 * Created by jph on 2016/12/15.
 */
public class LinearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.divider_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new LinearItemDecoration(this, R.color.red,
                R.dimen.divider, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new VerticalAdapter());
    }
}
