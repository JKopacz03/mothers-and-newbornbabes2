package org.kopacz;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.kopacz.dto.DayOfTheWeekWithBirthdaysResponse;
import org.kopacz.dto.GirlsWithSameNameLikeMotherResponse;
import org.kopacz.dto.HighestKidResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class Kid {
    private Long id;
    private Character sex;
    private String name;
    private LocalDate birthday;
    private Integer weight;
    private Double height;
    private Mother mother;

    @Override
    public String toString() {
        return "Kid{" +
                "id=" + id +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", height=" + height +
                ", mother=" + mother.getName() +
                '}';
    }

    public static HighestKidResponse getHighestBoy(List<Kid> kids){


        Kid kid = kids.stream()
                .filter(kid1 -> kid1.getSex() == 's')
                .max(Comparator.comparing(Kid::getHeight))
                .orElseThrow();

        return HighestKidResponse.builder()
                .name(kid.getName())
                .height(kid.getHeight())
                .build();
    }

    public static HighestKidResponse getHighestGirl(List<Kid> kids){

        Kid kid = kids.stream()
                .filter(kid1 -> kid1.getSex() == 'c')
                .max(Comparator.comparing(Kid::getHeight))
                .orElseThrow();

        return HighestKidResponse.builder()
                .name(kid.getName())
                .height(kid.getHeight())
                .build();
    }

    public static DayOfTheWeekWithBirthdaysResponse getDayOfTheWeekWithMostBirths(List<Kid> kids){

        List<DayOfWeek> dayOfWeek = kids.stream()
                .map(kid -> kid.getBirthday().getDayOfWeek())
                .toList();

        List<DayOfTheWeekWithBirthdaysResponse> dayOfTheWeekWithBirthdayResponses =  new ArrayList<>();
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.MONDAY, Collections.frequency(dayOfWeek, DayOfWeek.MONDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.TUESDAY, Collections.frequency(dayOfWeek, DayOfWeek.TUESDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.WEDNESDAY, Collections.frequency(dayOfWeek, DayOfWeek.WEDNESDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.THURSDAY, Collections.frequency(dayOfWeek, DayOfWeek.THURSDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.FRIDAY, Collections.frequency(dayOfWeek, DayOfWeek.FRIDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.SATURDAY, Collections.frequency(dayOfWeek, DayOfWeek.SATURDAY)));
        dayOfTheWeekWithBirthdayResponses.add(new DayOfTheWeekWithBirthdaysResponse(DayOfWeek.SUNDAY, Collections.frequency(dayOfWeek, DayOfWeek.SUNDAY)));

        DayOfTheWeekWithBirthdaysResponse dayOfTheWeekWithMostBirths = dayOfTheWeekWithBirthdayResponses.stream()
                .max(Comparator.comparing(DayOfTheWeekWithBirthdaysResponse::getAmountOfBirths))
                .get();

        return dayOfTheWeekWithMostBirths;
    }

    public static List<GirlsWithSameNameLikeMotherResponse> getGirlsWithSameNameLikeMother(List<Kid> kids){

        List<GirlsWithSameNameLikeMotherResponse> girlsWithSameNameLikeMother = kids.stream()
                .filter(kid1 -> kid1.getSex() == 'c')
                .filter(kid1 -> kid1.getMother().getName().equalsIgnoreCase(kid1.getName()))
                .map(kid -> new GirlsWithSameNameLikeMotherResponse(kid.getName(), kid.getBirthday()))
                .toList();

        return girlsWithSameNameLikeMother;
    }
}
