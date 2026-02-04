package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private Map<Coach, Integer> coachTimes = new HashMap<>();

    public Timetable() {
        for (DayOfWeek day : DayOfWeek.values()) {
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
        coachTimes.compute(trainingSession.getCoach(), (coach, count) -> count == null ? 1 : count + 1);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null || dayTimetable.isEmpty()) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();
        for (ArrayList<TrainingSession> sessions : dayTimetable.values()) {
            result.addAll(sessions);
        }
        return result;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        return dayTimetable.getOrDefault(timeOfDay, new ArrayList<>());
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
