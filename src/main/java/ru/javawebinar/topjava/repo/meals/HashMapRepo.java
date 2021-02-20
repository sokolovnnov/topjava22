package ru.javawebinar.topjava.repo.meals;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class HashMapRepo {
    private static final Logger log = getLogger(HashMapRepo.class);

    private static AtomicInteger mealIdCounter = new AtomicInteger(0);

    private static ConcurrentHashMap<Integer, Meal> mapRepo = new ConcurrentHashMap<>();
    static {
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020,
                Month.JANUARY, 30, 10, 0),
                "Завтрак", 500));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020,
                Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное " +
                "значение", 100));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mapRepo.put(mealIdCounter.get(), new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getAll(){
        return new ArrayList<Meal>(mapRepo.values());
    }

    public Meal get(int id){
        return mapRepo.get(id);
    }

    public Meal save(int id, Meal meal){
        log.debug("Save meal with id: " + id);
        mapRepo.merge(id, meal,(old, n) -> n);
//        mapRepo.replace(id, meal);
        Meal m = get(id);
        log.debug("Get meal, id = " + m.getId());

        return m;
    }

    public Meal create(){
        Meal meal = new Meal(mealIdCounter.getAndIncrement(), LocalDateTime.now(), "zzz", 111);
        log.debug("Ceraeted meal: id - " + meal.getId() + ", Dtime - " + meal.getDateTime() + ", desc - " +
                meal.getDescription() + ", calories - " + meal.getCalories());
        return meal;
    }

    public Meal delete(int id){
        return mapRepo.remove(id);
    }


}
