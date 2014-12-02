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
package  com.github.aliteralmind.codelet;
   import  com.github.xbn.keyed.SimpleNamed;
/**
   <p>A single gap in a Codelet template, including a function that fills it based on the Codelet instance and global configuration.</p>

   @see  com.github.aliteralmind.codelet.type.SourceCodeTemplate type.SourceCodeTemplate
   @see  com.github.aliteralmind.codelet.type.ConsoleOutTemplate type.ConsoleOutTemplate
   @see  com.github.aliteralmind.codelet.type.SourceAndOutTemplate type.SourceAndOutTemplate
   @see  com.github.aliteralmind.codelet.type.FileTextTemplate type.FileTextTemplate
   @see  com.github.aliteralmind.codelet.UserExtraGapGetter UserExtraGapGetter
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public abstract class CodeletGap extends SimpleNamed  {
   /**
   	<p>Create a new instance with a name.</p>

   	<p>Equal to {@link com.github.xbn.keyed.SimpleNamed#SimpleNamed(String) super}{@code (name)}</p>

   	@param  name  May not be {@code null} or empty, and must contain only letters, digits, and underscores. Get with {@link com.github.xbn.keyed.Named#getName() getName}{@code ()}*.
    **/
   public CodeletGap(String name)  {
      super(name);
   }
   /**
      <p>The output string that replaces the gap.</p>

    * @param  instance  The codelet instance. May not be {@code null}. <i>When creating a ...custom gap... you do not need to check (or try-catch) for {@code null}-ness.</i>
    * @return  A non-{@code null} string.
    */
   public abstract String getFillText(CodeletInstance instance);
}
