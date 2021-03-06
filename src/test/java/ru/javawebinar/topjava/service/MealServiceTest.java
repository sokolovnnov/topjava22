package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(100002, UserTestData.USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(meal01);
    }

    @Test
    public void delete() {
        mealService.delete(100002, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(100002, UserTestData.USER_ID));
    }

    @Test
    public void deleteInaccessible() {
        assertThrows(NotFoundException.class, () -> mealService.delete(100002, NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = mealService.getBetweenInclusive(LocalDate.parse("2020-01-15"),
                LocalDate.parse("2020-01-15"), UserTestData.USER_ID);
        assertThat(meals).usingRecursiveFieldByFieldElementComparator().isEqualTo(Arrays.asList(meal04, meal03,
                meal02, meal01));
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(UserTestData.USER_ID);
        assertThat(meals).usingRecursiveFieldByFieldElementComparator().isEqualTo(Arrays.asList(meal04, meal03,
                meal02, meal01));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, UserTestData.USER_ID);
        assertThat(updated).usingRecursiveComparison().isEqualTo(mealService.get(100002, UserTestData.USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = mealService.create(getNew(), UserTestData.USER_ID);
        int id = newMeal.getId();
        assertThat(newMeal).usingRecursiveComparison().isEqualTo(mealService.get(id, UserTestData.USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null, meal01.getDateTime(), "duplicate", 100), UserTestData.USER_ID));
    }


}