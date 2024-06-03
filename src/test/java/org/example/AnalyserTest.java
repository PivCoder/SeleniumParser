package org.example;

import Parser.Analyser.DayAnalyser;
import Parser.Model.Day;
import Parser.Model.Discipline;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AnalyserTest {
    DayAnalyser dayAnalyser;

    @Test
    public void analyserDayTest(){
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

        dayAnalyser = new DayAnalyser(day);
        System.out.println(dayAnalyser.analyseDay());
    }

    @Test
    public void analyserListOfDaysTest(){
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

        dayAnalyser = new DayAnalyser(dayList);
        System.out.println(dayAnalyser.analyseListOfDays());
    }
}
