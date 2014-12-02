
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
   <p>The instructions returned by a Codelet Customizer, which is used by the taglet-processor to modify its output.</p>

   <A NAME="3_parts"></a><h4><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> Codelet: Customizer: <u>Three parts</u></h4>

   <p>A {@code CustomizationInstructions} is the object returned by all <a href="#overview">Codelet Customizer</a>s. A {@code CustomizationInstructions} is composed of three items: A <a href="#3_parts_filter">line filter</a>, <a href="#3_parts_alterer">alterer</a>, and <a href="#3_parts_template">template</a>.</p>

   <A NAME="3_parts_filter"></a><h4><a href="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> Codelet: Customizer: Three parts: <u>Part 1: Line filter</u></h4>

   <p>A line filter is used to keeping only wanted lines, such as a <a href="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</a>, or eliminating lines you define as unwanted. An example is to <a href="{@docRoot}/overview-summary.html#xmpl_hello">eliminate</a> the package declaration line and all JavaDoc multi-line comments.</p>

   <p><ul>
      <li>Raw object: {@code com.github.xbn.linefilter.}{@link com.github.xbn.linefilter.FilteredLineIterator} (set with {@link #filter(FilteredLineIterator) filter})</li>
      <li>Convenience creators: {@link NewLineFilterFor}, {@link com.github.xbn.linefilter.NewFilteredLineIteratorFor}</li>
   </ul></p>

   <A NAME="3_parts_alterer"></a><h4><a href="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> Codelet: Customizer: Three parts: <u>2: Alterer</u></h4>

   <p>The all-line alterer modifies each line returned (kept) by the filter. A {@linkplain com.github.xbn.analyze.validate.ValidResultFilter filter} may be applied so it does not start until needed, and {@linkplain com.github.xbn.lang.Expirable expires} when complete.</p>

   <p><ul>
      <li>Raw objects: {@code com.github.xbn.linefilter.}{@link com.github.xbn.linefilter.alter.AllTextLineAlterer} (set with {@link #alterer(AllTextLineAlterer) alterer}), which is an array of {@link com.github.xbn.linefilter.alter.TextLineAlterer}s (set with {@link #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) orderedAlterers})</li>
      <li>Convenience creators: {@link com.github.aliteralmind.codelet.alter.NewLineAltererFor}, {@link com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum}, {@link com.github.xbn.linefilter.alter.NewTextLineAltererFor}</li>
   </ul></p>

   <A NAME="3_parts_template"></a><h4><a href="#3_parts"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> Codelet: Customizer: Three parts: <u>3: Template</u></h4>

   <p>The context into which final output text is placed, and whose {@linkplain CodeletTemplateBase#getRendered(CodeletInstance) rendered} output is what actually replaces the taglet. Templates may be overridden for an individual taglet (by setting one into <!-- GENERIC PARAMETERS FAIL IN @link --><a href="#template(T)"><code>template</code></a>, in a <a href="#overview">custom customizer</a>), or for all taglets in a JavaDoc file or an entire package (with {@link TemplateOverrides}).</p>

   <p><ul>
      <li>Raw objects: {@linkplain CodeletTemplateBase template} (set with <!-- GENERIC PARAMETERS FAIL IN @link --><a href="#template(T)"><code>template</code></a>) which, at its heart, is a {@code com.github.aliteralmind.templatefeather.FeatherTemplate}</li>
   </ul></p>

   <A NAME="overview"></a><h2><a href="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: <u>Overview</u></h2>

   <p>A &quot;Codelet Customizer&quot; is a function that returns the <i><a href="#skip-navbar_top">instructions</a></i> for tailoring an example code's output. As stated in the <a href="{@docRoot}/overview-summary.html#overview_description">overview</a>, common customizations include<ul>
      <li>Displaying only a portion of an example's source code: A <a href="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</a>.</li>
      <li><a href="{@docRoot}/overview-summary.html#xmpl_hello">Eliminating</a> unwanted lines, such as the package declaration line and all multi-line comments.</li>
      <li>Making the first appearance of a class, function, or object names into a <a href="{@docRoot}/overview-summary.html#xmpl_links">clickable JavaDoc link</a>.</li>
   </ul></p>

   <p><b>Contents:</b><ul>
      <li><b>Taglet syntax:</b> A customizer function is &quot;called&quot; by one or more codelet-taglets. <b>Examples:</b><ul>
         <li><a href="#xmpl_defaults">Default function name and class location</a></li>
         <li>Defaults with a <a href="#proc_custom_post">custom postfix</a></li>
         <li><a href="#xmpl_sig">Specifying the class</a> in which the processor function exists</li>
         <li>Specifying <a href="#xmpl_params">extra parameters</a> for the customizer function</li>
      </ul></p></li>
      <li><b>The customizer function:</b><ul>
         <li>Examples: A customizer function that<ul>
            <li><a href="#func_does_nothing">Does nothing</a>.</li>
            <li>Changes a function, constructor, class, or field name to a <a href="{@docRoot}/overview-summary.html#xmpl_links">clickable JavaDoc link</a>.</li>
         </ul></li>
         <li><a href="#specifications">Specifications</a> and </li>
         <li>Pre-made customizers: {@link BasicCustomizers}</li>
         <li>{@link CustomizationInstructions}: The object returned by the customizer function. Made up of three parts: A <a href="#3_parts_filter">line filter</a>, <a href="#3_parts_alterer">alterer</a>, and <a href="#3_parts_template">template</a></li>
      </ul></li>
   </ul></p>

   <A NAME="func_does_nothing"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer function: <u>Example: A customizer that does nothing</u></h2>

   <p>A customizer function that makes (almost) no changes:</p>

{@.codelet com.github.aliteralmind.codelet.examples.DoNothingCustomizerCompact%lineRangeWithReplace(1, true, "(<SourceCodeTemplate> aCustomizerThatDoesNothing)", "$1", "FIRST", 1, true, "&#125; +//End snippet$", "&#125;", "FIRST", "^   ")}

   <p>Here is the same function, with documentation on the available {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL debugging} parameters:</p>

{@.codelet com.github.aliteralmind.codelet.examples.DoNothingCustomizer%lineRangeWithReplace(1, true, "(<SourceCodeTemplate> aCustomizerThatDoesNothing)", "$1", "FIRST", 1, true, "&#125; +//End snippet$", "&#125;", "FIRST", "^   ")}

   <p>This do-nothing customizer uses all {@linkplain #defaults(Appendable, LengthInRange, Appendable, Appendable) defaults}. It<ol>
      <li>{@link #unfiltered(Appendable, LengthInRange) Filters no lines},</li>
      <li>{@linkplain com.github.aliteralmind.codelet.alter.DefaultAlterGetter Default alterers} as {@linkplain CodeletBaseConfig#DEFAULT_ALTERERS_CLASS_NAME configured}.</li>
      <li>Uses the {@link #defaultOrOverrideTemplate(Appendable) Default template},</li>
   </ol></p>

   <A NAME="specifications"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: <u>Requirements</u></h2>

   <p>The customizer function has the following requirements:<ul>
      <li>Its location (containing class) must be <a href="#xmpl_sig">explicitely specified</a> in the taglet, or must exist in one of the following <b><u>default classes</u></b>, which are searched in order:<ol>
         <li>{@link BasicCustomizers},</li>
         <li>The {@linkplain CodeletInstance#getEnclosingClass() enclosing class}, if it is a class,</li>
         <li>And a class named "{@link TagletOfTypeProcessor#DEFAULT_CUSTOMIZER_CLASS_NAME zCodeletCustomizers}", if one exists in the {@linkplain CodeletInstance#getEnclosingPackage() enclosing package}.</li>
      </ol></li>
      <li>It must be {@code static} and</li>
      <li>accessible (it is obtained with <code>{@link java.lang.Class Class}.{@link java.lang.Class#getDeclaredMethod(String, Class...) getDeclaredMethod}</code> and made accessible with <code>theLineProcMethod.{@link java.lang.reflect.AccessibleObject#setAccessible(boolean) setAccessible}(true)</code>).</li>
      <li>Its first parameter must be a {@link CodeletInstance CodeletInstance} and second must be a {@link CodeletType}. Both of these parameters are ommitted from all taglets.</li>
      <li>It may contain zero-or-more <a href="#xmpl_params">extra parameters</a>, whose types are either primitives or non-{@code null} strings ({@code null} is not possible), as specified by
      <br/> &nbsp; &nbsp; <code>com.github.xbn.util.{@link com.github.aliteralmind.codelet.simplesig.SimpleStringSignature SimpleStringSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleStringSignature#getObjectFromString(String) getObjectFromString} </code>
      <br/>If there are any extra types in the customizer function signature, they must be provided in the {@linkplain TagletOfTypeProcessor#getCustomizerPortion() customizer portion} of every taglet using it. <i>The types, amount, and order of extra parameters, in both the taglet and the customizer function signature, must exactly match.</i></li>
   </ul></p>

   <p>When taglets are used in a class (as opposed to <a href="http://stackoverflow.com/questions/3644726/javadoc-package-html-or-package-info-java">{@code package-info.java}</a> or your project's <a href="http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#sourcefiles">overview summary</a>), it is encouraged that its customizer functions be in the class, and that these functions are {@code private}. (In the Codelet and <a href="http://codelet.aliteralmind.com">XBN-Java</a> projects, this is not possible, as doing so would create <a href="http://en.wikipedia.org/wiki/Circular_dependency">circular-dependency</a> nightmare--this is the primary reason for the {@code zCodeletCustomizers} default class.)</p>

   <A NAME="xmpl_defaults"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: Taglet syntax: Example: <u>Default function name and class location</u></h2>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName%()}}</blockquote>

   <p>This {@code ":()"} shortcut indicates a customizer function with the standard name and class location should be used. In particular:<ul>
      <li>Its name is {@code "getSourceCode_ExampleClassName"},</li>
      <li>It has no extra parameters, and it</li>
      <li>Must exist in one of the <a href="#specifications">default classes</a></li>
   </ul></p>

   <p>{@code {@.codelet fully.qualified.examples.ExampleClassName%()}}</p>

   <p>is equivalent to both</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:getSourceCode_ExampleClassName()}}</blockquote>

   <p>and</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:package.of.EnclosingClass#getSourceCode_ExampleClassName()}}</blockquote>

   <p>with one exception: When the processor's function name is explicitely specified, <i>but its class is not</i> (which is true in the first two of the three above), the customizer must exist in one of the default classes</p>

   <A NAME="proc_custom_post"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: Taglet syntax: Example: <u>Defaults with custom postfix</u></h2>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName%_ExtraStuff()}}</blockquote>

   <p>Same as the <a href="#xmpl_defaults">default example</a>, except the underscore-first-character indicates that this is not the customizer's entire function name, rather its <i>postfix</i>.</p>

   <p>This is useful when there are multiple codelets of the same {@linkplain CodeletType type}, for the same example class (or text file).</p>

   <A NAME="xmpl_sig"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: Taglet syntax: Example: <u>Specifying the class in which the processor function exists</u></h2>

   <p>The customizer function can be in any class, which may explicitely specified:</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:fully.qualified.package.MyCodeletCustomizers#getSource_ExampleClass(true, "See line 12")}}</blockquote>

   <p>If using the <a href="#xmpl_defaults">default function name</a>, it may be omitted, although the hash ({@code '#'}) is required:</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:fully.qualified.package.MyCodeletCustomizers#(true, "See line 12")}}</blockquote>

   <p>Signature formatting is as specified by
   <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</code>
   <br/>(Before being provided to {@code newFromStringAndDefaults}, the omitted function name is given its default value [{@code "getSource_ExampleClass"}], as described in this and the <a href="#proc_custom_post">previous example</a>. In all cases, {@code SimpleMethodSignature} requires a function name.)</p>

   <A NAME="xmpl_params"></a><h2><a href="#overview"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></a> &nbsp; Codelet: Customizer: Taglet syntax: Example: <u>Specifying extra processor parameters</u></h2>

   <p>The default customizer has a single {@link CodeletInstance CodeletInstance} parameter. This is specified in the taglet with either empty parentheses, or no parens at all, as demonstrated in the <a href="#xmpl_defaults">default example</a>.</p>

   <p>Extra parameters may be optionally specified, and must be provided <i>both</i> in the function and in any taglet that uses (calls) it. For example, this taglet</p>

<blockquote>{@code {@.codelet fully.qualified.examples.ExampleClassName:(true, "See line 12")}}</blockquote>

   <p>refers to this function:</p>

<blockquote>{@code getSourceCode_ExampleClassName(CodeletInstance taglet, CodeletType needed_defaultAlterType, boolean do_displayLineNums, String annotation)}</blockquote>

   <p>which, since there is no fully-qualified class specified after the {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR percent sign}, must be in one of the <a href="#specifications">default class-locations</a>.</p>

   <p>Parameter formatting is specified by
   <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature}.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}</code></p>

   <p>This is a "simple" signature. Only {@linkplain com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getObjectFromString(String) primitives and strings} are allowed. {@code null} is not possible.</p>

   <p>Extra parameters can also be specified with the <a href="#xmpl_defaults">{@code ":()"} shortcut</a>:
   <br/> &nbsp; &nbsp; {@code {@.codelet fully.qualified.examples.ExampleClassName:((byte)3, false)}}</p>


 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
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
   	<p>Create a new instance for any taglet type except {@code {@.codelet.and.out}}.</p>

   	<p>Equal to
   	<br/> &nbsp; &nbsp; <code>{@link #CustomizationInstructions(CodeletInstance, CodeletType) this}(instance, instance.getType())</code></p>

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
   	<p>Create a new instance.</p>

   	<p>Equal to
   	<br/> &nbsp; &nbsp; <code>{@link #CustomizationInstructions(CodeletInstance, CodeletType) this}(instance, instance.getType())</code></p>

   	@param  instance  May not be {@code null}.
   	@param  needed_defaultAlterType  The type of {@linkplain com.github.aliteralmind.codelet.alter.DefaultAlterGetter default alterers} needed when using the {@linkplain #defaultOrOverrideTemplate(Appendable) default template}. May not be {@code null}. If <code>instance.{@link CodeletInstance#getType() getType}.{@link CodeletType#SOURCE_AND_OUT SOURCE_AND_OUT}</code>, then this must be either {@link CodeletType#SOURCE_CODE SOURCE_CODE} or {@link CodeletType#CONSOLE_OUT CONSOLE_OUT}. If <code>instance.{@link CodeletInstance#getType() getType}</code> is any other type, then {@code needed_defaultAlterType} must be equal to it.
    */
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
      <p>Make no customizations.</p>

      <p>This calls<ol>
         <li>{@link #unfiltered(Appendable, LengthInRange) unfiltered}{@code (dbgDest_ifNonNull)}</li>
         <li><code>{@link #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) orderedAlterers}(dbgAllAltr_ifNonNull, {@link com.github.xbn.array.NullElement NullElement}.{@link com.github.xbn.array.NullElement#BAD BAD}
         <br/> &nbsp; &nbsp; {@link com.github.xbn.analyze.alter.ExpirableElements}.{@link com.github.xbn.analyze.alter.ExpirableElements#OPTIONAL OPTIONAL}, {@link com.github.xbn.analyze.alter.MultiAlterType}.{@link com.github.xbn.analyze.alter.MultiAlterType#CUMULATIVE CUMULATIVE}
            <br/> &nbsp; &nbsp; {@link com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil}.{@link com.github.aliteralmind.codelet.alter.DefaultAlterGetterUtil#getDefaultAlterArray(CodeletType) getDefaultAlterArray}({@link #getNeededAlterArrayType() getNeededAlterArrayType}()))</code></li>
         <li>{@link #defaultOrOverrideTemplate(Appendable) defaultOrOverrideTemplate}{@code (dbgTemplate_ifNonNull)}</li>
      </ol></p>

    * @return  <i>{@code this}</i>
    */
   public CustomizationInstructions<T> defaults(Appendable dbgEveryLine_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull, Appendable dbgAllAltr_ifNonNull, Appendable dbgTemplate_ifNonNull)  {
      unfiltered(dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull);
      orderedAlterers(dbgAllAltr_ifNonNull, NullElement.BAD,
         ExpirableElements.OPTIONAL, MultiAlterType.CUMULATIVE,
         DefaultAlterGetterUtil.getDefaultAlterArray(getNeededAlterArrayType()));
      return  defaultOrOverrideTemplate(dbgTemplate_ifNonNull);
   }
   /*
      <p>The type of template needed, when using the default template. This is intended for {@link CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets only.</p>

    * @return  A non-{@code null} type representing the kind of template needed.
    * @see  #CustomizationInstructions(CodeletInstance, CodeletType) constructor
    * @see  #defaults(Appendable, LengthInRange, Appendable, Appendable) defaults
    */
   public CodeletType getNeededAlterArrayType()  {
      return  defaultAltersType;
   }
   /**
      <p>Get the line-filter.</p>

    * @see  #filter(FilteredLineIterator)
    */
   public FilteredLineIterator getFilter()  {
      return  filter;
   }
   /**
      <p>Get the line-alterer.</p>

    * @see  #alterer(AllTextLineAlterer)
    */
   public AllTextLineAlterer getAlterer()  {
      return  alterer;
   }
   /**
      <p>Get the template.</p>

    * @return  <ul>
         <li>A non-{@code null} template: When <i>this taglet only</i> should use a non-default (and non-{@linkplain TemplateOverrides override}) template.</li>
         <li>{@code null}: If {@link #wasTemplateSet() wasTemplateSet}{@code ()} is<ul>
            <li>{@code true}: No template was set.</li>
            <li>{@code false}: The default (or override) template should be used.</li>
         </ul></li>
      </ul>
    * @see  <code><!-- GENERIC PARAMETERS FAIL IN @link --><a href="#template(T)">template</a>(T)</code>
    * @see  #defaultOrOverrideTemplate(Appendable)
    */
   public T getTemplate()  {
      return  template;
   }
   public CodeletInstance getInstance()  {
      return  instance;
   }
   /**
      <p>Use the default (or override) template.</p>

      <p>This sets<ol>
         <li>{@link #getTemplate() getTemplate}{@code ()} to {@code null}</li>
         <li>{@link #wasTemplateSet() wasTemplateSet}{@code ()} to {@code true}</li>
      </ol></p>

      <p>This leaves the template object as {@code null} to avoid having to know about the {@link CodeletInstance}, which is required to determine if the default or {@linkplain TemplateOverrides override} template should be used. A {@code null} template value triggers the {@linkplain TagletOfTypeProcessor taglet processor} to get the appropriate template.</p>

    * @param  dbgDest_ifNonNull  When non-{@code null}, this is the debugging destination for all gap-fills. Get with {@link #getDefaultTemplateDebug() getDefaultTemplateDebug}{@code ()}
    * @return  <i>{@code this}</i>
    * @exception  LockException  If {@link #build() build}{@code ()} was already called.
    * @exception  IllegalStateException  If {@code wasTemplateSet()} is {@code true}.
    * @see  <code><!-- GENERIC PARAMETERS FAIL IN @link --><a href="#template(T)">template</a>(T)</code>
    */
   public CustomizationInstructions<T> defaultOrOverrideTemplate(Appendable dbgDest_ifNonNull)  {
      ciLockedOrTmplAlreadySet();
      template = null;
      wasTmplSet = true;
      dfltTmplDbg = dbgDest_ifNonNull;
      return  this;
   }
   /**
      <p>Override the template for this codelet-taglet only.</p>

      <p>This sets {@link #wasTemplateSet() wasTemplateSet}{@code ()} to {@code true}.</p>

    * @param  template  May not be {@code null}. Get with {@link #getTemplate() getTemplate}{@code ()}. Note that JavaDoc is multi-threaded, and therefore this template-object must be a new object (not shared among multiple taglets). Use the {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#FeatherTemplate(FeatherTemplate, Appendable) copy constructor} or {@link com.github.aliteralmind.templatefeather.FeatherTemplate#getObjectCopy() getObjectCopy}{@code ()} to duplicate it.
    * @return  <i>{@code this}</i>
    * @exception  LockException  If {@link #build() build}{@code ()} was already called.
    * @exception  IllegalStateException  If {@code wasTemplateSet()} is {@code true}.
    * @see  #defaultOrOverrideTemplate(Appendable)
    */
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
      <p>Display all lines.</p>

    * @return  <code>{@link #filter(FilteredLineIterator) filter}({@link com.github.xbn.linefilter.NewFilteredLineIteratorFor NewFilteredLineIteratorFor}.{@link com.github.xbn.linefilter.NewFilteredLineIteratorFor#keepAllLinesUnchanged(Iterator, Appendable, LengthInRange) keepAllLinesUnchanged}(null, dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull))</code>
    */
   public CustomizationInstructions<T> unfiltered(Appendable dbgEveryLine_ifNonNull, LengthInRange rangeForEveryLineDebug_ifNonNull)  {
      return  filter(NewFilteredLineIteratorFor.keepAllLinesUnchanged(null, dbgEveryLine_ifNonNull, rangeForEveryLineDebug_ifNonNull));
   }
   /**
      <p>Keep or eliminate lines that meet some conditions. Kept lines may be {@linkplain #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) altered}.</p>

      <p>Two examples of filtering lines:<ul>
         <li>Displaying only a range of lines--a <a href="{@docRoot}/overview-summary.html#xmpl_snippet">code snippet</a>.</li>
         <li><a href="{@docRoot}/overview-summary.html#xmpl_hello">Eliminating</a> the package declaration line and all JavaDoc multi-line comments</li>
      </ul></p>

    * @param  filter  May not be {@code null}. Get with {@link #getFilter() getFilter}{@code ()}.
    * @return  <i>{@code this}</i>
    * @see  #unfiltered(Appendable, LengthInRange)
    * @exception  LockException  If {@link #build() build}{@code ()} was already called.
    */
   public CustomizationInstructions<T> filter(FilteredLineIterator filter)  {
      ciLocked();
      if(filter.wasAllIteratorSet())  {
         throw  new IllegalStateException("filter.wasAllIteratorSet()=true");
      }
      this.filter = filter;
      return  this;
   }
   /**
      <p>Set an ordered series of line-alterers.</p>

    * @param  alterers  May not be {@code null} or empty and, if <code>null_element.{@link com.github.xbn.array.NullElement#isBad() isBad}()</code> is {@code true}, no elements may be {@code null}. Elements <i>should</i> not be duplicate.
    * @return  <code>{@link #alterer(AllTextLineAlterer) alterer}(new {@link com.github.xbn.linefilter.alter.AllTextLineAlterer#AllTextLineAlterer(TextLineAlterer[], ExpirableElements, MultiAlterType, Appendable) AllTextLineAlterer}(alterers, xprbl_elements, multi_type, dbgDest_ifNonNull))</code>
    */
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
      <p>Set the line-alterer.</p>

    * @param  all_lineAlterer  May not be {@code null}. Get with {@link #getAlterer() getAlterer}{@code ()}.
    * @return  <i>{@code this}</i>
    * @exception  LockException  If {@link #build() build}{@code ()} was already called.
    * @see  #orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...)
    */
   public CustomizationInstructions<T> alterer(AllTextLineAlterer all_lineAlterer)  {
      ciLocked();
      Objects.requireNonNull(all_lineAlterer, "all_lineAlterer");
      alterer = all_lineAlterer;
      return  this;
   }
   /**
      <p>Wildcard search-term to restrict the classes or files that may utilize this customizer.</p>

    * @param  whitelist_searchTerm  Wildcard search term to match the fully-qualified class name of the example code, or path of the text file that is allowed to use this customizer. Class name examples:<ul>
         <li>{@code "com.github.mylibrary.examples.AGoodExample"}</li>
         <li>{@code "com.github.mylibrary.examples.A*Example"}</li>
         <li>{@code "*.examples.A*Example"}</li>
      </ul>Text file examples:<ul>
         <li>{@code "com/github/mylibrary/examples/doc-files/AGoodExample_input.txt"}</li>
         <li>{@code "com/github/mylibrary/examples/doc-files/*_input.txt"}</li>
         <li>{@code "*_input.txt"}</li>
      </ul>May not be {@code null} or empty, and <i>should</i> be a valid wildcard term. Get with {@link #getClassNameOrFilePathRestricter() getClassNameOrFilePathRestricter}{@code ()}.
    * @return  <i>{@code this}</i>
    * @see  org.apache.commons.io.FilenameUtils#wildcardMatch(String, String) FilenameUtils#wildcardMatch
    * @see  TagletOfTypeProcessor#crashIfClassOrFileCannotUseCustomizer(CustomizationInstructions) TagletOfTypeProcessor#crashIfClassOrFileCannotUseCustomizer
    */
   public CustomizationInstructions<T> classNameOrFilePathRestricter(String whitelist_searchTerm)  {
      CrashIfString.nullEmpty(whitelist_searchTerm, "whitelist_searchTerm", null);
      classNameOrFilePathRestricter = whitelist_searchTerm;
      return  this;
   }
   /**
      <p>Wildcard search-term to restrict the classes that may utilize this customizer.</p>

    * @see  #classNameOrFilePathRestricter(String)
    */
   public String getClassNameOrFilePathRestricter()  {
      return  classNameOrFilePathRestricter;
   }
   /**
      <p>Was the template set?.</p>

    * @see  #getTemplate()
    * @see  <code><!-- GENERIC PARAMETERS FAIL IN @link --><a href="#template(T)">template</a>(T)</code>
    * @see  #defaultOrOverrideTemplate(Appendable)
    */
   public boolean wasTemplateSet()  {
      return  wasTmplSet;
   }
   /**
      <p>When using the default template only, this is its debug destination.</p>

    * @see  #getTemplate()
    * @see  #defaultOrOverrideTemplate(Appendable)
    */
   public Appendable getDefaultTemplateDebug()  {
      return  dfltTmplDbg;
   }
   /**
      <p>Given all configured customization instructions, transform the raw output (source-code, console-output, or file-text) into its fully-processed form, ready for insertion into the template.</p>

      <p>This logs all alterers that do not make an alteration.</p>

    * @exception  AlterationNotMadeException  If at least one alteration is not made, and it is {@linkplain CodeletBaseConfig#ALTERATION_NOT_MADE_CRASH configured} that a crash should occur (in addition to the warning).
    */
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
      <p>Declare that this object is ready for use and should be locked.</p>

    * @return  <i>{@code this}</i>
    * @exception  IllegalStateException  If the {@linkplain #getFilter() filter} or {@linkplain #getAlterer() alterer} are {@code null}, or {@link #wasTemplateSet() wasTemplateSet}{@code ()} is {@code false}.
    */
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
