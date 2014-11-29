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
package  com.github.aliteralmind.codelet.alter;
	import  com.github.xbn.analyze.alter.AlterationRequired;
	import  com.github.aliteralmind.codelet.CodeletInstance;
	import  com.github.xbn.linefilter.AdaptRegexReplacerTo;
	import  com.github.xbn.linefilter.alter.TextLineAlterAdapter;
	import  com.github.aliteralmind.codelet.simplesig.AllSimpleParamSignatures;
	import  com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm;
	import  com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm;
	import  com.github.xbn.analyze.validate.NewValidResultFilterFor;
	import  com.github.xbn.analyze.validate.ValidResultFilter;
	import  com.github.xbn.lang.CrashIfObject;
	import  com.github.xbn.lang.reflect.Declared;
	import  com.github.xbn.lang.reflect.ReflectRtxUtil;
	import  com.github.xbn.regexutil.RegexReplacer;
	import  com.github.xbn.regexutil.StringReplacer;
	import  com.github.xbn.text.CrashIfString;
	import  java.lang.reflect.Constructor;
	import  java.lang.reflect.Field;
	import  java.lang.reflect.Method;
	import  java.util.HashMap;
	import  java.util.Map;
	import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <p>Convenience functions for creating new line alterers that replace a <i>single occurance</i> of a function (or class, constructor, or field) name with a clickable JavaDoc link.</p>

   <h4>Known issues</h4>

   <p>While it is verified that the link's target exists, it is not known whether the target is made viewable by JavaDoc. For example, if you link to a protected class, but configure JavaDoc to only display <a href="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#public">public classes</a>, the link will be created, but clicking on it will result in going to the class itself (assuming the class is also viewable).</p>

   @see  com.github.xbn.linefilter.alter.NewTextLineAltererFor
	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class NewJDLinkForWordOccuranceNum  {
	/**
		<p>Replaces a single occurance of a constructor name with a JavaDoc link.</p>

		@param  lineOccurance_num  Which &quot;line&quot; occurance should be linked? This is the <i>n-th line</i> in which the function name is found in the source code. If it exists in three lines (regardless how many occurances exist within those lines), and you want the second to have the link, set this to two. When there are multiple occurances of a constructor call in a line, the first is always the one linked. Must be one or greater.
		@return  <code>{@link com.github.xbn.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.xbn.linefilter.AdaptRegexReplacerTo#lineReplacer(AlterationRequired, RegexReplacer, ValidResultFilter) lineReplacer}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES}, rr, filterAllBut)</code>
		<br/>Where<ul>
			<li>{@code rr} is a
			<br/> &nbsp; &nbsp; <code>{@link NewJavaDocLinkReplacerFor NewJavaDocLinkReplacerFor}.{@link NewJavaDocLinkReplacerFor#constructor(CodeletInstance, Constructor, Appendable) constructor}(instance, target, dbgRplcr_ifNonNull)</code></li>
			<li>{@code target} is
			<br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm}.
			<br/> &nbsp; &nbsp; {@link com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm#getConstructorFromAllSigsAndSearchTerm(AllSimpleParamSignatures, String, Appendable, Appendable) getConstructorFromAllSigsAndSearchTerm}(
			<br/> &nbsp; &nbsp;  &nbsp; &nbsp; {@link #getAllParamSigsForLinkTarget(Class) getAllParamSigsForLinkTarget}(target_class),
			<br/> &nbsp; &nbsp;  &nbsp; &nbsp; param_shortcut, dbgSearchTerm_ifNonNull, dbgSearchTermDoesMatch_ifNonNull)</code></li>
			<li>{@code filterAllBut} is a &quot;{@linkplain com.github.xbn.analyze.validate.ValidResultFilter result filter}&quot; that only accepts a {@linkplain com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) single occurance}.</li>
		</ul>
	 **/
	public static final TextLineAlterAdapter<StringReplacer> constructor(CodeletInstance instance, int lineOccurance_num, Class<?> target_class, String param_shortcut, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull, Appendable dbgSearchTerm_ifNonNull, Appendable dbgSearchTermDoesMatch_ifNonNull)  {
		Constructor<?> target = ConstructorParamSearchTerm.
			getConstructorFromAllSigsAndSearchTerm(
				getAllParamSigsForLinkTarget(target_class),
				param_shortcut, dbgSearchTerm_ifNonNull, dbgSearchTermDoesMatch_ifNonNull);
		RegexReplacer rr = NewJavaDocLinkReplacerFor.constructor(instance, target, dbgRplcr_ifNonNull);
		return  newReplacerAlterer(rr, lineOccurance_num, "lineOccurance_num", dbgResultFilter_ifNonNull, target_class, param_shortcut);
	}
	/**
		<p>Create a constructor link replacer with named debuggers.</p>

		<p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #constructor(CodeletInstance, int, Class, String, Appendable, Appendable, Appendable, Appendable) constructor} parameters:<ul>
			<li><code><i>[debug_prefix]</i>.link.<i>[dbgPreCnstrNm_ifNonNull]</i></code>: {@code dbgRplcr_ifNonNull}<ul>
				<li>{@code .validfilter}: {@code dbgResultFilter_ifNonNull}</li>
				<li>{@code .searchterm}: {@code dbgSearchTerm_ifNonNull}<ul>
					<li>{@code .doesMatch}: {@code dbgSearchTermDoesMatch_ifNonNull}</LI
				</ul></li>
			</ul></li>
		</ul>Both of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.link.DBGPRECNSTRNM_IFNONNULL.=-1
PREFIX.link.DBGPRECNSTRNM_IFNONNULL.validfilter=-1
PREFIX.link.DBGPRECNSTRNM_IFNONNULL.searchterm=-1
PREFIX.link.DBGPRECNSTRNM_IFNONNULL.searchterm.doesMatch=-1</pre></blockquote>

		@param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
		@param  debug_prefix  Prepended to all named debuggers. May not be {@code null} or empty.
		@param  dbgPreCnstrNm_ifNonNull  If non-{@code null}, this is the name of the class used in the debug-level name. If {@code null}, {@code "constructor"} is used.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> constructor(CodeletInstance instance, String debug_prefix, String dbgPreCnstrNm_ifNonNull, int lineOccurance_num, Class<?> target_class, String param_shortcut)  {
		CrashIfString.nullEmpty(debug_prefix, "debug_prefix", null);
		debug_prefix += ".link." + ((dbgPreCnstrNm_ifNonNull != null)
			?  dbgPreCnstrNm_ifNonNull : "constructor");
		Appendable dbgLinkRplcr = getDebugApblIfOn(instance,
			debug_prefix);
		Appendable dbgLinkValidFilter = getDebugApblIfOn(instance,
			debug_prefix + ".validfilter");
		Appendable dbgLinkSearchTerm = getDebugApblIfOn(instance,
			debug_prefix + ".searchterm");
		Appendable dbgLinkSearchTermDM = getDebugApblIfOn(instance,
			debug_prefix + ".searchterm.doesmatch");

		return  constructor(instance, lineOccurance_num,
			target_class, param_shortcut,
				dbgLinkRplcr,
				dbgLinkValidFilter,
				dbgLinkSearchTerm,
				dbgLinkSearchTermDM);
	}
		private static final TextLineAlterAdapter<StringReplacer> newReplacerAlterer(RegexReplacer replacer, int line_occuranceNum, String occurNum_varName, Appendable dbgResultFilter_ifNonNull, Class<?> target_class, String shortcut)  {
			try  {
				return  AdaptRegexReplacerTo.lineReplacer(AlterationRequired.YES,
					replacer, NewValidResultFilterFor.exactly(
						line_occuranceNum, occurNum_varName, dbgResultFilter_ifNonNull));
			}  catch(RuntimeException rx)  {
				throw  new RuntimeException("Attempting to create JavaDoc link replacer for target_class=" + target_class.getName() + ", shortcut/field-name=" + shortcut, rx);
			}
		}
	/**
		<p>Replaces a single occurance of a function name with a JavaDoc link.</p>

		@param  line_occuranceNum  Which occurance should be linked? This is the <i>n-th <b>line</b></i> in which the function name is found in the source code. If it exists in three lines (regardless how many occurances exist within those lines), and you want the second to have the link, set this to two. When there are multiple occurances of a function call in that line, the first is always the one linked. Must be one or greater.
		@return  <code>{@link com.github.xbn.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.xbn.linefilter.AdaptRegexReplacerTo#lineReplacer(AlterationRequired, RegexReplacer, ValidResultFilter) lineReplacer}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES}, rr, filterAllBut)</code>
		<br/>Where<ul>
			<li>{@code rr} is a
			<br/> &nbsp; &nbsp; <code>{@link NewJavaDocLinkReplacerFor NewJavaDocLinkReplacerFor}.{@link NewJavaDocLinkReplacerFor#method(CodeletInstance, Method, Appendable) method}(instance, target, dbgRplcr_ifNonNull)</code></li>
			<li>{@code target} is
			<br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm}.
			<br/> &nbsp; &nbsp; {@link com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm#getMethodFromAllSigsAndSearchTerm(AllSimpleParamSignatures, String, Appendable, Appendable) getConstructorFromAllSigsAndSearchTerm}(
			<br/> &nbsp; &nbsp;  &nbsp; &nbsp; {@link #getAllParamSigsForLinkTarget(Class) getAllParamSigsForLinkTarget}(target_class),
			<br/> &nbsp; &nbsp;  &nbsp; &nbsp; name_paramShortcut, dbgSearchTerm_ifNonNull, dbgSearchTermDoesMatch_ifNonNull)</code></li>
			<li>{@code filterAllBut} is a &quot;{@linkplain com.github.xbn.analyze.validate.ValidResultFilter result filter}&quot; that only accepts a {@linkplain com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) single occurance}.</li>
		</ul>
	 **/
	public static final TextLineAlterAdapter<StringReplacer> method(CodeletInstance instance, int line_occuranceNum, Class<?> target_class, String name_paramShortcut, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull, Appendable dbgSearchTerm_ifNonNull, Appendable dbgSearchTermDoesMatch_ifNonNull)  {
		Method target = MethodSigSearchTerm.getMethodFromAllSigsAndSearchTerm(
			getAllParamSigsForLinkTarget(target_class), name_paramShortcut, dbgSearchTerm_ifNonNull, dbgSearchTermDoesMatch_ifNonNull);
		return  method(instance, line_occuranceNum, target, dbgRplcr_ifNonNull, dbgResultFilter_ifNonNull);
	}
		private static final TextLineAlterAdapter<StringReplacer> method(CodeletInstance instance, int line_occuranceNum, Method target, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull)  {
			RegexReplacer rr = NewJavaDocLinkReplacerFor.method(instance, target, dbgRplcr_ifNonNull);
			return  newReplacerAlterer(rr, line_occuranceNum, "line_occuranceNum", dbgResultFilter_ifNonNull, target.getDeclaringClass(), target.getName());
		}
	/**
		<p>Create a function link replacer with named debuggers.</p>

		<p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #method(CodeletInstance, int, Class, String, Appendable, Appendable, Appendable, Appendable) method} parameters:<ul>
			<li><code><i>[debug_prefix]</i>.link.<i>[dbgPreMethodNm_ifNonNull]</i></code>: {@code dbgRplcr_ifNonNull}<ul>
				<li>{@code .validfilter}: {@code dbgResultFilter_ifNonNull}</li>
				<li>{@code .searchterm}: {@code dbgSearchTerm_ifNonNull}<ul>
					<li>{@code .doesMatch}: {@code dbgSearchTermDoesMatch_ifNonNull}</LI
				</ul></li>
			</ul></li>
		</ul>Both of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.link.DBGPREMETHODNM_IFNONNULL.=-1
PREFIX.link.DBGPREMETHODNM_IFNONNULL.validfilter=-1
PREFIX.link.DBGPREMETHODNM_IFNONNULL.searchterm=-1
PREFIX.link.DBGPREMETHODNM_IFNONNULL.searchterm.doesMatch=-1</pre></blockquote>

		@param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
		@param  debug_prefix  Prepended to all named debuggers. May not be {@code null} or empty.
		@param  dbgPreMethodNm_ifNonNull  If non-{@code null}, this is the name of the method (with potentially a class-dot prefix) used in the debug-level name. If {@code null}, the method's name is used.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> method(CodeletInstance instance, String debug_prefix, String dbgPreMethodNm_ifNonNull, int line_occuranceNum, Class<?> target_class, String name_paramShortcut)  {
		CrashIfString.nullEmpty(debug_prefix, "debug_prefix", null);
		debug_prefix += ".link.";

		MethodSigSearchTerm searchTerm = new MethodSigSearchTerm(name_paramShortcut, null, null);

		if(dbgPreMethodNm_ifNonNull == null)  {
			debug_prefix += searchTerm.getMethodName();
		}

		Appendable dbgLinkSearchTerm = getDebugApblIfOn(instance,
			debug_prefix + ".searchterm");
		Appendable dbgLinkSearchTermDM = getDebugApblIfOn(instance,
			debug_prefix + ".searchterm.doesmatch");

		searchTerm.setDebug(dbgLinkSearchTerm, dbgLinkSearchTermDM);

		Method target = searchTerm.getOnlyMatch(
				getAllParamSigsForLinkTarget(target_class)).
			getMethod();

		Appendable dbgLinkRplcr = getDebugApblIfOn(instance,
			debug_prefix);
		Appendable dbgLinkValidFilter = getDebugApblIfOn(instance,
			debug_prefix + ".validfilter");

		return  method(instance, line_occuranceNum, target,
			dbgLinkRplcr,
			dbgLinkValidFilter);
	}
	/**
		<p>Replaces a single occurance of a field name (an object contained in another object) with a JavaDoc link.</p>

		@param  line_occuranceNum  Which occurance should be linked?  This is the <i>n-th <b>line</b></i> in which the obect name is found in the source code. If it exists in three lines (regardless how many occurances exist within those lines), and you want the second to have the link, set this to two. When there are multiple occurances of a field name in a line, the first is always the one linked. Must be one or greater.
		@return  <code>{@link com.github.xbn.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.xbn.linefilter.AdaptRegexReplacerTo#lineReplacer(AlterationRequired, RegexReplacer, ValidResultFilter) lineReplacer}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES}, rr, filterAllBut)</code>
		<br/>Where {@code rr} is a
		<br/> &nbsp; &nbsp; <code>{@link NewJavaDocLinkReplacerFor NewJavaDocLinkReplacerFor}.{@link NewJavaDocLinkReplacerFor#field(CodeletInstance, Field, Appendable) field}(instance, target, dbgRplcr_ifNonNull)</code>
		<br/>and {@code filterAllBut} is a &quot;{@linkplain com.github.xbn.analyze.validate.ValidResultFilter result filter}&quot; that only accepts a {@linkplain com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) single occurance}.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> field(CodeletInstance instance, int line_occuranceNum, Class<?> containing_class, String field_name, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull)  {
		Field target = ReflectRtxUtil.getField(containing_class, field_name, Declared.YES, null);
		return  field(instance, line_occuranceNum, target, dbgRplcr_ifNonNull, dbgResultFilter_ifNonNull);
	}
	public static final TextLineAlterAdapter<StringReplacer> field(CodeletInstance instance, int line_occuranceNum, Field target, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull)  {
		RegexReplacer rr = NewJavaDocLinkReplacerFor.field(instance, target,
			dbgRplcr_ifNonNull);
		return  newReplacerAlterer(rr, line_occuranceNum, "line_occuranceNum",
			dbgResultFilter_ifNonNull, target.getDeclaringClass(),
			target.getName());
	}
	/**
		<p>Create a field link replacer with named debuggers.</p>

		<p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #field(CodeletInstance, int, Class, String, Appendable, Appendable) field} parameters:<ul>
			<li><code><i>[debug_prefix]</i>.link.<i>[dbgPreFieldNm_ifNonNull]</i></code>: {@code dbgRplcr_ifNonNull}<ul>
				<li>{@code .validfilter}: {@code dbgResultFilter_ifNonNull}</li>
			</ul></li>
		</ul>Both of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.link.DBGPREFIELDNM_IFNONNULL.=-1
PREFIX.link.DBGPREFIELDNM_IFNONNULL.validfilter=-1</pre></blockquote>

		@param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
		@param  debug_prefix  Prepended to all named debuggers. May not be {@code null} or empty.
		@param  dbgPreFieldNm_ifNonNull  If non-{@code null}, this is the name of the field (with potentially a class-dot prefix) used in the debug-level name. If {@code null}, the field's name is used.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> field(CodeletInstance instance, String debug_prefix, String dbgPreFieldNm_ifNonNull, int line_occuranceNum, Class<?> containing_class, String field_name)  {
		CrashIfString.nullEmpty(debug_prefix, "debug_prefix", null);
		debug_prefix += ".link.";

		Field target = ReflectRtxUtil.getField(containing_class, field_name, Declared.YES, null);

		if(dbgPreFieldNm_ifNonNull == null)  {
			debug_prefix += target.getName();
		}

		Appendable dbgLinkRplcr = getDebugApblIfOn(instance,
			debug_prefix);
		Appendable dbgLinkValidFilter = getDebugApblIfOn(instance,
			debug_prefix + ".validfilter");

		return  field(instance, line_occuranceNum, target,
			dbgLinkRplcr,
			dbgLinkValidFilter);
	}
	/**
		<p>Replaces a single occurance of a class name with a JavaDoc link.</p>

		@param  line_occuranceNum  Which occurance should be linked?  This is the <i>n-th <b>line</b></i> in which the class name is found in the source code. If it exists in three lines (regardless how many occurances exist within those lines), and you want the second to have the link, set this to two. When there are multiple occurances of a class name in a line, the first is always the one linked. Must be one or greater.
		@return  <code>{@link com.github.xbn.linefilter.AdaptRegexReplacerTo AdaptRegexReplacerTo}.{@link com.github.xbn.linefilter.AdaptRegexReplacerTo#lineReplacer(AlterationRequired, RegexReplacer, ValidResultFilter) lineReplacer}({@link com.github.xbn.analyze.alter.AlterationRequired}.{@link com.github.xbn.analyze.alter.AlterationRequired#YES YES}, rr, filterAllBut)</code>
		<br/>Where {@code rr} is a
		<br/> &nbsp; &nbsp; <code>{@link NewJavaDocLinkReplacerFor}.{@link NewJavaDocLinkReplacerFor#cclass(CodeletInstance, Class, Appendable) cclass}(urlToExampleClassPkgWSlash_fromTagletFile, class_name, dbgRplcr_ifNonNull)</code>
		<br/>and {@code filterAllBut} is a &quot;{@linkplain com.github.xbn.analyze.validate.ValidResultFilter result filter}&quot; that only accepts a {@linkplain com.github.xbn.analyze.validate.NewValidResultFilterFor#exactly(int, String, Appendable) single occurance}.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> cclass(CodeletInstance instance, int line_occuranceNum, Class<?> target, Appendable dbgRplcr_ifNonNull, Appendable dbgResultFilter_ifNonNull)  {
		RegexReplacer rr = NewJavaDocLinkReplacerFor.cclass(instance, target, dbgRplcr_ifNonNull);
		return  newReplacerAlterer(rr, line_occuranceNum, "line_occuranceNum", dbgResultFilter_ifNonNull, target, null);
	}
	/**
		<p>Create a class link replacer with named debuggers.</p>

		<p>{@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE Named debuggers} provided to the following {@link #cclass(CodeletInstance, int, Class, Appendable, Appendable) cclass} parameters:<ul>
			<li><code><i>[debug_prefix]</i>.link.<i>[dbgPreClassNm_ifNonNull]</i></code>: {@code dbgRplcr_ifNonNull}<ul>
				<li>{@code .validfilter}: {@code dbgResultFilter_ifNonNull}</li>
			</ul></li>
		</ul>Both of which must be added to the named-debug-level configuration file:</p>

<blockquote><pre>PREFIX.link.DBGPRECLASSNM_IFNONNULL.=-1
PREFIX.link.DBGPRECLASSNM_IFNONNULL.validfilter=-1</pre></blockquote>

		@param  instance  For determining the current {@linkplain com.github.aliteralmind.codelet.CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) debugging level}.
		@param  debug_prefix  Prepended to all named debuggers. May not be {@code null} or empty.
		@param  dbgPreClassNm_ifNonNull  If non-{@code null}, this is the name of the class used in the debug-level name. If {@code null}, <code>target.{@link java.lang.Class#getSimpleName() getSimpleName}()</code> is used.
	 **/
	public static final TextLineAlterAdapter<StringReplacer> cclass(CodeletInstance instance, String debug_prefix, String dbgPreClassNm_ifNonNull, int line_occuranceNum, Class<?> target)  {
		CrashIfString.nullEmpty(debug_prefix, "debug_prefix", null);
		debug_prefix += ".link.";
		try  {
			debug_prefix += ((dbgPreClassNm_ifNonNull != null) ? dbgPreClassNm_ifNonNull
				:  target.getSimpleName());
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(dbgPreClassNm_ifNonNull, "dbgPreClassNm_ifNonNull", null, rx);
		}
		Appendable dbgLinkRplcr = getDebugApblIfOn(instance,
			debug_prefix);
		Appendable dbgLinkValidFilter = getDebugApblIfOn(instance,
			debug_prefix + ".validfilter");

		return  cclass(instance, line_occuranceNum, target,
			dbgLinkRplcr,
			dbgLinkValidFilter);
	}
		private static final ValidResultFilter exactly(int line_occuranceNum, Appendable dbgResultFilter_ifNonNull)  {
			return  NewValidResultFilterFor.
				exactly(line_occuranceNum, "line_occuranceNum", dbgResultFilter_ifNonNull);
		}
	/**
		<p>Get the object that translates function and constructor shortcut signatures to the method or constructor object they represent. These objects are only created once.</p>

		<p>The map holding these class objects is given an initial capacity as in the configuration variable &quot;{@link com.github.aliteralmind.codelet.CodeletBaseConfig#UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY unique_jd_class_target_init_capacity}&quot;.</p>

		@param  target  May not be {@code null}.
	 **/
	public static final AllSimpleParamSignatures getAllParamSigsForLinkTarget(Class<?> target)  {
		if(!classAllSigsMap.containsKey(target))  {
			synchronized(classAllSigsMap)  {
				if(!classAllSigsMap.containsKey(target))  {
					try  {
						classAllSigsMap.put(target, new AllSimpleParamSignatures(target, Declared.YES));
					}  catch(RuntimeException rx)  {
						throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
					}
				}
			}
		}
		return  classAllSigsMap.get(target);
	}
	private static Map<Class<?>,AllSimpleParamSignatures> classAllSigsMap = new HashMap<Class<?>,AllSimpleParamSignatures>(getTargetClassMapInitCapacity());
   private NewJDLinkForWordOccuranceNum()  {
		throw  new IllegalStateException("Do not instantiate");
	}
}
