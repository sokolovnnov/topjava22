package ru.javawebinar.topjava.controller.meal;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MemoryMealController implements BaseMealController
{
    private static final Logger log = getLogger(MemoryMealController.class);
//    private List<Meal> meals = MealsUtil.mealsListInit();

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(new ArrayList<>(MealsUtil.meals.values()),
                LocalTime.of(0, 0, 0), LocalTime.of(23, 59,59), 2000);
    }

    @Override
    public boolean deleteById(int id) {
        log.debug("before delete meal:" + id);
        return MealsUtil.meals.remove(id) != null;
    }

    @Override
    public Meal getById(int id) {
        return MealsUtil.meals.get(id);
    }

    @Override
    public void save(int id, Meal meal) {
        MealsUtil.meals.replace(id, meal);
    }


}
