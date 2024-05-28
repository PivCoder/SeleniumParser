package Parser.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect
public class Day {
    private String dayOfWeak;
    private List<Discipline> disciplineList;

    public Day() {
    }

    public Day(String dayOfWeak, List<Discipline> disciplineList) {
        this.dayOfWeak = dayOfWeak;
        this.disciplineList = disciplineList;
    }

    public String getDayOfWeak() {
        return dayOfWeak;
    }

    public void setDayOfWeak(String dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public void setDisciplineList(List<Discipline> disciplineList) {
        this.disciplineList = disciplineList;
    }
}
