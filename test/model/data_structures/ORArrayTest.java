package model.data_structures;
import model.data_structures.ORArray;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class ORArrayTest {
	
	/**
	 * arreglo de numeros en la estructura orderedArray
	 */
	public ORArray<Integer> numeros;
	
	/**
	 * escenario en el cual se inicializa el orderedArray
	 */
	public void setUpEscenario1() {
		numeros = new ORArray<Integer>(10);
	}
	
	/**
	 * M�todo que prueba que el m�todo isEmpty funcione correctamente
	 */
	@Test
	public void isEmptyTest() {
		setUpEscenario1();
		assertTrue("la lista deber�a estar vacia", numeros.isEmpty());
	}
	
	/**
	 * M�todo que prueba que el m�todo getSize() funcione correctamente
	 */
	@Test
	public void getSizeTest() {
		setUpEscenario1();
		int tama�o = numeros.getSize();
		assertEquals("el tama�o no es el correcto",tama�o, 0);
	}
	
	/**
	 * m�todo que prueba que el m�todo Test funcione correctamente
	 */
	@Test
	public void addTest() {
		setUpEscenario1();
		numeros.add(1);
		int tama�o = numeros.getSize();
		assertEquals("el elemento no se a�adio o el tama�o no es el correcto", 1, tama�o);
		int comparador = numeros.getElement(0);
		assertEquals("el numero no se a�adio correctamente", 1,comparador);
		numeros.add(2);
		tama�o = numeros.getSize();
		assertEquals("el elemento no se a�adio o el tama�o no es el correcto", 2, tama�o);
		
	}
	
	/**
	 * M�todo que rectifica que isEmpty() funcione correctamente en otras circunstancias
	 */
	@Test
	public void isEmptyTest2() {
		 setUpEscenario1();
		 numeros.add(1);
		 assertFalse("la lista no deber�a estar vacia", numeros.isEmpty());
	}
	
	/**
	 * M�todo que rectifica que delete funcione correctamente.
	 */
	@Test
	public void deteleTest() {
		setUpEscenario1();
		numeros.add(1);
		int borrado = numeros.delete();
		int tama�o = numeros.getSize();
		assertEquals("el tama�o no es el correcto", tama�o,0);
		assertEquals("el elemento borrado no es el correcto", 1, borrado);
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		for(int i = 0; i < 3; i++) {
			borrado = numeros.delete();
			assertEquals("el valor del elemento que se borro no es el correcto",(3-i),borrado);
		}
	}
	
	/**
	 * M�todo que rectifica que isEmpty funcione bien bajo otras circunstancias
	 */
	@Test
	public void isEmptyTest3() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.delete();
		numeros.delete();
		assertTrue("la lista deber�a esta vacia", numeros.isEmpty());
	}
	
	/**
	 * M�todo que rectifica que getSize funcione bien bajo otras circunstancias 
	 */
	@Test
	public void getSizeTest2() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		int tama�o = numeros.getSize();
		assertEquals("el tama�o no es el correcto",4,tama�o);
		numeros.delete();
		numeros.delete();
		tama�o = numeros.getSize();
		assertEquals("el tama�o no es el correcto",2,tama�o);
		numeros.delete();
		numeros.delete();
		tama�o = numeros.getSize();
		assertEquals("el tama�o no es el correcto",0, tama�o);
	}
	
	/**
	 * m�todo que verifica que deleteAtK funcione de manera adecuada
	 */
	@Test
	public void deleteAtKTest() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		int borrado = numeros.deleteAtK(2);
		assertEquals("el numero borrado no es el correcto",borrado,3);
		int tama�o = numeros.getSize();
		assertEquals("el tama�o de la lista no es el correcto",tama�o,3);
		int elemento = numeros.getElement(2);
		assertEquals("la lista no quedo corrida correctamente",elemento,4);
		borrado = numeros.deleteAtK(0);
		assertEquals("el numero borrado no es el correcto",1,borrado);
		tama�o = numeros.getSize();
		assertEquals("el tama�o de la lista no es el correcto",tama�o,2);
	}
	
	/**
	 * M�todo que verifica que sort se este arreglando de manera correcta.
	 */
	@Test
	public void SortTest() {
		setUpEscenario1();
		for(int i = 0; i < 100; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
			numeros.add(randomNum);
		}
		Comparator compara = new IntegerComparator();
		numeros.sort(compara);
		for(int i = 1; i < 100; i++) {
			assertTrue("la lista no esta bien ordenada",compara.compare(numeros.getElement(i-1), numeros.getElement(i))<=0);
		}
	}
	
	/**
	 * M�todo que verifica que iterator este funcionando de manera correcta
	 */
	@Test
	public void iteratorTest() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		int i = 1;
		for(Integer ans : numeros) {
			int comparacion = ans;
			assertEquals("el iterador no devolvio el elemento correcto", i,comparacion);
			i++;
		}
	}
	
	/**
	 * 
	 * Clase auxiliar utilizada para hacer un comparador
	 *
	 */
	class IntegerComparator implements Comparator<Integer> {

	    public int compare(Integer x, Integer y) {
	        return x < y ? -1 : x > y ? +1 : 0;
	    }
	}
}
