package util.time;

import java.time.Duration;
import java.time.LocalDateTime;

public class TestClass {
    public static void main(String[] args) {
        TimeControl timer = new TimeControl();
        //���݂̎������L�^����
        timer.record();
        timer.record();

        LocalDateTime time = timer.getRecord(1);
        System.out.println(time);

        timer.makeStartPoint();
        timer.makeEndPoint();
        Duration duration = timer.getDuration();
        System.out.println(duration);

        TimeControl timer2 = new TimeControl();
        timer.makeEndPoint();
        System.out.println("DEBUG�ǂ܂�Ă��܂�");
        Duration duration2 = timer2.getDuration();
        System.out.println(duration2);
    }
}
