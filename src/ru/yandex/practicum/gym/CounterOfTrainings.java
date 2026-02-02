package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int counter;

    public CounterOfTrainings(Coach coach, int counter) {
        this.coach = coach;
        this.counter = counter;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.counter - this.counter;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCounter() {
        return counter;
    }

}
