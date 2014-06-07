/*license*\
   Codelet

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.util.EnumUtil;
/**
   <P>The type of a single JavaDoc codelet instance.</P>

   @author  Copyright (C) 2014, Jeff Epstein, released under the LPGL 2.1. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum CodeletType  {
   /**
      <P>{@code {@.codelet}}: A taglet that displays the example code's source code.</P>

      <P>This sets<OL>
         <LI>{@link #getName() getName}{@code ()} to {@code ".codelet"}</LI>
         <LI>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getSourceConfig_"}</LI>
      </OL></P>

      <H3>{@code {@.codelet}}: Format</H3>

      <P><CODE>{&#64;.codelet <I>fully.qualified.ClassName</I>[:<A HREF="CustomizerUtil.html#overview">lineProcessorFunction</A>()]}</CODE></P>

      <P>The customizer portion is optional, but when provided, must be preceded by a {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR colon}. The customizer is available to all types except {@link #SOURCE_AND_OUT {@.codelet.and.out}}.</P>

      <P><B>Examples:</B></P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName}}</BLOCKQUOTE>

      <P>Prints out all lines in (assuming Windows)
      <BR> &nbsp; &nbsp; {@code fully\qualified\examples\ExampleClassName.java}
      <BR>Where {@code "fully"} is in the {@linkplain CodeletBaseConfig#EXAMPLE_CLASS_SRC_BASE_DIR example-code base directory} as configured.</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:{@link com.github.aliteralmind.codelet.BasicCustomizers#lineRange(CodeletInstance, CodeletType, int, boolean, String, int, boolean, String) lineRange}(1, false, "text in start line", 1, false, "text in end line")}}</BLOCKQUOTE>

      <P>Same as above, but only displays a <A HREF="{@docRoot}/overview-summary.html#xmpl_snippet">portion</A> of the lines, starting and ending with lines that contain specific text (inclusive).</P>

      @see  #CONSOLE_OUT
      @see  #SOURCE_AND_OUT
      @see  #FILE_TEXT
      @see  #isSourceCode()
      @see  com.github.aliteralmind.codelet.type.SourceCodeProcessor
      @see  com.github.aliteralmind.codelet.type.SourceCodeTemplate
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
    **/
   SOURCE_CODE(".codelet", "getSourceConfig_"),
            //The value of the second parameter ("getSourceConfig_") must be the
            //same for both SOURCE_CODE and SOURCE_AND_OUT
   /**
      <P>{@code {@.codelet.out}}: A taglet that displays the example code's console output (via <CODE>java.lang.{@link java.lang.System System}.{@link java.lang.System#out out}</CODE>).</P>

      <P>This sets<OL>
         <LI>{@link #getName() getName}{@code ()} to {@code ".codelet.out"}</LI>
         <LI>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getConsoleOutConfig_"}</LI>
      </OL></P>

      <H3>{@code {@.codelet.out}}: Format</H3>

      <P><CODE>{&#64;.codelet.out <I>fully.qualified.ClassName</I>[(&quot;Command line params&quot;, false, -1)][:<A HREF="CustomizerUtil.html#overview">lineProcessorFunction</A>()]}</CODE></P>

      <P><UL>
         <LI>The command-line parameters are optional. When not provided, an empty string array is passed to the example-code's <A HREF="http://docs.oracle.com/javase/tutorial/getStarted/application/index.html#MAIN">{@code main} function</A>. When provided, it must be formatted as specified by
         <BR> &nbsp; &nbsp; <CODE>com.github.xbn.util.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</CODE>
         <LI>The customizer portion is optional.</LI>
      </UL></P>

      <P><B>Examples:</B></P>

<BLOCKQUOTE>{@code {@.codelet.out fully.qualified.examples.ExampleClassName}}</BLOCKQUOTE>

      <P>ReplacedInEachInput the taglet with the entire console output.</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params")}}</BLOCKQUOTE>

      <P>Same as above, but passes a five-element string array to the main function.</P>

      @see  #SOURCE_CODE
      @see  #isConsoleOut()
      @see  com.github.aliteralmind.codelet.type.ConsoleOutProcessor
      @see  com.github.aliteralmind.codelet.type.ConsoleOutTemplate
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
    **/
   CONSOLE_OUT(".codelet.out", "getConsoleOutConfig_"),
   /**
      <P>{@code {@.codelet.and.out}}: A taglet that displays the example code's source code and console output.</P>

      <P>This sets<OL>
         <LI>{@link #getName() getName}{@code ()} to {@code ".codelet.and.out"}</LI>
         <LI>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getSourceConfig_"}</LI>
      </OL></P>

      <H3>{@code {@.codelet.and.out}}: Format</H3>

      <P><CODE>{&#64;.codelet.and.out <I>fully.qualified.ClassName</I>[(&quot;Command line params&quot;, false, -1)][:customizerFunction()]}</CODE></P>

      <P>See the format requirements for both {@link #SOURCE_CODE {@.codelet}} and {@link #CONSOLE_OUT {@.codelet.out}} for examples.</P>

<BLOCKQUOTE>{@code {@.codelet.and.out fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params"):customizerFunction()}}</BLOCKQUOTE></P>

      <P>is essentially equal to</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:customizerFunction()}{@.codelet.out fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params")}}</BLOCKQUOTE></P>

      <P>A customizer in a {@code {@.codelet.and.out}} taglet is only applied to the source code. To also customize the output, use</P>

   <P style="font-size: 125%;"><B><CODE>{&#64;.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo:customizerForSourceCode()}
   <BR>{&#64;.codelet.out com.github.aliteralmind.codelet.examples.adder.AdderDemo:customizerForOutput()}</CODE></B></P>

      @see  #SOURCE_CODE
      @see  #isSourceAndOut()
      @see  com.github.aliteralmind.codelet.type.SourceAndOutProcessor
      @see  com.github.aliteralmind.codelet.type.SourceAndOutTemplate
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_AND_OUT_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
    **/
   SOURCE_AND_OUT(".codelet.and.out", "getSourceConfig_"),
            //The value of the second parameter ("getSourceConfig_") must be the
            //same for both SOURCE_CODE and SOURCE_AND_OUT
   /**
      <P>{@code {@.file.textlet}}: A taglet that displays the contents of a plain text file.</P>

      <P>This sets<OL>
         <LI>{@link #getName() getName}{@code ()} to {@code ".file.textlet"}</LI>
         <LI>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getFileTextConfig_"}</LI>
      </OL></P>

      <H3>{@code {@.file.textlet}}: Format</H3>

      <P><CODE>{&#64;.file.textlet <I>path\to\file.txt</I>[:<A HREF="CustomizerUtil.html#overview">lineProcessorFunction</A>()]}</CODE></P>

      <P>Replaced with all lines in a plain-text file, such as for displaying an example code's input. The path may be absolute, relative to where the Java Virtual Machine is invoked, or, if a file-name only, in the {@code doc-files} directory off of the containing classes source file-directory (or is a file in the same directory in which the JVM was invoked).</P>

      <P>The customizer portion is optional.</P>

      @see  #SOURCE_CODE
      @see  #isFileText()
      @see  com.github.aliteralmind.codelet.type.FileTextProcessor
      @see  com.github.aliteralmind.codelet.type.FileTextTemplate
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_FILE_TEXT_TMPL_PATH CodeletBaseConfig#DEFAULT_FILE_TEXT_TMPL_PATH
    **/
   FILE_TEXT(".file.textlet", "getFileTextConfig_");

   private final String tagName;
   private final String defaultLineProcNamePrefix;
   /**
      <P>Construct an {@code CodeletType}.</P>

      @param  tag_name  The name of the codelet. <I>Should</I> not be {@code null} or empty. Get with {@link #getName() getName}{@code ()}
      @param  default_lineProcNamePrefix  The default prefix for Customizers of this type. <I>Should</I> not be {@code null} or empty, and should end with an underscore ({@code '_'}). Get with {@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()}.
      @see  #SOURCE_CODE
    **/
   CodeletType(String tag_name, String default_lineProcNamePrefix)  {
      tagName = tag_name;
      defaultLineProcNamePrefix = default_lineProcNamePrefix;
   }
   /**
      <P>The {@linkplain com.sun.javadoc.Tag#name() name} of this taglet, as used in JavaDoc.</P>

      @return  For example, if the taglet is
      <BR> &nbsp; &nbsp; {@code {@.codelet.out my.package.examples.AnExample}}
      <BR>this returns {@code ".codelet.out"}</P>
      @see  #CodeletType(String, String) CodeletType(s,s)
      @see  #newTypeForTagletName(String, String) newTypeForTagletName(s,s)
    **/
   public String getName()  {
      return  tagName;
   }
   /**
      <P>The default prefix for Customizers of this type.</P>

      <P>This is intended to be followed by either the example classes {@linkplain java.lang.Class#getSimpleName() simple name}, or the <I>explicitely-provided</I> function-name-postfix for the plain-text file being displayed.</P>
      @see  #CodeletType(String, String)
    **/
   public String getDefaultLineProcNamePrefix()  {
      return  defaultLineProcNamePrefix;
   }
   /**
      <P>Is this {@code CodeletType} equal to {@code SOURCE_CODE}?.</P>

      @return  <CODE>this == {@link #SOURCE_CODE}</CODE>

      @see  #isConsoleOut()
      @see  #isFileText()
      @see  #isSourceAndOut()
    **/
   public final boolean isSourceCode()  {
      return  this == SOURCE_CODE;
   }
   /**
      <P>Is this {@code CodeletType} equal to {@code CONSOLE_OUT}?.</P>

      @return  <CODE>this == {@link #CONSOLE_OUT}</CODE>
      @see  #isSourceCode()
    **/
   public final boolean isConsoleOut()  {
      return  this == CONSOLE_OUT;
   }
   /**
      <P>Is this {@code CodeletType} equal to {@code FILE_TEXT}?.</P>

      @return  <CODE>this == {@link #FILE_TEXT}</CODE>
      @see  #isSourceCode()
    **/
   public final boolean isFileText()  {
      return  this == FILE_TEXT;
   }
   /**
      <P>Is this {@code CodeletType} equal to {@code SOURCE_AND_OUT}?.</P>

      @return  <CODE>this == {@link #SOURCE_AND_OUT}</CODE>
      @see  #isSourceCode()
    **/
   public final boolean isSourceAndOut()  {
      return  this == SOURCE_AND_OUT;
   }

   /**
      <P>If an <CODE>CodeletType</CODE> is not a required value, crash.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.util.EnumUtil EnumUtil}.{@link com.github.xbn.util.EnumUtil#crashIfNotRequiredValue(Enum, Enum, String, Object) crashIfNotRequiredValue}(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo)</CODE></P>
      @see  #crashIfForbiddenValue(CodeletType, String, Object) crashIfForbiddenValue(ert,s,o)
    **/
   public void crashIfNotRequiredValue(CodeletType e_rqd, String s_thisEnumsVarNm, Object o_xtraInfo)  {
      EnumUtil.crashIfNotRequiredValue(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo);
   }
   /**
      <P>If an <CODE>CodeletType</CODE> is a forbidden value, crash.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.util.EnumUtil EnumUtil}.{@link com.github.xbn.util.EnumUtil#crashIfForbiddenValue(Enum, Enum, String, Object) crashIfForbiddenValue}(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo)</CODE></P>
      @see  #crashIfNotRequiredValue(CodeletType, String, Object) crashIfNotRequiredValue(ert,s,o)
    **/
   public void crashIfForbiddenValue(CodeletType e_rqd, String s_thisEnumsVarNm, Object o_xtraInfo)  {
      EnumUtil.crashIfForbiddenValue(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo);
   }
   /**
      <P>Is a string equal to <I>this</I> taglet type's name?.</P>

      @param  name  May not be {@code null}.
      @return  <CODE>name.equals({@link #getName() getName}())</CODE>
    **/
   public boolean isNameEqualTo(String name)  {
      return  isNameEqualTo(name, "name");
   }
      private boolean isNameEqualTo(String name, String name_varName)  {
         try  {
            return  name.equals(getName());
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(name, name_varName, null, rx);
         }
      }
   /**
      <P>Get a new {@code CodeletType} whose name is equal to a string value.</P>

      @param  name  The name, which must be non-{@code null}, and equal to a type's {@link #getName() getName}{@code ()}.
      @param  name_varName  Descriptive name of the {@code name} parameter, for the potential error message. <I>Should</I> not be {@code null} or empty.
      @exception  IllegalArgumentException  If {@code name} is not equal to a known codelet type.
    **/
   public static final CodeletType newTypeForTagletName(String name, String name_varName)  {
      if(CodeletType.SOURCE_CODE.isNameEqualTo(name, name_varName))  {
         return  CodeletType.SOURCE_CODE;
      }
      if(CodeletType.CONSOLE_OUT.isNameEqualTo(name, name_varName))  {
         return  CodeletType.CONSOLE_OUT;
      }
      if(CodeletType.FILE_TEXT.isNameEqualTo(name, name_varName))  {
         return  CodeletType.FILE_TEXT;
      }
      if(CodeletType.SOURCE_AND_OUT.isNameEqualTo(name, name_varName))  {
         return  CodeletType.SOURCE_AND_OUT;
      }

      throw  new IllegalArgumentException(name_varName + " (\"" + name + "\") is not CodeletType.SOURCE_CODE.getName() (\"" + CodeletType.SOURCE_CODE.getName() + "\"), CodeletType.CONSOLE_OUT.getName() (\"" + CodeletType.CONSOLE_OUT.getName() + "\"), CodeletType.FILE_TEXT.getName() (\"" + CodeletType.FILE_TEXT.getName() + "\"), or CodeletType.SOURCE_AND_OUT.getName() (\"" + CodeletType.SOURCE_AND_OUT.getName() + "\")");
   }
};
