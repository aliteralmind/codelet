/*license*\
   Codelet

   XBN-Java is a collection of generically-useful backend (non-GUI) programming utilities, featuring automated insertion of example code into JavaDoc, regular expression convenience classes, shareable self-returning method chains, and highly-configurable output for lists.

   Copyright (C) 2014, Jeff Epstein

   This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA*)
\*license*/
package  com.github.aliteralmind.codelet.examples.linefilter;
   import  java.util.regex.Pattern;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.StringValidatorReplacer;
   import  com.github.xbn.regexutil.z.RegexReplacer_Cfg;
/**
   <P>Uses a {@code com.github.xbn.regexutil.}{@link com.github.xbn.regexutil.StringValidatorReplacer StringValidatorReplacer} and {@code com.github.xbn.analyze.validate.}{@link com.github.xbn.analyze.validate.FilterValidCounts FilterValidCounts} to replace the second-through-fourth occurances of a string.</P>

   <P>{@code java com.github.xbn.examples.regexutil.StringValidatorReplacerXmpl}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class StringValidatorReplacerXmpl  {
   public static final void main(String[] ignored)  {
      System.out.println("Replacing 2nd-through-4th \"T.\" with \"The\"");

      ValidResultFilter vrf = NewValidResultFilterFor.inUnchangedOutFalse(2, 4, null, null,
         null);     //<--Debug filter: System.out. No debug: null

      StringValidatorReplacer svrTDotToThe234 = AdaptRegexReplacerTo.stringValidator(
         Pattern.compile("T.", Pattern.LITERAL),
         "The", ReplacedInEachInput.FIRST, vrf,
            null);  //<--Debug

      for(int i = 0; i < 10; i++)  {
         String sKTF = "Kermit T. Frog";
         if(svrTDotToThe234.isValid(sKTF))  {
            sKTF = svrTDotToThe234.getMostRecent();
         }
         System.out.println(sKTF);
      }
   }
}
