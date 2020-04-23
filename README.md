# Adapter

适用 Android 开发，提供 RecyclerView, ListView 和 ViewPager 的通用 Adapter.

## 1. 特性

* LvAdapter 和 SimpleLvAdapter 适用于 ListView，实现了通用的 ViewHolder 和 View 的复用。
* RvAdapter 适用于 RecyclerView，实现了通用的 ViewHolder。
* ChoiceRvAdapter 继承自 RvAdapter，扩展了“单选/多选”的功能。
* AutoChoiceRvAdapter 继承自 ChoiceRvAdapter，实现自动记录单/复选的功能。
* VpAdapter 适用于 ViewPager，实现并扩展部分方法，使得其使用方式与 RvAdapter 类似。
* 其它的类不再一一列举，主要是扩展或简化了以上列举的类。

## 2. 用法举例

RvAdapter:

```java
new SimpleRvAdapter<Repo>(items, R.layout.item_repo) {
  @Override
  public void bindView(@NonNull RvHolder holder, @NonNull Repo data) {
    holder.getHelper()
      .setText(R.id.tv_name, data.getName())
      .setText(R.id.tv_description, data.getDescription())
      .setText(R.id.tv_license, data.getLicense().getName())
      .setText(R.id.tv_html_link, Html.fromHtml(data.getHtmlUrl()));
  }
};
```

或

```java
AdapterHelper.newSimpleRvAdapter(
  items, 
  new CourseViewBinder()
);


public class CourseViewBinder implements ViewBinder2<Course> {
  @Override
  public int itemLayout() {
    return R.layout.item_course;
  }

  @Override
  public void bindView(@NonNull AdapterViewHolder holder, Course data) {
    holder.setText(R.id.tv_serial_number, Integer.toString(holder.getPosition()))
      .setText(R.id.tv_name, data.getName())
      .setText(R.id.tv_description, data.getDescription());
  }
}
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
    implementation 'com.github.ccolorcat:Adapter:v3.3.0'
}
```

## 4. 其它说明

* 适用于 ListView 的 LvAdapter 实现了 View 的复用，其实现使用了 LvHolder.getRoot().setTag(final Object tag)，故使用中不应再调用此方法，但 LvHolder.getRoot.setTag(int key, final Object tag) 不受影响。
* 含有 "Simple" 字样的 Adapter 适用于同一类数据的显示，含有 "Fixed" 字样的 Adapter 在创建时数据应已初始化，一旦创建其内部数据不可更改。
* 如果需要 RecyclerView 的 Adapter 的单/复选功能，而数据又无需变动，推荐优选使用 FixedSimpleChoiceRvAdapter，足够简单。
* 继承 PagerAdapter 时，不建议在其内部缓存 View，如要避免频繁的创建/销毁 View，应考虑 ViewPager.setOffscreenPageLimit(int) 方法，且应注意数值不可过大，以避免占用过多的内存。
* 自 3.x 版本后添加了 AdapterHelper 类，创建单一类型的 Adapter 建议优先使用。
* 自 3.x 版本后，提供了 SingleTypeAdapterHelper 辅助更新 Adapater 的数据和刷新 UI，所有带有 Simple 字样的 Adapter 类均可使用。

## 5. 版本历史

v3.3.0

> 1. 新增 OnChoiceModeChangeListener 以监听选择模式的变化。
> 2. SingleTypeAdapterHelper 新增 clear() 和 justRefreshUI() 两个方法。
> 3. SimpleChoiceRvAdapter 和 SimpleAutoChoiceRvAdapter 新增部分方法和内部抽象监听。

v3.1.0

> 1. 迁移至 AndroidX
> 2. 重构部分代码，优化结构。
> 3. 新增 ViewBinder 接口和 AdapterHelper 类，进一步简化 Adapter 的编写。
> 4. 新增 SingleTypeAdapterHelper 类，辅助更新 Adapter 的数据和刷新 Adapter 的 UI.

v1.2.0

> 优化

v1.1.1

> 1. 添加 ViewHolder.setImageUri()
>
> 2. 升级 build.gradle

v1.1.0

> 添加适用于 ViewPager 的 VpAdapter 和 SimpleVpAdapter.