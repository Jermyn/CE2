import static org.junit.Assert.*;

import org.junit.Test;


public class TextBuddyTest {

	TextBuddy textBuddy=new TextBuddy();
	@Test
	public void testaddCommand() {
		String strFile[], result="", element="";
		Integer index;
		strFile="mytextfile".split(" ");
		//TextBuddy.main(strFile);
		TextBuddy.checkFile(strFile);
		TextBuddy.init("mytextfile");
		TextBuddy.addCommand("china");
		for(String s:textBuddy.arrFile) {
			element+=textBuddy.arrFile;
		}
		for(index=0; index<textBuddy.arrFile.size(); index++) {
			index++;
			result=index.toString().concat(". ");
			result=result.concat(element);
			result=result.replaceAll("\\[", "").replaceAll("\\]", "");
			testCommand("1. china", result);
			index--;
		}
		//testCommand("1. china", result);
	}
	
	@Test
	public void testclearCommand() {
		int index;
		String result="";
		TextBuddy.clearCommand();
		for(index=0; index<textBuddy.arrFile.size(); index++) {
			result=textBuddy.arrFile.get(index);
		}
		testCommand("", result);
	}
	
	private void testCommand(String description, String expected) {
		assertEquals(description, expected);
	}
	

}
