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
package  com.github.aliteralmind.codelet.type;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.UserExtraGapGetter;
   import  com.github.aliteralmind.codelet.CodeletGap;
   import  com.github.aliteralmind.codelet.CodeletBaseConfig;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.TagletTextUtil;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  java.util.Map;
   import  java.util.Set;
   import  java.util.TreeMap;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>The text into which {@code {@.codelet}} output (the example-code's source) is placed.</P>

   <A NAME="gaps"></A><H2><A HREF="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: {@code {@.codelet}}: Template: <U>Gap names</U></H2>

   <P>The only required gap is &quot;{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#BODY_GAP_NAME body}&quot;, which is where the fully-processed source-code is placed.</P>

   <P>In addition to any {@linkplain com.github.aliteralmind.codelet.UserExtraGapGetter#getForSourceCodelet() user-extra} gaps, the {@code {@.codelet}} template also provides for the following &quot;default&quot; optional gaps:</P>

   <A NAME="gaps"></A><P><A HREF="SourceCodeTemplate.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; <B>Url-related:</B></P>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>{@link #GAP_SOURCE_URL source_url}</TD>
      <TD>The absolute url to the example code's source file, <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linksource">as created by</A> JavaDoc (in <A HREF="{@docRoot}/src-html/">{@code {@docRoot}/src-html/}</A>)</TD>
   </TR><TR>
      <TD>{@link #GAP_JAVADOC_URL javadoc_url}</TD>
      <TD>The absolute url to the example code's JavaDoc.</TD>
   </TR><TR>
      <TD>{@link #GAP_FQ_CLASS_NAME_URL fq_class_name_url}</TD>
      <TD>The example's fully-qualified class name, with dots ({@code '.'}) replaced by url-slashes ({@code '/'}).</TD>
   </TR><TR>
      <TD>{@link #GAP_PACKAGE_URL package_url}</TD>
      <TD>The example code's package, with dots ({@code '.'}) replaced by url-slashes ({@code '/'}).</TD>
   </TR></TABLE></P>

   <A NAME="gaps"></A><P><A HREF="SourceCodeTemplate.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; <B>Non-url related:</B></P>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>{@link #GAP_SRC_PATH source_path}</TD>
      <TD>The full on-disk path to the example code's source file.</TD>
   </TR><TR>
      <TD>{@link #GAP_SRC_BASE_DIR source_base_dir}</TD>
      <TD>The on-disk directory in which the <I>top-most package</I> of example source-code file exists.</TD>
   </TR><TR>
      <TD>{@link #GAP_SIMPLE_CLASS_NAME simple_class_name}</TD>
      <TD>The example code's non-fully-qualified class name.</TD>
   </TR><TR>
      <TD>{@link #GAP_FQ_CLASS_NAME fq_class_name}</TD>
      <TD>The example code's fully-qualified class name.</TD>
   </TR><TR>
      <TD>{@link #GAP_FQ_CLASS_NAME_DIR fq_class_name_dir}</TD>
      <TD>The example's fully-qualified class name, with dots {@code '.'} replaced by file-separators.</TD>
   </TR><TR>
      <TD>{@link #GAP_PACKAGE package}</TD>
      <TD>The example code's package name.</TD>
   </TR><TR>
      <TD>{@link #GAP_PACKAGE_DIR package_dir}</TD>
      <TD>The example's package name, with dots {@code '.'} replaced by file-separators.</TD>
   </TR></TABLE></P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class SourceCodeTemplate extends OnlyOneBodyGapTemplateBase  {
   /**
      <P>The full on-disk path to the example code's source file--Gap name is {@code "source_path"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

      <P><CODE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getJavaSourceFilePath(CodeletInstance) getJavaSourceFilePath}(<I>[the-instance]</I>)</CODE></P>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_SRC_BASE_DIR
    **/
   public static final CodeletGap GAP_SRC_PATH = new SourcePathGap();
   /**
      <P>The on-disk directory in which the <I>top-most package</I> of example source-code file exists--Gap name is {@code "source_base_dir"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.CodeletBaseConfig CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#INSTANCE INSTANCE}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getExampleSourceBaseDir() getExampleClassFQName}()</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_SRC_PATH
    **/
   public static final CodeletGap GAP_SRC_BASE_DIR = new SourceBaseDirGap();
   /**
      <P>The example code's non-fully-qualified class name--Gap name is {@code "simple_class_name"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleSimpleClassName(CodeletInstance) getExampleSimpleClassName}(<I>[the-instance]</I>)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_FQ_CLASS_NAME
      @see  #GAP_PACKAGE
    **/
   public static final CodeletGap GAP_SIMPLE_CLASS_NAME = new ClassNameGap();
   /**
      <P>The example code's fully-qualified class name--Gap name is {@code "fq_class_name"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(<I>[the-instance]</I>)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_FQ_CLASS_NAME_DIR
      @see  #GAP_FQ_CLASS_NAME_URL
      @see  #GAP_PACKAGE
      @see  #GAP_SIMPLE_CLASS_NAME
    **/
   public static final CodeletGap GAP_FQ_CLASS_NAME = new FQClassNameGap();
   /**
      <P>The example's fully-qualified class name, with dots ({@code '.'}) replaced by url-slashes ({@code '/'})--Gap name is {@code "fq_class_name_url"}.</P>

      <P>Example input:<OL>
         <LI>Example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</LI>
      </OL></P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output}:</P>

      <P>{@code "fully/qualified/examples/AnExample"}</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(<I>[the-instance]</I>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_url"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_FQ_CLASS_NAME
    **/
   public static final CodeletGap GAP_FQ_CLASS_NAME_URL = new FQClassNameUrlGap();
   /**
      <P>The example's fully-qualified class name, with dots {@code '.'} replaced by file-separators--Gap name is {@code "fq_class_name_dir"}.</P>

      <P>Example input:<OL>
         <LI>Example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</LI>
      </OL></P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output} (assuming Microsoft Windows):</P>

      <P>{@code "fully\qualified\examples\AnExample"}</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(<I>[the-instance]</I>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, {@link com.github.xbn.lang.XbnConstants#FILE_SEP FILE_SEP}*)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_FQ_CLASS_NAME
    **/
   public static final CodeletGap GAP_FQ_CLASS_NAME_DIR = new FQClassNameDirGap();
   /**
      <P>The absolute url to the example code's source file, <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linksource">as created by</A> JavaDoc (in <A HREF="{@docRoot}/src-html/">{@code {@docRoot}/src-html/}</A>)--Gap name is {@code "source_url"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getJavaDocSourceUrl(CodeletInstance) getJavaDocSourceUrl}(<I>[the-instance]</I>)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_url"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_JAVADOC_URL
    **/
   public static final CodeletGap GAP_SOURCE_URL = new SourceUrlGap();
   /**
      <P>The absolute url to the example code's JavaDoc--Gap name is {@code "javadoc_url"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getJavaDocSourceUrl(CodeletInstance) getJavaDocSourceUrl}(<I>[the-instance]</I>)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_url"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_SOURCE_URL
    **/
   public static final CodeletGap GAP_JAVADOC_URL = new JavadocUrlGap();
   /**
      <P>The example code's package name--Gap name is {@code "package"}.</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExamplePackageName(CodeletInstance) getExamplePackageName}(<I>[the-instance]</I>)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_PACKAGE_DIR
      @see  #GAP_PACKAGE_URL
      @see  #GAP_FQ_CLASS_NAME
      @see  #GAP_SIMPLE_CLASS_NAME
    **/
   public static final CodeletGap GAP_PACKAGE = new PackageGap();
   /**
      <P>The example code's package, with dots ({@code '.'}) replaced by url-slashes ({@code '/'})--Gap name is {@code "package_url"}.</P>

      <P>Example input:<OL>
         <LI>The example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</LI>
      </OL></P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output}:</P>

      <P>{@code "fully/qualified/examples"}</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExamplePackageName(CodeletInstance) getExamplePackageName}(<I>[the-instance]</I>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_url"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_PACKAGE
    **/
   public static final CodeletGap GAP_PACKAGE_URL = new PackageUrlGap();
   /**
      <P>The example's package name, with dots {@code '.'} replaced by file-separators--Gap name is {@code "package_dir"}.</P>

      <P>Example input:<OL>
         <LI>The example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</LI>
      </OL></P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output} (assuming Microsoft Windows):</P>

      <P>{@code "fully\qualified\examples"}</P>

      <P>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExamplePackageName(CodeletInstance) getExamplePackageName}(<I>[the-instance]</I>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, {@link com.github.xbn.lang.XbnConstants#FILE_SEP FILE_SEP}*)</PRE></BLOCKQUOTE>

      @see  <A HREF="#codelet_tmpl_gaps_dir"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A>
      @see  #GAP_PACKAGE
    **/
   public static final CodeletGap GAP_PACKAGE_DIR = new PackageDirGap();

   /**
      <P>Create the first instance.</P>

      <P>Equal to</P>

<BLOCKQUOTE><PRE>
{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(CodeletType, FeatherTemplate, String, CodeletGap[], UserExtraGapGetter) super}({@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE SOURCE_CODE}, template, tmpl_path,
   new CodeletGap[] {
      {@link #GAP_SRC_PATH},
      {@link #GAP_SRC_BASE_DIR},
      {@link #GAP_SIMPLE_CLASS_NAME},
      {@link #GAP_FQ_CLASS_NAME},
      {@link #GAP_FQ_CLASS_NAME_URL},
      {@link #GAP_FQ_CLASS_NAME_DIR},
      {@link #GAP_SOURCE_URL},
      {@link #GAP_JAVADOC_URL},
      {@link #GAP_PACKAGE},
      {@link #GAP_PACKAGE_URL},
      {@link #GAP_PACKAGE_DIR}},
   userExtra_getter)
</PRE></BLOCKQUOTE>
      @see  #SourceCodeTemplate(SourceCodeTemplate, FeatherTemplate, String)
    **/
   public SourceCodeTemplate(FeatherTemplate template, String tmpl_path, UserExtraGapGetter userExtra_getter)  {
      super(CodeletType.SOURCE_CODE, template, tmpl_path,
         new CodeletGap[] {
            GAP_SRC_PATH,
            GAP_SRC_BASE_DIR,
            GAP_SIMPLE_CLASS_NAME,
            GAP_FQ_CLASS_NAME,
            GAP_FQ_CLASS_NAME_URL,
            GAP_FQ_CLASS_NAME_DIR,
            GAP_SOURCE_URL,
            GAP_JAVADOC_URL,
            GAP_PACKAGE,
            GAP_PACKAGE_URL,
            GAP_PACKAGE_DIR},
         userExtra_getter);
   }
   /**
      <P>Create the second or subsequent instance.</P>

      <P>Equal to</P>

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase, FeatherTemplate, String) super}(to_copy, template, tmpl_path)</PRE></BLOCKQUOTE>

      @see  #SourceCodeTemplate(FeatherTemplate, String, UserExtraGapGetter)
    **/
   public SourceCodeTemplate(SourceCodeTemplate to_copy, FeatherTemplate template, String tmpl_path)  {
      super(to_copy, template, tmpl_path);
   }
   public SourceCodeTemplate(SourceCodeTemplate to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy, debugDest_ifNonNull);
   }
   public SourceCodeTemplate fillBody(String fully_processed)  {
      fillBodyGap(fully_processed);
      return  this;
   }
   public static final SourceCodeTemplate newFromPathAndUserExtraGaps(String path, String path_name, UserExtraGapGetter userExtra_getter)  {
      return  (new SourceCodeTemplate(newTemplateFromPath(path, path_name), path, userExtra_getter));
   }
   /**
      <P>Duplicate this template.</P>

      @return  <CODE>(new {@link #SourceCodeTemplate(SourceCodeTemplate, Appendable) SourceCodeTemplate}(this, debugDest_ifNonNull))</CODE>
    **/
   public SourceCodeTemplate getObjectCopy(Appendable debugDest_ifNonNull)  {
      return  (new SourceCodeTemplate(this, debugDest_ifNonNull));
   }
}
class SourcePathGap extends CodeletGap  {
   public SourcePathGap()  {
      super("source_path");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getJavaSourceFilePath(instance);
   }
}
class SourceBaseDirGap extends CodeletGap  {
   public SourceBaseDirGap()  {
      super("source_base_dir");
   }
   public String getFillText(CodeletInstance ignored)  {
      return  CodeletBaseConfig.getExampleSourceBaseDir();
   }
}
class ClassNameGap extends CodeletGap  {
   public ClassNameGap()  {
      super("simple_class_name");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleSimpleClassName(instance);
   }
}
class FQClassNameGap extends CodeletGap  {
   public FQClassNameGap()  {
      super("fq_class_name");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleClassFQName(instance);
   }
}
class FQClassNameUrlGap extends CodeletGap  {
   public FQClassNameUrlGap()  {
      super("fq_class_name_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleClassFQName(instance).replace(".", "/");
   }
}
class FQClassNameDirGap extends CodeletGap  {
   public FQClassNameDirGap()  {
      super("fq_class_name_dir");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleClassFQName(instance).replace(".", FILE_SEP);
   }
}
class JavadocUrlGap extends CodeletGap  {
   public JavadocUrlGap()  {
      super("javadoc_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getJavaDocUrl(instance);
   }
}
class SourceUrlGap extends CodeletGap  {
   public SourceUrlGap()  {
      super("source_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getJavaDocSourceUrl(instance);
   }
}
class PackageGap extends CodeletGap  {
   public PackageGap()  {
      super("package");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExamplePackageName(instance);
   }
}
class PackageUrlGap extends CodeletGap  {
   public PackageUrlGap()  {
      super("package_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExamplePackageName(instance).replace(".", "/");
   }
}
class PackageDirGap extends CodeletGap  {
   public PackageDirGap()  {
      super("package_dir");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExamplePackageName(instance).replace(".", FILE_SEP);
   }
}
