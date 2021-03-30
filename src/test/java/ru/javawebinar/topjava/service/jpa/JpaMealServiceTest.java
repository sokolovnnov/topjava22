package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Prifiles.JPA;

@ActiveProfiles(JPA)
public class JpaMealServiceTest extends MealServiceTest {
}
