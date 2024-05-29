package org.example;

import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Util.JXLSConvertor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JXLSTest {
    JXLSConvertor jxlsConvertor;

    @Test
    public void convertTest() {
        Discipline discipline = new Discipline();
        discipline.setHourStart("1");
        discipline.setHourEnd("2");
        discipline.setDiscipline("disc");
        discipline.setClassroom("class");
        discipline.setTeacher("teach");

        List<Discipline> disciplineList = new ArrayList<>();
        disciplineList.add(discipline);
        disciplineList.add(discipline);
        disciplineList.add(discipline);

        Day day = new Day();
        day.setDayOfWeak("day");
        day.setDisciplineList(disciplineList);

        List<Day> dayList = new ArrayList<>();
        dayList.add(day);
        dayList.add(day);
        dayList.add(day);
        dayList.add(day);
        dayList.add(day);
        dayList.add(day);

        JXLSConvertor jxlsConvertor = new JXLSConvertor(dayList);
        jxlsConvertor.convert();
        jxlsConvertor.convert();
        jxlsConvertor.convert();
    }
}
