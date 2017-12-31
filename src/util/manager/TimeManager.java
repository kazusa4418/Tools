package util.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a date-time without the time-zone in the ISO-8601 calendar system
 * expressed by the java.time.LocalDateTime class.
 * <p>
 * TimeManager can express the current time, the time when
 * specific processing was done, and the period of the two recorded time
 * based on the object received in the LocalDateTime type.
 * <p>
 * This class does not store or represent a time-zone.
 * Instead, it is a description of the date, as used for birthdays,
 * combined with the local time as seen on a wall clock.
 * It cannot represent an instant on the time-line without additional information
 * such as an offset or time-zone.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world.
 * It is equivalent to the proleptic Gregorian calendar system,
 * in which today's rules for leap years are applied for all time.
 * For most applications written today, the ISO-8601 rules are entirely suitable.
 * However, any application that makes user of historical dates,
 * and requires them to be accurate will find the ISO-8601 approach unsuitable.
 *
 * @author kazusa4418
 * @see LocalDateTime
 * @see Duration
 */
public class TimeManager {

    /** Variable that records the time at a certain point int time */
    private List<LocalDateTime> records = new ArrayList<>();
    /** Start point for calculating the duration */
    private LocalDateTime start;
    /** End point for calculating the duration */
    private LocalDateTime end;

    /**
     * Obtains the current time and holds it as one record.
     * The time is obtained from the system clock of the default time-zone.
     * For details on how to retrieve saved records, see {@link #getRecord()}, {@link #getRecord(int)}
     * <p>
     * The record created by this method will be retained until
     * you intentionally delete it.
     * <p>
     * Records are stored int the order in which they are recorded,
     * and it is possible to retrieve them by specifying the order.
     * <p>
     * Using this method will prevent the ability to use an alternate clock
     * for testing because the clock is hard-corded.
     */
    public void record() {
        records.add(LocalDateTime.now());
    }

    /**
     * Take out one record.
     * Refer to {@link #record()} for storing records.
     * <p>
     * This method fetches the first record recorded by {@link #record()}.
     * <p>
     * Even retrieving a record does not delete that record.
     * So, unless you intentionally delete the retrieved record,
     * this method will refer to the same record.
     * <p>
     * If you do not want to use the retrieved record anymore,
     * delete the record using the remove method.
     *
     * @throws NotRecordException If there is no record.
     * @return The first recorded record among the stored records.
     */
    public LocalDateTime getRecord() throws NotRecordException {
        recordsLengthCheck(0);
        return records.get(0);
    }

    /**
     * Take out one record.
     * Refer to {@link #record()} for storing records.
     * <p>
     * The argument specifies the record to be acquired in saved record.
     * Even retrieving a record does not delete that record.
     * So, unless you intentionally delete the retrieved record,
     * this method will refer to the same record.
     * <p>
     * If you do not want to use the retrieved record anymore,
     * delete the record using the remove method.
     * <p>
     * An exception is thrown if the argument is greater
     * than the number of records stored.
     *
     * @param index index of the element to return
     * @return The first recorded record among the stored records.
     * @throws NotRecordException If there is no record.
     */
    public LocalDateTime getRecord(int index) throws NotRecordException {
        recordsLengthCheck(index);
        return records.get(index);
    }

    /**
     * Obtain the number of saved records.
     *
     * @return the number of saved records.
     */
    public int size() {
        return records.size();
    }

    /**
     * Set the start point for calculating the duration.
     * This is the same as the start when measuring time
     * such as stopwatch.
     * <p>
     * If you call this method with the start point already recorded,
     * the start point that was originally recorded will be overwritten.
     */
    public void makeStartPoint() {
        this.start = LocalDateTime.now();
    }

    /**
     * Set the end point for calculating the duration.
     * This is the same as the start when measuring time
     * such as stopwatch.
     * <p>
     * If you call this method with the end point already recorded,
     * the end point that was originally recorded will be overwritten.
     */
    public void makeEndPoint() {
        this.end = LocalDateTime.now();
    }

    /**
     * Calculate the duration from the start point and the end point.
     * <p>
     * If the end point is newer than the start point,
     * the calculation result is expressed in negative time.
     * <p>
     * An exception is thrown if the start point is null
     * or the end point is null.
     *
     * @return Duration between start point and end point.
     * @throws NullRecordException If start point or end point is null.
     */
    public Duration getDuration() throws NullRecordException {
        startPointNullCheck();
        endPointNullCheck();
        return Duration.between(this.start, this.end);
    }

    /*
     * this method checks whether the specified index is not more than
     * the number of stored record.
     */
    private void recordsLengthCheck(int index) throws NotRecordException {
        if (records.size() <= index) {
            throw new NotRecordException("The specified record could not be found");
        }
    }

    /*
     * This method checks whether null is assigned to the start point.
     */
    private void startPointNullCheck() throws NullRecordException {
        if (this.start == null) {
            throw new NullRecordException("StartPoint does not exist");
        }
    }

    /*
     * This method checks whether null is assigned to the end point.
     */
    private void endPointNullCheck() throws NullRecordException {
        if (this.end == null) {
            throw new NullRecordException("EndPoint does not exist");
        }
    }
}
