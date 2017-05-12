# XXXRecyclerViewDivider
Divider item decoration for RecyclerView

RecyclerView的分割线，支持LinearLayoutManager和GridLayoutManager

**使用方式:**

```java
compile 'com.jph:xxxrecyclerviewdivider:1.1.3'
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
对特殊的Item不显示分割线
继承对应的ItemDecoration，重写isAllowShowDivider方法
```
@Override
protected boolean isAllowShowDivider(int position, RecyclerView parent) {
    if (特殊样式的Item) {
        //特殊样式Item（比如header/footer）不显示分割线
        //一般通过得到对应的ItemType判断
        return false;
    }
    return super.isAllowShowDivider(position, parent);
}

```
