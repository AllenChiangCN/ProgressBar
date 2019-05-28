# ProgressBarView

## 集成

1. 在项目的build.gradle中添加
```gradle
allprojects {
    repositories {
        ...
            maven { url 'https://jitpack.io' }
        }
    }
}
```

2. 在app的build.gradle中添加
```gradle
implementation 'com.github.AllenChiangCN:ProgressBar:1.0.0-beta'
```

## 使用说明

```xml
<cn.allen.progressbarview.CircleProgressBar
    android:id="@+id/circleProgressBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingStart="3dp"
    android:paddingTop="7dp"
    android:paddingEnd="4dp"
    android:paddingBottom="8dp"
    app:currentProgress="30"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:maxProgress="100"
    app:ringBackgroundColor="@android:color/darker_gray"
    app:ringColor="@color/colorAccent"
    app:ringWidth="1dp" />
```

> 自定义属性

| 属性 | 类型 | 说明 |
| --- | --- | --- |
| app:currentProgress | float | 当前进度值 |
| app:maxProgress | float | 最大进度值 |
| app:ringBackgroundColor | color | 未完成部分的颜色 |
| app:ringColor | color | 完成部分的颜色 |
| app:ringWidth | dimension | 圆环的宽度 |

> 可通过代码设置进度

```java
mCircleProgressBar.setCurrentProgress(Float.parseFloat(mEtProgress.getText().toString()), 500L);
```
