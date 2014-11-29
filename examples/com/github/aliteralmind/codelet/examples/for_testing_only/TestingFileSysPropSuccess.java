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
	<p>For testing a <i>successful use</i> of a directory root environment variable ({@code $<root_dir>}) in a {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}}--this will only succeed if a file named {@code "INSTALL"} exists on your machine, in the path derived from the system property (environment variable) named {@code "ANT_HOME"} (which is required by all <a href="http://ant.apache.org">Ant</a> installs). If it does exist, the contents of that file will be printed below. If it doesn't, this entire JavaDoc block will be suppressed, and a stack-trace will be in the JavaDoc output.</p>

	<h3><u>Taglet:</u></h3>

	<P style="font-size: 125%;"><b>{@code {@.file.textlet $<ANT_HOME>/INSTALL}}</b></p>

	<h3><u>Replaced with:</u></h3>

	<p><i>(Output begins and ends between the horizontal lines.)</i></p>

	<HR/>

{@.file.textlet $<ANT_HOME>/INSTALL}

	<HR/>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class TestingFileSysPropSuccess  {
}