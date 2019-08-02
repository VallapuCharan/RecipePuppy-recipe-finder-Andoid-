/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

/**
 * Created by valla on 24-03-2019.
 */
public class Ingredients {

    String ingredients;

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredients='" + ingredients + '\'' +
                '}';
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredients(String ingredients) {

        this.ingredients = ingredients;
    }
}
