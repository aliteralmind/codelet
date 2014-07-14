package  com.github.aliteralmind.codelet.test;
   import  com.github.xbn.testdev.DisplayOutputToConsole;
   import  com.github.xbn.testdev.VerifyApplicationOutput;

import  org.junit.Test;
/**
   <P>{@code java com.github.xbn.unittests.ExampleCodeOutputsContain_Unit}</P>
 **/
public class ExampleCodeOutputsContain_Unit  {
   public static final void main(String[] ignored)  {
      ExampleCodeOutputsContain_Unit test = new ExampleCodeOutputsContain_Unit();
   	test.simplesig_ConstructorParamSearchTermXmpl();
   	test.simplesig_MethodSigSearchTermXmpl();
   	test.simplesig_SimpleMethodSigNoDefaults();
   	test.simplesig_SimpleMethodSigWithClassDefaults();
   	test.util_FunctionConstructorJavaDocLink();
   	test.util_JavaDocUtilGetEllipsisArrayLastParamXmpl();
   	test.io_BlackWhiteListForJavaClasses();
//   	test.YYY_FindJavaDocBlocksAndSLCmtsWSubModesXmpl();
//   	test.YYY_FindJavaDocBlocksAndSLCmtsXmpl();
//   	test.YYY_OutputSourceHighlightSubModeXmpl();
   }
   @Test
   public void io_BlackWhiteListForJavaClasses()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.codelet.examples.util.BlackWhiteListForJavaClasses.class,
         "true", "false", "true", "false", "true",
         "false", "true", "false", "true", "false");
   }
   @Test
   public void simplesig_ConstructorParamSearchTermXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.codelet.examples.simplesig.ConstructorParamSearchTermXmpl.class,
         "for: \"()\"", "Not found",
         "for: \"(*)\"", "First match: (boolean)", "All", "(boolean)", "(int)",
         "for: \"(boolean)\"", "First match: (boolean)", "All", "(boolean)");
   }
   @Test
   public void simplesig_SimpleMethodSigNoDefaults()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO,
         new String[]{" - "},
         com.github.aliteralmind.codelet.examples.simplesig.SimpleMethodSigNoDefaults.class,
         "b=false, i=3");
   }
   @Test
   public void simplesig_SimpleMethodSigWithClassDefaults()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO,
         new String[]{" - "},
         com.github.aliteralmind.codelet.examples.simplesig.SimpleMethodSigWithClassDefaults.class,
         "b=false, i=3");
   }
   @Test
   public void simplesig_MethodSigSearchTermXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.codelet.examples.simplesig.MethodSigSearchTermXmpl.class,
         "for: \"nonExisting()\"", "Not found",
         "for: \"doSomething()\"", "Not",
         "for: \"doSomething(*)\"", "Not",
            "First match: doSomething(String, boolean, int, Object)",
            "All", "(String, boolean, int, Object)", "(String, boolean, int[], byte, Object)",
         "for: \"doSomething(*,byte,*)\"",
            "First match: doSomething(String, boolean, int[], byte, Object)",
            "All", "(String, boolean, int[], byte, Object)");
   }
   @Test
   public void util_FunctionConstructorJavaDocLink()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.codelet.examples.util.FunctionConstructorJavaDocLink.class,
         "DocLink.html#FunctionConstructorJavaDocLink()",
         "DocLink.html#FunctionConstructorJavaDocLink(java.lang.String, int[], int...)",
         "DocLink.html#doSomething(int)",
         "DocLink.html#doSomething(java.lang.String, int[])");
   }
   @Test
   public void util_JavaDocUtilGetEllipsisArrayLastParamXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.codelet.examples.util.JavaDocUtilGetEllipsisArrayLastParamXmpl.class,
         "int: java.lang.Illegal", "int[]: int...",
         "int[][]: int[]...", "String[]: java.lang.String...",
         "String[][]: java.lang.String[]...");
   }
}
