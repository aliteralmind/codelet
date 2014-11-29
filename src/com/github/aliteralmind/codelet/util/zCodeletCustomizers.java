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
package  com.github.aliteralmind.codelet.util;
	import  com.github.aliteralmind.codelet.CodeletInstance;
	import  com.github.aliteralmind.codelet.CodeletType;
	import  com.github.aliteralmind.codelet.CustomizationInstructions;
	import  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum;
	import  com.github.aliteralmind.codelet.NewLineFilterFor;
	import  com.github.xbn.linefilter.alter.NewTextLineAltererFor;
	import  com.github.xbn.linefilter.alter.TextLineAlterer;
	import  com.github.xbn.linefilter.FilteredLineIterator;
	import  com.github.aliteralmind.codelet.type.SourceCodeTemplate;
	import  com.github.xbn.analyze.alter.ExpirableElements;
	import  com.github.xbn.analyze.alter.MultiAlterType;
	import  com.github.xbn.array.NullElement;
	import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
	<p>Custom <a href="{@docRoot}/com/github/aliteralmind/codelet/CustomizationInstructions.html#overview">customizers</a> used throughout {@code com.github.aliteralmind.codelet.util}.</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class zCodeletCustomizers  {
	private static final CustomizationInstructions<SourceCodeTemplate> getSourceConfig_FunctionConstructorJavaDocLink_JavaDocUtil(CodeletInstance instance, CodeletType needed_defaultAlterType)  {
		String debugPrefix = "JavaDocUtil.FunctionConstructorJavaDocLink";

		FilteredLineIterator filter = NewLineFilterFor.
			eliminateAllCmtBlocksAndPackageLine(
				instance, debugPrefix, true, true);

		TextLineAlterer[] alterers = {
			NewTextLineAltererFor.escapeHtml(),
			NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
				1, JavaDocUtil.class, "getUrlToConstructor(*)"),
			NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
				1, JavaDocUtil.class, "getUrlToMethod(*)")};

		return  new CustomizationInstructions<SourceCodeTemplate>(instance,
				needed_defaultAlterType).
			filter(filter).
			orderedAlterers(
				getDebugApblIfOn(instance, debugPrefix + ".allalterer"),
				NullElement.OK, ExpirableElements.OPTIONAL,
				MultiAlterType.CUMULATIVE, alterers).
			defaultOrOverrideTemplate(
				getDebugApblIfOn(instance, debugPrefix + ".template")).
			build();
	}
	private static final CustomizationInstructions<SourceCodeTemplate> getSourceConfig_JavaDocUtilGetEllipsisArrayLastParamXmpl(CodeletInstance instance, CodeletType needed_defaultAlterType)  {
		String debugPrefix = "JavaDocUtil.GetEllipsisArrayLastParamXmpl";

		FilteredLineIterator filter = NewLineFilterFor.
			eliminateAllCmtBlocksAndPackageLine(
				instance, debugPrefix, true, true);

		TextLineAlterer[] alterers = {
			NewTextLineAltererFor.escapeHtml(),
			NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
				1, JavaDocUtil.class, "getEllipsisArrayLastParam(*)")};

		return  new CustomizationInstructions<SourceCodeTemplate>(instance,
				needed_defaultAlterType).
			filter(filter).
			orderedAlterers(
				getDebugApblIfOn(instance, debugPrefix + ".allalterer"),
				NullElement.OK, ExpirableElements.OPTIONAL,
				MultiAlterType.CUMULATIVE, alterers).
			defaultOrOverrideTemplate(
				getDebugApblIfOn(instance, debugPrefix + ".template")).
			build();
	}
	private zCodeletCustomizers()  {
		throw  new IllegalStateException("Do not instantiate.");
	}
}