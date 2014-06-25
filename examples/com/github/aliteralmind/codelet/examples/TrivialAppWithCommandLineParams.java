/*license*\
   XBN-Java Library

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names LICENSE_lgpl-3.0.txt and LICENSE_asl-2.0.txt. The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.examples;
/**
   <P>For testing {@code {@.codelet.out}}</P>

   <P style="font-size: 125%;"><B>{@code {@.codelet com.github.aliteralmind.codelet.examples.TrivialAppWithCommandLineParams:eliminateCommentBlocksAndPackageDecl()}}
   <BR>{@code {@.codelet.out com.github.aliteralmind.codelet.examples.TrivialAppWithCommandLineParams("string one", "string two")}}</B></P>

{@.codelet com.github.aliteralmind.codelet.examples.TrivialAppWithCommandLineParams:eliminateCommentBlocksAndPackageDecl()}
{@.codelet.out com.github.aliteralmind.codelet.examples.TrivialAppWithCommandLineParams("string one", "string two")}

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <CODE><A HREF="http://xbnjava.aliteralmind.com">http://xbnjava.aliteralmind.com</A></CODE>, <CODE><A HREF="https://github.com/aliteralmind/xbnjava">https://github.com/aliteralmind/xbnjava</A></CODE>
 **/
public class TrivialAppWithCommandLineParams  {
   public static final void main(String[] command_lineParameters)  {
      System.out.println("command_lineParameters[0]=\"" + command_lineParameters[0] + "\"");
      System.out.println("command_lineParameters[1]=\"" + command_lineParameters[1] + "\"");
   }
}
