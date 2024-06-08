package Parser.Util;

import Parser.Model.Discipline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisciplineTypeExtractor {

    public static Map<String, Integer> countDisciplines(List<Discipline> disciplineList) {
        List<String> disciplines = extractListOfDisciplineNames(disciplineList);
        Map<String, Integer> disciplineCount = new HashMap<>();

        for (String discipline : disciplines) {
            String type = extractDisciplineType(discipline);
            disciplineCount.put(type, disciplineCount.getOrDefault(type, 0) + 1);
        }

        return disciplineCount;
    }

    private static List<String> extractListOfDisciplineNames(List<Discipline> disciplineList) {
        List<String> disciplines = new ArrayList<>();

        for (Discipline discipline : disciplineList) {
            disciplines.add(discipline.getClassroom());
        }

        return disciplines;
    }

    private static String extractDisciplineType(String discipline) {
        String lecture = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лекционные"),
        practice = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Практические"),
        labs = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лабораторные"),
        exam = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Экзамен"),
        test = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Зачет");
        List<String> activityTypes = List.of(lecture, practice, labs, exam, test);

        Pattern pattern = createPattern(activityTypes);
        Matcher matcher = pattern.matcher(discipline);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("Unknown lesson type found !!!");
            return "Unknown lesson type";
        }
    }

    private static Pattern createPattern(List<String> activityTypes) {
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("(");
        for (int i = 0; i < activityTypes.size(); i++) {
            patternBuilder.append(activityTypes.get(i));
            if (i < activityTypes.size() - 1) {
                patternBuilder.append("|");
            }
        }
        patternBuilder.append(")");
        return Pattern.compile(patternBuilder.toString());
    }
}
