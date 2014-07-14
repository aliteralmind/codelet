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
   import  com.github.aliteralmind.codelet.type.ConsoleOutTemplate;
   import  com.github.aliteralmind.codelet.type.FileTextTemplate;
   import  com.github.aliteralmind.codelet.type.SourceAndOutTemplate;
   import  com.github.aliteralmind.codelet.type.SourceCodeTemplate;
   import  com.github.xbn.io.IOUtil;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  com.github.xbn.text.CrashIfString;
   import  java.util.HashMap;
   import  java.util.Iterator;
   import  java.util.Map;
   import  java.util.regex.Pattern;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>For optional overriding of default templates, for a single JavaDoc page or an entire package. For overriding a template in a single taglet, a customizer must be used.</P>

   <P>Configuration is in a text file named {@linkplain CodeletBootstrap#TMPL_OVERRIDES_CONFIG_FILE_NAME template_overrides_config.txt}, which is located in the same directory as {@link com.github.aliteralmind.codelet.CodeletBaseConfig codelet.properties} (view <A HREF="{@docRoot}/${jd_project_codelet_config_dir}/template_overrides_config.txt">{@code {@docRoot}/${jd_project_codelet_config_dir}/template_overrides_config.txt}</A>). Loading is executed by {@link com.github.aliteralmind.codelet.CodeletBootstrap}.</P>

   <P>If {@code template_overrides_config.txt} is empty (or contains only comments), then default templates are always used.</P>

   <P>Each line is an override for either a specific JavaDoc file, or an entire package. It's format:</P>

<BLOCKQUOTE><PRE><I>[fully-qualified-JavaDoc-file-name]     [codelet-type]     [relative-path-of-template-file]</I></PRE></BLOCKQUOTE>

   <P><B>Examples:</B></P>

<BLOCKQUOTE><PRE>com.github.smith.overview-summary.html    SOURCE_CODE    overview_codelet_tmpl.txt
com.github.smith.overview-summary.html    SOURCE_AND_OUT   overview_codelet_and_out.txt
com.github.smith.sub.package              SOURCE_CODE    sub_packages\smith_pkg_codelet_tmpl.txt
com.github.smith.sub.package.AClass.java  CONSOLE_OUT   sub_packages\com_github_smith_overview_codelet_dot_out_tmpl.txt
com.github.smith.sub.package.AClass.java  FILE_TEXT      sub_packages\com_github_smith_overview_file_textlet_tmpl.txt</PRE></BLOCKQUOTE>

   <P>There are three columns, {@linkplain #SPLIT_PATTERN separated by} one-or-more tabs, or two-or-more spaces:<OL>
      <LI><B>{@code [fully-qualified-JavaDoc-file-name]}:</B> The file or package to override.</LI>
      <LI><B>{@code [codelet-type]}:</B> The {@linkplain CodeletType type} of codelet to override. Must equal &quot;{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE SOURCE_CODE}&quot;, &quot;{@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT CONSOLE_OUT}&quot;, &quot;{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT SOURCE_AND_OUT}&quot;, or &quot;{@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT FILE_TEXT}&quot;.</LI>
      <LI><B>{@code [relative-path-of-template-file]}:</B> The relative directory to the template file, as it exists in the user-template {@linkplain CodeletBaseConfig#USER_TEMPLATE_BASE_DIR base directory}.</LI>
   </OL></P>

   <P>Lines may be indented, and any lines starting with a hash ({@code '#'}) are ignored. Empty lines are also ignored.</P>

   <P><B>Column one:</B> <U><I>{@code [fully-qualified-JavaDoc-file-name]}</I></U></P>

   <P><TABLE ALIGN="center" WIDTH="100%" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><U><B>Item type</B></U></TD>
      <TD><B><U>Example</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>An individual class</TD>
      <TD>{@code fully.qualified.AClass.java}</TD>
      <TD>Its fully-qualified class name, plus {@code ".java"}</TD>
   </TR><TR>
      <TD>The package summary page</TD>
      <TD>{@code fully.qualified.package-info.java}
      <BR>{@code fully.qualified.package-summary.html}</TD>
      <TD>It's fully-qualified name, including postfix, as it is <I>read by</I> the {@code javadoc} application (use the name of its <I>source</I>-file).</TD>
   </TR><TR>
      <TD>The overview summary page</TD>
      <TD>{@code overview-summary.html}</TD>
      <TD>The relative path of overview file, as it is configured into the <A HREF="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#overview">{@code -overview} option</A>.</TD>
   </TR><TR>
      <TD>An entire package</TD>
      <TD>{@code fully.qualified}</TD>
      <TD>The fully qualified package name. This only overrides classes directly in this package. No sub-packages are affected.</TD>
   </TR></TABLE></P>

   <P>If both a file and its package are overridden, the individual file's override always takes precedence.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum TemplateOverrides  {
   INSTANCE;
   private static boolean                        wasLoaded           ;
   private static Map<String,TemplateMapForItem> pkgMap              ;
   private static Map<String,TemplateMapForItem> fileMap             ;
   /**
      <P>The pattern to split each line on (after it's trimmed)--Equal to <CODE>{@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(&quot;(?:\\t|[ \\t]{2,})&quot;)</CODE></P>
    **/
   public static final Pattern SPLIT_PATTERN = Pattern.compile("(?:\\t|[ \\t]{2,})");
   /**
   	<P>YYY</P>

      @exception  IllegalStateException  If
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.CodeletTemplateConfig CodeletTemplateConfig}.{@link com.github.aliteralmind.codelet.CodeletTemplateConfig#wasLoaded() wasLoaded}</CODE>
      <BR>is {@code false}, or {@link #wasLoaded() wasLoaded}{@code ()} is {@code true}.
    **/
   static final TemplateOverrides loadConfigGetInstance(Iterator<String> configFile_lineItr)  {
      if(!CodeletTemplateConfig.wasLoaded())  {
         throw  new IllegalStateException("CodeletTemplateConfig.wasLoaded() is false.");
      }
      if(wasLoaded())  {
         throw  new IllegalStateException("wasLoaded() is true.");
      }

      if(isDebugOn(null, "zzconfiguration.progress"))  {
         debugln("   Loading template overrides config");
      }

      pkgMap = new HashMap<String,TemplateMapForItem>(500);
      fileMap = new HashMap<String,TemplateMapForItem>(500);

      int lineNum = 1;
      try  {
         while(configFile_lineItr.hasNext())  {
            processLine(lineNum++, configFile_lineItr.next());
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(configFile_lineItr, "configFile_lineItr", null, rx);
      }

      if(isDebugOn(null, "zzconfiguration.progress"))  {
         debugln("  - Template overrides:");
         debugln(staticToString());
      }

      wasLoaded = true;

      return  INSTANCE;
   }
   /**
      <P>Was configuration loaded?.</P>

      @return  {@code true} If all values loaded successfully.
    **/
   public static final boolean wasLoaded()  {
      return  wasLoaded;
   }
      private static final void processLine(int line_num, String line)  {
         line = line.trim();
         boolean doDebug = isDebugOn(null,
            "zzconfiguration.templateoverrides.eachentryasloaded");

         if(doDebug)  {
            debugln("   [" + line_num + "] " + line);
         }

         if(line.startsWith("#")  ||  line.length() == 0)  {
            return;
         }
         String[] split = SPLIT_PATTERN.split(line.trim());
         if(split.length != 3)  {
            throw  new TemplateOverridesConfigLineException(line_num, line, "Line contains " + split.length + " elements after splitting on \"" + SPLIT_PATTERN + "\".");
         }

         String itemName = split[0];
         CodeletType type = null;
         try  {
            type = CodeletType.newTypeForTagletName(split[1], "[split line element index 1 (second element)]");
         }  catch(IllegalArgumentException iax)  {
            throw  new TemplateOverridesConfigLineException(line_num, line, "Element two is not a valid Codelet type", iax);
         }
         String path = getCustomTemplateDir() + split[2];
         CodeletTemplateBase tmpl = newTemplateForTypeFromPath(type, path, line_num);
         if(itemName.endsWith(".html")  ||  itemName.endsWith(".java"))  {
            String nameNoDotExt = itemName.substring(0, itemName.length() - 5);

            if(doDebug)  {
               debugln("      File exception");
            }

            addLineItem(fileMap, nameNoDotExt, tmpl, line_num, line);
         }  else  {

            if(doDebug)  {
               debugln("      Package exception");
            }

            addLineItem(pkgMap, itemName, tmpl, line_num, line);
         }
      }
      private static final void addLineItem(Map<String,TemplateMapForItem> item_map, String item_name, CodeletTemplateBase template, int line_num, String line)  {
         if(item_map.containsKey(item_name))  {
            item_map.get(item_name).addTemplate(template, line_num, line);
         }  else  {
            item_map.put(item_name, new TemplateMapForFile(item_name, template, line_num, line));
         }
      }
   /**
      <P>Get the template.</P>

      @return   If this taglet's<OL>
         <LI>{@linkplain CodeletInstance#getEnclosingFile() enclosing file} has a template-override: The template as {@linkplain TemplateOverrides configured}</LI>
         <LI>{@linkplain CodeletInstance#getEnclosingPackage() enclosing package} has a template-override: The template as configured.</LI>
      </OL>Otherwise, the default template for the taglet's {@linkplain CodeletType type}. <I>In all cases, because JavaDoc is multi-threaded, the returned template object is duplicated.</I>
      @exception  IllegalArgumentStateException  If the enclosing file's path does not start with the enclosing class {@linkplain CodeletBaseConfig#ENCLOSING_CLASS_SRC_BASE_DIRS base directory}.
    **/
   public static final <T extends CodeletTemplateBase> T get(CodeletInstance instance, Appendable debugDest_ifNonNull)  {
      String itemName = null;

      try  {
         if(!instance.isOverviewSummary())  {
            //According to the user, this uses getEnclosingBaseDirList().
            //We're actually using getEnclosingBaseDirs(), which is to
            //avoid translating the list to an array each time.
            itemName = IOUtil.getRelativePathCrashIfBadBaseDir(instance.getEnclosingFile().getPath(),
                  "instance.getEnclosingFile().getPath()", "getEnclosingBaseDirList()", getEnclosingBaseDirs()).
               replace(FILE_SEP, ".");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(instance, "instance", null, rx);
      }

      boolean doDebug = isDebugOn(null, "zzTemplateOverrides.getresult");
      if(doDebug)  {
         debugln("   TemplateOverrides.get: " + itemName + ", type: " + instance.getType());
      }

      CodeletTemplateBase tmpl = null;
      try  {
         if(fileMap.containsKey(itemName))  {
            TemplateMapForItem tpmlForItem = fileMap.get(itemName);
            if(doDebug)  {
               debugln("   File override " + tpmlForItem);
            }
            tmpl = tpmlForItem.getDuplicatedTemplateOfType(instance.getType(), debugDest_ifNonNull);
         }  else if(pkgMap.containsKey(itemName))  {
//				tmpl = pkgMap.get(itemName).getDuplicatedTemplateOfType(instance.getType(), debugDest_ifNonNull);
            TemplateMapForItem tpmlForItem = pkgMap.get(itemName);
            if(doDebug)  {
               debugln("   Package override " + tpmlForItem);
            }
            tmpl = tpmlForItem.getDuplicatedTemplateOfType(instance.getType(), debugDest_ifNonNull);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(instance, "instance", null, rx);
      }
      if(tmpl == null)  {
         if(doDebug)  {
            debugln("   No override. Returning default template.");
         }
         if(instance.getType().isSourceCode())  {
            tmpl = CodeletTemplateConfig.getDefaultSourceCodeTemplate().
               getObjectCopy(debugDest_ifNonNull);
         }
         if(instance.getType().isConsoleOut())  {
            tmpl = CodeletTemplateConfig.getDefaultConsoleOutTemplate().
               getObjectCopy(debugDest_ifNonNull);
         }
         if(instance.getType().isSourceAndOut())  {
            tmpl = CodeletTemplateConfig.getDefaultSourceAndOutTemplate().
               getObjectCopy(debugDest_ifNonNull);
         }
         if(instance.getType().isFileText())  {
            tmpl = CodeletTemplateConfig.getDefaultFileTextTemplate().
               getObjectCopy(debugDest_ifNonNull);
         }

      }
      @SuppressWarnings("unchecked")
      T t = (T)tmpl;
      if(doDebug)  {
         debugln("   Returning template: " + t.getPath());
      }
      return  t;
   }
      @SuppressWarnings("unchecked")
      private static final <T extends CodeletTemplateBase> T newTemplateForTypeFromPath(CodeletType type, String path, int line_num)  {
         CodeletTemplateBase tmplBase = null;
         String lineNumDesc = "TemplateOverrides configuration file, line " + line_num;
         try  {
            switch(type)  {
               case SOURCE_CODE:
                  tmplBase = SourceCodeTemplate.newFromPathAndUserExtraGaps(path, lineNumDesc,
                     CodeletTemplateConfig.getUserExtraGapsClass());  break;
               case CONSOLE_OUT:
                  tmplBase = ConsoleOutTemplate.newFromPathAndUserExtraGaps(path, lineNumDesc,
                     CodeletTemplateConfig.getUserExtraGapsClass());  break;
               case SOURCE_AND_OUT:
                  tmplBase = SourceAndOutTemplate.newFromPathAndUserExtraGaps(path, lineNumDesc,
                     CodeletTemplateConfig.getUserExtraGapsClass());  break;
               case FILE_TEXT:
                  tmplBase = FileTextTemplate.newFromPathAndUserExtraGaps(path, lineNumDesc,
                     CodeletTemplateConfig.getUserExtraGapsClass());  break;
               default:  throw  new IllegalStateException("Unknown type: template.getType()=" + type);
            }
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(type, "type", null, rx);
         }
         @SuppressWarnings("unchecked")
         T t = (T)tmplBase;
         return  t;
      }
   public static final String staticToString()  {
      return  appendStaticToString(new StringBuilder()).toString();
   }
   public static final StringBuilder appendStaticToString(StringBuilder to_appendTo)  {
      to_appendTo.append("File overrides (" + fileMap.size() + " total)");

      boolean doDebug = isDebugOn(null,
         "zzconfiguration.templateoverrides.allentriespostloaded");

      if(doDebug)  {
         to_appendTo.append(":").append(LINE_SEP);
         for (Map.Entry<String,TemplateMapForItem> entry : fileMap.entrySet())  {
            entry.getValue().appendToString(to_appendTo);
         }
      }  else  {
         to_appendTo.append(LINE_SEP);
      }

      to_appendTo.append("Package overrides (" + pkgMap.size() + " total)");

      if(doDebug)  {
         to_appendTo.append(":").append(LINE_SEP);
         for (Map.Entry<String,TemplateMapForItem> entry : pkgMap.entrySet())  {
            entry.getValue().appendToString(to_appendTo);
         }
      }
      return  to_appendTo;
   }
}
class TemplateMapForItem  {
   private String itemName;
   public SourceCodeTemplate   scTmpl  ;
   public ConsoleOutTemplate   coTmpl;
   public SourceAndOutTemplate saoTmpl;
   public FileTextTemplate     ftTmpl ;
   public TemplateMapForItem(String item_name, CodeletTemplateBase first_tmpl, int line_num, String line)  {
      CrashIfString.nullEmpty(item_name, "item_name", null);
      itemName = item_name;
      addTemplate(first_tmpl, line_num, line);
   }
   public String getItemName()  {
      return  itemName;
   }
   public CodeletTemplateBase getDuplicatedTemplateOfType(CodeletType type, Appendable debugDest_ifNonNull)  {
      switch(type)  {
         case SOURCE_CODE:    return  (new SourceCodeTemplate(scTmpl, debugDest_ifNonNull));
         case CONSOLE_OUT:    return  (new ConsoleOutTemplate(coTmpl, debugDest_ifNonNull));
         case SOURCE_AND_OUT: return  (new SourceAndOutTemplate(saoTmpl, debugDest_ifNonNull));
         case FILE_TEXT:      return  (new FileTextTemplate(ftTmpl, debugDest_ifNonNull));
         default:  throw  new IllegalStateException("Unknown type: " + type);
      }
   }
   @SuppressWarnings("unchecked")
   public void addTemplate(CodeletTemplateBase template, int line_num, String line)  {
      CodeletType type = template.getType();
      switch(type)  {
         case SOURCE_CODE:
            scTmpl = TemplateMapForItem.<SourceCodeTemplate>getParamTmplOrCrashIfAlreadySet
               (this, scTmpl, (SourceCodeTemplate)template, line_num, line);
            break;
         case CONSOLE_OUT:
            coTmpl = TemplateMapForItem.<ConsoleOutTemplate>getParamTmplOrCrashIfAlreadySet
               (this, coTmpl, (ConsoleOutTemplate)template, line_num, line);
            break;
         case SOURCE_AND_OUT:
            saoTmpl = TemplateMapForItem.<SourceAndOutTemplate>getParamTmplOrCrashIfAlreadySet
               (this, saoTmpl, (SourceAndOutTemplate)template, line_num, line);
            break;
         case FILE_TEXT:
            ftTmpl = TemplateMapForItem.<FileTextTemplate>getParamTmplOrCrashIfAlreadySet
               (this, ftTmpl, (FileTextTemplate)template, line_num, line);
            break;
         default:  throw  new IllegalStateException("Unknown type: template.getType()=" + type);
      }
   }
      private static final <T extends CodeletTemplateBase> T getParamTmplOrCrashIfAlreadySet(TemplateMapForItem map_forItem, T class_tmpl, T param_tmpl, int line_num, String line)  {
         if(class_tmpl != null)  {
            throw  new TemplateOverridesConfigLineException(line_num, line, "Template of type " + param_tmpl.getType() + " type already set for " + map_forItem.getItemName());
         }
         return  param_tmpl;
      }
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      to_appendTo.append("   ").append(getItemName()).append(LINE_SEP);
      appendTypeToString(to_appendTo, scTmpl);
      appendTypeToString(to_appendTo, coTmpl);
      appendTypeToString(to_appendTo, saoTmpl);
      appendTypeToString(to_appendTo, ftTmpl);
      return  to_appendTo;
   }
   public StringBuilder appendTypeToString(StringBuilder to_appendTo, CodeletTemplateBase tmpl)  {
      return  ((scTmpl == null) ? to_appendTo
         :  to_appendTo.append("    - ").append(scTmpl.getType()).append(": ").append(scTmpl.getPath())).append(LINE_SEP);
   }
}
class TemplateMapForPackage extends TemplateMapForItem  {
   public TemplateMapForPackage(String item_name, CodeletTemplateBase first_tmpl, int line_num, String line)  {
      super(item_name, first_tmpl, line_num, line);
      if(item_name.endsWith(".html"))  {
         throw  new TemplateOverridesConfigLineException(line_num, line, "Not a package (ends with \".html\")");
      }
   }
}
class TemplateMapForFile extends TemplateMapForItem  {
   public TemplateMapForFile(String item_name, CodeletTemplateBase first_tmpl, int line_num, String line)  {
      super(item_name, first_tmpl, line_num, line);
      if(item_name.endsWith(".html"))  {
         throw  new TemplateOverridesConfigLineException(line_num, line, "Not a file (does not end with \".html\")");
      }
   }
}
