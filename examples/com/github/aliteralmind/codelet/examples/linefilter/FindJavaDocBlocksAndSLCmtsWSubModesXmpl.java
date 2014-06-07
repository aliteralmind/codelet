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
package  com.github.aliteralmind.codelet.examples.linefilter;
   import  java.util.Iterator;
   import  com.github.aliteralmind.codelet.linefilter.z.BlockEnd;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  com.github.xbn.testdev.GetFromCommandLineAtIndex;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg;
/**
   <P>Uses {@code com.github.aliteralmind.codelet.linefilter.TextLineFilter} to find (the line numbers of) all JavaDoc blocks and single-line comments within a Java source-code file--this also detects sub-modes that may be contained within each JavaDoc block (output occurs automatically, via <CODE>LineFilter</CODE> debugging).</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.FindJavaDocBlocksAndSLCmtsWSubModesXmpl xbn\examples\text\line\FindJavaDocBlocksAndSLCmtsWSubModes_input.txt}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FindJavaDocBlocksAndSLCmtsWSubModesXmpl  {
   public static final void main(String[] oneRqdParam_javaSourcePath)  {
      Iterator<String> lineItr = GetFromCommandLineAtIndex.fileLineIterator(oneRqdParam_javaSourcePath, 0, null);

      //Sub-mode, which is only valid within JavaDoc blocks:

         String sSubBlockMarkPrefix = "/" + "/sub-mode 1 block...";

         TextLineFilter tlmSub = new TextLineFilter_Cfg(1, "submode").
            start(NewPatternFor.literal(sSubBlockMarkPrefix + "START"), null, null).
            end(BlockEnd.REQUIRED, NewPatternFor.literal(sSubBlockMarkPrefix + "END"), null, null).
            debugTo(null).         //Debug on: System.out, off: null
            build();

      TextLineFilter tlm = new TextLineFilter_Cfg().
         javaDocBlock(null).       //Debug
         javaLineComment(null).    //Debug
            //This must be System.out in order to see
            //this example code's output [1/2]:
            debugTo(System.out).
         setSubModes(new TextLineFilter[]{tlmSub}).
         build();

      int lineNum = 0;
      while(lineItr.hasNext())  {
         lineNum++;					 //1st iteration: Was 0, now 1
         //Output is from debugging [2/2]
         tlm.getActiveLine(lineNum, lineItr.next());
      }
      tlm.declareAllLinesAnalyzed();
   }
}
