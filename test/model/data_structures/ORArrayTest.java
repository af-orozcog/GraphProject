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
	 * Método que prueba que el método isEmpty funcione correctamente
	 */
	@Test
	public void isEmptyTest() {
		setUpEscenario1();
		assertTrue("la lista debería estar vacia", numeros.isEmpty());
	}
	
	/**
	 * Método que prueba que el método getSize() funcione correctamente
	 */
	@Test
	public void getSizeTest() {
		setUpEscenario1();
		int tamaño = numeros.getSize();
		assertEquals("el tamaño no es el correcto",tamaño, 0);
	}
	
	/**
	 * método que prueba que el método Test funcione correctamente
	 */
	@Test
	public void addTest() {
		setUpEscenario1();
		numeros.add(1);
		int tamaño = numeros.getSize();
		assertEquals("el elemento no se añadio o el tamaño no es el correcto", 1, tamaño);
		int comparador = numeros.getElement(0);
		assertEquals("el numero no se añadio correctamente", 1,comparador);
		numeros.add(2);
		tamaño = numeros.getSize();
		assertEquals("el elemento no se añadio o el tamaño no es el correcto", 2, tamaño);
		
	}
	
	/**
	 * Método que rectifica que isEmpty() funcione correctamente en otras circunstancias
	 */
	@Test
	public void isEmptyTest2() {
		 setUpEscenario1();
		 numeros.add(1);
		 assertFalse("la lista no debería estar vacia", numeros.isEmpty());
	}
	
	/**
	 * Método que rectifica que delete funcione correctamente.
	 */
	@Test
	public void deteleTest() {
		setUpEscenario1();
		numeros.add(1);
		int borrado = numeros.delete();
		int tamaño = numeros.getSize();
		assertEquals("el tamaño no es el correcto", tamaño,0);
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
	 * Método que rectifica que isEmpty funcione bien bajo otras circunstancias
	 */
	@Test
	public void isEmptyTest3() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.delete();
		numeros.delete();
		assertTrue("la lista debería esta vacia", numeros.isEmpty());
	}
	
	/**
	 * Método que rectifica que getSize funcione bien bajo otras circunstancias 
	 */
	@Test
	public void getSizeTest2() {
		setUpEscenario1();
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		int tamaño = numeros.getSize();
		assertEquals("el tamaño no es el correcto",4,tamaño);
		numeros.delete();
		numeros.delete();
		tamaño = numeros.getSize();
		assertEquals("el tamaño no es el correcto",2,tamaño);
		numeros.delete();
		numeros.delete();
		tamaño = numeros.getSize();
		assertEquals("el tamaño no es el correcto",0, tamaño);
	}
	
	/**
	 * método que verifica que deleteAtK funcione de manera adecuada
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
		int tamaño = numeros.getSize();
		assertEquals("el tamaño de la lista no es el correcto",tamaño,3);
		int elemento = numeros.getElement(2);
		assertEquals("la lista no quedo corrida correctamente",elemento,4);
		borrado = numeros.deleteAtK(0);
		assertEquals("el numero borrado no es el correcto",1,borrado);
		tamaño = numeros.getSize();
		assertEquals("el tamaño de la lista no es el correcto",tamaño,2);
	}
	
	/**
	 * Método que verifica que sort se este arreglando de manera correcta.
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
	 * Método que verifica que iterator este funcionando de manera correcta
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
