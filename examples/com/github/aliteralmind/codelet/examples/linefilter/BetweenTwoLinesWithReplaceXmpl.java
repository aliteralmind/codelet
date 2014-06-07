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
package  com.github.aliteralmind.codelet.examples.linefilter;
   import  com.github.xbn.testdev.GetFromCommandLineAtIndex;
   import  com.github.aliteralmind.codelet.linefilter.BlockMarks;
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineFilterFor;
   import  com.github.aliteralmind.codelet.linefilter.TextLineIterator;
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  java.util.Iterator;
   import  java.util.regex.Pattern;
/**
   <P>YYY</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.BetweenTwoLinesWithReplaceXmpl com\\github\\aliteralmind\\codelet\\examples\\linefilter\\doc-files\\BetweenTwoLinesWithReplaceXmpl_input.txt}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class BetweenTwoLinesWithReplaceXmpl  {
   public static final void main(String[] pathToInput_idx0)  {
      Iterator<String> inputLineItr = GetFromCommandLineAtIndex.fileLineIterator(pathToInput_idx0, 0, null);
      String LINE_SEP = System.getProperty("line.separator", "\r\n");

      String startLinePtrn = "(public String getName)";
      String endLinePtrn = "\\} +//end snippet";

      TextLineIterator getNameSnippetLineItr = NewTextLineFilterFor.lineRangeWithReplace(
            BlockMarks.INCLUSIVE,
            Pattern.compile(startLinePtrn), "$1", ReplacedInEachInput.FIRST, null,
               null,     //dbgStart. on=System.out, off=null
            Pattern.compile(endLinePtrn), "}", ReplacedInEachInput.FIRST, null,
               null,     //dbgEnd
               null).    //dbgMode
         activeLineIterator(inputLineItr,
               null);    //debug

      while(getNameSnippetLineItr.hasNext())  {
         System.out.println(getNameSnippetLineItr.next().getBody());
      }   //end BetweenTwoLinesWithReplaceXmpl snippet
   }
}

/*
      String input = new StringBuilder().
         append("public class NameClass  {"          ).append(LINE_SEP).
         append("   private final String name;"      ).append(LINE_SEP).
         append("   public NameClass(String name)  {").append(LINE_SEP).
         append("      this.name = name;"            ).append(LINE_SEP).
         append("   }"                               ).append(LINE_SEP).
                                                       append(LINE_SEP).
         append("   public String getName()  {"      ).append(LINE_SEP).
         append("      return  name;"                ).append(LINE_SEP).
         append("   }  //end snippet"                ).append(LINE_SEP).
                                                       append(LINE_SEP).
         append("   public String getLowerCase()  {" ).append(LINE_SEP).
         append("      return  getName().toLowerCase();").append(LINE_SEP).
         append("   }"                               ).append(LINE_SEP).
         append("}"                                  ).toString();

 */
