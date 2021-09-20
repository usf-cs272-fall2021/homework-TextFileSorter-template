import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * A simple class for sorting text files.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2021
 */
public class TextFileSorter {
	/**
	 * A simple comparable class for storing and comparing text files. Should
	 * implement the {@link Comparable} interface to allow for comparison between
	 * {@link TextFile} objects.
	 *
	 * @see Comparable
	 */
	public static class TextFile { // TODO Update declaration to implement interface
		/** The normalized text file source. */
		private final Path source;

		/** The text file name. */
		private final String name;

		/** The text file size. */
		private final long size;

		/**
		 * Initializes a text file.
		 *
		 * @param source the text file source
		 */
		public TextFile(Path source) {
			this.source = source.normalize();
			this.name = source.getFileName().toString();
			this.size = size(source);
		}

		@Override
		public String toString() {
			String output = this.source.getNameCount() < 1 ? this.name : source.toString();
			return String.format("%s (%s)", output, size);
		}

		// TODO Add members and methods as needed.
	}

	/*
	 * TODO For the following, define the comparators based on the approach stated
	 * in the Javadoc comments (using an anonymous inner class, inner class, or
	 * static nested class). Add members, classes, and methods as needed.
	 */

	/**
	 * A comparator that compares text files by their size with the largest sizes
	 * first (descending order), defined using an anonymous inner class.
	 *
	 * @see TextFile#size
	 * @see Long#compare(long, long)
	 */
	public static final Comparator<TextFile> SIZE_COMPARATOR = null; // TODO Define SIZE_COMPARATOR

	/**
	 * A comparator that compares text files in case insensitive order by their
	 * name, defined using a non-static inner class.
	 *
	 * @see TextFile#name
	 * @see String#CASE_INSENSITIVE_ORDER
	 */
	public static final Comparator<TextFile> NAME_COMPARATOR = null; // TODO Define NAME_COMPARATOR

	/**
	 * A comparator that compares text files by the {@link #SIZE_COMPARATOR} if the
	 * sizes are not equal. If the sizes are equal, then compares using a
	 * {@link #NAME_COMPARATOR} instead. If the names are equal, then compares by
	 * the {@link TextFile} natural sort order (by its {@link Path} source).
	 *
	 * @see #SIZE_COMPARATOR
	 * @see #NAME_COMPARATOR
	 * @see TextFile
	 */
	public static final Comparator<TextFile> NESTED_COMPARATOR = null; // TODO Define NESTED_COMPARATOR

	/**
	 * Returns the text file size.
	 *
	 * @param file the text file to retrieve size for
	 * @return the text file size or -1 if not a readable text file
	 */
	public static long size(Path file) {
		try {
			return Files.isReadable(file) ? Files.size(file) : -1;
		}
		catch (IOException e) {
			return -1;
		}
	}
}
