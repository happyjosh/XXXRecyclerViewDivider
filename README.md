# XXXRecyclerViewDivider
Divider item decoration for RecyclerView

RecyclerView的分割线，支持LinearLayoutManager和GridLayoutManager

**使用方式:**

```java
compile 'com.jph:xxxrecyclerviewdivider:1.1.1'
```

For LieanrLayoutManager:
```java
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new LinearItemDecoration(this, R.color.red,
                R.dimen.divider));
        recyclerView.setAdapter(new VerticalAdapter());
```


For GridLayoutManager:
```java
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
        recyclerView.addItemDecoration(new GridItemDecoration(this, R.drawable.divider,
                R.dimen.divider, R.dimen.divider));

        recyclerView.setAdapter(new VerticalAdapter());
```
