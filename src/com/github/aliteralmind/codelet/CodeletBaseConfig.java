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
   import  com.github.aliteralmind.codelet.alter.DefaultAlterGetter;
   import  com.github.aliteralmind.codelet.alter.DefaultDefaultAlterGetter;
   import  com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots;
   import  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList;
   import  com.github.aliteralmind.codelet.util.NamedDebuggers;
   import  com.github.aliteralmind.codelet.util.RefreshOffline;
   import  com.github.aliteralmind.templatefeather.GapCharConfig;
   import  com.github.xbn.io.AppendOrOverwrite;
   import  com.github.xbn.io.DebugLevel;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.PathMustBe;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.Empty;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  com.github.xbn.list.MapUtil;
   import  com.github.xbn.util.IfError;
   import  com.github.xbn.util.PropertiesUtil;
   import  java.io.IOException;
   import  java.net.MalformedURLException;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.nio.file.Paths;
   import  java.util.Arrays;
   import  java.util.Collections;
   import  java.util.Iterator;
   import  java.util.List;
   import  java.util.Map;
   import  java.util.Properties;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>Immutable, static, and non-template related configuration, used throughout Codelet, as held in a file named {@link CodeletBootstrap#BASE_CONFIG_FILE_NAME codelet.properties}. Loading is executed by {@link com.github.aliteralmind.codelet.CodeletBootstrap}.</P>

   <P><I>View <A HREF="{@docRoot}/doc-files/codelet.properties">{@code {@docRoot}/doc-files/codelet.properties}</A>.)</I></P>

   <A NAME="base_dirs_other"><A/><H2><A HREF="{@docRoot}/overview-summary.html#overview_description"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; <U>Codelet: Configuration</U></H2>

   <P><I>See <A HREF="{@docRoot}/overview-summary.html#install_configure">getting started</A> in the Codelet installation instructions.</I></P>

   <P>There are four categories of Codelet global configuration:<UL>
      <LI><A HREF="#base_dirs_other">Base directories</A> and other</LI>
      <LI><A HREF="#tmpl_defaults">Default templates</A></LI>
      <LI><A HREF="#tmpl_gaps">Template gaps</A></LI>
      <LI><A HREF="#debugging">Debugging and diagnostics</A></LI>
   </UL></P>

   <A NAME="base_dirs_other"><A/><H2><A HREF="CodeletBaseConfig.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Configuration: <U>Base directories and urls, and other</U></H2>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD>&nbsp;</TD>
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #BASE_DIR_BASE_DIR base_dir_base_dir}</TD>
      <TD>For use in all other &quot;base_dir&quot; config vars, with &quot;${BASE}&quot;</TD>
   </TR><TR>
      <TD>{@linkplain #getExampleSourceBaseDir() func}</TD>
      <TD>{@link #EXAMPLE_CLASS_SRC_BASE_DIR example_class_src_base_dir}</TD>
      <TD>Directory containing the top-most package for the source code of all example classes.</TD>
   </TR><TR>
      <TD>{@linkplain #getEnclosingBaseDirList() func}</TD>
      <TD>{@link #ENCLOSING_CLASS_SRC_BASE_DIRS enclosing_class_src_base_dirs}</TD>
      <TD>Comma-separated list of all directories containing the <I>top-most package-directories</I> of all codelet-containing source files.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultAlterGetter() func}</TD>
      <TD>{@link #DEFAULT_ALTERERS_CLASS_NAME default_alterers_class_name}</TD>
      <TD>Fully-qualified name of the class that defines alterers used by all pre-built customizers, and are accessible in custom customizers.</TD>
   </TR></TABLE></P>

   <A NAME="tmpl_javadoc"><A/><H2><A HREF="CodeletBaseConfig.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Configuration: <U>JavaDoc related</U></H2>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Func</U></B></TD>
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>{@link #getTargetClassMapInitCapacity() func}</TD>
      <TD>{@link #UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY unique_jd_class_target_init_capacity}</TD>
      <TD>Initial capacity of the map holding the JavaDoc target-class shortcut translators.</TD>
   </TR><TR>
      <TD>{@link #getOnlinePackageListAttemptCount() func}</TD>
      <TD>{@link #PKGLIST_ONLINE_ATTEMPT_COUNT pkglist_online_attempts_per_url}</TD>
      <TD>How many attempts should be made to retrieve each online package-list? If zero, offline only.</TD>
   </TR><TR>
      <TD>{@link #getOnlinePackageListAttemptSleepMills() func}</TD>
      <TD>{@link #PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS pkglist_online_attempt_sleep_mills}</TD>
      <TD>How many milliseconds between each failed attempt?</TD>
   </TR><TR>
      <TD>{@link #doCrashIfOnlinePackageListFailure() func}</TD>
      <TD>{@link #PKGLIST_ONLINE_FAILS_BEHAVIOR pkglist_if_online_retrieval_fails__warn_crash}</TD>
      <TD>If all online-retrieval attempts fail, should only a warning be logged, or should Codelet stop execution? (Offline package-lists are always retrieved.)</TD>
   </TR><TR>
      <TD>{@link #doAutoUpdateOfflinePackageLists() func}</TD>
      <TD>{@link #AUTO_UPDATE_OFFLINE_PACKAGE_LISTS pkglist_auto_refresh_offline__yes_no}</TD>
      <TD>When online package-lists are retrieved, should offline package lists be refreshed (or created)?</TD>
   </TR><TR>
      <TD>{@link #getOfflinePackageListPostfix() func}</TD>
      <TD>{@link #PKGLIST_OFFLINE_NAME_POSTFIX pkglist_offline_name_postfix}</TD>
      <TD>Filename postfix, plus optional dot-extension that follows each offline name.</TD>
   </TR></TABLE></P>

   <A NAME="tmpl_defaults"><A/><H2><A HREF="CodeletBaseConfig.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Configuration: <U>Default template files</U></H2>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Func</U></B></TD>
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #DEFAULT_TPML_DIR_PREFIX default_template_directory_prefix}</TD>
      <TD>Prepended to all {@code "default_*_template_path"} values.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultSourceCodeTemplatePath() func}</TD>
      <TD>{@link #DEFAULT_SRC_CODE_TMPL_PATH default_source_codelet_template_path}</TD>
      <TD>Path to the (source-code) {@link CodeletType#SOURCE_CODE {@.codelet}} taglet's default template.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultConsoleOutTemplatePath() func}</TD>
      <TD>{@link #DEFAULT_DOT_OUT_TMPL_PATH default_dot_out_template_path}</TD>
      <TD>Path to the {@link CodeletType#CONSOLE_OUT {@.codelet.out}} taglet's default template.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultFileTextTemplatePath() func}</TD>
      <TD>{@link #DEFAULT_FILE_TEXT_TMPL_PATH default_file_textlet_template_path}</TD>
      <TD>Path to the default template used for the {@link CodeletType#FILE_TEXT {@.file.textlet}} taglet's default template.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultSourceAndOutTemplatePath() func}</TD>
      <TD>{@link #DEFAULT_AND_OUT_TMPL_PATH default_and_out_template_path}</TD>
      <TD>Path to the {@link CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglet's default template.</TD>
   </TR><TR>
      <TD>{@linkplain #getCustomTemplateDir() func}</TD>
      <TD>{@link #USER_TEMPLATE_BASE_DIR user_template_base_dir}</TD>
      <TD>Directory in which any custom (user-created) templates are stored.</TD>
   </TR></TABLE></P>

   <A NAME="tmpl_gaps"><A/><H2><A HREF="CodeletBaseConfig.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Configuration: <U>Template gaps</U></H2>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Func</U></B></TD>
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>{@linkplain #getGapCharConfig() func}</TD>
      <TD>{@link #GAP_NAME_PREFIX_CHAR gap_name_prefix_char}</TD>
      <TD>Character placed immediately before all template gap names.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #GAP_NAME_POSTFIX_CHAR gap_name_postfix_char}</TD>
      <TD>Character placed immediately after gap names.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #GAP_NAME_LITERAL_PREFIX gap_literal_prefix_char}</TD>
      <TD>Literal representation of the prefix char, for use in between texts.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #GAP_NAME_LITERAL_POSTFIX gap_literal_name_postfix_char}</TD>
      <TD>Literal representation of the postfix char, for use in between texts.</TD>
   </TR><TR>
      <TD>{@linkplain #getUserExtraGapsClassName() func}</TD>
      <TD>{@link #USER_EXTRA_GAPS_CLASS_NAME user_extra_gaps_class}</TD>
      <TD>Fully-qualified name of the class that defines extra gaps that can be placed in Codelet templates.</TD>
   </TR></TABLE></P>



   <A NAME="debugging"><A/><H2><A HREF="CodeletBaseConfig.html"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A> &nbsp; Codelet: Configuration: <U>Debugging and diagnostics</U></H2>

   <P><TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><B><U>Func</U></B></TD>
      <TD><B><U>Name</U></B></TD>
      <TD><B><U>Description</U></B></TD>
   </TR><TR>
      <TD>{@linkplain #getGlobalDebugLevel() func}</TD>
      <TD>{@link #GLOBAL_DEBUG_LEVEL global_debugging__off_12345}</TD>
      <TD>Amount that information that should be logged in every taglet-call</TD>
   </TR><TR>
      <TD>{@linkplain #getDebugAptr() func}</TD>
      <TD>{@link #DEBUG_DESTINATION debug_to__console_path}</TD>
      <TD>If debugging is on, where should it be written to?</TD>
   </TR><TR>
      <TD>{@linkplain #getBlackWhiteList() func}</TD>
      <TD>{@link #BLACK_WHITE_LIST_TYPE list_type__black_white_off}</TD>
      <TD>For ignoring all codelets in a single file, or in an entire package.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #BLACK_WHITE_LIST_CASE list_case__ignore_require_system}</TD>
      <TD>When comparing class names against the black/white list, how should case be considered?.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #BLACK_WHITE_PROPER_LIST comma_delimited_proper_list}</TD>
      <TD>List of wildcard matches that determine which files or packages should be recognized.</TD>
   </TR><TR>
      <TD>&nbsp;</TD>
      <TD>{@link #BLACK_WHITE_OVERRIDE_LIST comma_delimited_override_list}</TD>
      <TD>List of wildcard matches that trump those in the &quot;proper&quot;-list.</TD>
   </TR><TR>
      <TD>{@link #doCrashIfAlterationNotMade() func}</TD>
      <TD>{@link #ALTERATION_NOT_MADE_CRASH if_alteration_not_made_crash__yes_no}</TD>
      <TD>If a customizer attempts to make an alteration, but cannot (such as when the find-what text is not found in the example code), should a crash occur, in addition to the warning?</TD>
   </TR></TABLE></P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum CodeletBaseConfig  {
   INSTANCE;
   private static boolean       wasLoaded                 ;
   private static DebugLevel    dbgLevel                  ;
   private static TextAppenter  dbgAptr                   ;
   private static boolean       doDebugToConsole          ;
   private static GapCharConfig gapCharConfig             ;
   private static String        xmplSrcBaseDir            ;
   private static String[]      enclosingBaseDirs         ;
   private static List<String>  enclosingBaseDirList      ;
   private static DefaultAlterGetter defaultAlterGetter   ;
   private static String        customTmplDir             ;
   private static String        defaultSrcTmplPath        ;
   private static String        defaultOutTmplPath        ;
   private static String        defaultSrcAndOutTmplPath  ;
   private static String        defaultFileTextTmplPath   ;
   private static String        userExtraGapsClassNm      ;
   private static FilenameBlackWhiteList blackWhiteList   ;
   private static int           jdTgtClsMapInitCapacity   ;
   private static boolean       doCrashIfAlterNotMade     ;
   private static AllOnlineOfflineDocRoots allDocRoots    ;
   private static int           onlinePkgLstAttemptCount  ;
   private static long          onlinePkgLstAttemptSleepMills;
   private static boolean       doCrashIfOnlinePkgLstFails;
   private static boolean       doAutoUpdateOfflinePkgLsts;
   private static String        offlinePkgLstNamePost     ;
   private static NamedDebuggers namedDebugLevels       ;

   /**
      <P>Default value for {@code pkglist_online_attempts_per_url} when not provided--Equal to {@code 5}.</P>

      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final int DEFAULT_ONLINE_PKGLST_ATTEMPTS = 5;
   /**
      <P>Default value for {@code pkglist_online_attempts_per_url} when not provided--Equal to {@code 500}.</P>

      @see  #PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS
    **/
   public static final int DEFAULT_ONLINE_PKGLST_SLEEP_MILLS = 500;
   /**
      <P>Default value for {@code unique_jd_class_target_init_capacity} when not provided--Equal to {@code 100}.</P>

      @see  #UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY
    **/
   public static final int DEFAULT_JD_TARGET_CLASS_MAP_INIT_CAPACITY = 100;
   /*
      <P>Should the {@linkplain CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE offline versions} of external library {@code package-list} files be automatically updated?--Equal to {@code "pkglist_auto_refresh_offline__yes_no"}.</P>

      <P>Must be one of two values:<UL>
         <LI>{@code "yes"}: Every execution of {@code javadoc.exe} updates the offline files to equal the most recent content of their online counterparts.</LI>
         <LI>{@code "no"}: Offline files must be manually (created and) updated. If they do not exist, an error occurs.</LI>
      </UL></P>

      @see  #doAutoUpdateOfflinePackageLists()
   public static final String AUTO_UPDATE_OFFLINE_PACKAGE_LISTS = "pkglist_auto_refresh_offline__yes_no";
    */

   /**
      <P>Directory containing the top-most package for the source code of all example classes--Name is {@code "example_class_src_base_dir"}.</P>

      <P>If this is an example code</P>

      <P><CODE>com.github.smith.examples.AGoodExample</CODE></P>

      <P>and the path to its source code is</P>

      <P>{@code C:\java_code\com\github\smith\examples\AGoodExample.java}</P>

      <P>set this to</P>

      <P>{@code "C:\java_code\"}</P>

      <P>The ending file-separator is requried. This may or may not be equal to {@link #ENCLOSING_CLASS_SRC_BASE_DIRS}. And its value may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;.</P>

      @see  <CODE><A HREF="#base_dirs_other"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getExampleSourceBaseDir()
    **/
   public static final String EXAMPLE_CLASS_SRC_BASE_DIR = "example_class_src_base_dir";
   /**
      <P>For ignoring all codelets in a single file, or in an entire package--Name is {@code "list_type__black_white_off"}.</P>

      <P><I>({@linkplain #GLOBAL_DEBUG_LEVEL Debugging Codelet} in general)</I></P>

      <P><UL>
         <LI>{@code list_type__black_white_off}: Must be one of the following:<UL>
            <LI>{@code "black"}: The codelets in any files or packages that match an item in the proper-list, are ignored. Those not matched are processed.</LI>
            <LI>{@code "white"}: The codelets in any files or packages that match an item in the proper-list, are processed.</LI>
            <LI>{@code "off"}: All codelets are processed.</LI>
         </UL></LI>
         <LI>{@code list_case__ignore_require_system}: When comparing the fully-qualified name of the enclosing file (or package), should letter case be considered?<UL>
            <LI>{@code "ignore"}: Case {@linkplain org.apache.commons.io.IOCase#INSENSITIVE insensitive}</LI>
            <LI>{@code "require"}: Case ({@linkplain org.apache.commons.io.IOCase#SENSITIVE sensitive})</LI>
            <LI>{@code "system"}: Case sensitivity is determined by the ({@linkplain org.apache.commons.io.IOCase#SYSTEM operating system})</LI>
         </UL></LI>
         <LI>{@code comma_delimited_proper_list}: The list of wildcard matches that, when the fully-qualified file or package is matched, are ignored or processed (as determined by the black/white setting). Whitelist examples:<UL>
            <LI>To process all codelets in all packages: {@code "*"}</LI>
            <LI>To process codelets only in a single package: {@code "github.com.myname.text.*"}</LI>
            <LI>To process codelets in one package, plus a subset of files in another: {@code "github.com.myname.text.*,github.com.myname.io.Get*ForPath"}</LI>
         </UL>Do not put any dot-postfix after the file names (such as {@code ".java"} or {@code ".html"}). <I>For the overview summary ({@code "overview-summary.html"}), use {@code "OVERVIEW_SUMMARY"}. For package summary's ({@code "package-info.java"} or {@code "package-summary.html"}), use {@code "PACKAGE_SUMMARY"}.</I></LI>
         <LI>{@code comma_delimited_override_list}: The list of wildcard matches that trump those in the proper-list. This list is only applicable when a file or package is matched by a proper-item. Examples:<UL>
            <LI>To process all codelets except those in a single file, using a whitelist (the value of {@code list_case__ignore_require_system} is required, but left out of these examples):

<BLOCKQUOTE><PRE>list_type__black_white_off=white
comma_delimited_proper_list=fully.qualified.class.name.of.FileToProcess
comma_delimited_override_list=</PRE></BLOCKQUOTE></LI>
            <LI>Using a blacklist:
<BLOCKQUOTE><PRE>list_type__black_white_off=black
comma_delimited_proper_list=*
comma_delimited_override_list=*.FileToProcess</PRE></BLOCKQUOTE></LI>
            <LI>To process codelets only in a single package: {@code "github.com.myname.text.*"}</LI>
            <LI>To process codelets in one package, plus a subset of files in another: {@code "github.com.myname.text.*,github.com.myname.io.Get*ForPath"}</LI>
         </UL>Asterisks ({@code '*'}) indicate one-or-more of any character, and question marks ({@code '?'}) mean exactly one of any character. So, if {@code FileToProcess} is a unique filename across all project directories {@code comma_delimited_proper_list} could alternatively be
      <BR> &nbsp; &nbsp; {@code *.FileToProcess}
      <BR>If it is not unique:
      <BR> &nbsp; &nbsp; {@code *.name.of.FileToProcess}</LI>
      </UL>These values are processed by <CODE>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList FilenameBlackWhiteList}.{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#newFromConfigStringVars(String, String, String, String, String, Appendable, Appendable) newFromConfigStringVars}</CODE></P>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList
      @see  #getBlackWhiteList()
      @see  #BLACK_WHITE_LIST_CASE
      @see  #BLACK_WHITE_PROPER_LIST
      @see  #BLACK_WHITE_OVERRIDE_LIST
    **/
   public static final String BLACK_WHITE_LIST_TYPE = "list_type__black_white_off";
   /**
      <P>If a customizer attempts to make an alteration, but cannot (such as when the find-what text is not found in the example code), should a crash occur, in addition to the warning?--Name is {@code "if_alteration_not_made_crash__yes_no"}.</P>

      <P>Must be one of two values:<UL>
         <LI>{@code "no"}: Only warnings will be logged.</LI>
         <LI>{@code "yes"}: In addition to the warning, and exception is thrown.</LI>
      </UL></P>

      @see  #doCrashIfAlterationNotMade()
      @see  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum
      @see  CustomizationInstructions#getCustomizedBody(CodeletInstance, Iterator) CustomizationInstructions#getCustomizedBody
    **/
   public static final String ALTERATION_NOT_MADE_CRASH = "if_alteration_not_made_crash__yes_no";

   /**
      <P>Initial capacity of the map holding the JavaDoc target-class shortcut translators--Name is {@code "unique_jd_class_target_init_capacity"}. When not provided (set to the empty-string: {@code ""}), this defaults to {@link #DEFAULT_JD_TARGET_CLASS_MAP_INIT_CAPACITY}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum#getAllParamSigsForLinkTarget(Class)
      @see  #getTargetClassMapInitCapacity()
    **/
   public static final String UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY = "unique_jd_class_target_init_capacity";
   /**
      <P>When comparing class names against the black/white list, how should case be considered?--Name is {@code "list_case__ignore_require_system"}.</P>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #BLACK_WHITE_LIST_TYPE
    **/
   public static final String BLACK_WHITE_LIST_CASE = "list_case__ignore_require_system";
   /**
      <P>List of wildcard matches that determine which files or packages should be recognized--Name is {@code "comma_delimited_proper_list"}.</P>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #BLACK_WHITE_LIST_TYPE
    **/
   public static final String BLACK_WHITE_PROPER_LIST = "comma_delimited_proper_list";
   /**
      <P>List of wildcard matches that trump those in the &quot;proper&quot;-list--Name is {@code "comma_delimited_proper_list"}.</P>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #BLACK_WHITE_LIST_TYPE
    **/
   public static final String BLACK_WHITE_OVERRIDE_LIST = "comma_delimited_override_list";
   /**
      <P>Comma-separated list of all directories containing the <I>top-most package-directory</I> of all codelet-containing source files--Name is {@code "enclosing_class_src_base_dirs"}.</P>

      <P>If all codelet-containing source code exists in a single directory:</P>

<BLOCKQUOTE><PRE>C:\java_code\src\project_overview.html
C:\java_code\src\com\github\xbn\lang\AClass.java
C:\java_code\src\com\github\xbn\examples\lang\Demo_AClass.java
C:\java_code\src\com\github\xbn\examples\test\UnitTest_AClass.java</PRE></BLOCKQUOTE>

      <P>Then set this to {@code "C:\java_code\src\"}</P>

      <P>If there are multiple directories:</P>

<BLOCKQUOTE><PRE>C:\java_code\supplemental_files\project_overview.html
C:\java_code\src\com\github\xbn\lang\AClass.java
C:\java_code\src\com\github\xbn\examples\lang\Demo_AClass.java
C:\java_code\src\com\github\xbn\examples\test\UnitTest_AClass.java</PRE></BLOCKQUOTE>

      <P>Then use
      <BR> &nbsp; &nbsp; {@code C:\java_code\src\,C:\java_code\supplemental_files\}
      <BR>or set &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot; to {@code "C:\java_code\"}, and use
      <BR> &nbsp; &nbsp; {@code ${BASE}src\,${BASE}supplemental_files\}</P>

      <P><B>At least one directory-value is required.</B></P>

      <P>The project-overview file, as <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#overview">configured</A> into JavaDoc, must exist in one of these directories. If its path is
      <BR> &nbsp; &nbsp; {@code C:\java_code\src\project_overview.html}
      <BR>and {@code C:\java_code\src\} is its enclosing class base-directory, then its fully-qualified name as required by the {@linkplain #BLACK_WHITE_LIST_TYPE blacklist} is {@code "project_overview"}. And in the hand {@linkplain TemplateOverrides template-overrides} configuration file, it is {@code "project_overview.html"}.</P>

      <P>This value may be equal to (or contain) {@link #EXAMPLE_CLASS_SRC_BASE_DIR}.</P>

      @see  <CODE><A HREF="#base_dirs_other"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #BASE_DIR_BASE_DIR
      @see  #getEnclosingBaseDirList()
    **/
   public static final String ENCLOSING_CLASS_SRC_BASE_DIRS = "enclosing_class_src_base_dirs";
   /**
      <P>For use in all other &quot;base_dir&quot; config vars, with &quot;${BASE}&quot;--Name is {@code "base_dir_base_dir"}.</P>

      <P>Only the first use is recognized. If this variable equals
      <BR> &nbsp; &nbsp; <CODE>C:\java_code\</CODE>
      <BR>then
      <BR> &nbsp; &nbsp; <CODE>${BASE}hello${BASE}</CODE>
      <BR>is translated to
      <BR> &nbsp; &nbsp; <CODE>C:\java_code\hello${BASE}</CODE></P>

      @see  <CODE><A HREF="#base_dirs_other"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #ENCLOSING_CLASS_SRC_BASE_DIRS
    **/
   public static final String BASE_DIR_BASE_DIR = "base_dir_base_dir";
   /**
      <P>Name of the sub-directory in which the offline versions of external library {@code "package-list"} files are stored--Equal to {@code "offline_package_lists"}.</P>

      <P>This is located in the Codelet {@linkplain CodeletBootstrap#getCodeletConfigDir() configuration directory}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE
      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final String OFFLINE_PACKAGE_LIST_DIR_NAME = "offline_package_lists";
   /**
      <P>How many attempts should be made to retrieve each online package-list? If zero, offline only--Name is {@code "pkglist_online_attempts_per_url"}.</P>

      <P>Must be an integer zero or greater. If not provided (equal to the empty-string: {@code ""}), this defaults to {@link #DEFAULT_ONLINE_PKGLST_ATTEMPTS}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getAllJavaDocRoots()
      @see  #getOnlinePackageListAttemptCount()
    **/
   public static final String PKGLIST_ONLINE_ATTEMPT_COUNT = "pkglist_online_attempts_per_url";
   /**
      <P>How many milliseconds between each failed attempt?--Name is {@code "pkglist_online_attempt_sleep_mills"}.</P>

      <P>Must be an integer zero or greater. If not provided (equal to the empty-string: {@code ""}), thi defaults to {@link #DEFAULT_ONLINE_PKGLST_SLEEP_MILLS}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getOnlinePackageListAttemptSleepMills()
      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final String PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS = "pkglist_online_attempt_sleep_mills";
   /**
      <P>If all online-retrieval attempts fail, should only a warning be logged, or should Codelet stop execution? (Offline package-lists are always retrieved)--Name is {@code "pkglist_if_online_retrieval_fails__warn_crash"}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #doCrashIfOnlinePackageListFailure()
      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final String PKGLIST_ONLINE_FAILS_BEHAVIOR = "pkglist_if_online_retrieval_fails__warn_crash";
   /**
      <P>When online package-lists are retrieved, should offline package lists be refreshed (or created)?--Name is {@code "pkglist_auto_refresh_offline__yes_no"}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #doAutoUpdateOfflinePackageLists()
      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final String AUTO_UPDATE_OFFLINE_PACKAGE_LISTS = "pkglist_auto_refresh_offline__yes_no";
   /**
      <P>Filename postfix, plus optional dot-extension that follows each offline name.--Name is {@code "pkglist_offline_name_postfix"}.</P>

      @see  <CODE><A HREF="#tmpl_javadoc"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getOfflinePackageListPostfix()
      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final String PKGLIST_OFFLINE_NAME_POSTFIX = "pkglist_offline_name_postfix";
   /**
      <P>Amount that information that should be logged in every taglet-call--Name is {@code "global_debugging__off_12345"}.</P>

      <P>This {@linkplain com.github.xbn.io.DebugLevel#getFromStringOff12345(String, String) must be equal to}<UL>
         <LI>{@code "off"}: No automated debugging.</LI>
         <LI>A digit between one and five: An arbitrary number representing the amount of debugging information that will be output.</LI>
      </UL></P>

      <P>This is the {@linkplain #getGlobalDebugLevel() global debugging level} used by all taglets. An individual taglet may override this by prepending "{@link CodeletInstance#DEBUG_LEVEL_PREFIX_PREFIX [DEBUG_LEVEL_#]}" to its text. If the {@linkplain CodeletInstance#getDebugLevel() taglet's level} is lower than or equal to the global level, it is ignored.</P>

      <P>For very specific control over what is output <I>without having to recompile code</I>, create a &quot;{@link CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE named debugger}&quot;, each of which has a  level-number associated to it. If the actual (global or taglet) debugging level is equal-to-or-higher, it's information is output.</P>

      <H4>What is logged</H4>

      <P>The examples in this section assumes that {@code CodeletBaseConfig} is <A HREF="http://docs.oracle.com/javase/8/docs/technotes/guides/language/static-import.html">statically imported</A> &quot;en masse&quot;:
      <BR> &nbsp; &nbsp; <CODE>import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;</CODE></P>

      <P>To output something even if the debugging level is {@code "off"}:</P>

<BLOCKQUOTE><CODE>{@link #debugln(Object) debugln}(&quot;Important information&quot;);</CODE></BLOCKQUOTE>

      <P> To output only when global debugging is on (level one or greater):</P>

<BLOCKQUOTE><CODE>if({@link #isDebugOn(CodeletInstance) isDebugOn}(null))  {
   debugln(&quot;Important information&quot;)
}</CODE></BLOCKQUOTE>

      <P>To output when global debugging is at least level three:</P>

<BLOCKQUOTE><CODE>if({@link #isDebugOn(int, CodeletInstance) isDebugOn}(3, null))  {
   debugln(&quot;Important information&quot;)
}</CODE></BLOCKQUOTE>

      <P>To output when global or taglet debugging is at least three:</P>

<BLOCKQUOTE><CODE>if({@link #isDebugOn(int, CodeletInstance) isDebugOn}(3, <I>[the-taglet-instance]</I>))  {
   debugln(&quot;Important information&quot;)
}</CODE></BLOCKQUOTE>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #DEBUG_DESTINATION
      @see  #getGlobalDebugLevel()
      @see  <A HREF="{@docRoot}/overview-summary.html#xmpl_links_debug">Overview JavaDoc link example</A>
    **/
   public static final String GLOBAL_DEBUG_LEVEL = "global_debugging__off_12345";
   /**
      <P>When debugging is on, where should output be written to?--Name is {@code "debug_to__console_path"}.</P>

      <P>This must be one of two values:<UL>
         <LI>{@code "console"}: Sets {@link #getDebugAptr() getDebugAptr}{@code ()} to <CODE>{@link com.github.xbn.io.TextAppenter TextAppenter}.{@link com.github.xbn.io.TextAppenter#CONSOLE CONSOLE}</CODE></LI>
         <LI>The path to a writable file: Sets {@code getDebugAptr()} to
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.io.NewTextAppenterFor NewTextAppenterFor}.{@link com.github.xbn.io.NewTextAppenterFor#file(String, AppendOrOverwrite) file}(dbgAptrStr, {@link com.github.xbn.io.AppendOrOverwrite AppendOrOverwrite}.{@link com.github.xbn.io.AppendOrOverwrite#APPEND APPEND})</CODE></LI>
      </UL>In order to allow debugging at any time, even when the {@linkplain #GLOBAL_DEBUG_LEVEL debugging level} is set to off, this value is required.</P>

      @see  <CODE><A HREF="#debugging"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getDebugAptr()
    **/
   public static final String DEBUG_DESTINATION = "debug_to__console_path";
   /**
      <P>Prepended to all {@code "default_*_template_path"} values--Name is {@code "default_template_directory_prefix"}.</P>

      <P>This value may be the empty string ({@code ""}), and may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;</P>

      <P>All default template paths are required, must exist and be readable, and must refer to a valid Codelet template of its type. They may be in any of the following locations, which are searched for in order:<OL>
         <LI>A file name, which exists in the same directory as this configuration file.</LI>
         <LI>A relative path, which exists off of the directory from which the Java Virtual Machine was invoked.</LI>
         <LI>An absolute path.</LI>
      </OL>

      <P>View the locally installed templates:<UL>
         <LI><A HREF="{@docRoot}/doc-files/default_templates/source_code.txt">{@code {@.codelet}}</A></LI>
         <LI><A HREF="{@docRoot}/doc-files/default_templates/console_out.txt">{@code {@.codelet.out}}</A></LI>
         <LI><A HREF="{@docRoot}/doc-files/default_templates/src_and_out.txt">{@code {@.codelet.and.out}}</A></LI>
         <LI><A HREF="{@docRoot}/doc-files/default_templates/file_text.txt">{@code {@.file.textlet}}</A></LI>
      </UL>These links assume the default template directory is &quot;{@code {@docRoot}/doc-files/default_templates/}&quot;</P>

      @see  <CODE><A HREF="#tmpl_defaults"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_SRC_CODE_TMPL_PATH
      @see  #DEFAULT_DOT_OUT_TMPL_PATH
      @see  #DEFAULT_FILE_TEXT_TMPL_PATH
      @see  #DEFAULT_AND_OUT_TMPL_PATH
      @see  #USER_EXTRA_GAPS_CLASS_NAME
      @see  #USER_TEMPLATE_BASE_DIR
    **/
   public static final String DEFAULT_TPML_DIR_PREFIX = "default_template_directory_prefix";
   /**
      <P>Path to the default {@linkplain type.SourceCodeTemplate template} used for (source-code) {@link CodeletType#SOURCE_CODE {@.codelet}} taglets--Name is {@code "default_source_codelet_template_path"}.</P>

      @see  <CODE><A HREF="#tmpl_defaults"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_TPML_DIR_PREFIX
      @see  #getDefaultSourceCodeTemplatePath()
    **/
   public static final String DEFAULT_SRC_CODE_TMPL_PATH = "default_source_codelet_template_path";
   /**
      <P>Path to the default {@linkplain type.ConsoleOutTemplate template} used for {@link CodeletType#CONSOLE_OUT {@.codelet.out}} taglets--Name is {@code "default_dot_out_template_path"}.</P>

      @see  <CODE><A HREF="#tmpl_defaults"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_TPML_DIR_PREFIX
      @see  #getDefaultConsoleOutTemplatePath()
    **/
   public static final String DEFAULT_DOT_OUT_TMPL_PATH = "default_dot_out_template_path";
   /**
      <P>Path to the default {@linkplain type.SourceAndOutTemplate template} used for {@link CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets--Name is {@code "default_and_out_template_path"}.</P>

      @see  <CODE><A HREF="#tmpl_defaults"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_TPML_DIR_PREFIX
      @see  #getDefaultSourceAndOutTemplatePath()
    **/
   public static final String DEFAULT_AND_OUT_TMPL_PATH = "default_and_out_template_path";
   /**
      <P>Path to the default {@linkplain type.FileTextTemplate template} used for {@link CodeletType#FILE_TEXT {@.file.textlet}} taglets--Name is {@code "default_file_textlet_template_path"}.</P>

      @see  <CODE><A HREF="#tmpl_defaults"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_TPML_DIR_PREFIX
      @see  #getDefaultFileTextTemplatePath()
    **/
   public static final String DEFAULT_FILE_TEXT_TMPL_PATH = "default_file_textlet_template_path";
   /**
      <P>Fully-qualified name of the class that defines {@linkplain CustomizationInstructions#orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) alterers} used by all {@linkplain BasicCustomizers pre-built customizers}, and are accessible in <A HREF="CustomizationInstructions.html#overview">custom customizers</A>--Name is {@code "default_alterers_class_name"}.</P>

      <P>When provided, the class it represents must {@linkplain java.lang.Class#forName(String) exist}, implement {@link com.github.aliteralmind.codelet.alter.DefaultAlterGetter}, and have a no-parameter constructor. When not provided (equal to the empty-string {@code ""}), {@link DefaultDefaultAlterGetter} is used.</P>

      @see  <CODE><A HREF="#base_dirs_other"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></A></CODE>
      @see  #getDefaultAlterGetter()
    **/
   public static final String DEFAULT_ALTERERS_CLASS_NAME = "default_alterers_class_name";

   /**
      <P>Character placed immediately before gap names--Name is {@code "gap_name_prefix_char"}.</P>

      <P>If not provided, defaults to <CODE>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_PREFIX_CHAR DEFAULT_PREFIX_CHAR}</CODE></P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #GAP_NAME_POSTFIX_CHAR
      @see  #GAP_NAME_LITERAL_PREFIX
      @see  #getGapCharConfig()
    **/
   public static final String GAP_NAME_PREFIX_CHAR = "gap_name_prefix_char";
   /**
      <P>Character placed immediately after gap names--Name is {@code "gap_name_postfix_char"}.</P>

      <P>If not provided, defaults to <CODE>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_POSTFIX_CHAR DEFAULT_PREFIX_CHAR}</CODE></P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #GAP_NAME_PREFIX_CHAR
      @see  #GAP_NAME_LITERAL_POSTFIX
      @see  #getGapCharConfig()
    **/
   public static final String GAP_NAME_POSTFIX_CHAR = "gap_name_postfix_char";
   /**
      <P>Literal representation of the prefix char, for use in between texts--Name is {@code "gap_literal_prefix_char"}.</P>

      <P>If not provided, defaults to <CODE>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_LITERAL_PREFIX DEFAULT_LITERAL_PREFIX}</CODE></P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #GAP_NAME_PREFIX_CHAR
      @see  #GAP_NAME_LITERAL_POSTFIX
      @see  #getGapCharConfig()
    **/
   public static final String GAP_NAME_LITERAL_PREFIX = "gap_literal_prefix_char";
   /**
      <P>Literal representation of the postfix char, for use in between texts--Name is {@code "gap_literal_name_postfix_char"}.</P>

      <P>If not provided, defaults to <CODE>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_LITERAL_PREFIX DEFAULT_LITERAL_PREFIX}</CODE></P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #GAP_NAME_POSTFIX_CHAR
      @see  #GAP_NAME_LITERAL_PREFIX
      @see  #getGapCharConfig()
    **/
   public static final String GAP_NAME_LITERAL_POSTFIX = "gap_literal_name_postfix_char";
   /**
      <P>Fully-qualified name of the class that defines extra gaps that can be placed in Codelet templates--Name is {@code "user_extra_gaps_class"}.</P>

      <P>If there are no extra gaps, set this to the empty-string. Otherwise, the class it represents must {@linkplain java.lang.Class#forName(String) exist}, implement {@link UserExtraGapGetter}, and have a no-parameter constructor.</P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #DEFAULT_TPML_DIR_PREFIX
      @see  #getUserExtraGapsClassName()
    **/
   public static final String USER_EXTRA_GAPS_CLASS_NAME = "user_extra_gaps_class";
   /**
      <P>Directory in which any custom (user-created) templates are stored--Name is {@code "user_template_base_dir"}.</P>

      <P>This is optional and not validated in any way. Its value may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;.</P>

      @see  <CODE><A HREF="#tmpl_gaps"><IMG SRC="{@docRoot}/resources/up_arrow.gif"/></A></CODE>
      @see  #getCustomTemplateDir()
    **/
   public static final String USER_TEMPLATE_BASE_DIR = "user_template_base_dir";
   /**
      <P>Load configuration and get the instance. Call only once.</P>

      @param  props  May not be {@code null}, and must be a valid {@linkplain CodeletBaseConfig Codelet properties} file.
      @return  #INSTANCE
      @exception  IllegalStateException  If configuration was already loaded.
      @see  com.github.xbn.util.PropertiesUtil
    **/
   static final CodeletBaseConfig loadConfigGetInstance(Properties props, String config_baseDir, Iterator<String> externalJDDocRoot_configLineItr, Iterator<String> rqdDbgLevels_configLineItr, Iterator<String> debugLevels_configLineItr) throws NoSuchFileException, AccessDeniedException, MalformedURLException, IOException, InterruptedException  {
      if(wasLoaded())  {
         throw  new IllegalStateException("wasLoaded() is true.");
      }
      String dbgLvlStr = null;
      try  {
         dbgLvlStr = props.getProperty(GLOBAL_DEBUG_LEVEL, "");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(props, "props", null, rx);
      }

      dbgLevel = DebugLevel.getFromStringOff12345(dbgLvlStr, GLOBAL_DEBUG_LEVEL);

      String dbgAptrStr = props.getProperty(DEBUG_DESTINATION, "");
      if(dbgAptrStr.equals("console"))  {
         dbgAptr =  TextAppenter.CONSOLE;
         doDebugToConsole = true;
      }  else  {
         dbgAptr = NewTextAppenterFor.file(dbgAptrStr, AppendOrOverwrite.APPEND);
         doDebugToConsole = false;
      }

      if(dbgLevel.isOn())  {
         debugln("Loading Codelet configuration");
         debugln("   Loading base config");
         debugln("      Loading debug level config file...");
      }

      //Named debuggers cannet be used until the named-level map is created!

      int namedDebugLevelsRqd = 0;
      Map<String,DebugLevel> rqdDbgLevelNameMap = NamedDebuggers.
         newMapFromConfigFile(null, rqdDbgLevels_configLineItr,
         "rqdDbgLevels_configLineItr",
         null);    //debug
      namedDebugLevelsRqd = rqdDbgLevelNameMap.size();

      namedDebugLevels = new NamedDebuggers(rqdDbgLevelNameMap,
         debugLevels_configLineItr,
         "rqdDbgLevelNameMap",
         null);    //debug

      //NOW named debuggers can be used.

      if(isDebugOn(null, "zzconfiguration.nameddebuglevels.listallafterload"))  {
         debugln("All named debuggers:" + MapUtil.toString(namedDebugLevels.getMap(), null));
      }

      namedDebugLevels.setAllQueriesDebug(
         getDebugApblIfOn(null, "zzconfiguration.nameddebuglevels.eachquery"));

      String baseDirBaseRplcmntQuoted = Matcher.quoteReplacement(props.getProperty(BASE_DIR_BASE_DIR, ""));

      xmplSrcBaseDir = get1stDollarBASERplcd(
         props.getProperty(EXAMPLE_CLASS_SRC_BASE_DIR, ""),
         baseDirBaseRplcmntQuoted);
      String enclosingBaseDirPropVal = props.getProperty(ENCLOSING_CLASS_SRC_BASE_DIRS, "");
      enclosingBaseDirs = null;
      if(enclosingBaseDirPropVal.length() == 0)  {
         throw  new IllegalArgumentException("No values for " + ENCLOSING_CLASS_SRC_BASE_DIRS + ". At least one required.");
      }  else  {
         enclosingBaseDirs = enclosingBaseDirPropVal.split(",");
         for(int i = 0; i < enclosingBaseDirs.length; i++)  {
            enclosingBaseDirs[i] = get1stDollarBASERplcd(
               enclosingBaseDirs[i], baseDirBaseRplcmntQuoted);
            new PathMustBe().existing().readable().
               getOrCrashIfBad(enclosingBaseDirs[i],
               "Element " + i + " in " + ENCLOSING_CLASS_SRC_BASE_DIRS);
         }
      }
      enclosingBaseDirList = Collections.unmodifiableList(Arrays.asList(enclosingBaseDirs));

      char gapNamePreChar = PropertiesUtil.getWithEmptyDefault(props, GAP_NAME_PREFIX_CHAR, Empty.OK, GapCharConfig.DEFAULT_PREFIX_CHAR);
      char gapNamePostChar = PropertiesUtil.getWithEmptyDefault(props, GAP_NAME_POSTFIX_CHAR, Empty.OK, GapCharConfig.DEFAULT_POSTFIX_CHAR);
      String gapLiteralPre = props.getProperty(GAP_NAME_LITERAL_PREFIX, GapCharConfig.DEFAULT_LITERAL_PREFIX);
      String gapLiteralPost = props.getProperty(GAP_NAME_LITERAL_POSTFIX, GapCharConfig.DEFAULT_LITERAL_POSTFIX);

      gapCharConfig = new GapCharConfig(gapNamePreChar, gapNamePostChar, gapLiteralPre, gapLiteralPost);

 		String defaultAlterGetterClsNm = props.getProperty(DEFAULT_ALTERERS_CLASS_NAME, "");
      if(defaultAlterGetterClsNm.length() == 0)  {
         defaultAlterGetter = new DefaultDefaultAlterGetter();
         if(isDebugOn(null, "zzconfiguration.progress"))  {
            debugln("      No default-alter-getter class name provided. Using DefaultDefaultAlterGetter");
         }
      }  else  {
         defaultAlterGetter = ReflectRtxUtil.<DefaultAlterGetter>getNewInstanceFromNoParamCnstr(
            defaultAlterGetterClsNm, DefaultAlterGetter.class,
            getDebugApblIfOn(null, "zzconfiguration.progress"));
      }

      String tmplBaseDir = props.getProperty(DEFAULT_TPML_DIR_PREFIX, "");
      defaultSrcTmplPath = get1stDollarBASERplcd(
         tmplBaseDir + props.getProperty(DEFAULT_SRC_CODE_TMPL_PATH, ""),
         baseDirBaseRplcmntQuoted);
      defaultOutTmplPath = get1stDollarBASERplcd(
         tmplBaseDir + props.getProperty(DEFAULT_DOT_OUT_TMPL_PATH, ""),
         baseDirBaseRplcmntQuoted);
      defaultSrcAndOutTmplPath = get1stDollarBASERplcd(
         tmplBaseDir + props.getProperty(DEFAULT_AND_OUT_TMPL_PATH, ""),
         baseDirBaseRplcmntQuoted);
      defaultFileTextTmplPath = get1stDollarBASERplcd(
         tmplBaseDir + props.getProperty(DEFAULT_FILE_TEXT_TMPL_PATH, ""),
         baseDirBaseRplcmntQuoted);

      userExtraGapsClassNm = props.getProperty(USER_EXTRA_GAPS_CLASS_NAME, "");

      customTmplDir = get1stDollarBASERplcd(
         props.getProperty(USER_TEMPLATE_BASE_DIR, ""),
         baseDirBaseRplcmntQuoted);

      blackWhiteList = FilenameBlackWhiteList.newFromProperties(props, ",", BLACK_WHITE_LIST_TYPE,
         BLACK_WHITE_LIST_CASE, BLACK_WHITE_PROPER_LIST, BLACK_WHITE_OVERRIDE_LIST,
         getDebugApblIfOn(null, "zzconfiguration.blackwhitelist.loading"),
         getDebugApblIfOn(null, "zzconfiguration.blackwhitelist.usage"));

      jdTgtClsMapInitCapacity = PropertiesUtil.getWithEmptyDefault(props,
         UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY, Empty.OK,
         DEFAULT_JD_TARGET_CLASS_MAP_INIT_CAPACITY);

      doCrashIfAlterNotMade = PropertiesUtil.getWithEmptyDefault(props,
         ALTERATION_NOT_MADE_CRASH, "yes", "no", Empty.BAD, false);

      onlinePkgLstAttemptCount = PropertiesUtil.getWithEmptyDefault(props,
         PKGLIST_ONLINE_ATTEMPT_COUNT, Empty.OK, DEFAULT_ONLINE_PKGLST_ATTEMPTS);
      onlinePkgLstAttemptSleepMills = PropertiesUtil.getWithEmptyDefault(props,
         PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS, Empty.OK,
         DEFAULT_ONLINE_PKGLST_SLEEP_MILLS);
      doCrashIfOnlinePkgLstFails = PropertiesUtil.getWithEmptyDefault(props,
         PKGLIST_ONLINE_FAILS_BEHAVIOR, "crash", "warn", Empty.BAD, false);
      doAutoUpdateOfflinePkgLsts = PropertiesUtil.getWithEmptyDefault(props,
         AUTO_UPDATE_OFFLINE_PACKAGE_LISTS, "yes", "no", Empty.BAD, false);
      offlinePkgLstNamePost = props.getProperty(PKGLIST_OFFLINE_NAME_POSTFIX, "");

      allDocRoots = AllOnlineOfflineDocRoots.newFromConfigLineIterator(
         externalJDDocRoot_configLineItr,
         config_baseDir + OFFLINE_PACKAGE_LIST_DIR_NAME + FILE_SEP,
            offlinePkgLstNamePost,
         onlinePkgLstAttemptCount, onlinePkgLstAttemptSleepMills,
         RefreshOffline.getForBoolean(doAutoUpdateOfflinePkgLsts),
         IfError.getCRASHIfTrue(doCrashIfOnlinePkgLstFails),
         getDebugApblIfOn(null, "zzconfiguration.progress"),
         getDebugAptr().getAppendable());  //Required!!

      if(isDebugOn(null, "zzconfiguration.allvaluessummary"))  {
         debugln("   Codelet base-configuration loaded:");
         debugln("      - " + EXAMPLE_CLASS_SRC_BASE_DIR + ": \"" + xmplSrcBaseDir + "\"");
         debugln("      - " + ENCLOSING_CLASS_SRC_BASE_DIRS + ": \"" + Arrays.toString(enclosingBaseDirList.toArray()) + "\"");
         debugln("      - " + DEFAULT_ALTERERS_CLASS_NAME + ": " +
            ((defaultAlterGetter == null) ? null : defaultAlterGetter.getClass().getName()));
         debugln("      Templates:");
         debugln("        - Gap-name character configuration: " + gapCharConfig);
         debugln("        - " + USER_EXTRA_GAPS_CLASS_NAME + ": \"" + userExtraGapsClassNm + "\"");
         debugln("        - " + DEFAULT_TPML_DIR_PREFIX + ": \"" + tmplBaseDir + "\"");
         debugln("        - " + DEFAULT_SRC_CODE_TMPL_PATH + ": \"" + defaultSrcTmplPath + "\"");
         debugln("        - " + DEFAULT_DOT_OUT_TMPL_PATH + ": \"" + defaultOutTmplPath + "\"");
         debugln("        - " + DEFAULT_AND_OUT_TMPL_PATH + ": \"" + defaultSrcAndOutTmplPath + "\"");
         debugln("        - " + DEFAULT_FILE_TEXT_TMPL_PATH + ": \"" + defaultFileTextTmplPath + "\"");
         debugln("        - " + USER_TEMPLATE_BASE_DIR + ": \"" + customTmplDir + "\"");
         debugln("      JavaDoc:");
         debugln("        - " + UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY + ": " + jdTgtClsMapInitCapacity);
         debugln("        - " + PKGLIST_ONLINE_ATTEMPT_COUNT + ": " + onlinePkgLstAttemptCount);
         debugln("        - " + PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS + ": " + onlinePkgLstAttemptSleepMills);
         debugln("        - doCrashIfOnlinePackageListFailure(): " + doCrashIfOnlinePkgLstFails);
         debugln("        - doAutoUpdateOfflinePackageLists(): " + doAutoUpdateOfflinePkgLsts);
         debugln("        - doAutoUpdateOfflinePackageLists(): " + doAutoUpdateOfflinePkgLsts);
         debugln("        - doCrashIfAlterNotMade(): " + doCrashIfAlterNotMade);
         debugln("        - " + PKGLIST_OFFLINE_NAME_POSTFIX + ": \"" + offlinePkgLstNamePost + "\"");
         debugln("        - getAllJavaDocRoots(): " + allDocRoots);
         debugln("      Debugging:");
         debugln("        - " + GLOBAL_DEBUG_LEVEL + ": " + dbgLevel);
         debugln("        - " + DEBUG_DESTINATION + ": " + dbgAptr);
         debugln("        - Black/white list: " + blackWhiteList);
         debugln("        - Named debuggers: " + namedDebugLevels.getMap().size() + " (" + namedDebugLevelsRqd + " required)");
         debugln("   (done--ready to load CodeletTemplateConfig)");
      }

      wasLoaded = true;

      return  INSTANCE;
   }
      private static final String get1stDollarBASERplcd(String maybe_containsDollarBASE, String baseDirBase_rplcmntQuoted)  {
         return  dollarSignBASEMtchr.reset(maybe_containsDollarBASE).
            replaceFirst(baseDirBase_rplcmntQuoted);
      }
      private static final Matcher dollarSignBASEMtchr = Pattern.compile("${BASE}", Pattern.LITERAL).matcher("");

   /**
      <P>Was configuration loaded?.</P>

      @return  {@code true} If all values loaded successfully.
    **/
   public static final boolean wasLoaded()  {
      return  wasLoaded;
   }
   /**
      <P>Path to the default template used for (source-code) {@code {@.codelet}} taglets.</P>

      @see  #DEFAULT_SRC_CODE_TMPL_PATH
    **/
   public static final String getDefaultSourceCodeTemplatePath()  {
      return  defaultSrcTmplPath;
   }
   /**
      <P>Path to the default template used for {@code {@.codelet.out}} taglets.</P>

      @see  #DEFAULT_DOT_OUT_TMPL_PATH
    **/
   public static final String getDefaultConsoleOutTemplatePath()  {
      return  defaultOutTmplPath;
   }
   /**
      <P>Path to the default template used for {@code {@.codelet.and.out}} taglets.</P>

      @see  #DEFAULT_AND_OUT_TMPL_PATH
    **/
   public static final String getDefaultSourceAndOutTemplatePath()  {
      return  defaultSrcAndOutTmplPath;
   }
   /**
      <P>Path to the default template used for {@code {@.file.textlet}} taglets.</P>

      @see  #DEFAULT_FILE_TEXT_TMPL_PATH
    **/
   public static final String getDefaultFileTextTemplatePath()  {
      return  defaultFileTextTmplPath;
   }
   /**
      <P>Write a message to the console and, if debugging is both on and <I>not</I> to the console, writes it via the debug outputter as well.</P>

      <P>This<OL>
         <LI>Calls <CODE>System.out.println(message);</CODE></LI>
         <LI>If debugging is both {@linkplain #isDebugOn(CodeletInstance) on} and <I>not</I> {@linkplain #doDebugToConsole() to the console}, this also calls
         <BR> &nbsp; &nbsp; {@link #debugln(Object) debugln}{@code (message)}</LI>
      </OL></P>

      @param  message  <I>Should</I> not be {@code null} or empty.
      @see  #debugAndToConsole(CodeletInstance, Object)
    **/
   public static final void debuglnAndToConsole(CodeletInstance instance, Object message)  {
      System.out.println(message);
      if(isDebugOn(instance)  &&  !doDebugToConsole())  {
         debugln(message);
      }
   }
   /**
      <P>Write a message to the console and, if debugging is both on and <I>not</I> to the console, writes it via the debug outputter as well.</P>

      <P>This<OL>
         <LI>Calls <CODE>System.out.print(message);</CODE></LI>
         <LI>If debugging is both {@linkplain #isDebugOn(CodeletInstance) on} and <I>not</I> {@linkplain #doDebugToConsole() to the console}, this also calls
         <BR> &nbsp; &nbsp; {@link #debug(Object) debug}{@code (message)}</LI>
      </OL></P>

      @param  message  <I>Should</I> not be {@code null} or empty.
      @see  #debuglnAndToConsole(CodeletInstance, Object)
    **/
   public static final void debugAndToConsole(CodeletInstance instance, Object message)  {
      System.out.print(message);
      if(isDebugOn(instance)  &&  !doDebugToConsole())  {
         debug(message);
      }
   }
   /**
      <P>Write a message to the debugging output.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#appent(Object) appent}(message)</CODE></P>

      @see  #debugln(Object)
    **/
   public static final void debug(Object message)  {
      getDebugAptr().appent(message);
   }
   /**
      <P>Write a message to the debugging output.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#appentln(Object) appentln}(message)</CODE></P>

      @see  #debug(Object)
    **/
   public static final void debugln(Object message)  {
      getDebugAptr().appentln(message);
   }
   /**
      <P>Is debugging output being written to the console?. This is used only to avoid {@linkplain #debuglnAndToConsole(CodeletInstance, Object) duplicate messages} when {@linkplain #isDebugOn(CodeletInstance) debugging is on}.</P>

      @see  #getGlobalDebugLevel()
    **/
   public static final boolean doDebugToConsole()  {
      return  doDebugToConsole;
   }
   /**
      <P>The amount of debugging, beyond ......logging......, that is written in every taglet call.</P>

      @see  #GLOBAL_DEBUG_LEVEL
      @see  #isDebugOn(CodeletInstance) isDebugOn(ci)
      @see  #isDebugOn(CodeletInstance, String) isDebugOn(ci,s)
      @see  #getDebugAptr()
      @see  #doDebugToConsole()
      @see  #debuglnAndToConsole(CodeletInstance, Object) log
    **/
   public static final DebugLevel getGlobalDebugLevel()  {
      return  dbgLevel;
   }
   /**
      <P>Is debugging on, either globally or for a single taglet?.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>{@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#isThisOrAnyOn(DebugLevel...) isThisOrAnyOn}(instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}())</CODE>
      @see  #isDebugOn(CodeletInstance, String)
      @see  #isDebugOn(int, CodeletInstance)
    **/
   public static final boolean isDebugOn(CodeletInstance instance)  {
      return  getGlobalDebugLevel().isThisOrAnyOn(getInstanceDbgLevelOrNull(instance));
   }
      private static final DebugLevel getInstanceDbgLevelOrNull(CodeletInstance instance)  {
         return  ((instance == null) ? null
            :  instance.getDebugLevel());
      }
   /**
      <P>Is debugging on and at least a certain level?.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>{@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#isHighestOnAndAtLeast(int, DebugLevel...) isHighestOnAndAtLeast}(min_level, instance.{@link CodeletInstance#getDebugLevel() getGlobalDebugLevel}())</CODE>
      @see  #isDebugOn(CodeletInstance)
    **/
   public static final boolean isDebugOn(int min_level, CodeletInstance instance)  {
//System.out.println("isDebugOn: global=" + getGlobalDebugLevel() + ", instance=" + getInstanceDbgLevelOrNull(instance) + ", highest-at-least-" + min_level + "=" + getGlobalDebugLevel().isHighestOnAndAtLeast(min_level, getInstanceDbgLevelOrNull(instance)) + "");
      return  getGlobalDebugLevel().isHighestOnAndAtLeast(min_level, getInstanceDbgLevelOrNull(instance));
   }
   /**
      <P>Is debugging on and at most a certain level?.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>({@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#getHighestNumber(DebugLevel...) getHighestNumber}(instance.{@link CodeletInstance#getDebugLevel() getGlobalDebugLevel}()) &lt;= max_level)</CODE>
    **/
   public static final boolean isDebugLevelAtMost(int max_level, CodeletInstance instance)  {
//System.out.println("isDebugLevelAtMost: global=" + getGlobalDebugLevel() + ", instance=" + getInstanceDbgLevelOrNull(instance) + ", highest=" + getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) + ", max_level=" + max_level + ", returning=" + (getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) <= max_level) + "");
      return  (getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) <= max_level);
   }
   /**
      <P>The debugging outputter.</P>

      @see  #DEBUG_DESTINATION
      @see  #getGlobalDebugLevel()
      @see  #getDebugApblIfOn(CodeletInstance) getDebugApblIfOn
      @see  #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn
      @see  #doDebugToConsole()
    **/
   public static final TextAppenter getDebugAptr()  {
      return  dbgAptr;
   }
   /**
      <P>If debugging is on, get an appendable.</P>

      @return  <CODE>{@link #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn}(0, instance)</CODE>
    **/
   public static final Appendable getDebugApblIfOn(CodeletInstance instance)  {
      return  getDebugApblIfOn(0, instance);
   }
   /**
      <P>If debugging is on, get an appenter.</P>

      @return  <CODE>{@link #getDebugAptrIfOn(int, CodeletInstance) getDebugAptrIfOn}(0, instance)</CODE>
    **/
   public static final TextAppenter getDebugAptrIfOn(CodeletInstance instance)  {
      return  getDebugAptrIfOn(0, instance);
   }
   /**
      <P>If debugging is on and at least a certain level, get an appendable.</P>

      @return  <CODE>(!{@link #isDebugOn(int, CodeletInstance) isDebugOn}(min_level, instance) ? null
         :  {@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#getAppendable() getAppendable}()))</CODE>
      @see  #getDebugApblIfOn(CodeletInstance) getDebugApblIfOn(ci)
      @see  #getDebugAptrIfOn(int, CodeletInstance) getDebugAptrIfOn(i,ci)
    **/
   public static final Appendable getDebugApblIfOn(int min_level, CodeletInstance instance)  {
      return  (!isDebugOn(min_level, instance) ? null
         :  getDebugAptr().getAppendable());
   }
   /**
      <P>If debugging is on and at least a certain level, get an appenter.</P>

      @return  <CODE>(!{@link #isDebugOn(int, CodeletInstance) isDebugOn}(min_level, instance) ? null
         :  {@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#getAppendable() getAppendable}()))</CODE>
      @see  #getDebugAptrIfOn(CodeletInstance) getDebugAptrIfOn(ci)
      @see  #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn(i,ci)
    **/
   public static final TextAppenter getDebugAptrIfOn(int min_level, CodeletInstance instance)  {
      return  (!isDebugOn(min_level, instance) ? null
         :  getDebugAptr());
   }
   /**
      <P>Immutable map containing all named debug levels (both {@linkplain CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE required} and {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE user-created}).</P>

      @see  #isDebugOn(CodeletInstance, String)
      @see  #getDebugApblIfOn(CodeletInstance, String) getDebugApblIfOn(ci,s)
      @see  #getDebugAptrIfOn(CodeletInstance, String) getDebugAptrIfOn(ci,s)
    **/
   public static final NamedDebuggers getNamedDebuggers()  {
      return  namedDebugLevels;
   }
   /**
      <P>Is a named debuggers active?.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#isActive(String, DebugLevel, DebugLevel...) isActive}(name,
      <BR> &nbsp; &nbsp; {@link #getGlobalDebugLevel() getGlobalDebugLevel}(), instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</CODE>
      @see  #getDebugApblIfOn(CodeletInstance, String)
      @see  #getDebugAptrIfOn(CodeletInstance, String)
    **/
   public static final boolean isDebugOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().isActive(name,
         getGlobalDebugLevel(), getInstanceDbgLevelOrNull(instance));
   }
   /**
      <P>If a named debuggers active, get an appendable.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#getAppendableIfActive(String, TextAppenter, DebugLevel, DebugLevel...) getAppendableIfActive}(name,
      <BR> &nbsp; &nbsp; {@link #getDebugAptr() getDebugAptr}(), {@link #getGlobalDebugLevel() getGlobalDebugLevel}(),
      <BR> &nbsp; &nbsp; instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</CODE>
      @see  #getDebugAptrIfOn(CodeletInstance, String)
    **/
   public static final Appendable getDebugApblIfOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().getAppendableIfActive(name,
         getDebugAptr(), getGlobalDebugLevel(),
         getInstanceDbgLevelOrNull(instance));
   }
   /**
      <P>If a named debuggers active, get an appenter.</P>

      @param  instance  May be {@code null}.
      @return  <CODE>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#getAppenterIfActive(String, TextAppenter, DebugLevel, DebugLevel...) getAppenterIfActive}(name,
      <BR> &nbsp; &nbsp; {@link #getDebugAptr() getDebugAptr}(),
      <BR> &nbsp; &nbsp; {@link #getGlobalDebugLevel() getGlobalDebugLevel}(), instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</CODE>
      @see  #getDebugAptrIfOn(CodeletInstance, String)
    **/
   public static final TextAppenter getDebugAptrIfOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().getAppenterIfActive(name, getDebugAptr(),
         getGlobalDebugLevel(), getInstanceDbgLevelOrNull(instance));
   }
   /**
      <P>Directory containing the top-most package for the source code of all example classes.</P>

      @see  #EXAMPLE_CLASS_SRC_BASE_DIR
    **/
   public static final String getExampleSourceBaseDir()  {
      return  xmplSrcBaseDir;
   }
   /**
      <P>An immutable list of all directories containing the <I>top-most package</I> of codelet-containing source-code.</P>

      @see  #ENCLOSING_CLASS_SRC_BASE_DIRS
    **/
   public static final List<String> getEnclosingBaseDirList()  {
      return  enclosingBaseDirList;
   }
   static final String[] getEnclosingBaseDirs()  {
      return  enclosingBaseDirs;
   }
   /**
      <P>When <I>not</I> using a custom customizer (when using none, or a pre-made processor) how many spaces should all tabs be replaced with?.</P>

      @see  #DEFAULT_ALTERERS_CLASS_NAME
    **/
   public static final DefaultAlterGetter getDefaultAlterGetter()  {
      return  defaultAlterGetter;
   }
   /**
      <P>The character placed immediately before gap names.</P>

      @see  #GAP_NAME_POSTFIX_CHAR
    **/
   public static final GapCharConfig getGapCharConfig()  {
      return  gapCharConfig;
   }
   /**
      <P>The class that defines extra gaps that can be placed in Codelet templates.</P>

      @see  #USER_EXTRA_GAPS_CLASS_NAME
    **/
   public static final String getUserExtraGapsClassName()  {
      return  userExtraGapsClassNm;
   }
   /**
      <P>The directory in which any custom (user-created) templates are stored.</P>

      @see  #USER_TEMPLATE_BASE_DIR
    **/
   public static final String getCustomTemplateDir()  {
      return  customTmplDir;
   }
   /**
      <P>For determining which codelets should be processed or ignored.</P>

      @see  #BLACK_WHITE_LIST_TYPE
    **/
   public static final FilenameBlackWhiteList getBlackWhiteList()  {
      return  blackWhiteList;
   }
   public static final int getTargetClassMapInitCapacity()  {
      return  jdTgtClsMapInitCapacity;
   }
   /**
      <P>If a customizer attempts to make an alteration, but cannot (such as when the find-what text is not found in the example code), what should happen?.</P>

      @return  <UL>
         <LI>{@code true}: If a warning should be logged and an exception should be thrown.</LI>
         <LI>{@code false}: If only a warning should be logged.</LI>
      </UL>
      @see  #ALTERATION_NOT_MADE_CRASH
    **/
   public static final boolean doCrashIfAlterationNotMade()  {
      return  doCrashIfAlterNotMade;
   }
   /**
      <P>How many attempts should be made to retrieve each online package-list? If zero, offline only.</P>

      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final int getOnlinePackageListAttemptCount()  {
      return  onlinePkgLstAttemptCount;
   }
   /**
      <P>How many milliseconds between each failed attempt?.</P>

      @see  #PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS
    **/
   public static final long getOnlinePackageListAttemptSleepMills()  {
      return  onlinePkgLstAttemptSleepMills;
   }
   /**
      <P>If all online-retrieval attempts fail, should only a warning be logged, or should Codelet stop execution? (Offline package-lists are always retrieved.)</P>

      @see  #PKGLIST_ONLINE_FAILS_BEHAVIOR
    **/
   public static final boolean doCrashIfOnlinePackageListFailure()  {
      return  doCrashIfOnlinePkgLstFails;
   }
   /**
      <P>When online {@code package-list}s are retrieved, should offline package lists be refreshed (or created)?</P>

      @see  #AUTO_UPDATE_OFFLINE_PACKAGE_LISTS
    **/
   public static final boolean doAutoUpdateOfflinePackageLists()  {
      return  doAutoUpdateOfflinePkgLsts;
   }
   /**
      <P>The filename postfix, plus optional dot-extension that follows each offline name.</P>

      @see  #PKGLIST_OFFLINE_NAME_POSTFIX
    **/
   public static final String getOfflinePackageListPostfix()  {
      return  offlinePkgLstNamePost;
   }
   /**
      <P>All external JavaDoc doc roots, for <A HREF="{@docRoot}/overview-summary.html#xmpl_links">creating links</A>.</P>

      @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    **/
   public static final AllOnlineOfflineDocRoots getAllJavaDocRoots()  {
      return  allDocRoots;
   }
}
