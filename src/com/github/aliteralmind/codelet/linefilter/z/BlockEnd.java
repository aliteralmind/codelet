/*license*\
   XBN-Java is a collection of generically-useful backend (non-GUI) programming utilities, featuring automated insertion of example code into JavaDoc, regular expression convenience classes, shareable self-returning method chains, and highly-configurable output for lists.

   Copyright (C) 2014, Jeff Epstein

   This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
\*license*/
package  com.github.aliteralmind.codelet.linefilter.z;
/**
   <P>Is the block-end line in a {@code LineFilter} is optional or required?--this is used by <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder LineFilter_CfgForNeeder}.{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#end(BlockEnd, ValueAlterer) end}(av,b)</CODE>.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum BlockEnd  {
   /**
      <P>It is okay for block-end marks (lines) to not exist. Blocks are automatically closed when a new block is {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getActiveBlockStartLineNum() opened}, or at the {@link com.github.aliteralmind.codelet.linefilter.LineFilter#declareAllLinesAnalyzed() end of input}.</P>

      @see  #REQUIRED
      @see  #isOptional()
    **/
   OPTIONAL,
   /**
      <P>Block-end marks (line) must exist.</P>

      @see  #OPTIONAL
      @see  #isRequired()
    **/
   REQUIRED;
   /**
      <P>Is this {@code BlockEnd} equal to {@code OPTIONAL}?.</P>

      @return  <CODE>this == {@link #OPTIONAL}</CODE>

      @see  #isRequired()
    **/
   public final boolean isOptional()  {
      return  this == OPTIONAL;
   }
   /**
      <P>Is this {@code BlockEnd} equal to {@code REQUIRED}?.</P>

      @return  <CODE>this == {@link #REQUIRED}</CODE>
      @see  #isOptional()
    **/
   public final boolean isRequired()  {
      return  this == REQUIRED;
   }
   /**
      <P>Get a {@code BlockEnd} from an actual boolean.</P>

      @return  <CODE>(b ? {@link #REQUIRED} : {@link #OPTIONAL})</CODE>
    **/
   public static final BlockEnd getForBoolean(boolean b)  {
      return  (b ? REQUIRED : OPTIONAL);
   }
};
