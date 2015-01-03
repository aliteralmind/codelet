/*license*\
   Codelet: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

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
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <p>Extract and transform elements from the taglet text.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class TagletTextUtil  {
//	private static final CodeletBaseConfig CONFIG = CodeletBaseConfig.INSTANCE;
   /**
      <p>Get the example code's package name.</p>

    * @return  <code>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}({@link #getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance)).{@link java.lang.Class#getPackage() getPackage}().{@link java.lang.Package#getName() getName}()
      </code>
    */
   public static final String getExamplePackageName(CodeletInstance instance) throws CodeletFormatException  {
      return  ReflectRtxUtil.getClassForName(getExampleClassFQName(instance), "getExampleClassFQName(instance)").
         getPackage().getName();
   }
   /**
      <p>Get the example code's non-fully-qualified class name.</p>

    * @return  <code>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}({@link #getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance)).{@link java.lang.Class#getSimpleName() getSimpleName}()</code>
    */
   public static final String getExampleSimpleClassName(CodeletInstance instance) throws CodeletFormatException  {
      return  ReflectRtxUtil.getClassForName(getExampleClassFQName(instance), "getExampleClassFQName(instance)").
         getSimpleName();
   }
   /**
      <p>The example code's fully-qualified class name.</p>

    * @return  If this is a<ol>
         <li>{@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} or {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglet, and command-line parameters<ol>
            <li>are specified: The {@linkplain #getExampleClassOrFilePortionFromTagletText(CodeletInstance) class-portion} of the {@linkplain CodeletInstance#getText() taglet text} before the open parenthesis ({@code '('}). Example:<ol>
               <li>Class-portion: {@code fully.qualified.examples.AnExample("param1", true, 3)}</li>
               <li>Output: {@code fully.qualified.examples.AnExample}</li>
            </ol></li>
            <li>are not specified: The entire class-portion.</li>
         </ol></li>
         <li>{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE {@.codelet}} or {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.codelet.out}} taglet: The entire class-portion</li>
      </ol>
    * @see  #getExampleCommandLineParams(CodeletInstance)
    */
   public static final String getExampleClassFQName(CodeletInstance instance) throws CodeletFormatException  {
      String clsTxt = getExampleClassOrFilePortionFromTagletText(instance);
      int openParenIdx = clsTxt.indexOf("(");
      return  ((openParenIdx != -1)
         ?  clsTxt.substring(0, openParenIdx)
         :  clsTxt);
   }
   /**
      <p>The example code's command-line parameters, as used by {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} and {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets.</p>

    * @return  If this is a {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} or {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglet, and command-line parameters<ol>
         <li>are specified: The {@linkplain #getExampleClassOrFilePortionFromTagletText(CodeletInstance) class-portion} of the {@linkplain CodeletInstance#getText() taglet text} inside--but not including--the parentheses ({@code '('} and {@code ')'}). Example:<ol>
               <li>Class-portion: {@code fully.qualified.examples.AnExample("param1", true, 3)}</li>
               <li>Output: {@code "param1", true, 3}</li>
            </ol></li>
         <li>are not specified: {@code ""}.</li>
      </ol>
    * @see  #getExampleClassFQName(CodeletInstance)
    */
   public static final String getExampleCommandLineParams(CodeletInstance instance) throws CodeletFormatException  {
      String clsTxt = getExampleClassOrFilePortionFromTagletText(instance);
      int openParenIdx = clsTxt.indexOf("(");
      if(openParenIdx == -1)  {
         return  "";
      }
      return  clsTxt.substring((openParenIdx + 1), (clsTxt.length() - 1));
   }
   /**
      <p>The full path to the plain-text file.</p>

    * @return  <code>{@link #getExampleClassOrFilePortionFromTagletText(CodeletInstance instance) getExampleClassOrFilePortionFromTagletText}(instance)</code>
    * @see  #getFileNameWithExtension(CodeletInstance)
    * @see  #getFileNameWithoutExtension(CodeletInstance)
    */
   public static final String getFilePath(CodeletInstance instance)  {
      return  getExampleClassOrFilePortionFromTagletText(instance);
   }
   /**
      <p>The plain-text file name, including its dot-extension (such as {@code ".txt"}), if any.</p>

    * @see  #getFileNameWithoutExtension(CodeletInstance)
    */
   public static final String getFileNameWithExtension(CodeletInstance instance)  {
      String filePortion = getExampleClassOrFilePortionFromTagletText(instance);
      int idxFileSep = filePortion.indexOf(FILE_SEP);
      return ((idxFileSep == -1) ? filePortion
         :  filePortion.substring(idxFileSep + 1));
   }
   /**
      <p>The plain-text file name, excluding its dot-extension (such as {@code ".txt"}).</p>

    * @see  #getFileNameWithExtension(CodeletInstance)
    */
   public static final String getFileNameWithoutExtension(CodeletInstance instance)  {
      String fileWExt = getFileNameWithExtension(instance);
      int idxDot = fileWExt.indexOf(FILE_SEP);
      return ((idxDot == -1) ? fileWExt
         :  fileWExt.substring(0, idxDot));
   }
   /**
      <p>The full on-disk path to the example code's source file.</p>

      <p>Example input<ol>
         <li>{@linkplain #getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</li>
         <li>{@linkplain CodeletBaseConfig#getExampleSourceBaseDir() Base-directory}: {@code "C:\java_code\"}</li>
      </ol>

      <p>Output</p>

      <p>{@code "C:\java_code\fully\qualified\examples\AnExample.java"}</p>

    * @return  <code>{@link com.github.aliteralmind.codelet.CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getExampleSourceBaseDir() getExampleSourceBaseDir}() +
      <br> &nbsp; &nbsp; {@link #getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, {@link com.github.xbn.lang.XbnConstants#FILE_SEP FILE_SEP}*) +
      <br> &nbsp; &nbsp; &quot;.java&quot;</code>
    */
   public static final String getJavaSourceFilePath(CodeletInstance instance)  {
      return  getExampleSourceBaseDir() +
         getExampleClassFQName(instance).replace(".", FILE_SEP) +
         ".java";
   }
   /**
      <p>The absolute url to the example code's source file, <a href="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linksource">as created by</a> JavaDoc (in <a href="{@docRoot}/src-html/">{@code {@docRoot}/src-html/}</a>).</p>

      <p>Example input:<ol>
         <li>Example code's {@linkplain #getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</li>
      </ol>

      <p>Output:</p>

      <p>{@code "{@docRoot}/src-html/fully/qualified/examples/AnExample.html"}</p>

      <p>Gap is {@linkplain CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link #getJavaDocSourceUrl(CodeletInstance) getJavaDocSourceUrl}(<i>[the-instance]</i>)</code></p>

    * @return  <code>&quot;{@docRoot}/src-html/&quot; +
      <br> &nbsp; &nbsp; {@link #getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;) +
      <br> &nbsp; &nbsp; &quot;.html&quot;</code>
    * @see  #getJavaDocExampleUrl(CodeletInstance)
    */
   public static final String getJavaDocSourceUrl(CodeletInstance instance)  {
      return  "{@docRoot}/src-html/" +
         getExampleClassFQName(instance).replace(".", "/") +
         ".html";
   }
   public static final String getJavaDocUrl(CodeletInstance instance)  {
      return  "{@docRoot}" +
         getExampleClassFQName(instance).replace(".", "/") +
         ".html";
   }
   /**
      <p>The absolute url to the example code's source file, as created by JavaDoc (in {@code {@docRoot}/src-html/}).</p>

      <p>Example input:<ol>
         <li>Example code's {@linkplain #getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</li>
      </ol>

      <p>Output:</p>

      <p>{@code "{@docRoot}/src-html/fully/qualified/examples/AnExample.html"}</p>

      <p>Gap is {@linkplain CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link #getJavaDocSourceUrl(CodeletInstance) getJavaDocSourceUrl}(<i>[the-instance]</i>)</code></p>

    * @return  <code>&quot;{@docRoot}&quot; +
      <br> &nbsp; &nbsp; {@link #getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;) +
      <br> &nbsp; &nbsp; &quot;.html&quot;</code>
    * @see  #getJavaDocSourceUrl(CodeletInstance)
    */
   public static final String getJavaDocExampleUrl(CodeletInstance instance)  {
      return  "{@docRoot}" +
         getExampleClassFQName(instance).replace(".", "/") +
         ".html";
   }
   /**
      <p>The example-class or plain-text-file portion of the taglet text.</p>

    * @return  <code>{@link #getTagletTextSplitOnLineProcDelim(CodeletInstance) getTagletTextSplitOnLineProcDelim}(instance)[0]</code>
    */
   public static final String getExampleClassOrFilePortionFromTagletText(CodeletInstance instance) throws CodeletFormatException  {
      return  getTagletTextSplitOnLineProcDelim(instance)[0];
   }
   /**
      <p>The class-or-plain-text-file portion of the taglet text, split on the customizer delimiter character.</p>

    * @param  instance  May not be {@code null}
    * @return  If there is no line-proc (no delimiter char): A one element string array containing the class/file portion. Otherwise a two-element array, where element index one is the customizer portion.
    * @exception  CodeletFormatException  If<ol>
         <li>More than one customizer character is found. For including literal delimiters in string parameters, use <code>{@link com.github.aliteralmind.codelet.CodeletInstance CodeletInstance}.{@link com.github.aliteralmind.codelet.CodeletInstance CodeletInstance#ESCAPED_CUSTOMIZER_PREFIX_CHAR ESCAPED_CUSTOMIZER_PREFIX_CHAR}</code>.</li>
         <li>Either portion (class/file or line-proc) contains no characters.</li>
      </ol>
    * @see  com.github.aliteralmind.codelet.CodeletInstance#getText()
    * @see  #getExampleClassOrFilePortionFromTagletText(CodeletInstance)
    * @see  com.github.aliteralmind.codelet.CodeletInstance#CUSTOMIZER_PREFIX_CHAR CodeletInstance#CUSTOMIZER_PREFIX_CHAR
    */
   public static final String[] getTagletTextSplitOnLineProcDelim(CodeletInstance instance) throws CodeletFormatException  {
      String[] rawObjectProcStrs = null;
      try  {
         rawObjectProcStrs = instance.getText().split((new Character(CodeletInstance.CUSTOMIZER_PREFIX_CHAR)).toString());
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(instance, "instance", null, rx);
      }

      if(rawObjectProcStrs.length > 2)  {
         throw  new CodeletFormatException(instance, "More than one customizer delimiter ('" + CodeletInstance.CUSTOMIZER_PREFIX_CHAR + "') found. For including literal delimiters in string parameters, use \"" + CodeletInstance.ESCAPED_CUSTOMIZER_PREFIX_CHAR + "\"");
      }

      if(rawObjectProcStrs[0].length() == 0)  {
         throw  new CodeletFormatException(instance, "Class/file portion (or the entire taglet!) has no characters");
      }
      if(rawObjectProcStrs.length == 2)  {
         if(!rawObjectProcStrs[1].equals("()")  &&
               (rawObjectProcStrs[1].length() < 3  ||
               rawObjectProcStrs[1].charAt(rawObjectProcStrs[1].length() - 1) != ')'))  {
            throw  new CodeletFormatException(instance, "Customizer portion (\"" + rawObjectProcStrs[1] + "\") is invalid. Must be either the \":()\" shortcut (meaning equal to \"()\"), or a valid Java function-name followed by parentheses with zero or more parameters: \"funcName(...)\". The last character must be the close parenthesis (')').");
         }
      }

      return  rawObjectProcStrs;
   }
   private TagletTextUtil()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
