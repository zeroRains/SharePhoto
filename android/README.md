

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
2. (今天剩下的时间设计数据库去了)
3. 完成个人主页UI结构设计
4. 完成个人主页滑动隐藏功能
5. ![image-20210826114044559](https://gitee.com/zeroRains/drawing-bed/raw/master/20210826114046image-20210826114044559.png)


## 2021.8.24

1. 完成了图片点击进入详细页面的效果
2. 完成回复栏的设计
3. 完成评论内容的item
4. 在详细页面的按钮上创建了点击事件和简单的UI变化
5. 完成评论在RecycleView上的加载过程
6. 在Item上的点赞按钮中添加简单的点击事件和UI变化
7. ![image-20210824191616362](https://gitee.com/zeroRains/drawing-bed/raw/master/20210824191617image-20210824191616362.png)
8. 完成个人主页基本逻辑设计
9. 完成RecyclerView使用的Adapter设计以及item布局

## 2021.8.25

1. 发布页面设计完成
2. 修复了输入法遮挡了EditView内容的bug
3. 可以使用Recyclview在发布页面中展示图片
4. 可以点击加号添加图片，并展示在发布页面中
5. 发布页面逻辑设计完成
6. ![image-20210825162035350](https://gitee.com/zeroRains/drawing-bed/raw/master/20210825162036image-20210825162035350.png)![image-20210825162149559](https://gitee.com/zeroRains/drawing-bed/raw/master/20210825162150image-20210825162149559.png)

## 2021.8.26

1. 完成频道页面的设计
2. 完成频道单个项目的设计
3. 可以使用recyvleView显示每个频道
4. 完成每个频道的点击事件
5. 单击频道后可以进入一个Activity详细展示频道内容
6. 频道内容的事物可以被点击且可以进入详细页面
7. ![image-20210826113530183](https://gitee.com/zeroRains/drawing-bed/raw/master/20210826113531image-20210826113530183.png)![image-20210826113856785](https://gitee.com/zeroRains/drawing-bed/raw/master/20210826113859image-20210826113856785.png)