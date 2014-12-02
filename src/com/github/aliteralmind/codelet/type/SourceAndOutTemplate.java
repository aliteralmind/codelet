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
   import  com.github.aliteralmind.codelet.UserExtraGapGetter;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CodeletGap;
   import  com.github.aliteralmind.codelet.CodeletTemplateBase;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
/**
   <p>The text into which {@code {@.codelet.and.out}} output (the example-code's console output) is placed.</p>

   <A NAME="gaps"></a><h2><a href="{@docRoot}/com/github/aliteralmind/codelet/CodeletTemplateBase.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: {@code {@.codelet.and.out}}: Template: <u>Gap names</u></h2>

   <p>The only required gaps are &quot;{@link #GAP_BODY_SOURCE_CODE body_source_code}&quot; and &quot;{@link #GAP_BODY_CONSOLE_OUTPUT body_console_output}&quot;, which are where the fully-processed source and output are placed.</p>

   <p>In addition to any {@linkplain com.github.aliteralmind.codelet.UserExtraGapGetter#getForCodeletAndOut() user-extra} gaps {@code {@.codelet.and.out}} template provides for the same &quot;default&quot; optional gaps as made available in <i>both</i> {@link com.github.aliteralmind.codelet.type.SourceCodeTemplate} and {@link com.github.aliteralmind.codelet.type.SourceAndOutTemplate}.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class SourceAndOutTemplate extends CodeletTemplateBase  {
   /**
      <p>The gap name for the fully processed source-code, which is one of two gaps required to exist in the template--equal to {@code "body_source_code"}.</p>

    * @see  <a href="#gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a>
    * @see  #GAP_BODY_CONSOLE_OUTPUT
    * @see  #fillBodyGap(String, String)
    */
   public static final String GAP_BODY_SOURCE_CODE = "body_source_code";
   /**
      <p>The gap name for the fully processed console output, which is one of two gaps required to exist in the template--equal to {@code "body_console_output"}.</p>

    * @see  <a href="#gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a>
    * @see  #GAP_BODY_SOURCE_CODE
    * @see  #fillBodyGap(String, String)
    */
   public static final String GAP_BODY_CONSOLE_OUTPUT = "body_console_output";
   /**
      <p>Create the first instance.</p>

    * <p>Equal to</p>

<blockquote><pre>
{@link com.github.aliteralmind.codelet.CodeletTemplateBase#CodeletTemplateBase(CodeletType, FeatherTemplate, String, String[], CodeletGap[], UserExtraGapGetter) super}({@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT SOURCE_AND_OUT}, template, tmpl_path
   new String[]{{@link #GAP_BODY_SOURCE_CODE}, {@link #GAP_BODY_CONSOLE_OUTPUT}},
   new CodeletGap[] {
      {@link com.github.aliteralmind.codelet.type.SourceCodeTemplate ConsoleOutTemplate.GAP_COMMAND_LINE_PARAMS}.{@link com.github.aliteralmind.codelet.type.ConsoleOutTemplate#GAP_COMMAND_LINE_PARAMS GAP_COMMAND_LINE_PARAMS},
      {@link com.github.aliteralmind.codelet.type.SourceCodeTemplate}.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_FQ_CLASS_NAME GAP_FQ_CLASS_NAME},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_FQ_CLASS_NAME_DIR GAP_FQ_CLASS_NAME_DIR},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_FQ_CLASS_NAME_URL GAP_FQ_CLASS_NAME_URL},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_JAVADOC_URL GAP_JAVADOC_URL},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_PACKAGE GAP_PACKAGE},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_PACKAGE_DIR GAP_PACKAGE_DIR},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_PACKAGE_URL GAP_PACKAGE_URL},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SIMPLE_CLASS_NAME GAP_SIMPLE_CLASS_NAME},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SOURCE_URL GAP_SOURCE_URL},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SRC_BASE_DIR GAP_SRC_BASE_DIR},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SRC_PATH GAP_SRC_PATH}},
   userExtra_getter)
</pre></blockquote>

    * @see  #SourceAndOutTemplate(SourceAndOutTemplate, FeatherTemplate, String)
    */
   public SourceAndOutTemplate(FeatherTemplate template, String tmpl_path, UserExtraGapGetter userExtra_getter)  {
      super(CodeletType.SOURCE_AND_OUT, template, tmpl_path,
         new String[]{GAP_BODY_SOURCE_CODE, GAP_BODY_CONSOLE_OUTPUT},
         new CodeletGap[] {
            ConsoleOutTemplate.GAP_COMMAND_LINE_PARAMS,
            SourceCodeTemplate.GAP_FQ_CLASS_NAME,
            SourceCodeTemplate.GAP_FQ_CLASS_NAME_DIR,
            SourceCodeTemplate.GAP_FQ_CLASS_NAME_URL,
            SourceCodeTemplate.GAP_JAVADOC_URL,
            SourceCodeTemplate.GAP_PACKAGE,
            SourceCodeTemplate.GAP_PACKAGE_DIR,
            SourceCodeTemplate.GAP_PACKAGE_URL,
            SourceCodeTemplate.GAP_SIMPLE_CLASS_NAME,
            SourceCodeTemplate.GAP_SOURCE_URL,
            SourceCodeTemplate.GAP_SRC_BASE_DIR,
            SourceCodeTemplate.GAP_SRC_PATH},
         userExtra_getter);
   }
   /**
      <p>Create the second or subsequent instance.</p>

    * <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#CodeletTemplateBase(CodeletTemplateBase, FeatherTemplate, String) super}(to_copy, template, tmpl_path)</pre></blockquote>

    * @see  #SourceAndOutTemplate(FeatherTemplate, String, UserExtraGapGetter)
    */
   public SourceAndOutTemplate(SourceAndOutTemplate to_copy, FeatherTemplate template, String tmpl_path)  {
      super(to_copy, template, tmpl_path);
   }
   public SourceAndOutTemplate(SourceAndOutTemplate to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy, debugDest_ifNonNull);
   }
   /**
      <p>Fill the fully-processed source-code body gap.</p>

    * <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#fillBodyGap(String, String) fillBodyGap}({@link #GAP_BODY_SOURCE_CODE}, body_text)*</pre></blockquote>

    * @see  #fillConsoleOutputBody(String)
    */
   public final SourceAndOutTemplate fillSourceCodeBody(String body_text)  {
      fillBodyGap(GAP_BODY_SOURCE_CODE, body_text);
      return  this;
   }
   /**
      <p>Fill the fully-processed console-output body gap.</p>

    * <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#fillBodyGap(String, String) fillBodyGap}*({@link #GAP_BODY_CONSOLE_OUTPUT}, body_text)</pre></blockquote>

    * @see  #fillSourceCodeBody(String)
    */
   public final SourceAndOutTemplate fillConsoleOutputBody(String body_text)  {
      fillBodyGap(GAP_BODY_CONSOLE_OUTPUT, body_text);
      return  this;
   }
   /**
      <p>Duplicate this template.</p>

    * @return  <code>(new {@link #SourceAndOutTemplate(SourceAndOutTemplate, Appendable) SourceAndOutTemplate}(this, debugDest_ifNonNull))</code>
    */
   public SourceAndOutTemplate getObjectCopy(Appendable debugDest_ifNonNull)  {
      return  (new SourceAndOutTemplate(this, debugDest_ifNonNull));
   }
   public static final SourceAndOutTemplate newFromPathAndUserExtraGaps(String path, String path_name, UserExtraGapGetter userExtra_getter)  {
      return  (new SourceAndOutTemplate(newTemplateFromPath(path, path_name), path, userExtra_getter));
   }
   public static final FeatherTemplate newTemplateFromPath(String path, String path_name)  {
      return  CodeletTemplateBase.newTemplateFromPath(path, path_name, GAP_BODY_SOURCE_CODE, GAP_BODY_CONSOLE_OUTPUT);
   }
}
