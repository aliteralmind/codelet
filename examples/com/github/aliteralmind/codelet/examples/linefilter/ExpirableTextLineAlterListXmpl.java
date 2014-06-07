/*license*\
   Codelet

   XBN-Java is a collection of generically-useful backend (non-GUI) programming utilities, featuring automated insertion of example code into JavaDoc, regular expression convenience classes, shareable self-returning method chains, and highly-configurable output for lists.

   Copyright (C) 2014, Jeff Epstein

   This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA*)
\*license*/
package  com.github.aliteralmind.codelet.examples.linefilter;
   import  com.github.aliteralmind.codelet.linefilter.AdaptRegexReplacerTo;
   import  com.github.aliteralmind.codelet.linefilter.ExpirableTextLineAlterList;
   import  com.github.aliteralmind.codelet.linefilter.TextLine;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAlterer;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAltererAdapter;
   import  com.github.xbn.analyze.alter.ExpirableElements;
   import  com.github.xbn.analyze.alter.MultiAlterType;
   import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
   import  com.github.xbn.analyze.validate.ValidResultFilter;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  com.github.xbn.regexutil.StringReplacer;
   import  java.util.regex.Pattern;
/**
   <P>Demonstrates {@code com.github.xbn.analyze.alter.}{@link com.github.aliteralmind.codelet.linefilter.ExpirableTextLineAlterList ExpirableTextLineAlterList}: Alter some duplicate names, and once the alteration-limit for each name is met, expire its alterer.</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.ExpirableTextLineAlterListXmpl}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class ExpirableTextLineAlterListXmpl  {
   private static final String LINE_SEP = System.getProperty("line.separator", "\n");
   public static final void main(String[] ignored)  {
      //Pre-setup
         String sJA = "Johnny Appleseed";
         String sKF = "Kermit T. Frog";
         String sGG = "The Green Giant";
         int lineNum = 1;
         String[] asLines = new String[]  {
            (lineNum++) + " " + sJA,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sGG,
            (lineNum++) + " " + sJA,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sGG,
            (lineNum++) + " " + sJA,
            (lineNum++) + " " + sJA,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sKF,
            (lineNum++) + " " + sGG};

         System.out.println("Original:");
         for(String s : asLines)  {
            System.out.println(s);
         }

         System.out.println();

      //Configuration

         System.out.println("Replacing 1st and 2nd \"Apple\" with \"Pear\"");

            ValidResultFilter vrf12 = NewValidResultFilterFor.
               inUnchangedOutFalse(1, 2, null, null,
               null);    //<--Debugging: System.out. No debugging: null;
            TextLineAltererAdapter<StringReplacer> atlrAppleToPear12 = AdaptRegexReplacerTo.
               lineReplacer(
                  Pattern.compile("Apple", Pattern.LITERAL),
                  "Pear", ReplacedInEachInput.FIRST, vrf12,
               null);    //<--Debugging


         System.out.println("Replacing 2nd-through-4th \"T.\" with \"The\"");

            ValidResultFilter vrf234 = NewValidResultFilterFor.
               inUnchangedOutFalse(2, 4, null, null,
               null);    //<--Debugging
            TextLineAltererAdapter<StringReplacer> atlrTDotToThe234 = AdaptRegexReplacerTo.
               lineReplacer(
                  Pattern.compile("T.", Pattern.LITERAL),
                  "The", ReplacedInEachInput.FIRST, vrf234,
               null);    //<--Debugging


         System.out.println("Replacing 2nd \"Green\" with \"GREEN\"");

            ValidResultFilter vrf2Only = NewValidResultFilterFor.
               inUnchangedOutFalse(2, 2, null, null,
               null);    //<--Debugging
            TextLineAltererAdapter<StringReplacer> atlrCapGreen2Only = AdaptRegexReplacerTo.
               lineReplacer(
                  Pattern.compile("Green", Pattern.LITERAL),
                  "GREEN", ReplacedInEachInput.FIRST, vrf2Only,
               null);    //<--Debugging

         System.out.println();

         ExpirableTextLineAlterList avxl = new ExpirableTextLineAlterList(
            new TextLineAlterer[] {atlrAppleToPear12, atlrTDotToThe234, atlrCapGreen2Only},
            ExpirableElements.REQUIRED, MultiAlterType.SHORT_CIRCUIT,
               null);    //<--Debugging

         //avxl.setDebug(System.out, true);

      //Go

         for(int i = 0; i < asLines.length; i++)  {
            String s = asLines[i];
//				System.out.println(avxl.getAltered((new TextLine(i, s)), s) +
            System.out.println(avxl.getAltered(i, s) +
               "  (Alters=" + avxl.size() +
               (avxl.isExpired() ? ", expired" : "") + ")");
         }
   }
}
