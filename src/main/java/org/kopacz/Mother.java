package org.kopacz;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kopacz.dto.StartResponse;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.Long.valueOf;

@Data
@AllArgsConstructor
public class Mother {
    private static List<Mother> mothers = new ArrayList<>();
    private Long id;
    private String name;
    private Integer age;
    private Set<Kid> kids;

    public Mother(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.kids = new TreeSet<>(Comparator.comparingLong(Kid::getId));
        mothers.add(this);
    }

    public static void start(){

        Path pathOfMothers = Path.of("mamy.txt");
        try (Stream<String> lines = Files.lines(pathOfMothers)) {
            lines.forEach(
                    s -> {
                        String[] mothersParameters = s.split(" ");
                        new Mother(
                                valueOf(mothersParameters[0]),
                                mothersParameters[1],
                                Integer.valueOf(mothersParameters[2]));
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path pathOfKids = Path.of("noworodki.txt");
        try (Stream<String> lines = Files.lines(pathOfKids)) {
            lines.forEach(
                    s -> {
                        String[] kidsParameters = s.split(" ");
                        new Kid(
                                valueOf(kidsParameters[0]),
                                kidsParameters[1].charAt(0),
                                kidsParameters[2],
                                LocalDate.parse(kidsParameters[3]),
                                Integer.valueOf(kidsParameters[4]),
                                Double.valueOf(kidsParameters[5]),
                                findById(mothers, valueOf(kidsParameters[6]))
                        );
                    }
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addKid(Kid kid){
        kids.add(kid);
    }

    public static Mother findById(List<Mother> mothers, Long id){

        return mothers.stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findFirst()
                .get();
    }

    public static List<String> getMothersWithChildWeightsOver4000(){

        return mothers.stream()
                .filter(mother -> mother.getAge() < 25)
                .filter(mother -> mother.hasChildAboveWeight(4000))
                .map(Mother::getName)
                .toList();
    }

    private boolean hasChildAboveWeight(int i) {
        return this.kids.stream()
                .anyMatch(kid -> kid.getWeight() > i);
    }

    public static List<Mother> getMothersWithTwins(){

       return mothers.stream()
               .filter(Mother::hasTwins)
               .toList();
    }

    private boolean hasTwins() {
        return this.kids.stream()
                .collect(Collectors.groupingBy(Kid::getBirthday))
                .values()
                .stream()
                .anyMatch(k -> k.size() > 1);
    }
}
