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
package  com.github.aliteralmind.codelet.util;
/**
   <p>Is it a blacklist or whitelist?.</p>

 * @see  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public enum BlackOrWhite  {
   /**
      <p>YYY.</p>

    * @see  #WHITE
    * @see  #isBlack()
    */
   BLACK,
   /**
      <p>YYY.</p>

    * @see  #BLACK
    * @see  #isWhite()
    */
   WHITE;
   /**
      <p>Is this {@code BlackOrWhite} equal to {@code BLACK}?.</p>

    * @return  <code>this == {@link #BLACK}</code>

    * @see  #isWhite()
    */
   public final boolean isBlack()  {
      return  this == BLACK;
   }
   /**
      <p>Is this {@code BlackOrWhite} equal to {@code WHITE}?.</p>

    * @return  <code>this == {@link #WHITE}</code>
    * @see  #isBlack()
    */
   public final boolean isWhite()  {
      return  this == WHITE;
   }
   /**
      <p>Return {@code BlackOrWhite.BLACK} if the flag is {@code true}, or {@code WHITE} if {@code false}.</p>

    * @return  <code>(flag ? {@link #BLACK} : {@link #WHITE})</code>
    */
   public static final BlackOrWhite getBlackIfTrue(boolean flag)  {
      return  (flag ? BLACK : WHITE);
   }
};
