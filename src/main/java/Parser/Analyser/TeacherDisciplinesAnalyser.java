package Parser.Analyser;

import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Model.Teacher;

import java.util.HashMap;
import java.util.Map;

public class TeacherDisciplinesAnalyser {
    private final Teacher teacher;
    private final Map<String, Integer> disciplinesHours = new HashMap<>();

    public TeacherDisciplinesAnalyser(Teacher teacher) {
        this.teacher = teacher;
    }

    public Map<String, Integer> countDisciplinesHours(){
        for (Day day : teacher.getDayList()){
            for (Discipline discipline : day.getDisciplineList()){
                String disciplineName = discipline.getDiscipline();
                disciplinesHours.put(disciplineName, disciplinesHours.getOrDefault(disciplineName, 0) + 1);
            }
        }

        return disciplinesHours;
    }
}
