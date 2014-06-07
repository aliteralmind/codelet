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
   import  java.util.regex.Pattern;
   import  com.github.xbn.testdev.GetFromCommandLineAtIndex;
   import  com.github.aliteralmind.codelet.linefilter.LineObject;
   import  com.github.aliteralmind.codelet.linefilter.TextLineFilter;
   import  com.github.aliteralmind.codelet.linefilter.z.BlockEnd;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveBlockLines;
   import  com.github.aliteralmind.codelet.linefilter.z.InactiveLines;
   import  com.github.aliteralmind.codelet.linefilter.z.ActiveSingleLines;
   import  com.github.aliteralmind.codelet.linefilter.z.TextLineFilter_Cfg;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  java.util.Iterator;
/**
   <P>Uses <CODE>{@link com.github.aliteralmind.codelet.linefilter.TextLineFilter TextLineFilter}.{@link com.github.aliteralmind.codelet.linefilter.TextLineFilter#activeLineIterator(Iterator, Appendable) activeLineIterator}</CODE> to output all <I>inactive</I> lines, for the purpose of eliminating the license and {@code @}{@code author} tag from <I>this</I> example code's source-code--the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#isBlockActive() active block} is the license-comment, and the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#isLineActive() active line} is the at-author tag line. </P>

   <P>{@code java com.github.aliteralmind.codelet.examples.linefilter.IterateInactiveLinesOnly xbn\examples\text\line\IterateInactiveLinesOnly.java}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class IterateInactiveLinesOnly  {
   public static final void main(String[] oneRqdParam_javaSourcePath)  {
      Iterator<String> lineItr = GetFromCommandLineAtIndex.fileLineIterator(oneRqdParam_javaSourcePath, 0, null);

      TextLineFilter modeElimLicenseBlockAuthorLine =  new TextLineFilter_Cfg().
         start(NewPatternFor.literal("/"+"*license*\\"), null,
            null).          //debug. on=System.out, off=null
         end(BlockEnd.REQUIRED, NewPatternFor.literal("\\*license*"+"/"), null,
            null).          //debug
         line(Pattern.compile("^.*@"+"author.*$"), null,
            null).          //debug
         debugTo(null).     //debug the mode itself,
                            //(including the active-line iterator)
         ifBlockOrLineOrNotActive(ActiveBlockLines.DISCARD, ActiveSingleLines.DISCARD, InactiveLines.KEEP).
         build();

      Iterator<LineObject<String>> inactvLineItr = modeElimLicenseBlockAuthorLine.activeLineIterator(lineItr,
         null);             //debug

      while(inactvLineItr.hasNext())  {
         System.out.println(inactvLineItr.next().getBody());
      }
   }
}
