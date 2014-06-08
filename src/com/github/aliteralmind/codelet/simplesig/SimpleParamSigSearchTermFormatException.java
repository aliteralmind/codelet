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
package  com.github.aliteralmind.codelet.simplesig;
/**
   <P>Indicates a badly formatted search term.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>

 **/
public class SimpleParamSigSearchTermFormatException extends IllegalArgumentException  {
   private final String strSig;
   public SimpleParamSigSearchTermFormatException(String search_term, String message)  {
      super(message +
         ((search_term == null) ? "" : ". Signature: " + search_term));
      strSig = search_term;
   }
   public SimpleParamSigSearchTermFormatException(String search_term)  {
      super((search_term == null) ? "" : ". Signature: " + search_term);
      strSig = search_term;
   }
   public SimpleParamSigSearchTermFormatException(String search_term, String message, Throwable cause)  {
      super(message +
         ((search_term == null) ? "" : ". Signature: " + search_term));
      strSig = search_term;
   }
   public SimpleParamSigSearchTermFormatException(String search_term, Throwable cause)  {
      super((search_term == null) ? "" : ". Signature: " + search_term);
      strSig = search_term;
   }
   public String getStringSignature()  {
      return  strSig;
   }
}
