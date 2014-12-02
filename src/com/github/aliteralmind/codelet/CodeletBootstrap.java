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
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.lang.ExceptionUtil;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.xbn.util.PropertiesUtil;
   import  java.io.IOException;
   import  java.net.MalformedURLException;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  java.util.Properties;
/**
   <p>Initializes Codelet by loading all configuration--This is {@linkplain TagletProcessor#TagletProcessor(CodeletInstance) triggered} by the first codelet-taglet encountered by {@code javadoc.exe}.</p>

   <p>This:<ol>
      <li>Obtains the {@link #CODELET_CONFIG_DIR_SYS_PROP_NAME codelet_config_dir} system property as passed into the {@code javadoc} application.</li>
      <li>Loads {@linkplain CodeletBaseConfig base configuration} from {@link #BASE_CONFIG_FILE_NAME codelet.properties}, as exists in the configuration directory. This includes the {@linkplain #CODELET_RQD_NAMED_DBGRS_CONFIG_FILE Codelet-required} and {@linkplain #NAMED_DEBUGGERS_CONFIG_FILE user-created} named debuggers, and the external JavaDoc library {@linkplain #EXTERNAL_DOC_ROOT_URL_FILE package-lists}.</li>
      <li>Loads {@linkplain CodeletTemplateConfig default templates}.</li>
      <li>Loads the {@linkplain TemplateOverrides template-override} configuration from {@link #TMPL_OVERRIDES_CONFIG_FILE_NAME template_overrides_config}.</li>
   </ol>This order is required to avoid <a href="http://en.wikipedia.org/wiki/Circular_dependency">circular dependencies</a>.</p>

   <h4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE named debuggers}</h4>

   <p>{@code zzconfiguration.}<ul>
      <li>{@code nameddebuglevels.listallafterload}: List all levels after being loaded.</li>
      <li>{@code nameddebuglevels.eachquery}: List each level when queried--regardless if on or off. Use this to determine any unused levels that may exist in the configuration file.</li>
      <li>{@code allvaluessummary}: List all {@linkplain CodeletBaseConfig basic configuration} values after complete.</li>
      <li>{@code progress}: The basic steps taken while loading all configuration.</li>
      <li>{@code templateoverrides.}<ul>
         <li>{@code allentriespostloaded}: List all entries organized by JavaDoc file.</li>
         <li>{@code eachentryasloaded}: List all entries as they are loaded.</li>
      </ul></li>
   </ul></p>

   <p><b>Full names:</b></p>

<blockquote><pre>zzconfiguration.nameddebuglevels.listallafterload
zzconfiguration.nameddebuglevels.eachquery
zzconfiguration.allvaluessummary
zzconfiguration.progress
zzconfiguration.templateoverrides.allentriespostloaded
zzconfiguration.templateoverrides.eachentryasloaded</pre></blockquote>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public enum CodeletBootstrap  {
   INSTANCE;
   static  {
      try  {
         CodeletBootstrap.loadConfiguration();
      }  catch(Exception x)  {
         throw  new ExceptionInInitializerError(
            new IllegalStateException("Attempting to load Codelet configuration.", x));
      }
   }
   private static String                configDir    ;
   private static CodeletBaseConfig     baseConfig   ;
   private static CodeletTemplateConfig tmplConfig   ;
   private static TemplateOverrides     tmplOverrides;
   private static final String NAMED_DBG_LVL_PREFIX = "named_debuggers_config";
   /**
      <p>The name of the system property that is <a href="{@docRoot}/overview-summary.html#install_taglets">passed</a> into the {@code javadoc} application, whose value is the directory in which all Codelet configuration files are stored--Equal to {@code "codelet_config_dir"}. </p>

      <p>{@link #BASE_CONFIG_FILE_NAME codelet.properties}, {@link #TMPL_OVERRIDES_CONFIG_FILE_NAME template_overrides_config.txt}, and &quot;{@link #EXTERNAL_DOC_ROOT_URL_FILE external_doc_root_urls.txt}&quot; must all be in this directory.</p>

    * @see  CodeletBootstrap
    * @see  #getCodeletConfigDir()
    */
   public static final String CODELET_CONFIG_DIR_SYS_PROP_NAME = "codelet_config_dir";
   /**
      <p>The name of the &quot;base&quot; configuration file--Equal to {@code "codelet.properties"}.</p>

    * @see  #TMPL_OVERRIDES_CONFIG_FILE_NAME
    * @see  com.github.aliteralmind.codelet.CodeletBaseConfig
    */
   public static final String BASE_CONFIG_FILE_NAME = "codelet.properties";
   /**
      <p>The name of the configuration file containing user-created named-debugging-levels--Equal to {@code "named_debuggers_config.txt"}.</p>

      <p><i>(View <a href="{@docRoot}/../${jd_project_codelet_config_dir}/named_debuggers_config.txt">{@code {@docRoot}/../${jd_project_codelet_config_dir}/named_debuggers_config.txt}</a>.)</i></p>

      <p>This file contains a list of zero or more {@linkplain com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) names}, each of which is associated to a specific debugging task and {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL level}. These are in addition to those in the {@linkplain #CODELET_RQD_NAMED_DBGRS_CONFIG_FILE Codelet-required} configuration file.</p>

      <p>Named debuggers allow for very specific control over what information is logged, <i>without having to recompile code</i>. In particular, named debuggers are useful for debugging <a href="CustomizationInstructions.html#overview">custom customizers</a>.</p>

      <p>All possible level numbers:<ul>
<!--
   Originates in
      com.github.aliteralmind.codelet.util.NamedDebuggers
   needed by
      com.github.aliteralmind.codelet.CodeletBootstrap
   ...START
   -->
            <li><b>{@code -1}</b>: Never output.</li>
            <li><b>{@code 0}</b>: Always output (even if {@link com.github.xbn.io.DebugLevel#OFF OFF}).</li>
            <li><b>{@code 1}</b>: Output only when the actual level {@linkplain com.github.xbn.io.DebugLevel#isOn() on}--equal to or greater than {@link com.github.xbn.io.DebugLevel#ONE ONE}.</li>
            <li><b>{@code 2}</b>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#TWO TWO} or greater.</li>
            <li><b>{@code 3}</b>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#THREE THREE} or greater.</li>
            <li><b>{@code 4}</b>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#FOUR FOUR} or {@link com.github.xbn.io.DebugLevel#FIVE FIVE}.</li>
            <li><b>{@code 5}</b>: Output only when the actual level is {@code FIVE}.</li>
<!--
   Originates in
      com.github.aliteralmind.codelet.util.NamedDebuggers
   needed by
      com.github.aliteralmind.codelet.CodeletBootstrap
   ...END
   -->
      </ul></p>

      <h4>Example</h4>

      <p>Add this line to {@code "named_debuggers_config.txt"}:</p>

<blockquote><pre>super.important.debugginginformation=2</pre></blockquote>

      <p>Then associate specific debugging statements to it:</p>

<blockquote><code>if({@link CodeletBaseConfig#isDebugOn(CodeletInstance, String) isDebugOn}(&quot;super.important.debugginginformation&quot;, null))  {
   debugln(&quot;Super-duper important!&quot;)
}</code></blockquote>

      <p><i>(Unless otherwise noted, all below &quot;See&quot; links are located in {@link CodeletBaseConfig}.)</i></p>

    * @see  com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) NamedDebuggers#newMapFromConfigFile
    * @see  CodeletBaseConfig#GLOBAL_DEBUG_LEVEL GLOBAL_DEBUG_LEVEL
    * @see  CodeletBaseConfig#isDebugOn(CodeletInstance, String) isDebugOn(s,ci)
    * @see  CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) getDebugApblIfOn(ci,s)
    * @see  CodeletBaseConfig#getDebugAptrIfOn(CodeletInstance, String) getDebugAptrIfOn(ci,s)
    */
   public static final String NAMED_DEBUGGERS_CONFIG_FILE = NAMED_DBG_LVL_PREFIX + ".txt";
   /**
      <p>The name of the configuration file containing  Codelet-required named debuggers--Equal to {@code "named_debuggers_config_CODELET_RQD.txt"}.</p>

      <p><i>(View <a href="{@docRoot}/../${jd_project_codelet_config_dir}/named_debuggers_config_CODELET_RQD.txt">{@code {@docRoot}/../${jd_project_codelet_config_dir}/named_debuggers_config_CODELET_RQD.txt}</a>.)</i></p>

      <p>This file contains a list of {@linkplain com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) names}, each of which is associated to a specific debugging task and {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL level}. These are in addition to those in the {@linkplain #NAMED_DEBUGGERS_CONFIG_FILE user-created} configuration file.</p>

      <p>Changing the level numbers in this file allows you to print or suppress very specific aspects of debugging information that is <i>already built into Codelet</i>. <b>Warning:</b> Changing the <i>names</i> of any level in this file will result in an error. All Codelet-required levels are prefixed with {@code "zz"}.</p>

      <p>An example of a function taking advantage of named debuggers is
      <br/> &nbsp; &nbsp; <code>{@link BasicCustomizers}.{@link BasicCustomizers#lineRange(CodeletInstance, CodeletType, Integer, Boolean, String, Integer, Boolean, String, String) lineRange}</code>.</p>

    * @see  #NAMED_DEBUGGERS_CONFIG_FILE
    * @see  com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) NamedDebuggers#newMapFromConfigFile
    */
   public static final String CODELET_RQD_NAMED_DBGRS_CONFIG_FILE = NAMED_DBG_LVL_PREFIX + "_CODELET_RQD.txt";
   /**
      <p>The name of the file in which all <a href="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#externalreferencedclasses">external {@code {@docRoot}} urls</a> are listed--Equal to {@code "external_doc_root_urls.txt"}.</p>

      <p>These urls are the directories in which an external JavaDoc library's <a href="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#package-list">{@code package-list}</a> file exists. Each url must end with a slash: {@code '/'}.</p>

      <p>Each {@code package-list} file is also stored locally, in a sub-directory named &quot;{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME offline_package_lists}&quot;. The offline files are automatically used when the online version is not accessible or available. These files may be manually created and, if {@linkplain CodeletBaseConfig#AUTO_UPDATE_OFFLINE_PACKAGE_LISTS configured}, are automatically updated.</p>

      <p>Each line is in the format</p>

<blockquote><pre>[offline_file_name_prefix] [url]</pre></blockquote>

      <p>Where<ul>
         <li><code>[offline_file_name]</code> is the name of the locally-stored file (aside from {@code ".txt"}), which exists in a sub-directory named &quot;{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME offline_package_lists}&quot;. This is in the {@linkplain #CODELET_CONFIG_DIR_SYS_PROP_NAME same directory} as this {@code "external_doc_root_urls.txt"} file. Must only contain letters, digits, and underscores ({@code '_'}).</li>
         <li><code>[url]</code> is the url to the JavaDoc document root ({@code "{@docRoot}"}) of an external Java library. Must end with a slash ({@code '/'}), and must contain the library's {@code "package-list"} file.</li>
      </ul>The file-name and url are separated with at least one space or tab.</p>

      <h4>Example file</h4>

<blockquote><pre>templatefeather     http://aliteralmind.com/docs/computer/programming/templatefeather/documentation/javadoc/
xbnjava             http://aliteralmind.com/docs/computer/programming/xbnjava/documentation/javadoc/
commons_collections http://commons.apache.org/proper/commons-collections/javadocs/api-release/
commons_io          http://commons.apache.org/proper/commons-io/javadocs/api-2.4/
commons_lang        http://commons.apache.org/proper/commons-lang/javadocs/api-release/
java                http://docs.oracle.com/javase/7/docs/api/
javadoc             http://docs.oracle.com/javase/7/docs/jdk/api/javadoc/doclet/
junit               http://junit.sourceforge.net/javadoc/</pre></blockquote>

      <p>If the path of {@code "external_doc_root_urls.txt"} is
      <br/> &nbsp; &nbsp; <code>C:\java_code\my_package\codelet_config\external_doc_root_urls.txt</code>
      <br/>then the path to the {@code "commons_io"} offline package-list is
      <br/> &nbsp; &nbsp; <code>C:\java_code\my_package\codelet_config\offline_package_lists\commons_io.txt</code>
      <br/>The {@code ".txt"} postfix is {@linkplain CodeletBaseConfig#PKGLIST_OFFLINE_NAME_POSTFIX customizable}.</p>

      <p><i>(View <a href="{@docRoot}/../${jd_project_codelet_config_dir}/external_doc_root_urls.txt">{@code {@docRoot}/../${jd_project_codelet_config_dir}/external_doc_root_urls.txt}</a>.)</i></p>

      <p>This configuration file is loaded by <code>{@link com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots}.{@link com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots#newFromConfigLineIterator(Iterator, String, String, int, long, RefreshOffline, IfError, Appendable, Appendable) newFromConfigLineIterator}</code></p>

      <p>Related (all in {@link CodeletBaseConfig}):<ul>
         <li>{@link CodeletBaseConfig#PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS pkglist_online_attempt_sleep_mills}: The pause between each attempt.</li>
         <li>{@link CodeletBaseConfig#PKGLIST_ONLINE_FAILS_BEHAVIOR pkglist_if_online_retrieval_fails__warn_crash}: The behavior after all attempts fail</li>
         <li>{@link CodeletBaseConfig#AUTO_UPDATE_OFFLINE_PACKAGE_LISTS pkglist_auto_refresh_offline__yes_no}: After successfully retrieved, should the packages in its offline counterpart be {@linkplain com.github.aliteralmind.codelet.util.OnlineOfflineDocRoot#refreshOffline(Appendable, Appendable) refreshed}?</li>
         <li>{@link CodeletBaseConfig#PKGLIST_OFFLINE_NAME_POSTFIX pkglist_offline_name_postfix}: The postfix added to each offline-name in the {@linkplain CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE configuration file}.</li>
         <li>{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME}: The name of the sub-directory, existing in the main Codelet configuration directory, in which offline package lists are stored.</li>
      </ul></p>

    * @see  com.github.aliteralmind.codelet.alter.NewJavaDocLinkReplacerFor#getDocRootUrlToTargetClass(CodeletInstance, Class) NewJavaDocLinkReplacerFor#getDocRootUrlToTargetClass
    * @see  com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots#newFromConfigLineIterator(Iterator, String, String, int, long, RefreshOffline, IfError, Appendable, Appendable) util.AllOnlineOfflineDocRoots#newFromConfigLineIterator
    */
   public static final String EXTERNAL_DOC_ROOT_URL_FILE = "external_doc_root_urls.txt";
   /**
      <p>The name of the {@linkplain TemplateOverrides template-overrides} configuration file--Equal to {@code "template_overrides_config.txt"}.</p>

    * @see  #BASE_CONFIG_FILE_NAME
    * @see  com.github.aliteralmind.codelet.TemplateOverrides
    */
   public static final String TMPL_OVERRIDES_CONFIG_FILE_NAME = "template_overrides_config.txt";
