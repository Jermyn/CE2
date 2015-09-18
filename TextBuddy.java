
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
	private static final String INVALID_MESSAGE = "Invaid Command";
	private static final String ADD_MESSAGE = "added to %s: \"%s\"";
	private static final String DELETE_MESSAGE = "deleted from %s: \"%s\"";
	private static final String CLEAR_MESSAGE = "all content deleted from %s";
	private static final String SORT_MESSAGE = "all content sorted alphabetically";
	
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
			executeCommand(command);
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
	private static void executeCommand(COMMAND_TYPE command) throws Error {
		String result;
		switch(command) {
			case ADD:  
				addCommand(sc.nextLine());
				//break;
			case DISPLAY: 
				displayCommand();
				//break;
			case DELETE:  
				deleteCommand();
				//break;
			case CLEAR: 
				clearCommand();
				//break;
			case SORT:
				sortCommand();
				//break;
			case SEARCH:
				searchCommand(sc.nextLine());
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
	public static void addCommand(String lineAdded) {
		arrFile.add(lineAdded);
		store();
		System.out.println(String.format(ADD_MESSAGE.trim(), documentName, lineAdded));
	}
	
	/**
	 * This operation display the data in the text file.
	 * But it will also show that the file is empty if it is.				
	 */
	public static void displayCommand() {
		Integer index = 0;
		String result="";
		if(arrFile.size() == 0) {
			System.out.println(documentName+" is empty");
		}
		while(index < arrFile.size()) {
			index++;
			System.out.println(index.toString() + ". " + arrFile.get(index-1));
		}	
	}
	
	/**
	 * This operation delete the particular data preferred by the user.
	 * Exception is used for cases where the user types a non-number to delete.
	 */
	public static void deleteCommand(){
		int deleteIndex;
		String lineRemoved, result="";
		try {
			deleteIndex = sc.nextInt()-1;
			if((deleteIndex < arrFile.size() && ((deleteIndex+1) > 0))) {
				lineRemoved = arrFile.get(deleteIndex);
				arrFile.remove(deleteIndex);
				store();
				System.out.println(String.format(DELETE_MESSAGE.trim(), documentName, lineRemoved));
			} else {
				System.out.println("Number not found");
			}
		} catch(Exception e){
			System.out.println("Error! Please type a number to delete.");
			sc.nextLine();
		}
	}
	
	/**
	 * This operation clears all data in the text file
	 * and states that it has cleared all the contents in the text file.
	 */
	public static void clearCommand() {
		arrFile.clear();
		store();
		System.out.println(String.format(CLEAR_MESSAGE, documentName));
	}
	
	/**
	 * This operation sorts the elements in the text file 
	 * alphabetically.
	 */
	public static void sortCommand() {
		Collections.sort(arrFile);
		store();
		System.out.println(SORT_MESSAGE);
	}
	
	/**
	 * This operation searches for the element(s) that contains
	 * the input character(s).
	 */
	public static void searchCommand(String searchString) {
		Integer searchIndex;
		String stringInArrList="", result="";
		boolean isContained;
		try {
			searchString=searchString.trim();
			for(searchIndex=0; searchIndex<arrFile.size(); searchIndex++) {
				stringInArrList=arrFile.get(searchIndex).trim();
				isContained=stringInArrList.contains(searchString);
				if(isContained) {
					searchIndex++;
					System.out.println(searchIndex.toString() + ". " + arrFile.get(searchIndex-1));
					searchIndex--;
				}
			}
		} catch (Exception e) {
			System.out.println("Error! Please type an input to search.");
		}
	}
}

