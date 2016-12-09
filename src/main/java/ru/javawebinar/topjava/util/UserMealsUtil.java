package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> actualCaloriesPerDay = mealList.stream()
                .collect(Collectors.toMap(
                        um -> um.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        (calories1, calories2) -> calories1 + calories2));
        //System.out.println(actualCaloriesPerDay);

        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();

        mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(um -> userMealWithExceedList.add(new UserMealWithExceed(
                        um.getDateTime(), um.getDescription(), um.getCalories(),
                        actualCaloriesPerDay.get(um.getDateTime().toLocalDate()) <= caloriesPerDay)));
        //userMealWithExceedList.forEach(um -> System.out.println(um.dateTime + " " + um.description + " " + um.exceed));

        return userMealWithExceedList;
    }
}
