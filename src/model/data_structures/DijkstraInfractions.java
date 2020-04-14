package model.data_structures;

import java.util.Iterator;

public class DijkstraInfractions<K extends Comparable<K>,V extends Comparable<V>> {

	private Edge<Double>[] edgeTo;
	
	private long[] distTo;
	
	private IndexMinPQ<Long> pq;
	
	private HashTableSC<Pair<Integer,Integer>,Integer> visited;
	
	private Graph<K,VertexContent,Double> grafo;
	
	public DijkstraInfractions(Graph<K,VertexContent,Double> G, K s)
	{
		visited = new HashTableSC<Pair<Integer,Integer>,Integer>(500);
		edgeTo = new Edge[G.V()];
		distTo = new long[G.V()];
		grafo = G;
		pq = new IndexMinPQ<Long>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Long.MAX_VALUE;
		distTo[G.translate(s)] = 0;
		pq.insert(G.translate(s), new Long(0));
		while (!pq.isEmpty())
			relax(G, pq.delMin());
	}
	private void relax(Graph<K,VertexContent,Double> G, int v)
	{
		Iterator<Edge<Double>> ite = G.edgesTo(G.translateInverse(v));
		while(ite.hasNext())
		{
			Edge<Double> e = ite.next();
			int w = e.other(v);
			int first = v;
			int second = w;
			if(first < second) {
				int temp = w;
				second = first;
				first = temp;
			}
			if(visited.contains(new Pair<Integer,Integer>(first,second)))
				continue;
			if (distTo[w] > distTo[v] + G.getInfoVertex(G.translateInverse(w)).infractions.getSize())
			{
				distTo[w] = distTo[v] + G.getInfoVertex(G.translateInverse(w)).infractions.getSize();
				if(e.either() != v)
					e.swapEdges();
				edgeTo[w] = e;
				if (pq.contains(w)) pq.changeKey(w, distTo[w]);
				else pq.insert(w, distTo[w]);
				visited.put(new Pair<Integer,Integer>(first,second), 1);
			}
		}
	}
	public double distTo(K v) {
        return distTo[grafo.translate(v)];
    }
	
	public boolean hasPathTo(K v) {
        return distTo[grafo.translate(v)] < Double.POSITIVE_INFINITY;
    }
	
	public Iterable<Edge<Double>> pathTo(K v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge<Double>> path = new Stack<Edge<Double>>();
        for (Edge<Double> e = edgeTo[grafo.translate(v)]; e != null; e = edgeTo[e.either()]) {
            path.push(e);
        }
        return path;
    }
}
