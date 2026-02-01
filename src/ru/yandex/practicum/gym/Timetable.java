package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<String>>> timetable = new HashMap<>();
    private HashMap<Coach, Integer> coachTimes = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap <TimeOfDay, ArrayList<String>> dayTimetable = timetable.get(trainingSession.getDayOfWeek());
        if(dayTimetable == null) {
            dayTimetable = new TreeMap<>();
        }

        ArrayList<String> title = dayTimetable.get(trainingSession.getTimeOfDay());
        if (title == null) {
            title = new ArrayList<>();
        }

        title.add(trainingSession.getGroup().getTitle());
        dayTimetable.put(trainingSession.getTimeOfDay(), title);
        timetable.put(trainingSession.getDayOfWeek(), dayTimetable);

        if (coachTimes.containsKey(trainingSession.getCoach())) {
            int count = coachTimes.get(trainingSession.getCoach()) + 1;
            coachTimes.put(trainingSession.getCoach(), count);
        } else {
            coachTimes.put(trainingSession.getCoach(), 1);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<String>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<String>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null || dayTimetable.isEmpty()) {
            return null;
        }
        return dayTimetable;
    }

    public ArrayList<String> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<String>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return null;
        }
        return dayTimetable.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches(){
        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachTimes.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result);
        return result;
    }
}
