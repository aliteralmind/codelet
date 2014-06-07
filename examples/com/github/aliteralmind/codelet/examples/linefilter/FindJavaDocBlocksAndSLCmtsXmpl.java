/*license*\
license*   Codelet

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
package  com.github.aliteralmind.codelet.examples.linefilter;
   import  java.util.Iterator;
   import  com.github.xbn.testdev.GetFromCommandLineAtIndex;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg;
/**
   <P>Uses {@code com.github.aliteralmind.codelet.linefilter.}{@link com.github.aliteralmind.codelet.linefilter.TextLineFilter TextLineFilter} to find (the line numbers of) all JavaDoc blocks and single line comments in a Java source-code file (output is manually generated).</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.FindJavaDocBlocksAndSLCmtsXmpl xbn\examples\text\line\FindJavaDocBlocksAndSLCmtsWSubModes_input.txt}</P>

   @see  com.github.xbn.examples.io.non_xbn.PrintJDBlocksStartStopLineNumsXmpl io.non_xbn.PrintJDBlocksStartStopLineNumsXmpl
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FindJavaDocBlocksAndSLCmtsXmpl  {
   public static final void main(String[] oneRqdParam_javaSourcePath)  {
      Iterator<String> li = GetFromCommandLineAtIndex.fileLineIterator(oneRqdParam_javaSourcePath, 0, null);

      TextLineFilter tlm = new TextLineFilter_Cfg().
         javaDocBlock(null).       //Debug on: System.out, off: null
         javaLineComment(null).    //Debug
         //debugTo(System.out).
         build();

      int lineNum = 0;
      while(li.hasNext())  {
         lineNum++;                //1st iteration: Was 0, now 1
         tlm.getActiveLine(lineNum, li.next());
         if(tlm.isBlockStartLine())  {
            System.out.println(tlm.getLineNumber() + " Block start ");
         }  else if(tlm.isBlockEndLine())  {
            System.out.println(tlm.getLineNumber() + "       end");
         }  else if(tlm.isLineActive())  {
            System.out.println(tlm.getLineNumber() + " line");
         }
      }
      tlm.declareAllLinesAnalyzed();
   }
}
