package poureleves.triangle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class TriangleTest {
	@Rule
    public ErrorCollector collector = new ErrorCollector();

	@Test
	public void testTriangleValid() {
		try {
			new Triangle(1, 2, 3);
		} catch (Exception e) {
			collector.addError(e);
		}
		
		try {
			new Triangle(2, 3, 1);
		} catch (Exception e) {
			collector.addError(e);
		}
		
		try {
			new Triangle(3, 1, 2);
		} catch (Exception e) {
			collector.addError(e);
		}
	}
	
	@Test(expected = TriangleInvalide.class)
	public void testTriangleInvalid() throws TriangleInvalide {
		new Triangle(1, 2, 5);
	}
	
	@Test(expected = TriangleInvalide.class)
	public void testTriangleInvalidNegative() throws TriangleInvalide {
		new Triangle(-1, 2, 3);
	}

	@Test
	public void testEstEquilateralOK() throws TriangleInvalide {
		Triangle t = new Triangle(5, 5, 5);
		assertTrue(t.estEquilateral());
	}
	
	@Test
	public void testEstEquilateralNOK() throws TriangleInvalide {
		Triangle t;
		
		t = new Triangle(1, 2, 3);
		assertFalse(t.estEquilateral());
		
		t = new Triangle(2, 3, 1);
		assertFalse(t.estEquilateral());
		
		t = new Triangle(3, 1, 2);
		assertFalse(t.estEquilateral());	
		
		t = new Triangle(3, 3, 2);
		assertFalse(t.estEquilateral());
	}

	@Test
	public void testEstIsoceleOK() throws TriangleInvalide {
		Triangle t;
		
		t = new Triangle(4, 2, 2);
		assertTrue(t.estIsocele());
		
		t = new Triangle(2, 4, 4);
		assertTrue(t.estIsocele());
		
		t = new Triangle(2, 4, 2);
		assertTrue(t.estIsocele());
		
		t = new Triangle(2, 2, 2);
		assertTrue(t.estIsocele());
	}
	
	@Test
	public void testEstIsoceleNOK() throws TriangleInvalide {
		Triangle t;
		
		t = new Triangle(1, 2, 3);
		assertFalse(t.estIsocele());
		
		t = new Triangle(2, 3, 1);
		assertFalse(t.estIsocele());
		
		t = new Triangle(3, 1, 2);
		assertFalse(t.estIsocele());
	}

	@Test
	public void testEstRectangleOK() throws TriangleInvalide {
		Triangle t;
		
		t = new Triangle(3, 4, 5);
		assertTrue(t.estRectangle());
		
		t = new Triangle(4, 5, 3);
		assertTrue(t.estRectangle());
		
		t = new Triangle(5, 3, 4);
		assertTrue(t.estRectangle());
	}
	
	@Test
	public void testEstRectangleNOK() throws TriangleInvalide {
		Triangle t;
		
		t = new Triangle(1, 2, 3);
		assertFalse(t.estRectangle());
		
		t = new Triangle(2, 3, 1);
		assertFalse(t.estRectangle());
		
		t = new Triangle(3, 1, 2);
		assertFalse(t.estRectangle());
	}

	@Test
	public void testEstUnTriangleOK() {
		assertTrue(Triangle.estUnTriangle(1, 2, 3));
		assertTrue(Triangle.estUnTriangle(2, 3, 1));
		assertTrue(Triangle.estUnTriangle(3, 1, 2));
	}
	
	@Test
	public void testEstUnTriangleNOK() {
		assertFalse(Triangle.estUnTriangle(1, 2, 4));
		assertFalse(Triangle.estUnTriangle(2, 4, 1));
		assertFalse(Triangle.estUnTriangle(4, 1, 2));
		
		assertFalse(Triangle.estUnTriangle(1, 1, 4));
		assertFalse(Triangle.estUnTriangle(4, 1, 1));
		assertFalse(Triangle.estUnTriangle(1, 4, 1));
		
		assertFalse(Triangle.estUnTriangle(-1, 2, 4));
		assertFalse(Triangle.estUnTriangle(2, -4, 1));
		assertFalse(Triangle.estUnTriangle(4, 1, -2));
	}
	
	@Test
	public void testToString() throws TriangleInvalide {
		Triangle t = new Triangle(1, 2, 3);
		assertNotNull(t.toString());
	}

}
