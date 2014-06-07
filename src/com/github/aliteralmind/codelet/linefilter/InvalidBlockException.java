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
package  com.github.aliteralmind.codelet.linefilter;
/**
   <P>Indicates a block-related error, such as a start-mark found before the previous block was closed.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <CODE><A HREF="http://templatefeather.aliteralmind.com">http://templatefeather.aliteralmind.com</A></CODE>, <CODE><A HREF="https://github.com/aliteralmind/templatefeather">https://github.com/aliteralmind/templatefeather</A></CODE>
   @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="LineFilter.html#getActiveLine(L)">getActiveLine</A>(L)</CODE>
 **/
public class InvalidBlockException extends IllegalArgumentException  {
   public InvalidBlockException(String message)  {
      super(message);
   }
   public InvalidBlockException()  {
   }
   public InvalidBlockException(String message, Throwable cause)  {
      super(message, cause);
   }
   public InvalidBlockException(Throwable cause)  {
      super(cause);
   }
}
