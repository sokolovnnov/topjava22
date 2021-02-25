package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private static final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, SecurityUtil.authUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        else {
            Meal m = repository.get(meal.getId());
            if (m.getUserId() != userId) return null;
            else return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.debug("Delete {}", id);
        Meal me = repository.get(id);
        if ((me == null) || me.getUserId() != userId) return false;
        else return repository.remove(id) != null;

    }

    @Override
    public Meal get(int id, int userId) {
        Meal me = repository.get(id);
        if ((me == null) || me.getUserId() != userId) return null;
        else return me;
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                     LocalTime endTime) {
        //todo Если запрашивается список и он пустой, не возвращайте NULL! По пустому списку можно легко итерироваться без риска NullPoinException.
        return (repository.get(userId) == null) ? new ArrayList<>() : repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .sorted(Comparator.comparing((Meal meal) -> meal.getDateTime()).reversed())
//                .sorted((mealOne, mealTho) -> {
//                    if (mealOne.getDateTime().isBefore(mealTho.getDateTime())) return 1;
//                    if (mealTho.getDateTime().isBefore(mealOne.getDateTime())) return -1;
//                    return 0;
//                })
                .collect(Collectors.toList());
    }
}

