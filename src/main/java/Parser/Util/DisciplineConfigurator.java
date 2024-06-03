package Parser.Util;

import Parser.Model.Discipline;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DisciplineConfigurator {
    Discipline discipline;

    public DisciplineConfigurator() {
    }

    public DisciplineConfigurator(Discipline discipline) {
        this.discipline = discipline;
    }

    public Discipline configureDiscipline(String disciplineText) {
        String[] disciplineParts = disciplineText.split("\n");
        discipline.setHourStart(disciplineParts[0]);
        discipline.setHourEnd(disciplineParts[1]);
        discipline.setDiscipline(disciplineParts[2]);
        discipline.setClassroom(disciplineParts[3]);
        discipline.setTeacher(disciplineParts[4]);
        return discipline;
    }

    public Discipline configureDisciplineWithTeacherNumber(String disciplineText, int teacherNumber) {
        String[] disciplineParts = disciplineText.split("\n");
        discipline.setHourStart(disciplineParts[0]);
        discipline.setHourEnd(disciplineParts[1]);
        discipline.setDiscipline(disciplineParts[2]);
        discipline.setClassroom(disciplineParts[3]);
        discipline.setTeacher(new String("Преподаватель №".getBytes(Charset.forName("Windows-1251")),
                StandardCharsets.UTF_8) + teacherNumber);
        return discipline;
    }
}
