package model.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DjisktraTest {

	Graph<String,Integer,Double> grafoString;
	
	Dijkstra<String,Integer> caminos;
	
	public void setUp0() {
		grafoString = new Graph<String,Integer,Double>();
	}
	
	@Test
	public void weightEdgeTest() {
		setUp0();
		grafoString.addVertex("A", null);
		grafoString.addVertex("B", null);
		grafoString.addVertex("C", null);
		grafoString.addVertex("D", null);
		grafoString.addVertex("E", null);
		grafoString.addEdge("A", "B", 3.0);
		grafoString.addEdge("A", "C", 1.0);
		grafoString.addEdge("B", "C", 7.0);
		grafoString.addEdge("B", "D", 5.0);
		grafoString.addEdge("C", "D", 2.0);
		grafoString.addEdge("D", "E", 7.0);
		grafoString.addEdge("B", "E", 1.0);
		
		
		caminos = new Dijkstra<String,Integer>(grafoString,"C");
		
		for(Edge<Double> edg: caminos.pathTo("E")) {
			System.out.println(grafoString.translateInverse(edg.either()) + " -- " + grafoString.translateInverse(edg.other(edg.either())));
		}
		assertEquals(5.0,caminos.distTo("E"),0);
		assertEquals(1.0,caminos.distTo("A"),0);
		assertEquals(2.0,caminos.distTo("D"),0);
		assertEquals(4.0,caminos.distTo("B"),0);
	}

}
