package netflix;

import util.TestUtil;

import java.util.HashMap;
import java.util.Map;

public class EventLogger {

    /**
     * Given a time window (e.g., 60 seconds), implement printEvent(name, timestamp) that prints each unique event name
     * at most once per time window.
     * If the same event was printed less than window seconds ago, suppress it.
     */

    private Map<String, Integer> eventLastSeen = new HashMap<>();
    private int windowMs;

    EventLogger(int windowMs){
        this.windowMs = windowMs;
    }

    public boolean printEvent(String name, int timestamp){
        if(eventLastSeen.containsKey(name) && Math.abs(timestamp - eventLastSeen.get(name)) < windowMs){
            System.out.println("Suppressing event :" + name + "at timestamp " + timestamp);
            return false;
        } else {
            System.out.println(name + " at " + timestamp);
            eventLastSeen.put(name,timestamp);
            return true;
        }
    }

    static void main() {
        EventLogger logger = new EventLogger(60 *1000);
        TestUtil.check(logger.printEvent("click", 0));
        TestUtil.check(!logger.printEvent("click", 30 * 1000));    // within window
        TestUtil.check(logger.printEvent("click", 60 * 1000));     // exactly at window boundary
        TestUtil.check(logger.printEvent("hover", 30 * 1000));      // different event
        TestUtil.check(!logger.printEvent("click", 100 * 1000));
    }
}
