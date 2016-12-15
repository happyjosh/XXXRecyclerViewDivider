# XXXRecyclerViewDivider
Divider item decoration for RecyclerView

RecyclerView的分割线，支持LinearLayoutManager和GridLayoutManager

**使用方式:**
For LieanrLayoutManager:
```java
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new LinearItemDecoration(this, R.color.red,
                R.dimen.divider, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new VerticalAdapter());
```


For GridLayoutManager:
```java
 recyclerView.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new GridItemDecoration(this, R.drawable.divider,
                R.dimen.divider, R.dimen.divider, 5, GridLayoutManager.VERTICAL));

        recyclerView.setAdapter(new VerticalAdapter());
```
