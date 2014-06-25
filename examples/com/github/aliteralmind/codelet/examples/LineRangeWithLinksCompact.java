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
package  com.github.aliteralmind.codelet.examples;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CustomizationInstructions;
   import  com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil;
   import  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum;
   import  com.github.aliteralmind.codelet.alter.NewLineAltererFor;
   import  com.github.aliteralmind.codelet.NewLineFilterFor;
   import  com.github.aliteralmind.codelet.examples.adder.Adder;
   import  com.github.aliteralmind.codelet.type.SourceCodeTemplate;
   import  com.github.xbn.analyze.alter.ExpirableElements;
   import  com.github.xbn.analyze.alter.MultiAlterType;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.linefilter.alter.NewTextLineAltererFor;
   import  com.github.xbn.linefilter.alter.TextLineAlterer;
   import  com.github.xbn.linefilter.FilteredLineIterator;
   import  java.util.regex.Pattern;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P style="font-size: 150%;"><B><A HREF="{@docRoot}/overview-summary.html#xmpl_links"><FONT SIZE="+1"><CODE><IMG SRC="{@docRoot}/resources/left_arrow.gif"/> GO BACK</CODE></FONT></A> &nbsp; &nbsp; Codelet: Example: Advanced customization: Making relevant functions into clickable JavaDoc links</B></P>

   <H3><U>Taglet:</U></H3>

   <P style="font-size: 125%;"><B>{@code {@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo:adderDemo_lineSnippetWithLinks()}}</B></P>

   <H3><U>Replaced with:</U></H3>

   <P><I>(Output begins and ends between the horizontal lines.)</I></P>

   <HR/>
{@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo:adderDemo_lineSnippetWithLinks()}
   <HR/>

   <H3><U>Original source code:</U></H3>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo}

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class LineRangeWithLinksCompact  {
   private static final CustomizationInstructions<SourceCodeTemplate> adderDemo_lineSnippetWithLinks(CodeletInstance instance, CodeletType needed_defaultAlterType)  {
      FilteredLineIterator filter = NewLineFilterFor.lineRange(
         1, false, "Adder adder", null, null,
         2, false, "println(adder", null, null,
         null, null, null);

      TextLineAlterer[] alterers = {
         NewTextLineAltererFor.escapeHtml(),
         NewLineAltererFor.eliminateIndentationOrNull("^      ", null),
         NewJDLinkForWordOccuranceNum.constructor(instance,
            1, Adder.class, "(*)", null, null, null, null),
         NewJDLinkForWordOccuranceNum.method(instance,
            1, Adder.class, "getSum()", null, null, null, null)};

      return  new CustomizationInstructions<SourceCodeTemplate>(instance,
            needed_defaultAlterType).
         filter(filter).
         orderedAlterers(null, NullElement.OK, ExpirableElements.OPTIONAL,
            MultiAlterType.CUMULATIVE, alterers).
         defaultOrOverrideTemplate(null).
         build();
   }  //End snippet

   private static final CustomizationInstructions<SourceCodeTemplate> LineRangeWithLinksCompact_adderDemo_lineRangeWithLinks(CodeletInstance instance, CodeletType needed_defaultAlterType, Boolean do_highlightDbgOnOff)  {
      String debugPrefix = "LineRangeWithLinksCompact.LineRangeWithLinksCompact_adderDemo_lineRangeWithLinks";
      FilteredLineIterator filter = NewLineFilterFor.lineRangeWithReplace(instance,
         debugPrefix,
                   //Chopped in half, so this string isn't mistaken for the match
         1, true, "(<SourceCodeTemplate> ad"+"derDemo_lineSnippetWithLinks)", "$1",
               ReplacedInEachInput.FIRST,
         1, true, "\\} +//End snippet$", "}", ReplacedInEachInput.FIRST);

      TextLineAlterer[] alterers = {
         NewTextLineAltererFor.escapeHtml(),
         NewLineAltererFor.eliminateIndentationOrNull("^   ", null),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, NewLineFilterFor.class, "lineRange(int, *)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, NewLineAltererFor.class, "eliminateIndentationOrNull(*)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, NewJDLinkForWordOccuranceNum.class, "constructor(*, Appendable)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, NewJDLinkForWordOccuranceNum.class, "method(*, String, Appendable, *)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, CustomizationInstructions.class, "filter(*)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, CustomizationInstructions.class, "orderedAlterers(*)"),
         NewJDLinkForWordOccuranceNum.method(instance, debugPrefix, null,
            1, CustomizationInstructions.class, "defaultOrOverrideTemplate(*)"),
         ((!do_highlightDbgOnOff) ? null
            :  NewTextLineAltererFor.replacement(
                  Pattern.compile("(on=System.out, off=null)"), "<span style=\"background-color: #FFFF00\">$1</span>",
                  ReplacedInEachInput.FIRST,
                  getDebugApblIfOn(instance, debugPrefix + ".highlightonoff"),
                  null))};
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
}
