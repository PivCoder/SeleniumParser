package Parser.Analyser;

import Parser.Model.Day;

import java.util.List;

public class DayAnalyser {

    Day day;
    List<Day> dayList;

    public DayAnalyser(Day day) {
        this.day = day;
    }

    public DayAnalyser(List<Day> dayList) {
        this.dayList = dayList;
    }

    public int analyseDay() {
        return day.getDisciplineList().size() * 2;
    }

    public int analyseListOfDays(){
        int teacherLoad = 0;

        for (Day day : dayList){
            teacherLoad += day.getDisciplineList().size() * 2;
        }

        return teacherLoad;
    }
}
