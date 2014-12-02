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
   import  com.github.aliteralmind.codelet.type.OnlyOneBodyGapTemplateBase;
   import  com.github.aliteralmind.codelet.type.SourceAndOutTemplate;
   import  com.github.aliteralmind.codelet.type.SourceCodeTemplate;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.Empty;
   import  com.github.xbn.regexutil.IgnoreCase;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  com.github.xbn.text.CharUtil;
   import  com.github.xbn.util.GetBooleanFromString;
   import  com.github.xbn.util.PropertiesUtil;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  com.github.xbn.util.tuple.TwoTuple;
   import  java.util.Iterator;
   import  java.util.Properties;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <p>Loads and manages default templates and user-extra gaps. Loading is executed by {@link com.github.aliteralmind.codelet.CodeletBootstrap}.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public enum CodeletTemplateConfig  {
   INSTANCE;
   private static boolean              wasLoaded             ;
   private static SourceCodeTemplate   defaultCodeletTmpl    ;
   private static ConsoleOutTemplate   defaultConsoleOutTmpl ;
   private static SourceAndOutTemplate defaultSrcAndOutTmpl  ;
   private static FileTextTemplate     defaultFileTextletTmpl;
   private static UserExtraGapGetter   xtraGapGetter         ;
   /**
      <p>Load configuration and return theh instance. Call only once.</p>

    * @return  #INSTANCE
    * @exception  IllegalStateException  If
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.CodeletBaseConfig CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#wasLoaded() wasLoaded}</code>
      <br/>is {@code false}, or {@link #wasLoaded() wasLoaded}{@code ()} is {@code true}.
    * @exception  ClassNotFoundException  If
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.CodeletBaseConfig CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getUserExtraGapsClassName() getUserExtraGapsClassName}()</code>
      <br/>is non-empty, but does not represent an existing class.
    * @exception  ClassCastException  If the class exists, but is not a {@link com.github.aliteralmind.codelet.UserExtraGapGetter}.
    * @see  com.github.xbn.util.PropertiesUtil
    */
   public static final CodeletTemplateConfig loadConfigGetInstance() throws ClassNotFoundException  {
      if(!CodeletBaseConfig.wasLoaded())  {
         throw  new IllegalStateException("wasLoaded() is false.");
      }
      if(wasLoaded())  {
         throw  new IllegalStateException("wasLoaded() is true.");
      }

      boolean doDebug = isDebugOn(null, "zzconfiguration.progress");

      if(doDebug)  {
         debugln("   Loading template config");
      }

      if(getUserExtraGapsClassName().length() == 0)  {
         xtraGapGetter = null;
         if(doDebug)  {
            debugln("      No user-extra gap getter.");
         }

      }  else  {
         xtraGapGetter = ReflectRtxUtil.<UserExtraGapGetter>getNewInstanceFromNoParamCnstr(
            getUserExtraGapsClassName(), UserExtraGapGetter.class,
            getDebugApblIfOn(null, "zzconfiguration.progress"));
      }

      defaultCodeletTmpl = SourceCodeTemplate.newFromPathAndUserExtraGaps(
         getDefaultSourceCodeTemplatePath(),
         DEFAULT_SRC_CODE_TMPL_PATH, xtraGapGetter);

      defaultConsoleOutTmpl = ConsoleOutTemplate.newFromPathAndUserExtraGaps(
         getDefaultConsoleOutTemplatePath(),
         DEFAULT_DOT_OUT_TMPL_PATH, xtraGapGetter);

      defaultSrcAndOutTmpl = SourceAndOutTemplate.newFromPathAndUserExtraGaps(
         getDefaultSourceAndOutTemplatePath(),
         DEFAULT_AND_OUT_TMPL_PATH, xtraGapGetter);

      defaultFileTextletTmpl = FileTextTemplate.newFromPathAndUserExtraGaps(
         getDefaultFileTextTemplatePath(),
         DEFAULT_FILE_TEXT_TMPL_PATH, xtraGapGetter);

      if(doDebug)  {
         debugln("      (Done--ready to load TemplateOverrides)");
      }

      wasLoaded = true;

      return  INSTANCE;
   }
   /**
      <p>Was configuration loaded?.</p>

    * @return  {@code true} If all values loaded successfully.
    * @see  #loadConfigGetInstance()
    */
   public static final boolean wasLoaded()  {
      return  wasLoaded;
   }
   /**
      <p>The path to the default template used for (source-code) {@code {@.codelet}} taglets.</p>

    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_SRC_CODE_TMPL_PATH
    */
   public static final SourceCodeTemplate getDefaultSourceCodeTemplate()  {
      return  defaultCodeletTmpl;
   }
   /**
      <p>The path to the default template used for {@code {@.codelet.out}} taglets.</p>

    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_DOT_OUT_TMPL_PATH
    */
   public static final ConsoleOutTemplate getDefaultConsoleOutTemplate()  {
      return  defaultConsoleOutTmpl;
   }
   /**
      <p>The path to the default template used for {@code {@.codelet.and.out}} taglets.</p>

    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_AND_OUT_TMPL_PATH
    */
   public static final SourceAndOutTemplate getDefaultSourceAndOutTemplate()  {
      return  defaultSrcAndOutTmpl;
   }
   /**
      <p>The path to the default template used for {@code {@.file.textlet}} taglets.</p>

    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_FILE_TEXT_TMPL_PATH
    */
   public static final FileTextTemplate getDefaultFileTextTemplate()  {
      return  defaultFileTextletTmpl;
   }
   /**
      <p>The class that defines extra gaps that can be placed in Codelet templates.</p>

    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig#USER_EXTRA_GAPS_CLASS_NAME
    */
   public static final UserExtraGapGetter getUserExtraGapsClass()  {
      return  xtraGapGetter;
   }
}
