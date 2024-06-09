package org.example;

import Parser.Analyser.TeacherDisciplinesAnalyser;
import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Model.Teacher;
import Parser.Util.DateFormatter;
import Parser.Util.DisciplineTypeExtractor;
import Parser.Util.JXLSConvertor;
import Parser.Util.StringIntoWindows1251Convertor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JXLSTest {
    JXLSConvertor jxlsConvertor;

    @Test
    public void convertTest() {
        Discipline discipline = new Discipline();
        discipline.setHourStart("1");
        discipline.setHourEnd("2");
        discipline.setDiscipline("Программирование");
        discipline.setClassroom(StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лекционные занятия"));
        discipline.setTeacher("teach");

        Discipline discipline2 = new Discipline();
        discipline2.setHourStart("1");
        discipline2.setHourEnd("2");
        discipline2.setDiscipline("Теория электроцепей");
        discipline2.setClassroom(StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Практические"));
        discipline2.setTeacher("teach");

        Discipline discipline3 = new Discipline();
        discipline3.setHourStart("1");
        discipline3.setHourEnd("2");
        discipline3.setDiscipline("Программирование");
        discipline3.setClassroom(StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Экзамен бла бла бла"));
        discipline3.setTeacher("teach");

        List<Discipline> disciplineList = new ArrayList<>();
        disciplineList.add(discipline);
        disciplineList.add(discipline2);
        disciplineList.add(discipline3);

        Map<String, Integer> disciplineCounter = DisciplineTypeExtractor.countDisciplines(disciplineList);

        for (Map.Entry<String, Integer> entry : disciplineCounter.entrySet()) {
            String counter =
                    StringIntoWindows1251Convertor
                            .convertIntoWindows1251InUTF8(entry.getKey() + ": " + entry.getValue());
            System.out.println(counter);
        }

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
        String name = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Преподаватель №");
        teacher.setTeacherName(name + 1);
        teacher.setDayList(dayList);

        String lecture = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лекционные"),
                practice = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Практические"),
                labs = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лабораторные"),
                exam = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Экзамен"),
                test = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Зачет");
        teacher.setLectureHours(disciplineCounter.getOrDefault(lecture, 0) * 2);
        teacher.setPractiseHours(disciplineCounter.getOrDefault(practice, 0) * 2);
        teacher.setLabHours(disciplineCounter.getOrDefault(labs, 0) * 2);
        teacher.setExamHours(disciplineCounter.getOrDefault(exam, 0) * 2);
        teacher.setTestHours(disciplineCounter.getOrDefault(test, 0) * 2);

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        teacherList.add(teacher);
        teacherList.add(teacher);

        TeacherDisciplinesAnalyser teacherDisciplinesAnalyser = new TeacherDisciplinesAnalyser(teacher);
        Map<String, Integer> disciplinesHoursCounter = teacherDisciplinesAnalyser.countDisciplinesHours();

        for (Map.Entry<String, Integer> entry : disciplinesHoursCounter.entrySet()) {
            String counter =
                    StringIntoWindows1251Convertor
                            .convertIntoWindows1251InUTF8(entry.getKey() + ": " + entry.getValue());
            System.out.println(counter);
        }

        teacher.setDisciplinesHours(disciplinesHoursCounter);

        DateFormatter dateFormatter = new DateFormatter();
        LocalDate startReportDate = dateFormatter.formatDate("10.10.2023");
        LocalDate endReportDate = dateFormatter.formatDate("20.10.2023");

        jxlsConvertor = new JXLSConvertor(teacherList, startReportDate, endReportDate);
        jxlsConvertor.convert();
    }
}
