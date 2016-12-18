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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7,
                GridLayoutManager.VERTICAL,
                false);

//        //需要跨行
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position == 2) {
//                    return 2;
//                }
//
//                if (position == 3) {
//                    return 3;
//                }
//
//                if (position == 6) {
//                    return 4;
//                }
//                if (position == 82) {
//                    return 2;
//                }
//                return 1;
//            }
//        });
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new GridItemDecoration(this, R.drawable.divider,
//                R.dimen.divider, R.dimen.divider));

        GridItemDecoration gridItemDecoration = new GridItemDecoration(this,
                R.drawable.divider_padding_h, R.drawable.divider_padding_v,
                R.dimen.divider, R.dimen.divider);
//        gridItemDecoration.setUnderLayer(GridLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(gridItemDecoration);

        recyclerView.setAdapter(new VerticalAdapter());
    }
}
