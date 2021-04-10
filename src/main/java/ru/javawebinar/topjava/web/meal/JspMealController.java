package ru.javawebinar.topjava.web.meal;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller

public class JspMealController extends AbstractMealController {


    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/meals/filter")
    public String getFilteredMeals(Model model, @RequestParam Map<String, String> param) {
        LocalDate startDate = parseLocalDate(param.get("startDate"));
        LocalDate endDate = parseLocalDate(param.get("endDate"));
        LocalTime startTime = parseLocalTime(param.get("startTime"));
        LocalTime endTime = parseLocalTime(param.get("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "/meals";
    }

    @GetMapping("/meals/delete/{id}")
    public String del(@PathVariable int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/updater/{id}")
    public String updater(Model model, @PathVariable int id) {
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "update");
        model.addAttribute("formHeader", "Edit meal");
        return "mealForm";
    }

    @PostMapping("/meals/update/{id}")
    public String update(@PathVariable int id, @RequestParam Map<String, String> par) {
        Meal meal = new Meal(id, LocalDateTime.parse(par.get("dateTime")), par.get("description"),
                Integer.parseInt(par.get(
                        "calories")));
        super.update(meal, id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/creater")
    public String creater(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }


    @PostMapping("/meals/create")
    public String create(@RequestParam Map<String, String> par) {
        Meal meal = new Meal(LocalDateTime.parse(par.get("dateTime")), par.get("description"),
                Integer.parseInt(par.get("calories")));
        super.create(meal);
        return "redirect:/meals";
    }
}
