package Graphs;

import java.util.*;

public interface Graph<N> {
	
	void add(N n);
	void connect(N n, N n2, String namn, int vikt);
	List<Edge<N>> getEdgesFrom(N n);
	List<Edge<N>> getEdgesBetween(N n, N n2);
	void setConnectionWeight(N n, N n2, String namn, int v);
	Set<N> getNodes();
	String toString();
	
//	boolean pathExists(Graph<N> g, N n, N n1);
//	List<N> shortestPath(Graph<N> g, N n, N n1);

}
