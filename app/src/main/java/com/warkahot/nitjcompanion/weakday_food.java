package com.warkahot.nitjcompanion;

/**
 * Created by warkahot on 20-Sep-16.
 */
public class weakday_food {

    String breakfast,lunch,snack,dinner;

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getSnack() {
        return snack;
    }

    public String getDinner() {
        return dinner;
    }

    public weakday_food() {

    }

    public weakday_food(String breakfast, String lunch, String snack, String dinner) {

        this.breakfast = breakfast;
        this.lunch = lunch;
        this.snack = snack;
        this.dinner = dinner;
    }
}
