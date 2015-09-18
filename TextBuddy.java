
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
import java.io.File;

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
	//These messages shown to the user are defined in one place for easy accessing and editing.
	private static final String INVALID_MESSAGE = "Invaid Command\n";
	private static final String ADD_MESSAGE = "added to %s:\"%s\"\n";
	private static final String DELETE_MESSAGE = "deleted from %s: \"%s\"\n";
	private static final String CLEAR_MESSAGE = "all content deleted from %s\n";
	private static final String SORT_MESSAGE = "all content sorted alphabetically\n";
	
	//These are possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, SORT, SEARCH
	};
	
	//This is the name of the file that will be read or written to by the user.
	private static String documentName;
	//private static File documentName;
	
	/* These are the filereader, filewriter, bufferedreader, 
	 * bufferedwriter and printwriter to read or write 
	 * to the particular file.
	 */
	private static FileReader read;
	private static FileWriter write;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private static PrintWriter printToFile;
	
	// This is the arraylist to store the text based on the
	// command operation the user uses.
	public static ArrayList<String> arrFile;
	
	
	/*
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
	
	/* Checking whether it is the right file input by the user
	 * is important.
	 */
	public static void checkFile(String[] args) {
		if(args.length == 0) {
			System.out.println("No file typed. Please type a file.\n");
			System.exit(0);
		}
		documentName=args[0];
	}
	
	public static void init(String fileName)  {
		try {
			read = new FileReader(fileName);
			reader = new BufferedReader(read);
			arrFile = new ArrayList<String>();
			System.out.println("Welcome to TextBuddy. " + fileName + " is ready for use");
		} catch(FileNotFoundException e) {
			System.out.println("File not found! Please type the correct file.");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private static void userTypeCommand() {
		String commandString, result;
		while (true) {
			System.out.print("command: ");
			commandString = sc.next();
			executeUserCommand(commandString);
		}
	}
	
	public static void executeUserCommand(String userCommand) {
		String result="";
		if(userCommand.trim().equals("")) {
			System.out.println(String.format(INVALID_MESSAGE));
		} else {
			COMMAND_TYPE command = determineCommandType(userCommand);
			result=executeCommand(command);
			System.out.println(result);
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
		if(commandTypeString == null) {
			throw new Error ("Command cannot be empty");
		} else if(commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if(commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if(commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if(commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if(commandTypeString.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
		} else if(commandTypeString.equalsIgnoreCase("search")) {
			return COMMAND_TYPE.SEARCH;
		} else {
			return COMMAND_TYPE.EXIT;
		}
	}
	
	/* If the rest of the program is correct, this error will never be thrown.
	 * That is why we use an Error instead of an Exception.
	 */
	private static String executeCommand(COMMAND_TYPE command) throws Error {
		String result;
		switch(command) {
			case ADD:  
				result=addCommand(sc.nextLine());
				return result;
				//break;
			case DISPLAY: 
				result=displayCommand();
				return result;
				//break;
			case DELETE:  
				result=deleteCommand();
				return result;
				//break;
			case CLEAR: 
				result=clearCommand();
				return result;
				//break;
			case SORT:
				result=sortCommand();
				return result;
				//break;
			case SEARCH:
				result=searchCommand(sc.nextLine().trim());
				return result;
				//break;
			case EXIT: 
				System.exit(0);
				//Fallthrough
			default: 
				throw new Error ("Invalid Command Type");
				//Fallthrough
		}		
	}
	
	private  static void store() {
		int index = 0;
		try {
			write = new FileWriter(documentName);
			writer = new BufferedWriter(write);
			printToFile = new PrintWriter(writer);
			while(index < arrFile.size()) {
				printToFile.println((arrFile.get(index)));
				index++;
			}
			writer.close();
		} catch(IOException e) {
			System.out.println("Error reading file");
			System.exit(0);
		}
	}
	
	/**
	 * This operation executes the add command to insert the 
	 * data into the text file.
	 * @param lineAdded 
	 * 				is the word(s) after the add command to be 
	 *  			added to the file.
	 */
	public static String addCommand(String lineAdded) {
		arrFile.add(lineAdded);
		store();
		return (String.format(ADD_MESSAGE.trim(), documentName, lineAdded));
		//System.out.println(String.format(ADD_MESSAGE.trim(), documentName, lineAdded));
	}
	
	/**
	 * This operation display the data in the text file.
	 * But it will also show that the file is empty if it is.				
	 */
	public static String displayCommand() {
		Integer index = 0;
		String result="";
		if(arrFile.size() == 0) {
			return (String.format(documentName + "is empty", ""));
			//return result;
			//System.out.println(documentName+" is empty");
		}
		while(index < arrFile.size()) {
			index++;
			result=index.toString() + ". " + arrFile.get(index-1);
			return result;
			//System.out.println(index.toString() + ". " + arrFile.get(index-1));
		}	
		return result;
	}
	
	/**
	 * This operation delete the particular data preferred by the user.
	 * Exception is used for cases where the user types a non-number to delete.
	 */
	public static String deleteCommand(){
		Integer deleteIndex;
		String lineRemoved, result="";
		try {
			deleteIndex = sc.nextInt()-1;
			if(deleteIndex.toString().isEmpty()) {
				result="Error! Please type a number to delete.";
			}
			if((deleteIndex < arrFile.size() && ((deleteIndex+1) > 0))) {
				lineRemoved = arrFile.get(deleteIndex);
				arrFile.remove(deleteIndex);
				store();
				result=String.format(DELETE_MESSAGE.trim(), documentName, lineRemoved);
				//System.out.println(String.format(DELETE_MESSAGE.trim(), documentName, lineRemoved));
			} else {
				result="Number not found";
				//System.out.println("Number not found");
			}
		} catch(Exception e){
			result="Error! Please type a number to delete.";
			//System.out.println("Error! Please type a number to delete.");
			sc.nextLine();
		}
		return result;
	}
	
	/**
	 * This operation clears all data in the text file
	 * and states that it has cleared all the contents in the text file.
	 */
	public static String clearCommand() {
		String result="";
		arrFile.clear();
		store();
		result=String.format(CLEAR_MESSAGE, documentName);
		return result;
		//System.out.println(String.format(CLEAR_MESSAGE, documentName));
	}
	
	/**
	 * This operation sorts the elements in the text file 
	 * alphabetically.
	 */
	public static String sortCommand() {
		Collections.sort(arrFile);
		store();
		return SORT_MESSAGE;
		//System.out.println(SORT_MESSAGE);
	}
	
	/**
	 * This operation searches for the element(s) that contains
	 * the input character(s).
	 */
	public static String searchCommand(String searchString) {
		int searchIndex, numItemsFound=0;
		String stringInArrList="", result="";
		try {
			if(searchString.equals(null)||searchString.equals("")) {
				return result="Error! Please type an input to search.";
			}
			searchString=searchString.trim();
			for(searchIndex=0; searchIndex<arrFile.size(); searchIndex++) {
				//stringInArrList=arrFile.get(searchIndex).trim();
				if(arrFile.get(searchIndex).toString().contains(searchString)) {
					numItemsFound++;
					result=result.concat(numItemsFound + ". " + arrFile.get(searchIndex) + "\n");
				}
			}
			if(numItemsFound==0) {
				result="No such element!";
			}
		} catch (Exception e) {
			//System.out.println("Error! Please type an input to search.");
			result="Error! Please type an input to search.";
		}
		return result;
	}
}

