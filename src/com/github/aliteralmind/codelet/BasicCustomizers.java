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
   import  com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil;
   import  com.github.aliteralmind.codelet.alter.NewLineAltererFor;
   import  com.github.xbn.linefilter.alter.TextLineAlterer;
   import  com.github.xbn.linefilter.FilteredLineIterator;
   import  com.github.xbn.analyze.alter.ExpirableElements;
   import  com.github.xbn.analyze.alter.MultiAlterType;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.lang.IllegalArgumentStateException;
   import  com.github.xbn.regexutil.IgnoreCase;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.util.DefaultValueFor;
   import  com.github.xbn.util.EnumUtil;
   import  java.util.List;
   import  java.util.regex.PatternSyntaxException;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <p>Pre-made Codelet customizers.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class BasicCustomizers  {
   /**
      <p><i>Text snippet:</i> Eliminates all lines except those between a specific start and end line, based on text found in those (start and end) lines. The start and end lines are also kept.</p>

      <h4>Example</h4>

      <P style="font-size: 125%;"><b>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo%lineRange(1, false, "Adder adder", 1, false, "println(adder.getSum())", "^      ")}}</b></p>

      <p>Given <a href="{@docRoot}/overview-summary.html#xmpl_nocust">this source code</a>, this taglet displays the range of lines beginning with {@code "Adder adder"}, and ending with the <i>second</i> {@code "println(adder.getSum())"}. This also eliminates the indentation of the kept lines, which is two tabs at <a href="http://www.regular-expressions.info/anchors.html">line-start</a>: {@code "^      "}.</p>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo%lineRange(1, false, "Adder adder", 2, false, "println(adder.getSum())", "^      ")}


      <h4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE named debuggers}</h4>

      <p>{@code zzBasicCustomizers.lineRange}<ul>
         <li>{@code .allalterer}: {@linkplain com.github.xbn.linefilter#AllTextLineAlterer(TextLineAlterer[], ExpirableElements, MultiAlterType, Appendable) All alterers}, as a whole.</li>
         <li>{@code .elimindent}: <code>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) eliminateIndentationOrNull}</code></li>
         <li>{@code .linenums}: The start and end line numbers of the snippet.</li>
         <li>{@code filter}: <code>{@link com.github.aliteralmind.codelet.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRange}.dbgEveryLine_ifNonNull</code><ul>
            <li>{@code .endalterer}: <code>NewLineFilterFor.lineRange.dbgEnd_ifNonNull</code></li>
            <li>{@code .endvalidfilter}: <code>NewLineFilterFor.lineRange.dbgEndFilter_ifNonNull</code></li>
            <li>{@code .startalterer}: <code>NewLineFilterFor.lineRange.dbgStart_ifNonNull</code></li>
            <li>{@code .startvalidfilter}: <code>NewLineFilterFor.lineRange.dbgStartFilter_ifNonNull</code></li>
         </ul></li>
         <li>{@code template}: The {@linkplain TagletOfTypeProcessor#getTemplateFromInstructionsOverrideOrDefault(CustomizationInstructions) template}</li>
      </ul>

      <p><b>Full names:</b></p>

<blockquote><pre>zzBasicCustomizers.lineRange.allalterer
zzBasicCustomizers.lineRange.elimindent
zzBasicCustomizers.lineRange.linenums
zzBasicCustomizers.lineRange.filter
zzBasicCustomizers.lineRange.filter.endalterer
zzBasicCustomizers.lineRange.filter.endvalidfilter
zzBasicCustomizers.lineRange.filter.startalterer
zzBasicCustomizers.lineRange.filter.startvalidfilter
zzBasicCustomizers.lineRange.template</pre></blockquote>

    * @param  instance  May not be {@code null}. <i>When calling this customizer from a taglet, exclude this parameter. All other parameters are required.</i>
    * @param  startAppearance_num  If the text to be found ({@code startLine_findWhat}) exists in multiple lines, this is the one to use. Must be greater than zero (although {@code -1}, meaning "ignore this setting", is equivalent to {@code 1}: the first appearance).
    * @param  is_startLineRegex  If {@code true} then {@code startLine_findWhat} is treated as a regular expression. Otherwise, {@code startLine_findWhat} is treated as a literal.
    * @param  startLine_findWhat  The search term that defines the line-range start line. If a regex, this may or may not include start or end-of-line anchors ({@code '^'} and {@code '$'}) (matching is done with <code>{@link java.util.regex.Matcher Matcher}.{@link java.util.regex.Matcher#find() find}()</code>).
    * @param  endAppearance_num  The appearance number of the end-line search term. If it happens to exist before the start-line search-term, then this parameter must be at least equal to two.
    * @param  is_endLineRegex  If {@code true} then {@code endLine_findWhat} is treated as a regular expression.
    * @param  endLine_findWhat  The search term that defines the line-range end line.
    * @param  indentRegexToElim_emptyStrIfNone  The regular expression representing the indentation that should be eliminated from the line-range. Since these lines exist in the middle of a file, they may be {@linkplain com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) extra-indented}.
    * @return  <code>CustomizationInstructions.&lt;T&gt;newForMaybeElimIndent_tabToSpcEscHtml(instance, filter, indentRegexToElim_emptyStrIfNone)</code>
      <br>Where {@code filter} is created with
      <br> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.NewLineFilterFor NewLineFilterFor}.{@link com.github.aliteralmind.codelet.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRange}</code>
    * @see  #lineRangeWithReplace(CodeletInstance, CodeletType, Integer, Boolean, String, String, String, Integer, Boolean, String, String, String, String) lineRangeWithReplace
    */
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> lineRange(CodeletInstance instance, CodeletType needed_defaultAlterType, Integer startAppearance_num, Boolean is_startLineRegex, String startLine_findWhat, Integer endAppearance_num, Boolean is_endLineRegex, String endLine_findWhat, String indentRegexToElim_emptyStrIfNone) throws PatternSyntaxException  {
      //Named debuggers appendables
         String debugPrefix = "zzBasicCustomizers.lineRange";

      FilteredLineIterator filter = NewLineFilterFor.lineRange(instance, debugPrefix,
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
      <p><i>Text snippet:</i> Eliminates all lines except those between a specific start and end line, based on text found in those (start and end) lines--also makes replacements on the start and end lines only. The start and end lines are also kept. This function is useful when lines must be marked, but those marks should not be seen in the final output.</p>

      <h4>Example</h4>

   <P style="font-size: 125%;"><b>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithSnippetEndMarker%lineRangeWithReplace(1, true, "(Adder adder)", "$1", "FIRST", 1, true, "; +//End snippet$", ";", "FIRST", "^      ")}}</b></p>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithSnippetEndMarker%lineRangeWithReplace(1, true, "(Adder adder)", "$1", "FIRST", 1, true, "; +//End snippet$", ";", "FIRST", "^      ")}

      <p>Snippet starts with the line containing {@code "Adder adder"}, which is replaced with itself: {@code "$1"}. The last line in the range ends the regular expression {@code "; +//End snippet$"} which is replaced with a semi-colon. This also deletes the indentation of the kept lines, which is two tabs at <a href="http://www.regular-expressions.info/anchors.html">line-start</a>: {@code "^      "}. <i>{@linkplain examples.LineRangeWithReplace View example}</i></p>

      <p>This only documents things that are different from {@link #lineRange(CodeletInstance, CodeletType, Integer, Boolean, String, Integer, Boolean, String, String) lineRange}.</p>

      <h4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE named debuggers}</h4>

      <p>{@code zzBasicCustomizers.lineRangeWithReplace}<ul>
         <li>{@code allalterer}: {@linkplain com.github.xbn.linefilter#AllTextLineAlterer(TextLineAlterer[], ExpirableElements, MultiAlterType, Appendable) All alterers}, as a whole.</li>
         <li>{@code .elimindent}: <code>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentationOrNull(String, Appendable) eliminateIndentationOrNull}</code></li>
         <li>{@code filter}: <code>{@link com.github.aliteralmind.codelet.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.NewLineFilterFor#lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRange}.dbgEveryLine_ifNonNull</code><ul>
            <li>{@code .endalterer}: <code>NewLineFilterFor.lineRange.dbgEnd_ifNonNull</code></li>
            <li>{@code .endvalidfilter}: <code>NewLineFilterFor.lineRange.dbgEndFilter_ifNonNull</code></li>
            <li>{@code .startalterer}: <code>NewLineFilterFor.lineRange.dbgStart_ifNonNull</code></li>
            <li>{@code .startvalidfilter}: <code>NewLineFilterFor.lineRange.dbgStartFilter_ifNonNull</code></li>
         </ul></li>
         <li>{@code template}: The {@linkplain TagletOfTypeProcessor#getTemplateFromInstructionsOverrideOrDefault(CustomizationInstructions) template}</li>
      </ul>

      <p><b>Full names:</b></p>

<blockquote><pre>zzBasicCustomizers.lineRangeWithReplace.allalterer
zzBasicCustomizers.lineRangeWithReplace.elimindent
zzBasicCustomizers.lineRangeWithReplace.filter
zzBasicCustomizers.lineRangeWithReplace.filter.endalterer
zzBasicCustomizers.lineRangeWithReplace.filter.endvalidfilter
zzBasicCustomizers.lineRangeWithReplace.filter.startalterer
zzBasicCustomizers.lineRangeWithReplace.filter.startvalidfilter
zzBasicCustomizers.lineRangeWithReplace.template</pre></blockquote>

    * @param  startLine_findWhat  The text or match that defines the line-range start line. To search for text only (make no replacement), either set<ul>
         <li>{@code is_startLineRegex} to {@code false}, and</li>
         <li>both {@code startLine_findWhat} ({@code "text to find"}) and {@code startLine_rplcWith} to the same text ({@code "text to find"})</li>
      </ul>or<ul>
         <li>{@code is_startLineRegex} to {@code true},</li>
         <li>{@code startLine_findWhat} to the expected text surrounded by a <a href="http://stackoverflow.com/questions/21880127/have-trouble-understanding-capturing-groups-and-back-references">capture group</a> ({@code "(text to find)"}), and</li>
         <li>{@code startLine_rplcWith} to {@code "$1"}</li>
      </ul>
    * @param  startRplcWhat_notMatchNums  In most cases, this should be set to {@code "FIRST"}. See <code>com.github.xbn.regexutil.{@link com.github.xbn.regexutil.ReplacedInEachInput}</code>.
    */
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> lineRangeWithReplace(CodeletInstance instance, CodeletType needed_defaultAlterType, Integer startAppearance_num, Boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, String startRplcWhat_notMatchNums, Integer endAppearance_num, Boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, String endRplcWhat_notMatchNums, String indentRegexToElim_emptyStrIfNone) throws PatternSyntaxException  {
      String debugPrefix = "zzBasicCustomizers.lineRangeWithReplace";

      ReplacedInEachInput rplcsStart = EnumUtil.toValueWithNullDefault(startRplcWhat_notMatchNums, "startRplcWhat_notMatchNums", IgnoreCase.NO, DefaultValueFor.NOTHING, ReplacedInEachInput.ALL);
      ReplacedInEachInput rplcsEnd = EnumUtil.toValueWithNullDefault(endRplcWhat_notMatchNums, "startRplcWhat_notMatchNums", IgnoreCase.NO, DefaultValueFor.NOTHING, ReplacedInEachInput.ALL);

      FilteredLineIterator filter = NewLineFilterFor.lineRangeWithReplace(
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
            NullElement.OK, ExpirableElements.OPTIONAL,
            MultiAlterType.CUMULATIVE, alterers).
         defaultOrOverrideTemplate(
            getDebugApblIfOn(instance, debugPrefix + ".template")).
         build();
   }
   /**
      <p>Eliminate all multi-line comments and the package declaration line.</p>

    * @return  <code>eliminateCmtBlocksPkgLineAndPkgReferences(instance, needed_defaultAlterType, {@link java.lang.Boolean}.{@link java.lang.Boolean#TRUE TRUE}, Boolean.TRUE, Boolean.{@link java.lang.Boolean#FALSE FALSE})</code>
    */
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> eliminateCommentBlocksAndPackageDecl(CodeletInstance instance, CodeletType needed_defaultAlterType)  {
      return  eliminateCmtBlocksPkgLineAndPkgReferences(instance, needed_defaultAlterType, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
   }

   /**
      <p>Eliminate all multi-line comments, the package declaration line, and any references to that package.</p>

      <h4>Examples</h4>

   <P style="font-size: 125%;"><b>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.Adder%eliminateCmtBlocksPkgLineAndPkgReferences(false, true, false)}}</b></p>

{@.codelet com.github.aliteralmind.codelet.examples.adder.Adder%eliminateCmtBlocksPkgLineAndPkgReferences(false, true, false)}

      <p>Prints all lines except the package declaration. <i>{@linkplain examples.EliminatePackageDeclarationLine Full example}</i></p>

   <P style="font-size: 125%;"><b>{@code {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithFullyQualified%eliminateCommentBlocksAndPackageDecl()}}</b></p>

{@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemoWithFullyQualified%eliminateCommentBlocksAndPackageDecl()}

      <p>Eliminates the package declaration and all multi-line comment blocks (including license and JavaDoc). <i>(<a href="{@docRoot}/overview-summary.html#xmpl_hello">Intro example</a>)</i></p>

      <h4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE named debuggers}</h4>

      <p>{@code zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences}<ul>
         <li>{@code alterers}: {@linkplain com.github.xbn.linefilter#AllTextLineAlterer(TextLineAlterer[], ExpirableElements, MultiAlterType, Appendable) All alterers}, as a whole</li>
         <li>{@code blockmarks}: <code>{@link com.github.aliteralmind.codelet.NewLineFilterFor}.{@link com.github.aliteralmind.codelet.NewLineFilterFor#eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) lineRange}.dbgBlockMarks_ifNonNull</code></li>
         <li>{@code filter}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgLnFilter_ifNonNull}</li>
         <li>{@code pkglinevalidator}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgPkgLnVldtr_ifNonNull}</li>
         <li>{@code pkglinevalidfilter}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgValidResultFilter_ifNonNull}</li>
         <li>{@code template}: {@code NewLineFilterFor.eliminateAllCmtBlocksAndPackageLine.dbgPkgLnVldtr_ifNonNull}</li>
         <li>{@code elimpkgrefsreplacer}: <code>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#elimPackageReferences(CodeletInstance, Appendable) elimPackageReferences}</code></li>
      </ul>

      <p><b>Full names:</b></p>

<blockquote><pre>zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.alterers
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.blockmarks
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.filter
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.pkglinevalidator
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.pkglinevalidfilter
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.template
zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.elimpkgrefsreplacer</pre></blockquote>

    * @return  A non-{@code null} instructions object, using the {@linkplain CustomizationInstructions#defaultOrOverrideTemplate(Appendable) default template}, where the filter is created by
      <br> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.NewLineFilterFor NewLineFilterFor}.{@link com.github.aliteralmind.codelet.NewLineFilterFor#eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}</code>
      <br>The {@code dbgLnFilter_ifNonNull} parameter is set to
      <br> &nbsp; &nbsp; <code>{@link CodeletBaseConfig CodeletBaseConfig}.{@link CodeletBaseConfig#getDebugApblIfOn(CodeletInstance) getDebugApblIfOn}(instance)</code>
      <br>All other debugging parameters are set to
      <br> &nbsp; &nbsp; <code>{@link CodeletBaseConfig CodeletBaseConfig}.{@link CodeletBaseConfig#getDebugApblIfOn(3, CodeletInstance) getDebugApblIfTagletVerbose}(instance)</code>
    * @param  doElim_allPkgRefs  If {@code doElim_packageDecl} is {@code false}, this must also be {@code false}.
    * @see  #eliminateCommentBlocksAndPackageDecl(CodeletInstance, CodeletType) eliminateCommentBlocksAndPackageDecl
    */
   public static final <T extends CodeletTemplateBase> CustomizationInstructions<T> eliminateCmtBlocksPkgLineAndPkgReferences(CodeletInstance instance, CodeletType needed_defaultAlterType, Boolean doElim_multiLineCmts, Boolean doElim_packageDecl, Boolean doElim_allPkgRefs)  {
      String debugPrefix = "zzBasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences";

      FilteredLineIterator filter = NewLineFilterFor.
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
