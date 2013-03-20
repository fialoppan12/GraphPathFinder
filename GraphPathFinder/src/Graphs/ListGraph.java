package Graphs;

import java.util.*;

public class ListGraph<N> implements Graph<N>{
private Map<N, List<Edge<N>>> nodes = new HashMap<N, List<Edge<N>>>();
	
	public void add(N n) {
		if (nodes.containsKey(n)) 
			throw new IllegalArgumentException("node exists");
		nodes.put(n, new ArrayList<Edge<N>>());
	}
	
	public void connect(N n, N n1, String namn, int vikt) {
		if (!nodes.containsKey(n) || !nodes.containsKey(n1))
			throw new NoSuchElementException("no such node");
		if (getEdge(n, n1, namn)!=null)
			throw new IllegalStateException("connection exists");
		
		Edge<N> e = new Edge<N>(n, namn, vikt);
		List<Edge<N>> el = nodes.get(n1);
		el.add(e);
		
		Edge<N> e2 = new Edge<N>(n1, namn, vikt);
		List<Edge<N>> el2 = nodes.get(n);
		el2.add(e2);
	}
	
	public Edge<N> getEdge(N n, N n2, String namn) {
		for (Edge<N> e : nodes.get(n)) {
			if (e.getDestination().equals(n2) && e.getName().equals(namn))
				return e;
		}
		return null;
	}
	
	public void setConnectionWeight(N n, N n2, String namn, int v) {
		if (!nodes.containsKey(n) || !nodes.containsKey(n2)) 
			throw new NoSuchElementException("no such node");
		getEdge(n, n2, namn).setWeight(v);
		getEdge(n2, n, namn).setWeight(v);
	}
	
	public Set<N> getNodes() {
		return new HashSet<N>(nodes.keySet());
	}
	
	public List<Edge<N>> getEdgesFrom(N n) {
		if (!nodes.containsKey(n))
			throw new NoSuchElementException("no such node");
		return new ArrayList<Edge<N>>(nodes.get(n));
	}
	
	public List<Edge<N>> getEdgesBetween(N n, N n2) {
		if (!nodes.containsKey(n) || !nodes.containsKey(n2)) 
			throw new NoSuchElementException("no such node");
		List<Edge<N>> edges = new ArrayList<Edge<N>>();
		for (Edge<N> e : getEdgesFrom(n)) {
			if (e.getDestination().equals(n2))
				edges.add(e);
		}
			return edges;
	}
	
	public String toString() {
		String s = "";
		for (Map.Entry<N, List<Edge<N>>> e : nodes.entrySet())
			s+= e.getKey()+" "+e.getValue()+"\n";
		return s;
	}

}
