package netflix;

import java.util.*;


/**
 * Netflix displays titles in rows by category (Comedy, Romance, Drama, etc.).
 * A title may belong to multiple categories. On the home page, each title should appear only once
 * — in the first row where it is encountered. Subsequent occurrences in other rows are suppressed.
 */
public class DuplicateTitles {

    /**
     * Netflix displays titles in rows by category (Comedy, Romance, Drama, etc.).
     * A title may belong to multiple categories. On the home page, each title should appear only once —
     * in the first row where it is encountered. Subsequent occurrences in other rows are suppressed.
     * @param rows
     * @return
     */
    public static Map<String, List<String>> dedupeRows(Map<String, List<String>> rows) {

        LinkedHashMap<String, List<String>> categoryToTitles = new LinkedHashMap<>();

        Set<String> seen = new HashSet<>();
        for(Map.Entry<String,List<String>> rowCategoryAndTitles : rows.entrySet()){

            String category = rowCategoryAndTitles.getKey();
            List<String> titles = rowCategoryAndTitles.getValue();

            for(String title : titles){
                if(!seen.contains(title)){
                    categoryToTitles.computeIfAbsent(category, v-> new ArrayList<>()).add(title);
                    seen.add(title);
                }
            }
        }
        return categoryToTitles;

    }

}
