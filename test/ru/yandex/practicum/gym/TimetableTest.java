package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondayList = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> mondayTest = new ArrayList<>();
        mondayTest.add(new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        Assertions.assertEquals(mondayTest.toString(), mondayList.toString());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);


        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondayList = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> mondayTest = new ArrayList<>();
        mondayTest.add(new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        Assertions.assertEquals(mondayTest.toString(), mondayList.toString());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdayList = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> thursdayTest = new ArrayList<>();
        thursdayTest.add(new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));
        thursdayTest.add(new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        Assertions.assertEquals(thursdayTest.toString(), thursdayList.toString());

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        ArrayList<String> arrOne = new ArrayList<>();
        arrOne.add("Акробатика для детей");

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(arrOne.toString(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).toString());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));
    }

    @Test
    void testGetTrainingSessionsForSingleCoach() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Гимнастика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Гимнастика для взрослых", Age.ADULT, 60);

        Coach coach = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession firstTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.WEDNESDAY,
                new TimeOfDay(10, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.WEDNESDAY,
                new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        List <CounterOfTrainings> list = timetable.getCountByCoaches();
        
        Assertions.assertEquals("Иванов Иван Иванович", list.get(0).getCoach().toString());
        Assertions.assertEquals(2, list.get(0).getCounter());
    }

    @Test
    void testGetTrainingSessionsForDoubleCoach() {
        Timetable timetable = new Timetable();

        Group groupChildfirst = new Group("Гимнастика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Гимнастика для взрослых", Age.ADULT, 60);
        Group groupChildSecond = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coachFirst = new Coach("Иванов", "Иван", "Иванович");
        Coach coachSecond = new Coach("Николаев", "Николай", "Николаевич");

        TrainingSession firstTrainingSession = new TrainingSession(groupChildfirst, coachFirst, DayOfWeek.WEDNESDAY,
                new TimeOfDay(10, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupAdult, coachFirst, DayOfWeek.MONDAY,
                new TimeOfDay(12, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupChildSecond, coachSecond, DayOfWeek.THURSDAY,
                new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        List <CounterOfTrainings> list = timetable.getCountByCoaches();

        Assertions.assertEquals("Иванов Иван Иванович", list.get(0).getCoach().toString());
        Assertions.assertEquals(2, list.get(0).getCounter());

        Assertions.assertEquals("Николаев Николай Николаевич", list.get(1).getCoach().toString());
        Assertions.assertEquals(1, list.get(1).getCounter());
    }

    @Test
    void testGetTrainingSessionsForTripleCoach() {
        Timetable timetable = new Timetable();

        Group groupOne = new Group("one", Age.CHILD, 60);
        Group groupTwo = new Group("two", Age.CHILD, 60);
        Group groupThree = new Group("three", Age.CHILD, 60);
        Group groupFour = new Group("four", Age.CHILD, 60);
        Group groupFive = new Group("five", Age.CHILD, 60);

        Coach coachFirst = new Coach("Иванов", "Иван", "Иванович");
        Coach coachSecond = new Coach("Николаев", "Николай", "Николаевич");
        Coach coachThird = new Coach("Петров", "Петр", "Петрович");

        TrainingSession oneTrainingSession = new TrainingSession(groupOne, coachFirst, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession twoTrainingSession = new TrainingSession(groupTwo, coachFirst, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession threeTrainingSession = new TrainingSession(groupThree, coachSecond, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession fourTrainingSession = new TrainingSession(groupFour, coachSecond, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession fiveTrainingSession = new TrainingSession(groupFive, coachThird, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);
        timetable.addNewTrainingSession(threeTrainingSession);
        timetable.addNewTrainingSession(fourTrainingSession);
        timetable.addNewTrainingSession(fiveTrainingSession);

        List <CounterOfTrainings> list = timetable.getCountByCoaches();
        Assertions.assertEquals("Николаев Николай Николаевич", list.get(0).getCoach().toString());
        Assertions.assertEquals(2, list.get(0).getCounter());

        Assertions.assertEquals("Иванов Иван Иванович", list.get(1).getCoach().toString());
        Assertions.assertEquals(2, list.get(1).getCounter());

        Assertions.assertEquals("Петров Петр Петрович", list.get(2).getCoach().toString());
        Assertions.assertEquals(1, list.get(2).getCounter());
    }

    @Test
    void testGetTrainingSessionsForOneCoachAndMultitraning() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Иван", "Иванович");

        Group groupOne = new Group("one", Age.CHILD, 60);
        Group groupTwo = new Group("two", Age.CHILD, 60);
        Group groupThree = new Group("three", Age.CHILD, 60);
        Group groupFour = new Group("four", Age.CHILD, 60);
        Group groupFive = new Group("five", Age.CHILD, 60);

        TrainingSession oneTrainingSession = new TrainingSession(groupOne, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession twoTrainingSession = new TrainingSession(groupTwo, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession threeTrainingSession = new TrainingSession(groupThree, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession fourTrainingSession = new TrainingSession(groupFour, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession fiveTrainingSession = new TrainingSession(groupFive, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);
        timetable.addNewTrainingSession(threeTrainingSession);
        timetable.addNewTrainingSession(fourTrainingSession);
        timetable.addNewTrainingSession(fiveTrainingSession);

        List <CounterOfTrainings> list = timetable.getCountByCoaches();

        Assertions.assertEquals("Иванов Иван Иванович", list.get(0).getCoach().toString());
        Assertions.assertEquals(5, list.get(0).getCounter());
    }
}
