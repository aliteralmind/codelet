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
   import  com.github.aliteralmind.codelet.alter.NewLineFilterFor;
   import  com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil;
   import  com.github.aliteralmind.codelet.alter.NewLineAltererFor;
   import  com.github.aliteralmind.codelet.alter.NewLineFilterFor;
   import  com.github.aliteralmind.codelet.linefilter.BlockMarks;
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor;
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineFilterFor;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAlterer;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.xbn.analyze.alter.ExpirableElements;
   import  com.github.xbn.analyze.alter.MultiAlterType;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.IllegalArgumentStateException;
   import  com.github.xbn.regexutil.IgnoreCase;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.util.DefaultValueFor;
   import  com.github.xbn.util.EnumUtil;
   import  java.util.List;
   import  java.util.regex.Pattern;
   import  java.util.regex.PatternSyntaxException;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Pre-made Codelet customizers.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class BasicCustomizers  {
   /**
      <P><I>Text snippet:</I> Eliminates all lines except those between a specific start and end line, based on text found in those (start and end) lines.</P>

      <H4>Example</H4>

      <BLOCKQUOTE>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo:lineRange(1, false, "Adder adder", 2, false, "println(adder.getSum())", "^      ")}}</BLOCKQUOTE>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo:lineRange(1, false, "Adder adder", 2, false, "println(adder.getSum())", "^      ")}

      <P>Starts with the line containing {@code "Adder adder"}, and ends with the <I>second</I> line containing {@code "println(adder.getSum())"}. This also deletes the indentation of the kept lines, which is two tabs at <A HREF="http://www.regular-expressions.info/anchors.html">line-start</A>: {@code "^      "}. <I>{@linkplain examples.LineRange View example}</I></P>

      <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE level debugging}</H4>

      <P>{@code zzBasicCustomizers.lineRange}<UL>
         <LI>{@code .allalterer}: {@linkplain com.github.aliteralmind.codelet.linefilter#AllTextLineAlterer(TextLineAlterer[], Appendable) All alterers}, as a whole.</LI>
         <LI>{@code .elimindent}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) eliminateIndentationOrNull}</CODE></LI>
         <LI>{@code filter}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable) lineRange}.dbgFilter_ifNonNull</CODE><UL>
            <LI>{@code .endalterer}: <CODE>NewLineFilterFor.lineRange.dbgEnd_ifNonNull</CODE></LI>
            <LI>{@code .endvalidfilter}: <CODE>NewLineFilterFor.lineRange.dbgEndFilter_ifNonNull</CODE></LI>
            <LI>{@code .startalterer}: <CODE>NewLineFilterFor.lineRange.dbgStart_ifNonNull</CODE></LI>
            <LI>{@code .startvalidfilter}: <CODE>NewLineFilterFor.lineRange.dbgStartFilter_ifNonNull</CODE></LI>
         </UL></LI>
         <LI>{@code template}: The {@linkplain TagletOfTypeProcessor#getTemplateFromInstructionsOverrideOrDefault(CustomizationInstructions) template}</LI>
      </UL></P>

      @param  instance  May not be {@code null}. <I>When calling this customizer from a taglet, exclude this parameter. All other parameters are required.</I>
      @param  startAppearance_num  If the text to be found ({@code startLine_findWhat}) exists in multiple lines, this is the one to use. Must be greater than zero (although {@code -1}, meaning "ignore this setting", is equivalent to {@code 1}: the first appearance).
      @param  is_startLineRegex  If {@code true} then {@code startLine_findWhat} is treated as a regular expression. Otherwise, {@code startLine_findWhat} is treated as a literal.
      @param  startLine_findWhat  The search term that defines the line-range start line. If a regex, this may or may not include start or end-of-line anchors ({@code '^'} and {@code '$'}) (matching is done with <CODE>{@link java.util.regex.Matcher Matcher}.{@link java.util.regex.Matcher#find() find}()</CODE>).
      @param  endAppearance_num  The appearance number of the end-line search term. If it happens to exist before the start-line search-term, then this parameter must be at least equal to two.
      @param  is_endLineRegex  If {@code true} then {@code endLine_findWhat} is treated as a regular expression.
      @param  endLine_findWhat  The search term that defines the line-range end line.
      @param  indentRegexToElim_emptyStrIfNone  The regular expression representing the indentation that should be eliminated from the line-range. Since these lines exist in the middle of a file, they may be {@linkplain com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) extra-indented}.
      @return  <CODE>CustomizationInstructions.&lt;T&gt;newForMaybeElimIndent_tabToSpcEscHtml(instance, filter, indentRegexToElim_emptyStrIfNone)</CODE>
      <BR>Where {@code filter} is created with
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor NewLineFilterFor}.{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable) lineRange}</CODE>
      @see  #lineRangeWithReplace(CodeletInstance, CodeletType, Integer, Boolean, String, String, String, Integer, Boolean, String, String, String, String) lineRangeWithReplace
    **/
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> lineRange(CodeletInstance instance, CodeletType needed_defaultAlterType, Integer startAppearance_num, Boolean is_startLineRegex, String startLine_findWhat, Integer endAppearance_num, Boolean is_endLineRegex, String endLine_findWhat, String indentRegexToElim_emptyStrIfNone) throws PatternSyntaxException  {
      //Named debuggers appendables
         String debugPrefix = "zzBasicCustomizers.lineRange";

      TextLineFilter filter = NewLineFilterFor.lineRange(
         instance, debugPrefix,
         startAppearance_num, is_startLineRegex, startLine_findWhat,
         endAppearance_num, is_endLineRegex, endLine_findWhat);

      TextLineAlterer[] alterers = DefaultAlterGetterUtil.
         getAlterArrayWithDefaultAlterersAdded(
            needed_defaultAlterType,
            NewLineAltererFor.eliminateIndentationOrNull(
               indentRegexToElim_emptyStrIfNone,
               getDebugApblIfOn(instance, debugPrefix + ".elimindent")));

      return  new CustomizationInstructions<T>(instance, needed_defaultAlterType).filter(filter).
         orderedAlterers(
            getDebugApblIfOn(instance, debugPrefix + ".allalterer"),
            NullElement.BAD, ExpirableElements.OPTIONAL,
            MultiAlterType.CUMULATIVE, alterers).
         defaultOrOverrideTemplate(
            getDebugApblIfOn(instance, debugPrefix + ".template")).
         build();
   }
   /**
      <P><I>Text snippet:</I> Eliminates all lines except those between a specific start and end line, based on text found in those (start and end) lines--also makes replacements on the start and end lines only. This is useful when lines must be marked, but those marks should not be seen in the final output.</P>

      <H4>Example</H4>

      <BLOCKQUOTE>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithSnippetEndMarker:lineRangeWithReplace(1, true, "(Adder adder)", "$1", "FIRST", 1, true, "; +//End snippet$", ";", "FIRST", "^      ")}}</BLOCKQUOTE>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithSnippetEndMarker:lineRangeWithReplace(1, true, "(Adder adder)", "$1", "FIRST", 1, true, "; +//End snippet$", ";", "FIRST", "^      ")}

      <P>Snippet starts with the line containing {@code "Adder adder"}, which is replaced with itself: {@code "$1"}. The last line in the range ends the regular expression {@code "; +//End snippet$"} which is replaced with a semi-colon. This also deletes the indentation of the kept lines, which is two tabs at <A HREF="http://www.regular-expressions.info/anchors.html">line-start</A>: {@code "^      "}. <I>{@linkplain examples.LineRangeWithReplace View example}</I></P>

      <P>This only documents things that are different from {@link #lineRange(CodeletInstance, CodeletType, Integer, Boolean, String, Integer, Boolean, String, String) lineRange}.</P>

      <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE level debugging}</H4>

      <P>{@code zzBasicCustomizers.lineRangeWithReplace}<UL>
         <LI>{@code allalterer}: {@linkplain com.github.aliteralmind.codelet.linefilter#AllTextLineAlterer(TextLineAlterer[], Appendable) All alterers}, as a whole.</LI>
         <LI>{@code .elimindent}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) eliminateIndentationOrNull}</CODE></LI>
         <LI>{@code filter}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable) lineRange}.dbgFilter_ifNonNull</CODE><UL>
            <LI>{@code .endalterer}: <CODE>NewLineFilterFor.lineRange.dbgEnd_ifNonNull</CODE></LI>
            <LI>{@code .endvalidfilter}: <CODE>NewLineFilterFor.lineRange.dbgEndFilter_ifNonNull</CODE></LI>
            <LI>{@code .startalterer}: <CODE>NewLineFilterFor.lineRange.dbgStart_ifNonNull</CODE></LI>
            <LI>{@code .startvalidfilter}: <CODE>NewLineFilterFor.lineRange.dbgStartFilter_ifNonNull</CODE></LI>
         </UL></LI>
         <LI>{@code template}: The {@linkplain TagletOfTypeProcessor#getTemplateFromInstructionsOverrideOrDefault(CustomizationInstructions) template}</LI>
      </UL></P>

      @param  startLine_findWhat  The text or match that defines the line-range start line. To search for text only (make no replacement), either set<UL>
         <LI>{@code is_startLineRegex} to {@code false}, and</LI>
         <LI>both {@code startLine_findWhat} ({@code "text to find"}) and {@code startLine_rplcWith} to the same text ({@code "text to find"})</LI>
      </UL>or<UL>
         <LI>{@code is_startLineRegex} to {@code true},</LI>
         <LI>{@code startLine_findWhat} to the expected text surrounded by a <A HREF="http://stackoverflow.com/questions/21880127/have-trouble-understanding-capturing-groups-and-back-references">capture group</A> ({@code "(text to find)"}), and</LI>
         <LI>{@code startLine_rplcWith} to {@code "$1"}</LI>
      </UL>
      @param  startRplcWhat_notMatchNums  In most cases, this should be set to {@code "FIRST"}. See <CODE>com.github.xbn.regexutil.{@link com.github.xbn.regexutil.ReplacedInEachInput}</CODE>.
    **/
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> lineRangeWithReplace(CodeletInstance instance, CodeletType needed_defaultAlterType, Integer startAppearance_num, Boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, String startRplcWhat_notMatchNums, Integer endAppearance_num, Boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, String endRplcWhat_notMatchNums, String indentRegexToElim_emptyStrIfNone) throws PatternSyntaxException  {
      String debugPrefix = "zzBasicCustomizers.lineRangeWithReplace";

      ReplacedInEachInput rplcsStart = EnumUtil.toValueWithNullDefault(startRplcWhat_notMatchNums, "startRplcWhat_notMatchNums", IgnoreCase.NO, DefaultValueFor.NOTHING, ReplacedInEachInput.ALL);
      ReplacedInEachInput rplcsEnd = EnumUtil.toValueWithNullDefault(endRplcWhat_notMatchNums, "startRplcWhat_notMatchNums", IgnoreCase.NO, DefaultValueFor.NOTHING, ReplacedInEachInput.ALL);

      TextLineFilter filter = NewLineFilterFor.lineRangeWithReplace(
         instance, debugPrefix,
         startAppearance_num, is_startLineRegex,
         startLine_findWhat, startLine_rplcWith, rplcsStart,
         endAppearance_num, is_endLineRegex,
         endLine_findWhat, endLine_rplcWith, rplcsEnd);

      TextLineAlterer[] alterers = DefaultAlterGetterUtil.getAlterArrayWithDefaultAlterersAdded(
         needed_defaultAlterType,
         NewLineAltererFor.eliminateIndentationOrNull(
            indentRegexToElim_emptyStrIfNone,
            getDebugApblIfOn(instance, debugPrefix + ".elimindent")));

      return  new CustomizationInstructions<T>(instance, needed_defaultAlterType).filter(filter).
         orderedAlterers(
            getDebugApblIfOn(instance, debugPrefix + ".allalterer"),
            NullElement.BAD, ExpirableElements.OPTIONAL,
            MultiAlterType.CUMULATIVE, alterers).
         defaultOrOverrideTemplate(
            getDebugApblIfOn(instance, debugPrefix + ".template")).
         build();
   }
   /**
      <P>Eliminate all multi-line comments, the package declaration line, and any references to that package.</P>

      <H4>Examples</H4>

      <BLOCKQUOTE>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.Adder:eliminateCmtBlocksPkgLineAndPkgReferences(false, true, false)}}</BLOCKQUOTE>

      <P>Prints all lines except the package declaration. <I>{@linkplain examples.EliminatePackageDeclarationLine View example}</I></P>

      <BLOCKQUOTE>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithLicenseLicenseJDBlksAndFullyQualified:eliminateCmtBlocksPkgLineAndPkgReferences(true, true, false)}}</BLOCKQUOTE>

      <P>Eliminates the package declaration and all multi-line comment blocks (including license and JavaDoc). <I>{@linkplain examples.EliminateCommentBlocksAndPackageDeclaration View example}</I></P>

      <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE level debugging}</H4>

      <P>{@code zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences}<UL>
         <LI>{@code alterers}: {@linkplain com.github.aliteralmind.codelet.linefilter#AllTextLineAlterer(TextLineAlterer[], Appendable) All alterers}, as a whole</LI>
         <LI>{@code blockmarks}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor#eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) lineRange}.dbgBlockMarks_ifNonNull</CODE></LI>
         <LI>{@code filter}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgLnFilter_ifNonNull}</LI>
         <LI>{@code pkglinevalidator}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgPkgLnVldtr_ifNonNull}</LI>
         <LI>{@code pkglinevalidfilter}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgValidResultFilter_ifNonNull}</LI>
         <LI>{@code template}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgPkgLnVldtr_ifNonNull}</LI>
         <LI>{@code elimpkgrefsreplacer}: <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#elimPackageReferences(CodeletInstance, Appendable) elimPackageReferences}</CODE></LI>
      </UL></P>

      @return  A non-{@code null} instructions object, using the {@linkplain CustomizationInstructions#defaultOrOverrideTemplate(Appendable) default template}, where the filter is created by
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor NewLineFilterFor}.{@link com.github.aliteralmind.codelet.alter.NewLineFilterFor#eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}</CODE>
      <BR>The {@code dbgLnFilter_ifNonNull} parameter is set to
      <BR> &nbsp; &nbsp; <CODE>{@link CodeletBaseConfig CodeletBaseConfig}.{@link CodeletBaseConfig#getDebugApblIfOn(CodeletInstance) getDebugApblIfOn}(instance)</CODE>
      <BR>All other debugging parameters are set to
      <BR> &nbsp; &nbsp; <CODE>{@link CodeletBaseConfig CodeletBaseConfig}.{@link CodeletBaseConfig#getDebugApblIfOn(3, CodeletInstance) getDebugApblIfTagletVerbose}(instance)</CODE>
      @param  doElim_allPkgRefs  If {@code doElim_packageDecl} is {@code false}, this must also be {@code false}.
    **/
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> eliminateCmtBlocksPkgLineAndPkgReferences(CodeletInstance instance, CodeletType needed_defaultAlterType, Boolean doElim_multiLineCmts, Boolean doElim_packageDecl, Boolean doElim_allPkgRefs)  {
      String debugPrefix = "zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences";

      TextLineFilter filter = NewLineFilterFor.
         eliminateAllCmtBlocksAndPackageLine(instance, debugPrefix,
            doElim_packageDecl, doElim_multiLineCmts);

      CustomizationInstructions<T> instructions = (new CustomizationInstructions<T>(instance, needed_defaultAlterType)).
         filter(filter).
         defaultOrOverrideTemplate(
            getDebugApblIfOn(instance, debugPrefix + ".template"));

      List<TextLineAlterer> alterList = DefaultAlterGetterUtil.
         newEmptyArrayListWithDefaultInitCapacityPlus(needed_defaultAlterType, 1);
      if(doElim_allPkgRefs)  {
         if(!doElim_packageDecl)  {
            throw  new IllegalArgumentStateException("doElim_allPkgRefs is true, but doElim_packageDecl is false.");
         }

         alterList.add(NewLineAltererFor.elimPackageReferences(
            instance, getDebugApblIfOn(instance, debugPrefix + ".elimpkgrefs")));
      }

      TextLineAlterer[] alterers = DefaultAlterGetterUtil.
         getAlterArrayWithDefaultAlterersAdded(needed_defaultAlterType, alterList);

      return  instructions.orderedAlterers(
            getDebugApblIfOn(instance, debugPrefix + ".allalterer"),
            NullElement.BAD, ExpirableElements.OPTIONAL,
            MultiAlterType.CUMULATIVE, alterers).
         build();
   }
   private BasicCustomizers()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
