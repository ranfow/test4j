package forfilter.samples;

import static org.test4j.junit.filter.SuiteType.SUITE_TEST_CLASSES;

import org.junit.runner.RunWith;
import org.test4j.junit.annotations.TestPath;
import org.test4j.junit.suitetest.suite.ClassPathSuite;

/**
 * Run all test suites in this package except itself (to prevent JUnit's
 * recursion exception)
 */

@RunWith(ClassPathSuite.class)
@TestPath(patterns = { "samples.*", "!samples\\.ANestingCpSuite" }, value = { SUITE_TEST_CLASSES })
public class ANestingCpSuite {

}
