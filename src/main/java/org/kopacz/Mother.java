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
    private Long id;
    private String name;
    private Integer age;
    private Set<Kid> kids;

    public Mother(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static StartResponse start(){
        List<Mother> mothers = new ArrayList<>();
        List<Kid> kids = new ArrayList<>();

        Path pathOfMothers = Path.of("D:\\Pobrane\\mamy.txt");
        try (Stream<String> lines = Files.lines(pathOfMothers)) {
            lines.forEach(
                    s -> {
                        String[] mothersParameters = s.split(" ");
                        mothers.add(new Mother(
                                valueOf(mothersParameters[0]),
                                mothersParameters[1],
                                Integer.valueOf(mothersParameters[2])));
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path pathOfKids = Path.of("D:\\Pobrane\\noworodki.txt");
        try (Stream<String> lines = Files.lines(pathOfKids)) {
            lines.forEach(
                    s -> {
                        String[] kidsParameters = s.split(" ");
                        kids.add(new Kid(
                                valueOf(kidsParameters[0]),
                                kidsParameters[1].charAt(0),
                                kidsParameters[2],
                                LocalDate.parse(kidsParameters[3]),
                                Integer.valueOf(kidsParameters[4]),
                                Double.valueOf(kidsParameters[5]),
                                findById(mothers, valueOf(kidsParameters[6]))
                        ));


                    }
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        mothers
                .forEach(m -> {
                    Set<Kid> mothersKids = kids.stream()
                            .filter(kid -> Objects.equals(kid.getMother().getId(), m.getId()))
                            .collect(Collectors.toSet());
                    m.setKids(mothersKids);
                });


        StartResponse startResponse = new StartResponse(mothers, kids);

        return startResponse;
    }


    public static Mother findById(List<Mother> mothers, Long id){
        Mother mother = mothers.stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findFirst()
                .get();

        return mother;
    }

    public static List<String> getMothersWithChildWeightsOver4000(List<Mother> mothers){

        List<Mother> mothersBelow25 = mothers.stream()
                .filter(mother -> mother.getAge() < 25)
                .toList();

        List<String> mothersWithKidsOver4000 = new ArrayList<>();

        mothersBelow25.forEach(mother -> {

            Set<Kid> setOfKids = mother.getKids();
            for (Kid kid: setOfKids) {
                if(kid.getWeight() > 4000){
                    mothersWithKidsOver4000.add(mother.getName());
                    break;
                }
            }
        });

        return mothersWithKidsOver4000;
    }

    public static List<Mother> getMothersWithTwins(List<Mother> mothers){

        List<Mother> mothersWithTwins = new ArrayList<>();

        mothers.stream()
                .forEach(mother -> {
                    Set<Kid> listOfKids = mother.getKids();
                    List<Kid> listOfKidsWithTwins = listOfKids.stream()
                            .collect(Collectors.groupingBy(Kid::getBirthday))
                            .entrySet()
                            .stream()
                            .filter(localDateListEntry -> localDateListEntry.getValue().size() > 1)
                            .flatMap(localDateListEntry -> localDateListEntry.getValue().stream())
                            .toList();
                    if(!listOfKidsWithTwins.isEmpty()){
                        mothersWithTwins.add(mother);
                    }
                });

        return mothersWithTwins;
    }
}
