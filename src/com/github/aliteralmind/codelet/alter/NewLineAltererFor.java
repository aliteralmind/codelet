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
   import  com.github.xbn.analyze.alter.AlterationRequired;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.TagletTextUtil;
   import  com.github.xbn.linefilter.alter.NewTextLineAltererFor;
   import  com.github.xbn.linefilter.alter.TextLineAlterer;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.util.JavaRegexes;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <p>Convenience functions for creating line alterers tailored for codelets.</p>

   @see  com.github.xbn.linefilter.alter.TextLineAlterer
   @see  com.github.xbn.linefilter.alter.NewTextLineAltererFor
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class NewLineAltererFor  {
   /**
      <p>Eliminates all references to a package. Use in conjunction with {@link #eliminatePackageLine(Appendable, Appendable) eliminatePackageLine}.</p>

    * @return

<blockquote><pre>{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor#replacement(AlterationRequired, Pattern, String, ReplacedInEachInput, Appendable, ValidResultFilter) replacement}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES},
   {@link com.github.xbn.regexutil.NewPatternFor NewPatternFor}.{@link com.github.xbn.regexutil.NewPatternFor#literal(String) literal}({@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExamplePackageName(CodeletInstance) getExamplePackageName}(instance) + &quot;.&quot;),
   &quot;&quot;, {@link com.github.xbn.regexutil.ReplacedInEachInput ReplacedInEachInput}.{@link com.github.xbn.regexutil.ReplacedInEachInput#ALL ALL},
   dbgRplcr_ifNonNull,
   null)</pre></blockquote>
    */
   public static final TextLineAlterer elimPackageReferences(CodeletInstance instance, Appendable dbgRplcr_ifNonNull)  {
      return  NewTextLineAltererFor.replacement(AlterationRequired.YES,
         NewPatternFor.literal(TagletTextUtil.getExamplePackageName(instance) + "."),
         "", ReplacedInEachInput.ALL,
         dbgRplcr_ifNonNull,
         null);
   }
   /**
      <p>Eliminates the package declaration line.</p>

    * @return
<blockquote><pre>{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor#text(Pattern, ValidResultFilter, Appendable) text}(packageLinePtrn, firstOccuranceFilter,
   dbgPkgLnVldtr_ifNonNull)</pre></blockquote>
   	Where {@code packageLinePtrn} is
   	<br> &nbsp; &nbsp; <code>{@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}({@link com.github.xbn.util.JavaRegexes JavaRegexes}.{@link com.github.xbn.util.JavaRegexes#PACKAGE_DECL_ONE_LINE_NO_CMTS PACKAGE_DECL_ONE_LINE_NO_CMTS})</code>
   	<br>and {@code firstOccuranceFilter} is a
   	<br> &nbsp; &nbsp; <code>{@link com.github.xbn.analyze.validate.NewValidResultFilterFor NewValidResultFilterFor}.{@link com.github.xbn.analyze.validate.NewValidResultFilterFor#inUnchangedOutFalse(int, int, String, String, Appendable) inUnchangedOutFalse}(1, 1, null, null, dbgEveryLine_ifNonNull);</code>
    * @see  #elimPackageReferences(CodeletInstance, Appendable) elimPackageReferences
    */
   public static final TextLineAlterer eliminatePackageLine(Appendable dbgEveryLine_ifNonNull, Appendable dbgPkgLnVldtr_ifNonNull)  {
      Pattern  packageLinePtrn = Pattern.compile(JavaRegexes.PACKAGE_DECL_ONE_LINE_NO_CMTS);
      ValidResultFilter firstOccuranceFilter = NewValidResultFilterFor.
         inUnchangedOutFalse(1, 1, null, null, dbgEveryLine_ifNonNull);

      return  NewTextLineAltererFor.textValidateOnly(packageLinePtrn, firstOccuranceFilter,
         dbgPkgLnVldtr_ifNonNull);
   }
   private static final Matcher INDENT_MTCHR = Pattern.compile("\\^[ \\\\t]+").matcher("");
   /**
      <p>Eliminates indentation from every line, if desired.</p>

    * @param  indentRegexToElim_emptyStrIfNone  The regular expression representing the indentation to eliminate. May not be {@code null}. Must begin with a line-start anchor {@code '^'} and otherwise contain only spaces ({@code ' '}) and tabs {@code '\t'}.
    * @return  If {@code indentRegexToElim_emptyStrIfNone.length()} is<ul>
         <li>{@code 0}: {@code null}.</li>
         <li>One or greater:
<blockquote><pre>{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor NewTextLineAltererFor}.{@link com.github.xbn.linefilter.alter.NewTextLineAltererFor#replacement(AlterationRequired, Pattern, String, ReplacedInEachInput, Appendable, ValidResultFilter) replacement}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES},
   {@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(indentRegexToElim_emptyStrIfNone), &quot;&quot;,
   {@link com.github.xbn.regexutil.ReplacedInEachInput ReplacedInEachInput}.{@link com.github.xbn.regexutil.ReplacedInEachInput#FIRST FIRST}, debugDest_ifNonNull, null)</pre></blockquote></li>
      </ul>
    */
   public static final TextLineAlterer eliminateIndentationOrNull(String indentRegexToElim_emptyStrIfNone, Appendable debugDest_ifNonNull)  {
      if(indentRegexToElim_emptyStrIfNone.length() == 0)  {
         return  null;
      }

      if(!INDENT_MTCHR.reset(indentRegexToElim_emptyStrIfNone).matches())  {
         throw  new IllegalArgumentException("Invalid indentation regex: indentRegexToElim_emptyStrIfNone=\"" + indentRegexToElim_emptyStrIfNone + "\"");
      }

      return  NewTextLineAltererFor.replacement(AlterationRequired.YES,
         Pattern.compile(indentRegexToElim_emptyStrIfNone), "",
         ReplacedInEachInput.FIRST, debugDest_ifNonNull, null);
   }
   private NewLineAltererFor()  {
      throw  new IllegalStateException("Do not instantiate");
   }
}
