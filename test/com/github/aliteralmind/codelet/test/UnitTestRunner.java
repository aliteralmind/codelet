package  com.github.aliteralmind.codelet.test;
	import  org.junit.runner.JUnitCore;
	import  org.junit.runner.Result;
	import  org.junit.runner.notification.Failure;
/**
	<p>Run all unit tests in the project.</p>

	<p>{@code java com.github.aliteralmind.codelet.test.UnitTestRunner}</p>
 **/
public class UnitTestRunner  {
    public static void main(String[] as_cmdLineArgs) {

		Class[] acALL = new Class[] {
/*
			com.github.xbn.unittests.ExampleCodeOutputsContain_Unit.class,
			com.github.xbn.unittests.number.IntInRange_Unit.class,
			com.github.xbn.unittests.text.padchop.VzblPadChop_Unit.class,
			com.github.aliteralmind.codelet.test.simplesig.SimpleMethodSignature_Unit.class,
			com.github.aliteralmind.codelet.test.util.FQClassNameWithBaseDir_Unit.class
 */
			com.github.aliteralmind.codelet.test.simplesig.SimpleParamSigSearchTerm_Unit.class,
			com.github.aliteralmind.codelet.test.simplesig.SimpleMethodSignature_Unit.class,
			com.github.aliteralmind.codelet.test.util.FQClassNameWithBaseDir_Unit.class,
			com.github.aliteralmind.codelet.test.util.FilenameBlackWhiteList_Unit.class
/*
 */
		};

		Result r = JUnitCore.runClasses(                 //Comma-separated list of classes
			acALL
		);

		System.out.println("Test results:");
		System.out.println("  Successful:  " + (r.getRunCount() - r.getFailureCount()));
		System.out.println("  Failures:    " + r.getFailureCount());
		System.out.println("               ----");
		System.out.println("  Total:       " + r.getRunCount() + "\n");

		if(r.getFailureCount() > 0)  {
			System.out.println("Failure descriptions:");
			for(Failure f : r.getFailures())  {
				System.out.println("  " + f.toString());
			}
		}
    }
}

