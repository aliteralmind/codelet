
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
	import  com.github.xbn.number.LengthInRange;
	import  com.github.xbn.analyze.alter.AlterationNotMadeException;
	import  com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil;
	import  com.github.xbn.linefilter.alter.AllTextLineAlterer;
	import  com.github.xbn.linefilter.alter.ExpirableTextLineAlterList;
	import  com.github.xbn.linefilter.NewFilteredLineIteratorFor;
	import  com.github.xbn.linefilter.alter.TextLineAlterer;
	import  com.github.xbn.linefilter.FilteredLineIterator;
	import  com.github.xbn.analyze.alter.ExpirableElements;
	import  com.github.xbn.analyze.alter.MultiAlterType;
	import  com.github.xbn.array.CrashIfArray;
	import  com.github.xbn.array.NullContainer;
	import  com.github.xbn.array.NullElement;
	import  com.github.xbn.lang.CrashIfObject;
	import  com.github.xbn.lang.IllegalArgumentStateException;
	import  com.github.xbn.text.CrashIfString;
	import  com.github.xbn.util.lock.AbstractOneWayLockable;
	import  com.google.common.base.Joiner;
	import  java.util.Iterator;
	import  java.util.Objects;
	import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
	import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>The instructions returned by a Codelet Customizer, which is used by the taglet-processor to modify its output.</P>

	<A NAME="3_parts"></A><H4><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> Codelet: Customizer: <U>Three parts</U></H4>

	<P>A {@code CustomizationInstructions} is the object returned by all <A HREF="#overview">Codelet Customizer</A>s. A {@code CustomizationInstructions} is composed of three items: A <A HREF="#3_parts_filter">line filter</A>, <A HREF="#3_parts_alterer">alterer</A>, and <A HREF="#3_parts_template">template</A>.</P>

	<A NAME="3_parts_filter"></A><H4><A HREF="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> Codelet: Customizer: Three parts: <U>Part 1: Line filter</U></H4>

	<P>A line filter is used to keeping only wanted lines, such as a <A HREF="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</A>, or eliminating lines you define as unwanted. An example is to <A HREF="{@docRoot}/overview-summary.html#xmpl_hello">eliminate</A> the package declaration line and all JavaDoc multi-line comments.</P>

	<P><UL>
		<LI>Raw object: {@code com.github.xbn.linefilter.}{@link com.github.xbn.linefilter.FilteredLineIterator} (set with {@link #filter(FilteredLineIterator) filter})</LI>
		<LI>Convenience creators: {@link NewLineFilterFor}, {@link com.github.xbn.linefilter.NewFilteredLineIteratorFor}</LI>
	</UL></P>

	<A NAME="3_parts_alterer"></A><H4><A HREF="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> Codelet: Customizer: Three parts: <U>2: Alterer</U></H4>

	<P>The all-line alterer modifies each line returned (kept) by the filter. A {@linkplain com.github.xbn.analyze.validate.ValidResultFilter filter} may be applied so it does not start until needed, and {@linkplain com.github.xbn.lang.Expirable expires} when complete.</P>

	<P><UL>
		<LI>Raw objects: {@code com.github.xbn.linefilter.}{@link com.github.xbn.linefilter.AllTextLineAlterer} (set with {@link #alterer(AllTextLineAlterer) alterer}), which is an array of {@link com.github.xbn.linefilter.alter.TextLineAlterer}s (set with {@link #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) orderedAlterers})</LI>
		<LI>Convenience creators: {@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}, {@link com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum}, {@link com.github.xbn.linefilter.alter.NewTextLineAltererFor}</LI>
	</UL></P>

	<A NAME="3_parts_template"></A><H4><A HREF="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> Codelet: Customizer: Three parts: <U>3: Template</U></H4>

	<P>The context into which final output text is placed, and whose {@linkplain CodeletTemplateBase#getRendered(CodeletInstance) rendered} output is what actually replaces the taglet. Templates may be overridden for an individual taglet (by setting one into <!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#template(T)"><CODE>template</CODE></A>, in a <A HREF="#overview">custom customizer</A>), or for all taglets in a JavaDoc file or an entire package (with {@link TemplateOverrides}).</P>

	<P><UL>
		<LI>Raw objects: {@linkplain CodeletTemplateBase template} (set with <!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#template(T)"><CODE>template</CODE></A>) which, at its heart, is a {@code com.github.aliteralmind.templatefeather.FeatherTemplate}</LI>
	</UL></P>

	<A NAME="overview"></A><H2><A HREF="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: <U>Overview</U></H2>

	<P>A &quot;Codelet Customizer&quot; is a function that returns the <I><A HREF="#skip-navbar_top">instructions</A></I> for tailoring an example code's output. As stated in the <A HREF="{@docRoot}/overview-summary.html#overview_description">overview</A>, common customizations include<UL>
		<LI>Displaying only a portion of an example's source code: A <A HREF="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</A>.</LI>
		<LI><A HREF="{@docRoot}/overview-summary.html#xmpl_hello">Eliminating</A> unwanted lines, such as the package declaration line and all multi-line comments.</LI>
		<LI>Making the first appearance of a class, function, or object names into a <A HREF="{@docRoot}/overview-summary.html#xmpl_links">clickable JavaDoc link</A>.</LI>
	</UL></P>

	<P><B>Contents:</B><UL>
		<LI><B>Taglet syntax:</B> A customizer function is &quot;called&quot; by one or more codelet-taglets. <B>Examples:</B><UL>
			<LI><A HREF="#xmpl_defaults">Default function name and class location</A></LI>
			<LI>Defaults with a <A HREF="#proc_custom_post">custom postfix</A></LI>
			<LI><A HREF="#xmpl_sig">Specifying the class</A> in which the processor function exists</LI>
			<LI>Specifying <A HREF="#xmpl_params">extra parameters</A> for the customizer function</LI>
		</UL></P></LI>
		<LI><B>The customizer function:</B><UL>
			<LI>Examples: A customizer function that<UL>
				<LI><A HREF="#func_does_nothing">Does nothing</A>.</LI>
				<LI>Changes a function, constructor, class, or field name to a <A HREF="{@docRoot}/overview-summary.html#xmpl_links">clickable JavaDoc link</A>.</LI>
			</UL></LI>
			<LI><A HREF="#specifications">Specifications</A> and </LI>
			<LI>Pre-made customizers: {@link BasicCustomizers}</LI>
			<LI>{@link CustomizationInstructions}: The object returned by the customizer function. Made up of three parts: A <A HREF="#3_parts_filter">line filter</A>, <A HREF="#3_parts_alterer">alterer</A>, and <A HREF="#3_parts_template">template</A></LI>
		</UL></LI>
	</UL></P>

	<A NAME="func_does_nothing"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer function: <U>Example: A customizer that does nothing</U></H2>

	<P>A customizer function that makes (almost) no changes:</P>

{@.codelet com.github.aliteralmind.codelet.examples.DoNothingCustomizerCompact%lineRangeWithReplace(1, true, "(<SourceCodeTemplate> aCustomizerThatDoesNothing)", "$1", "FIRST", 1, true, "&#125; +//End snippet$", "&#125;", "FIRST", "^   ")}

	<P>Here is the same function, with documentation on the available {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL debugging} parameters:</P>

{@.codelet com.github.aliteralmind.codelet.examples.DoNothingCustomizer%lineRangeWithReplace(1, true, "(<SourceCodeTemplate> aCustomizerThatDoesNothing)", "$1", "FIRST", 1, true, "&#125; +//End snippet$", "&#125;", "FIRST", "^   ")}

	<P>This do-nothing customizer uses all {@linkplain #defaults(Appendable, LengthInRange, Appendable, Appendable) defaults}. It<OL>
		<LI>{@link #unfiltered(Appendable, LengthInRange) Filters no lines},</LI>
		<LI>{@linkplain com.github.aliteralmind.codelet.alter.DefaultAlterGetter Default alterers} as {@linkplain CodeletBaseConfig#DEFAULT_ALTERERS_CLASS_NAME configured}.</LI>
		<LI>Uses the {@link #defaultOrOverrideTemplate(Appendable) Default template},</LI>
	</OL></P>

	<A NAME="specifications"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: <U>Requirements</U></H2>

	<P>The customizer function has the following requirements:<UL>
		<LI>Its location (containing class) must be <A HREF="#xmpl_sig">explicitely specified</A> in the taglet, or must exist in one of the following <B><U>default classes</U></B>, which are searched in order:<OL>
			<LI>{@link BasicCustomizers},</LI>
			<LI>The {@linkplain CodeletInstance#getEnclosingClass() enclosing class}, if it is a class,</LI>
			<LI>And a class named "{@link TagletOfTypeProcessor#DEFAULT_CUSTOMIZER_CLASS_NAME zCodeletCustomizers}", if one exists in the {@linkplain CodeletInstance#getEnclosingPackage() enclosing package}.</LI>
		</OL></LI>
		<LI>It must be {@code static} and</LI>
		<LI>accessible (it is obtained with <CODE>{@link java.lang.Class Class}.{@link java.lang.Class#getDeclaredMethod(String, Class...) getDeclaredMethod}</CODE> and made accessible with <CODE>theLineProcMethod.{@link java.lang.reflect.AccessibleObject#setAccessible(boolean) setAccessible}(true)</CODE>).</LI>
		<LI>Its first parameter must be a {@link CodeletInstance CodeletInstance} and second must be a {@link CodeletType}. Both of these parameters are ommitted from all taglets.</LI>
		<LI>It may contain zero-or-more <A HREF="#xmpl_params">extra parameters</A>, whose types are either primitives or non-{@code null} strings ({@code null} is not possible), as specified by
		<BR> &nbsp; &nbsp; <CODE>com.github.xbn.util.{@link com.github.xbn.util.SimpleStringSignature SimpleStringSignature}.{@link com.github.xbn.util.SimpleStringSignature#getObjectFromString(String) getObjectFromString} </CODE>
		<BR>If there are any extra types in the customizer function signature, they must be provided in the {@linkplain TagletOfTypeProcessor#getCustomizerPortion() customizer portion} of every taglet using it. <I>The types, amount, and order of extra parameters, in both the taglet and the customizer function signature, must exactly match.</I></LI>
	</UL></P>

	<P>When taglets are used in a class (as opposed to <A HREF="http://stackoverflow.com/questions/3644726/javadoc-package-html-or-package-info-java">{@code package-info.java}</A> or your project's <A HREF="http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#sourcefiles">overview summary</A>), it is encouraged that its customizer functions be in the class, and that these functions are {@code private}. (In the Codelet and <A HREF="http://codelet.aliteralmind.com">XBN-Java</A> projects, this is not possible, as doing so would create <A HREF="http://en.wikipedia.org/wiki/Circular_dependency">circular-dependency</A> nightmare--this is the primary reason for the {@code zCodeletCustomizers} default class.)</P>

	<A NAME="xmpl_defaults"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: Taglet syntax: Example: <U>Default function name and class location</U></H2>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName%()}}</BLOCKQUOTE>

	<P>This {@code ":()"} shortcut indicates a customizer function with the standard name and class location should be used. In particular:<UL>
		<LI>Its name is {@code "getSourceCode_ExampleClassName"},</LI>
		<LI>It has no extra parameters, and it</LI>
		<LI>Must exist in one of the <A HREF="#specifications">default classes</A></LI>
	</UL></P>

	<P>{@code {@.codelet fully.qualified.examples.ExampleClassName%()}}</P>

	<P>is equivalent to both</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:getSourceCode_ExampleClassName()}}</BLOCKQUOTE>

	<P>and</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:package.of.EnclosingClass#getSourceCode_ExampleClassName()}}</BLOCKQUOTE>

	<P>with one exception: When the processor's function name is explicitely specified, <I>but its class is not</I> (which is true in the first two of the three above), the customizer must exist in one of the default classes</P>

	<A NAME="proc_custom_post"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: Taglet syntax: Example: <U>Defaults with custom postfix</U></H2>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName%_ExtraStuff()}}</BLOCKQUOTE>

	<P>Same as the <A HREF="#xmpl_defaults">default example</A>, except the underscore-first-character indicates that this is not the customizer's entire function name, rather its <I>postfix</I>.</P>

	<P>This is useful when there are multiple codelets of the same {@linkplain CodeletType type}, for the same example class (or text file).</P>

	<A NAME="xmpl_sig"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: Taglet syntax: Example: <U>Specifying the class in which the processor function exists</U></H2>

	<P>The customizer function can be in any class, which may explicitely specified:</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:fully.qualified.package.MyCodeletCustomizers#getSource_ExampleClass(true, "See line 12")}}</BLOCKQUOTE>

	<P>If using the <A HREF="#xmpl_defaults">default function name</A>, it may be omitted, although the hash ({@code '#'}) is required:</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:fully.qualified.package.MyCodeletCustomizers#(true, "See line 12")}}</BLOCKQUOTE>

	<P>Signature formatting is as specified by
	<BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</CODE>
	<BR>(Before being provided to {@code newFromStringAndDefaults}, the omitted function name is given its default value [{@code "getSource_ExampleClass"}], as described in this and the <A HREF="#proc_custom_post">previous example</A>. In all cases, {@code SimpleMethodSignature} requires a function name.)</P>

	<A NAME="xmpl_params"></A><H2><A HREF="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Customizer: Taglet syntax: Example: <U>Specifying extra processor parameters</U></H2>

	<P>The default customizer has a single {@link CodeletInstance CodeletInstance} parameter. This is specified in the taglet with either empty parentheses, or no parens at all, as demonstrated in the <A HREF="#xmpl_defaults">default example</A>.</P>

	<P>Extra parameters may be optionally specified, and must be provided <I>both</I> in the function and in any taglet that uses (calls) it. For example, this taglet</P>

<BLOCKQUOTE>{@code {@.codelet fully.qualified.examples.ExampleClassName:(true, "See line 12")}}</BLOCKQUOTE>

	<P>refers to this function:</P>

<BLOCKQUOTE>{@code getSourceCode_ExampleClassName(CodeletInstance taglet, CodeletType needed_defaultAlterType, boolean do_displayLineNums, String annotation)}</BLOCKQUOTE>

	<P>which, since there is no fully-qualified class specified after the {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR percent sign}, must be in one of the <A HREF="#specifications">default class-locations</A>.</P>

	<P>Parameter formatting is specified by
	<BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</CODE></P>

	<P>This is a "simple" signature. Only {@linkplain com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getObjectFromString(String) primitives and strings} are allowed. {@code null} is not possible.</P>

	<P>Extra parameters can also be specified with the <A HREF="#xmpl_defaults">{@code ":()"} shortcut</A>:
	<BR> &nbsp; &nbsp; {@code {@.codelet fully.qualified.examples.ExampleClassName:((byte)3, false)}}</P>


	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class CustomizationInstructions<T extends CodeletTemplateBase> extends AbstractOneWayLockable  {
	private FilteredLineIterator     filter        ;
	private String             classNameOrFilePathRestricter;
	private AllTextLineAlterer alterer       ;
	private T                  template      ;
	private boolean            wasTmplSet    ;
	private Appendable         dfltTmplDbg   ;
	private final CodeletInstance instance   ;
	private final CodeletType  defaultAltersType;
	/*
   	<P>Create a new instance for any taglet type except {@code {@.codelet.and.out}}.</P>

   	<P>Equal to
   	<BR> &nbsp; &nbsp; <CODE>{@link #CustomizationInstructions(CodeletInstance, CodeletType) this}(instance, instance.getType())</CODE></P>

   	@param  instance  May not be {@code null}.
	public CustomizationInstructions(CodeletInstance instance)  {
		this(instance, CustomizationInstructions.getType(instance));
	}
		private static final CodeletType getType(CodeletInstance instance)  {
			try  {
				return  instance.getType();
			}  catch(RuntimeException rx)  {
				throw  CrashIfObject.nullOrReturnCause(instance, "instance", null, rx);
			}
		}
	 */
	/**
   	<P>Create a new instance.</P>

   	<P>Equal to
   	<BR> &nbsp; &nbsp; <CODE>{@link #CustomizationInstructions(CodeletInstance, CodeletType) this}(instance, instance.getType())</CODE></P>

   	@param  instance  May not be {@code null}.
   	@param  needed_defaultAlterType  The type of {@linkplain com.github.aliteralmind.codelet.alter.DefaultAlterGetter default alterers} needed when using the {@linkplain #defaultOrOverrideTemplate(Appendable) default template}. May not be {@code null}. If <CODE>instance.{@link CodeletInstance#getType() getType}.{@link CodeletType#SOURCE_AND_OUT SOURCE_AND_OUT}</CODE>, then this must be either {@link CodeletType#SOURCE_CODE SOURCE_CODE} or {@link CodeletType#CONSOLE_OUT CONSOLE_OUT}. If <CODE>instance.{@link CodeletInstance#getType() getType}</CODE> is any other type, then {@code needed_defaultAlterType} must be equal to it.
	 **/
	public CustomizationInstructions(CodeletInstance instance, CodeletType needed_defaultAlterType)  {
		try  {
			if(!instance.getType().isSourceAndOut())  {
				if(instance.getType() != needed_defaultAlterType)  {
					throw  new IllegalArgumentStateException("instance.getType()." + instance.getType() + ", needed_defaultAlterType=" + needed_defaultAlterType + ".");
				}
			}  else if(!needed_defaultAlterType.isSourceCode()  &&
					!needed_defaultAlterType.isConsoleOut())  {
				throw  new IllegalArgumentStateException("instance.getType().SOURCE_AND_OUT, needed_defaultAlterType must be SOURCE_CODE or CONSOLE_OUT, but is actually " + needed_defaultAlterType + ".");
			}
		}  catch(RuntimeException rx)  {
			CrashIfObject.nnull(instance, "instance", null);
			throw  CrashIfObject.nullOrReturnCause(needed_defaultAlterType, "needed_defaultAlterType", null, rx);
		}
		alterer = null;
		filter = null;
		template = null;
		wasTmplSet = false;
		classNameOrFilePathRestricter("*");
		this.instance = instance;
		defaultAltersType = needed_defaultAlterType;
	}
	/**
		<P>Make no customizations.</P>

		<P>This calls<OL>
			<LI>{@link #unfiltered(Appendable, LengthInRange) unfiltered}{@code (dbgDest_ifNonNull)}</LI>
			<LI><CODE>{@link #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) orderedAlterers}(dbgAllAltr_ifNonNull, {@link com.github.xbn.array.NullElement NullElement}.{@link com.github.xbn.array.NullElement#BAD BAD}
			<BR> &nbsp; &nbsp; {@link com.github.xbn.analyze.alter.ExpirableElements}.{@link com.github.xbn.analyze.alter.ExpirableElements#OPTIONAL OPTIONAL}, {@link com.github.xbn.analyze.alter.MultiAlterType}.{@link com.github.xbn.analyze.alter.MultiAlterType#CUMULATIVE CUMULATIVE}
				<BR> &nbsp; &nbsp; {@link com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil}.{@link com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil#getDefaultAlterArray(CodeletType) getDefaultAlterArray}({@link #getNeededAlterArrayType() getNeededAlterArrayType}()))</CODE></LI>
			<LI>{@link #defaultOrOverrideTemplate(Appendable) defaultOrOverrideTemplate}{@code (dbgTemplate_ifNonNull)}</LI>
		</OL></P>

		@return  <I>{@code this}</I>
	 **/
	public CustomizationInstructions<T> defaults(Appendable dbgEveryLine_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull, Appendable dbgAllAltr_ifNonNull, Appendable dbgTemplate_ifNonNull)  {
		unfiltered(dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull);
		orderedAlterers(dbgAllAltr_ifNonNull, NullElement.BAD,
			ExpirableElements.OPTIONAL, MultiAlterType.CUMULATIVE,
			DefaultAlterGetterUtil.getDefaultAlterArray(getNeededAlterArrayType()));
		return  defaultOrOverrideTemplate(dbgTemplate_ifNonNull);
	}
	/*
		<P>The type of template needed, when using the default template. This is intended for {@link CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets only.</P>

		@return  A non-{@code null} type representing the kind of template needed.
		@see  #CustomizationInstructions(CodeletInstance, CodeletType) constructor
		@see  #defaults(Appendable, LengthInRange, Appendable, Appendable) defaults
	 */
	public CodeletType getNeededAlterArrayType()  {
		return  defaultAltersType;
	}
	/**
		<P>Get the line-filter.</P>

		@see  #filter(FilteredLineIterator)
	 **/
	public FilteredLineIterator getFilter()  {
		return  filter;
	}
	/**
		<P>Get the line-alterer.</P>

		@see  #alterer(AllTextLineAlterer)
	 **/
	public AllTextLineAlterer getAlterer()  {
		return  alterer;
	}
	/**
		<P>Get the template.</P>

		@return  <UL>
			<LI>A non-{@code null} template: When <I>this taglet only</I> should use a non-default (and non-{@linkplain TemplateOverrides override}) template.</LI>
			<LI>{@code null}: If {@link #wasTemplateSet() wasTemplateSet}{@code ()} is<UL>
				<LI>{@code true}: No template was set.</LI>
				<LI>{@code false}: The default (or override) template should be used.</LI>
			</UL></LI>
		</UL>
		@see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#template(T)">template</A>(T)</CODE>
		@see  #defaultOrOverrideTemplate(Appendable)
	 **/
	public T getTemplate()  {
		return  template;
	}
	public CodeletInstance getInstance()  {
		return  instance;
	}
	/**
		<P>Use the default (or override) template.</P>

		<P>This sets<OL>
			<LI>{@link #getTemplate() getTemplate}{@code ()} to {@code null}</LI>
			<LI>{@link #wasTemplateSet() wasTemplateSet}{@code ()} to {@code true}</LI>
		</OL></P>

		<P>This leaves the template object as {@code null} to avoid having to know about the {@link CodeletInstance}, which is required to determine if the default or {@linkplain TemplateOverrides override} template should be used. A {@code null} template value triggers the {@linkplain TagletOfTypeProcessor taglet processor} to get the appropriate template.</P>

		@param  dbgDest_ifNonNull  When non-{@code null}, this is the debugging destination for all gap-fills. Get with {@link #getDefaultTemplateDebug() getDefaultTemplateDebug}{@code ()}
		@return  <I>{@code this}</I>
		@exception  LockException  If {@link #build() build}{@code ()} was already called.
		@exception  IllegalStateException  If {@code wasTemplateSet()} is {@code true}.
		@see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#template(T)">template</A>(T)</CODE>
	 **/
	public CustomizationInstructions<T> defaultOrOverrideTemplate(Appendable dbgDest_ifNonNull)  {
		ciLockedOrTmplAlreadySet();
		template = null;
		wasTmplSet = true;
		dfltTmplDbg = dbgDest_ifNonNull;
		return  this;
	}
	/**
		<P>Override the template for this codelet-taglet only.</P>

		<P>This sets {@link #wasTemplateSet() wasTemplateSet}{@code ()} to {@code true}.</P>

		@param  template  May not be {@code null}. Get with {@link #getTemplate() getTemplate}{@code ()}. Note that JavaDoc is multi-threaded, and therefore this template-object must be a new object (not shared among multiple taglets). Use the {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#FeatherTemplate(FeatherTemplate, Appendable) copy constructor} or {@link com.github.aliteralmind.templatefeather.FeatherTemplate#getObjectCopy() getObjectCopy}{@code ()} to duplicate it.
		@return  <I>{@code this}</I>
		@exception  LockException  If {@link #build() build}{@code ()} was already called.
		@exception  IllegalStateException  If {@code wasTemplateSet()} is {@code true}.
		@see  #defaultOrOverrideTemplate(Appendable)
	 **/
	public CustomizationInstructions<T> template(T template)  {
		Objects.requireNonNull(template, "template");
		ciLockedOrTmplAlreadySet();
		this.template = template;
		wasTmplSet = true;
		return  this;
	}
		private void ciLockedOrTmplAlreadySet()  {
			ciLocked();
			if(wasTmplSet)  {
				throw  new IllegalStateException("Already set");
			}
		}
	/**
		<P>Display all lines.</P>

		@return  <CODE>{@link #filter(FilteredLineIterator) filter}({@link com.github.xbn.linefilter.NewFilteredLineIteratorFor NewFilteredLineIteratorFor}.{@link com.github.xbn.linefilter.NewFilteredLineIteratorFor#keepAllLinesUnchanged(Iterator, Appendable, LengthInRange) keepAllLinesUnchanged}(null, dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull))</CODE>
	 **/
	public CustomizationInstructions<T> unfiltered(Appendable dbgEveryLine_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull)  {
		return  filter(NewFilteredLineIteratorFor.keepAllLinesUnchanged(null, dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull));
	}
	/**
		<P>Keep or eliminate lines that meet some conditions. Kept lines may be {@linkplain #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) altered}.</P>

		<P>Two examples of filtering lines:<UL>
			<LI>Displaying only a range of lines--a <A HREF="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</A>.</LI>
			<LI><A HREF="{@docRoot}/overview-summary.html#xmpl_hello">Eliminating</A> the package declaration line and all JavaDoc multi-line comments</LI>
		</UL></P>

		@param  filter  May not be {@code null}. Get with {@link #getFilter() getFilter}{@code ()}.
		@return  <I>{@code this}</I>
		@see  #unfiltered(Appendable, LengthInRange)
		@exception  LockException  If {@link #build() build}{@code ()} was already called.
	 **/
	public CustomizationInstructions<T> filter(FilteredLineIterator filter)  {
		ciLocked();
		if(filter.wasAllIteratorSet())  {
			throw  new IllegalStateException("filter.wasAllIteratorSet()=true");
		}
		this.filter = filter;
		return  this;
	}
	/**
		<P>Set an ordered series of line-alterers.</P>

		@param  alterers  May not be {@code null} or empty and, if <CODE>null_element.{@link com.github.xbn.array.NullElement#isBad() isBad}()</CODE> is {@code true}, no elements may be {@code null}. Elements <I>should</I> not be duplicate.
		@return  <CODE>{@link #alterer(AllTextLineAlterer) alterer}(new {@link com.github.xbn.linefilter.AllTextLineAlterer#AllTextLineAlterer(TextLineAlterer[], ExpirableElements, MultiAlterType, Appendable) AllTextLineAlterer}(alterers, xprbl_elements, multi_type, dbgDest_ifNonNull))</CODE>
	 **/
	public CustomizationInstructions<T> orderedAlterers(Appendable dbgDest_ifNonNull, NullElement null_element, ExpirableElements xprbl_elements, MultiAlterType multi_type, TextLineAlterer... alterers)  {
		try  {
			if(null_element.isOk())  {
				alterers = ExpirableTextLineAlterList.
					getTextLineAltererArrayFromOrderedElementsIfNonNull(null_element, "alterers", alterers);
			}
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(null_element, "null_element", null, rx);
		}
		CrashIfArray.lengthLessThan(alterers, "alterers", NullContainer.BAD, 1, null);
		return  alterer(new AllTextLineAlterer(alterers, xprbl_elements, multi_type, dbgDest_ifNonNull));
	}
	/**
		<P>Set the line-alterer.</P>

		@param  all_lineAlterer  May not be {@code null}. Get with {@link #getAlterer() getAlterer}{@code ()}.
		@return  <I>{@code this}</I>
		@exception  LockException  If {@link #build() build}{@code ()} was already called.
		@see  #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...)
	 **/
	public CustomizationInstructions<T> alterer(AllTextLineAlterer all_lineAlterer)  {
		ciLocked();
		Objects.requireNonNull(all_lineAlterer, "all_lineAlterer");
		alterer = all_lineAlterer;
		return  this;
	}
	/**
		<P>Wildcard search-term to restrict the classes or files that may utilize this customizer.</P>

		@param  whitelist_searchTerm  Wildcard search term to match the fully-qualified class name of the example code, or path of the text file that is allowed to use this customizer. Class name examples:<UL>
			<LI>{@code "com.github.mylibrary.examples.AGoodExample"}</LI>
			<LI>{@code "com.github.mylibrary.examples.A*Example"}</LI>
			<LI>{@code "*.examples.A*Example"}</LI>
		</UL>Text file examples:<UL>
			<LI>{@code "com/github/mylibrary/examples/doc-files/AGoodExample_input.txt"}</LI>
			<LI>{@code "com/github/mylibrary/examples/doc-files/*_input.txt"}</LI>
			<LI>{@code "*_input.txt"}</LI>
		</UL>May not be {@code null} or empty, and <I>should</I> be a valid wildcard term. Get with {@link #getClassNameOrFilePathRestricter() getClassNameOrFilePathRestricter}{@code ()}.
		@return  <I>{@code this}</I>
		@see  org.apache.commons.io.FilenameUtils#wildcardMatch(String, String) FilenameUtils#wildcardMatch
		@see  TagletOfTypeProcessor#crashIfClassOrFileCannotUseCustomizer(CustomizationInstructions) TagletOfTypeProcessor#crashIfClassOrFileCannotUseCustomizer
	 **/
	public CustomizationInstructions<T> classNameOrFilePathRestricter(String whitelist_searchTerm)  {
		CrashIfString.nullEmpty(whitelist_searchTerm, "whitelist_searchTerm", null);
		classNameOrFilePathRestricter = whitelist_searchTerm;
		return  this;
	}
	/**
		<P>Wildcard search-term to restrict the classes that may utilize this customizer.</P>

		@see  #classNameOrFilePathRestricter(String)
	 **/
	public String getClassNameOrFilePathRestricter()  {
		return  classNameOrFilePathRestricter;
	}
	/**
		<P>Was the template set?.</P>

		@see  #getTemplate()
		@see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#template(T)">template</A>(T)</CODE>
		@see  #defaultOrOverrideTemplate(Appendable)
	 **/
	public boolean wasTemplateSet()  {
		return  wasTmplSet;
	}
	/**
		<P>When using the default template only, this is its debug destination.</P>

		@see  #getTemplate()
		@see  #defaultOrOverrideTemplate(Appendable)
	 **/
	public Appendable getDefaultTemplateDebug()  {
		return  dfltTmplDbg;
	}
	/**
		<P>Given all configured customization instructions, transform the raw output (source-code, console-output, or file-text) into its fully-processed form, ready for insertion into the template.</P>

		<P>This logs all alterers that do not make an alteration.</P>

		@exception  AlterationNotMadeException  If at least one alteration is not made, and it is {@linkplain CodeletBaseConfig#ALTERATION_NOT_MADE_CRASH configured} that a crash should occur (in addition to the warning).
	 **/
	public String getCustomizedBody(CodeletInstance instance, Iterator<String> raw_lineItr)  {
		getFilter().setAllIterator(raw_lineItr);
		String body = getAlterer().getAlteredFromLineObjects(1, getFilter(), LINE_SEP);

		if(!getAlterer().isComplete())  {
			String msg = getAlterer().appendIncompleteInfo((new StringBuilder())).toString();
			if(!isDebugOn(instance, "zzTagletProcessor.codeletfound"))  {
				msg = "(" + instance + ") " + msg;
			}
			debuglnAndToConsole(instance, "Codelet warning: " + msg);
			if(doCrashIfAlterationNotMade())  {
				throw  new AlterationNotMadeException("(CodeletBaseConfig.doCrashIfAlterationNotMade() is true): " + msg);
			}
		}

		return  body;
	}
	/**
		<P>Declare that this object is ready for use and should be locked.</P>

		@return  <I>{@code this}</I>
		@exception  IllegalStateException  If the {@linkplain #getFilter() filter} or {@linkplain #getAlterer() alterer} are {@code null}, or {@link #wasTemplateSet() wasTemplateSet}{@code ()} is {@code false}.
	 **/
	public CustomizationInstructions<T> build()  {
		crashIfNotReady();
		super.lock();
		return  this;
	}
		private void crashIfNotReady()  {
			if(alterer != null  &&  filter != null  &&
					wasTmplSet)  {
				return;
			}

		//At least one is unset
		String sFltr = ((getFilter() != null) ? null : "filter(FilteredLineIterator)");
		String sAlter = ((getAlterer() != null) ? null : "alterer(AllTextLineAlterer)");
		String sTmpl = (wasTmplSet ? null : "template(T)");

/*
String s = Joiner.on(", ").skipNulls().join(null, null);
System.out.println("Joiner.on(', ').skipNulls().join(null, null, null) == null: " + (s == null));
System.out.println("Joiner.on(', ').skipNulls().join(null, null, null).length(): " + s.length());

	Output:
		... == null: false
		....length(): 0
 */
		throw  new IllegalStateException("Must set: " +
			Joiner.on(", ").skipNulls().join(sFltr, sAlter, sTmpl));
	}
}
