import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;


public class TextBuddyTest {

	
	@Before
	public void setUp() {
		String strFile[];
		strFile="mytextfile".split(" ");
		TextBuddy.checkFile(strFile);
		TextBuddy.init("mytextfile");
	}
	
	@Test
	public void testAddCommand() {
		setUp();
		assertEquals("add west", ("added to mytextfile:\"west\""), TextBuddy.addCommand("west"));
		assertEquals("add east", ("added to mytextfile:\"east\""), TextBuddy.addCommand("east"));
		assertEquals("add north", ("added to mytextfile:\"north\""), TextBuddy.addCommand("north"));
	}
	
	@Test
	public void testSearchCommand() {
		assertEquals("add west", ("added to mytextfile:\"west\""), TextBuddy.addCommand("west"));
		assertEquals("add east", ("added to mytextfile:\"east\""), TextBuddy.addCommand("east"));
		assertEquals("add north", ("added to mytextfile:\"north\""), TextBuddy.addCommand("north"));
		assertEquals("add south", ("added to mytextfile:\"south\""), TextBuddy.addCommand("south"));
		assertEquals("search st", ("1. west\n2. east\n"), TextBuddy.searchCommand("st"));
		assertEquals("search th", ("1. north\n"), TextBuddy.searchCommand("th"));
		assertEquals("search nt", ("No such element"), TextBuddy.searchCommand("nt"));
	}
	
	@Test
	public void testSortCommand() {
		assertEquals("sort", "all content sorted alphabetically", TextBuddy.sortCommand());
	}
	
	
	

}
