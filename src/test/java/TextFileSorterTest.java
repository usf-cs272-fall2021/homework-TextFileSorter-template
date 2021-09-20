import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests for the {@link TextFileSorter} class.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2021
 */
@TestMethodOrder(MethodName.class)
public class TextFileSorterTest {
	/** Location of test files. */
	public static final Path BASE = Path.of("src", "test", "resources");

	/**
	 * Tests a specific comparator.
	 *
	 * @see TextFileSorter.TextFile
	 */
	@Nested
	public class A_PathTests extends ComparatorTests {
		@Override
		@BeforeEach
		public void setup() {
			// can't use Comparator.naturalOrder() here because not guaranteed interface implemented correctly
			Comparator<TextFileSorter.TextFile> temp = new Comparator<TextFileSorter.TextFile>() {
				@Override
				public int compare(TextFileSorter.TextFile first, TextFileSorter.TextFile second) {
					try {
						// See a warning? Then your TextFile does not implement the interface correctly!
						Comparable<TextFileSorter.TextFile> casted1 = (Comparable<TextFileSorter.TextFile>) first;
						return casted1.compareTo(second);
					}
					catch (ClassCastException e) {
						// fail test because unable to cast
						fail("\nTextFile does not implement the necessary interface.");
					}

					return 0;
				}
			};

			test = temp;
			small = Path.of("a", "z.txt");
			large = Path.of("z", "a.txt");
			equal = Path.of(".", "z", "a.txt");
		}
	}

	/**
	 * Tests a specific comparator.
	 *
	 * @see TextFileSorter#SIZE_COMPARATOR
	 */
	@Nested
	public class B_SizeTests extends ComparatorTests {
		@Override
		@BeforeEach
		public void setup() {
			test = TextFileSorter.SIZE_COMPARATOR; 	// descending sort order
			small = BASE.resolve("a_large_size.txt");
			large = BASE.resolve("z_small_size.txt");
			equal = BASE.resolve("a_equal_size.txt");
		}
	}

	/**
	 * Tests a specific comparator.
	 *
	 * @see TextFileSorter#NAME_COMPARATOR
	 */
	@Nested
	public class C_NameTests extends ComparatorTests {
		@Override
		@BeforeEach
		public void setup() {
			test = TextFileSorter.NAME_COMPARATOR;
			small = Path.of("z", "a.txt");
			large = Path.of("a", "z.txt");
			equal = Path.of("Z.TXT");
		}
	}

	/**
	 * Tests a specific comparator.
	 *
	 * @see TextFileSorter#NESTED_COMPARATOR
	 */
	@Nested
	public class D_NestedTests extends ComparatorTests {
		@Override
		@BeforeEach
		public void setup() {
			test = TextFileSorter.NESTED_COMPARATOR;

			// handles case where sizes are different
			small = BASE.resolve("a_large_size.txt");
			large = BASE.resolve("z_small_size.txt");

			// handles case where size, name, and path are same
			equal = BASE.resolve(".").resolve("z_small_size.txt");
		}

		/**
		 * Tests that this comparator was implemented properly.
		 */
		@Test
		@Order(4)
		public void testSameSizeUniqueName() {
			// paths don't exist, so sizes are all -1
			small = Path.of("z", "a.txt");
			large = Path.of("a", "z.txt");

			TextFileSorter.TextFile one = new TextFileSorter.TextFile(large);
			TextFileSorter.TextFile two = new TextFileSorter.TextFile(small);
			String debug = String.format("%s > %s", one, two);
			assertTrue(test.compare(one, two) > 0, debug);
		}

		/**
		 * Tests that this comparator was implemented properly.
		 */
		@Test
		@Order(5)
		public void testSameSizeSameName() {
			// paths don't exist, so sizes are all -1
			small = Path.of("a", "a.txt");
			large = Path.of("z", "a.txt");

			TextFileSorter.TextFile one = new TextFileSorter.TextFile(large);
			TextFileSorter.TextFile two = new TextFileSorter.TextFile(small);
			String debug = String.format("%s > %s", one, two);
			assertTrue(test.compare(one, two) > 0, debug);
		}
	}

	/**
	 * Tests approaches used.
	 *
	 * @see TextFileSorter
	 */
	@Nested
	public class E_ApproachTests {
		/**
		 * Tests approach.
		 */
		@Test
		@Order(1)
		public void testTextFileInterface() {
			Type[] types = TextFileSorter.TextFile.class.getGenericInterfaces();

			if (types.length == 1) {
				String name = types[0].getTypeName();

				Supplier<String> error1 = () -> String.format("%nThe Comparable interface not properly implemented. %nFound: %s%n", name);
				Supplier<String> error2 = () -> String.format("%nThe interface generic type not properly specified. %nFound: %s%n", name);

				assertAll(
						() -> assertTrue(name.contains("Comparable"), error1),
						() -> assertTrue(name.contains("TextFile"), error2)
				);
			}
			else {
				fail("\nTextFile does not implement the necessary interface.");
			}
		}

