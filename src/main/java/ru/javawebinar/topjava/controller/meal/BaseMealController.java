package ru.javawebinar.topjava.controller.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface BaseMealController {

    List<MealTo> getAll();
    boolean deleteById(int id);

    Meal getById(int id);

    void save(int id, Meal meal);
}
