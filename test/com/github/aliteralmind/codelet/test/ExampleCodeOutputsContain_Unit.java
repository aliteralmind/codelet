package  com.github.aliteralmind.codelet.test;
   import  com.github.xbn.testdev.UnitTestUtils;
   import  org.junit.Test;
/**
   <P>{@code java com.github.xbn.unittests.ExampleCodeOutputsContain_Unit}</P>
 **/
public class ExampleCodeOutputsContain_Unit  {
   public static final void main(String[] ignored)  {
      ExampleCodeOutputsContain_Unit unit = new ExampleCodeOutputsContain_Unit();
   	test.simplesig_ConstructorParamSearchTermXmpl();
   	test.simplesig_MethodSigSearchTermXmpl();
   	test.simplesig_SimpleMethodSigNoDefaults();
   	test.simplesig_SimpleMethodSigWithClassDefaults();
   	test.linefilter_ExpirableAlterListXmpl();
   	test.linefilter_StringReplacerXmpl();
   	test.linefilter_StringValidatorReplacerXmpl();
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
         com.github.aliteralmind.codelet.util.BlackWhiteListForJavaClasses.class,
         "true", "false", "true", "false", "true",
         "false", "true", "false", "true", "false");
   }
   @Test
   public void simplesig_ConstructorParamSearchTermXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.lang.reflect.simplesig.ConstructorParamSearchTermXmpl.class,
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
         com.github.xbn.examples.lang.reflect.simplesig.MethodSigSearchTermXmpl.class,
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
         com.github.xbn.examples.util.FunctionConstructorJavaDocLink.class,
         "DocLink.html#FunctionConstructorJavaDocLink()",
         "DocLink.html#FunctionConstructorJavaDocLink(java.lang.String, int[], int...)",
         "DocLink.html#doSomething(int)",
         "DocLink.html#doSomething(java.lang.String, int[])");
   }
   @Test
   public void util_JavaDocUtilGetEllipsisArrayLastParamXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.util.JavaDocUtilGetEllipsisArrayLastParamXmpl.class,
         "int: java.lang.Illegal", "int[]: int...",
         "int[][]: int[]...", "String[]: java.lang.String...",
         "String[][]: java.lang.String[]...");
   }
   @Test
   public void linefilter_StringValidatorReplacerXmpl()  {
      String sT = "Kermit T. Frog";
      String sThe = "Kermit The Frog";
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, "()=",
         com.github.xbn.examples.linefilter.StringValidatorReplacerXmpl.class,
         sT, sThe, sThe, sThe, sT, sT, sT, sT, sT, sT);
   }
   @Test
   public void linefilter_StringReplacerXmpl()  {
      String sT = "Kermit T. Frog";
      String sThe = "Kermit The Frog";
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, "()=",
         com.github.xbn.examples.linefilter.StringReplacerXmpl.class,
         sT, sThe, sThe, sThe, sT, sT, sT, sT, sT, sT);
   }
   @Test
   public void linefilter_ExpirableAlterListXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.text.line.ExpirableTextLineAlterListXmpl.class,
         "1st and 2nd \"Apple\"", "2nd-through-4th \"T.\"", "2nd \"Green\"",
         "1 Johnny Pearseed  (Alters=3)", "3 Kermit The Frog  (Alters=3)",
         "6 Kermit The Frog  (Alters=2)", "9 Johnny Appleseed  (Alters=0, expired)",
         "13 The Green Giant  (Alters=0, expired)");
   }
/*  REQUIRES PARAMETERS
   @Test
   public void YYY_FindJavaDocBlocksAndSLCmtsWSubModesXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.text.line.FindJavaDocBlocksAndSLCmtsWSubModesXmpl.class,
         YYY);
   }
   @Test
   public void YYY_FindJavaDocBlocksAndSLCmtsXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.text.line.FindJavaDocBlocksAndSLCmtsXmpl.class,
         YYY);
   }
   @Test
   public void YYY_OutputSourceHighlightSubModeXmpl()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.xbn.examples.text.line.OutputSourceHighlightSubModeXmpl.class,
         YYY);
   }
 */
}
