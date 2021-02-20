package ru.javawebinar.topjava.controllers.meals;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.meals.HashMapRepo;

import java.util.ArrayList;
import java.util.List;

public class MemoryMealsController implements BaseMealsController {

    private HashMapRepo hashMapRepo = new HashMapRepo();

    @Override
    public List<Meal> getAll() {
        return hashMapRepo.getAll();
    }

    @Override
    public Meal getById(int id) {
        return hashMapRepo.get(id);
    }

    @Override
    public Meal save(int id, Meal meal) {
        hashMapRepo.save(id, meal);
        return getById(id);
    }

    @Override
    public Meal create() {
        return hashMapRepo.create();
    }

    @Override
    public boolean delete(int id) {

        return hashMapRepo.delete(id)!=null;
    }
}
