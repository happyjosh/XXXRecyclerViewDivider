package com.jph.xxxrecyclerviewdivider.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jph.xxxrecyclerviewdivider.GridItemDecoration;

/**
 * Created by jph on 2016/12/15.
 */
public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.divider_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new GridItemDecoration(this, R.drawable.divider,
                R.dimen.divider, R.dimen.divider, 5, GridLayoutManager.VERTICAL));

        recyclerView.setAdapter(new VerticalAdapter());
    }
}
