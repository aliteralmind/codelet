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
package  com.github.aliteralmind.codelet;
   import  com.github.xbn.keyed.SimpleNamed;
/**
   <P>A single gap in a Codelet template, including a function that fills it based on the Codelet instance and global configuration.</P>

   @see  com.github.aliteralmind.codelet.type.SourceCodeTemplate type.SourceCodeTemplate
   @see  com.github.aliteralmind.codelet.type.ConsoleOutTemplate type.ConsoleOutTemplate
   @see  com.github.aliteralmind.codelet.type.SourceAndOutTemplate type.SourceAndOutTemplate
   @see  com.github.aliteralmind.codelet.type.FileTextTemplate type.FileTextTemplate
   @see  com.github.aliteralmind.codelet.UserExtraGapGetter UserExtraGapGetter
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public abstract class CodeletGap extends SimpleNamed  {
   /**
   	<P>Create a new instance with a name.</P>

   	<P>Equal to {@link com.github.xbn.keyed.SimpleNamed#SimpleNamed(String) super}{@code (name)}</P>

   	@param  name  May not be {@code null} or empty, and must contain only letters, digits, and underscores. Get with {@link com.github.xbn.keyed.Named#getName() getName}{@code ()}*.
    **/
   public CodeletGap(String name)  {
      super(name);
   }
   /**
      <P>The output string that replaces the gap.</P>

      @param  instance  The codelet instance. May not be {@code null}. <I>When creating a ...custom gap... you do not need to check (or try-catch) for {@code null}-ness.</I>
      @return  A non-{@code null} string.
    **/
   public abstract String getFillText(CodeletInstance instance);
}
