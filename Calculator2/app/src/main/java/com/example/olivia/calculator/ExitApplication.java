package com.example.olivia.calculator;

import android.app.Activity;
import android.app.Application;
import java.util.LinkedList;
import java.util.List;

public class ExitApplication extends Application {

    private List<Activity> activityList=new LinkedList<Activity>();
    private static ExitApplication instance;
    private ExitApplication(){}

    //单例模式获取唯一的exitapplication

    public static ExitApplication getInstance(){
        if(null==instance){
            instance=new ExitApplication();
        }
        return instance;
    }

    //添加activity到容器中

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    //遍历所有的Activiy并finish

    public void exit(){
        for(Activity activity:activityList){
            activity.finish();
        }
        System.exit(0);
    }
}