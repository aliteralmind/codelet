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
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  com.github.aliteralmind.codelet.UserExtraGapGetter;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CodeletGap;
   import  com.github.aliteralmind.codelet.CodeletTemplateBase;
/**
   <p>For all templates but {@code {@.codelet.and.out}}.</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public abstract class OnlyOneBodyGapTemplateBase extends CodeletTemplateBase  {
   /**
      <p>The gap name for the fully processed source-code, which is the <i>only</i> gap required to exist in the template--equal to {@code "body"}.</p>

      @see  <a href="SourceCodeTemplate.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a>
      @see  #fillBodyGap(String, String)
    **/
   public static final String BODY_GAP_NAME = "body";
   /**
      <p>Create the first instance.</p>

      <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#CodeletTemplateBase(CodeletType, FeatherTemplate, String, String[], CodeletGap[], UserExtraGapGetter) super}(type, template, tmpl_path, new String[]{{@link #BODY_GAP_NAME}}, optional_defaultGaps, userExtra_getter)</pre></blockquote>

      @see  #OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase, FeatherTemplate, String)
    **/
   public OnlyOneBodyGapTemplateBase(CodeletType type, FeatherTemplate template, String tmpl_path, CodeletGap[] optional_defaultGaps, UserExtraGapGetter userExtra_getter)  {
      super(type, template, tmpl_path, new String[]{BODY_GAP_NAME}, optional_defaultGaps, userExtra_getter);
   }
   /**
      <p>Create the second or subsequent instance.</p>

      <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#CodeletTemplateBase(CodeletTemplateBase, FeatherTemplate, String) super}(to_copy, template, tmpl_path)</pre></blockquote>

      @see  #OnlyOneBodyGapTemplateBase(CodeletType, FeatherTemplate, String, CodeletGap[], UserExtraGapGetter)
    **/
   public OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase to_copy, FeatherTemplate template, String tmpl_path)  {
      super(to_copy, template, tmpl_path);
   }
   public OnlyOneBodyGapTemplateBase(OnlyOneBodyGapTemplateBase to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy, debugDest_ifNonNull);
   }
   /**
      <p>Fill the fully-processed text into the body gap.</p>

      <p>Equal to</p>

<blockquote><pre>{@link com.github.aliteralmind.codelet.CodeletTemplateBase#fillBodyGap(String, String) fillBodyGap}*({@link #BODY_GAP_NAME}, body_text)</pre></blockquote>
    **/
   public final void fillBodyGap(String body_text)  {
      fillBodyGap(BODY_GAP_NAME, body_text);
   }
   public static final FeatherTemplate newTemplateFromPath(String path, String path_name)  {
      return  CodeletTemplateBase.newTemplateFromPath(path, path_name, BODY_GAP_NAME);
   }
}
