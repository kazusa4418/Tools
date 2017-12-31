package util.manager;

import java.time.Duration;
import java.time.LocalDateTime;

public class TestClass {
    public static void main(String[] args) {
        TimeManager timer = new TimeManager();
        //Œ»İ‚Ì‚ğ‹L˜^‚·‚é
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

        TimeManager timer2 = new TimeManager();
        timer.makeEndPoint();
        System.out.println("DEBUG“Ç‚Ü‚ê‚Ä‚¢‚Ü‚·");
        Duration duration2 = timer2.getDuration();
        System.out.println(duration2);
    }
}
