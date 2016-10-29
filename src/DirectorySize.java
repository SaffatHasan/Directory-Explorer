import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This program provides a tool that given a name of a directory, explores all
 * its sub-directories and files and does two things: 
 * 		[1] Compute total size of all files and sub-directories
 * 		[2] Prints N largest files (with their sizes and absolute paths).
 * 
 */
public class DirectorySize {

	/** list of files found in the directory structure */
	static List<FileOnDisk> listOfFiles;
	/**
	 * list of visited directories (kept to avoid circular links and infinite
	 * recursion)
	 */
	static List<String> listOfVisitedDirs;

	/**
	 * This method expects one or two arguments.
	 * 
	 * @param args
	 *            Array of arguments passed to the program. The first one is the
	 *            name of the directory to be explored. The second (optional) is
	 *            the max number of largest files to be printed to the screen.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// First check to confirm 1 or 2 arguments input
		if (args.length != 1 && args.length != 2) {
			System.err.println("Invalid number of inputs, exiting.");
			System.exit(0);
		}
		// Secondary check to confirm that the path input is valid
		String directory = args[0];
		File dir = new File(directory);
		if (!dir.isDirectory()) {
			System.err.println("Invalid directory input, exiting.");
			System.exit(1);
		}

		// create an empty list of files
		listOfFiles = new LinkedList<FileOnDisk>();
		// create an empty list of directories
		listOfVisitedDirs = new ArrayList<String>();

		// Display the total size of the directory/file
		long size = getSize(dir);
		if (size < 1024) // print bytes
			System.out.printf("Total space used: %7.2f  B\n", (float) size);
		else if (size / 1024 < 1024)// print kilobytes
			System.out.printf("Total space used: %7.2f KB\n",
					(float) size / 1024.0);
		else if (size / 1024 / 1024 < 1024)// print megabytes
			System.out.printf("Total space used: %7.2f MB\n", (float) size
					/ (1024.0 * 1024));
		else
			// print gigabytes
			System.out.printf("Total space used: %7.2f GB\n", (float) size
					/ (1024.0 * 1024 * 1024));

		// Display the largest files in the directory
		int numOfFiles = 20; // default value
		try {
			if (args.length == 2) {
				numOfFiles = Integer.parseInt(args[1]);
			}
		} catch (NumberFormatException ex) {
			System.err.printf("ERROR: Invalid number of files provided."
					+ "The second argument should be an integer. \n");
			System.exit(1);
		}
		System.out.printf("Largest %d files: \n", numOfFiles);

		// Sorts the list of files found in the directory by size. This is by
		// using the Comparable interface which implements the CompareTo method
		// within FileOnDisk class.
		Collections.sort(listOfFiles);

		// Prints the largest to the nth largest file contained in the
		// directory
		for (int i = 0; i < numOfFiles; i++) {
			int g = listOfFiles.size() - i - 1;
			// a check to make sure that the nth largest file is never an
			// out-of-bounds array value
			if (g < 0)
				break;
			System.out.println(listOfFiles.get(listOfFiles.size() - i - 1));
		}

	}

	/**
	 * Recursively determines the size of a directory or file represented by the
	 * abstract parameter object file. This method populates the listOfFiles
	 * with all the files contained in the explored directory. This method
	 * populates the listOfVisitedDirs with canonical path names of all the
	 * visited directories.
	 * 
	 * @param file
	 *            directory/file name whose size should be determined
	 * @return number of bytes used for storage on disk
	 * @throws IOException
	 */
	public static long getSize(File file) throws IOException {
		long size = 0; // Store the total size of all files
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (!listOfVisitedDirs.contains(f.getCanonicalFile()))
					size += getSize(f);
			}
		} else {
			size += file.length();
			listOfFiles.add(new FileOnDisk(file));
		}

		return size;
	}

}