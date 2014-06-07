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
package  com.github.aliteralmind.codelet.linefilter.z;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.analyze.validate.ValueValidator;
   import  com.github.xbn.neederneedable.Needer;
   import  com.github.xbn.neederneedable.NeederComposer;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.number.LengthInRange;
   import  com.github.xbn.number.LengthInRangeValidator;
   import  com.github.xbn.number.z.LengthInRangeValidator_CfgForNeeder;
   import  com.github.aliteralmind.codelet.linefilter.LineObject;
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAltererAdapter;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo;
   import  com.github.xbn.regexutil.RegexReplacer;
   import  com.github.xbn.regexutil.RegexValidator;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.StringReplacer;
   import  com.github.xbn.regexutil.z.RegexReplacer_Cfg;
   import  com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder;
   import  com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder;
   import  java.util.regex.Pattern;
/**
   <P>For <A HREF="{@docRoot}/com/github/xbn/chain/Needable.html#indirect">indirectly</A> configuring a {@link com.github.aliteralmind.codelet.linefilter.TextLineFilter TextLineFilter}.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TextLineFilter_CfgForNeeder<M extends TextLineFilter,R extends Needer> extends LineFilter_CfgForNeeder<String,LineObject<String>,M,R> implements Needer, LineFilter_Fieldable<String,LineObject<String>>  {
//internal
   private SetEndOrLine endOrLn;
   private boolean bForLnNumOrTxt;
   private final NeederComposer ndrc;
   private ValidResultFilter vrfForNeededRR;
//public
   public static enum SetEndOrLine {START, END, LINE;};
//constructors
   /**
      <P>Create a new instance, for the root-mode only.</P>

      <P>This calls<OL>
         <LI><CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="LineFilter_CfgForNeeder.html#LineFilter_CfgForNeeder(R)">super</A>(needer)</CODE></LI>
         <LI>{@link #resetTLMCFN() resetTLMCFN}{@code ()}</LI>
      </OL></P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#TextLineFilter_CfgForNeeder(R, int, java.lang.String)">this</A>(R)</CODE>
    **/
   public TextLineFilter_CfgForNeeder(R needer)  {
      super(needer);
      ndrc = new NeederComposer();
      resetTLMCFN();
   }
   /**
      <P>Create a new instance for a sub-mode.</P>

      <P>This calls<OL>
         <LI><CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="LineFilter_CfgForNeeder.html#LineFilter_CfgForNeeder(R, int, String)">super</A>(needer, level_num, name)</CODE></LI>
         <LI>{@link #resetTLMCFN() resetTLMCFN}{@code ()}</LI>
      </OL></P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#TextLineFilter_CfgForNeeder(R)">this</A>(R)</CODE>
    **/
   public TextLineFilter_CfgForNeeder(R needer, int level_num, String name)  {
      super(needer, level_num, name);
      ndrc = new NeederComposer();
      resetTLMCFN();
   }
