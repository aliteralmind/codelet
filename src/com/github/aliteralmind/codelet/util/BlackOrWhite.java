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
package  com.github.aliteralmind.codelet.util;
/**
   <P>Is it a blacklist or whitelist?.</P>

   @see  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum BlackOrWhite  {
   /**
      <P>YYY.</P>

      @see  #WHITE
      @see  #isBlack()
    **/
   BLACK,
   /**
      <P>YYY.</P>

      @see  #BLACK
      @see  #isWhite()
    **/
   WHITE;
   /**
      <P>Is this {@code BlackOrWhite} equal to {@code BLACK}?.</P>

      @return  <CODE>this == {@link #BLACK}</CODE>

      @see  #isWhite()
    **/
   public final boolean isBlack()  {
      return  this == BLACK;
   }
   /**
      <P>Is this {@code BlackOrWhite} equal to {@code WHITE}?.</P>

      @return  <CODE>this == {@link #WHITE}</CODE>
      @see  #isBlack()
    **/
   public final boolean isWhite()  {
      return  this == WHITE;
   }
   /**
      <P>Return {@code BlackOrWhite.BLACK} if the flag is {@code true}, or {@code WHITE} if {@code false}.</P>

      @return  <CODE>(flag ? {@link #BLACK} : {@link #WHITE})</CODE>
    **/
   public static final BlackOrWhite getBlackIfTrue(boolean flag)  {
      return  (flag ? BLACK : WHITE);
   }
};
