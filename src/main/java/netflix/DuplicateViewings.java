package netflix;


import util.TestUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
public class DuplicateViewings {


    // Part 1: Any duplicate in the entire list
    public static boolean hasDuplicate(int[] ids) {
        Set<Integer> seen = new HashSet<>();
        for (int id : ids) {
            if (seen.contains(id)) {
                return true;
            } else {
                seen.add(id);
            }
        }
        return false;
    }

    // Part 2: Duplicate within sliding window of size K
    public static boolean hasDuplicateWithinK(int[] ids, int k) {
        Set<Integer> window = new HashSet<>();

        for(int i=0; i < ids.length; i++){
            if(window.contains(ids[i])){
                return true;
            } else {
                if(window.size() == k-1){
                    window.remove(ids[i-k+1]);
                }
                window.add(ids[i]);
            }
        }

        return false;


        // 0 1 2 3
        //       i
    }



    // Part 3: Any two IDs with value-distance <= T
    public static boolean hasValueDiffWithinT2(int[] ids, int t) {
        TreeSet<Integer> bst = new TreeSet<>();

        for(int id : ids){

            Integer greatestSmallerThanId = bst.floor(id);
            Integer smallestGreaterThanId = bst.ceiling(id);

            if((greatestSmallerThanId != null && id - greatestSmallerThanId <= t) || (smallestGreaterThanId != null && smallestGreaterThanId - id <= t)){
                return true;
            }
            bst.add(id);
        }
        return false;
    }


    // Part 3: Any two IDs with value-distance <= T
    public static boolean hasValueDiffWithinT(int[] ids, int t) {
        if(ids == null){
            return false;
        }
        Arrays.sort(ids);
        for(int i = 1 ; i < ids.length ; i++){
            if(ids[i] - ids[i-1] <=t){
                return true;
            }
        }
        return false;
    }





        static void main() {
            TestUtil.check(hasDuplicate(new int[]{4, 5, 100, 200, 5}));
            TestUtil.check(!hasDuplicate(new int[]{1, 2, 3}));
            TestUtil.check(!hasDuplicate(new int[]{}));


            TestUtil.check(!hasDuplicateWithinK(new int[]{1, 2, 3, 1, 4}, 3));
            TestUtil.check(hasDuplicateWithinK(new int[]{1, 2, 1, 4}, 3)); // |0-2|=2 < 3

// Part 3
            TestUtil.check(hasValueDiffWithinT(new int[]{1, 5, 100}, 4) == true);  // |5-1|=4 <= 4
            TestUtil.check(hasValueDiffWithinT(new int[]{1, 5, 100}, 3) == false);  // min diff is 4 > 3
            TestUtil.check(hasValueDiffWithinT(new int[]{}, 5) == false);
        }

}