//self-returners...START
   /**
      <P>Reset configuration so no lines are active.</P>

      <P>This calls<OL>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#reset() reset}{@code ()}</LI>
         <LI>{@link #resetTLMCFN() resetTLMCFN}{@code ()}</LI>
      </OL></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> reset()  {
      super.reset();
      resetTLMCFN();
      return  this;
   }
   /**
      <P>Reset configuration specific to this {@code TextLineFilter_CfgForNeeder}.</P>

      <P>This sets some internal-only values.</P>
    **/
   protected final void resetTLMCFN()  {
      endOrLn = null;
      bForLnNumOrTxt = false;
      vrfForNeededRR = null;
   }
   /**
      <P>Set information to append to error messages.</P>

      @param  info  If non-{@code null}, this object's {@code toString()} is appended to error messages. Get with {@link com.github.xbn.lang.ExtraErrInfoable#getExtraErrInfo() getExtraErrInfo}{@code ()}*
      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> extraErrInfo(Object info)  {
      super.extraErrInfo(info);
      return  this;
   }
   /**
      <P>Set block marks as inclusive.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksInclusive() blockMarksInclusive}()</CODE></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> blockMarksInclusive()  {
      super.blockMarksInclusive();
      return  this;
   }
   /**
      <P>Set block marks as exclusive.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}()</CODE></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> blockMarksExclusive()  {
      super.blockMarksExclusive();
      return  this;
   }
   /**
      <P>Set block marks as exclusive.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksInclusive(boolean) blockMarksInclusive}(are_inclusive)</CODE></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> blockMarksInclusive(boolean are_inclusive)  {
      super.blockMarksInclusive(are_inclusive);
      return  this;
   }
   /**
      <P>Declare the block start-and-end marks as a JavaDoc block: <CODE>&quot;/<!--->**&quot;</CODE> and <CODE>&quot;**<!--->/&quot;</CODE>--the end mark must contain two asterisks, in order to not be confused  with the regular Java multi-line comments.</P>

      <P>This sets<UL>
         <LI>The {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterStart() start-alterer} to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(validator)</CODE>
         <BR>Where {@code validator} is equal to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.regexutil.z.RegexValidator_Cfg#RegexValidator_Cfg() RegexValidator_Cfg}().{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#literal(String) literal}(&quot;/<!--->**&quot;).{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#useFind() useFind}.{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#debugTo(Appendable) debugTo}(dbgDest_ifNonNull).{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#build() build}()</CODE></LI>
         <LI>The {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterStart() end-alterer} with the same (kind of) object, but with the literal term <CODE>&quot;**<!--->/&quot;</CODE> (block-end is {@link #isEndRequired() required})</LI>
      </UL></P>

      @see  #javaBlock(Appendable) javaBlock(apbl)
      @see  #javaLineComment(Appendable) javaLineComment(apbl)
    **/
   public TextLineFilter_CfgForNeeder<M,R> javaDocBlock(Appendable dbgDest_ifNonNull)  {
      TextLineFilter_CfgForNeeder<M,R> tlmcfn = cfgStartPattern().literal("/**").useFind().debugTo(dbgDest_ifNonNull).endCfg();
      return  cfgEndPattern(BlockEnd.REQUIRED).literal("**/").useFind().debugTo(dbgDest_ifNonNull).endCfg();
   }
   /**
      <P>Declare the block start-and-end marks as a Java comment block: <CODE>&quot;/<!--->*&quot;</CODE> and <CODE>&quot;*<!--->/&quot;</CODE>.</P>

      <P>This does the same as {@link #javaDocBlock(Appendable) javaDocBlock}{@code (apbl)}, except the search terms are the following regular expresions (not literal):<UL>
         <LI><B>Start:</B> {@code "/\\*(?!\\*)"}</LI>
         <LI><B>End:</B> {@code "(?<!\\*)\\*" + "/"}</LI>
      </UL>The lookahead and lookbehind is necessary to <I>not</I> match JavaDoc blocks (in order to use <CODE>TextLineFilter</CODE>, it is required that JavaDoc blocks end with two asterisks: {@code **<!--->/}).</P>

      @see  #javaDocBlock(Appendable)
      @see  #javaLineComment(Appendable) javaLineComment(apbl)
    **/
   public TextLineFilter_CfgForNeeder<M,R> javaBlock(Appendable dbgDest_ifNonNull)  {
      return  cfgStartPattern().regex("/\\*(?!\\*)").useFind().debugTo(dbgDest_ifNonNull).endCfg().
      cfgEndPattern(BlockEnd.REQUIRED).regex("(?<!\\*)\\*" + "/").useFind().debugTo(dbgDest_ifNonNull).endCfg();
   }
   /**
      <P>Declare the block start-and-end marks as a Java <I>or JavaDoc</I> comment block: <CODE>&quot;/<!--->*&quot;</CODE> and <CODE>&quot;*<!--->/&quot;</CODE>. An example use is for identifying and stripping all comment blocks out of source code.</P>

      <P>This does the same as {@link #javaBlock(Appendable) javaBlock}{@code (apbl)}, except the search terms  are literal (and do not include the lookahead and lookbehind):<UL>
         <LI><B>Start:</B> {@code "/" + "*"}</LI>
         <LI><B>End:</B> {@code "*" + "/"}</LI>
      </UL></P>

      @see  #javaDocBlock(Appendable)
      @see  #javaLineComment(Appendable) javaLineComment(apbl)
    **/
   public TextLineFilter_CfgForNeeder<M,R> allJavaMultiLineComments(Appendable dbgDest_ifNonNull)  {
      return  cfgStartPattern().literal("/" + "*").useFind().debugTo(dbgDest_ifNonNull).endCfg().
      cfgEndPattern(BlockEnd.REQUIRED).literal("*" + "/").useFind().debugTo(dbgDest_ifNonNull).endCfg();
   }
   /**
      <P>Declare the single line-entity to be a Java-comment: {@code /<!--->/}.</P>

      <P>This sets the {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterLine() line-alterer} to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(validator)</CODE>
      <BR>Where {@code validator} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.regexutil.z.RegexValidator_Cfg#RegexValidator_Cfg() RegexValidator_Cfg}().{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#literal(String) literal}(&quot;/<!--->/&quot;).{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#useFind() useFind}.{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#debugTo(Appendable) debugTo}(dbgDest_ifNonNull).{@link com.github.xbn.regexutil.z.RegexValidator_CfgForNeeder#build() build}()</CODE></P>

      @see  #javaDocBlock(Appendable) javaDocBlock(apbl)
    **/
   public TextLineFilter_CfgForNeeder<M,R> javaLineComment(Appendable dbgDest_ifNonNull)  {
      return  cfgLinePattern().literal("/" + "/").useFind().debugTo(dbgDest_ifNonNull).endCfg();
   }
   /**
      <P>Configure a {@code RegexReplacer} that will detect <I>and alter</I> the block-start line. End configuration with <CODE>{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder RegexReplacer_CfgForNeeder}.{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#endCfg() endCfg}</CODE> (instead of {@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#build() build}{@code ()}).</P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/text/regex/RegexReplacer.html#cfg">RegexReplacer:config</A></CODE>
      @see  #cfgEndReplacer(BlockEnd, ValidResultFilter) (bei,vrf)
      @see  #cfgLineReplacer(ValidResultFilter) cfgLineReplacer(vrf)
    **/
   public RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>> cfgStartReplacer(ValidResultFilter filter)  {
      return  cfgForReplacer(SetEndOrLine.START, filter);
   }
   /**
      <P>Configure a {@code RegexReplacer} that will detect <I>and alter</I> block-end lines (which are required).</P>

      <P>This sets<UL>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterEnd() getAlterEnd}{@code ()}* to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(RegexReplacer, ValidResultFilter) lineReplacer}(<I>[the-configured-RegexReplacer]</I>, filter)</CODE></LI>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#isEndRequired() isEndRequired}{@code ()} to {@code true}</LI>
      </UL></P>

      @see  #cfgStartReplacer(ValidResultFilter)
    **/
   public RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>> cfgEndReplacer(BlockEnd end_is, ValidResultFilter filter)  {
      bBlockEndRqd = true;
      return  cfgForReplacer(SetEndOrLine.END, filter);
   }
   /**
      <P>Configure a {@code RegexReplacer} that will detect <I>and alter</I> block-end lines (which are optional).</P>

      <P>This sets<UL>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterEnd() getAlterEnd}{@code ()}* to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(RegexReplacer, ValidResultFilter) lineReplacer}(<I>[the-configured-RegexReplacer]</I>, filter)</CODE></LI>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#isEndRequired() isEndRequired}{@code ()} to {@code false}</LI>
      </UL></P>

      @see  #cfgStartReplacer(ValidResultFilter)
   public RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>> cfgOptionalEndReplacer(ValidResultFilter filter)  {
      bBlockEndRqd = false;
      return  cfgForReplacer(SetEndOrLine.END, filter);
   }
    **/
   /**
      <P>Configure a {@code RegexReplacer} that will detect <I>and alter</I> single-line entities.</P>

      <P>This sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterLine() getAlterLine}{@code ()}* to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(RegexReplacer, ValidResultFilter) lineReplacer}(<I>[the-configured-RegexReplacer]</I>, filter)</CODE></P>

      @see  #cfgStartReplacer(ValidResultFilter)
    **/
   public RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>> cfgLineReplacer(ValidResultFilter filter)  {
      return  cfgForReplacer(SetEndOrLine.LINE, filter);
   }
      private RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>> cfgForReplacer(SetEndOrLine end_orLn, ValidResultFilter filter)  {
         endOrLn = end_orLn;
         bForLnNumOrTxt = false;
         vrfForNeededRR = filter;
         return  (new RegexReplacer_CfgForNeeder<RegexReplacer,TextLineFilter_CfgForNeeder<M,R>>(this));
      }
   /**
      <P>Configure a {@code RegexValidator} that detects block-start lines.</P>

      <P>This sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterStart() getAlterStart}{@code ()}* to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(<I>[the-configured-RegexValidator]</I>, null)</CODE></P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/text/regex/RegexValidator.html#cfg">RegexReplacer:config</A></CODE>
      @see  #cfgEndPattern(BlockEnd) cfgEndPattern(bei)
      @see  #cfgLinePattern()
    **/
   public RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>> cfgStartPattern()  {
      return  cfgForPattern(SetEndOrLine.START);
   }
   /**
      <P>Configure a {@code RegexValidator} that detects block-end lines.</P>

      <P>This sets<UL>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterEnd() getAlterEnd}{@code ()}* to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(<I>[the-configured-RegexValidator]</I>, null)</CODE></LI>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#isEndRequired() isEndRequired}{@code ()} to {@code true}</LI>
      </UL></P>

      @see  #cfgStartPattern()
    **/
   public RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>> cfgEndPattern(BlockEnd end_is)  {
      try  {
         bBlockEndRqd = end_is.isRequired();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(end_is, "end_is", null, rx);
      }
      return  cfgForPattern(SetEndOrLine.END);
   }
   /**
      <P>Configure a {@code RegexValidator} that detects block-end lines (which are optional).</P>

      <P>This sets<UL>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterEnd() getAlterEnd}{@code ()}* to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(<I>[the-configured-RegexValidator]</I>, null)</CODE></LI>
         <LI>{@link com.github.aliteralmind.codelet.linefilter.LineFilter#isEndRequired() isEndRequired}{@code ()} to {@code false}</LI>
      </UL></P>

      @see  #cfgStartPattern()
   public RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>> cfgOptionalEndPattern()  {
      bBlockEndRqd = false;
      return  cfgForPattern(SetEndOrLine.END);
   }
    **/
   /**
      <P>Configure a {@code RegexValidator} that detects single-line entities.</P>

      <P>This sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterLine() getAlterLine}{@code ()}* to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(ValueValidator) text}(<I>[the-configured-RegexValidator]</I>, null)</CODE></P>

      @see  #cfgStartPattern()
    **/
   public RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>> cfgLinePattern()  {
      return  cfgForPattern(SetEndOrLine.LINE);
   }
      private RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>> cfgForPattern(SetEndOrLine end_orLn)  {
         endOrLn = end_orLn;
         bForLnNumOrTxt = false;
         vrfForNeededRR = null;  //Only used for RegexReplacers
         return  (new RegexValidator_CfgForNeeder<RegexValidator,TextLineFilter_CfgForNeeder<M,R>>(this));
      }
   /**
      <P>Configure an int-validator detects block start lines based on their line number. End this configuration by calling {@link com.github.xbn.number.z.LengthInRangeValidator_CfgForNeeder#endCfg() endCfg}{@code ()} (instead of {@link com.github.xbn.number.z.LengthInRangeValidator_CfgForNeeder#build() build}{@code ()})</P>

      <P>The sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter LineFilter}.{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterStart() getAlterStart}{@code ()}* to the </P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/number/LengthInRangeValidator.html#cfg">LengthInRangeValidator:config</A></CODE>
      @see  #cfgLineLineNum()
    **/
   public LengthInRangeValidator_CfgForNeeder<LengthInRange,LengthInRangeValidator,TextLineFilter_CfgForNeeder<M,R>> cfgStartLineNum()  {
      return  cfgForLineNum(SetEndOrLine.START);
   }
   /**
      <P>Configure an int-validator detects block-end lines based on their line number.</P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/number/LengthInRangeValidator.html#cfg">LengthInRangeValidator:config</A></CODE>
      @see  #cfgLineLineNum()
    **/
   public LengthInRangeValidator_CfgForNeeder<LengthInRange,LengthInRangeValidator,TextLineFilter_CfgForNeeder<M,R>> cfgEndLineNum(BlockEnd end_is)  {
      try  {
         bBlockEndRqd = end_is.isRequired();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(end_is, "end_is", null, rx);
      }
      return  cfgForLineNum(SetEndOrLine.END);
   }
   /**
      <P>Configure an int-validator detects single-line entities based on their line number.</P>

      @see  #cfgStartLineNum()
    **/
   public LengthInRangeValidator_CfgForNeeder<LengthInRange,LengthInRangeValidator,TextLineFilter_CfgForNeeder<M,R>> cfgLineLineNum()  {
      return  cfgForLineNum(SetEndOrLine.LINE);
   }
      private LengthInRangeValidator_CfgForNeeder<LengthInRange,LengthInRangeValidator,TextLineFilter_CfgForNeeder<M,R>> cfgForLineNum(SetEndOrLine end_orLn)  {
         endOrLn = end_orLn;
         bForLnNumOrTxt = true;
         vrfForNeededRR = null;  //Only used for RegexReplacers
         return  (new LengthInRangeValidator_CfgForNeeder<LengthInRange,LengthInRangeValidator,TextLineFilter_CfgForNeeder<M,R>>(this));
      }
   /**
      <P>Set the block-start alterer.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#start(ValueAlterer) start}(alterer)</CODE></P>

      @return  <I>{@code this}</I>
      @see  #end(BlockEnd, ValueAlterer) end(bei,av)
      @see  #line(ValueAlterer) line(av)
    **/
   public TextLineFilter_CfgForNeeder<M,R> start(ValueAlterer<LineObject<String>,String> alterer)  {
      super.start(alterer);
      return  this;
   }
   /**
      <P>Set the block-end alterer.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#end(BlockEnd, ValueAlterer) end}(end_is, alterer)</CODE></P>

      @return  <I>{@code this}</I>
      @see  #start(ValueAlterer)
    **/
   public TextLineFilter_CfgForNeeder<M,R> end(BlockEnd end_is, ValueAlterer<LineObject<String>,String> alterer)  {
      super.end(end_is, alterer);
      return  this;
   }
   /**
      <P>Set the single-line entity alterer.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#line(ValueAlterer) line}(alterer)</CODE></P>

      @return  <I>{@code this}</I>
      @see  #start(ValueAlterer)
    **/
   public TextLineFilter_CfgForNeeder<M,R> line(ValueAlterer<LineObject<String>,String> alterer)  {
      super.line(alterer);
      return  this;
   }
   /**
      <P>Declare there are no sub-modes.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#noSubModes() noSubModes}()</CODE></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> noSubModes()  {
      super.noSubModes();
      return  this;
   }
   /**
      <P>Add all direct-child sub-modes.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#setSubModes(LineFilter[]) setSubModes}(sub_modes)</CODE></P>

      @return  <I>{@code this}</I>
      @see  #noSubModes()
    **/
   public TextLineFilter_CfgForNeeder<M,R> setSubModes(TextLineFilter[] sub_modes)  {
      super.setSubModes(sub_modes);
      return  this;
   }
   /**
      <P>Set the alterer that detects <I>and alters</I> block-start lines.</P>

      @return  <CODE>{@link #start(ValueAlterer) start}(txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) lineReplacer}(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #start(Pattern, ValidResultFilter, Appendable) start
      @see  #endReplacer(BlockEnd, Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) endReplacer
      @see  #lineReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) lineReplacer
    **/
   public TextLineFilter_CfgForNeeder<M,R> startReplacer(Pattern pattern_toFind, String direct_rplcWith, ReplacedInEachInput rplcWhat_notMatchNums, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      TextLineAltererAdapter<StringReplacer> txtLineAlterer = AdaptRegexReplacerTo.lineReplacer(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull);
      start(txtLineAlterer);
      return  this;
   }
   /**
      <P>Set the alterer that detects <I>and alters</I> block-end lines (which are optional).</P>

      @return  <CODE>{@link #end(BlockEnd, ValueAlterer) end}(end_is, txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) lineReplacer}(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #startReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) startReplacer
      @see  #end(BlockEnd, Pattern, ValidResultFilter, Appendable) end
    **/
   public TextLineFilter_CfgForNeeder<M,R> endReplacer(BlockEnd end_is, Pattern pattern_toFind, String direct_rplcWith, ReplacedInEachInput rplcWhat_notMatchNums, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      TextLineAltererAdapter<StringReplacer> txtLineAlterer = AdaptRegexReplacerTo.lineReplacer(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull);
      end(end_is, txtLineAlterer);
      return  this;
   }
   /**
      <P>Set the alterer that detects <I>and alters</I> single-line entities.</P>

      @return  <CODE>{@link #line(ValueAlterer) line}(txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) lineReplacer}(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #startReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable)
    **/
   public TextLineFilter_CfgForNeeder<M,R> lineReplacer(Pattern pattern_toFind, String direct_rplcWith, ReplacedInEachInput rplcWhat_notMatchNums, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      TextLineAltererAdapter<StringReplacer> txtLineAlterer = AdaptRegexReplacerTo.lineReplacer(pattern_toFind, direct_rplcWith, rplcWhat_notMatchNums, filter_ifNullOff, dbgDest_ifNonNull);
      line(txtLineAlterer);
      return  this;
   }
   /**
      <P>Set the alterer that detects block-start lines.</P>

      @return  <CODE>{@link #start(ValueAlterer) start}(txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(Pattern, ValidResultFilter, Appendable) text}(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #end(BlockEnd, Pattern, ValidResultFilter, Appendable) end
      @see  #line(Pattern, ValidResultFilter, Appendable) line
    **/
   public TextLineFilter_CfgForNeeder<M,R> start(Pattern pattern_toFind, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      start(NewTextLineAltererFor.text(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull));
      return  this;
   }
   /**
      <P>Set the alterer that detects block-end lines (which are optional).</P>

      @return  <CODE>{@link #end(BlockEnd, ValueAlterer) end}(end_is, txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(Pattern, ValidResultFilter, Appendable) text}(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #start(Pattern, ValidResultFilter, Appendable) start
    **/
   public TextLineFilter_CfgForNeeder<M,R> end(BlockEnd end_is, Pattern pattern_toFind, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      end(end_is, NewTextLineAltererFor.text(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull));
      return  this;
   }
   /**
      <P>Set the alterer that detects single-line entities.</P>

      @return  <CODE>{@link #line(ValueAlterer) line}(txtLineAlterer)</CODE>
      <BR>Where {@code txtLineAlterer} is equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(Pattern, ValidResultFilter, Appendable) text}(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull)</CODE>
      @see  #start(Pattern, ValidResultFilter, Appendable) start
    **/
   public TextLineFilter_CfgForNeeder<M,R> line(Pattern pattern_toFind, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      line(NewTextLineAltererFor.text(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull));
      return  this;
   }
   public TextLineFilter_CfgForNeeder<M,R> ifBlockOrLineOrNotActive(ActiveBlockLines ifBlk_active, ActiveSingleLines ifLn_active, InactiveLines if_inactive)  {
      super.ifBlockOrLineOrNotActive(ifBlk_active, ifLn_active, if_inactive);
      return  this;
   }
   public TextLineFilter_CfgForNeeder<M,R> chainID(boolean do_setStatic, Object id)  {
      super.chainID(do_setStatic, id);
      return  this;
   }
   /**
      <P>Set debugging.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder super}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(dest_ifNonNull)</CODE></P>

      @return  <I>{@code this}</I>
    **/
   public TextLineFilter_CfgForNeeder<M,R> debugTo(Appendable dest_ifNonNull)  {
      super.debugTo(dest_ifNonNull);
      return  this;
   }
