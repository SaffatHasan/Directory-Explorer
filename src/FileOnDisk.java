import java.io.File;
import java.io.IOException;

/**
 * This class provides a digital representation for a single File or Directory.
 * The toString() method for this class outputs a formatted
 * " -Space used- -absolute path-" string. By implementing Comparable and
 * defining a CompareTo method, we are able to utilize Collections.Sort() on a
 * list of FileOnDisk objects to create a sorted list based on the size of each
 * file.
 * 
 * 
 */
public class FileOnDisk implements Comparable {
	private File file;
	private long size;

	public FileOnDisk(File F) throws IOException {
		file = F;
		size = F.length();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	// The compareTo method compares another FileOnDisk to the current
	// FileOnDisk. The method will output 1 when the current file has a larger
	// file size than the other, 0 when they are equal, and -1 if the current
	// file is smaller.
	public int compareTo(Object o) {
		if (this.size == ((FileOnDisk) o).getSize())
			return 0;
		else if (this.size > ((FileOnDisk) o).getSize())
			return 1;
		else
			return -1;

	}

	public String toString() {
		if (size < 1024) // print bytes
			return String.format("%7.2f  B\t %s", (float) size,
					file.getAbsolutePath());
		else if (size / 1024 < 1024)// print kilobytes
			return String.format("%7.2f KB\t %s", (float) size / 1024.0,
					file.getAbsolutePath());
		else if (size / 1024 / 1024 < 1024)// print megabytes
			return String.format("%7.2f MB\t %s", (float) size
					/ (1024.0 * 1024), file.getAbsolutePath());
		else
			// print gigabytes
			return String.format("%7.2f GB\t %s", (float) size
					/ (1024.0 * 1024 * 1024), file.getAbsolutePath());
	}

}
