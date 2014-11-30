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
   import  com.github.xbn.io.DebugLevel;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  java.io.File;
   import  java.util.Objects;
/**
   <p>Represents a single codelet-taglet, as found by {@code javadoc.exe}. Contains its basic elements that compose a single codelet: Its name, text, and enclosing class. Also contains the relative url to the JavaDoc root directory ({@code "{@docRoot}"}), and utility functions.</p>

   @see  com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor#get(Tag) taglet.CodletComSunJavadocTagProcessor#get(Tag)
   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class CodeletInstance  {
   private final DebugLevel  dbgLvl           ;
   private final String      enclosingPackage ;
   private final String      enclosingSimpleNm;
   private final File        enclosingFile    ;
   private final int         lineNum          ;
   private final String      text             ;
   private final String      relUrlToDocRoot  ;
   private final CodeletType type             ;
   /**
      <p>The prefix that, if found before a taglet's text, turns on debugging for that taglet only--Equal to {@code "[DEBUG_LEVEL_"}. This overrides {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL global debugging}, but only if the taglet level is higher.</p>

      <p>Example:</p>

<blockquote>{@code [DEBUG_LEVEL_3]{@.codelet com.github.myjavacode.examples.AnExample}}</blockquote>

      <p>If {@linkplain CodeletBaseConfig#getGlobalDebugLevel() global debugging} is <code>{@link com.github.xbn.io.DebugLevel#OFF OFF}</code>, then this taglet outputs all 1, 2, and 3-level debugging statements.
    **/
   public static final String DEBUG_LEVEL_PREFIX_PREFIX = "[DEBUG_LEVEL_";
   /**
      <p>The character that divides the fully-qualified class-name or plain-text file-path, and its optional <a href="CustomizerUtil.html#overview">Codelet-customizer</a>--Equal to {@code '%'}.</p>

      <p>If there is no customizer, this character must not exist in the taglet's text. For literally-displaying this character (in a string-value parameter, for either the processor or the example code's {@code main} function), use {@link #ESCAPED_CUSTOMIZER_PREFIX_CHAR}.</p>

      <p><i>This cannot be a colon, because it conflicts with absolute Windows paths (such as {@code C:\java_code\}).</i></p>
    **/
   public static final char CUSTOMIZER_PREFIX_CHAR = '%';
   /**
      <p>When the customizer prefix-char needs to be literally displayed in a string-value parameter--Equal to {@code "&amp;#37;"}.</p>

      @see  #CUSTOMIZER_PREFIX_CHAR
      @see  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getObjectFromString(String)
    **/
   public static final String ESCAPED_CUSTOMIZER_PREFIX_CHAR = "&#37;";
   /**
      <p>Create a new Codelet from its taglet elements.</p>

      @param  taglet_name  The {@linkplain CodeletType#getName() name} of the taglet, which implies its type. Get with {@link #getType() getType}{@code ()}.
      @param  enclosing_package  The fully-qualified class name of the codelet's enclosing class--the class whose source-code contains a JavaDoc block, in which this taglet exists. This must be an actually-existing class, as determined by
      <br/> &nbsp; &nbsp; <code>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}(enclosing_package)</code>. Get with {@link #getEnclosingPackage() getEnclosingPackage}{@code ()}.
      @param  enclosing_simpleName  The name of the enclosing file, without any path (or {@linkplain com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil#getEnclosingSimpleName(Tag, IncludePostClassName) generics or function signature}). If the containing file is the overview summary, this must equal {@code "OVERVIEW_SUMMARY"}. If a package summary, this must be {@code "PACKAGE_SUMMARY"}.
      @param  enclosing_file  The enclosing file's {@code File} object, which contains the full path to its source code.
      @param  line_num  The line number in the enclosing classes source code, at which this taglet exists. May not be less than one. Get with {@link #getLineNumber() getLineNumber}{@code ()}.
      @param  tag_text  The original text following the taglet's name. For example, if the taglet is
      <br/> &nbsp; &nbsp; {@code {@.codelet.out my.package.examples.AnExample((byte)-30, true, "Ribsy")%()}}
      <br/>this parameter must equal
      <br/> &nbsp; &nbsp; <code>my.package.examples.AnExample((byte)-30, true, "Ribsy"):()</code>
      <br/>Get with {@link #getText() getText}{@code ()}. Any literal curly braces ({@code '&#123;}' or '{@code '&#125;}') found in string parameters are replaced with actual braces: {@code '&#123;'} and {@code '&#125;'}. Escaping curlys is required in order to prevent {@code javadoc.exe} from incorrectly parsing taglets.
      @param  relUrl_toDocRoot  The relative directory from the containing JavaDoc file to the JavaDoc root directory (the value of {@code {@docRoot}}). Get with {@link #getRelativeUrlToDocRoot() getRelativeUrlToDocRoot}{@code ()}.
    **/
   public CodeletInstance(String taglet_name, String enclosing_package, String enclosing_simpleName, File enclosing_file, int line_num, String tag_text, String relUrl_toDocRoot)  {
      Objects.requireNonNull(relUrl_toDocRoot, "relUrl_toDocRoot");
      if(enclosing_simpleName.indexOf('<') != -1  ||
            enclosing_simpleName.indexOf('(') != -1)  {
         throw  new IllegalArgumentException("enclosing_simpleName (\"" + enclosing_simpleName + "\") contains an open parenthsis ('(') or greater-than sign ('<').");
      }

      if(!tag_text.startsWith(DEBUG_LEVEL_PREFIX_PREFIX))  {
         dbgLvl = DebugLevel.OFF;

      }  else  {
         int prePreLen = DEBUG_LEVEL_PREFIX_PREFIX.length();
         String levelDigitStr = null;
         try  {
            levelDigitStr = tag_text.substring(prePreLen, (prePreLen + 1));
         }  catch(StringIndexOutOfBoundsException sbx)  {
            throw  new IllegalArgumentException("Text *equals* \"" + DEBUG_LEVEL_PREFIX_PREFIX + "\"", sbx);
         }
         dbgLvl = DebugLevel.getFromStringOff12345(levelDigitStr, "[Codelet taglet prefix level number]");

         try  {
            tag_text = tag_text.substring(tag_text.indexOf("]") + 1);
         }  catch(StringIndexOutOfBoundsException sbx)  {
            throw  new IllegalArgumentException("Text starts with \"" + DEBUG_LEVEL_PREFIX_PREFIX + "\", but does not contain the close ']'.", sbx);
         }
      }

      type = CodeletType.newTypeForTagletName(taglet_name, "taglet_name");

      text = tag_text.replace("&#123;", "{").replace("&#125;", "}");

      enclosingPackage = enclosing_package;
      enclosingSimpleNm = enclosing_simpleName;
      enclosingFile = enclosing_file;

      lineNum = line_num;
      relUrlToDocRoot = relUrl_toDocRoot;
   }
   /**
      <p>The original, unaltered {@linkplain com.sun.javadoc.Tag#text() text}, as found after the taglet's name (and any whitespace).</p>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public String getText()  {
      return  text;
   }
   /**
      <p>The type of this Codelet, which implies its {@linkplain CodeletType#getName() name}.</p>

      @see  com.sun.javadoc.Tag#name()
      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public CodeletType getType()  {
      return  type;
   }
   /**
      <p>Get the taglet's enclosing class object, if it is a class.</p>

      @return  If the enclosing JavaDoc file represents a class  according to
      <br/> &nbsp; &nbsp; <code>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}({@link #getEnclosingFullyQualifiedName() getEnclosingFullyQualifiedName}())</code>
      <br/>This returns
      <br/> &nbsp; &nbsp; <code>{@link com.github.xbn.util.ReflectRtxUtil#getClassIfExistsOrNull(String) getClassIfExistsOrNull}(getEnclosingFullyQualifiedName())</code>
      <br/>Otherwise, this returns {@code null}.
    **/
   public Class<?> getEnclosingClass()  {
      return  ReflectRtxUtil.getClassIfExistsOrNull(getEnclosingFullyQualifiedName());
   }
   /**
      <p>Get the fully-qualified name of the taglet's enclosing JavaDoc file. If it is a class, this is appropriate for <code>{@link java.lang.Class}.{@link java.lang.Class#forName(String) forName})</code>.</p>

      @return  If {@link #getEnclosingPackage() getEnclosingPackage}{@code ()} has<ul>
         <li>zero characters:
      <br/> &nbsp; &nbsp; <code>getEnclosingPackage() + getEnclosingSimpleName()</code></li>
         <li>One-or-more characters: <code>getEnclosingPackage() + &quot;.&quot; + getEnclosingSimpleName()</code></li>
      </ul>
      @see  #getEnclosingClass()
    **/
   public String getEnclosingFullyQualifiedName()  {
      StringBuilder bldr = new StringBuilder(getEnclosingPackage());
      if(getEnclosingPackage().length() > 0)  {
         bldr.append(".");
      }
      return  bldr.append(getEnclosingSimpleName()).toString();
   }
   /**
      <p>The package name of this taglet's {@linkplain com.sun.javadoc.Tag#holder() containing JavaDoc file}.</p>

      @see  #isOverviewSummary()
      @see  #getEnclosingClass()
      @see  #getEnclosingFullyQualifiedName()
      @see  #getEnclosingSimpleName()
      @see  #getEnclosingFile()
      @see  #getEnclosingPackage()
      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public String getEnclosingPackage()  {
      return  enclosingPackage;
   }
   /**
      <p>Is the enclosing file the <a href="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#overview">overview summary</a> page?.</p>

      @return  <code>({@link #getEnclosingPackage() getEnclosingPackage}().length() == 0)</code>
    **/
   public boolean isOverviewSummary()  {
      return  (getEnclosingPackage().length() == 0);
   }
   /**
      <p>The post-package name of this taglet's containing JavaDoc file.</p>

      @see  #getEnclosingPackage()
    **/
   public String getEnclosingSimpleName()  {
      return  enclosingSimpleNm;
   }
   /**
      <p>The {@linkplain com.sun.javadoc.SourcePosition#file() file-object} whose source code contains a JavaDoc block, in which this codelet exists. This contains the full path to the enclosing classes source code.</p>

      @see  #getEnclosingPackage()
      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public File getEnclosingFile()  {
      return  enclosingFile;
   }
   /**
      <p>The {@linkplain com.sun.javadoc.SourcePosition#line() line number} in the enclosing classes source-code, at which this codelet exists.</p>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public int getLineNumber()  {
      return  lineNum;
   }
   /**
      <p>The relative directory from the containing classes JavaDoc file, to the JavaDoc root directory--Equivalent to {@code {@docRoot}}.</p>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public String getRelativeUrlToDocRoot()  {
      return  relUrlToDocRoot;
   }
   /**
      <p>The debugging level for this taglet only, as defined in the optional override prefix. This is used only when it is at a higher level than {@linkplain CodeletBaseConfig#getGlobalDebugLevel() global debugging}.</p>

      @see  #DEBUG_LEVEL_PREFIX_PREFIX
      @see  CodeletBaseConfig#isDebugOn(CodeletInstance) CodeletBaseConfig#isDebugOn
      @see  CodeletBaseConfig#isDebugOn(CodeletInstance) CodeletBaseConfig#isDebugOn
    **/
   public DebugLevel getDebugLevel()  {
      return  dbgLvl;
   }
   /**
      <p>The enclosing file, plus line number, followed by the full taglet text.</p>

      @return  <code>{@link #getEnclosingFile() getEnclosingFile}() + &quot;(&quot; + {@link #getLineNumber() getLineNumber}() &quot;): &quot; + {@link #getFullOriginalTaglet() getFullOriginalTaglet}()</code>
    **/
   public String toString()  {
      return  getEnclosingFile() + "(" + getLineNumber() + "): " + getFullOriginalTaglet();
   }
   /**
      <p>Returns the full original taglet, exactly as found in the JavaDoc.</p>

      @return  <code>&quot;{&amp;#64;&quot; + <i>[the-{@link #DEBUG_LEVEL_PREFIX_PREFIX debug-prefix}-if-provided]</i> + {@link #getType() getType}().{@link com.github.aliteralmind.codelet.CodeletType#getName() getName}() + &quot; &quot; + {@link #getText() getText}() + &quot;}&quot;</code>
    **/
   public String getFullOriginalTaglet()  {
      StringBuilder bldr = new StringBuilder("{@").append(getType().getName()).append(" ");
      if(getDebugLevel().isOn())  {
         bldr.append(DEBUG_LEVEL_PREFIX_PREFIX).
            append(getDebugLevel().getNumber()).append("]");
      }
      return  bldr.append(getText()).append("}").toString();
   }
}
