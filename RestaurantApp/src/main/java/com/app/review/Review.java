package com.app.review;

import java.util.ArrayList;

public class Review {

    private boolean isPositive;
    private ArrayList<String> contents;

    public Review(boolean isPositive, ArrayList<String> contents){
        this.isPositive = isPositive;
        this.contents = contents;
    }

    public boolean getIsPositive(){
        return isPositive;
    }

    public ArrayList<String> getContents(){
        return contents;
    }

    public void setIsPositive(boolean isPositive){
        this.isPositive = isPositive;
    }

    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }

    public void fillContents(String word){
        contents.add(word);
    }

    public void trimList(){

    }


}