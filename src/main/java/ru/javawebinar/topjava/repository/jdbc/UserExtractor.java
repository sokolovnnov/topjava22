package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserExtractor implements ResultSetExtractor<Map<String, User>> {
    private static final Logger log = LoggerFactory.getLogger(UserExtractor.class);
    @Override
    public Map<String, User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<String, User> userMap = new HashMap<>();
        while (resultSet.next()){
            User user = new User();
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setPassword("password");
            user.setCaloriesPerDay(Integer.parseInt(resultSet.getString("calories_per_day")));
            user.setRegistered(parsePostgreTimestamp(resultSet.getString("registered")));
            Role role = Role.valueOf(resultSet.getString("role"));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userMap.merge(user.getEmail(), user, (User old, User newU) ->{
                HashSet<Role> r = new HashSet<>();
                r.addAll(old.getRoles());
                r.addAll(newU.getRoles());
                newU.setRoles(r);
                return newU;
            } );

        }
        log.info("..{}",userMap);
        return userMap;
    }

    private Date parsePostgreTimestamp(String timeStamp) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 2021-04-12 18:56:32.719868
//        LocalDate localDate;
        try {
            return format.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
