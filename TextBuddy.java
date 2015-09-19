import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.System;

/**
 * This class is used to manipulate text in a file.
 * Commands such as add, display, delete, clear and exit are used
 * by the user. Each time an operation such as add, delete and clear
 * are used, the file is being saved to the disk.
 * @author jermyn lai
 *
 */

public class TextBuddy {
	
	/** These messages shown to the user are defined in one place for easy accessing and editing. */
	private static final String INVALID_MESSAGE = "Invaid Command\n";
	private static final String ADD_MESSAGE = "added to %s:\"%s\"\n";
	private static final String DELETE_MESSAGE = "deleted from %s: \"%s\"\n";
	private static final String EMPTY_DELETE_MESSAGE="Error! Please type a number to delete.\n";
	private static final String CLEAR_MESSAGE = "all content deleted from %s\n";
	private static final String SORT_MESSAGE = "all content sorted alphabetically\n";
	private static final String ERROR_ADD_MESSAGE="Type an input to add\n";
	private static final String EMPTY_FILE_MESSAGE="%s is empty\n";
	private static final String EMPTY_NUMBER_MESSAGE="Error! Please type a number to delete.\n";
	private static final String EMPTY_MESSAGE="No such element\n";
	private static final String INPUT_SEARCH_MESSAGE="Error! Please type an input to search.\n";
	private static final String ERROR_NUMBER_NOT_FOUND="Number not found\n";
	private static final String ERROR_TYPE_FILE="No file typed. Please type a file.\n";
	private static final String ERROR_WRONG_FILE="File not found! Please type the correct file.";
	private static final String ERROR_EMPTY_COMMAND="Command cannot be empty";
	private static final String ADD_COMMAND="add";
	private static final String DELETE_COMMAND="delete";
	private static final String DISPLAY_COMMAND="display";
	private static final String CLEAR_COMMAND="clear";
	private static final String SORT_COMMAND="sort";
	private static final String SEARCH_COMMAND="search";
	private static final String INVALID_COMMAND="Invalid Command Type";
	private static final String ERROR_READING="Error reading file";
	
