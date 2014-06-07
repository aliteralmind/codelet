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
   import  com.github.xbn.analyze.alter.AbstractValueAlterer;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.analyze.validate.ValueValidator;
   import  com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.RegexReplacer;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.StringReplacer;
   import  com.github.xbn.regexutil.z.RegexReplacer_Cfg;
   import  com.github.xbn.text.StringUtil;
   import  java.util.regex.Pattern;
   import  org.apache.commons.lang3.StringEscapeUtils;
/**
   <P>Convenience functions for creating text-line alterers from validators and regular expressions.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class NewTextLineAltererFor  {
   /**
      <P>Does nothing. The input is returned without change.</P>

      @return  <CODE>(new {@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen#AlterTextLineWhen() AlterTextLineWhen}()</CODE>
    **/
   public static final AlterTextLineWhen alwaysReturnUnchanged()  {
      return  (new AlterTextLineWhen());
   }
   /**
      <P>ReplacedInEachInput all tabs to spaces.</P>

      @return  <CODE>{@link #literalReplacement(String, String, ReplacedInEachInput, Appendable, ValidResultFilter) literalReplacement}(&quot;\t&quot;, spaces, {@link com.github.xbn.regexutil.ReplacedInEachInput}.{@link com.github.xbn.regexutil.ReplacedInEachInput#ALL ALL}, dbgDest_ifNonNull, null)</CODE>
      <BR>Where {@code spaces} is a {@linkplain com.github.xbn.text.StringUtil#getStringOfLengthAllCharsEqualTo(int, char, String) string of only} spaces, {@code space_count} characters in length.
      @see  #literalReplacement(String, String, ReplacedInEachInput, Appendable, ValidResultFilter)
    **/
   public static final TextLineAltererAdapter<StringReplacer> replaceTabToSpaces(int space_count, Appendable dbgDest_ifNonNull, String count_varName)  {
      String spaces = StringUtil.getStringOfLengthAllCharsEqualTo(space_count, ' ', count_varName);
      return  literalReplacement("\t", spaces, ReplacedInEachInput.ALL, dbgDest_ifNonNull, null);
   }
   /**
      <P>Identifies the line's text based on a regular expression--the line is not altered.</P>

      @return  <CODE>{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen AlterTextLineWhen}.{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen#newATLWAlwaysReturnSelf(ValueValidator) newATLWAlwaysReturnSelf}{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor NewLineValidatorFor}.{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor#text(Pattern, ValidResultFilter, Appendable) text}(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull))</CODE>
    **/
   public static final AlterTextLineWhen text(Pattern pattern_toFind, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      return  AlterTextLineWhen.newATLWAlwaysReturnSelf(NewLineValidatorFor.text(pattern_toFind, filter_ifNullOff, dbgDest_ifNonNull));
   }
   /**
      <P>Identifies the line's text based on some condition--the line is not altered.</P>

      @return  <CODE>{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen AlterTextLineWhen}.{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen#newATLWAlwaysReturnSelf(ValueValidator) newATLWAlwaysReturnSelf}{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor NewLineValidatorFor}.{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor#text(ValueValidator) text}(string_validator))</CODE>
    **/
   public static final AlterTextLineWhen text(ValueValidator<String> string_validator)  {
      return  AlterTextLineWhen.newATLWAlwaysReturnSelf(NewLineValidatorFor.text(string_validator));
   }
   /**
      <P>Identifies the line's number based on some condition--the line's text is not altered.</P>

      @return  <CODE>{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen AlterTextLineWhen}.{@link com.github.aliteralmind.codelet.linefilter.AlterTextLineWhen#newATLWAlwaysReturnSelf(ValueValidator) newATLWAlwaysReturnSelf}{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor NewLineValidatorFor}.{@link com.github.aliteralmind.codelet.linefilter.NewLineValidatorFor#number(ValueValidator) number}(int_validator))</CODE>
    **/
   public static final AlterTextLineWhen number(ValueValidator<Integer> int_validator)  {
      return  AlterTextLineWhen.newATLWAlwaysReturnSelf(NewLineValidatorFor.number(int_validator));
   }
   /**
      <P>Inserts the line number before each line's body.</P>

      @param  start_lineNum  The first line number. <I>Should</I> be 1 or greater.
      @param  btw_numAndText  The text to go between the number and body. <I>Should</I> be non-{@code null} and non-empty.
    **/
   public static final TextLineAlterer prependLineNumber(int start_lineNum, String btw_numAndText)  {
      return  (new PrependLineNumToText(start_lineNum, btw_numAndText));
   }
   /**
      <P>Escape all characters for html display. Uses <CODE>org.apache.commons.lang3.{@link org.apache.commons.lang3.StringEscapeUtils}{@link org.apache.commons.lang3.StringEscapeUtils#escapeHtml4 escapeHtml4}</CODE>.</P>
    **/
   public static final TextLineAlterer escapeHtml()  {
      return  (new EscapeLineTextForHtmlDisplay());
   }
   /**
      <P>Make a literal string replacement.</P>

      @return  <CODE>{@link #replacement(Pattern, String, ReplacedInEachInput, Appendable, ValidResultFilter) replacement}({@link com.github.xbn.regexutil.NewPatternFor#literal(String) literal}(find_what), rplc_with, rplcs_what, dbgDest_ifNonNull, filter_ifNullOff)</CODE>
    **/
   public static final TextLineAltererAdapter<StringReplacer> literalReplacement(String find_what, String rplc_with, ReplacedInEachInput rplcs_what, Appendable dbgDest_ifNonNull, ValidResultFilter filter_ifNullOff)  {
      return  replacement(NewPatternFor.literal(find_what, "find_what"), rplc_with, rplcs_what, dbgDest_ifNonNull, filter_ifNullOff);
   }
   /**
      <P>Makes a regular expression replacement.</P>

      @return  <CODE>{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo#lineReplacer(RegexReplacer, ValidResultFilter) lineReplacer}(rr, filter_ifNullOff)</CODE>
      <BR>Where {@code rr} is a
      <BR> &nbsp; &nbsp; <CODE>new {@link com.github.xbn.regexutil.z.RegexReplacer_Cfg RegexReplacer_Cfg}.{@link com.github.xbn.regexutil.z.RegexReplacer_Cfg#RegexReplacer_Cfg() RegexReplacer_Cfg}().
         {@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#direct(Pattern, Object) direct}(ptrn_findWhat, rplc_with).{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#replaceWhatNotMatchNums(ReplacedInEachInput) replaceWhatNotMatchNums}(rplcs_what).
         {@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#debugTo(Appendable) debugTo}(dbgDest_ifNonNull).{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#build() build}();</CODE>
      @see  #replaceTabToSpaces(int, Appendable, String)
      @see  #literalReplacement(String, String, ReplacedInEachInput, Appendable, ValidResultFilter)
    **/
   public static final TextLineAltererAdapter<StringReplacer> replacement(Pattern ptrn_findWhat, String rplc_with, ReplacedInEachInput rplcs_what, Appendable dbgDest_ifNonNull, ValidResultFilter filter_ifNullOff)  {
      RegexReplacer rr = new RegexReplacer_Cfg().
         direct(ptrn_findWhat, rplc_with).replaceWhatNotMatchNums(rplcs_what).
         debugTo(dbgDest_ifNonNull).build();
      return  AdaptRegexReplacerTo.lineReplacer(rr, filter_ifNullOff);
   }
   private NewTextLineAltererFor()  {
      throw  new IllegalStateException("Do not instantiate");
   }

}
class PrependLineNumToText extends AbstractValueAlterer<LineObject<String>,String> implements TextLineAlterer  {
   private final String btwNumAndTxt;
   private int lineNum;
   public PrependLineNumToText(int first_lineNum)  {
      this(first_lineNum, ".  ");
   }
   public PrependLineNumToText(int first_lineNum, String between_numAndText)  {
      super();
      lineNum = first_lineNum;
      btwNumAndTxt = between_numAndText;
   }
   public PrependLineNumToText(PrependLineNumToText to_copy)  {
      super(to_copy);
      lineNum = to_copy.getLineNum();
      btwNumAndTxt = to_copy.getBetweenNumAndText();
   }
   public int getLineNum()  {
      return  lineNum;
   }
   public String getBetweenNumAndText()  {
      return  btwNumAndTxt;
   }
   public String getAlteredPostResetCheck(LineObject<String> ignored, String to_alter)  {
      declareAlteredNotDeleted();
      return  (lineNum++) + getBetweenNumAndText() + to_alter;
   }
   public PrependLineNumToText getObjectCopy()  {
      return  (new PrependLineNumToText(this));
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      return  to_appendTo.append(this.getClass().getName()).append(": getLineNum()=" + getLineNum() + " getBetweenNumAndText()=\"" + getBetweenNumAndText() + "\"");
   }
}
class EscapeLineTextForHtmlDisplay extends AbstractValueAlterer<LineObject<String>,String> implements TextLineAlterer  {
   public EscapeLineTextForHtmlDisplay()  {
   }
   public EscapeLineTextForHtmlDisplay(EscapeLineTextForHtmlDisplay to_copy)  {
      super(to_copy);
   }
   public String getAlteredPostResetCheck(LineObject<String> ignored, String to_alter)  {
      declareAlteredNotDeleted();
      return  StringEscapeUtils.escapeHtml4(to_alter);
   }
   public EscapeLineTextForHtmlDisplay getObjectCopy()  {
      return  (new EscapeLineTextForHtmlDisplay(this));
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      return  to_appendTo.append(this.getClass().getName());
   }
}
