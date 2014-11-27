package  com.github.aliteralmind.codelet.test.util;
	import  java.util.Iterator;
	import  java.nio.file.NoSuchFileException;
	import  com.github.aliteralmind.codelet.util.FQClassNameWithBaseDir;
	import  java.nio.file.AccessDeniedException;
   import  org.junit.Test;
   import  static org.junit.Assert.*;
	import  com.github.xbn.io.Existence;
	import  com.github.xbn.io.Readable;
	import  com.github.xbn.io.Writable;

/**

java com.github.aliteralmind.codelet.test.util.FQClassNameWithBaseDir_Unit

 **/
public class FQClassNameWithBaseDir_Unit  {
   public static final void main(String[] ignored)  {
      FQClassNameWithBaseDir_Unit unit = new FQClassNameWithBaseDir_Unit();
      unit.test_success();
   }
   @Test
   public void test_success()  {
		FQClassNameWithBaseDir javaClsPath = new FQClassNameWithBaseDir("test\\", "com.github.aliteralmind.codelet.test.util.FQClassNameWithBaseDir_Unit", ".java");
	      assertTrue(true);
		try  {
	      javaClsPath.getThisOrCrashIfBad(Existence.OPTIONAL, Readable.OPTIONAL, Writable.REQUIRED);
		}  catch(NoSuchFileException | AccessDeniedException x)  {
			System.out.println(x);
			assertFalse(true);
		}

		Iterator<String> lineItr = javaClsPath.newLineIterator();
		assertEquals("package  com.github.aliteralmind.codelet.test.util;", lineItr.next());
   }
}
