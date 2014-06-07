/*license*\
   Codelet

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

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
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  com.github.aliteralmind.templatefeather.GapMap;
   import  com.github.aliteralmind.templatefeather.IncorrectGapsException;
   import  com.github.aliteralmind.templatefeather.Resettable;
   import  com.github.aliteralmind.templatefeather.TemplateValidationUtil;
   import  com.github.xbn.array.CrashIfArray;
   import  com.github.xbn.array.NullContainer;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.io.SimpleDebuggable;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.Null;
   import  com.github.xbn.text.CrashIfString;
   import  java.util.Arrays;
   import  java.util.HashSet;
   import  java.util.Map;
   import  java.util.Objects;
   import  java.util.Set;
   import  java.util.TreeMap;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>What the fully-processed output (source-code, console output, or plain-file text) is put into--The rendered template is what actually replaces the codelet-taglet.</P>

   <H2><A HREF="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: <U>Templates: Overview</U></H2>

   <P>Codelet templates have one or two required &quot;body&quot; gaps, where the fully-processed text (source-code, console output, or plain-file text) is placed. The {@code {@codelet.and.out}} template has two body gaps, the rest have one. Each template type defines &quot;<B>optional-default</B>&quot; gaps, which you may place in your templates at your discretion. &quot;<B>Extra user-created</B>&quot; gaps are permitted if {@linkplain CodeletBaseConfig#USER_EXTRA_GAPS_CLASS_NAME explicitely configured}.</P>

   <P><I>All gaps, in all Codelet templates (including user-created), are automatically {@linkplain CodeletGap#getFillText(CodeletInstance) filled}.</I></P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public abstract class CodeletTemplateBase extends SimpleDebuggable  {
   private final CodeletType            type            ;
   private final FeatherTemplate        template        ;
   private final String                 tmplPath        ;
   private final Map<String,CodeletGap> allNonBodyGapMap;
   /**
      <P>Create the first instance only. To avoid circular dependencies, this class cannot have any references to {@link com.github.aliteralmind.codelet.CodeletBaseConfig}.</P>

      @param  type  May not be {@code null}. Get with {@link #getType() getType}{@code ()}.
      @param  template  May not be {@code null}, must be {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#isResettable() resettable}, and must contain all body gaps, and no gaps that are not either optional-default or user-extra. This is duplicated (defensively copied). Get with {@link #getTemplate() getTemplate}{@code ()}.
      @param  tmpl_path  The full path to the template file. May not be {@code null} or empty. Get with {@link #getPath() getPath}{@code ()}
      @param  required_bodyGapNames  The one or two required body-gap names.
      @param  optional_defaultGaps  The optional-default gaps for the codelet type. May not be {@code null} or contain {@code null} elements, and all gap {@linkplain com.github.xbn.keyed.Named#getName() names} must be unique.
      @param  userExtra_gapGetter  Extra user-configured gaps. If {@code null}, there are no extra. Otherwise, the gaps its function (of type {@code type}) returns may not be {@code null} or contain {@code null} elements, and all gap names must be unique <I>and not equal to the body gaps or those in {@code optional_defaultGaps}</I>.
      @exception  IncorrectGapsException  If<UL>
         <LI>The length of {@code required_bodyGapNames} is invalid</LI>
         <LI>The user-extra gaps contain a body or optional-default gap</LI>
         <LI>The template is missing a body gap or contains any not in either the optional-defaults or user-extra</LI>
      </UL></LI>
      @exception  IllegalArgumentException  If the {@code userExtra_gapGetter} function of type {@code type} returns {@code null}.
      @see  #CodeletTemplateBase(CodeletTemplateBase, FeatherTemplate, String)
      @see  #CodeletTemplateBase(CodeletTemplateBase, Appendable)
    **/
   public CodeletTemplateBase(CodeletType type, FeatherTemplate template, String tmpl_path, String[] required_bodyGapNames, CodeletGap[] optional_defaultGaps, UserExtraGapGetter userExtra_gapGetter)  {
      Objects.requireNonNull(type, "type");
      CrashIfString.nullEmpty(tmpl_path, "tmpl_path", null);
      CrashIfArray.lengthLessThan(required_bodyGapNames, "required_bodyGapNames", NullContainer.BAD, 1, null);
      if(required_bodyGapNames.length > 2)  {
         throw  new IncorrectGapsException("required_bodyGapNames.length (" + required_bodyGapNames.length + ") must be two or one. required_bodyGapNames=" + Arrays.toString(required_bodyGapNames));
      }
      TemplateValidationUtil.crashIfNotResettable(template, "template");
      this.template = template.getObjectCopy();

      this.type = type;
      tmplPath = tmpl_path;

      CodeletGap[] usrXtraGaps = null;
      if(userExtra_gapGetter != null)  {
         switch(type)  {
            case SOURCE_CODE:
               usrXtraGaps = userExtra_gapGetter.getForSourceCodelet();
               break;
            case CONSOLE_OUT:
               usrXtraGaps = userExtra_gapGetter.getForCodeletDotOut();
               break;
            case SOURCE_AND_OUT:
               usrXtraGaps = userExtra_gapGetter.getForCodeletAndOut();
               break;
            case FILE_TEXT:
               usrXtraGaps = userExtra_gapGetter.getForFileTextlet();
               break;
            default:  throw  new IllegalStateException("Unknown type: " + type);
         }
         if(usrXtraGaps == null)  {
            throw  new IllegalArgumentException("userExtra_gapGetter.getFor*() returned null. For no gaps, it must return an empty array.");
         }
      }  else  {
         usrXtraGaps = new CodeletGap[]{};
      }

      TemplateValidationUtil.crashIfMissingRequiredGaps(template, "template", required_bodyGapNames);
      for(String name : required_bodyGapNames)  {
         for(CodeletGap gap : usrXtraGaps)  {
            try  {
               if(gap.getName().equals(name))  {
                  throw  new IncorrectGapsException("Body gap named \"" + name + "\" found in user-extra gaps.");
               }
            }  catch(RuntimeException rx)  {
               throw  CrashIfObject.nullOrReturnCause(gap, "[One of the user-extra gaps]", null, rx);
            }
         }
      }

      allNonBodyGapMap = CodeletTemplateBase.newGapMapFromArray(optional_defaultGaps);
      CodeletTemplateBase.addCustomGaps(allNonBodyGapMap, usrXtraGaps);

      //user-extras have no duplicates.

      //Crash if the template has any unexpected (not optional-default
      //or user-extra) gaps
      Set<String> unexpectedNameSet = new HashSet<String>(template.getGapMap().size());
      unexpectedNameSet.addAll(template.getGapMap().newNameSet());
      for(String name : required_bodyGapNames)  {
         unexpectedNameSet.remove(name);
      }
      unexpectedNameSet.removeAll(allNonBodyGapMap.keySet());

      if(unexpectedNameSet.size() > 0)  {
         throw  new IncorrectGapsException("Unexpected gaps found in template: " + Arrays.toString(unexpectedNameSet.toArray()) + ". Body gap names: " + Arrays.toString(required_bodyGapNames) + ", Optional-default *and* user-extra: " + Arrays.toString(allNonBodyGapMap.keySet().toArray()));
      }
   }
   /**
      <P>Create the second or subsequent instance.</P>

      <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE level debugging}</H4>

      <P>{@code zzCodeletTemplateBase.templateparseandfill}: <CODE>{@link com.github.aliteralmind.templatefeather.FeatherTemplate}.</CODE>{@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#FeatherTemplate(FeatherTemplate, Appendable) constructor}</P>

      @param  to_copy  May not be {@code null}.
      @param  template  May not be {@code null}, must have all body gaps, and may not have any gaps that are not either optional-default or user-extra. This is duplicated (defensively copied). Get with {@link #getTemplate() getTemplate}{@code ()}.
      @see  #CodeletTemplateBase(CodeletType, FeatherTemplate, String, String[], CodeletGap[], UserExtraGapGetter)
      @see  #CodeletTemplateBase(CodeletTemplateBase, Appendable)
    **/
   public CodeletTemplateBase(CodeletTemplateBase to_copy, FeatherTemplate template, String tmpl_path)  {

      try  {
         type = to_copy.getType();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_copy, "to_copy", null, rx);
      }
      allNonBodyGapMap = new TreeMap<String,CodeletGap>();
      allNonBodyGapMap.putAll(to_copy.allNonBodyGapMap);

      Appendable dbgTmpl = getDebugApblIfOn(null,
         "zzCodeletTemplateBase.newTemplateFromPath.templateparseandfill");
      try  {
         this.template = new FeatherTemplate(template, dbgTmpl);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(template, "template", null, rx);
      }
      CrashIfString.nullEmpty(tmpl_path, "tmpl_path", null);
      tmplPath = tmpl_path;
   }
   /**
      <P>Create a new instance as a duplicate of another.</P>

      <P>This calls<OL>
         <LI>{@link #CodeletTemplateBase(CodeletTemplateBase, FeatherTemplate, String) this}(to_copy, to_copy.getTemplate(), to_copy.getPath())</LI>
         <LI>{@link com.github.xbn.io.SimpleDebuggable#onIfNonNull(Appendable) onIfNonNull}{@code (debugDest_ifNonNull)}</LI>
      </OL></P>
    **/
   public CodeletTemplateBase(CodeletTemplateBase to_copy, Appendable debugDest_ifNonNull)  {
      this(to_copy,
         ((to_copy == null) ? null : to_copy.getTemplate()),
         ((to_copy == null) ? null : to_copy.getPath()));
      onIfNonNull(debugDest_ifNonNull);
   }

   /**
      <P>The type of this template.</P>

      @see  #CodeletTemplateBase(CodeletType, FeatherTemplate, String, String[], CodeletGap[], UserExtraGapGetter)
    **/
   public CodeletType getType()  {
      return  type;
   }
   /**
      <P>Fill a body gap.</P>

      <P>Equal to</P>

      <P><CODE>{@link #getTemplate() getTemplate}().{@link com.github.aliteralmind.templatefeather.FeatherTemplate#fill(String, Object) fill}(body_gapName, fill_with)</CODE></P>

      @param  body_gapName  The body gap name. Must not have already been filled.
      @param  body_text  May not be {@code null} or empty.
    **/
   public void fillBodyGap(String body_gapName, String body_text)  {
      CrashIfString.empty(Null.OK, body_text, "body_text", null);
      getTemplate().fill(body_gapName, body_text);
   }
   /**
      <P>ReplacedInEachInput all default-optional and user-extra gaps with their values, resets the template, and returns its fully-rendered text--This is what actually replaces the codelet-taglet.</P>

      @param  instance  May not be {@code null}.
      @exception  IllegalArgumentException  If any gaps are already filled, or if the com.github.aliteralmind.codelet.CodeletGap#getFillText(CodeletInstance) fill text is {@code null} or empty.
      @see  #fillBodyGap(String, String)
    **/
   public String getRendered(CodeletInstance instance)  {
      Set<String> nameSet = allNonBodyGapMap.keySet();
      for(String name : nameSet)  {
         if(!getTemplate().getGapMap().contains(name))  {
            continue;
         }
         String fillText = null;
         try  {
            fillText = allNonBodyGapMap.get(name).getFillText(instance);
            if(fillText.length() == 0)  {
               throw  new IllegalArgumentException("Fill text for CodeletGap named \"" + name + "\" has no characters.");
            }
         }  catch(RuntimeException rx)  {
            CrashIfObject.nnull(instance, "instance", null);
            throw  CrashIfObject.nullOrReturnCause(fillText, "[Fill text for CodeletGap named \"" + name + "\"]", null, rx);
         }
         getTemplate().fill(name, fillText);
      }
      return  getTemplate().getFilledAndReset();
   }
   /**
      <P>The number of gaps actually in the template.</P>

      @return  <CODE>{@link #getTemplate() getTemplate}().{@link com.github.aliteralmind.templatefeather.FeatherTemplate#getGapMap() getGapMap}().size()</CODE>
    **/
   public int getGapCount()  {
      return  getTemplate().getGapMap().size();
   }
   /**
      <P>The template.</P>

      @see  #getGapCount()
      @see  #CodeletTemplateBase(CodeletType, FeatherTemplate, String, String[], CodeletGap[], UserExtraGapGetter) CodeletTemplateBase(ct,ft,s,s[],cg[],uxgg)
    **/
   protected FeatherTemplate getTemplate()  {
      return  template;
   }
   public String getPath()  {
      return  tmplPath;
   }
   public abstract CodeletTemplateBase getObjectCopy(Appendable debugDest_ifNonNull);
   /**
      <P>Create a new map of all gap objects.</P>

      @param  gap_array  May not be {@code null} or contain {@code null} elements, and all gap {@linkplain com.github.xbn.keyed.Named#getName() names} must be unique.
      @exception  IllegalArgumentException  If a gap name is used more than once.
      @see  #addCustomGaps(Map, CodeletGap[])
    **/
   public static final Map<String,CodeletGap> newGapMapFromArray(CodeletGap[] gap_array)  {
      return  newGapMapFromArray(gap_array, true);
   }
      private static final Map<String,CodeletGap> newGapMapFromArray(CodeletGap[] gap_array, boolean do_crashIfDupName)  {
         Map<String,CodeletGap> optionalDefault_gapMap = new TreeMap<String,CodeletGap>();
         try  {
            for(int i = 0; i < gap_array.length; i++)  {
               CodeletGap gap = gap_array[i];
               try  {
                  if(do_crashIfDupName  &&  optionalDefault_gapMap.containsKey(gap.getName()))  {
                     throw  new IllegalArgumentException("Duplicate gap name: gap_array[" + i + "].getName() (\"" + gap.getName() + "\"). All names: " + Arrays.toString(gap_array));
                  }
               }  catch(RuntimeException rx)  {
                  throw  CrashIfObject.nullOrReturnCause(gap, "gap_array[" + i + "]", null, rx);
               }
               optionalDefault_gapMap.put(gap.getName(), gap);
            }
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(gap_array, "gap_array", null, rx);
         }
         return  optionalDefault_gapMap;
      }
   /**
      <P>Add custom gaps to the template.</P>

      @param  optionalDefault_gapMap  May not be {@code null}, and must contain only the ...default gaps....
      @param  gap_array  May not be {@code null} or contain {@code null} elements, and all gap {@linkplain com.github.xbn.keyed.Named#getName() names} must be unique <I>and not contain any in {@code optionalDefault_gapMap}.</I>
      @exception  IllegalArgumentException  If {@code gap_array} contains a duplicate or default gap name (or they've already been added to the template!).
    **/
   public static final void addCustomGaps(Map<String,CodeletGap> optionalDefault_gapMap, CodeletGap[] gap_array)  {
      Map<String,CodeletGap> paramGapMap = newGapMapFromArray(gap_array);
      Set<String> paramNameSet = paramGapMap.keySet();
      for(String name : paramNameSet)  {
         if(optionalDefault_gapMap.containsKey(name))  {
            throw  new IllegalArgumentException("gap_array contains a default gap name: \"" + name + "\". Default gap names: " + Arrays.toString(optionalDefault_gapMap.keySet().toArray()) + ". Names in gap_array: " + Arrays.toString(paramNameSet.toArray()));
         }
      }
      optionalDefault_gapMap.putAll(optionalDefault_gapMap);
   }
   /**
      <P>Read in and parse a template given its path.</P>

      <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE level debugging}</H4>

      <P>{@code zzCodeletTemplateBase.newTemplateFromPath.}<UL>
         <LI>{@code loading}: &quot;Loading template from <I>[path]</I>...&quot;</LI>
         <LI>{@code templateparseandfill}: <CODE>{@link com.github.aliteralmind.templatefeather.FeatherTemplate}.</CODE>{@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#FeatherTemplate(String, GapCharConfig, Resettable, Appendable) constructor}</LI>
      </UL></P>

      @see  com.github.aliteralmind.codelet.type.SourceCodeTemplate#newFromPathAndUserExtraGaps(String, String, UserExtraGapGetter) SourceCodeTemplate#newFromPathAndUserExtraGaps
      @see  com.github.aliteralmind.codelet.type.SourceAndOutTemplate#newFromPathAndUserExtraGaps(String, String, UserExtraGapGetter) SourceAndOutTemplate#newFromPathAndUserExtraGaps
      @see  com.github.aliteralmind.codelet.type.ConsoleOutTemplate#newFromPathAndUserExtraGaps(String, String, UserExtraGapGetter) ConsoleOutTemplate#newFromPathAndUserExtraGaps
      @see  com.github.aliteralmind.codelet.type.FileTextTemplate#newFromPathAndUserExtraGaps(String, String, UserExtraGapGetter) FileTextTemplate#newFromPathAndUserExtraGaps
    **/
   public static final FeatherTemplate newTemplateFromPath(String path, String path_varName, String... required_gaps)  {
      String prefix = "zzCodeletTemplateBase.newTemplateFromPath.";
      TextAppenter tbgLoading = getDebugAptrIfOn(null, prefix + "loading");
      Appendable dbgTmpl = getDebugApblIfOn(null,
         prefix + "templateparseandfill");

      if(tbgLoading != null)  {
         tbgLoading.appentln("      Loading " + path_varName + ": \"" + path+ "\"");
      }

      FeatherTemplate tmpl = new FeatherTemplate(
         PlainTextFileUtil.getText(path, path_varName),
         getGapCharConfig(), Resettable.YES, dbgTmpl);
      TemplateValidationUtil.crashIfMissingRequiredGaps(tmpl, path_varName, required_gaps);
      return  tmpl;
   }
   public void setDebug(Appendable destination, boolean is_on)  {
      if(getTemplate() != null)  {
         //This is *not* a call from the constructor (before the template exists).
         getTemplate().setDebug(destination, is_on);
      }
      super.setDebug(destination, is_on);
   }
   public void setDebugOn(boolean is_on)  {
      if(getTemplate() != null)  {
         //This is *not* a call from the constructor (before the template exists).
         getTemplate().setDebugOn(is_on);
      }
      super.setDebugOn(is_on);
   }
}
