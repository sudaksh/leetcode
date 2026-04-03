package netflix;

import util.TestUtil;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FunctionTimeInWords {

    /**
     * Implement a recursive function that converts an integer number of seconds into a
     * human-readable string like "1 months, 2 weeks, 56 minutes, 40 seconds".
     */

    int[] secondsPerWindow = new int[]{
            30 * 24 * 60 * 60,
            7  * 24 * 60 * 60,
            60,
            1
    };
    String[] windowName = new String[]{
            "months",
            "weeks",
            "minutes",
            "seconds"
    };

    public String printNumber(int seconds){
        List<Integer>  parts = new ArrayList<>();
        helper(seconds, 0, parts);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.size(); i ++){
            if(parts.get(i) != 0 || i == parts.size()-1){
                if(!builder.isEmpty()){
                    builder.append(", ");
                }
                builder.append(parts.get(i)). append(" ").append(windowName[i]);
            }
        }
        String result = builder.toString();
        System.out.println(result);
        return result;
    }

    private void helper(int seconds, int windowId, List<Integer> parts) {
        if(windowId == windowName.length){
            return;
        }
        int quotient = seconds / secondsPerWindow[windowId];
        int remainder = seconds % secondsPerWindow[windowId];

        parts.add(quotient);
        helper(remainder, windowId+1, parts );
    }

    static void main() {
        FunctionTimeInWords timer = new FunctionTimeInWords();
        TestUtil.check(timer.printNumber(55).equals("55 seconds"));
        TestUtil.check(timer.printNumber(65).equals("1 minutes, 5 seconds"));
        TestUtil.check(timer.printNumber(0).equals("0 seconds"));
        TestUtil.check(timer.printNumber(60).equals("1 minutes, 0 seconds"));
        TestUtil.check(timer.printNumber(3805000).equals("1 months, 2 weeks, 56 minutes, 40 seconds"));
    }
}
