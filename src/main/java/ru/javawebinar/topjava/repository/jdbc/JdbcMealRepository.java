package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private final static BeanPropertyRowMapper<Meal> rowMapper1 = BeanPropertyRowMapper.newInstance(Meal.class);
    private final RowMapper<Meal> rowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.rowMapper = new RowMapper<Meal>() {
            @Override
            public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
                Meal meal = new Meal();
                meal.setCalories(rs.getInt("calories"));
                meal.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setId(rs.getInt("id"));
                return meal;
            }
        };
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("userId", userId)
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        System.out.println(mapSqlParameterSource);

        if (meal.isNew()) {
            Number newId = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource);
            meal.setId(newId.intValue());

        } else
            if (namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:dateTime, " +
                "description=:description, calories=:calories WHERE id=:id AND user_id=:userId", mapSqlParameterSource) == 0)
                     {  return null;}
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        //todo проверить Бинпропертироумепер
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", rowMapper, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override //fixme добавить сортировку
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", rowMapper1, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND date_time BETWEEN ? AND ? ORDER BY " +
                        "date_time DESC",
                rowMapper, userId, Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime));
    }
}
