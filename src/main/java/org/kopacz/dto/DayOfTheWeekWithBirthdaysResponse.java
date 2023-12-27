package org.kopacz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
public class DayOfTheWeekWithBirthdaysResponse {
    private DayOfWeek dayOfWeek;
    private Integer amountOfBirths;
}