	/** These are possible command types */
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, SORT, SEARCH
	};
	
	/**This is the name of the file that will be read or written to by the user. */
	private static String documentName;
	
	/** These are the filereader, filewriter, bufferedreader, 
	 * bufferedwriter and printwriter to read or write 
	 * to the particular file.
	 */
	private static FileReader read;
	private static BufferedReader reader;
	private static FileWriter write;
	private static BufferedWriter writer;
	private static PrintWriter printToFile;
	
	/** This is the arraylist to store the text based on the 
	 *  command operation the user uses.
	 */
	public static ArrayList<String> arrFile;
	
	
	/**
	 * This variable is declared for the whole class (instead of declaring it
	 * inside the readUserCommand() method to facilitate automated testing using
	 * the I/O redirection technique. If not, only the first line of the input
	 * text file will be processed.
	 */
	private static Scanner sc=new Scanner (System.in);
	
	public static void main(String[] args) {
		checkFile(args);
		init(documentName);
		userTypeCommand();
	}
	
	/** Checking whether it is the right file input by the user
	 * is important.
	 */
	public static void checkFile(String[] args) {
		if (args.length == 0) {
			System.out.println(ERROR_TYPE_FILE);
			System.exit(0);
		}
		documentName=args[0];
	}
	
	public static void init(String fileName)  {
		try {
			read = new FileReader(fileName);
			reader = new BufferedReader(read);
			arrFile = new ArrayList<String>();
			System.out.print("Welcome to TextBuddy. " + fileName + " is ready for use");
		} catch(FileNotFoundException e) {
			System.out.println(ERROR_WRONG_FILE);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private static void userTypeCommand() {
		String commandString;
		println("");
		while (true) {
			println("command: ");
			commandString = sc.next();
			executeUserCommand(commandString);
		}
	}
	
	public static void executeUserCommand(String userCommand) {
		String result="";
		if (userCommand.trim().equals("")) {
			println(String.format(INVALID_MESSAGE));
		} else {
			COMMAND_TYPE command = determineCommandType(userCommand);
			result=executeCommand(command);
			print(result);
		}
	}  
	
	/**
	 * This operation determines which type of commands the user
	 * wants to perform
	 * 
	 * @param commandTypeString
	 *            is the command that the user wants to perform
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) throws Error {
		if (commandTypeString == null) {
			throw new Error (ERROR_EMPTY_COMMAND);
		} else if (commandTypeString.equalsIgnoreCase(ADD_COMMAND)) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase(DISPLAY_COMMAND)) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase(DELETE_COMMAND)) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase(CLEAR_COMMAND)) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase(SORT_COMMAND)) {
			return COMMAND_TYPE.SORT;
		} else if (commandTypeString.equalsIgnoreCase(SEARCH_COMMAND)) {
			return COMMAND_TYPE.SEARCH;
		} else {
			return COMMAND_TYPE.EXIT;
		}
	}
	
	/** If the rest of the program is correct, this error will never be thrown.
	 * That is why we use an Error instead of an Exception.
	 */
	private static String executeCommand(COMMAND_TYPE command) throws Error {
		String result;
		switch (command) {
			case ADD:  
				result=addCommand(sc.next());
				return result;
			case DISPLAY: 
				result=displayCommand();
				return result;
			case DELETE:  
				result=deleteCommand();
				return result;
			case CLEAR: 
				result=clearCommand();
				return result;
			case SORT:
				result=sortCommand();
				return result;
			case SEARCH:
				result=searchCommand(sc.next().trim());
				return result;
			case EXIT: 
				System.exit(0);
				//Fallthrough
			default: 
				throw new Error (INVALID_COMMAND);
				//Fallthrough
		}		
	}
	/**
	 * This operation will store the texts from the arraylist to the file.
	 */
	private  static void store() {
		int index = 0;
		try {
			write = new FileWriter(documentName);
			writer = new BufferedWriter(write);
			printToFile = new PrintWriter(writer);
			while (index < arrFile.size()) {
				printToFile.println((arrFile.get(index)));
				index++;
			}
			writer.close();
		} catch (IOException e) {
			println(ERROR_READING);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * This operation executes the add command to insert the 
	 * data into the text file.
	 * @param lineAdded 	lineAdded is the word(s) after 
	 * the add command to be added to the file.
	 */
	public static String addCommand(String lineAdded) {
		if (lineAdded!="") {
			arrFile.add(lineAdded);
			store();
			return (String.format(ADD_MESSAGE, documentName, lineAdded));
		} else {
			return ERROR_ADD_MESSAGE;
		}
	}
	
	/**
	 * This operation display the data in the text file.
	 * But it will also show that the file is empty if it is.				
	 */
	public static String displayCommand() {
		Integer index = 0;
		String result="";
		if (arrFile.size() == 0) {
			return (String.format(EMPTY_FILE_MESSAGE, documentName));
		}
		while (index < arrFile.size()) {
			index++;
			result=result.concat(index + ". " + arrFile.get(index-1) + "\n");
		}
		return result;
	}
	
	/**
	 * This operation delete the particular data preferred by the user.
	 * Exception is used for cases where the user types a non-number to delete.
	 */
	public static String deleteCommand(){
		int deleteIndex;
		Integer deleteID;
		String lineRemoved, result="";
		try {
			deleteIndex=sc.nextInt()-1;
			deleteID=new Integer(deleteIndex);
			if(deleteID.toString().isEmpty()) {
				result=EMPTY_DELETE_MESSAGE;
				sc.nextLine();
				return result;
			}
			if ((deleteIndex < arrFile.size() && ((deleteIndex+1) > 0))) {
				lineRemoved = arrFile.get(deleteIndex);
				arrFile.remove(deleteIndex);
				store();
				return result=String.format(DELETE_MESSAGE, documentName, lineRemoved);
			} else {
				return ERROR_NUMBER_NOT_FOUND;
			}
		} catch (Exception e){
			result=EMPTY_DELETE_MESSAGE;
			sc.nextLine();
			return result;
		}
	}
	
	/**
	 * This operation clears all data in the text file
	 * and states that it has cleared all the contents in the text file.
	 */
	public static String clearCommand() {
		String result = "";
		arrFile.clear();
		store();
		result=String.format(CLEAR_MESSAGE, documentName);
		return result;
	}
	
	/**
	 * This operation sorts the elements in the text file 
	 * alphabetically.
	 */
	public static String sortCommand() {
		if (arrFile.isEmpty()) {
			return String.format(EMPTY_FILE_MESSAGE, documentName);
		} else {
			Collections.sort(arrFile);
			store();
			return SORT_MESSAGE;
		}
	}
	
	/**
	 * This operation searches for the element(s) that contains
	 * the input character(s).
	 */
	public static String searchCommand(String searchString) {
		int searchIndex, numItemsFound=0;
		String result="";
		try {
			if (searchString.equals(null)||searchString.equals("")) {
				return EMPTY_NUMBER_MESSAGE;

			} else if (arrFile.size()==0) {
				return String.format(EMPTY_FILE_MESSAGE, documentName);
			}
			searchString=searchString.trim();
			for (searchIndex=0; searchIndex<arrFile.size(); searchIndex++) {
				if (arrFile.get(searchIndex).toString().contains(searchString)) {
					numItemsFound++;
					result=result.concat(numItemsFound + ". " + arrFile.get(searchIndex) + "\n");
				}
			}
			if (numItemsFound==0) {
				return EMPTY_MESSAGE;
			}
		} catch (Exception e) {
			return INPUT_SEARCH_MESSAGE;
		}
		return result;
	}
	
	/**
	 * This operation prints the input with newline.
	 * This is done to enable SLAP.
	 * @param input
	 */
	public static void println(String input) {
		System.out.println(input);
	}
	
	/**
	 * This operation prints the input with no newline.
	 * This is done to enable SLAP.
	 * @param input
	 */
	public static void print(String input) {
		System.out.print(input);
	}
}

