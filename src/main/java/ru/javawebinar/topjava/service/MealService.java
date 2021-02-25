package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    @Autowired
    private MealRepository repository;

    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                     LocalTime endTime) {
        return repository.getAllFiltered(userId, startDate, endDate, startTime, endTime);
    }

    public void delete(int id, int userId) {
        log.debug("Delete {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
        log.debug("Delete {}", id);
    }

    public Meal get(int id, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
            return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}