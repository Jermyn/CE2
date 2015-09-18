import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;


public class TextBuddyTest {

	
	@Before
	public void setUp() {
		//TextBuddy textBuddy=new TextBuddy();
		String strFile[];
		strFile="mytextfile".split(" ");
		TextBuddy.checkFile(strFile);
		TextBuddy.init("mytextfile");
	}
	
	@Test
	public void testaddCommand() {
		String result="", element="";
		Integer index;
		setUp();
		assertEquals("add west", ("added to mytextfile:\"west\""), TextBuddy.addCommand("west"));
		assertEquals("add east", ("added to mytextfile:\"east\""), TextBuddy.addCommand("east"));
		assertEquals("add north", ("added to mytextfile:\"north\""), TextBuddy.addCommand("north"));
		
		assertEquals("search st", ("1. west\n2. east\n"), TextBuddy.searchCommand("st"));
		assertEquals("search th", ("1. north\n"), TextBuddy.searchCommand("th"));
		assertEquals("search nt", ("No such element!"), TextBuddy.searchCommand("nt"));
	}
	
	/*@Test
	public void testclearCommand() {
		int index;
		String result="";
		TextBuddy.clearCommand();
		for(index=0; index<TextBuddy.arrFile.size(); index++) {
			result=TextBuddy.arrFile.get(index);
		}
		testCommand("", result);
	}*/
	
	private void testCommand(String description, String expected) {
		assertEquals(description, expected);
	}
	

}
