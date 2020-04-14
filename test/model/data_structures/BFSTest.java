package model.data_structures;
import model.data_structures.BFSPaths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * Pruebas para comprobar la valides del DFS
 *
 */
class BFSTest {
	Graph<Integer,Integer,Integer> grafoNumerico;
	
	BFSPaths<Integer,Integer,Integer> caminos;
	
	void setUp0() {
		grafoNumerico = new Graph<Integer,Integer,Integer>();
	}
	
	
	/**
	 * Test que prueba que para los nodos conectados haya un camino
	 */
	@Test
	public void hasPathTest() {
		setUp0();
		for(int i = 0; i < 16; i++) {
			grafoNumerico.addVertex(i, null);
		}
		grafoNumerico.addEdge(0, 5, null);
		grafoNumerico.addEdge(0, 2, null);
		grafoNumerico.addEdge(0, 6, null);
		grafoNumerico.addEdge(2, 7, null);
		grafoNumerico.addEdge(2, 1, null);
		grafoNumerico.addEdge(7, 15, null);
		grafoNumerico.addEdge(15, 14, null);
		grafoNumerico.addEdge(14, 13, null);
		grafoNumerico.addEdge(5, 13, null);
		grafoNumerico.addEdge(5, 4, null);
		grafoNumerico.addEdge(5, 12, null);
		grafoNumerico.addEdge(4, 11, null);
		grafoNumerico.addEdge(11, 3, null);
		grafoNumerico.addEdge(11, 10, null);
		grafoNumerico.addEdge(11, 12, null);
		grafoNumerico.addEdge(11, 1, null);
		grafoNumerico.addEdge(1, 9, null);
		grafoNumerico.addEdge(9, 8, null);
		grafoNumerico.addVertex(17, null);
		System.out.println("paso por aqui");
		
		caminos = new BFSPaths(grafoNumerico,0);
		
		for(int i = 1; i < 16; i++) {
			assertTrue("deberian estar conectados",caminos.hasPathTo(i));
		}
		
		assertFalse("no deberian estar conectados", caminos.hasPathTo(17));
	}

	
	/**
	 * Test que prueba que los caminos más cortos sean los que se estan encontrando
	 */
	@Test
	public void PathTo() {
		setUp0();
		for(int i = 0; i < 16; i++) {
			grafoNumerico.addVertex(i, null);
		}
		grafoNumerico.addEdge(0, 5, null);
		grafoNumerico.addEdge(0, 2, null);
		grafoNumerico.addEdge(0, 6, null);
		grafoNumerico.addEdge(2, 7, null);
		grafoNumerico.addEdge(2, 1, null);
		grafoNumerico.addEdge(7, 15, null);
		grafoNumerico.addEdge(15, 14, null);
		grafoNumerico.addEdge(14, 13, null);
		grafoNumerico.addEdge(5, 13, null);
		grafoNumerico.addEdge(5, 4, null);
		grafoNumerico.addEdge(5, 12, null);
		grafoNumerico.addEdge(4, 11, null);
		grafoNumerico.addEdge(11, 3, null);
		grafoNumerico.addEdge(11, 10, null);
		grafoNumerico.addEdge(11, 12, null);
		grafoNumerico.addEdge(11, 1, null);
		grafoNumerico.addEdge(1, 9, null);
		grafoNumerico.addEdge(9, 8, null);
		grafoNumerico.addVertex(17, null);
		
		caminos = new BFSPaths(grafoNumerico,0);
		Iterator<Integer> route = caminos.pathTo(6).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		int desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		route = caminos.pathTo(5).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		route = caminos.pathTo(2).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		
		route = caminos.pathTo(4).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",5,desde);
		
		
		route = caminos.pathTo(1).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",2,desde);
		
		
		route = caminos.pathTo(13).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",5,desde);
		
		
		route = caminos.pathTo(12).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",5,desde);
		
		
		route = caminos.pathTo(7).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",2,desde);
		
		
		route = caminos.pathTo(8).iterator(); 
		assertTrue("hay una ruta",route.hasNext());
		desde = route.next();
		assertEquals("la ruta no es la correcta",0,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",2,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",1,desde);
		desde = route.next();
		assertEquals("la ruta no es la correcta",9,desde);
		
		Iterable<Integer> route2 = caminos.pathTo(17);
		assertNull("no debería haber una ruta",route2);
	}
}
