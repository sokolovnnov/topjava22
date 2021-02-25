package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAllFiltered(HttpServletRequest request) {
        return MealsUtil.getTos(
                service.getAllFiltered(SecurityUtil.authUserId(),
                        getStartDateFromRequest(request),
                        getEndDateFromRequest(request),
                        getStartTimeFromRequest(request),
                        getEndTimeFromRequest(request)),
                SecurityUtil.authUserCaloriesPerDay());
    }

//    public List<MealTo> getAllFilter(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate){
//        return MealsUtil.getFilteredTos(getAll(), SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
//    }

    public void delete(int id) {
        log.debug("Delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
        log.debug("Delete {}", id);
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }

    public Meal update(Meal meal, int id) {
        ValidationUtil.assureIdConsistent(meal, id);
        return service.update(meal, SecurityUtil.authUserId());
    }

    private LocalDate getStartDateFromRequest(HttpServletRequest request) {
        String startDate = request.getParameter("startdatefromform");
        log.debug(startDate);
        return (startDate == null || startDate.equals("")) ? LocalDate.MIN : LocalDate.parse(startDate);
    }

    private LocalDate getEndDateFromRequest(HttpServletRequest request) {
        String endDate = request.getParameter("enddatefromform");
        log.debug(endDate);
        return (endDate == null || endDate.equals("")) ? LocalDate.MAX : LocalDate.parse(endDate);
    }

    private LocalTime getStartTimeFromRequest(HttpServletRequest request) {
        String startTime = request.getParameter("starttimefromform");
        log.debug(startTime);
        return (startTime == null || startTime.equals("")) ? LocalTime.MIN : LocalTime.parse(startTime);
    }

    private LocalTime getEndTimeFromRequest(HttpServletRequest request) {
        String endTime = request.getParameter("endtimefromform");
        log.debug(endTime);
        return (endTime == null || endTime.equals("")) ? LocalTime.MAX : LocalTime.parse(endTime);
    }

}