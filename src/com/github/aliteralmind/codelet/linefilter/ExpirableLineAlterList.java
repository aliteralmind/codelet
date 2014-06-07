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
   import  com.github.xbn.analyze.alter.ExpirableAlterList;
   import  com.github.xbn.analyze.alter.ValueAlterer;
/**
   <P>For a series of alterers that expire--once an item is expired, it is removed from the list.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class ExpirableLineAlterList<O> extends ExpirableAlterList<LineObject<O>,O>  {
   /*
      <P>Create a new instance in which expirable elements are optional, and {@code null} elements are forbidden.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.array.ExpirableAlterList#ExpirableAlterList(MultiAlterType, MultiAlterType) this}(line_alterers, multi_alterType)</CODE></P>
   public ExpirableLineAlterList(ValueAlterer<LineObject<O>,O>[] line_alterers, MultiAlterType multi_alterType)  {
      super(line_alterers, multi_alterType);
   }
    */
   /**
      <P>Create a new instance.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.array.ExpirableAlterList#ExpirableAlterList(ValueAlterer[], ExpirableElements, MultiAlterType, Appendable) super}(line_alterers, xprbl_lmntsAre, multi_alterType, debug_ifNonNull)</CODE></P>
    **/
   public ExpirableLineAlterList(ValueAlterer<LineObject<O>,O>[] line_alterers, ExpirableElements xprbl_lmntsAre, MultiAlterType multi_alterType, Appendable debug_ifNonNull)  {
      super(line_alterers, xprbl_lmntsAre, multi_alterType, debug_ifNonNull);
   }
   public ExpirableLineAlterList(ExpirableLineAlterList<O> to_copy)  {
      super(to_copy);
   }
   /**
      <P>Get a duplicate of this <CODE>ExpirableLineAlterList</CODE>.</P>

      @return  <CODE>(new <A HREF="#ExpirableLineAlterList(ExpirableLineAlterList)">ExpirableLineAlterList</A>&lt;O&gt;(this))</CODE>
    **/
   public ExpirableLineAlterList<O> getObjectCopy()  {
      return  (new ExpirableLineAlterList<O>(this));
   }
}