//	static final CodeletBootstrap loadConfigGetInstance() throws ClassNotFoundException, NoSuchFileException, AccessDeniedException, MalformedURLException, IOException, InterruptedException  {
   private static final void loadConfiguration() throws ClassNotFoundException, NoSuchFileException, AccessDeniedException, MalformedURLException, IOException, InterruptedException  {
      if(wasLoaded())  {
         throw  new IllegalStateException("Configuration already loaded. wasLoaded()=true");
      }

      configDir = System.getProperty(CODELET_CONFIG_DIR_SYS_PROP_NAME, null);

      //0.1.4
      CrashIfString.nullEmpty(configDir, "[System property \"" + CODELET_CONFIG_DIR_SYS_PROP_NAME + "\", which is required to be passed into javadoc.exe, via \"-J-Dcodelet_config_dir\"]", null);

      String configDirEquals = CODELET_CONFIG_DIR_SYS_PROP_NAME + "=" + configDir;
      Properties props = PropertiesUtil.
         getPropertiesFromFile(configDir + BASE_CONFIG_FILE_NAME, configDirEquals);

      Iterator<String> externalJDDocRootConfigItr = PlainTextFileUtil.
         getLineIterator(configDir + EXTERNAL_DOC_ROOT_URL_FILE,
         configDirEquals);

      Iterator<String> rqdDebugLevelConfigItr = PlainTextFileUtil.
         getLineIterator(configDir + CODELET_RQD_NAMED_DBGRS_CONFIG_FILE,
         configDirEquals);
      Iterator<String> debugLevelConfigItr = PlainTextFileUtil.
         getLineIterator(configDir + NAMED_DEBUGGERS_CONFIG_FILE,
         configDirEquals);

      baseConfig = CodeletBaseConfig.loadConfigGetInstance(props,
         configDir, externalJDDocRootConfigItr,
         rqdDebugLevelConfigItr, debugLevelConfigItr);

      tmplConfig = CodeletTemplateConfig.loadConfigGetInstance();

      Iterator<String> overridesItr = PlainTextFileUtil.
         getLineIterator(configDir + TMPL_OVERRIDES_CONFIG_FILE_NAME, configDirEquals);
      tmplOverrides = TemplateOverrides.loadConfigGetInstance(overridesItr);
   }
   /**
      <p>Was all Codelet configuration properly loaded?.</p>

    * @return  <code>{@link TemplateOverrides}.{@link TemplateOverrides#wasLoaded() wasLoaded}()</code>
    */
   public static final boolean wasLoaded()  {
      return  TemplateOverrides.wasLoaded();
   }
   /**
      <p>The directory in which all Codelet configuration files exist, as passed in via the {@code javadoc.exe} system property named {@code "codelet_config_dir"}.</p>

    * @see  #CODELET_CONFIG_DIR_SYS_PROP_NAME
    */
   public static final String getCodeletConfigDir()  {
      return  configDir;
   }
}
