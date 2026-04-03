package netflix;

import util.TestUtil;

public class EventLoggerOutOfOrder {

    public boolean printEvent(String name, int timestamp) {

    }


    static void main() {
        EventLoggerOutOfOrder logger = new EventLoggerOutOfOrder(60);

        TestUtil.check(logger.printEvent("click", 0) == true);
        TestUtil.check(logger.printEvent("click", 30) == false);    // within window
        TestUtil.check(logger.printEvent("click", 60) == true);     // exactly at window boundary
        TestUtil.check(logger.printEvent("hover", 30) == true);      // different event
        TestUtil.check(logger.printEvent("click", 100) == false);    // 100 - 60 = 40 < 60
    }
}
