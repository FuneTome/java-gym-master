package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private Map<Coach, Integer> coachTimes = new HashMap<>();

    public Timetable() {
        for(DayOfWeek day : DayOfWeek.values()) {
        timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(trainingSession.getDayOfWeek());

        dayTimetable.compute(trainingSession.getTimeOfDay(), (time, sessions) -> {
            if (sessions == null) {
                sessions = new ArrayList<>();
            }
            sessions.add(trainingSession);
            return sessions;
        });
        timetable.put(trainingSession.getDayOfWeek(), dayTimetable);

        coachTimes.compute(trainingSession.getCoach(), (coach, count) -> count == null ? 1 : count + 1);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null || dayTimetable.isEmpty()) {
            return new TreeMap<>();
        }
        return dayTimetable;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return new ArrayList<>();
        }
        return dayTimetable.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachTimes.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result);
        return result;
    }
}
