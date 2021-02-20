package ru.javawebinar.topjava.controllers.meals;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface BaseMealsController {

    List<Meal> getAll();

    Meal getById(int id);

    Meal save(int id, Meal meal);

    Meal create();

    boolean delete(int id);
}
