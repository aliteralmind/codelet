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
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.BlockEnd;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveBlockLines;
   import  com.github.aliteralmind.codelet.linefilter.z.InactiveLines;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveSingleLines;
   import  java.util.regex.Pattern;
   import  java.util.Iterator;
   import  com.github.aliteralmind.codelet.linefilter.z.LineFilter_Fieldable;
/**
   <P>A {@code LineFilter} for textual lines.</P>

   <a name="cfg"/><H3>Builder Configuration: {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg TextLineFilter_Cfg}</H3>

   <P><UL>
      <LI><B>Block:</B><UL>
         <LI><CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}()</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#blockMarksInclusive() blockMarksInclusive}()</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#blockMarksInclusive(boolean) blockMarksInclusive}(b)</CODE></LI>
         <LI><B>Start:</B><UL>
            <LI><B>Direct:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#start(ValueAlterer) start}(av)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#start(Pattern, ValidResultFilter, Appendable) start}(p,vrf,apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#startReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) startReplacer}(p,s,rw,vrf,apbl)</CODE></LI>
            <LI><B>Indirect:</B> {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgStartLineNum() cfgStartLineNum}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgStartPattern() cfgStartPattern}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgStartReplacer(ValidResultFilter) cfgStartReplacer}{@code ()}</LI>
         </UL></LI>
         <LI><B>End:</B><UL>
            <LI><B>Direct:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#end(BlockEnd, ValueAlterer) end}(be,av)</CODE>, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#end(BlockEnd, Pattern, ValidResultFilter, Appendable) end}{@code (be,p,vrf,apbl)}, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#endReplacer(BlockEnd, Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) endReplacer}(be,p,s,rplcs,vrf,apbl)</CODE></LI>
            <LI><B>Indirect:</B> {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgEndLineNum(BlockEnd) cfgEndLineNum}{@code (be)}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgEndPattern(BlockEnd) cfgEndPattern}{@code (be)}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgEndReplacer(BlockEnd, ValidResultFilter) cfgEndReplacer}{@code (be)}</LI>
         </UL></LI>
         <LI><B>Both:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#javaBlock(Appendable) javaBlock}(apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#javaDocBlock(Appendable) javaDocBlock}(apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#allJavaMultiLineComments(Appendable) allJavaMultiLineComments}(apbl)</CODE></LI>
      </UL></LI>
      <LI><B>Line direct:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#line(ValueAlterer) line}(av)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#line(Pattern, ValidResultFilter, Appendable) line}(p,vrf,apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#lineReplacer(Pattern, String, ReplacedInEachInput, ValidResultFilter, Appendable) lineReplacer}(p,s,rw,vrf,apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#javaLineComment(Appendable) javaLineComment}(apbl)</CODE></LI>
      <LI><B>Line indirect:</B> {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgLineLineNum() cfgLineLineNum}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgLinePattern() cfgLinePattern}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#cfgLineReplacer(ValidResultFilter) cfgLineReplacer}{@code ()}</LI>
      <LI><B>Sub modes:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#noSubModes() noSubModes}()</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#setSubModes(TextLineFilter[]) setSubModes}(tlm[])</CODE></LI>
      <LI><B>Other:</B> {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines) ifBlockOrLineOrNotActive}{@code (iba,ila,iia)}, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#extraErrInfo(Object) extraErrInfo}(o)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#reset() reset}()</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_CfgForNeeder#chainID(boolean, Object) chainID}(b,o)</CODE></LI>
   </UL></P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TextLineFilter extends LineFilter<String,LineObject<String>>  {
   public TextLineFilter(LineFilter_Fieldable<String,LineObject<String>> fieldable)  {
      super(fieldable);
   }
   public TextLineFilter(TextLineFilter to_copy)  {
      super(to_copy);
   }
   /**
      <P>Returns all lines that are both marked and should be kept.</P>

      @see  com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) LineFilter#activeLineIterator
    **/
   public TextLineIterator activeLineIterator(Iterator<String> line_itr, Appendable dbgEachLine_ifNonNull)  {
      return  (new ItrAllTextLines(this, line_itr, dbgEachLine_ifNonNull));
   }
   /**
      <P>Get a duplicate of this <CODE>TextLineFilter</CODE>.</P>

      @return  <CODE>(new {@link #TextLineFilter(TextLineFilter) TextLineFilter}(this))</CODE>
    **/
   public TextLineFilter getObjectCopy()  {
      return  (new TextLineFilter(this));
   }
}
class ItrAllTextLines extends ItrAllLines<String,LineObject<String>> implements TextLineIterator  {
   public ItrAllTextLines(TextLineFilter mode, Iterator<String> line_itr, Appendable dbgEachLine_ifNonNull)  {
      super(mode, line_itr, dbgEachLine_ifNonNull);
   }
}
