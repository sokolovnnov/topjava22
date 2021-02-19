package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.controller.meal.MemoryMealController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    MemoryMealController memoryMealController;

    @Override
    public void init() throws ServletException {
        memoryMealController = new MemoryMealController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doPost method");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("datetimeInForm"));
        log.debug("dateTime parsed successful: " + localDateTime);
        String description = request.getParameter("descriptionInForm");
        log.debug("description parsed successful: ");
        Integer calories = Integer.parseInt(request.getParameter("caloriesInForm"));
        log.debug("calories parsed successful: " + calories);
        int id = Integer.parseInt(request.getParameter("id"));
        log.debug("id parsed successful: " + id);
        Meal meal = new Meal(localDateTime,description,calories);
        meal.setId(id);
        memoryMealController.save(id, meal);
        response.sendRedirect("meals");
//        request.getRequestDispatcher("meals").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in mealservlet");
        String action;
//        try {
            action = request.getParameter("action");
//        }catch (NullPointerException nullPointerException){
//            action = "null";
//        }
        log.debug("Value of \"request.getParameter(\"action\") is: " + action);
        if(action == null) {
            action = "all";
            log.debug("in section if..");
        }

        switch (action) {
            case "null":
                log.debug("in switch section \"null\")");
            case "all":
                log.debug("in switch section \"all\")");
                List<MealTo> meals = memoryMealController.getAll();
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "update":

                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = memoryMealController.getById(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("in switch section \"delete\"");
                memoryMealController.deleteById(Integer.parseInt(request.getParameter("id")));
                List<MealTo> meals1 = memoryMealController.getAll();
                response.sendRedirect("meals");
                //                request.setAttribute("meals", meals1);
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "add":

                break;
        }
    }
}
