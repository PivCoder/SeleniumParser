package org.example;

import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Model.Teacher;
import Parser.Util.DateFormatter;
import Parser.Util.JXLSConvertor;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

        Teacher teacher = new Teacher();
        String name = "default";
        try {
            byte[] windows1251Bytes = "Преподаватель №".getBytes("Windows-1251");
            name = new String(windows1251Bytes, StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        teacher.setTeacherName(name + 1);
        teacher.setDayList(dayList);

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        teacherList.add(teacher);
        teacherList.add(teacher);

        DateFormatter dateFormatter = new DateFormatter();
        LocalDate startReportDate = dateFormatter.formatDate("10.10.2023");
        LocalDate endReportDate = dateFormatter.formatDate("20.10.2023");

        jxlsConvertor = new JXLSConvertor(teacherList, startReportDate, endReportDate);
        jxlsConvertor.convert();
    }
}
