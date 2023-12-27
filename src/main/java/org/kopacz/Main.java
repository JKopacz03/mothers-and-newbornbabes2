package org.kopacz;

import org.kopacz.dto.DayOfTheWeekWithBirthdaysResponse;
import org.kopacz.dto.GirlsWithSameNameLikeMotherResponse;
import org.kopacz.dto.HighestKidResponse;
import org.kopacz.dto.StartResponse;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        StartResponse start = Mother.start();
        List<Mother> mothers = start.getMothers();
        List<Kid> kids = start.getKids();

//        1.  Podaj imię i wzrost najwyższego chłopca oraz imię i wzrost najwyższej dziewczynki.
        HighestKidResponse highestBoy = Kid.getHighestBoy(kids);
        System.out.println(highestBoy);

        HighestKidResponse highestGirl = Kid.getHighestGirl(kids);
        System.out.println(highestGirl);

//        name=Oskar, height=62.0
//        name=Maja, height=61.0

//        2. W którym dniu tygodnia urodziło się najwięcej dzieci? Podaj dzien tygodnia i liczbe dzieci.

        DayOfTheWeekWithBirthdaysResponse dayOfTheWeekWithMostBirths = Kid.getDayOfTheWeekWithMostBirths(kids);
        System.out.println(dayOfTheWeekWithMostBirths);

//        dayOfWeek=SATURDAY, amountOfBirths=34

//       3. Podaj imiona kobiet w wieku poniżej 25 lat, które urodziły dzieci o wadze powyżej 4000 g.

        List<String> mothersWithChildWeightsOver4000 = Mother.getMothersWithChildWeightsOver4000(mothers);
        System.out.println(mothersWithChildWeightsOver4000);

//        Barbara, Eliza, Janina, Maria, Marzena, Paulina

//       4. Podaj imiona i daty urodzenia dziewczynek, które odziedziczyły imię po matce.

        List<GirlsWithSameNameLikeMotherResponse> girlsWithSameNameLikeMother = Kid.getGirlsWithSameNameLikeMother(kids);
        System.out.println(girlsWithSameNameLikeMother);

//        name=Anna, birthday=1999-11-21,
//		  name=Wiktoria, birthday=1999-11-20

//        5. Znajdz matki które urodziły bliźnięta.

        List<Mother> mothersWithTwins = Mother.getMothersWithTwins(mothers);
        System.out.println(mothersWithTwins);

//        Mother(id=1, name=Agata, age=25, kids=[
        //		Kid{id=75, sex=s, name='Kacper',
        //		birthday=1999-11-23, weight=2100,
        //		height=54.0, mother=Agata},
        //		Kid{id=139, sex=s, name='Adam',
        //		birthday=1999-11-23, weight=1950,
        //		height=54.0, mother=Agata}]),
//		Mother(id=25, name=Anna, age=28, kids=[
        //		Kid{id=57, sex=s, name='Kacper',
        //		birthday=1999-11-21, weight=2150,
        //		height=51.0, mother=Anna},
        //		Kid{id=72, sex=s, name='Dominik',
        //		birthday=1999-11-21, weight=2010,
        //		height=52.0, mother=Anna}]),
//		Mother(id=41, name=Beata, age=22, kids=[
        //		Kid{id=90, sex=c, name='Wiktoria',
        //		birthday=1999-12-09, weight=1940,
        //		height=52.0, mother=Beata},
        //		Kid{id=97, sex=c, name='Olga',
        //		birthday=1999-12-09, weight=1890,
        //		height=51.0, mother=Beata}]),
//		Mother(id=115, name=Lidia, age=26, kids=[
        //		Kid{id=137, sex=c, name='Izabela',
        //		birthday=1999-11-22, weight=2160,
        //		height=52.0, mother=Lidia},
        //		Kid{id=138, sex=c, name='Sonia',
        //		birthday=1999-11-22, weight=2100,
        //		height=53.0, mother=Lidia}]),
//		Mother(id=121, name=Marcelina, age=32, kids=[
        //		Kid{id=136, sex=s, name='Mateusz',
        //		birthday=1999-11-19, weight=2500,
        //		height=51.0, mother=Marcelina},
        //		Kid{id=144, sex=s, name='Adam',
        //		birthday=1999-11-19, weight=2260,
        //		height=53.0, mother=Marcelina}]),
//		Mother(id=152, name=Sabrina, age=20, kids=[
        //		Kid{id=120, sex=s, name='Rafał',
        //		birthday=1999-12-12, weight=2060,
        //		height=53.0, mother=Sabrina},
        //		Kid{id=121, sex=c, name='Katarzyna',
        //		birthday=1999-12-12, weight=2010,
        //		height=55.0, mother=Sabrina}])]

    }
}