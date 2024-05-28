package Parser.Model;

public class Discipline {
    private String hourStart, hourEnd, discipline, classroom, teacher;

    public Discipline() {
    }

    public Discipline(String hourStart, String hourEnd, String discipline, String classroom, String teacher) {
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.discipline = discipline;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
