package util;

import java.util.*;

public class TestUtil {

    public static void check(boolean condition, String message){
        if(condition){
            System.out.println("Condition Passed");
        } else {
            System.out.println("Condition Failed : " + message);
        }
    }

    public static void checkCollectionEqual(List a, List b){
        checkCollectionEqual(a,b,null);
    }
    public static void checkCollectionEqual(List a, List b, String message){
        Map<Object, Integer> freqA = new HashMap<>();
        Map<Object, Integer> freqB = new HashMap<>();
        for(Object obj : a){
            freqA.merge(obj, 1 , Integer::sum);
        }
        for(Object obj : b){
            freqB.merge(obj, 1, Integer::sum);
        }
        if(isEqual(freqA,freqB)){
            System.out.println("Lists Equal");
        } else {
            System.out.println("List Equality failed : " + message);
        }
    }

    private static boolean isEqual(Map<Object, Integer> freqA, Map<Object, Integer> freqB) {
        if(freqA.size() != freqB.size()){
            return false;
        }

        for(Map.Entry<Object, Integer> entry : freqA.entrySet()){
            if(!Objects.equals(freqB.getOrDefault(entry.getKey(), 0), entry.getValue())){
                return false;
            }
        }
        return true;
    }

    public static void check(boolean condition){
        check(condition,null);
    }
}
