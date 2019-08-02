/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import java.util.ArrayList;

/**
 * Created by valla on 24-03-2019.
 */
public class Recipe {
    String dishname;

    @Override
    public String toString() {
        return "Recipe{" +
                "dishname='" + dishname + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe(String dishname, ArrayList<Ingredients> ingredients) {

        this.dishname = dishname;
        this.ingredients = ingredients;
    }

    ArrayList<Ingredients> ingredients;
}
