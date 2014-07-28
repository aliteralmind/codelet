/*license*\
   Codelet: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.examples.for_testing_only;
/**
   <P>For testing a <I>successful use</I> of a directory root environment variable ({@code $<root_dir>}) in a {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}}--this will only succeed if a file named {@code "INSTALL"} exists on your machine, in the path derived from the system property (environment variable) named {@code "ANT_HOME"} (which is required by all <A HREF="http://ant.apache.org">Ant</A> installs). If it does exist, the contents of that file will be printed below. If it doesn't, this entire JavaDoc block will be suppressed, and a stack-trace will be in the JavaDoc output.</P>

   <H3><U>Taglet:</U></H3>

   <P style="font-size: 125%;"><B>{@code {@.file.textlet $<ANT_HOME>/INSTALL}}</B></P>

   <H3><U>Replaced with:</U></H3>

   <P><I>(Output begins and ends between the horizontal lines.)</I></P>

   <HR/>

{@.file.textlet $<ANT_HOME>/INSTALL}

   <HR/>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TestingFileSysPropSuccess  {
}