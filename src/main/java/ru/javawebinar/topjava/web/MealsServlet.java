package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.controllers.meals.MemoryMealsController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {

    private MemoryMealsController memoryMealsController;
    private static final Logger log = getLogger(MealsServlet.class);

    @Override
    public void init() throws ServletException {
        memoryMealsController = new MemoryMealsController();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "";

        switch (action) {
            case (""):
                log.debug("in ALL case");
                List<MealTo> meals = new ArrayList<>(MealsUtil.filteredByStreams(memoryMealsController.getAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
                request.setAttribute("getall", meals);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
            case ("update"):
                log.debug("in UPDATE case");
                Meal meal1 = memoryMealsController.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal1);
                request.getRequestDispatcher("meal.jsp").forward(request, response);

                break;
            case ("delete"):
                log.debug("in DELETE case");
                int id = Integer.parseInt(request.getParameter("id"));
                memoryMealsController.delete(id);
//                request.getRequestDispatcher("meals").forward(request, response);
                response.sendRedirect("meals");
                break;
            case ("new"):
                log.debug("in NEW case");
                Meal meal = memoryMealsController.create();
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);

                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doPost method");


        Meal meal = new Meal(Integer.parseInt(request.getParameter("idInForm")),
                LocalDateTime.parse(request.getParameter("dateTimeInForm")),
                request.getParameter("descriptionInForm"),
                Integer.parseInt(request.getParameter("caloriesInForm")));
        memoryMealsController.save(Integer.parseInt(request.getParameter("idInForm")), meal);
        response.sendRedirect("meals");


    }


}
