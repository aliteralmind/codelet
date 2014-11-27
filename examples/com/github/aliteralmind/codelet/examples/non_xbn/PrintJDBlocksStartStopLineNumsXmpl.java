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
package  com.github.aliteralmind.codelet.examples.non_xbn;
	import  com.github.xbn.io.RTIOException;
	import  java.io.File;
	import  java.io.IOException;
	import  java.util.Iterator;
	import  java.util.regex.Pattern;
	import  org.apache.commons.io.FileUtils;
/**
	<P>Prints the start and end line-numbers for all JavaDoc blocks in a Java source-code file. The one and only parameter is the path to the file. This assumes that the JavaDoc open-comment (slash-asterisk-asterisk) is the first non-whitespace on its line. The end comment (asterisk-slash) may be anywhere on the line.</P>

	<P><CODE>java com.github.codelet.examples.non_xbn.PrintJDBlocksStartStopLineNumsXmpl xbnjava\xbn\examples\<!--escape-u is illegal-->util\PrintJDBlocksStartStopLineNumsXmpl.java</CODE></P>

	<P>Self-answered question on stackoverflow.com
	<BR> &nbsp; &nbsp; <CODE><A HREF="http://stackoverflow.com/questions/21312336/how-to-detect-each-javadoc-block-start-and-end-line-in-a-source-code-file-self">http://stackoverflow.com/questions/21312336/how-to-detect-each-javadoc-block-start-and-end-line-in-a-source-code-file-self</A></CODE></P>

	@see  com.github.xbn.examples.text.line.FindJavaDocBlocksAndSLCmtsXmpl
	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class PrintJDBlocksStartStopLineNumsXmpl  {
	/**
		<P>The main function.</P>
	 **/
	public static final void main(String[] as_1RqdJavaSourcePath)  {

		//Read command-line parameter
			String sJPath = null;
			try  {
				sJPath = as_1RqdJavaSourcePath[0];
			}  catch(ArrayIndexOutOfBoundsException aibx)  {
				throw  new NullPointerException("Missing one-and-only required parameter: Path to java source-code file.");
			}
			System.out.println("Java source: " + sJPath);

		//Establish line-iterator
			Iterator<String> lineItr = null;
			try  {
				lineItr = FileUtils.lineIterator(new File(sJPath)); //Throws npx if null
			}  catch(IOException iox)  {
				throw  new RTIOException("PrintJDBlocksStartStopLinesXmpl", iox);
			}
			Pattern pTrmdJDBlockStart = Pattern.compile("^[\\t ]*/\\*\\*");

		String sDD = "..";
		int lineNum = 1;
		boolean bInJDBlock = false;
		while(lineItr.hasNext())  {
			String sLn = lineItr.next();
			if(!bInJDBlock)  {
				if(pTrmdJDBlockStart.matcher(sLn).matches())  {
					bInJDBlock = true;
					System.out.print(lineNum + sDD);
				}
			}  else if(sLn.indexOf("*/") != -1)  {
				bInJDBlock = false;
				System.out.println(lineNum);
			}
			lineNum++;
		}
		if(bInJDBlock)  {
			throw  new IllegalStateException("Reach end of file. JavaDoc not closed.");
		}
	}
	/**
		<P>Another one</P>
	 **/
	private static final void oneMoreForGoodMeasure()  {
	}
}