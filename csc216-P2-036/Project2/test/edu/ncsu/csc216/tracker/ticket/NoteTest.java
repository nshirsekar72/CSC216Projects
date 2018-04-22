package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Note class
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class NoteTest {
	/**
	 * Tests Note constructor
	 */
	@Test
	public void testNote() {
		//test valid note
		Note n1 = new Note("mklemons", "hello");
		assertEquals("mklemons", n1.getNoteAuthor());
		assertEquals("hello", n1.getNoteText());
		
		//test passing in null
		try {
			new Note(null, "Hi");
			fail();
		} catch(Exception e){
			assertEquals("java.lang.IllegalArgumentException", e.getClass().getName());
		}
		
		try {
			new Note("mklemons", null);
			fail();
		} catch(Exception e){
			assertEquals("java.lang.IllegalArgumentException", e.getClass().getName());
		}
		
		try {
			new Note("", "Hi");
			fail();
		} catch(Exception e){
			assertEquals("java.lang.IllegalArgumentException", e.getClass().getName());
		}
		
		try {
			new Note("mklemons", "");
			fail();
		} catch(Exception e){
			assertEquals("java.lang.IllegalArgumentException", e.getClass().getName());
		}
	}
	
	/**
	 * Test getting the note array of author and text
	 */
	@Test
	public void testGetNoteArray() {
		Note n1 = new Note("mklemons", "hello");
		String[] s1 = n1.getNoteArray();
		assertEquals("mklemons", s1[0]);
		assertEquals("hello", s1[1]);
	}

}
