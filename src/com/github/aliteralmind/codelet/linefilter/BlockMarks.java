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
/**
   <P>In a {@code LineFilter}, are the block start and end marks included or excluded from the block itself?.</P>

   @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksInclusive(boolean) LineFilter_Cfg#blockMarksInclusive(b)
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum BlockMarks  {
   /**
      <P>YYY.</P>

      @see  #EXCLUSIVE
      @see  #areInclusive()
    **/
   INCLUSIVE,
   /**
      <P>YYY.</P>

      @see  #INCLUSIVE
      @see  #areExclusive()
    **/
   EXCLUSIVE;
   /**
      <P>Is this {@code BlockMarks} equal to {@code INCLUSIVE}?.</P>

      @return  <CODE>this == {@link #INCLUSIVE}</CODE>

      @see  #areExclusive()
    **/
   public final boolean areInclusive()  {
      return  this == INCLUSIVE;
   }
   /**
      <P>Is this {@code BlockMarks} equal to {@code EXCLUSIVE}?.</P>

      @return  <CODE>this == {@link #EXCLUSIVE}</CODE>
      @see  #areInclusive()
    **/
   public final boolean areExclusive()  {
      return  this == EXCLUSIVE;
   }
};
