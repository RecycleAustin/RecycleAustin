package com.austindroids.recycleaustin.beans;

/**
 * Created by dutch on 6/20/14.
 */
public class RecyclableItem {

    boolean recyclable;
    String description;
    String category;
    String subCategory;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecyclableItem that = (RecyclableItem) o;

        if (!category.equals(that.category)) return false;
        if (subCategory != null ? !subCategory.equals(that.subCategory) : that.subCategory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + (subCategory != null ? subCategory.hashCode() : 0);
        return result;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRecyclable() {
        return recyclable;
    }

    public void setRecyclable(boolean recyclable) {
        this.recyclable = recyclable;
    }
}
