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
package  com.github.aliteralmind.codelet;
	import  com.github.xbn.lang.CrashIfObject;
/**
	<P>Indicates a line in the template-override configuration file is invalid.</P>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>

 **/
public class TemplateOverridesConfigLineException extends IllegalArgumentException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1657372670112147061L;
	private final int lineNum;
	private final String line;
	public TemplateOverridesConfigLineException(int line_num, String line, String message)  {
		super(TemplateOverridesConfigLineException.getErrorMessage(line_num, line, message));
		lineNum = line_num;
		this.line = line;
	}
	public TemplateOverridesConfigLineException(int line_num, String line)  {
		super(TemplateOverridesConfigLineException.getErrorMessage(line_num, line, null));
		lineNum = line_num;
		this.line = line;
	}
	public TemplateOverridesConfigLineException(int line_num, String line, String message, Throwable cause)  {
		super(TemplateOverridesConfigLineException.getErrorMessage(line_num, line, message), cause);
		lineNum = line_num;
		this.line = line;
	}
	public TemplateOverridesConfigLineException(int line_num, String line, Throwable cause)  {
		super(TemplateOverridesConfigLineException.getErrorMessage(line_num, line, null), cause);
		lineNum = line_num;
		this.line = line;

	}
	public int getLineNumber()  {
		return  lineNum;
	}
	public String getLineText()  {
		return  line;
	}
	public static final String getErrorMessage(int line_num, String line, String message)  {
		try  {
			return  ((message == null) ? "" : message + ". ") +
				"[line " + line_num + "]: " + line.toString();
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(line, "line", null, rx);
		}
	}
}

