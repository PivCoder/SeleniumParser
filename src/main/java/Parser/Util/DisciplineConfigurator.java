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
        discipline.setTeacher(disciplineParts[4]);
        return discipline;
    }
}
