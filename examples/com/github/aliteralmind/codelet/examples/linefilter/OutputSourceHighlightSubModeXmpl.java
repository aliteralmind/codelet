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
   import  com.github.xbn.regexutil.ReplacedInEachInput;
   import  java.util.Iterator;
   import  com.github.aliteralmind.codelet.linefilter.z.BlockEnd;
   import  com.github.xbn.testdev.GetFromCommandLineAtIndex;
   import  java.util.regex.Pattern;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg;
/**
   <P>Uses {@code com.github.aliteralmind.codelet.linefilter.TextLineFilter} to alter the sub-mode lines contained in a Java source-code file, and otherwise print the source-code unchanged.</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.OutputSourceHighlightSubModeXmpl xbn\examples\text\line\FindJavaDocBlocksAndSLCmtsWSubModes_input.txt}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class OutputSourceHighlightSubModeXmpl  {
   public static final void main(String[] oneRqdParam_javaSourcePath)  {
      Iterator<String> lineItr = GetFromCommandLineAtIndex.fileLineIterator(oneRqdParam_javaSourcePath, 0, null);

      //Sub-mode, which is only valid within JavaDoc blocks:
         //Regex
            String sRESubModePre = "" +
               "^" +								 //Line-start
               "(.+?)" +							//All characters...	  (GROUP 1)
               "[ \t]*" +						  //...up-to-but-not-including one or more
                                             //	tabs or spaces
               "/" + "/" +						 //Followed by a literal single-line comment
                                             //	(separated into two strings so the
                                             //	compiler doesn't get confused)
               "sub-mode 1 block\\.\\.\\.";  //Followed by "sub-mode 1 block..."
            String sRESubBlockStart = sRESubModePre + "START$";  //Followed by "START/END",
            String sRESubBlockEnd = sRESubModePre + "END$";		//then end-of-line
            String sReplaceWithGroup1 = "$1";

         //Configure
            TextLineFilter tlmSub = new TextLineFilter_Cfg(1, "submode").
               startReplacer(Pattern.compile(sRESubBlockStart), sReplaceWithGroup1, ReplacedInEachInput.MATCHES, null, null).
               endReplacer(BlockEnd.REQUIRED, Pattern.compile(sRESubBlockEnd), sReplaceWithGroup1, ReplacedInEachInput.MATCHES, null, null).
               //debugTo(System.out).
               build();

      //Parent mode (makes a defensive copy of sub-mode)
         TextLineFilter tlm = new TextLineFilter_Cfg().
            javaDocBlock(null).javaLineComment(null).
            setSubModes(new TextLineFilter[] {tlmSub}).
            //debugTo(System.out).
            build();

      int lineNum = 0;
      while(lineItr.hasNext())  {
         tlm.resetState();
         String sOrigLine = lineItr.next();
         String sActiveLine = tlm.getActiveLine(++lineNum, sOrigLine);	//lineNum: 1st iteration: Was 0, now 1
         System.out.println((tlm.getActiveSub() != null)
            ?  sActiveLine.replaceFirst("paragraph", "paragraph IN THE SUB-MODE!!!")
            :  sOrigLine);
      }
      tlm.declareAllLinesAnalyzed();
   }
}
