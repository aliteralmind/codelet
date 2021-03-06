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
   <p>The text into which {@code {@.codelet}} output (the example-code's source) is placed.</p>

   <A NAME="gaps"></a><h2><a href="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: {@code {@.codelet}}: Template: <u>Gap names</u></h2>

   <p>The only required gap is &quot;{@link #BODY_GAP_NAME body}&quot;, which is where the fully-processed source-code is placed.</p>

   <p>In addition to any {@linkplain com.github.aliteralmind.codelet.UserExtraGapGetter#getForCodeletDotOut() user-extra} gaps, the {@code {@.codelet}} template also provides for the following &quot;default&quot; optional gaps:</p>

   <p><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
   </TR><TR>
      <TD>{@link #GAP_COMMAND_LINE_PARAMS command_line_params}</TD>
      <TD>Parameters as passed to the example code's main function.</TD>
   </TR><TR>
      <TD>{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SIMPLE_CLASS_NAME simple_class_name}*</TD>
      <TD>The example code's non-fully-qualified class name.</TD>
   </TR><TR>
      <TD>{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_FQ_CLASS_NAME fq_class_name}*</TD>
      <TD>The example code's fully-qualified class name.</TD>
   </TR><TR>
      <TD>{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_PACKAGE package}*</TD>
      <TD>The example code's package name.</TD>
   </TR></TABLE></p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class ConsoleOutTemplate extends OnlyOneBodyGapTemplateBase  {
   /**
      <p>Parameters as passed to the example code's main function--Name is {@code "command_line_params"}.</p>


      <p>Gap is {@linkplain com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) filled} with</p>

<blockquote><pre>................</pre></blockquote>

    * @see  <a href="#gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a>
    */
   public static final CodeletGap GAP_COMMAND_LINE_PARAMS = new CmdLineParamsGap();
   /**
      <p>Create the first instance.</p>

    * <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(CodeletType, FeatherTemplate, String, CodeletGap[], UserExtraGapGetter) super}({@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE SOURCE_CODE}, template, tmpl_path,
   new CodeletGap[] {
      {@link com.github.aliteralmind.codelet.type.SourceCodeTemplate SourceCodeTemplate}.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_PACKAGE GAP_PACKAGE},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_SIMPLE_CLASS_NAME GAP_SIMPLE_CLASS_NAME},
      SourceCodeTemplate.{@link com.github.aliteralmind.codelet.type.SourceCodeTemplate#GAP_FQ_CLASS_NAME GAP_FQ_CLASS_NAME},
      {@link #GAP_COMMAND_LINE_PARAMS}},
   userExtra_getter)</pre></blockquote>
    * @see  #ConsoleOutTemplate(ConsoleOutTemplate, FeatherTemplate, String)
    */
   public ConsoleOutTemplate(FeatherTemplate template, String tmpl_path, UserExtraGapGetter userExtra_getter)  {
      super(CodeletType.SOURCE_CODE, template, tmpl_path,
         new CodeletGap[] {
            SourceCodeTemplate.GAP_PACKAGE,
            SourceCodeTemplate.GAP_SIMPLE_CLASS_NAME,
            SourceCodeTemplate.GAP_FQ_CLASS_NAME,
            GAP_COMMAND_LINE_PARAMS},
         userExtra_getter);
   }
   /**
      <p>Create the second or subsequent instance.</p>

    * <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase#OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase, FeatherTemplate, String) super}(to_copy, template, tmpl_path)</pre></blockquote>

    * @see  #ConsoleOutTemplate(FeatherTemplate, String, UserExtraGapGetter)
    */
   public ConsoleOutTemplate(ConsoleOutTemplate to_copy, FeatherTemplate template, String tmpl_path)  {
      super(to_copy, template, tmpl_path);
   }
   public ConsoleOutTemplate(ConsoleOutTemplate to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy, debugDest_ifNonNull);
   }
   public ConsoleOutTemplate fillBody(String fully_processed)  {
      fillBodyGap(fully_processed);
      return  this;
   }
   /**
      <p>Duplicate this template.</p>

    * @return  <code>(new {@link #ConsoleOutTemplate(ConsoleOutTemplate, Appendable) ConsoleOutTemplate}(this, debugDest_ifNonNull))</code>
    */
   public ConsoleOutTemplate getObjectCopy(Appendable debugDest_ifNonNull)  {
      return  (new ConsoleOutTemplate(this, debugDest_ifNonNull));
   }
   public static final ConsoleOutTemplate newFromPathAndUserExtraGaps(String path, String path_name, UserExtraGapGetter userExtra_getter)  {
      return  (new ConsoleOutTemplate(newTemplateFromPath(path, path_name), path, userExtra_getter));
   }
}
class CmdLineParamsGap extends CodeletGap  {
   public CmdLineParamsGap()  {
      super("command_line_params");
   }
   public String getFillText(CodeletInstance instance)  {
      return  "CmdLineParamsGap.getFillText(ci): FIX ME!";
   }
}