		/**
		 * Tests approach.
		 */
		@Test
		@Order(2)
		public void testTextFileType() {
			Class<?> test = TextFileSorter.TextFile.class;
			boolean expectAnonymous = false;
			boolean expectMember = true;
			boolean expectStatic = true;
			testClassDefinition(test, expectAnonymous, expectMember, expectStatic);
		}

		/**
		 * Tests approach.
		 */
		@Test
		@Order(3)
		public void testSizeComparatorType() {
			Class<?> test = TextFileSorter.SIZE_COMPARATOR.getClass();
			boolean expectAnonymous = true;
			boolean expectMember = false;
			boolean expectStatic = false;
			testClassDefinition(test, expectAnonymous, expectMember, expectStatic);
		}

		/**
		 * Tests approach.
		 */
		@Test
		@Order(4)
		public void testNameComparatorType() {
			Class<?> test = TextFileSorter.NAME_COMPARATOR.getClass();
			boolean expectAnonymous = false;
			boolean expectMember = true;
			boolean expectStatic = false;
			testClassDefinition(test, expectAnonymous, expectMember, expectStatic);
		}

		/**
		 * Tests approach.
		 */
		@Test
		@Order(5)
		public void testNestedComparatorType() {
			Class<?> test = TextFileSorter.NESTED_COMPARATOR.getClass();
			boolean expectAnonymous = false;
			boolean expectMember = true;
			boolean expectStatic = true;
			testClassDefinition(test, expectAnonymous, expectMember, expectStatic);
		}

		/**
		 * Tests if the class is defined as expected.
		 *
		 * @param test the class to test
		 * @param expectAnonymous whether the class should be anonymous
		 * @param expectMember whether the class should be an inner/member class
		 * @param expectStatic whether the class should be a static class
		 */
		public void testClassDefinition(Class<?> test, boolean expectAnonymous,
				boolean expectMember, boolean expectStatic) {
			boolean actualAnonymous = test.isAnonymousClass();
			boolean actualMember = test.isMemberClass();
			boolean actualStatic = Modifier.isStatic(test.getModifiers());

			assertAll(
					() -> assertEquals(expectAnonymous, actualAnonymous, "is anonymous?"),
					() -> assertEquals(expectMember, actualMember, "is inner class?"),
					() -> assertEquals(expectStatic, actualStatic, "is static class?")
			);
		}
	}

	/**
	 * Tests a specific comparator.
	 */
	@TestMethodOrder(OrderAnnotation.class)
	public abstract class ComparatorTests {
		/** The comparator being tested. */
		public Comparator<TextFileSorter.TextFile> test;

		/** A Path expected to be smaller than the small Path object. */
		public Path small;

		/** A Path expected to be larger than the small Path object. */
		public Path large;

		/** A Path expected to be equal to the large Path object. */
		public Path equal;

		/** Sets the comparator being tested. */
		@BeforeEach
		public abstract void setup();

		/**
		 * Tests that this comparator was implemented properly.
		 */
		@Test
		@Order(1)
		public void testPositive() {
			TextFileSorter.TextFile one = new TextFileSorter.TextFile(large);
			TextFileSorter.TextFile two = new TextFileSorter.TextFile(small);
			String debug = String.format("%s > %s", one, two);
			assertTrue(test.compare(one, two) > 0, debug);
		}

		/**
		 * Tests that this comparator was implemented properly.
		 */
		@Test
		@Order(2)
		public void testNegative() {
			TextFileSorter.TextFile one = new TextFileSorter.TextFile(small);
			TextFileSorter.TextFile two = new TextFileSorter.TextFile(large);
			String debug = String.format("%s < %s", one, two);
			assertTrue(test.compare(one, two) < 0, debug);
		}

		/**
		 * Tests that this comparator was implemented properly.
		 */
		@Test
		@Order(3)
		public void testEqual() {
			TextFileSorter.TextFile one = new TextFileSorter.TextFile(large);
			TextFileSorter.TextFile two = new TextFileSorter.TextFile(equal);
			String debug = String.format("%s == %s", one, two);
			assertTrue(test.compare(one, two) == 0, debug);
		}
	}
}
