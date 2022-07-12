package com.sagesurfer.school.home.main;

import java.util.ArrayList;

public class DrawerMenuList {
    static ArrayList<ModelDrawerMenuListItems> drawerMenuList;
    static ArrayList<ModelDrawerMenuListItems> getDrawerMenuList(){
        drawerMenuList=new ArrayList<>();
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(1),"Home",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(2),"Mood Tracking",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(3),"Journaling",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(4),"Assessment",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(5),"Self-Care",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(5),"Divider",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(6),"Daily Planner",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(7),"Goal Management",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(8),"Proper Nutrition",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(5),"Divider",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(9),"Education / Spiritual",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(10),"Positive Quotes",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(11),"Team Care",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(5),"Divider",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(12),"Notifications",0));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(13),"Support",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(5),"Divider",1));
        drawerMenuList.add(new ModelDrawerMenuListItems(GetDrawableIcons.get(14),"Logout",0));
        return drawerMenuList;
    }
}
