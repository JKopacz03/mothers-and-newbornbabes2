package org.kopacz;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.kopacz.dto.DayOfTheWeekWithBirthdaysResponse;
import org.kopacz.dto.GirlsWithSameNameLikeMotherResponse;
import org.kopacz.dto.HighestKidResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Setter(AccessLevel.NONE)
public class Kid {
    private static List<Kid> kids = new ArrayList<>();
    private Long id;
    private Character sex;
    private String name;
    private LocalDate birthday;
    private Integer weight;
    private Double height;
    private Mother mother;

    public Kid(Long id, Character sex, String name, LocalDate birthday, Integer weight, Double height, Mother mother) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.mother = mother;
        mother.addKid(this);
        kids.add(this);
    }

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

    public static HighestKidResponse getHighestBoy(){


        Kid kid = kids.stream()
                .filter(kid1 -> kid1.getSex() == 's')
                .max(Comparator.comparing(Kid::getHeight))
                .orElseThrow();

        return HighestKidResponse.builder()
                .name(kid.getName())
                .height(kid.getHeight())
                .build();
    }

    public static HighestKidResponse getHighestGirl(){

        Kid kid = kids.stream()
                .filter(kid1 -> kid1.getSex() == 'c')
                .max(Comparator.comparing(Kid::getHeight))
                .orElseThrow();

        return HighestKidResponse.builder()
                .name(kid.getName())
                .height(kid.getHeight())
                .build();
    }

    public static DayOfTheWeekWithBirthdaysResponse getDayOfTheWeekWithMostBirths(){

        return kids.stream()
                .collect(Collectors.groupingBy(k -> k.getBirthday().getDayOfWeek()))
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(k -> k.getValue().size()))
                .map(k -> new DayOfTheWeekWithBirthdaysResponse(k.getKey(), k.getValue().size()))
                .get();
    }

    public static List<GirlsWithSameNameLikeMotherResponse> getGirlsWithSameNameLikeMother(){

        return kids.stream()
                .filter(kid1 -> kid1.getSex() == 'c')
                .filter(kid1 -> kid1.getMother().getName().equalsIgnoreCase(kid1.getName()))
                .map(kid -> new GirlsWithSameNameLikeMotherResponse(kid.getName(), kid.getBirthday()))
                .toList();
    }
}
