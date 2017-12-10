package util.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a date-time without the time-zone in the ISO-8601 calendar system
 * expressed by the java.time.LocalDateTime class.
 * <p>
 * TimeControl can express the current time, the time when
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
public class TimeControl {

    /** Variable that records the time at a certain point int time */
    private List<LocalDateTime> records = new ArrayList<>();
    /** Start point for calculating the duration */
    private LocalDateTime start;
    /** End point for calculating the duration */
    private LocalDateTime end;

    /**
     * Obtains the current time and holds it as one record.
     * The time is obtained from the system clock of the default time-zone.
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
     * <p>
     * This method fetches the first record recorded by the makeRecord method.
     * <p>
     * Even retrieving a record does not delete that record.
     * So, unless you intentionally delete the retrieved record,
     * this method will refer to the same record.
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
     * @param index
     * @return
     * @throws NotRecordException
     */
    public LocalDateTime getRecord(int index) throws NotRecordException {
        recordsLengthCheck(index);
        return records.get(index);
    }

    public void startRecord() {
        this.start = LocalDateTime.now();
    }

    public void endRecord() throws NullRecordException {
        startPointNullCheck();
        this.end = LocalDateTime.now();
    }

    public Duration getDuration() throws NullRecordException {
        startPointNullCheck();
        endPointNullCheck();
        return Duration.between(this.start, this.end);
    }

    private void recordsLengthCheck(int index) throws NotRecordException {
        if (records.size() <= index) {
            throw new NotRecordException("The specified record could not be found");
        }
    }

    private void startPointNullCheck() throws NullRecordException {
        if (this.start == null) {
            throw new NullRecordException("StartPoint does not exist");
        }
    }

    private void endPointNullCheck() throws NullRecordException {
        if (this.end == null) {
            throw new NullRecordException("EndPoint does not exist");
        }
    }

}
