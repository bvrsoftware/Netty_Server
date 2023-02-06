package com.netty.NettyServer.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Used to build dates during parsing.
 * Traccar version of DateBuilder.
 */
public class DateBuilder {

    private Calendar calendar;

    public DateBuilder() {
        this(TimeZone.getTimeZone("UTC"));
    }

    public DateBuilder(Date time) {
        this(time, TimeZone.getTimeZone("UTC"));
    }

    public DateBuilder(TimeZone timeZone) {
        this(new Date(0), timeZone);
    }

    public DateBuilder(Date time, TimeZone timeZone) {
        calendar = Calendar.getInstance(timeZone);
        calendar.clear();
        calendar.setTimeInMillis(time.getTime());
    }

    /**
     * Set year in Calendar and Return DateBuilder.
     * @param year int
     * @return DateBuilder
     */
    public DateBuilder setYear(int year) {
        if (year < 100) {
            year += 2000;
        }
        calendar.set(Calendar.YEAR, year);
        return this;
    }

    /**
     * Set month in Calendar and Return DateBuilder.
     * @param month int
     * @return DateBuilder
     */
    public DateBuilder setMonth(int month) {
        calendar.set(Calendar.MONTH, month - 1);
        return this;
    }

    /**
     * Set day in Calendar and Return DateBuilder.
     * @param day int
     * @return DateBuilder
     */
    public DateBuilder setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return this;
    }

    /**
     * Set Date (year, month, day) in Calendar and return DateBuilder.
     * @param year int
     * @param month int
     * @param day int
     * @return DateBuilder
     */
    public DateBuilder setDate(int year, int month, int day) {
        return setYear(year).setMonth(month).setDay(day);
    }

    /**
     * Set Date in reverse order (day, month, year) in Calendar and return DateBuilder.
     * @param day int
     * @param month int
     * @param year int
     * @return DateBuilder
     */
    public DateBuilder setDateReverse(int day, int month, int year) {
        return setDate(year, month, day);
    }

    public DateBuilder setCurrentDate() {
        Calendar now = Calendar.getInstance(calendar.getTimeZone());
        return setYear(now.get(Calendar.YEAR)).setMonth(now.get(Calendar.MONTH)).setDay(now.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Set hour in Calendar and Return DateBuilder.
     * @param hour int
     * @return DateBuilder
     */
    public DateBuilder setHour(int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return this;
    }

    /**
     * Set minute in Calendar and Return DateBuilder.
     * @param minute int
     * @return DateBuilder
     */
    public DateBuilder setMinute(int minute) {
        calendar.set(Calendar.MINUTE, minute);
        return this;
    }

    public DateBuilder addMinute(int minute) {
        calendar.add(Calendar.MINUTE, minute);
        return this;
    }

    /**
     * Set second in Calendar and Return DateBuilder.
     * @param second int
     * @return DateBuilder
     */
    public DateBuilder setSecond(int second) {
        calendar.set(Calendar.SECOND, second);
        return this;
    }

    public DateBuilder addSeconds(long seconds) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + seconds * 1000);
        return this;
    }

    /**
     * Set millisecond in Calendar and Return DateBuilder.
     * @param millis int
     * @return DateBuilder
     */
    public DateBuilder setMillis(int millis) {
        calendar.set(Calendar.MILLISECOND, millis);
        return this;
    }

    public DateBuilder addMillis(long millis) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + millis);
        return this;
    }

    /**
     * Set time (hour, minute, second) in Calendar and Return DateBuilder.
     * @param hour int
     * @param minute int
     * @param second int
     * @return DateBuilder
     */
    public DateBuilder setTime(int hour, int minute, int second) {
        return setHour(hour).setMinute(minute).setSecond(second);
    }

    /**
     * Set time in reverse order (second, minute, hour) in Calendar and Return DateBuilder.
     * @param second int
     * @param minute int
     * @param hour int
     * @return DateBuilder
     */
    public DateBuilder setTimeReverse(int second, int minute, int hour) {
        return setHour(hour).setMinute(minute).setSecond(second);
    }

    /**
     * Set time (hour, minute, second, millis) in Calendar and Return DateBuilder.
     * @param hour int
     * @param minute int
     * @param second int
     * @param millis int
     * @return DateBuilder
     */
    public DateBuilder setTime(int hour, int minute, int second, int millis) {
        return setHour(hour).setMinute(minute).setSecond(second).setMillis(millis);
    }

    /**
     * Return Date which is set in the Calendar.
     * @return Date
     */
    public Date getDate() {
        return calendar.getTime();
    }

}
