package netflix;

import util.TestUtil;

import java.sql.Array;
import java.util.*;

public class ShortestPathInWarehouse {

    /**
     * Find the shortest path between two products in a warehouse. You must:
     *
     * Propose a data model (graph preferred over int[][]).
     * Implement an algorithm that returns the path (not just the distance) as an array.
     */

    Map<String, List<String>> graph = new HashMap<>();

    ShortestPathInWarehouse(String[][] productEdges){
        for (String [] edge : productEdges) {
            String p1 = edge[0];
            String p2 = edge[1];
            graph.computeIfAbsent(p1, v-> new ArrayList<>()).add(p2);
            graph.computeIfAbsent(p2, v-> new ArrayList<>()).add(p1);
        }
    }
    public List<String> shortestPath(String start, String end) {

        // run dijikstras

        if(!graph.containsKey(start) || !graph.containsKey(end)){
            return Collections.emptyList();
        }
        if(start.equals(end)){
            return List.of(start);
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        Map<String, String> nodeToPreviousNode = new HashMap<>();

        while(!queue.isEmpty()){
            String node = queue.poll();
            if(node.equals(end)){
                break;
            }
            if(graph.get(node) != null){
                for (String childNode : graph.get(node)){
                    if(!nodeToPreviousNode.containsKey(childNode)){
                        queue.add(childNode);
                        nodeToPreviousNode.put(childNode,node);
                    }
                }
            }
        }

        if(nodeToPreviousNode.get(end) != null){
            List<String> path = new ArrayList<>();
            String curr = end;
            while(!curr.equals(start)){
                path.add(curr);
                curr = nodeToPreviousNode.get(curr);
            }
            path.add(start);
            return path.reversed();
        } else {
            return  Collections.emptyList();
        }

    }


    static void main() {
        ShortestPathInWarehouse w = new ShortestPathInWarehouse(new String [][]{
                {"A", "B"},
                {"B", "C"},
                {"A", "D"},
                {"D", "C"}
        });


        List<String> path = w.shortestPath("A", "C");
        TestUtil.check(path.size() == 3); // either [A, B, C] or [A, D, C]
        TestUtil.check(path.get(0).equals("A") && path.get(2).equals("C"));
        TestUtil.check(w.shortestPath("A", "A").equals(List.of("A")));
        TestUtil.check(w.shortestPath("A", "Z").isEmpty()); // no path
    }



}
