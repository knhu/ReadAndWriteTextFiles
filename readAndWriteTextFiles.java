/* This program reads ints from a file and outputs those same ints into
 * another file, without duplicates. The program will read the user's input
 * for a file name, and will read another input from the user to put those
 * numbers in the 2nd file name that the user names.   
 *
 * Authors: Matthew Gloriani and Kevin Nhu
 *
 * Date Last Modified: March 4, 2021
 */

import java.util.*; // import all utilities (need scanner)
import java.io.*; // import all input/output

public class proj2 {

	// The main method
	public static void main(String[] args) {
		while(true) {
			Scanner scan = new Scanner(System.in);

			// calls the ascii reader
			ascii(scan);

			System.out.println("Do you want to run the program again? "
								+ "(y for yes and n for no)");
			String decision = scan.nextLine();
			System.out.println();
			if (decision.charAt(0) == 'n') {
				System.exit(1); // exits out of the program
			}

		}
	}

	
	// This method will read a user's input to read a file
	// @param scan  - scanner object 
	public static void ascii(Scanner scan) {
		// This will read user's input for which file to use
		System.out.println("Please enter the name of an ASCII file that "
							+ "contains a sorted list of integer numbers "
							+ "one per line: ");
		String userInfile = scan.nextLine();
		System.out.println();
		File infile = new File(userInfile);
		BufferedReader reader; 
		try { // checks to see if file exists
			reader = new BufferedReader(new FileReader(infile));
		}
		catch (IOException e) {
			System.out.println("Please enter an existing file");
			return;
		}


		// This will read a user's input to write to the file
		System.out.println("Please enter the name of the output file that "
							+ "saves the result from the program:");
		String userOutfile = scan.nextLine();
		System.out.println();
		File outfile;
		try {
			outfile = new File(userOutfile);
			if (outfile.createNewFile()) {
				System.out.println("New file created");
			}
			else {
				System.out.println("Overwriting existing file");
			}
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			return;
		}
		
		
		// Need this to read and write
		BufferedReader reader1, reader2, finalReader;
		PrintWriter writer;
	
		String readLine, filter; // readLine reads, filter filters through copied ints 
		double firstNum, secondNum; // comparison of lines 
		double lastNum = 0; // lastNum is the last number put into file 
		double average; // average 
		double total = 0; // total
		int count = 0; // divides by count for average 
			
		try {
			reader1 = new BufferedReader(new FileReader(infile)); // read
			reader2 = new BufferedReader(new FileReader(infile)); // checks for duplicates 
			writer = new PrintWriter(new FileOutputStream(outfile)); // overwrites
			
			filter = reader2.readLine(); // offsets the second reader for comparison 
			if (Integer.parseInt(filter) == 0) {
				lastNum = -1; // this is to avoid the cases of 0 as first entry 
			}

			// This while loop will compare all the numbers except for the last number
			while ((filter = reader2.readLine()) != null) {
				readLine = reader1.readLine();
				firstNum = Double.parseDouble(readLine);
				secondNum = Double.parseDouble(filter);
				if (firstNum > secondNum) {
					throw new NumberFormatException("Please check file to see if numbers are in order");
				}
				else if (lastNum == firstNum) {
					// just moves to the next lines
				}
				else if (firstNum < secondNum) {
					writer.println(firstNum);
					writer.flush();
					lastNum = firstNum;
					total += firstNum;
					count++;
				}
				else { // (firstNum == secondNum)
					// just moves to the next lines 
				}

			}
			// checks last number on file
			readLine = reader1.readLine();
			firstNum = Double.parseDouble(readLine);
			if (firstNum != lastNum) {
				writer.println(firstNum);
				writer.flush();
				total += firstNum; 
				count++; 
			}

			// finds the average
			average = (total / count);
			writer.println("The average: " + average);
			writer.flush();
			
			// close the readers' and writers' files
			reader1.close();
			reader2.close();
			writer.close();
		} // end of try block
		
		// catches two types of exceptions
		catch (IOException e) {
			System.out.println("Error in the IO process, please check your file names");
			return; 
		}
		catch (NumberFormatException n) {
			System.out.println(n.getMessage());
			return; 
		}


		return;
	}

}
