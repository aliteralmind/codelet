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
   import  com.github.xbn.linefilter.entity.EntityRequired;
   import  com.github.xbn.linefilter.entity.OutOfRangeResponseWhen;
   import  com.github.xbn.linefilter.entity.OnOffAbort;
   import  com.github.xbn.number.NewLengthInRangeFor;
   import  com.github.xbn.linefilter.entity.PostFilterSelfActiveInOutRange;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.lang.IllegalArgumentStateException;
   import  com.github.xbn.linefilter.FilteredLineIterator;
   import  com.github.xbn.linefilter.KeepUnmatched;
   import  com.github.xbn.linefilter.Returns;
   import  com.github.xbn.linefilter.entity.BlockEntity;
   import  com.github.xbn.util.IncludeJavaDoc;
   import  com.github.xbn.linefilter.entity.KeepMatched;
   import  com.github.xbn.linefilter.entity.NewBlockEntityFor;
   import  com.github.xbn.linefilter.entity.NewSingleLineEntityFor;
   import  com.github.xbn.linefilter.entity.SingleLineEntity;
   import  com.github.xbn.linefilter.entity.TextChildEntity;
   import  com.github.xbn.number.LengthInRange;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.xbn.util.JavaRegexes;
   import  java.util.ArrayList;
   import  java.util.List;
   import  java.util.regex.Pattern;
   import  java.util.regex.PatternSyntaxException;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <p>Convenience functions for creating line filters tailored for codelets.</p>

   @see  com.github.xbn.linefilter.FilteredLineIterator
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class NewLineFilterFor  {
   /**
      <p>Eliminates all multi-line comments, including the license and JavaDoc blocks.</p>

    * @return  <code>{@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}(false, null, null, true, dbgJavaMlcs_ifNotNull, dbgAllLines_ifNonNull)</code>
    */
   public static final FilteredLineIterator eliminateAllMultiLineComments(Appendable dbgJavaMlcs_ifNotNull, Appendable dbgAllLines_ifNonNull)  {
      return  eliminateAllCmtBlocksAndPackageLine(false, null, null, true, dbgJavaMlcs_ifNotNull, dbgAllLines_ifNonNull);
   }
   /**
      <p>Eliminates the package declaration line.</p>

    * @return  <code>{@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}(true, dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull, false, null, dbgAllLines_ifNonNull)</code>
    */
   public static final FilteredLineIterator eliminatePackageLine(Appendable dbgPkgLnValidFilter_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull, Appendable dbgAllLines_ifNonNull)  {
      return  eliminateAllCmtBlocksAndPackageLine(true, dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull, false, null, dbgAllLines_ifNonNull);
   }

   /**
      <p>Eliminates the package declaration line and all multi-line comment blocks.</p>

    * @param  doDelete_pkgDecl  If {@code true}, the package declaration line is eliminated. If {@code false}, the alterer is not created ({@code line()} is not called, and both {@code dbgPkgLnValidFilter_ifNonNull} and {@code dbgPkgLnVldtr_ifNonNull} are ignored). At least one boolean parameter must be {@code true}.
    * @param  doElim_multiLineCmts  If {@code true}, all multi-line comments are deleted. If {@code false}, {@code allJavaMultiLineComments} is not called (and {@code dbgJavaMlcs_ifNotNull} is ignored). Warning: This eliminates <i>entire lines</i>, including any text that happens to be outside of the block:
      <br> &nbsp; &nbsp; <code>This text will also be eliminated  /&#42; The start of a comment...</code>)
    * @see  #eliminateAllCmtBlocksAndPackageLine(CodeletInstance, String, boolean, boolean) eliminateAllCmtBlocksAndPackageLine(CodeletInstance, String, *)
    * @see  #eliminateAllMultiLineComments(Appendable, Appendable) eliminateAllMultiLineComments
    * @see  #eliminatePackageLine(Appendable, Appendable, Appendable) eliminatePackageLine
    */
   public static final FilteredLineIterator eliminateAllCmtBlocksAndPackageLine(boolean doDelete_pkgDecl, Appendable dbgPkgLnValidFilter_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull, boolean doElim_multiLineCmts, Appendable dbgJavaMlcs_ifNotNull, Appendable dbgAllLines_ifNonNull)  {
/*
      if(!doElim_multiLineCmts  &&  !doDelete_pkgDecl)  {
         throw  new IllegalArgumentStateException("doElim_multiLineCmts and doDelete_pkgDecl and both false. Nothing to do.");
      }
 */
      List<TextChildEntity> childList = new ArrayList<TextChildEntity>(3);

      if(doDelete_pkgDecl)  {
         PostFilterSelfActiveInOutRange onOffFilter = new PostFilterSelfActiveInOutRange(
            NewLengthInRangeFor.maxInclusive(null, 0, null),
            OnOffAbort.ON, OnOffAbort.OFF,
            OutOfRangeResponseWhen.IMMEDIATE,
            null);     //debug

         SingleLineEntity pkgDeclLineEntity = NewSingleLineEntityFor.match(
            "pkgdecl", KeepMatched.NO, EntityRequired.YES,
            Pattern.compile(JavaRegexes.PACKAGE_DECL_ONE_LINE_NO_CMTS),
            null,      //dbgAlter (on:System.out, off:null)
            onOffFilter,
            null);     //dbgLineNums
         childList.add(pkgDeclLineEntity);
      }

      if(doElim_multiLineCmts)  {
         BlockEntity javaMlcBlock = NewBlockEntityFor.javaComment_Cfg(
            "comment", EntityRequired.YES, IncludeJavaDoc.YES,
            null,      //dbgStart
            null,      //dbgEnd
            null,
            null).     //dbgLineNums
               keepNone().build();
         childList.add(javaMlcBlock);
      }

      if(childList.size() == 0)  {
         throw  new IllegalArgumentStateException("doElim_multiLineCmts and doDelete_pkgDecl and both false. Nothing to do.");
      }

      return  new FilteredLineIterator(
         null, Returns.KEPT, KeepUnmatched.YES,
         null, null,    //dbgEveryLine and its line-range
         childList.toArray(new TextChildEntity[childList.size()]));
   }
   /**
      <p>Create a new eliminate-comment-blocks-and-package-line filter with named debuggers.</p>

      <p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine} parameters:<ul>
         <li><code><i>[named_debugPrefix]</i>.filter</code>: {@code dbgAllLines_ifNonNull}<ul>
            <li>{@code .packageline}: {@code dbgPkgLnValidFilter_ifNonNull}</li>
            <li>{@code .packageline.validfilter}: {@code dbgPkgLnVldtr_ifNonNull}</li>
            <li>{@code .javamlcs}: {@code dbgJavaMlcs_ifNotNull}</li>
         </ul></li>
      </ul>All of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.filter=-1
PREFIX.filter.packageline=-1
PREFIX.filter.packageline.validfilter=-1
PREFIX.filter.javamlcs=-1</pre></blockquote>

    * @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
    * @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    */
   public static final FilteredLineIterator eliminateAllCmtBlocksAndPackageLine(CodeletInstance instance, String named_debugPrefix, boolean doDelete_pkgDecl, boolean doElim_multiLineCmts)  {
      CrashIfString.nullEmpty(named_debugPrefix, "named_debugPrefix", null);
      String prefixFltr = named_debugPrefix + ".filter";
      Appendable dbgPackageLine = getDebugApblIfOn(instance,
         prefixFltr + ".packageline");
      Appendable dbgPackageLineValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".packageline.validfilter");
      Appendable dbgMlcBlkStartEnd = getDebugApblIfOn(instance,
         prefixFltr + ".javamlcs");
      Appendable dbgFilter = getDebugApblIfOn(instance, prefixFltr + ".alllines");

      return  	eliminateAllCmtBlocksAndPackageLine(doDelete_pkgDecl,
            dbgPackageLine,           //dbgPkgLnValidFilter_ifNonNull
            dbgPackageLineValidFltr,  //dbgPkgLnVldtr_ifNonNull
         doElim_multiLineCmts,
            dbgMlcBlkStartEnd,        //dbgJavaMlcs_ifNotNull
            dbgFilter);               //dbgAllLines_ifNonNull
   }
   /**
      <p><i>A code snippet:</i> Keeps all lines in a specific range, based on the text existing in the first and last lines.</p>

    * @param  startAppearance_num  If the start-line search term is found on multiple lines, this is the desired occurance (if two, the second line the term is found on).
    * @param  is_startLineRegex  If {@code true} {@code startLine_findWhat} is treated as a regular expression. If {@code false}, literal.
    * @param  startLine_findWhat  The start-line search term. This is the text or pattern that exists in the first line that should be kept. May not be {@code null} or empty.
    * @param  endAppearance_num  If the end-line search term is found on multiple lines, this is the desired occurance. This is the appearance of the term <i>in the entire document</i>, including any appearances before the start line.
    * @param  is_endLineRegex  If {@code true} {@code startLine_findWhat} is treated as a regular expression.
    * @param  endLine_findWhat  The start-line search term.
    * @return
<blockquote><pre>new {@link com.github.xbn.linefilter.FilteredLineIterator#FilteredLineIterator(Iterator, Returns, KeepUnmatched, Appendable, LengthInRange, TextChildEntity...) FilteredLineIterator}(
   null, {@link com.github.xbn.linefilter.Returns}.{@link com.github.xbn.linefilter.Returns#KEPT KEPT}, {@link com.github.xbn.linefilter.KeepUnmatched}.{@link com.github.xbn.linefilter.KeepUnmatched#NO NO},
   dbgAllLines_ifNonNull, rangeForEveryLineDebug_ifNonNull,
   snippetBlock)</pre></blockquote>
      Where {@code snippetBlock} is a
<blockquote><pre>
{@link com.github.xbn.linefilter.entity.NewBlockEntityFor}.{@link com.github.xbn.linefilter.entity.NewBlockEntityFor#lineRange(String, KeepMatched, EntityRequired, Pattern, ValidResultFilter, Appendable, Pattern, ValidResultFilter, Appendable, RawOnOffEntityFilter, Appendable) lineRange}(&quot;lineRange&quot;,
   {@link com.github.xbn.linefilter.entity.KeepMatched}.{@link com.github.xbn.linefilter.entity.KeepMatched#YES YES},
   startLinePattern, startAppearanceFilter, dbgStart_ifNonNull,
   endLinePattern, endAppearanceFilter, dbgEnd_ifNonNull,
   {@link com.github.xbn.linefilter.entity.EntityRequired}.{@link com.github.xbn.linefilter.entity.EntityRequired#YES YES}, null, dbgLineNums_ifNonNull)</pre></blockquote>
      and {@code startAppearanceFilter} is a
<blockquote><pre>{@link com.github.xbn.analyze.validate.NewValidResultFilterFor}.{@link com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) exactly}(
   startAppearance_num, ..., dbgStartFilter_ifNonNull)</pre></blockquote>
    * @see  #lineRange(CodeletInstance, String, int, boolean, String, int, boolean, String) lineRange(CodeletInstance, ...)
    * @see  #lineRangeWithReplace(int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRangeWithReplace
    */
   public static final FilteredLineIterator lineRange(int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, Appendable dbgStartFilter_ifNonNull, Appendable dbgStart_ifNonNull, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, Appendable dbgEndFilter_ifNonNull, Appendable dbgEnd_ifNonNull, Appendable dbgLineNums_ifNonNull, Appendable dbgAllLines_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull) throws PatternSyntaxException  {
      Pattern startLinePattern = NewPatternFor.regexIfTrueLiteralIfFalse(is_startLineRegex, startLine_findWhat, "startLine_findWhat");
      Pattern endLinePattern = NewPatternFor.regexIfTrueLiteralIfFalse(is_endLineRegex, endLine_findWhat, "endLine_findWhat");

      ValidResultFilter startAppearanceFilter = NewValidResultFilterFor.exactly(
         startAppearance_num, "startAppearance_num",
         dbgStartFilter_ifNonNull);
      ValidResultFilter endAppearanceFilter = NewValidResultFilterFor.exactly(
         endAppearance_num, "endAppearance_num",
         dbgEndFilter_ifNonNull);

      BlockEntity snippetBlock = NewBlockEntityFor.lineRange("lineRange",
         KeepMatched.YES, EntityRequired.YES,
         startLinePattern, startAppearanceFilter, dbgStart_ifNonNull,
         endLinePattern, endAppearanceFilter, dbgEnd_ifNonNull,
         null, dbgLineNums_ifNonNull);

      return  new FilteredLineIterator(
         null, Returns.KEPT, KeepUnmatched.NO,
         dbgAllLines_ifNonNull, rangeForEveryLineDebug_ifNonNull,
         snippetBlock);
   }
   /**
      <p>Create a new line-range filter with named debuggers.</p>

      <p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRange} parameters:<ul>
         <li><code><i>[named_debugPrefix]</i>.filter</code><ul>
            <li>{@code .blockstart}: {@code dbgStart_ifNonNull}</li>
            <li>{@code .blockstart.validfilter}: {@code dbgStartFilter_ifNonNull}</li>
            <li>{@code .blockend}: {@code dbgEnd_ifNonNull}</li>
            <li>{@code .blockend.validfilter}: {@code dbgEndFilter_ifNonNull}</li>
            <li>{@code .linenums}: {@code dbgLineNums_ifNonNull}</li>
            <li>{@code .alllines}: {@code dbgAllLines_ifNonNull}</li>
         </ul></li>
      </ul>All of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.filter.alllines=-1
PREFIX.filter.blockend.validfilter=-1
PREFIX.filter.blockend=-1
PREFIX.filter.blockstart.validfilter=-1
PREFIX.filter.blockstart=-1
PREFIX.filter.linenums=-1</pre></blockquote>

    * @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
    * @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    */
   public static final FilteredLineIterator lineRange(CodeletInstance instance, String named_debugPrefix, int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat) throws PatternSyntaxException  {
      CrashIfString.nullEmpty(named_debugPrefix, "named_debugPrefix", null);
      String prefixFltr = named_debugPrefix + ".filter";
      Appendable dbgBlkStart = getDebugApblIfOn(instance,
         prefixFltr + ".blockstart");
      Appendable dbgBlkStartValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".blockstart.validfilter");
      Appendable dbgBlkEnd = getDebugApblIfOn(instance,
         prefixFltr + ".blockend");
      Appendable dbgBlkEndValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".blockend.validfilter");
      Appendable dbgLineNums = getDebugApblIfOn(instance,
         prefixFltr + ".linenums");
      Appendable dbgAllLines = getDebugApblIfOn(instance,
         prefixFltr + ".alllines");

      return  lineRange(
         startAppearance_num, is_startLineRegex, startLine_findWhat,
            dbgBlkStartValidFltr, dbgBlkStart,
         endAppearance_num, is_endLineRegex, endLine_findWhat,
            dbgBlkEndValidFltr, dbgBlkEnd, dbgLineNums, dbgAllLines, null);
   }
   /**
      <p>Keeps all lines in a specific range, based on the text existing in the first and last lines, making a replacement on the first and last lines only. This is useful when lines must be marked, but those marks should not be seen in the final output.</p>

      <p>For documentation on all other parameters, see {@link #lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRange}.</p>

    * @param  startLine_rplcWith  The replacement term for the start-line search term ({@code startLine_findWhat}). May not be {@code null} or empty.
    * @param  startRplcs_notMtchNum  In most cases, this should be set to {@code "FIRST"}. See <code>com.github.xbn.regexutil.{@link com.github.xbn.regexutil.ReplacedInEachInput}</code>.
    * @param  endLine_rplcWith  The replacement term for the end-line search term ({@code endLine_findWhat}).
    * @param  endRplcs_notMtchNum  In most cases, this should be set to {@code "FIRST"}.
    * @return  The same as {@link #lineRange(CodeletInstance, String, int, boolean, String, int, boolean, String) lineRange}, with this alternative block entity:
<blockquote><pre>{@link com.github.xbn.linefilter.entity.NewBlockEntityFor}.{@link com.github.xbn.linefilter.entity.NewBlockEntityFor#lineRangeWithReplace(String, KeepStartLine, KeepMidLines, KeepEndLine, EntityRequired, Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable, ValueAlterer, Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable, RawOnOffEntityFilter, Appendable) lineRangeWithReplace}(
   &quot;lineRangeWithReplace&quot;, {@link com.github.xbn.linefilter.entity.KeepMatched}.{@link com.github.xbn.linefilter.entity.KeepMatched#YES YES},
   startLinePattern, startLine_rplcWith, startRplcs_notMtchNum, startAppearanceFilter,
      dbgStartRplcr_ifNonNull,
   null,         //No mid alterer
   endLinePattern, endLine_rplcWith, endRplcs_notMtchNum, endAppearanceFilter,
      dbgEndRplcr_ifNonNull,
   {@link com.github.xbn.linefilter.entity.EntityRequired}.{@link com.github.xbn.linefilter.entity.EntityRequired#YES YES}, null, dbgLineNums_ifNonNull)</pre></blockquote>
    */
   public static final FilteredLineIterator lineRangeWithReplace(int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, ReplacedInEachInput startRplcs_notMtchNum, Appendable dbgStartFilter_ifNonNull, Appendable dbgStartRplcr_ifNonNull, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, ReplacedInEachInput endRplcs_notMtchNum, Appendable dbgEndFilter_ifNonNull, Appendable dbgEndRplcr_ifNonNull, Appendable dbgLineNums_ifNonNull, Appendable dbgAllLines_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull) throws PatternSyntaxException  {
      Pattern startLinePattern = NewPatternFor.regexIfTrueLiteralIfFalse(is_startLineRegex, startLine_findWhat, "startLine_findWhat");
      Pattern endLinePattern = NewPatternFor.regexIfTrueLiteralIfFalse(is_endLineRegex, endLine_findWhat, "endLine_findWhat");

      ValidResultFilter startAppearanceFilter = NewValidResultFilterFor.exactly(
         startAppearance_num, "startAppearance_num",
         dbgStartFilter_ifNonNull);
      ValidResultFilter endAppearanceFilter = NewValidResultFilterFor.exactly(
         endAppearance_num, "endAppearance_num",
         dbgEndFilter_ifNonNull);

      BlockEntity snippetBlock = NewBlockEntityFor.lineRangeWithReplace(
         "lineRangeWithReplace", KeepMatched.YES, EntityRequired.YES,
         startLinePattern, startLine_rplcWith, startRplcs_notMtchNum, startAppearanceFilter,
            dbgStartRplcr_ifNonNull,
         null,         //No mid alterer
         endLinePattern, endLine_rplcWith, endRplcs_notMtchNum, endAppearanceFilter,
            dbgEndRplcr_ifNonNull,
         null, dbgLineNums_ifNonNull);

      return  new FilteredLineIterator(
         null, Returns.KEPT, KeepUnmatched.NO,
         dbgAllLines_ifNonNull, rangeForEveryLineDebug_ifNonNull,
         snippetBlock);
   }

   /**
      <p>Create a new line-range filter with named debuggers.</p>

      <p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #lineRangeWithReplace(int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, Appendable, Appendable, LengthInRange) lineRangeWithReplace} parameters:<ul>
         <li><code><i>[named_debugPrefix]</i>.filter</code><ul>
            <li>{@code .blockstart}: {@code dbgStartRplcr_ifNonNull}</li>
            <li>{@code .blockstart.validfilter}: {@code dbgStartFilter_ifNonNull}</li>
            <li>{@code .blockend}: {@code dbgEndRplcr_ifNonNull}</li>
            <li>{@code .blockend.validfilter}: {@code dbgEndFilter_ifNonNull}</li>
            <li>{@code .linenums}: {@code dbgLineNums_ifNonNull}</li>
            <li>{@code .alllines}: {@code dbgAllLines_ifNonNull}</li>
         </ul></li>
      </ul>All of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.filter.alllines=-1
<blockquote><pre>PREFIX.filter.linenums=-1
PREFIX.filter.blockend.validfilter=-1
PREFIX.filter.blockend=-1
PREFIX.filter.blockstart.validfilter=-1
PREFIX.filter.blockstart=-1</pre></blockquote>

    * @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
    * @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    */
   public static final FilteredLineIterator lineRangeWithReplace(CodeletInstance instance, String named_debugPrefix, int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, ReplacedInEachInput startRplcs_notMtchNum, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, ReplacedInEachInput endRplcs_notMtchNum) throws PatternSyntaxException  {
      CrashIfString.nullEmpty(named_debugPrefix, "named_debugPrefix", null);
      String prefixFltr = named_debugPrefix + ".filter";
      Appendable dbgBlkStart = getDebugApblIfOn(instance,
         prefixFltr + ".blockstart");
      Appendable dbgBlkStartValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".blockstart.validfilter");
      Appendable dbgBlkEnd = getDebugApblIfOn(instance,
         prefixFltr + ".blockend");
      Appendable dbgBlkEndValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".blockend.validfilter");
      Appendable dbgLineNums = getDebugApblIfOn(instance,
         prefixFltr + ".linenums");
      Appendable dbgAllLines = getDebugApblIfOn(instance,
         prefixFltr + ".alllines");

      return  lineRangeWithReplace(
         startAppearance_num, is_startLineRegex,
         startLine_findWhat, startLine_rplcWith, startRplcs_notMtchNum,
            dbgBlkStartValidFltr, dbgBlkStart,
         endAppearance_num, is_endLineRegex,
         endLine_findWhat, endLine_rplcWith, endRplcs_notMtchNum,
            dbgBlkEndValidFltr, dbgBlkEnd,
            dbgLineNums, dbgAllLines, null);
   }
   private NewLineFilterFor()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
