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
   import  com.github.xbn.analyze.validate.NullnessValidator;
   import  com.github.xbn.analyze.validate.ResultReturnedBy;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.analyze.validate.Validator;
   import  com.github.xbn.analyze.validate.ValidatorComposer;
   import  com.github.xbn.analyze.validate.ValueValidator;
   import  com.github.xbn.analyze.validate.ValueValidatorAdapter;
   import  com.github.xbn.analyze.validate.z.ValueValidator_Cfg;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.ObjectOrCrashIfNull;
   import  com.github.xbn.lang.RuleType;
   import  com.github.xbn.lang.RuleableComposer;
   import  com.github.xbn.lang.SimpleAdapter;
   import  com.github.xbn.neederneedable.Needer;
   import  com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo;
   import  com.github.xbn.regexutil.RegexReplacer;
   import  com.github.xbn.regexutil.RegexValidator;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.StringValidatorReplacer;
   import  com.github.xbn.regexutil.z.RegexValidator_Cfg;
   import  java.util.regex.Pattern;
/**
   <P>Convenience functions for creating text-line validators with line-number validators, string validators, and regular expressions (which only identify the line), and RegexReplacers (which also alter them).</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class NewLineValidatorFor  {
   private NewLineValidatorFor()  {
      throw  new IllegalStateException("Do not instantiate");
   }
   /**
      <P>A new text-line validator that identifies the line's text based on a regular expression.</P>
    **/
   public static final TextLineValidatorAdapter<ValueValidator<String>> text(Pattern find_what, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      return  text(new RegexValidator_Cfg().pattern(find_what).useFind().filter(filter_ifNullOff).debugTo(dbgDest_ifNonNull).build());
   }
   /**
      <P>A new text-line validator that identifies the line's text based on a regular expression.</P>
    **/
   public static final TextLineValidatorAdapter<ValueValidator<String>> text(RegexValidator validator)  {
      return  new TLVForStringV(validator, "validator");
   }
   /**
      <P>A new text-line validator that identifies the line's text based on a condition.</P>

      @param  string_validator  The validator to decorate. May not be {@code null}. Get with {@code getAdapted()}
      @return  A new {@code TextLineValidator} that does not alter valid lines.
    **/
   public static final TextLineValidatorAdapter<ValueValidator<String>> text(ValueValidator<String> string_validator)  {
      return  new TLVForStringV(string_validator, "string_validator");
   }
   /**
      <P>A new text-line validator that identifies the line's number based on a condition--the line's text is ignored.</P>

      @param  int_validator  The validator to decorate. May not be {@code null}. Get with {@code getAdapted()}
      @return  A new {@code TextLineValidator} that does not alter valid lines.
    **/
   public static final TextLineValidatorAdapter<ValueValidator<Integer>> number(ValueValidator<Integer> int_validator)  {
      return  new TLVForIntV(int_validator, "int_validator");
   }
   /**
      <P>A new text-line validator that identifies <I>and replaces</I> the line's text based on a regular expression.</P>
    **/
   public static final TextLineValidatorAdapter<StringValidatorReplacer> textReplacer(RegexReplacer replacer, ValidResultFilter filter_ifNullOff)  {
      return  new TLVForSVR(new StringValidatorReplacer(replacer, filter_ifNullOff));
   }
   /**
      <P>A new text-line validator that identifies <I>and replaces</I> the line's text based on a regular expression.</P>
    **/
   public static final TextLineValidatorAdapter<StringValidatorReplacer> textReplacer(Pattern find_what, String rplcWith_direct, ValidResultFilter filter_ifNullOff, Appendable dbgDest_ifNonNull)  {
      return  new TLVForSVR(AdaptRegexReplacerTo.stringValidator(find_what, rplcWith_direct, ReplacedInEachInput.MATCHES, filter_ifNullOff, dbgDest_ifNonNull));
   }
}
class TLVForSVR extends SimpleAdapter<StringValidatorReplacer> implements TextLineValidatorAdapter<StringValidatorReplacer>  {
   public TLVForSVR(StringValidatorReplacer sv_replacer)  {
      super(sv_replacer);
   }
   @SuppressWarnings("unchecked")
   public TLVForSVR(TLVForSVR to_copy)  {
      super((to_copy == null) ? null
         :  ObjectOrCrashIfNull.
               <StringValidatorReplacer>getCopy(to_copy.getAdapted(), StringValidatorReplacer.class, "to_copy.getAdapted()"));
   }
   public void resetState()  {
      getAdapted().resetState();
   }
   public boolean isNullOk()  {
      return  getAdapted().isNullOk();
   }
   public void resetCounts()  {
      getAdapted().resetCounts();
   }
   public int getValidCount()  {
      return  getAdapted().getValidCount();
   }
   public boolean doAutoResetState()  {
      return  getAdapted().doAutoResetState();
   }
   public boolean isValid()  {
      return  getAdapted().isValid();
   }
   public ResultReturnedBy getValidResultSource()  {
      return  getAdapted().getValidResultSource();
   }
   public ValidResultFilter getFilter()  {
      return  getAdapted().getFilter();
   }
   public boolean doInvertRules()  {
      return  getAdapted().doInvertRules();
   }
   public int getAnalyzedCount()  {
      return  getAdapted().getAnalyzedCount();
   }
   public boolean wasAnalyzed()  {
      return  getAdapted().wasAnalyzed();
   }
   public boolean equals(Object to_compareTo)  {
      return  getAdapted().equals(to_compareTo);
   }
   public void setDebug(Appendable destination, boolean is_on)  {
      getAdapted().setDebug(destination, is_on);
   }
   public void setDebugOn(boolean is_on)  {
      getAdapted().setDebugOn(is_on);
   }
   public boolean doesExpire()  {
      return  getAdapted().doesExpire();
   }
   public boolean isExpired()  {
      return  getAdapted().isExpired();
   }
   public boolean isDebugOn()  {
      return  getAdapted().isDebugOn();
   }
   public Appendable getDebugApbl()  {
      return  getDebugAptr().getAppendable();
   }
   public TextAppenter getDebugAptr()  {
      return  getAdapted().getDebugAptr();
   }
   public Object getExtraErrInfo()  {
      return  getAdapted().getExtraErrInfo();
   }
   public void setExtraErrInfo(Object info)  {
      getAdapted().setExtraErrInfo(info);
   }
   public String getRules()  {
      return  getAdapted().getRules();
   }
   public String toString()  {
      return  getAdapted().toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      return  getAdapted().appendToString(to_appendTo);
   }
   public StringBuilder appendRules(StringBuilder to_appendTo)  {
      return  getAdapted().appendRules(to_appendTo);
   }
   public RuleType getRuleType()  {
      return  getAdapted().getRuleType();
   }
   public TLVForSVR getObjectCopy()  {
      return  (new TLVForSVR(this));
   }
   public boolean isValid(LineObject<String> line)  {
      return  getAdapted().isValid(TextLine.getBodyCINull(line, "line"));
   }
   public void crashIfBadValue(LineObject<String> line, String line_name)  {
      getAdapted().crashIfBadValue(TextLine.getBodyCINull(line, "line"), line_name);
   }
}
abstract class TLVForVV<O> extends SimpleAdapter<ValueValidator<O>> implements TextLineValidatorAdapter<ValueValidator<O>>  {
   public TLVForVV(ValueValidator<O> validator)  {
      super(validator);
   }
   @SuppressWarnings("unchecked")
   public TLVForVV(TLVForVV<O> to_copy)  {
      super((to_copy == null) ? null
         :  ObjectOrCrashIfNull.
               <ValueValidator>getCopy(to_copy.getAdapted(), ValueValidator.class, "to_copy.getAdapted()"));
   }
   public void resetState()  {
      getAdapted().resetState();
   }
   public boolean isNullOk()  {
      return  getAdapted().isNullOk();
   }
   public void resetCounts()  {
      getAdapted().resetCounts();
   }
   public int getValidCount()  {
      return  getAdapted().getValidCount();
   }
   public boolean doAutoResetState()  {
      return  getAdapted().doAutoResetState();
   }
   public boolean isValid()  {
      return  getAdapted().isValid();
   }
   public ResultReturnedBy getValidResultSource()  {
      return  getAdapted().getValidResultSource();
   }
   public ValidResultFilter getFilter()  {
      return  getAdapted().getFilter();
   }
   public boolean doInvertRules()  {
      return  getAdapted().doInvertRules();
   }
   public int getAnalyzedCount()  {
      return  getAdapted().getAnalyzedCount();
   }
   public boolean wasAnalyzed()  {
      return  getAdapted().wasAnalyzed();
   }
   public boolean equals(Object to_compareTo)  {
      return  getAdapted().equals(to_compareTo);
   }
   public void setDebug(Appendable destination, boolean is_on)  {
      getAdapted().setDebug(destination, is_on);
   }
   public void setDebugOn(boolean is_on)  {
      getAdapted().setDebugOn(is_on);
   }
   public boolean doesExpire()  {
      return  getAdapted().doesExpire();
   }
   public boolean isExpired()  {
      return  getAdapted().isExpired();
   }
/*
   public void debugToConsole()  {
      getAdapted().debugToConsole();
   }
 */
   public boolean isDebugOn()  {
      return  getAdapted().isDebugOn();
   }
   public Appendable getDebugApbl()  {
      return  getDebugAptr().getAppendable();
   }
   public TextAppenter getDebugAptr()  {
      return  getAdapted().getDebugAptr();
   }
   public Object getExtraErrInfo()  {
      return  getAdapted().getExtraErrInfo();
   }
   public void setExtraErrInfo(Object info)  {
      getAdapted().setExtraErrInfo(info);
   }
   public String getRules()  {
      return  getAdapted().getRules();
   }
   public String toString()  {
      return  getAdapted().toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      return  getAdapted().appendToString(to_appendTo);
   }
   public StringBuilder appendRules(StringBuilder to_appendTo)  {
      return  getAdapted().appendRules(to_appendTo);
   }
   public RuleType getRuleType()  {
      return  getAdapted().getRuleType();
   }
}
class TLVForIntV extends TLVForVV<Integer>  {
   public TLVForIntV(ValueValidator<Integer> int_validator, String iv_name)  {
      super(int_validator);
   }
   public TLVForIntV(TLVForIntV to_copy)  {
      super(to_copy);

   }
   public boolean isValid(LineObject<String> line)  {
      return  getAdapted().isValid(TextLine.getNumberCINull(line, "line"));
   }
   public TLVForIntV getObjectCopy()  {
      return  (new TLVForIntV(this));
   }
/*
   public TLVForIntV extraErrInfo(Object info)  {
      getAdapted().extraErrInfo(info);
      return  this;
   }
 */
   public void crashIfBadValue(LineObject<String> line, String line_name)  {
      getAdapted().crashIfBadValue(TextLine.getNumberCINull(line, "line"), line_name);
   }
}
class TLVForStringV extends TLVForVV<String>  {
   public TLVForStringV(ValueValidator<String> string_validator, String sv_name)  {
      super(string_validator);
   }
   public TLVForStringV(TLVForStringV to_copy)  {
      super(to_copy);
   }
   public boolean isValid(LineObject<String> line)  {
      return  getAdapted().isValid(TextLine.getBodyCINull(line, "line"));
   }
   public TLVForStringV getObjectCopy()  {
      return  (new TLVForStringV(this));
   }
/*
   public TLVForStringV extraErrInfo(Object info)  {
      getAdapted().extraErrInfo(info);
      return  this;
   }
 */
   public void crashIfBadValue(LineObject<String> line, String line_name)  {
      getAdapted().crashIfBadValue(TextLine.getBodyCINull(line, "line"), line_name);
   }
}
class TLVForVUtil  {
   public static final ValidResultFilter getFilter(Validator validator, String vldtr_name)  {
      try  {
         return  validator.getFilter();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(validator, vldtr_name, null, rx);
      }
   }
   public static final Object getExtraErrInfo(Validator validator, String vldtr_name)  {
      try  {
         return  validator.getExtraErrInfo();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(validator, vldtr_name, null, rx);
      }
   }
}
