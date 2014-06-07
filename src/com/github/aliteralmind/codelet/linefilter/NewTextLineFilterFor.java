/*license*\
   Codelet

   Copyright (c) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.linefilter;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.aliteralmind.codelet.linefilter.z.BlockEnd;
   import  com.github.aliteralmind.codelet.linefilter.BlockMarks;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveBlockLines;
   import  com.github.aliteralmind.codelet.linefilter.z.InactiveLines;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveSingleLines;
   import  java.util.regex.Pattern;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg;
/**
   <P>Convenience functions for creating text-line filters.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class NewTextLineFilterFor  {
   /**
      <P>Keeps all lines.</P>

      @return  <CODE>new {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg#TextLineFilter_Cfg() TextLineFilter_Cfg}().{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(debug_ifNonNull).{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#build() build}()</CODE>
    **/
   public static final TextLineFilter unfiltered(Appendable debug_ifNonNull)  {
      return  new TextLineFilter_Cfg().debugTo(debug_ifNonNull).build();
   }
   /**
      <P>Keeps all lines in a specific range, based on the text existing in the first and last lines.</P>

      @param  start_ptrn  The pattern matching the start line. <I>Should</I> be a pattern that matches exactly one line in the input.
      @param  end_ptrn  The pattern matching the end line. <I>Should</I> be a pattern that matches exactly one line following the start line. If {@code null}, then all lines following the start-pattern are kept.
      @return  <PRE>new {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg#TextLineFilter_Cfg() TextLineFilter_Cfg}.
   {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#start(Pattern, ValidResultFilter, Appendable) start} (start_ptrn, null, dbgStart_ifNonNull).
   {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#end(BlockEnd, Pattern, ValidResultFilter, Appendable) end}({@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd BlockEnd}.{@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd#REQUIRED REQUIRED}, end_ptrn, null, dbgEnd_ifNonNull).
   blockMarksInclusive(block_marksAre.{@link com.github.aliteralmind.codelet.linefilter.BlockMarks#areInclusive() inclusive}()).
   ifBlockOrLineOrNotActive({@link com.github.aliteralmind.codelet.linefilter.z.ActiveBlockLines ActiveBlockLines}.{@link com.github.aliteralmind.codelet.linefilter.z.ActiveBlockLines#KEEP KEEP}, {@link com.github.aliteralmind.codelet.linefilter.z.ActiveSingleLines ActiveSingleLines}.{@link com.github.aliteralmind.codelet.linefilter.z.ActiveSingleLines#DISCARD DISCARD}, {@link com.github.aliteralmind.codelet.linefilter.z.InactiveLines InactiveLines}.{@link com.github.aliteralmind.codelet.linefilter.z.InactiveLines#DISCARD DISCARD}).
   {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(dbgMode_ifNonNull).{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#build() build}();</PRE>
    **/
   public static final TextLineFilter lineRange(BlockMarks block_marksAre, Pattern start_ptrn, ValidResultFilter start_filter, Appendable dbgStart_ifNonNull, Pattern end_ptrn, ValidResultFilter end_filter, Appendable dbgEnd_ifNonNull, Appendable dbgMode_ifNonNull)  {
      BlockEnd blkEndIs = BlockEnd.REQUIRED;
      if(end_ptrn == null)  {
         blkEndIs = BlockEnd.OPTIONAL;
         end_ptrn = NewPatternFor.IMPOSSIBLE_TO_MATCH;
      }

      try  {
         return  new TextLineFilter_Cfg().
            start(start_ptrn, start_filter, dbgStart_ifNonNull).
            end(blkEndIs, end_ptrn, end_filter, dbgEnd_ifNonNull).
            blockMarksInclusive(block_marksAre.areInclusive()).
            ifBlockOrLineOrNotActive(ActiveBlockLines.KEEP, ActiveSingleLines.DISCARD, InactiveLines.DISCARD).
            debugTo(dbgMode_ifNonNull).build();
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(block_marksAre, "block_marksAre", null);
         CrashIfObject.nnull(start_ptrn, "start_ptrn", null);
         throw  CrashIfObject.nullOrReturnCause(end_ptrn, "end_ptrn", null, rx);
      }
   }
   /**
      <P>Keeps all lines in a specific range, based on the text existing in the first and last lines, making a replacement on the first and last lines only. This is useful when lines must be marked, but those marks should not be seen in the final output.</P>

      <P><B>Input</B></P>

{@.file.textlet com\\github\\aliteralmind\\codelet\\examples\\linefilter\\doc-files\\BetweenTwoLinesWithReplaceXmpl_input.txt}

{@.codelet.and.out com.github.aliteralmind.codelet.examples.linefilter.BetweenTwoLinesWithReplaceXmpl("examples\\com\\github\\aliteralmind\\codelet\\examples\\linefilter\\doc-files\\BetweenTwoLinesWithReplaceXmpl_input.txt"):eliminateCmtBlocksPkgLineAndPkgReferences(true, true, false)}

    **/
   public static final TextLineFilter lineRangeWithReplace(BlockMarks block_marksAre, Pattern start_ptrn, String start_rplcWith, ReplacedInEachInput startRplcWhat_notMatchNums, ValidResultFilter start_filter, Appendable dbgStart_ifNonNull, Pattern end_ptrn, String end_rplcWith, ReplacedInEachInput endRplcWhat_notMatchNums, ValidResultFilter end_filter, Appendable dbgEnd_ifNonNull, Appendable dbgMode_ifNonNull)  {
      BlockEnd blkEndIs = BlockEnd.REQUIRED;
      if(end_ptrn == null)  {
         blkEndIs = BlockEnd.OPTIONAL;
         end_ptrn = NewPatternFor.IMPOSSIBLE_TO_MATCH;
      }

      try  {
         return  new TextLineFilter_Cfg().
            startReplacer(start_ptrn, start_rplcWith, startRplcWhat_notMatchNums, start_filter, dbgStart_ifNonNull).
            endReplacer(blkEndIs, end_ptrn, end_rplcWith, endRplcWhat_notMatchNums, end_filter, dbgEnd_ifNonNull).
            blockMarksInclusive(block_marksAre.areInclusive()).
            ifBlockOrLineOrNotActive(ActiveBlockLines.KEEP, ActiveSingleLines.DISCARD, InactiveLines.DISCARD).
            debugTo(dbgMode_ifNonNull).build();
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(block_marksAre, "block_marksAre", null);
         CrashIfObject.nnull(start_ptrn, "start_ptrn", null);
         throw  CrashIfObject.nullOrReturnCause(end_ptrn, "end_ptrn", null, rx);
      }
   }
}
