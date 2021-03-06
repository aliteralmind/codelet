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
   <p>Indicates a badly formatted {@code NamedDebuggers} configuration file.</p>

 * @see  NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable)
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class NamedDebuggerFormatException extends IllegalArgumentException  {
   /**
    * 
    */
   private static final long serialVersionUID = 4617394249689798008L;
   /**
      <p>Create an NamedDebuggerFormatException with no error message.</p>
    */
   public NamedDebuggerFormatException()  {
   }
   /**
      <p>Create an NamedDebuggerFormatException.</p>
    */
   public NamedDebuggerFormatException(String message)  {
      super(message);
   }
   /**
      <p>Create an NamedDebuggerFormatException with no error message.</p>
    */
   public NamedDebuggerFormatException(Throwable cause)  {
      super(cause);
   }
   /**
      <p>Create an NamedDebuggerFormatException.</p>
    */
   public NamedDebuggerFormatException(String message, Throwable cause)  {
      super(message, cause);
   }
}
