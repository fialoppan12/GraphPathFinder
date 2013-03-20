package Graphs;
import java.util.*;
     
    public class GraphMethods<N> {
                   
                    public static <N> boolean pathExists(Graph<N> g, N from, N to) {
                            Set<N> found = new HashSet<N>();
                            depthFirst(g, from, found);
                            return found.contains(to);
                    }
                   
                    public static <N> ArrayList<Edge<N>> recursivePath(HashMap<N,Edge<N>> path, N from, N to, ArrayList<Edge<N>> zePath) {
                        if (from.equals(to))
                                return zePath;
                        Edge<N> edge = path.get(from);
                        zePath.add(edge);
                        return recursivePath(path, edge.getDestination(), to, zePath);
                    }
                   
                    private static <N> ArrayList<Edge<N>> recPath(Graph<N> g, HashMap<N, N> path, N from, N stopNode, ArrayList<Edge<N>> zePath) {
                        if (from.equals(stopNode))
                                return zePath;
                        int min = Integer.MAX_VALUE;
                        Edge<N> rightEdge = null;
                        for (Edge<N> enEdge : g.getEdgesBetween(from, path.get(from))) {
                                if (enEdge.getWeight()<min) {
                                        min=enEdge.getWeight();
                                        rightEdge=enEdge;
                                }
                        }
                        zePath.add(rightEdge);
                        return recPath(g, path, path.get(from), stopNode, zePath);
                       
                    }
                   
                    public static <N> ArrayList<Edge<N>> shortestPath(Graph<N> g, N from, N to) {
                            if (!pathExists(g, from, to))
                                    return null;
                           
                            HashMap<N,Integer> tabell = new HashMap<N,Integer>();
                            HashMap<N, Boolean> trueFalse = new HashMap<N, Boolean>();
                            ArrayList<N> unvisited = new ArrayList<N>();
                            HashMap<N, N> fromVertex = new HashMap<N, N>();
     
                            // FYLL LISTOR MED STÄDER OCH VÄRDEN
                            for (N aktuellStad : g.getNodes()) {
                                    tabell.put(aktuellStad, Integer.MAX_VALUE);
                                    trueFalse.put(aktuellStad, false);
                                    unvisited.add(aktuellStad);
                                    fromVertex.put(aktuellStad, null);
                            }
                           
                            tabell.put(from, 0);
                            trueFalse.put(from, true);
                           
                            N current = from; // NOD SOM ÄR NU
                            Integer low = new Integer(Integer.MAX_VALUE); // FÖR ATT KOLLA VILKEN NOD HAR MINST VÄRDE
                           
                            //SÅ LÄNGE SISTA NODEN INTE ÄR SATT... ELLER EEH FAN. PAUS..
                            while (!trueFalse.get(to)) {
                            unvisited.remove(current);
                           
                           
                            for (Edge<N> c : g.getEdgesFrom(current)) {
                                    if (tabell.get(c.getDestination()) > (tabell.get(current)+c.getWeight())) {
                                                tabell.put(c.getDestination(), (tabell.get(current)+c.getWeight()));
                                                fromVertex.put(c.getDestination(), current);
                                        }
                                    }
                           
                            for (N s : unvisited) {
                                 if (!trueFalse.get(s)) {
                                       if (tabell.get(s)<low) {
                                               low = tabell.get(s);
                                               current=s;
                                       }
                                 }
                            }
                            low = Integer.MAX_VALUE;
                            trueFalse.put(current, true);                
                            }
                            ArrayList<Edge<N>> zePath = new ArrayList<Edge<N>>();
                            zePath = recPath(g, fromVertex, to, from, zePath);                
                            return zePath;
                    }
                   
 
                   
            public static <N> void depthFirst(Graph<N> g, N s, Set<N> found) {
                    found.add(s);
                    List<Edge<N>> e = g.getEdgesFrom(s);
                    for (Edge<N> es : e) {
                            N ss = es.getDestination();
                            if (!found.contains(ss))
                                    depthFirst(g, ss, found);
                            }
                           
                    }
         
           
     
    }