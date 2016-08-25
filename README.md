# StepNaviBar
自定义的步骤流程控件

## Capture 效果截图
![](https://github.com/Mr-lin930819/StepNaviBar/raw/master/capture/port_captrue.png)

![](https://github.com/Mr-lin930819/StepNaviBar/raw/master/capture/land_capture.png)

## 使用方法
#### 1.在xml布局中添加StepNavigateBar控件
例如：

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:paddingBottom="@dimen/activity_vertical_margin"
        android:paddingLeft="@dimen/activity_horizontal_margin"
        android:paddingRight="@dimen/activity_horizontal_margin"
        android:paddingTop="@dimen/activity_vertical_margin"
        tools:context="me.cviews.mrlin.stepnavibardemo.MainActivity">
    
        <me.cviews.mrlin.stepnavibar.StepNavigateBar
            android:layout_width="match_parent"
            android:layout_height="30dp"
            android:id="@+id/step_view_blue"
            android:layout_marginTop="25dp"
            app:fillColor="@android:color/holo_blue_light"      <!-- 已完成步骤的填充色 -->
            app:labelColor="@android:color/white"               <!-- 已完成步骤的字体色 -->
            app:noFillColor="@android:color/white"              <!-- 未完成步骤的填充色 -->
            app:pendingLabelColor="@android:color/darker_gray"  <!-- 未完成步骤的字体色 -->
            app:borderColor="@android:color/holo_blue_light"    <!-- 步骤的边框色 -->
            app:radius="8" app:stepSpace="6"/>                  <!-- 边框圆角 、 步骤间隔-->
            <!-- 还有textMarginStart：字体距左边的偏移;labelSize：字体大小--> 
    </RelativeLayout>
#### 2.在Activity的java文件中设置步骤数据

    StepNavigateBar stepBarBlue;
    stepBarBlue = (StepNavigateBar) findViewById(R.id.step_view_blue);
    //使用String[]设置步骤
    String[] steps = new String[]{"Step1", "Step2", "Step3", "Step4"};
    stepBarBlue.setStepNames(steps);
    //或者直接传n个String也可以
    //stepBarPurple.setStepNames("step1", "step2", "step3", "step4", "step5");
#### 3.设置当前进行的步骤

    stepBarBlue.setCurrentStep(1);
