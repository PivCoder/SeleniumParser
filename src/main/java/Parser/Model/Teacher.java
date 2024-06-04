package Parser.Model;

import java.util.List;

public class Teacher {
    private String teacherName;
    private List<Day> dayList;
    private int weekHours;

    public Teacher() {

    }

    public Teacher(String teacherName, List<Day> dayList, int weekHours) {
        this.teacherName = teacherName;
        this.dayList = dayList;
        this.weekHours = weekHours;
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
        this.weekHours = weekHours;
    }
}
