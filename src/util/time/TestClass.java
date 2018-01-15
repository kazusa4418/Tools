package util.time;

import java.time.Duration;
import java.time.LocalDateTime;

public class TestClass {
    public static void main(String[] args) {
        TimeControl timer = new TimeControl();
        //現在の時刻を記録する
        timer.record();
        timer.record();

        LocalDateTime time = timer.getRecord(1);
        time = timer.getRecord();
        System.out.println(time);
        System.out.println(timer.size());

        timer.makeStartPoint();
        timer.makeEndPoint();
        Duration duration = timer.getDuration();
        System.out.println(duration);

        TimeControl timer2 = new TimeControl();
        timer.makeEndPoint();
        System.out.println("DEBUG読まれています");
        Duration duration2 = timer2.getDuration();
        System.out.println(duration2);
    }
}
