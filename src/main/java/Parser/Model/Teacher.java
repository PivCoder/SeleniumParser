package Parser.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teacher {
    private String teacherName;
    private List<Day> dayList = new ArrayList<>();
    private int weekHours = 0, lectureHours = 0, practiseHours = 0, labHours = 0, testHours = 0, examHours = 0;
    private Map<String, Integer> disciplinesHours;

    public Teacher() {

    }

    public Teacher(String teacherName, List<Day> dayList, int weekHours, int lectureHours, int practiseHours, int labHours, int testHours, int examHours, Map<String, Integer> disciplinesHours) {
        this.teacherName = teacherName;
        this.dayList = dayList;
        this.weekHours = weekHours;
        this.lectureHours = lectureHours;
        this.practiseHours = practiseHours;
        this.labHours = labHours;
        this.testHours = testHours;
        this.examHours = examHours;
        this.disciplinesHours = disciplinesHours;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public int getWeekHours() {
        return weekHours;
    }

    public void setWeekHours(int weekHours) {
        this.weekHours += weekHours;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(int lectureHours) {
        this.lectureHours += lectureHours;
    }

    public int getPractiseHours() {
        return practiseHours;
    }

    public void setPractiseHours(int practiseHours) {
        this.practiseHours += practiseHours;
    }

    public int getLabHours() {
        return labHours;
    }

    public void setLabHours(int labHours) {
        this.labHours += labHours;
    }

    public int getTestHours() {
        return testHours;
    }

    public void setTestHours(int testHours) {
        this.testHours += testHours;
    }

    public int getExamHours() {
        return examHours;
    }

    public void setExamHours(int examHours) {
        this.examHours += examHours;
    }

    public Map<String, Integer> getDisciplinesHours() {
        return disciplinesHours;
    }

    public void setDisciplinesHours(Map<String, Integer> disciplinesHours) {
        this.disciplinesHours = disciplinesHours;
    }
}
