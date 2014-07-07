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
   import  com.github.xbn.lang.ExceptionUtil;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.util.PropertiesUtil;
   import  java.io.IOException;
   import  java.net.MalformedURLException;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  java.util.Properties;
/**
   <P>Initializes Codelet by loading all configuration--This is {@linkplain TagletProcessor#TagletProcessor(CodeletInstance) triggered} by the first codelet-taglet encountered by {@code javadoc.exe}.</P>

   <P>This:<OL>
      <LI>Obtains the {@link #CODELET_CONFIG_DIR_SYS_PROP_NAME codelet_config_dir} system property as passed into the {@code javadoc} application.</LI>
      <LI>Loads {@linkplain CodeletBaseConfig base configuration} from {@link #BASE_CONFIG_FILE_NAME codelet.properties}, as exists in the configuration directory. This includes the {@linkplain #CODELET_RQD_NAMED_DBGRS_CONFIG_FILE Codelet-required} and {@linkplain #NAMED_DEBUGGERS_CONFIG_FILE user-created} named debuggers, and the external JavaDoc library {@linkplain #EXTERNAL_DOC_ROOT_URL_FILE package-lists}.</LI>
      <LI>Loads {@linkplain CodeletTemplateConfig default templates}.</LI>
      <LI>Loads the {@linkplain TemplateOverrides template-override} configuration from {@link #TMPL_OVERRIDES_CONFIG_FILE_NAME template_overrides_config}.</LI>
   </OL>This order is required to avoid <A HREF="http://en.wikipedia.org/wiki/Circular_dependency">circular dependencies</A>.</P>

   <H4>Automated {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE named debuggers}</H4>

   <P>{@code zzconfiguration.}<UL>
      <LI>{@code nameddebuglevels.listallafterload}: List all levels after being loaded.</LI>
      <LI>{@code nameddebuglevels.eachquery}: List each level when queried--regardless if on or off. Use this to determine any unused levels that may exist in the configuration file.</LI>
      <LI>{@code allvaluessummary}: List all {@linkplain CodeletBaseConfig basic configuration} values after complete.</LI>
      <LI>{@code progress}: The basic steps taken while loading all configuration.</LI>
      <LI>{@code templateoverrides.}<UL>
         <LI>{@code allentriespostloaded}: List all entries organized by JavaDoc file.</LI>
         <LI>{@code eachentryasloaded}: List all entries as they are loaded.</LI>
      </UL></LI>
   </UL></P>

   <P><B>Full names:</B></P>

<BLOCKQUOTE><PRE>zzconfiguration.nameddebuglevels.listallafterload
zzconfiguration.nameddebuglevels.eachquery
zzconfiguration.allvaluessummary
zzconfiguration.progress
zzconfiguration.templateoverrides.allentriespostloaded
zzconfiguration.templateoverrides.eachentryasloaded</PRE></BLOCKQUOTE>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
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
      <P>The name of the system property that is <A HREF="{@docRoot}/overview-summary.html#install_taglets">passed</A> into the {@code javadoc} application, whose value is the directory in which all Codelet configuration files are stored--Equal to {@code "codelet_config_dir"}. </P>

      <P>{@link #BASE_CONFIG_FILE_NAME codelet.properties}, {@link #TMPL_OVERRIDES_CONFIG_FILE_NAME template_overrides_config.txt}, and &quot;{@link #EXTERNAL_DOC_ROOT_URL_FILE external_doc_root_urls.txt}&quot; must all be in this directory.</P>

      @see  CodeletBootstrap
      @see  #getCodeletConfigDir()
    **/
   public static final String CODELET_CONFIG_DIR_SYS_PROP_NAME = "codelet_config_dir";
   /**
      <P>The name of the &quot;base&quot; configuration file--Equal to {@code "codelet.properties"}.</P>

      @see  #TMPL_OVERRIDES_CONFIG_FILE_NAME
      @see  com.github.aliteralmind.codelet.CodeletBaseConfig
    **/
   public static final String BASE_CONFIG_FILE_NAME = "codelet.properties";
   /**
      <P>The name of the configuration file containing user-created named-debugging-levels--Equal to {@code "named_debuggers_config.txt"}.</P>

      <P><I>(View <A HREF="{@docRoot}/doc-files/named_debuggers_config.txt">{@code {@docRoot}/doc-files/named_debuggers_config.txt}</A>.)</I></P>

      <P>This file contains a list of zero or more {@linkplain com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) names}, each of which is associated to a specific debugging task and {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL level}. These are in addition to those in the {@linkplain #CODELET_RQD_NAMED_DBGRS_CONFIG_FILE Codelet-required} configuration file.</P>

      <P>Named debuggers allow for very specific control over what information is logged, <I>without having to recompile code</I>. In particular, named debuggers are useful for debugging <A HREF="CustomizationInstructions.html#overview">custom customizers</A>.</P>

      <P>All possible level numbers:<UL>
<!--
   Originates in
      com.github.aliteralmind.codelet.util.NamedDebuggers
   needed by
      com.github.aliteralmind.codelet.CodeletBootstrap
   ...START
   -->
            <LI><B>{@code -1}</B>: Never output.</LI>
            <LI><B>{@code 0}</B>: Always output (even if {@link com.github.xbn.io.DebugLevel#OFF OFF}).</LI>
            <LI><B>{@code 1}</B>: Output only when the actual level {@linkplain com.github.xbn.io.DebugLevel#isOn() on}--equal to or greater than {@link com.github.xbn.io.DebugLevel#ONE ONE}.</LI>
            <LI><B>{@code 2}</B>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#TWO TWO} or greater.</LI>
            <LI><B>{@code 3}</B>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#THREE THREE} or greater.</LI>
            <LI><B>{@code 4}</B>: Output when the actual level is {@link com.github.xbn.io.DebugLevel#FOUR FOUR} or {@link com.github.xbn.io.DebugLevel#FIVE FIVE}.</LI>
            <LI><B>{@code 5}</B>: Output only when the actual level is {@code FIVE}.</LI>
<!--
   Originates in
      com.github.aliteralmind.codelet.util.NamedDebuggers
   needed by
      com.github.aliteralmind.codelet.CodeletBootstrap
   ...END
   -->
      </UL></P>

      <H4>Example</H4>

      <P>Add this line to {@code "named_debuggers_config.txt"}:</P>

<BLOCKQUOTE><PRE>super.important.debugginginformation=2</PRE></BLOCKQUOTE>

      <P>Then associate specific debugging statements to it:</P>

<BLOCKQUOTE><CODE>if({@link CodeletBaseConfig#isDebugOn(CodeletInstance, String) isDebugOn}(&quot;super.important.debugginginformation&quot;, null))  {
   debugln(&quot;Super-duper important!&quot;)
}</CODE></BLOCKQUOTE>

      <P><I>(Unless otherwise noted, all below &quot;See&quot; links are located in {@link CodeletBaseConfig}.)</I></P>

      @see  com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) NamedDebuggers#newMapFromConfigFile
      @see  CodeletBaseConfig#GLOBAL_DEBUG_LEVEL GLOBAL_DEBUG_LEVEL
      @see  CodeletBaseConfig#isDebugOn(CodeletInstance, String) isDebugOn(s,ci)
      @see  CodeletBaseConfig#getDebugApblIfOn(CodeletInstance, String) getDebugApblIfOn(ci,s)
      @see  CodeletBaseConfig#getDebugAptrIfOn(CodeletInstance, String) getDebugAptrIfOn(ci,s)
    **/
   public static final String NAMED_DEBUGGERS_CONFIG_FILE = NAMED_DBG_LVL_PREFIX + ".txt";
   /**
      <P>The name of the configuration file containing  Codelet-required named debuggers--Equal to {@code "named_debuggers_CODELET_RQD.txt"}.</P>

      <P><I>(View <A HREF="{@docRoot}/doc-files/named_debuggers_CODELET_RQD.txt">{@code {@docRoot}/doc-files/named_debuggers_CODELET_RQD.txt}</A>.)</I></P>

      <P>This file contains a list of {@linkplain com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) names}, each of which is associated to a specific debugging task and {@linkplain CodeletBaseConfig#GLOBAL_DEBUG_LEVEL level}. These are in addition to those in the {@linkplain #NAMED_DEBUGGERS_CONFIG_FILE user-created} configuration file.</P>

      <P>Changing the level numbers in this file allows you to print or suppress very specific aspects of debugging information that is <I>already built into Codelet</I>. <B>Warning:</B> Changing the <I>names</I> of any level in this file will result in an error. All Codelet-required levels are prefixed with {@code "zz"}.</P>

      <P>An example of a function taking advantage of named debuggers is
      <BR> &nbsp; &nbsp; <CODE>{@link BasicCustomizers}.{@link BasicCustomizers#lineRange(CodeletInstance, CodeletType, Integer, Boolean, String, Integer, Boolean, String, String) lineRange}</CODE>.</P>

      @see  #NAMED_DEBUGGERS_CONFIG_FILE
      @see  com.github.aliteralmind.codelet.util.NamedDebuggers#newMapFromConfigFile(Map, Iterator, String, Appendable) NamedDebuggers#newMapFromConfigFile
    **/
   public static final String CODELET_RQD_NAMED_DBGRS_CONFIG_FILE = NAMED_DBG_LVL_PREFIX + "_CODELET_RQD.txt";
   /**
      <P>The name of the file in which all <A HREF="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#externalreferencedclasses">external {@code {@docRoot}} urls</A> are listed--Equal to {@code "external_doc_root_urls.txt"}.</P>

      <P>These urls are the directories in which an external JavaDoc library's <A HREF="http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#package-list">{@code package-list}</A> file exists. Each url must end with a slash: {@code '/'}.</P>

      <P>Each {@code package-list} file is also stored locally, in a sub-directory named &quot;{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME offline_package_lists}&quot;. The offline files are automatically used when the online version is not accessible or available. These files may be manually created and, if {@linkplain CodeletBaseConfig#AUTO_UPDATE_OFFLINE_PACKAGE_LISTS configured}, are automatically updated.</P>

      <P>Each line is in the format</P>

<BLOCKQUOTE><PRE>[offline_file_name_prefix] [url]</PRE></BLOCKQUOTE>

      <P>Where<UL>
         <LI><CODE>[offline_file_name]</CODE> is the name of the locally-stored file (aside from {@code ".txt"}), which exists in a sub-directory named &quot;{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME offline_package_lists}&quot;. This is in the {@linkplain #CODELET_CONFIG_DIR_SYS_PROP_NAME same directory} as this {@code "external_doc_root_urls.txt"} file. Must only contain letters, digits, and underscores ({@code '_'}).</LI>
         <LI><CODE>[url]</CODE> is the url to the JavaDoc document root ({@code "{@docRoot}"}) of an external Java library. Must end with a slash ({@code '/'}), and must contain the library's {@code "package-list"} file.</LI>
      </UL>The file-name and url are separated with at least one space or tab.</P>

      <H4>Example file</H4>

<BLOCKQUOTE><PRE>templatefeather     http://aliteralmind.com/docs/computer/programming/templatefeather/documentation/javadoc/
xbnjava             http://aliteralmind.com/docs/computer/programming/xbnjava/documentation/javadoc/
commons_collections http://commons.apache.org/proper/commons-collections/javadocs/api-release/
commons_io          http://commons.apache.org/proper/commons-io/javadocs/api-2.4/
commons_lang        http://commons.apache.org/proper/commons-lang/javadocs/api-release/
java                http://docs.oracle.com/javase/7/docs/api/
javadoc             http://docs.oracle.com/javase/7/docs/jdk/api/javadoc/doclet/
junit               http://junit.sourceforge.net/javadoc/</PRE></BLOCKQUOTE>

      <P>If the path of {@code "external_doc_root_urls.txt"} is
      <BR> &nbsp; &nbsp; <CODE>C:\java_code\my_package\codelet_config\external_doc_root_urls.txt</CODE>
      <BR>then the path to the {@code "commons_io"} offline package-list is
      <BR> &nbsp; &nbsp; <CODE>C:\java_code\my_package\codelet_config\offline_package_lists\commons_io.txt</CODE>
      <BR>The {@code ".txt"} postfix is {@linkplain CodeletBaseConfig#PKGLIST_OFFLINE_NAME_POSTFIX customizable}.</P>

      <P><I>(View <A HREF="{@docRoot}/doc-files/external_doc_root_urls.txt">{@code {@docRoot}/doc-files/external_doc_root_urls.txt}</A>.)</I></P>

      <P>This configuration file is loaded by <CODE>{@link com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots}.{@link com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots#newFromConfigLineIterator(Iterator, String, String, int, long, RefreshOffline, IfError, Appendable, Appendable) newFromConfigLineIterator}</CODE></P>

      <P>Related (all in {@link CodeletBaseConfig}):<UL>
         <LI>{@link CodeletBaseConfig#PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS pkglist_online_attempt_sleep_mills}: The pause between each attempt.</LI>
         <LI>{@link CodeletBaseConfig#PKGLIST_ONLINE_FAILS_BEHAVIOR pkglist_if_online_retrieval_fails__warn_crash}: The behavior after all attempts fail</LI>
         <LI>{@link CodeletBaseConfig#AUTO_UPDATE_OFFLINE_PACKAGE_LISTS pkglist_auto_refresh_offline__yes_no}: After successfully retrieved, should the packages in its offline counterpart be {@linkplain com.github.aliteralmind.codelet.util.OnlineOfflineDocRoot#refreshOffline(Appendable, Appendable) refreshed}?</LI>
         <LI>{@link CodeletBaseConfig#PKGLIST_OFFLINE_NAME_POSTFIX pkglist_offline_name_postfix}: The postfix added to each offline-name in the {@linkplain CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE configuration file}.</LI>
         <LI>{@link CodeletBaseConfig#OFFLINE_PACKAGE_LIST_DIR_NAME}: The name of the sub-directory, existing in the main Codelet configuration directory, in which offline package lists are stored.</LI>
      </UL></P>

      @see  com.github.aliteralmind.codelet.alter.NewJavaDocLinkReplacerFor#getDocRootUrlToTargetClass(CodeletInstance, Class) NewJavaDocLinkReplacerFor#getDocRootUrlToTargetClass
      @see  com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots#newFromConfigLineIterator(Iterator, String, String, int, long, RefreshOffline, IfError, Appendable, Appendable) util.AllOnlineOfflineDocRoots#newFromConfigLineIterator
    **/
   public static final String EXTERNAL_DOC_ROOT_URL_FILE = "external_doc_root_urls.txt";
   /**
      <P>The name of the {@linkplain TemplateOverrides template-overrides} configuration file--Equal to {@code "template_overrides_config.txt"}.</P>

      @see  #BASE_CONFIG_FILE_NAME
      @see  com.github.aliteralmind.codelet.TemplateOverrides
    **/
   public static final String TMPL_OVERRIDES_CONFIG_FILE_NAME = "template_overrides_config.txt";
//	static final CodeletBootstrap loadConfigGetInstance() throws ClassNotFoundException, NoSuchFileException, AccessDeniedException, MalformedURLException, IOException, InterruptedException  {
   private static final void loadConfiguration() throws ClassNotFoundException, NoSuchFileException, AccessDeniedException, MalformedURLException, IOException, InterruptedException  {
      if(wasLoaded())  {
         throw  new IllegalStateException("Configuration already loaded. wasLoaded()=true");
      }

      configDir = System.getProperty(CODELET_CONFIG_DIR_SYS_PROP_NAME, null);

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
      <P>Was all Codelet configuration properly loaded?.</P>

      @return  <CODE>{@link TemplateOverrides}.{@link TemplateOverrides#wasLoaded() wasLoaded}()</CODE>
    **/
   public static final boolean wasLoaded()  {
      return  TemplateOverrides.wasLoaded();
   }
   /**
      <P>The directory in which all Codelet configuration files exist, as passed in via the {@code javadoc.exe} system property named {@code "codelet_config_dir"}.</P>

      @see  #CODELET_CONFIG_DIR_SYS_PROP_NAME
    **/
   public static final String getCodeletConfigDir()  {
      return  configDir;
   }
}