//self-returners...END
   public void startConfig(Class<?> needed_class)  {
      ndrc.startConfig(needed_class);
   }
   public TextLineFilter_CfgForNeeder<M,R> startConfigReturnNeedable(R needer)  {
      super.startConfigReturnNeedable(needer);
      return  this;
   }
   /**
      <P>Create a new {@code TextLineFilter} as configured.</P>

      @return  <CODE>(M)(new {@link com.github.aliteralmind.codelet.linefilter.TextLineFilter#TextLineFilter(LineFilter_Fieldable) TextLineFilter}(this))</CODE>
    **/
   public M build()  {
      @SuppressWarnings("unchecked")
      M m = (M)(new TextLineFilter(this));

      return  m;
   }
   public void neeadableSetsNeeded(Object fully_configured)  {
      Object o = setGetNeededEndConfig(fully_configured);

      @SuppressWarnings("unchecked")
      ValueAlterer<LineObject<String>,String> al = (bForLnNumOrTxt
         ?  NewTextLineAltererFor.number((ValueValidator<Integer>)o)
         :  ((o instanceof RegexReplacer)
            ?  AdaptRegexReplacerTo.lineReplacer((RegexReplacer)o, vrfForNeededRR)
            :  NewTextLineAltererFor.text((ValueValidator<String>)o)));

      if(endOrLn == SetEndOrLine.START)  {
         start(al);
      }  else if(endOrLn == SetEndOrLine.END)  {
         //isEndRequired() already set in cfgEnd()
         end(BlockEnd.getForBoolean(isEndRequired()), al);
      }  else  {   //SetEndOrLine.LINE
         line(al);
      }
      endOrLn = null;
      bForLnNumOrTxt = false;
   }
   //Composition implementation: NeederComposer...START
      /**
         @return  {@code <A HREF="YYY/NeederComposer.html"><I>[NeederComposer]</I></A>.<A HREF="YYY/NeederComposer.html#getNeededType()">getNeededType</A>()}
       **/
      public Class getNeededType()  {
         return  ndrc.getNeededType();
      }
      /**
         @return  {@code <A HREF="YYY/NeederComposer.html"><I>[NeederComposer]</I></A>.<A HREF="YYY/NeederComposer.html#isConfigActive()">isConfigActive</A>()}
       **/
      public boolean isConfigActive()  {
         return  ndrc.isConfigActive();
      }
      /**
         @return  {@code <A HREF="YYY/NeederComposer.html"><I>[NeederComposer]</I></A>.<A HREF="YYY/NeederComposer.html#setGetNeededEndConfig_4prot(Object)">setGetNeededEndConfig_4prot</A>(needed_fullyConfigured)}
       **/
      protected Object setGetNeededEndConfig(Object needed_fullyConfigured)  {
         return  ndrc.setGetNeededEndConfig_4prot(needed_fullyConfigured);
      }
   //Composition implementation: NeederComposer...END
}
