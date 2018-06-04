# Adapter

适用 Android 开发，提供 RecyclerView 和 ListView 的通用 Adapter.

## 1. 特性

* LvAdapter 和 SimpleLvAdapter 适用于 ListView，实现了通用的 ViewHolder 和 View 的复用。
* RvAdapter 适用于 RecyclerView，实现了通用的 ViewHolder。
* ChoiceRvAdapter 继承自 RvAdapter，扩展了“单选/多选”的功能。

## 2. 用法举例

LvAdapter:

```java
mAdapter = new LvAdapter() {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_sample;
    }

    @Override
    protected void bindView(@NonNull LvHolder holder, int position) {   
        holder.setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
            .setText(R.id.tv_content, mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
};
```

ChoiceRvAdapter:

```java
private SparseBooleanArray mRecords = new SparseBooleanArray(30);
...
mAdapter = new ChoiceRvAdapter() {        
    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.item_sample;
    }

    @Override
    public void bindView(@NonNull RvHolder holder, int position) {
        holder.getHelper()
            .setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
            .setText(R.id.tv_content, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    protected boolean isSelected(int position) {
        return mRecords.get(position);
    }

    @Override
    protected void updateItem(int position, boolean selected) {
        mRecords.put(position, selected);
    }
};
mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.MULTIPLE);
```

## 3. 使用方法

(1) 在项目的 build.gradle 中配置仓库地址：

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

(2) 添加项目依赖：

```groovy
	dependencies {
	        implementation 'com.github.ccolorcat:Adapter:v1.0.0'
	}
```

## 4. 其它说明

* 适用于 ListView 的 LvAdapter 实现了 View 的复用，其实现使用了 LvHolder.getRoot().setTag(final Object tag)，故不应再调用此方法，但 LvHolder.getRoot.setTag(int key, final Object tag) 不受影响。
* 含有 "Simple" 字样的 Adapter 适用于同一类数据的显示，含有 "Fixed" 字样的 Adapter 在创建时数据应已初始化，一旦创建其内部数据不可更改。
* 如果需要 RecyclerView 的 Adapter 的单/复选功能，而数据又无需变动，推荐优选使用 FixedSimpleChoiceRvAdapter，足够简单。