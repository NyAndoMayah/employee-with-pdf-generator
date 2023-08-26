package com.example.prog4.model.utilities;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
@Component
public class Age {
    public static int getAge(LocalDate date){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        return period.getYears();
    }
}
