package com.austindroids.recycleaustin.service;


import com.austindroids.recycleaustin.beans.RecyclableItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by dutch on 6/18/14.
 */

public class CheckRecyclableService {
    public CheckRecyclableService() {
        this.allItems = loadAll();
        System.out.println(this.allItems);
    }

    public List<String> getCategories(){
        TreeSet categories = new TreeSet();
        for (RecyclableItem item : allItems)
            categories.add(item.getCategory());
        return new ArrayList(categories);
    }

    public List<String> getSubcategories(String category) {
        TreeSet subcats = new TreeSet(); //sorted
        //return Arrays.asList(categories);
        for (RecyclableItem item : allItems)
            if (category.equals(item.getCategory()))
                subcats.add(item.getSubCategory());
        return new ArrayList(subcats);
    }

    public RecyclableItem getRecyclableItem(String category, String subcategory) {
        for (RecyclableItem item : allItems)
            if (category.equals(item.getCategory()) && subcategory.equals(item.getSubCategory()) )
                return item;
        return null;
    }

    String [] rawData = {
            "category1|subcategory1|True|some data",
            "category1|subcategory2|True|some more data",
            "category2|subcategory1|True|even some nsome data"
    };
    List <RecyclableItem> allItems = null;


    private List <RecyclableItem> loadAll(){
        List <RecyclableItem> allItems = new ArrayList<RecyclableItem>();
        System.out.println(rawData[0] + "here");
        for (String e : rawData) {
            String[] tokens = e.split("\\|");
            RecyclableItem item = new RecyclableItem();
            System.out.println(tokens[1]);
            System.out.println(e);
            item.setCategory(tokens[0]);
            item.setSubCategory(tokens[1]);
            item.setRecyclable(new Boolean(tokens[2]));
            item.setDescription(tokens[3]);
            allItems.add(item);
        }
        return allItems;
    }

    public static void main(String [] args) {
        System.out.println("Run main.");
    }
}
