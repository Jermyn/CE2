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
	public void testSearchCommand() {
		assertEquals("add west", ("added to mytextfile:\"west\"\n"), TextBuddy.addCommand("west"));
		assertEquals("add east", ("added to mytextfile:\"east\"\n"), TextBuddy.addCommand("east"));
		assertEquals("add north", ("added to mytextfile:\"north\"\n"), TextBuddy.addCommand("north"));
		assertEquals("add south", ("added to mytextfile:\"south\"\n"), TextBuddy.addCommand("south"));
		assertEquals("add singapore", ("added to mytextfile:\"singapore\"\n"), TextBuddy.addCommand("singapore"));
		assertEquals("add malaysia", ("added to mytextfile:\"malaysia\"\n"), TextBuddy.addCommand("malaysia"));
		assertEquals("add indonesia", ("added to mytextfile:\"indonesia\"\n"), TextBuddy.addCommand("indonesia"));
		assertEquals("add india", ("added to mytextfile:\"india\"\n"), TextBuddy.addCommand("india"));
		assertEquals("search st", ("1. west\n2. east\n"), TextBuddy.searchCommand("st"));
		assertEquals("search th", ("1. north\n2. south\n"), TextBuddy.searchCommand("th"));
		assertEquals("search nt", ("No such element\n"), TextBuddy.searchCommand("nt"));
		assertEquals("search sin", ("1. singapore\n"), TextBuddy.searchCommand("sin"));
		assertEquals("search ia", ("1. malaysia\n2. indonesia\n3. india\n"), TextBuddy.searchCommand("ia"));
		assertEquals("search ma", ("1. malaysia\n"), TextBuddy.searchCommand("ma"));
		assertEquals("search do", ("1. indonesia\n"), TextBuddy.searchCommand("do"));
		assertEquals("search es", ("1. west\n2. indonesia\n"), TextBuddy.searchCommand("es"));
		assertEquals("search sia", ("1. malaysia\n2. indonesia\n"), TextBuddy.searchCommand("sia"));
		assertEquals("search zi", ("No such element\n"), TextBuddy.searchCommand("zi"));
		assertEquals("clear texts in file", ("all content deleted from mytextfile\n"), TextBuddy.clearCommand());
		assertEquals("search ias", ("mytextfile is empty\n"), TextBuddy.searchCommand("zi"));
	}
	
	@Test
	public void testSortCommand() {
		assertEquals("add west", ("added to mytextfile:\"west\"\n"), TextBuddy.addCommand("west"));
		assertEquals("add east", ("added to mytextfile:\"east\"\n"), TextBuddy.addCommand("east"));
		assertEquals("add north", ("added to mytextfile:\"north\"\n"), TextBuddy.addCommand("north"));
		assertEquals("add south", ("added to mytextfile:\"south\"\n"), TextBuddy.addCommand("south"));
		assertEquals("add singapore", ("added to mytextfile:\"singapore\"\n"), TextBuddy.addCommand("singapore"));
		assertEquals("add malaysia", ("added to mytextfile:\"malaysia\"\n"), TextBuddy.addCommand("malaysia"));
		assertEquals("add indonesia", ("added to mytextfile:\"indonesia\"\n"), TextBuddy.addCommand("indonesia"));
		assertEquals("add india", ("added to mytextfile:\"india\"\n"), TextBuddy.addCommand("india"));
		assertEquals("sort", "all content sorted alphabetically\n", TextBuddy.sortCommand());
		assertEquals("display", "1. east\n2. india\n3. indonesia\n4. malaysia\n5. north\n6. singapore\n7. south\n8. west\n", TextBuddy.displayCommand());
		assertEquals("clear texts in file", ("all content deleted from mytextfile\n"), TextBuddy.clearCommand());
		assertEquals("sort", "mytextfile is empty\n", TextBuddy.sortCommand());
	}
	
	
	

}
