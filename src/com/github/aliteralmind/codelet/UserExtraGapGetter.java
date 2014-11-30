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
/**
   <p>Defines &quot;user-created extra&quot; gaps that may be added to Codelet templates. The fully-qualified class name of your implementation is specified in the {@link com.github.aliteralmind.codelet.CodeletBaseConfig#USER_EXTRA_GAPS_CLASS_NAME user_extra_gaps_class} configuration variable.</p>

   <p><b>Implementation requirements:</b><ul>
   	<li>All functions must return a non-{@code null} array, where no element is {@code null}, and every {@linkplain com.github.xbn.keyed.Named#getName() name} is unique and not equal to any of the required or &quot;optional-default&quot; gaps for its Codelet type.</li>
   	<li>There must be a no-parameter constructor.</li>
   </ul>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public interface UserExtraGapGetter  {
   /**
   	<p>Extra gap-filler objects for (source-code) {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE {@.codelet}} taglets, when {@linkplain UserExtraGapGetter configured}.</p>

   	@return  A non-{@code null} array, where no element is {@code null}, and every {@linkplain com.github.xbn.keyed.Named#getName() name} is unique and not equal to any of the <a href="{@docRoot}/com/github/aliteralmind.codelet/type/SourceCodeTemplate.html">required or &quot;optional-default&quot; gaps</a> for this Codelet type.
   	@see  #getForCodeletDotOut()
   	@see  #getForCodeletAndOut()
   	@see  #getForFileTextlet()
    **/
   CodeletGap[] getForSourceCodelet();
   /**
   	<p>Extra gap-filler objects for {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} taglets, when {@linkplain UserExtraGapGetter configured}.</p>

   	@return  A non-{@code null} array, where no element is {@code null}, and every {@linkplain com.github.xbn.keyed.Named#getName() name} is unique and not equal to any of the <a href="{@docRoot}/com/github/aliteralmind.codelet/type/ConsoleOutTemplate.html">required or &quot;optional-default&quot; gaps</a> for this Codelet type.
   	@see  #getForSourceCodelet()
    **/
   CodeletGap[] getForCodeletDotOut();
   /**
   	<p>Extra gap-filler objects for {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets, when {@linkplain UserExtraGapGetter configured}.</p>

   	@return  A non-{@code null} array, where no element is {@code null}, and every {@linkplain com.github.xbn.keyed.Named#getName() name} is unique and not equal to any of the <a href="{@docRoot}/com/github/aliteralmind.codelet/type/SourceAndOutTemplate.html">required or &quot;optional-default&quot; gaps</a> for this Codelet type.
   	@see  #getForSourceCodelet()
    **/
   CodeletGap[] getForCodeletAndOut();
   /**
   	<p>Extra gap-filler objects for {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}} taglets, when {@linkplain UserExtraGapGetter configured}.</p>

   	@return  A non-{@code null} array, where no element is {@code null}, and every {@linkplain com.github.xbn.keyed.Named#getName() name} is unique and not equal to any of the <a href="{@docRoot}/com/github/aliteralmind.codelet/type/FileTextTemplate.html">required or &quot;optional-default&quot; gaps</a> for this Codelet type.
   	@see  #getForSourceCodelet()
    **/
   CodeletGap[] getForFileTextlet();
}
