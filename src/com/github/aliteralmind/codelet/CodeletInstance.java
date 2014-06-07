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
   import  com.github.xbn.io.DebugLevel;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.xbn.text.StringWithNullDefault;
   import  java.io.File;
   import  java.util.Objects;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>Represents a single codelet-taglet, as found by {@code javadoc.exe}. Contains its basic elements that compose a single codelet: Its name, text, and enclosing class. Also contains the relative url to the JavaDoc root directory ({@code "{@docRoot}"}), and utility functions.</P>

   @see  com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor#get(Tag) taglet.CodletComSunJavadocTagProcessor#get(Tag)
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
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
      <P>The prefix that, if found before a taglet's text, turns on debugging for that taglet only--Equal to {@code "[DEBUG_LEVEL_"}. This overrides {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL global debugging}, but only if the taglet level is higher.</P>

      <P>Example:</P>

<BLOCKQUOTE>{@code [DEBUG_LEVEL_3]{@.codelet com.github.myjavacode.examples.AnExample}}</BLOCKQUOTE>

      <P>If {@linkplain CodeletBaseConfig#getGlobalDebugLevel() global debugging} is <CODE>{@link com.github.xbn.io.DebugLevel#OFF OFF}</CODE>, then this taglet outputs all 1, 2, and 3-level debugging statements.
    **/
   public static final String DEBUG_LEVEL_PREFIX_PREFIX = "[DEBUG_LEVEL_";
   /**
      <P>The character that divides the fully-qualified class-name or plain-text file-path, and its optional <A HREF="CustomizerUtil.html#overview">Codelet-customizer</A>--Equal to {@code ':'}.</P>

      <P>If there is no customizer, this character must not exist in the taglet's text. For literally-displaying this character (in a string-value parameter, for either the processor or the example code's {@code main} function), use {@link #ESCAPED_CUSTOMIZER_PREFIX_CHAR}.</P>
    **/
   public static final char CUSTOMIZER_PREFIX_CHAR = ':';
   /**
      <P>When the customizer prefix-char needs to be literally displayed in a string-value parameter--Equal to {@code "&amp;#58;"}.</P>

      @see  #CUSTOMIZER_PREFIX_CHAR
      @see  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getObjectFromString(String)
    **/
   public static final String ESCAPED_CUSTOMIZER_PREFIX_CHAR = "&#58;";
   /**
      <P>Create a new Codelet from its taglet elements.</P>

      @param  taglet_name  The {@linkplain CodeletType#getName() name} of the taglet, which implies its type. Get with {@link #getType() getType}{@code ()}.
      @param  enclosing_package  The fully-qualified class name of the codelet's enclosing class--the class whose source-code contains a JavaDoc block, in which this taglet exists. This must be an actually-existing class, as determined by
      <BR> &nbsp; &nbsp; <CODE>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}(enclosing_package)</CODE>. Get with {@link #getEnclosingPackage() getEnclosingPackage}{@code ()}.
      @param  enclosing_simpleName  The name of the enclosing file, without any path (or {@linkplain com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil#getEnclosingSimpleName(Tag, IncludePostClassName) generics or function signature}). If the containing file is the overview summary, this must equal {@code "OVERVIEW_SUMMARY"}. If a package summary, this must be {@code "PACKAGE_SUMMARY"}.
      @param  enclosing_file  The enclosing file's {@code File} object, which contains the full path to its source code.
      @param  line_num  The line number in the enclosing classes source code, at which this taglet exists. May not be less than one. Get with {@link #getLineNumber() getLineNumber}{@code ()}.
      @param  tag_text  The original text following the taglet's name. For example, if the taglet is
      <BR> &nbsp; &nbsp; {@code {@.codelet.out my.package.examples.AnExample((byte)-30, true, "Ribsy"):()}}
      <BR>this parameter must equal
      <BR> &nbsp; &nbsp; <CODE>my.package.examples.AnExample((byte)-30, true, "Ribsy"):()</CODE>
      <BR>Get with {@link #getText() getText}{@code ()}. Any literal close curlys ({@code '&#125;}) found in string parameters are replaced with actual close-curlys: {@code '&#125;'}. Escaping close curlys is required in order to prevent {@code javadoc.exe} from mistakenly ending the taglet in the middle of a string parameter.
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

      text = tag_text.replace("&#125;", "}");

      enclosingPackage = enclosing_package;
      enclosingSimpleNm = enclosing_simpleName;
      enclosingFile = enclosing_file;

      lineNum = line_num;
      relUrlToDocRoot = relUrl_toDocRoot;
   }
   /**
      <P>The original, unaltered {@linkplain com.sun.javadoc.Tag#text() text}, as found after the taglet's name (and any whitespace).</P>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public String getText()  {
      return  text;
   }
   /**
      <P>The type of this Codelet, which implies its {@linkplain CodeletType#getName() name}.</P>

      @see  com.sun.javadoc.Tag#name()
      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public CodeletType getType()  {
      return  type;
   }
   /**
      <P>Get the taglet's enclosing class object, if it is a class.</P>

      @return  If the enclosing JavaDoc file represents a class  according to
      <BR> &nbsp; &nbsp; <CODE>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}({@link #getEnclosingFullyQualifiedName() getEnclosingFullyQualifiedName}())</CODE>
      <BR>This returns
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.util.ReflectRtxUtil#getClassIfExistsOrNull(String) getClassIfExistsOrNull}(getEnclosingFullyQualifiedName())</CODE>
      <BR>Otherwise, this returns {@code null}.
    **/
   public Class<?> getEnclosingClass()  {
      return  ReflectRtxUtil.getClassIfExistsOrNull(getEnclosingFullyQualifiedName());
   }
   /**
      <P>Get the fully-qualified name of the taglet's enclosing JavaDoc file. If it is a class, this is appropriate for <CODE>{@link java.lang.Class}.{@link java.lang.Class#forName(String) forName})</CODE>.</P>

      @return  If {@link #getEnclosingPackage() getEnclosingPackage}{@code ()} has<UL>
         <LI>zero characters:
      <BR> &nbsp; &nbsp; <CODE>getEnclosingPackage() + getEnclosingSimpleName()</CODE></LI>
         <LI>One-or-more characters: <CODE>getEnclosingPackage() + &quot;.&quot; + getEnclosingSimpleName()</CODE></LI>
      </UL>
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
      <P>The package name of this taglet's {@linkplain com.sun.javadoc.Tag#holder() containing JavaDoc file}.</P>

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
      <P>Is the enclosing file the <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#overview">overview summary</A> page?.</P>

      @return  <CODE>({@link #getEnclosingPackage() getEnclosingPackage}().length() == 0)</CODE>
    **/
   public boolean isOverviewSummary()  {
      return  (getEnclosingPackage().length() == 0);
   }
   /**
      <P>The post-package name of this taglet's containing JavaDoc file.</P>

      @see  #getEnclosingPackage()
    **/
   public String getEnclosingSimpleName()  {
      return  enclosingSimpleNm;
   }
   /**
      <P>The {@linkplain com.sun.javadoc.SourcePosition#file() file-object} whose source code contains a JavaDoc block, in which this codelet exists. This contains the full path to the enclosing classes source code.</P>

      @see  #getEnclosingPackage()
      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public File getEnclosingFile()  {
      return  enclosingFile;
   }
   /**
      <P>The {@linkplain com.sun.javadoc.SourcePosition#line() line number} in the enclosing classes source-code, at which this codelet exists.</P>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public int getLineNumber()  {
      return  lineNum;
   }
   /**
      <P>The relative directory from the containing classes JavaDoc file, to the JavaDoc root directory--Equivalent to {@code {@docRoot}}.</P>

      @see  #CodeletInstance(String, String, String, File, int, String, String) CodeletInstance(s,s,s,f,i,s,s)
    **/
   public String getRelativeUrlToDocRoot()  {
      return  relUrlToDocRoot;
   }
   /**
      <P>The debugging level for this taglet only, as defined in the optional override prefix. This is used only when it is at a higher level than {@linkplain CodeletBaseConfig#getGlobalDebugLevel() global debugging}.</P>

      @see  #DEBUG_LEVEL_PREFIX_PREFIX
      @see  CodeletBaseConfig#isDebugOn(CodeletInstance) CodeletBaseConfig#isDebugOn
      @see  CodeletBaseConfig#isDebugOn(CodeletInstance) CodeletBaseConfig#isDebugOn
    **/
   public DebugLevel getDebugLevel()  {
      return  dbgLvl;
   }
   /**
      <P>The enclosing file, plus line number, followed by the full taglet text.</P>

      @return  <CODE>{@link #getEnclosingFile() getEnclosingFile}() + &quot;(&quot; + {@link #getLineNumber() getLineNumber}() &quot;): &quot; + {@link #getFullOriginalTaglet() getFullOriginalTaglet}()</CODE>
    **/
   public String toString()  {
      return  getEnclosingFile() + "(" + getLineNumber() + "): " + getFullOriginalTaglet();
   }
   /**
      <P>Returns the full original taglet, exactly as found in the JavaDoc.</P>

      @return  <CODE>&quot;{&amp;#64;&quot; + <I>[the-{@link #DEBUG_LEVEL_PREFIX_PREFIX debug-prefix}-if-provided]</I> + {@link #getType() getType}().{@link com.github.aliteralmind.codelet.CodeletType#getName() getName}() + &quot; &quot; + {@link #getText() getText}() + &quot;}&quot;</CODE>
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
