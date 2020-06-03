package com.blogspot.prajbtc.simplechatting;

public class Item {
    private boolean isA;
    private String message;
    public Item(boolean isA,String message){
        this.isA=isA;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isA() {
        return isA;
    }
}
