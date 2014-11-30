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
   <p>Indicates a badly formatted {@code SimpleMethodSignature} string.</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>

 **/
public class SimpleMethodSigFormatException extends IllegalArgumentException  {
   /**
    * 
    */
   private static final long serialVersionUID = -7187385529111942307L;
   private final String strSig;
   public SimpleMethodSigFormatException(Object string_signature, String message)  {
      super(message +
         ((string_signature == null) ? "" : ". Signature: " + string_signature.toString()));
      strSig = ((string_signature == null) ? null : string_signature.toString());
   }
   public SimpleMethodSigFormatException(Object string_signature)  {
      super((string_signature == null) ? "" : ". Signature: " + string_signature.toString());
      strSig = ((string_signature == null) ? null : string_signature.toString());
   }
   public SimpleMethodSigFormatException(Object string_signature, String message, Throwable cause)  {
      super(message +
         ((string_signature == null) ? "" : ". Signature: " + string_signature.toString()));
      strSig = ((string_signature == null) ? null : string_signature.toString());
   }
   public SimpleMethodSigFormatException(Object string_signature, Throwable cause)  {
      super((string_signature == null) ? "" : ". Signature: " + string_signature.toString());
      strSig = ((string_signature == null) ? null : string_signature.toString());
   }
   public String getStringSignature()  {
      return  strSig;
   }
}
