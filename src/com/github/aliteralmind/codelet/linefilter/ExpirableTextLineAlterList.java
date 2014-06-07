/*license*\
   Codelet

   XBN-Java is a collection of generically-useful backend (non-GUI) programming utilities, featuring automated insertion of example code into JavaDoc, regular expression convenience classes, shareable self-returning method chains, and highly-configurable output for lists.

   Copyright (C) 2014, Jeff Epstein

   This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA*)
\*license*/
package  com.github.aliteralmind.codelet.linefilter;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.analyze.alter.ExpirableElements;
   import  com.github.xbn.analyze.alter.MultiAlterType;
   import  com.github.xbn.analyze.alter.ValueAlterer;
/**
   <P>For a series of alterers that expire--once an item is expired, it is removed from the list.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class ExpirableTextLineAlterList extends ExpirableLineAlterList<String>  {
   /*
      <P>Create a new instance in which expirable elements are optional, and {@code null} elements are forbidden.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.array.ExpirableAlterList#ExpirableAlterList(MultiAlterType, MultiAlterType) this}(text_lineAlterers, multi_alterType)</CODE></P>
   public ExpirableTextLineAlterList(ValueAlterer<LineObject<String>,String>[] text_lineAlterers, MultiAlterType multi_alterType)  {
      super(text_lineAlterers, multi_alterType);
   }
    */
   /**
      <P>Create a new instance.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.array.ExpirableLineAlterList#ExpirableAlterList(ValueAlterer[], ExpirableElements, MultiAlterType, Appendable) super}(text_lineAlterers, xprbl_lmntsAre, multi_alterType, debug_ifNonNull)</CODE></P>
    **/
   public ExpirableTextLineAlterList(ValueAlterer<LineObject<String>,String>[] text_lineAlterers, ExpirableElements xprbl_lmntsAre, MultiAlterType multi_alterType, Appendable debug_ifNonNull)  {
      super(text_lineAlterers, xprbl_lmntsAre, multi_alterType, debug_ifNonNull);
   }
   public ExpirableTextLineAlterList(ExpirableTextLineAlterList to_copy)  {
      super(to_copy);
   }
   public String getAltered(int line_num, String line_text)  {
      return  getAltered(new TextLine(line_num, line_text), line_text);
   }
   /**
      <P>Get a duplicate of this <CODE>ExpirableTextLineAlterList</CODE>.</P>

      @return  <CODE>(new <A HREF="#ExpirableTextLineAlterList(ExpirableTextLineAlterList)">ExpirableTextLineAlterList</A>(this))</CODE>
    **/
   public ExpirableTextLineAlterList getObjectCopy()  {
      return  (new ExpirableTextLineAlterList(this));
   }
   public void crashIfNotExpired()  {
      if(!isExpired())  {
         throw  new IllegalStateException("linkCreatorList.isExpired() is false. " + toString());
      }
   }
   /**
      <P>Get a new text-line-alterer array from the provided <I>ordered</I> elements, optionally excluding {@code null} elements.</P>

      @return  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/analyze/ExpirableAlterList.html#getAltererArrayFromOrderedElementsIfNonNull(L[], com.github.xbn.array.NullElement, java.lang.Class, java.lang.String)">getAltererArrayFromOrderedElementsIfNonNull</A>(orderedAlterers_whichMayBeNull, null_element, TextLineAlterer.class, alterers_varName)</CODE>
    **/
   public static final <O> TextLineAlterer[] getTextLineAltererArrayFromOrderedElementsIfNonNull(NullElement null_element, String alterers_varName, TextLineAlterer... orderedAlterers_whichMayBeNull)  {
      return  getAltererArrayFromOrderedElementsIfNonNull(orderedAlterers_whichMayBeNull, null_element, TextLineAlterer.class, alterers_varName);
   }
   /**
      <P>Get a new text-line-alterer array from the provided <I>ordered</I> elements, optionally excluding {@code null} elements.</P>

      @return  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/analyze/ExpirableAlterList.html#getAltererArrayFromOrderedElementsIfNonNull(L[], com.github.xbn.array.NullElement, java.lang.Class, java.lang.String)">getAltererArrayFromOrderedElementsIfNonNull</A>(orderedAlterers_whichMayBeNull, null_element, valosCls, alterers_varName)</CODE>
      <BR>Where {@code valosCls} is
      <BR> &nbsp; &nbsp; <CODE>(Class&lt;ValueAlterer&lt;LineObject&lt;String&gt;,String&gt;+&gt;)(Class)LineObject.class</CODE>
   @SuppressWarnings({"unchecked", "varargs"})
   public static final <O> ValueAlterer<LineObject<String>,String>[] getTextLineAltererArrayFromOrderedElementsIfNonNull(NullElement null_element, String alterers_varName, ValueAlterer<LineObject<String>,String>... orderedAlterers_whichMayBeNull)  {
      @SuppressWarnings("unchecked")
      Class<ValueAlterer<LineObject<String>,String>> valosCls = (Class<ValueAlterer<LineObject<String>,String>>)(Class)ValueAlterer.class;
      return  getAltererArrayFromOrderedElementsIfNonNull(orderedAlterers_whichMayBeNull, null_element, valosCls, alterers_varName);
   }
    **/
}
