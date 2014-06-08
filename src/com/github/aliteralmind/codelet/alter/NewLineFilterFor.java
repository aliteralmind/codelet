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
package  com.github.aliteralmind.codelet.alter;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.xbn.linefilter.BlockMarks;
   import  com.github.xbn.linefilter.LineObject;
   import  com.github.xbn.linefilter.NewTextLineAltererFor;
   import  com.github.xbn.linefilter.NewTextLineFilterFor;
   import  com.github.xbn.linefilter.TextLineAlterer;
   import  com.github.xbn.linefilter.TextLineFilter;
   import  com.github.xbn.linefilter.z.ActiveBlockLines;
   import  com.github.xbn.linefilter.z.ActiveSingleLines;
   import  com.github.xbn.linefilter.z.BlockEnd;
   import  com.github.xbn.linefilter.z.InactiveLines;
   import  com.github.xbn.linefilter.z.TextLineFilter_Cfg;
   import  com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.lang.IllegalArgumentStateException;
   import  com.github.xbn.neederneedable.DummyForNoNeeder;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.text.CrashIfString;
   import  java.util.regex.Pattern;
   import  java.util.regex.PatternSyntaxException;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Convenience functions for creating line filters tailored for codelets.</P>

   @see  com.github.xbn.linefilter.TextLineFilter
   @see  com.github.xbn.linefilter.NewTextLineFilterFor
   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class NewLineFilterFor  {
   /**
      <P>Eliminates the (strictly-formmatted) license block and the {@code @author} line. This requires license blocks starting with {@code "/&#42;license&#42;\"} and ending with {@code "\&#42;license&#42;/"}. Example:</P>

<BLOCKQUOTE><PRE>/&#42;license&#42;\
   ...license text goes here...
\&#42;license&#42;/</PRE></BLOCKQUOTE>

      @return  <CODE>{@link #eliminateLicenseBlockAndAtAuthorLine(String, Appendable, String, Appendable, Appendable, Appendable) eliminateLicenseBlockAndAtAuthorLine}(&quot;license*\\&quot;, debug_licenseStart, &quot;\\*license&quot;, debug_licenseEnd, debug_authorLine, dbgFilter_ifNonNull)</CODE>
    **/
   public static final TextLineFilter eliminateLicenseBlockAndAtAuthorLine(Appendable debug_licenseStart, Appendable debug_licenseEnd, Appendable debug_authorLine, Appendable dbgFilter_ifNonNull)  {
      return  eliminateLicenseBlockAndAtAuthorLine("license*\\", debug_licenseStart, "\\*license", debug_licenseEnd, debug_authorLine, dbgFilter_ifNonNull);
   }
   /**
      <P>Eliminates the (strictly-formmatted) license block and the {@code @author} line.</P>

      @param  blockStart_postSlashStar  The text that must follow the license block's multi-line comment start mark ({@code "/&#42;"}). May not be {@code null} or empty, and <I>should</I> not contain {@code "&#42;/"}. This extra text is required due to a limitation of {@code TextLineFilter}, where it cannot distinguish between multi-line comments.
      @param  blockEnd_preStarSlash  The text that must precede the license block's multi-line comment end mark ({@code "/&#42;"})
      @return
<BLOCKQUOTE><PRE>new {@link com.github.xbn.linefilter.z.TextLineFilter_Cfg#TextLineFilter_Cfg() TextLineFilter_Cfg}().
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#start(Pattern, ValidResultFilter, Appendable) start}(NewPatternFor.literal(&quot;/&quot;+&quot;*&quot; + blockStart_postSlashStar), null, debug_licenseStart).
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#end(BlockEnd, Pattern, ValidResultFilter, Appendable) end}(BlockEnd.REQUIRED, NewPatternFor.literal(blockEnd_preStarSlash + &quot;*&quot;+&quot;/&quot;), null, debug_licenseEnd).
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#line(Pattern, ValidResultFilter, Appendable) line}(Pattern.compile(&quot;^[\t ]*@author.*$&quot;), null, debug_authorLine).
   {@link com.github.xbn.linefilter.z.LineFilter_CfgForNeeder#ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines) ifBlockOrLineOrNotActive}({@link com.github.xbn.linefilter.z.ActiveBlockLines ActiveBlockLines}.{@link com.github.xbn.linefilter.z.ActiveBlockLines#DISCARD DISCARD}, {@link com.github.xbn.linefilter.z.ActiveSingleLines ActiveSingleLines}.{@link com.github.xbn.linefilter.z.ActiveSingleLines#DISCARD DISCARD}, {@link com.github.xbn.linefilter.z.InactiveLines InactiveLines}.{@link com.github.xbn.linefilter.z.InactiveLines#KEEP KEEP}).
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(dbgFilter_ifNonNull).{@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#build() build}()</PRE></BLOCKQUOTE>
      @see  #eliminateLicenseBlockAndAtAuthorLine(Appendable, Appendable, Appendable, Appendable)
    **/
   public static final TextLineFilter eliminateLicenseBlockAndAtAuthorLine(String blockStart_postSlashStar, Appendable debug_licenseStart, String blockEnd_preStarSlash, Appendable debug_licenseEnd, Appendable debug_authorLine, Appendable dbgFilter_ifNonNull)  {
      return  new TextLineFilter_Cfg().
         start(NewPatternFor.literal("/"+"*" + blockStart_postSlashStar), null, debug_licenseStart).
         end(BlockEnd.REQUIRED, NewPatternFor.literal(blockEnd_preStarSlash + "*"+"/"), null, debug_licenseEnd).
         line(Pattern.compile("^[\t ]*@author.*$"), null, debug_authorLine).
         ifBlockOrLineOrNotActive(ActiveBlockLines.DISCARD, ActiveSingleLines.DISCARD, InactiveLines.KEEP).
         debugTo(dbgFilter_ifNonNull).build();
   }
   /**
      <P>Eliminates all multi-line comments, including the license and JavaDoc blocks.</P>

      @return  <CODE>{@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}(false, null, null, true, dbgJavaMlcs_ifNotNull, dbgFilter_ifNonNull)</CODE>
    **/
   public static final TextLineFilter eliminateAllMultiLineComments(Appendable dbgJavaMlcs_ifNotNull, Appendable dbgFilter_ifNonNull)  {
      return  eliminateAllCmtBlocksAndPackageLine(false, null, null, true, dbgJavaMlcs_ifNotNull, dbgFilter_ifNonNull);
   }
   /**
      <P>Eliminates the package declaration line.</P>

      @return  <CODE>{@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine}(true, dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull, false, null, dbgFilter_ifNonNull)</CODE>
    **/
   public static final TextLineFilter eliminatePackageLine(Appendable dbgPkgLnValidFilter_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull, Appendable dbgFilter_ifNonNull)  {
      return  eliminateAllCmtBlocksAndPackageLine(true, dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull, false, null, dbgFilter_ifNonNull);
   }

   /**
      <P>Eliminates the package declaration line and all multi-line comment blocks.</P>

      @param  doDelete_pkgDecl  If {@code true}, the package declaration line is eliminated. If {@code false}, the alterer is not created ({@code line()} is not called, and both {@code dbgPkgLnValidFilter_ifNonNull} and {@code dbgPkgLnVldtr_ifNonNull} are ignored). At least one boolean parameter must be {@code true}.
      @param  doElim_multiLineCmts  If {@code true}, all multi-line comments are deleted. If {@code false}, {@code allJavaMultiLineComments} is not called (and {@code dbgJavaMlcs_ifNotNull} is ignored). Warning: This eliminates <I>entire lines</I>, including any text that happens to be outside of the block:
      <BR> &nbsp; &nbsp; <CODE>This text will also be eliminated  /&#42; The start of a comment...</CODE>)
      @return
<BLOCKQUOTE><PRE>new {@link com.github.xbn.linefilter.z.TextLineFilter_Cfg#TextLineFilter_Cfg() TextLineFilter_Cfg}().
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#allJavaMultiLineComments(Appendable) allJavaMultiLineComments}(dbgJavaMlcs_ifNotNull).{@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#line(ValueAlterer) line}(alterer).
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines) ifBlockOrLineOrNotActive}({@link com.github.xbn.linefilter.z.ActiveBlockLines ActiveBlockLines}.{@link com.github.xbn.linefilter.z.ActiveBlockLines#DISCARD DISCARD}, {@link com.github.xbn.linefilter.z.ActiveSingleLines ActiveSingleLines}.{@link com.github.xbn.linefilter.z.ActiveSingleLines#DISCARD DISCARD}, {@link com.github.xbn.linefilter.z.InactiveLines InactiveLines}.{@link com.github.xbn.linefilter.z.InactiveLines#KEEP KEEP}).
   {@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(dbgFilter_ifNonNull).{@link com.github.xbn.linefilter.z.TextLineFilter_CfgForNeeder#build() build}()</PRE></BLOCKQUOTE>
   Where {@code alterer} is a
   <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor NewLineAltererFor}.{@link com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminatePackageLine(Appendable, Appendable) eliminatePackageLine}(dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull)</CODE>
   	@see  #eliminateAllCmtBlocksAndPackageLine(CodeletInstance, String, boolean, boolean) eliminateAllCmtBlocksAndPackageLine(CodeletInstance, String, *)
   	@see  #eliminateAllMultiLineComments(Appendable, Appendable) eliminateAllMultiLineComments
   	@see  #eliminatePackageLine(Appendable, Appendable, Appendable) eliminatePackageLine
    **/
   public static final TextLineFilter eliminateAllCmtBlocksAndPackageLine(boolean doDelete_pkgDecl, Appendable dbgPkgLnValidFilter_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull, boolean doElim_multiLineCmts, Appendable dbgJavaMlcs_ifNotNull, Appendable dbgFilter_ifNonNull)  {
      if(!doElim_multiLineCmts  &&  !doDelete_pkgDecl)  {
         throw  new IllegalArgumentStateException("doElim_multiLineCmts and doDelete_pkgDecl and both false. Nothing to do.");
      }
      TextLineFilter_CfgForNeeder<TextLineFilter,DummyForNoNeeder> filterCfg = new TextLineFilter_Cfg().
         ifBlockOrLineOrNotActive(ActiveBlockLines.DISCARD, ActiveSingleLines.DISCARD, InactiveLines.KEEP).
         debugTo(dbgFilter_ifNonNull);

      if(doDelete_pkgDecl)  {
         TextLineAlterer alterer = NewLineAltererFor.eliminatePackageLine(dbgPkgLnValidFilter_ifNonNull, dbgPkgLnVldtr_ifNonNull);

         filterCfg.line(alterer);
      }

      return  (doElim_multiLineCmts
         ?  filterCfg.allJavaMultiLineComments(dbgJavaMlcs_ifNotNull).build()
         :  filterCfg.build());
   }
   /**
      <P>Create a new eliminate-comment-blocks-and-package-line filter with named debuggers.</P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #eliminateAllCmtBlocksAndPackageLine(boolean, Appendable, Appendable, boolean, Appendable, Appendable) eliminateAllCmtBlocksAndPackageLine} parameters:<UL>
         <LI><CODE><I>[named_debugPrefix]</I>.filter</CODE>: {@code dbgFilter_ifNonNull}<UL>
            <LI>{@code .packageline}: {@code dbgPkgLnValidFilter_ifNonNull}</LI>
            <LI>{@code .packageline.validfilter}: {@code dbgPkgLnVldtr_ifNonNull}</LI>
            <LI>{@code .javamlcs}: {@code dbgJavaMlcs_ifNotNull}</LI>
         </UL></LI>
      </UL>All of which must be added to the named-debug-level configuration file:</P>

<BLOCKQUOTE><PRE>PREFIX.filter=-1
PREFIX.filter.packageline=-1
PREFIX.filter.packageline.validfilter=-1
PREFIX.filter.javamlcs=-1</PRE></BLOCKQUOTE>

      @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
      @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    **/
   public static final TextLineFilter eliminateAllCmtBlocksAndPackageLine(CodeletInstance instance, String named_debugPrefix, boolean doDelete_pkgDecl, boolean doElim_multiLineCmts)  {
      CrashIfString.nullEmpty(named_debugPrefix, "named_debugPrefix", null);
      String prefixFltr = named_debugPrefix + ".filter";
      Appendable dbgPackageLine = getDebugApblIfOn(instance,
         prefixFltr + ".packageline");
      Appendable dbgPackageLineValidFltr = getDebugApblIfOn(instance,
         prefixFltr + ".packageline.validfilter");
      Appendable dbgMlcBlkStartEnd = getDebugApblIfOn(instance,
         prefixFltr + ".javamlcs");
      Appendable dbgFilter = getDebugApblIfOn(instance, prefixFltr);

      return  	eliminateAllCmtBlocksAndPackageLine(doDelete_pkgDecl,
            dbgPackageLine,           //dbgPkgLnValidFilter_ifNonNull
            dbgPackageLineValidFltr,  //dbgPkgLnVldtr_ifNonNull
         doElim_multiLineCmts,
            dbgMlcBlkStartEnd,        //dbgJavaMlcs_ifNotNull
            dbgFilter);               //dbgFilter_ifNonNull
   }
      /**
      <P><I>A code snippet:</I> Keeps all lines in a specific range, based on the text existing in the first and last lines.</P>

      @param  startAppearance_num  If the start-line search term is found on multiple lines, this is the desired occurance (if two, the second line the term is found on).
      @param  is_startLineRegex  If {@code true} {@code startLine_findWhat} is treated as a regular expression. If {@code false}, literal.
      @param  startLine_findWhat  The start-line search term. This is the text or pattern that exists in the first line that should be kept. May not be {@code null} or empty.
      @param  endAppearance_num  If the end-line search term is found on multiple lines, this is the desired occurance. This is the appearance of the term <I>in the entire document</I>, including any appearances before the start line.
      @param  is_endLineRegex  If {@code true} {@code startLine_findWhat} is treated as a regular expression.
      @param  endLine_findWhat  The start-line search term.
      @return
<BLOCKQUOTE><PRE>{@link com.github.xbn.linefilter.NewTextLineFilterFor NewTextLineFilterFor}.{@link com.github.xbn.linefilter.NewTextLineFilterFor#lineRange(BlockMarks, Pattern, ValidResultFilter, Appendable, Pattern, ValidResultFilter, Appendable, Appendable) lineRange}({@link com.github.xbn.linefilter.BlockMarks}.{@link com.github.xbn.linefilter.BlockMarks#INCLUSIVE INCLUSIVE},
   <I>[start-pattern]</I>, startFilter, dbgStart_ifNonNull,
   <I>[end-pattern]</I>, endFilter, dbgEnd_ifNonNull,
      dbgFilter_ifNonNull)</PRE></BLOCKQUOTE>
      Where {@code startFilter} is a
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.analyze.validate.NewValidResultFilterFor NewValidResultFilterFor}.{@link com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) exactly}(
         <BR> &nbsp; &nbsp; &nbsp; &nbsp; startAppearance_num, &quot;startAppearance_num&quot;, dbgStartFilter_ifNonNull)</CODE>
      @see  #lineRange(CodeletInstance, String, int, boolean, String, int, boolean, String) lineRange(CodeletInstance, String, *)
      @see  #lineRangeWithReplace(int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, Appendable) lineRangeWithReplace
    **/
   public static final TextLineFilter lineRange(int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, Appendable dbgStartFilter_ifNonNull, Appendable dbgStart_ifNonNull, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, Appendable dbgEndFilter_ifNonNull, Appendable dbgEnd_ifNonNull, Appendable dbgFilter_ifNonNull) throws PatternSyntaxException  {
      Pattern startPrtn = NewPatternFor.regexIfTrueLiteralIfFalse(is_startLineRegex, startLine_findWhat, "startLine_findWhat");
      Pattern endPrtn = NewPatternFor.regexIfTrueLiteralIfFalse(is_endLineRegex, endLine_findWhat, "endLine_findWhat");

      ValidResultFilter startFilter = NewValidResultFilterFor.exactly(
         startAppearance_num, "startAppearance_num",
         dbgStartFilter_ifNonNull);
      ValidResultFilter endFilter = NewValidResultFilterFor.exactly(
         endAppearance_num, "endAppearance_num",
         dbgEndFilter_ifNonNull);

      return  NewTextLineFilterFor.lineRange(BlockMarks.INCLUSIVE,
         startPrtn, startFilter, dbgStart_ifNonNull,
         endPrtn, endFilter, dbgEnd_ifNonNull,
         dbgFilter_ifNonNull);
   }
   /**
      <P>Create a new line-range filter with named debuggers.</P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable) lineRange} parameters:<UL>
         <LI><CODE><I>[named_debugPrefix]</I>.filter</CODE>: {@code dbgFilter_ifNonNull}<UL>
            <LI>{@code .blockstart}: {@code dbgStart_ifNonNull}</LI>
            <LI>{@code .blockstart.validfilter}: {@code dbgStartFilter_ifNonNull}</LI>
            <LI>{@code .blockend}: {@code dbgEnd_ifNonNull}</LI>
            <LI>{@code .blockend.validfilter}: {@code dbgEndFilter_ifNonNull}</LI>
         </UL></LI>
      </UL>All of which must be added to the named-debug-level configuration file:</P>

<BLOCKQUOTE><PRE>PREFIX.filter=-1
PREFIX.filter.blockstart=-1
PREFIX.filter.blockstart.validfilter=-1
PREFIX.filter.blockend=-1
PREFIX.filter.blockend.validfilter=-1</PRE></BLOCKQUOTE>

      @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
      @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    **/
   public static final TextLineFilter lineRange(CodeletInstance instance, String named_debugPrefix, int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat) throws PatternSyntaxException  {
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
      Appendable dbgFilter = getDebugApblIfOn(instance, prefixFltr);

      return  lineRange(
         startAppearance_num, is_startLineRegex, startLine_findWhat,
            dbgBlkStartValidFltr, dbgBlkStart,
         endAppearance_num, is_endLineRegex, endLine_findWhat,
            dbgBlkEndValidFltr, dbgBlkEnd, dbgFilter);
   }
   /**
      <P>Keeps all lines in a specific range, based on the text existing in the first and last lines, making a replacement on the first and last lines only. This is useful when lines must be marked, but those marks should not be seen in the final output.</P>

      <P>For documentation on all other parameters, see {@link #lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable) lineRange}.</P>

      @param  startLine_rplcWith  The replacement term for the start-line search term ({@code startLine_findWhat}). May not be {@code null} or empty.
      @param  startRplcs_notMtchNum  In most cases, this should be set to {@code "FIRST"}. See <CODE>com.github.xbn.regexutil.{@link com.github.xbn.regexutil.ReplacedInEachInput}</CODE>.
      @param  endLine_rplcWith  The replacement term for the end-line search term ({@code endLine_findWhat}).
      @param  endRplcs_notMtchNum  In most cases, this should be set to {@code "FIRST"}.
      @return
<BLOCKQUOTE><PRE>{@link com.github.xbn.linefilter.NewTextLineFilterFor NewTextLineFilterFor}.{@link com.github.xbn.linefilter.NewTextLineFilterFor#lineRangeWithReplace(BlockMarks block_marksAre, Pattern start_ptrn, String start_rplcWith, ReplacedInEachInput startRplcWhat_notMatchNums, ValidResultFilter start_filter, Appendable dbgStart_ifNonNull, Pattern end_ptrn, String end_rplcWith, ReplacedInEachInput endRplcWhat_notMatchNums, ValidResultFilter end_filter, Appendable dbgEnd_ifNonNull, Appendable dbgFilter_ifNonNull) lineRangeWithReplace}({@link com.github.xbn.linefilter.BlockMarks}.{@link com.github.xbn.linefilter.BlockMarks#INCLUSIVE INCLUSIVE},
   startPrtn, startLine_rplcWith, startRplcs_notMtchNum,
      startFilter, dbgStartRplcr_ifNonNull,
   endPrtn, endLine_rplcWith, endRplcs_notMtchNum,
      endFilter, dbgEndRplcr_ifNonNull,
   dbgFilter_ifNonNull)</PRE></BLOCKQUOTE>
   	@see  #lineRange(CodeletInstance, String, int, boolean, String, int, boolean, String) lineRange(CodeletInstance, String, *)
    **/
   public static final TextLineFilter lineRangeWithReplace(int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, ReplacedInEachInput startRplcs_notMtchNum, Appendable dbgStartFilter_ifNonNull, Appendable dbgStartRplcr_ifNonNull, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, ReplacedInEachInput endRplcs_notMtchNum, Appendable dbgEndFilter_ifNonNull, Appendable dbgEndRplcr_ifNonNull, Appendable dbgFilter_ifNonNull) throws PatternSyntaxException  {
      Pattern startPrtn = NewPatternFor.regexIfTrueLiteralIfFalse(is_startLineRegex, startLine_findWhat, "startLine_findWhat");
      Pattern endPrtn = NewPatternFor.regexIfTrueLiteralIfFalse(is_endLineRegex, endLine_findWhat, "endLine_findWhat");

      ValidResultFilter startFilter = NewValidResultFilterFor.exactly(
         startAppearance_num, "startAppearance_num",
         dbgStartFilter_ifNonNull);
      ValidResultFilter endFilter = NewValidResultFilterFor.exactly(
         endAppearance_num, "endAppearance_num",
         dbgEndFilter_ifNonNull);

      return  NewTextLineFilterFor.lineRangeWithReplace(BlockMarks.INCLUSIVE,
         startPrtn, startLine_rplcWith, startRplcs_notMtchNum,
            startFilter, dbgStartRplcr_ifNonNull,
         endPrtn, endLine_rplcWith, endRplcs_notMtchNum,
            endFilter, dbgEndRplcr_ifNonNull,
         dbgFilter_ifNonNull);
   }
   /**
      <P>Create a new line-range filter with named debuggers.</P>

      <P>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #lineRangeWithReplace(int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, int, boolean, String, String, ReplacedInEachInput, Appendable, Appendable, Appendable) lineRangeWithReplace} parameters:<UL>
         <LI><CODE><I>[named_debugPrefix]</I>.filter</CODE>: {@code dbgFilter_ifNonNull}<UL>
            <LI>{@code .blockstart}: {@code dbgStartRplcr_ifNonNull}</LI>
            <LI>{@code .blockstart.validfilter}: {@code dbgStartFilter_ifNonNull}</LI>
            <LI>{@code .blockend}: {@code dbgEndRplcr_ifNonNull}</LI>
            <LI>{@code .blockend.validfilter}: {@code dbgEndFilter_ifNonNull}</LI>
         </UL></LI>
      </UL>All of which must be added to the named-debug-level configuration file:</P>

<BLOCKQUOTE><PRE>PREFIX.filter=-1
PREFIX.filter.blockstart=-1
PREFIX.filter.blockstart.validfilter=-1
PREFIX.filter.blockend=-1
PREFIX.filter.blockend.validfilter=-1</PRE></BLOCKQUOTE>

      @param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
      @param  named_debugPrefix  Prepended to all named debuggers. May not be {@code null} or empty.
    **/
   public static final TextLineFilter lineRangeWithReplace(CodeletInstance instance, String named_debugPrefix, int startAppearance_num, boolean is_startLineRegex, String startLine_findWhat, String startLine_rplcWith, ReplacedInEachInput startRplcs_notMtchNum, int endAppearance_num, boolean is_endLineRegex, String endLine_findWhat, String endLine_rplcWith, ReplacedInEachInput endRplcs_notMtchNum) throws PatternSyntaxException  {
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
      Appendable dbgFilter = getDebugApblIfOn(instance, prefixFltr);

      return  lineRangeWithReplace(
         startAppearance_num, is_startLineRegex,
         startLine_findWhat, startLine_rplcWith, startRplcs_notMtchNum,
            dbgBlkStartValidFltr, dbgBlkStart,
         endAppearance_num, is_endLineRegex,
         endLine_findWhat, endLine_rplcWith, endRplcs_notMtchNum,
            dbgBlkEndValidFltr, dbgBlkEnd,
            dbgFilter);
   }
   private NewLineFilterFor()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
