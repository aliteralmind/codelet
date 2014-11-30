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
   <p>The text into which {@code {@.file.textlet}} output (the example-code's console output) is placed.</p>

   <A NAME="gaps"></a><h2><a href="{@docRoot}/com/github/aliteralmind.codelet/CodeletTemplateBase.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: {@code {@.file.textlet}}: Template: <u>Gap names</u></h2>

   <p>The only required gap is &quot;{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#BODY_GAP_NAME body}&quot;, which is where the fully-processed source-code is placed.</p>

   <p>In addition to any {@linkplain com.github.aliteralmind.codelet.UserExtraGapGetter#getForFileTextlet() user-extra} gaps, the {@code {@.codelet}} template also provides for the following &quot;default&quot; optional gaps:</p>

   <A NAME="gaps"></a><p><a href="SourceCodeTemplate.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; <b>Url-related:</b></p>

   <p><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
   </TR><TR>
      <TD>{@link #GAP_REL_PATH_AS_URL rel_path_url}</TD>
      <TD>The file's relative url-path, as it exists in the base url.</TD>
   </TR><TR>
      <TD>{@link #GAP_URL javadoc_path_url}</TD>
      <TD>The absolute url to the example code's source file, as exists somewhere in the JavaDoc output directory ({@code {@docRoot}}).</TD>
   </TR><TR>
      <TD>{@link #GAP_REL_DIR_AS_URL relative_dir_as_url}</TD>
      <TD>The example code's relative_dir, with file-separators replaced by url-slashes ({@code '/'}).</TD>
   </TR></TABLE></p>

   <A NAME="gaps"></a><p><a href="SourceCodeTemplate.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; <b>Non-url related:</b></p>

   <p><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
   </TR><TR>
      <TD>{@link #GAP_PATH path}</TD>
      <TD>The absolute on-disk path to the file.</TD>
   </TR><TR>
      <TD>{@link #GAP_BASE_DIR base_dir}</TD>
      <TD>The on-disk path that is prepended to the file's relative path.</TD>
   </TR><TR>
      <TD>{@link #GAP_FILE_NAME_NO_EXT file_name_no_ext}</TD>
      <TD>The name of the file, without its dot-extension (such as .txt"}).</TD>
   </TR><TR>
      <TD>{@link #GAP_FILE_NAME_WITH_EXT file_name}</TD>
      <TD>The name of the file, including its dot-extension (such as .txt"}).</TD>
   </TR><TR>
      <TD>{@link #GAP_RELATIVE_PATH file_name_dir}</TD>
      <TD>The example's path as it exists in the base directory.</TD>
   </TR><TR>
      <TD>{@link #GAP_RELATIVE_DIR relative_dir}</TD>
      <TD>The example code's relative directory, as it exists in the absolute path.</TD>
   </TR></TABLE></p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class FileTextTemplate extends OnlyOneBodyGapTemplateBase  {
   /**
      <p>The absolute on-disk path to the file--Gap name is {@code "path"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getFilePath(CodeletInstance) getFilePath}(<i>[the-instance]</i>)</code></p>

      @see  #GAP_BASE_DIR
      @see  #GAP_RELATIVE_PATH
    **/
   public static final CodeletGap GAP_PATH = new FilePathGap();
   /**
      <p>The on-disk path that is prepended to the file's relative path--Gap name is {@code "base_dir"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.CodeletBaseConfig CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#INSTANCE INSTANCE}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getExampleSourceBaseDir() getExampleSourceBaseDir}()</code></p>

      @see  #GAP_PATH
    **/
   public static final CodeletGap GAP_BASE_DIR = new FileBaseDirGap();
   /**
      <p>The name of the file, without its dot-extension (such as {@code ".txt"})--Gap name is {@code "file_name_no_ext"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getFileNameWithoutExtension(CodeletInstance) getFileNameWithoutExtension}(<i>[the-instance]</i>)</code></p>

      @see  #GAP_FILE_NAME_WITH_EXT
      @see  #GAP_RELATIVE_PATH
    **/
   public static final CodeletGap GAP_FILE_NAME_NO_EXT = new FileNameNoExtGap();
   /**
      <p>The name of the file, including its dot-extension (such as {@code ".txt"})--Gap name is {@code "file_name"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getFileNameWithExtension(CodeletInstance) getFileNameWithExtension}(<i>[the-instance]</i>)</code></p>

      @see  #GAP_RELATIVE_PATH
      @see  #GAP_REL_PATH_AS_URL
      @see  #GAP_FILE_NAME_NO_EXT
    **/
   public static final CodeletGap GAP_FILE_NAME_WITH_EXT = new FileNameWithExtGap();
   /**
      <p>The file's relative url-path, as it exists in the base url--Gap name is {@code "rel_path_url"}..</p>

      <p>Example input:<ol>
         <li>Example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</li>
      </ol></p>

      <p>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output}:</p>

      <p>{@code "fully/qualified/examples/AnExample"}</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(<i>[the-instance]</i>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;)</code></p>

      @see  #GAP_URL
    **/
   public static final CodeletGap GAP_REL_PATH_AS_URL = new RelPathAsUrlGap();
   /**
      <p>The example's path as it exists in the base directory--Gap name is {@code "file_name_dir"}.</p>

      <p>Example input:<ol>
         <li>Example code's {@linkplain com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}: {@code "fully.qualified.examples.AnExample"}</li>
      </ol></p>

      <p>{@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) Output} (assuming Microsoft Windows):</p>

      <p>{@code "fully\qualified\examples\AnExample"}</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(<i>[the-instance]</i>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, {@link com.github.xbn.lang.XbnConstants#FILE_SEP FILE_SEP}*)</code></p>

      @see  #GAP_PATH
      @see  #GAP_REL_PATH_AS_URL
      @see  #GAP_FILE_NAME_WITH_EXT
    **/
   public static final CodeletGap GAP_RELATIVE_PATH = new RelativeDirGap();
   /**
      <p>The absolute url to the example code's source file, as exists somewhere in the JavaDoc output directory ({@code {@docRoot}})--Gap name is {@code "javadoc_path_url"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getJavaDocSourceUrl(CodeletInstance) getJavaDocSourceUrl}(<i>[the-instance]</i>)</code></p>
    **/
   public static final CodeletGap GAP_URL = new JavaDocFileUrlGap();
   /**
      <p>The example code's relative directory, as it exists in the absolute path--Gap name is {@code "relative_dir"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getFilePath(CodeletInstance) getFilePath}(<i>[the-instance]</i>)</code></p>

      @see  #GAP_PATH
      @see  #GAP_REL_DIR_AS_URL
    **/
   public static final CodeletGap GAP_RELATIVE_DIR = new RelDirGap();
   /**
      <p>The example code's relative_dir, with file-separators replaced by url-slashes ({@code '/'})--Gap name is {@code "relative_dir_as_url"}.</p>

      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

      <p><code>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getFilePath(CodeletInstance) getFilePath}(<i>[the-instance]</i>).{@link java.lang.String#replace(CharSequence, CharSequence) replace}(&quot;.&quot;, &quot;/&quot;)</code></p>

      @see  #GAP_RELATIVE_DIR
    **/
   public static final CodeletGap GAP_REL_DIR_AS_URL = new RelDirAsUrlGap();

   /**
      <p>Create the first instance.</p>

      <p>Equal to</p>

<blockquote><pre>
{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(CodeletType, FeatherTemplate, String, CodeletGap[], UserExtraGapGetter) super}({@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT FILE_TEXT}, template, tmpl_path,
   new CodeletGap[] {
      {@link #GAP_PATH},
      {@link #GAP_BASE_DIR},
      {@link #GAP_FILE_NAME_NO_EXT},
      {@link #GAP_FILE_NAME_WITH_EXT},
      {@link #GAP_REL_PATH_AS_URL},
      {@link #GAP_RELATIVE_PATH},
      {@link #GAP_URL},
      {@link #GAP_RELATIVE_DIR},
      {@link #GAP_REL_DIR_AS_URL}},
   userExtra_getter)
</pre></blockquote>

      @see  #FileTextTemplate(FileTextTemplate, FeatherTemplate, String)
    **/
   public FileTextTemplate(FeatherTemplate template, String tmpl_path, UserExtraGapGetter userExtra_getter)  {
      super(CodeletType.SOURCE_CODE, template, tmpl_path,
         new CodeletGap[] {
            GAP_PATH,
            GAP_BASE_DIR,
            GAP_FILE_NAME_NO_EXT,
            GAP_FILE_NAME_WITH_EXT,
            GAP_REL_PATH_AS_URL,
            GAP_RELATIVE_PATH,
            GAP_URL,
            GAP_RELATIVE_DIR,
            GAP_REL_DIR_AS_URL},
         userExtra_getter);
   }
   /**
      <p>Create the second or subsequent instance.</p>

      <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase, FeatherTemplate, String) super}(to_copy, template, tmpl_path)</pre></blockquote>

      @see  #FileTextTemplate(FeatherTemplate, String, UserExtraGapGetter)
    **/
   public FileTextTemplate(FileTextTemplate to_copy, FeatherTemplate template, String tmpl_path)  {
      super(to_copy, template, tmpl_path);
   }
   public FileTextTemplate(FileTextTemplate to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy, debugDest_ifNonNull);
   }
   public FileTextTemplate fillBody(String fully_processed)  {
      fillBodyGap(fully_processed);
      return  this;
   }
   /**
      <p>Duplicate this template.</p>

      @return  <code>(new {@link #FileTextTemplate(FileTextTemplate, Appendable) FileTextTemplate}(this, debugDest_ifNonNull))</code>
    **/
   public FileTextTemplate getObjectCopy(Appendable debugDest_ifNonNull)  {
      return  (new FileTextTemplate(this, debugDest_ifNonNull));
   }
   public static final FileTextTemplate newFromPathAndUserExtraGaps(String path, String path_name, UserExtraGapGetter userExtra_getter)  {
      return  (new FileTextTemplate(newTemplateFromPath(path, path_name), path, userExtra_getter));
   }
}
class FilePathGap extends CodeletGap  {
   public FilePathGap()  {
      super("path");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getFilePath(instance);
   }
}
class FileBaseDirGap extends CodeletGap  {
   public FileBaseDirGap()  {
      super("base_dir");
   }
   public String getFillText(CodeletInstance ignored)  {
      return  CodeletBaseConfig.getExampleSourceBaseDir();
   }
}
class FileNameNoExtGap extends CodeletGap  {
   public FileNameNoExtGap()  {
      super("file_name_no_ext");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getFileNameWithoutExtension(instance);
   }
}
class FileNameWithExtGap extends CodeletGap  {
   public FileNameWithExtGap()  {
      super("file_name");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getFileNameWithExtension(instance);
   }
}
class RelPathAsUrlGap extends CodeletGap  {
   public RelPathAsUrlGap()  {
      super("rel_path_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleClassFQName(instance).replace(".", "/");
   }
}
class RelativeDirGap extends CodeletGap  {
   public RelativeDirGap()  {
      super("file_name_dir");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getExampleClassFQName(instance).replace(".", FILE_SEP);
   }
}
class JavaDocFileUrlGap extends CodeletGap  {
   public JavaDocFileUrlGap()  {
      super("javadoc_path_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getJavaDocSourceUrl(instance);
   }
}
class RelDirGap extends CodeletGap  {
   public RelDirGap()  {
      super("relative_dir");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getFilePath(instance);
   }
}
class RelDirAsUrlGap extends CodeletGap  {
   public RelDirAsUrlGap()  {
      super("relative_dir_as_url");
   }
   public String getFillText(CodeletInstance instance)  {
      return  TagletTextUtil.getFilePath(instance).replace(".", "/");
   }
}
