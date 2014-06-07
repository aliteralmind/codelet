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
package  com.github.aliteralmind.codelet.alter;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.TagletTextUtil;
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAlterer;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.util.JavaRegexes;
   import  java.util.Arrays;
   import  java.util.LinkedHashMap;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Convenience functions for creating line alterers tailored for codelets.</P>

   @see  com.github.aliteralmind.codelet.linefilter.TextLineAlterer
   @see  com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class NewLineAltererFor  {
   /**
      <P>Eliminates all references to a package. Use in conjunction with {@link #eliminatePackageLine(Appendable, Appendable) eliminatePackageLine}.</P>

      @return

<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#replacement(Pattern, String, ReplacedInEachInput, Appendable, ValidResultFilter) replacement}(
   {@link com.github.xbn.regexutil.NewPatternFor NewPatternFor}.{@link com.github.xbn.regexutil.NewPatternFor#literal(String) literal}({@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExamplePackageName(CodeletInstance) getExamplePackageName}(instance) + &quot;.&quot;),
   &quot;&quot;, {@link com.github.xbn.regexutil.ReplacedInEachInput ReplacedInEachInput}.{@link com.github.xbn.regexutil.ReplacedInEachInput#ALL ALL},
   dbgRplcr_ifNonNull,
   null)</PRE></BLOCKQUOTE>
    **/
   public static final TextLineAlterer elimPackageReferences(CodeletInstance instance, Appendable dbgRplcr_ifNonNull)  {
      return  NewTextLineAltererFor.replacement(
         NewPatternFor.literal(TagletTextUtil.getExamplePackageName(instance) + "."),
         "", ReplacedInEachInput.ALL,
         dbgRplcr_ifNonNull,
         null);
   }
   /**
      <P>Eliminates the package declaration line.</P>

      @return
<BLOCKQUOTE><PRE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#text(Pattern, ValidResultFilter, Appendable) text}(packageLinePtrn, firstOccuranceFilter,
   dbgPkgLnVldtr_ifNonNull)</PRE></BLOCKQUOTE>
   	Where {@code packageLinePtrn} is
   	<BR> &nbsp; &nbsp; <CODE>{@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}({@link com.github.xbn.util.JavaRegexes JavaRegexes}.{@link com.github.xbn.util.JavaRegexes#PACKAGE_DECL_ONE_LINE_NO_CMTS PACKAGE_DECL_ONE_LINE_NO_CMTS})</CODE>
   	<BR>and {@code firstOccuranceFilter} is a
   	<BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.analyze.validate.NewValidResultFilterFor NewValidResultFilterFor}.{@link com.github.xbn.analyze.validate.NewValidResultFilterFor#inUnchangedOutFalse(int, int, String, String, Appendable) inUnchangedOutFalse}(1, 1, null, null, dbgFilter_ifNonNull);</CODE>
      @see  #elimPackageReferences(CodeletInstance, Appendable) elimPackageReferences
    **/
   public static final TextLineAlterer eliminatePackageLine(Appendable dbgFilter_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull)  {
      Pattern  packageLinePtrn = Pattern.compile(JavaRegexes.PACKAGE_DECL_ONE_LINE_NO_CMTS);
      ValidResultFilter firstOccuranceFilter = NewValidResultFilterFor.
         inUnchangedOutFalse(1, 1, null, null, dbgFilter_ifNonNull);

      return  NewTextLineAltererFor.text(packageLinePtrn, firstOccuranceFilter,
         dbgPkgLnVldtr_ifNonNull);
   }
   private static final Matcher INDENT_MTCHR = Pattern.compile("\\^[ \\\\t]+").matcher("");
   /**
      <P>Eliminates indentation from every line, if desired.</P>

      @param  indentRegexToElim_emptyStrIfNone  The regular expression representing the indentation to eliminate. May not be {@code null}. Must begin with a line-start anchor {@code '^'} and otherwise contain only spaces ({@code ' '}) and tabs {@code '\t'}.
      @return  If {@code indentRegexToElim_emptyStrIfNone.length()} is<UL>
         <LI>{@code 0}: {@code null}.</LI>
         <LI>One or greater:
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#replacement(Pattern, String, ReplacedInEachInput, Appendable, ValidResultFilter) replacement}({@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(indentRegexToElim_emptyStrIfNone), &quot;&quot;,
         <BR> &nbsp; &nbsp; {@link com.github.xbn.regexutil.ReplacedInEachInput ReplacedInEachInput}.{@link com.github.xbn.regexutil.ReplacedInEachInput#FIRST FIRST}, debugDest_ifNonNull, null)</CODE></LI>
      </UL>
    **/
   public static final TextLineAlterer eliminateIndentationOrNull(String indentRegexToElim_emptyStrIfNone, Appendable debugDest_ifNonNull)  {
      if(indentRegexToElim_emptyStrIfNone.length() == 0)  {
         return  null;
      }

      if(!INDENT_MTCHR.reset(indentRegexToElim_emptyStrIfNone).matches())  {
         throw  new IllegalArgumentException("Invalid indentation regex: indentRegexToElim_emptyStrIfNone=\"" + indentRegexToElim_emptyStrIfNone + "\"");
      }

      return  NewTextLineAltererFor.replacement(Pattern.compile(indentRegexToElim_emptyStrIfNone), "",
         ReplacedInEachInput.FIRST, debugDest_ifNonNull, null);
   }
   /*
      <P>Change all tabs to spaces, as (and if) configured.</P>

      @return  <CODE>{@link #newForTabToSpacesOrNull(int, Appendable) newForTabToSpacesOrNull}({@link com.github.aliteralmind.codelet.CodeletBaseConfig CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getTabToHowManySpaces() getTabToHowManySpaces}(), debugDest_ifNonNull)</CODE>
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig#TAB_TO_HOW_MANY_SPACES CodeletBaseConfig#TAB_TO_HOW_MANY_SPACES
   public static final TextLineAlterer newForTabToSpacesOrNull(Appendable debugDest_ifNonNull)  {
      return  newForTabToSpacesOrNull(getTabToHowManySpaces(), debugDest_ifNonNull);
   }
    */
   /*
      <P>Change all tabs to spaces, or {@code null} if the number of spaces is -1.</P>

      @param  space_count  The number of spaces to replace each tab with. May not be zero or less than {@code -1}.
      @return  If {@code space_count} is<UL>
         <LI>{@code -1}: {@code null}</LI>
         <LI>One or greater:
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#replaceTabToSpaces(String, Appendable, String) replaceTabToSpaces}(
         <BR> &nbsp; &nbsp; &nbsp; &nbsp; space_count, debugDest_ifNonNull, "space_count")</CODE></LI>
      </UL>
      @see  #newForTabToSpacesOrNull(Appendable)
      @see  com.github.aliteralmind.codelet.alter.NewLineAltererFor#eliminateIndentation(String, Appendable)
      @see  com.github.aliteralmind.codelet.CustomizationInstructions#newForMaybeElimIndent_tabToSpcEscHtml(CodeletInstance, TextLineFilter, String) CustomizationInstructions#newForMaybeElimIndent_tabToSpcEscHtml
   public static final TextLineAlterer newForTabToSpacesOrNull(int space_count, Appendable debugDest_ifNonNull)  {
      if(space_count == 0)  {
         throw  new IllegalArgumentException("space_count is zero.");
      }
      return  ((space_count == -1) ? null
         :  NewTextLineAltererFor.replaceTabToSpaces(
               space_count, debugDest_ifNonNull, "space_count"));
   }
    */
   private NewLineAltererFor()  {
      throw  new IllegalStateException("Do not instantiate");
   }
}
