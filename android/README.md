

# SharePhoto APP

- 开发者：1900300819卢林军，1900301236谢浚霖

## Important!!!!

```
版权所有（c）2021 ZeroRains，pommespeter

反课设抄袭许可证-version 0.6

1、如果你想使用本项目的代码来交课设，
那么请尽可能在完善本项目的基础上进行使用，
因为本项目其实还有部分模块写得比较懒需要继续完善。(BUG还很多）
因为本项目代码比较乱，复用性太差，可读性不强，稳定性太差，所以参考价值不大。

2、一行不改甚至只改个100-200行代码就加上学号和名字说是你做的，
这种情况是是不被允许的！

3、上文所指的课设，包括诸如毕设，课程设计，课程结束考核等，
诸如此类情况而违反上述条例使用该程序的代码，都是不被允许的。
```

# work summary

##  2021.8.21

1. 完成了基本的登录界面搭建

2. 完成帐号，密码输入

3. 检查，点击，跳转事件设置完成

4. 初步成果图(后期图片会更换)

   ![image-20210821123342521](https://gitee.com/zeroRains/drawing-bed/raw/master/20210821123344image-20210821123342521.png)

5. 工具栏界面制作完成，如图下图

   ![image-20210821220512332](https://gitee.com/zeroRains/drawing-bed/raw/master/20210821220515image-20210821220512332.png)

6. 工具栏点击逻辑制作完成(主要是动画切换部分，点中下面的图标会进行一定的颜色变化，这里用了select的xml类型)

## 2021.8.22

1. 完成了四个内容的Fragment切换关系

2. 在首页中添加了tabLayout+viewpage+fragment的形式，并解决了从首页到频道再切换成首页时，tabLayout+viewpage+fragment显示不正常的问题(解决方案，在首页fragment中使用一个ViewHolder，然后存储好第一次配置好的tabLayout和fragments)

3. 完成RecycleView Item设计

4. 完成了在viewpager+fragment的内容中使用RecycleView实现图片内容加载

5. 使用CardView显示内容

6. 完成h自适应显示图像，利用瀑布布局获得更好的效果

7. 实现了itema点击和status点击的简单逻辑

   ![image-20210822213853049](https://gitee.com/zeroRains/drawing-bed/raw/master/20210822213854image-20210822213853049.png)

   

## 2021.8.23

1. 完成了点击图片查看详细页面的界面结构，设计完成(除了回复栏)

```xml
<ImageView
                android:id="@+id/detail_photo"
                android:layout_width="wrap_content"
                android:layout_height="400dp"
                android:layout_marginTop="10dp"
                android:adjustViewBounds="true"
                android:src="@drawable/icon"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="parent" />

            <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/detail_status"
                android:layout_width="0dp"
                android:layout_height="35dp"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="8dp"
                android:layout_marginRight="20dp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"

                app:layout_constraintTop_toBottomOf="@id/detail_photo">

                <LinearLayout
                    android:id="@+id/detail_zan"
                    android:layout_width="90dp"
                    android:layout_height="wrap_content"
                    app:layout_constraintStart_toStartOf="parent">

                    <ImageView
                        android:layout_width="wrap_content"
                        android:layout_height="match_parent"
                        android:adjustViewBounds="true"
                        android:src="@drawable/zan1" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="match_parent"
                        android:text="666"
                        android:textSize="26dp" />
                </LinearLayout>

                <LinearLayout
                    android:id="@+id/detail_love"
                    android:layout_width="90dp"
                    android:layout_height="wrap_content"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintStart_toEndOf="@id/detail_zan">

                    <ImageView
                        android:layout_width="wrap_content"
                        android:layout_height="match_parent"
                        android:adjustViewBounds="true"
                        android:src="@drawable/star" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="match_parent"
                        android:text="666"
                        android:textSize="26dp" />
                </LinearLayout>

                <ImageView
                    android:id="@+id/detail_transition"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_marginBottom="5dp"
                    android:src="@drawable/transition"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />
            </androidx.constraintlayout.widget.ConstraintLayout>

            <View
                android:id="@+id/detail_divider"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:layout_marginTop="8dp"
                android:background="#aaa"
                app:layout_constraintTop_toBottomOf="@id/detail_status" />

            <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/detail_description_container"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="8dp"
                android:layout_marginRight="20dp"
                app:layout_constraintTop_toBottomOf="@id/detail_divider">

                <TextView
                    android:id="@+id/detail_description_title"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="描述"
                    android:textSize="36sp"
                    android:textStyle="bold"
                    app:layout_constraintTop_toTopOf="parent" />

                <TextView
                    android:id="@+id/detail_description"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="描述性语言描述性语言描述性语言描述性语言描述性语言描述性语言描述性语言描述性语言"
                    android:textSize="28sp"
                    app:layout_constraintTop_toBottomOf="@id/detail_description_title" />
            </androidx.constraintlayout.widget.ConstraintLayout>

            <TextView
                android:id="@+id/detail_remark_title"
                android:layout_width="match_parent"
                android:layout_height="35dp"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="15dp"
                android:layout_marginRight="20dp"
                android:text="评论"
                android:textColor="#aaaaaa"
                android:textSize="30sp"
                android:textStyle="bold"
                app:layout_constraintTop_toBottomOf="@+id/detail_description_container" />

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:layout_marginTop="10dp"
                android:background="#aaa"
                app:layout_constraintTop_toBottomOf="@+id/detail_remark_title" />

            <androidx.recyclerview.widget.RecyclerView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:scrollbars="none"
                app:layout_constraintTop_toBottomOf="@id/detail_remark_title" />
```



