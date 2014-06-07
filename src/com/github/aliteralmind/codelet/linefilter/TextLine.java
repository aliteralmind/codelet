/*license*\
   Codelet: http://codelet.aliteralmind.com

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
   import  com.github.xbn.lang.Copyable;
/**
   <P>One in a series of text lines, most commonly derived from a file.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TextLine extends SimpleLineObject<String> implements Copyable  {
   /**
      <P>Create a new instance.</P>
    **/
   public TextLine(int line_num, String text)  {
      super(line_num, text);
   }
   /**
      <P>Create a new instance as a duplicate of another.</P>
    **/
   public TextLine(TextLine to_copy)  {
      super(
         SimpleLineObject.getNumberCINull(to_copy, "to_copy"),
         to_copy.getBody());
   }
   /**
      <P>Get a duplicate of this <CODE>TextLine</CODE>.</P>

      @return  <CODE>(new {@link #TextLine(TextLine) TextLine}(this))</CODE>
    **/
   public TextLine getObjectCopy()  {
      return  (new TextLine(this));
   }
}
