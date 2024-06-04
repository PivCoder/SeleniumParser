package Parser.Util;

import Parser.Model.Discipline;

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
        return discipline;
    }

    public Discipline configureDisciplineWithTeacherNumber(String disciplineText, int teacherNumber) {
        String[] disciplineParts = disciplineText.split("\n");
        discipline.setHourStart(disciplineParts[0]);
        discipline.setHourEnd(disciplineParts[1]);
        discipline.setDiscipline(disciplineParts[2]);
        discipline.setClassroom(disciplineParts[3]);
        return discipline;
    }
}
