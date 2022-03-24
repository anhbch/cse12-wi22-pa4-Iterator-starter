
/**
 * Name: Anh Bach
 * ID: A17133630
 * Email: tbach@ucsd.edu
 * Sources used: None
 * This is the custom test for a ListIterator
 * Its contains tests that test all methods in the class
 * that implements ListIterator interface
 */

import static org.junit.Assert.*;

import org.junit.*;

import java.util.NoSuchElementException;

/**
 * Custom tests for a LitIterator implementation list in MyLikedList.java file
 * It contains tests that test all methods.
 * 
 * IMPORTANT: Do not change the method headers and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class MyLinkedListCustomTester {

    private MyLinkedList myList1, myList2;
    private MyLinkedList.MyListIterator myListIter1, myListIter2;

    static final String EXCEPTION = "Exception is thrown!";

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        // List 1: empty list
        myList1 = new MyLinkedList<>();
        myListIter1 = myList1.new MyListIterator();

        // List 2: non empty list
        myList2 = new MyLinkedList<>();
        myList2.add(2);
        myList2.add(4);
        myList2.add(6);
        myListIter2 = myList2.new MyListIterator();

    }

    /**
     * Test the hasNext method when the list is empty
     */
    @Test
    public void testHasNext() {
        assertFalse("Return false when the list is empty", myListIter1.hasNext());
    }

    /**
     * Test the next method when the list is empty
     */
    @Test
    public void testNext() {
        try {
            myListIter1.next();
            fail();
        }
        catch (NoSuchElementException e) {
            System.out.println(EXCEPTION);
        }
        // Check index, forward, canRemoveOrSet
        assertEquals(0, myListIter1.idx);
        assertEquals(true, myListIter1.forward);
        assertEquals(false, myListIter1.canRemoveOrSet);
    }

    /**
     * Test the hasPrevious method when the list is empty
     */
    @Test
    public void testHasPrevious() {
        assertFalse("Return false when the list is empty", myListIter1.hasPrevious());
    }

    /**
     * Test the previous method when the list is empty
     */
    @Test
    public void testPrevious() {
        try {
            myListIter1.previous();
            fail();
        }
        catch (NoSuchElementException e) {
            System.out.println(EXCEPTION);
        }
        // Check index, forward, canRemoveOrSet
        assertEquals(0, myListIter1.idx);
        assertEquals(true, myListIter1.forward);
        assertEquals(false, myListIter1.canRemoveOrSet);
    }

    /**
     * Test the nextIndex method when the list is empty
     * and after add a new element to the list
     */
    @Test
    public void testNextIndex() {
        // When the list is empty
        assertEquals(0, myListIter1.nextIndex());

        // After add a new element to the list
        myList1.add("Hello");
        myListIter1.next(); // Call next() after adding an element
        // Check index
        assertEquals(1, myListIter1.nextIndex());
        }

    /**
     * Test the previousIndex method when the list is empty
     * and after add 4 new element to the list
     * and move the pointers to the end of the list
     */
    @Test
    public void testPreviousIndex() {
        // When the list is empty
        assertEquals(-1, myListIter1.previousIndex());

        // After add 4 new element to the list and move 
        myList1.add("Hi");
        myList1.add("CSE");
        myList1.add("12");
        myList1.add("PA4");
        // Move iterator 
        myListIter1.next();
        myListIter1.next();
        myListIter1.next();
        assertEquals(2, myListIter1.previousIndex());
        assertEquals(3,myListIter1.idx);
        myListIter1.previous();
        assertEquals(2, myListIter1.idx);
    }

    /**
     * Test the set method when the new element is null
     */
    @Test
    public void testSet() {
        // Add null element to the list
        try {
            myListIter2.set(null);
            fail();
        }
        catch (NullPointerException e) {
            System.out.println(EXCEPTION);
        }
    }

    /**
     * Test the remove method when add new element to the list is empty
     */
    @Test
    public void testRemoveTestOne() {
        myListIter1.add(10);
        try {
            myListIter1.remove();
        }
        catch (IllegalStateException e) {
            System.out.println(EXCEPTION);
        }
    }

    /**
     * Test the remove method when next() is called 
     * and remove() when the list is empty
     */
    @Test
    public void testRemoveTestTwo() {
        // When next() is called
        myListIter2.next();
        myListIter2.next();
        myListIter2.remove();
        assertEquals(6, myListIter2.right.getElement());
        assertEquals(2, myListIter2.left.getElement());

        // When the list is empty
        try {
            myListIter1.remove();
            fail();
        }
        catch (IllegalStateException e) {
            System.out.println(EXCEPTION);
        }
    }

    /**
     * Test the add method when element is invalid
     */
    @Test
    public void testAdd() {
        try {
            myListIter2.add(null);
            fail();
        }
        catch (NullPointerException e) {
            System.out.println(EXCEPTION);
        }
    }

}