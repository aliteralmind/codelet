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
	import  com.github.xbn.util.EnumUtil;
/**
	<p>The type of a single JavaDoc codelet instance.</p>

	@author  Copyright (C) 2014, Jeff Epstein, released under the LPGL 2.1. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public enum CodeletType  {
	/**
		<p>{@code {@.codelet}}: A taglet that displays the example code's source code.</p>

		<p>This sets<ol>
			<li>{@link #getName() getName}{@code ()} to {@code ".codelet"}</li>
			<li>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getSourceConfig_"}</li>
		</ol></p>

		<h3>{@code {@.codelet}}: Format</h3>

		<p><code>{&#64;.codelet <i>fully.qualified.ClassName</i>[:<a href="CustomizationInstructions.html#overview">customizerFunction</a>()]}</code></p>

		<p>The customizer portion is optional, but when provided, must be preceded by a {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR percent sign} ({@code '%'}).</p>

		<p><b>Examples:</b></p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName}}</blockquote>

		<p>Prints out all lines in (assuming Windows)
		<br/> &nbsp; &nbsp; {@code fully\qualified\examples\ExampleClassName.java}
		<br/>Where {@code "fully"} is in the {@linkplain CodeletBaseConfig#EXAMPLE_CLASS_SRC_BASE_DIR example-code base directory} as configured.</p>

<blockquote><code>{&#64;.codelet fully.qualified.examples.ExampleClassName:{@link com.github.aliteralmind.codelet.BasicCustomizers#lineRange(CodeletInstance, CodeletType, Integer, Boolean, String, Integer, Boolean, String, String) lineRange}(1, false, "text in start line", 1, false, "text in end line")}</code></blockquote>

		<p>Same as above, but only displays a <a href="{@docRoot}/overview-summary.html#xmpl_snippet">portion</a> of the lines, starting and ending with lines that contain specific text (inclusive).</p>

		@see  #CONSOLE_OUT
		@see  #SOURCE_AND_OUT
		@see  #FILE_TEXT
		@see  #isSourceCode()
		@see  com.github.aliteralmind.codelet.type.SourceCodeProcessor
		@see  com.github.aliteralmind.codelet.type.SourceCodeTemplate
		@see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
	 **/
	SOURCE_CODE(".codelet", "getSourceConfig_"),
				//The value of the second parameter ("getSourceConfig_") must be the
				//same for both SOURCE_CODE and SOURCE_AND_OUT
	/**
		<p>{@code {@.codelet.out}}: A taglet that displays the example code's console output (via <code>java.lang.{@link java.lang.System System}.{@link java.lang.System#out out}</code>).</p>

		<p>This sets<ol>
			<li>{@link #getName() getName}{@code ()} to {@code ".codelet.out"}</li>
			<li>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getConsoleOutConfig_"}</li>
		</ol></p>

		<h3>{@code {@.codelet.out}}: Format</h3>

		<p><code>{&#64;.codelet.out <i>fully.qualified.ClassName</i>[(&quot;Command line params&quot;, false, -1)][:<a href="CustomizationInstructions.html#overview">customizerFunction</a>()]}</code></p>

		<p><ul>
			<li>The command-line parameters are optional. When not provided, an empty string array is passed to the example-code's <a href="http://docs.oracle.com/javase/tutorial/getStarted/application/index.html#MAIN">{@code main} function</a>. When provided, it must be formatted as specified by
			<br/> &nbsp; &nbsp; <code>com.github.xbn.util.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</code>
			<li>The customizer portion is optional.</li>
		</ul></p>

		<p><b>Examples:</b></p>

<blockquote>{@code {@.codelet.out fully.qualified.examples.ExampleClassName}}</blockquote>

		<p>ReplacedInEachInput the taglet with the entire console output.</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params")}}</blockquote>

		<p>Same as above, but passes a five-element string array to the main function.</p>

		@see  #SOURCE_CODE
		@see  #isConsoleOut()
		@see  com.github.aliteralmind.codelet.type.ConsoleOutProcessor
		@see  com.github.aliteralmind.codelet.type.ConsoleOutTemplate
		@see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
	 **/
	CONSOLE_OUT(".codelet.out", "getConsoleOutConfig_"),
	/**
		<p>{@code {@.codelet.and.out}}: A taglet that displays the example code's source code and console output.</p>

		<p>This sets<ol>
			<li>{@link #getName() getName}{@code ()} to {@code ".codelet.and.out"}</li>
			<li>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getSourceConfig_"}</li>
		</ol></p>

		<h3>{@code {@.codelet.and.out}}: Format</h3>

		<p><code>{&#64;.codelet.and.out <i>fully.qualified.ClassName</i>[(&quot;Command line params&quot;, false, -1)][:customizerFunction()]}</code></p>

		<p>See the format requirements for both {@link #SOURCE_CODE {@.codelet}} and {@link #CONSOLE_OUT {@.codelet.out}} for examples.</p>

<blockquote>{@code {@.codelet.and.out fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params"):customizerFunction()}}</blockquote></p>

		<p>is essentially equal to</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:customizerFunction()}{@.codelet.out fully.qualified.examples.ExampleClassName("command", -1, "line", true, "params")}}</blockquote></p>

		<p>A customizer in a {@code {@.codelet.and.out}} taglet is only applied to the source code. To also customize the output, use</p>

	<P style="font-size: 125%;"><b><code>{&#64;.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo%customizerForSourceCode()}
	<br/>{&#64;.codelet.out com.github.aliteralmind.codelet.examples.adder.AdderDemo%customizerForOutput()}</code></b></p>

		@see  #SOURCE_CODE
		@see  #isSourceAndOut()
		@see  com.github.aliteralmind.codelet.type.SourceAndOutProcessor
		@see  com.github.aliteralmind.codelet.type.SourceAndOutTemplate
		@see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_AND_OUT_TMPL_PATH CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
	 **/
	SOURCE_AND_OUT(".codelet.and.out", "getSourceConfig_"),
				//The value of the second parameter ("getSourceConfig_") must be the
				//same for both SOURCE_CODE and SOURCE_AND_OUT
	/**
		<p>{@code {@.file.textlet}}: A taglet that displays the contents of a plain text file.</p>

		<p>This sets<ol>
			<li>{@link #getName() getName}{@code ()} to {@code ".file.textlet"}</li>
			<li>{@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()} to {@code "getFileTextConfig_"}</li>
		</ol></p>

		<h3>{@code {@.file.textlet}}: Format</h3>

		<p><code>{&#64;.file.textlet <i>path\to\file.txt</i>[:<a href="CustomizationInstructions.html#overview">customizerFunction</a>()]}</code></p>

		<p>Replaced with all lines in a plain-text file, such as for displaying an example code's input. The <b>customizer function</b> is optional. The <b>path</b> may be<ul>
			<li><a href="http://docs.oracle.com/javase/tutorial/essential/io/path.html#relative">absolute</a>,</li>
			<li>relative to the directory in which {@code javadoc.exe} was invoked, or,</li>
			<li>relative to the directory of the taglet's {@linkplain CodeletInstance#getEnclosingFile() enclosing file}.</li>
		</ul>This list also represents the order in which the search occurs.</p>

		<p><i>(New for version {@code 0.1.3}):</i> Absolute paths must contain the <a href="http://en.wikipedia.org/wiki/Path_(computing)#Representations_of_paths_by_operating_system_and_shell">file separators exactly as required by your system</a> (such as {@code "C:\directory\subdir\file.txt"}). Otherwise all <i>single</i> url-slashes ({@code '/'}) used as file separators are changed to</p>

<blockquote><pre>{@link java.lang.System}.{@link java.lang.System#getProperty(String) getProperty}("file.separator", &quot;\\&quot;)</pre></blockquote>

		<p>For example, on Microsoft Windows, {@code "java_code/input.txt"} is changed to {@code "java_code\input.txt"}.</p>

		<p>A single environment variable may be used to prefix the path. For example:</p>

<blockquote><pre>$&lt;base_directory&gt;/filelets/input.txt</pre></blockquote>

		<p>When used, it is required that the first two characters in the path are <code>&quot;$&lt;&quot;</code>, followed by the environment variable name, followed by a close sharp (<code>'&gt;'</code>). This environment variable, as returned by <i>either</i></p>

<blockquote><pre>{@link java.lang.System}.{@link java.lang.System#getProperty(String) getProperty}(&quot;base_directory&quot;)</pre></blockquote>

or

<blockquote><pre>System.{@link java.lang.System#getenv(String) getenv}(&quot;base_directory&quot;)</pre></blockquote>

		<p>must be a non-null, non-empty value, and be either a system property or environment variable--not both. <i>(New for version {@code 0.1.3}, the idea from Stack Overflow user <a href="http://stackoverflow.com/users/3622940/unihedron">Unihedron</a>.)</i></p>

		@see  #SOURCE_CODE
		@see  #isFileText()
		@see  com.github.aliteralmind.codelet.type.FileTextProcessor
		@see  com.github.aliteralmind.codelet.type.FileTextTemplate
		@see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_FILE_TEXT_TMPL_PATH CodeletBaseConfig#DEFAULT_FILE_TEXT_TMPL_PATH
	 **/
	FILE_TEXT(".file.textlet", "getFileTextConfig_");

	private final String tagName;
	private final String defaultLineProcNamePrefix;
	/**
		<p>Construct an {@code CodeletType}.</p>

		@param  tag_name  The name of the codelet. <i>Should</i> not be {@code null} or empty. Get with {@link #getName() getName}{@code ()}
		@param  default_lineProcNamePrefix  The default prefix for Customizers of this type. <i>Should</i> not be {@code null} or empty, and should end with an underscore ({@code '_'}). Get with {@link #getDefaultLineProcNamePrefix() getDefaultLineProcNamePrefix}{@code ()}.
		@see  #SOURCE_CODE
	 **/
	CodeletType(String tag_name, String default_lineProcNamePrefix)  {
		tagName = tag_name;
		defaultLineProcNamePrefix = default_lineProcNamePrefix;
	}
	/**
		<p>The {@linkplain com.sun.javadoc.Tag#name() name} of this taglet, as used in JavaDoc.</p>

		@return  For example, if the taglet is
		<br/> &nbsp; &nbsp; {@code {@.codelet.out my.package.examples.AnExample}}
		<br/>this returns {@code ".codelet.out"}</p>
		@see  #CodeletType(String, String) CodeletType(s,s)
		@see  #newTypeForTagletName(String, String) newTypeForTagletName(s,s)
	 **/
	public String getName()  {
		return  tagName;
	}
	/**
		<p>The default prefix for Customizers of this type.</p>

		<p>This is intended to be followed by either the example classes {@linkplain java.lang.Class#getSimpleName() simple name}, or the <i>explicitely-provided</i> function-name-postfix for the plain-text file being displayed.</p>
		@see  #CodeletType(String, String)
	 **/
	public String getDefaultLineProcNamePrefix()  {
		return  defaultLineProcNamePrefix;
	}
	/**
		<p>Is this {@code CodeletType} equal to {@code SOURCE_CODE}?.</p>

		@return  <code>this == {@link #SOURCE_CODE}</code>

		@see  #isConsoleOut()
		@see  #isFileText()
		@see  #isSourceAndOut()
	 **/
	public final boolean isSourceCode()  {
		return  this == SOURCE_CODE;
	}
	/**
		<p>Is this {@code CodeletType} equal to {@code CONSOLE_OUT}?.</p>

		@return  <code>this == {@link #CONSOLE_OUT}</code>
		@see  #isSourceCode()
	 **/
	public final boolean isConsoleOut()  {
		return  this == CONSOLE_OUT;
	}
	/**
		<p>Is this {@code CodeletType} equal to {@code FILE_TEXT}?.</p>

		@return  <code>this == {@link #FILE_TEXT}</code>
		@see  #isSourceCode()
	 **/
	public final boolean isFileText()  {
		return  this == FILE_TEXT;
	}
	/**
		<p>Is this {@code CodeletType} equal to {@code SOURCE_AND_OUT}?.</p>

		@return  <code>this == {@link #SOURCE_AND_OUT}</code>
		@see  #isSourceCode()
	 **/
	public final boolean isSourceAndOut()  {
		return  this == SOURCE_AND_OUT;
	}

	/**
		<p>If an <code>CodeletType</code> is not a required value, crash.</p>

		<p>Equal to
		<br/> &nbsp; &nbsp; <code>{@link com.github.xbn.util.EnumUtil EnumUtil}.{@link com.github.xbn.util.EnumUtil#crashIfNotRequiredValue(Enum, Enum, String, Object) crashIfNotRequiredValue}(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo)</code></p>
		@see  #crashIfForbiddenValue(CodeletType, String, Object) crashIfForbiddenValue(ert,s,o)
	 **/
	public void crashIfNotRequiredValue(CodeletType e_rqd, String s_thisEnumsVarNm, Object o_xtraInfo)  {
		EnumUtil.crashIfNotRequiredValue(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo);
	}
	/**
		<p>If an <code>CodeletType</code> is a forbidden value, crash.</p>

		<p>Equal to
		<br/> &nbsp; &nbsp; <code>{@link com.github.xbn.util.EnumUtil EnumUtil}.{@link com.github.xbn.util.EnumUtil#crashIfForbiddenValue(Enum, Enum, String, Object) crashIfForbiddenValue}(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo)</code></p>
		@see  #crashIfNotRequiredValue(CodeletType, String, Object) crashIfNotRequiredValue(ert,s,o)
	 **/
	public void crashIfForbiddenValue(CodeletType e_rqd, String s_thisEnumsVarNm, Object o_xtraInfo)  {
		EnumUtil.crashIfForbiddenValue(this, e_rqd, s_thisEnumsVarNm, o_xtraInfo);
	}
	/**
		<p>Is a string equal to <i>this</i> taglet type's name?.</p>

		@param  name  May not be {@code null}.
		@return  <code>name.equals({@link #getName() getName}())</code>
	 **/
	public boolean isNameEqualTo(String name)  {
		return  isNameEqualTo(name, "name");
	}
		private boolean isNameEqualTo(String name, String name_varName)  {
			try  {
				return  name.equals(getName());
			}  catch(RuntimeException rx)  {
				throw  CrashIfObject.nullOrReturnCause(name, name_varName, null, rx);
			}
		}
	/**
		<p>Get a new {@code CodeletType} whose name is equal to a string value.</p>

		@param  name  The name, which must be non-{@code null}, and equal to a type's {@link #getName() getName}{@code ()}.
		@param  name_varName  Descriptive name of the {@code name} parameter, for the potential error message. <i>Should</i> not be {@code null} or empty.
		@exception  IllegalArgumentException  If {@code name} is not equal to a known codelet type.
	 **/
	public static final CodeletType newTypeForTagletName(String name, String name_varName)  {
		if(CodeletType.SOURCE_CODE.isNameEqualTo(name, name_varName))  {
			return  CodeletType.SOURCE_CODE;
		}
		if(CodeletType.CONSOLE_OUT.isNameEqualTo(name, name_varName))  {
			return  CodeletType.CONSOLE_OUT;
		}
		if(CodeletType.FILE_TEXT.isNameEqualTo(name, name_varName))  {
			return  CodeletType.FILE_TEXT;
		}
		if(CodeletType.SOURCE_AND_OUT.isNameEqualTo(name, name_varName))  {
			return  CodeletType.SOURCE_AND_OUT;
		}

		throw  new IllegalArgumentException(name_varName + " (\"" + name + "\") is not CodeletType.SOURCE_CODE.getName() (\"" + CodeletType.SOURCE_CODE.getName() + "\"), CodeletType.CONSOLE_OUT.getName() (\"" + CodeletType.CONSOLE_OUT.getName() + "\"), CodeletType.FILE_TEXT.getName() (\"" + CodeletType.FILE_TEXT.getName() + "\"), or CodeletType.SOURCE_AND_OUT.getName() (\"" + CodeletType.SOURCE_AND_OUT.getName() + "\")");
	}
};
