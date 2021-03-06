package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {
    public static final Meal meal01 = new Meal(100002, LocalDateTime.parse("2020-01-15T10:00:00"), "Breakfast" , 500 );
    public static final Meal meal02 = new Meal(100003, LocalDateTime.parse("2020-01-15T12:00:00"), "Dinner"    , 500 );
    public static final Meal meal03 = new Meal(100004, LocalDateTime.parse("2020-01-15T16:00:00"), "Lunch"     , 500 );
    public static final Meal meal04 = new Meal(100005, LocalDateTime.parse("2020-01-15T20:00:00"), "Supper"    , 400 );
    public static final Meal meal05 = new Meal(100006, LocalDateTime.parse("2020-01-17T11:00:00"), "Breakfast" , 700 );
    public static final Meal meal06 = new Meal(100007, LocalDateTime.parse("2020-01-17T15:00:00"), "Lunch"     , 800 );
    public static final Meal meal07 = new Meal(100008, LocalDateTime.parse("2020-01-17T21:00:00"), "Supper"    , 900 );

    public static Meal getUpdated(){
        Meal updated = new Meal(meal01);
        updated.setDateTime(LocalDateTime.parse("2020-11-11T11:11:00"));
        updated.setDescription("updated Breakfast");
        updated.setCalories(1111);
        return updated;
    }

    public static Meal getNew(){
        return new Meal(null, LocalDateTime.parse("2020-02-22T22:22:00"), "New Meal", 2222);
    }
}
