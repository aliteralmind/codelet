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
   <p>Immutable, static, and non-template related configuration, used throughout Codelet, as held in a file named {@link CodeletBootstrap#BASE_CONFIG_FILE_NAME codelet.properties}. Loading is executed by {@link com.github.aliteralmind.codelet.CodeletBootstrap}.</p>

   <p><i>View <a href="{@docRoot}/../${jd_project_codelet_config_dir}/codelet.properties">{@code {@docRoot}/../${jd_project_codelet_config_dir}/codelet.properties}</a>.)</i></p>

   <A NAME="base_dirs_other"></a><h2><a href="{@docRoot}/overview-summary.html#overview_description"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; <u>Codelet: Configuration</u></h2>

   <p><i>See <a href="{@docRoot}/overview-summary.html#install_configure">getting started</a> in the Codelet installation instructions.</i></p>

   <p>There are four categories of Codelet global configuration:<ul>
      <li><a href="#base_dirs_other">Base directories</a> and other</li>
      <li><a href="#tmpl_defaults">Default templates</a></li>
      <li><a href="#tmpl_gaps">Template gaps</a></li>
      <li><a href="#debugging">Debugging and diagnostics</a></li>
   </ul>

   <A NAME="base_dirs_other"></a><h2><a href="CodeletBaseConfig.html"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; Codelet: Configuration: <u>Base directories and urls, and other</u></h2>

   <TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD>&nbsp;</TD>
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
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
      <TD>Comma-separated list of all directories containing the <i>top-most package-directories</i> of all codelet-containing source files.</TD>
   </TR><TR>
      <TD>{@linkplain #getDefaultAlterGetter() func}</TD>
      <TD>{@link #DEFAULT_ALTERERS_CLASS_NAME default_alterers_class_name}</TD>
      <TD>Fully-qualified name of the class that defines alterers used by all pre-built customizers, and are accessible in custom customizers.</TD>
   </TR></TABLE>

   <A NAME="tmpl_javadoc"></a><h2><a href="CodeletBaseConfig.html"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; Codelet: Configuration: <u>JavaDoc related</u></h2>

   <TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Func</u></b></TD>
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
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
   </TR></TABLE>

   <A NAME="tmpl_defaults"></a><h2><a href="CodeletBaseConfig.html"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; Codelet: Configuration: <u>Default template files</u></h2>

   <TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Func</u></b></TD>
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
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
   </TR></TABLE>

   <A NAME="tmpl_gaps"></a><h2><a href="CodeletBaseConfig.html"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; Codelet: Configuration: <u>Template gaps</u></h2>

   <TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Func</u></b></TD>
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
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
   </TR></TABLE>



   <A NAME="debugging"></a><h2><a href="CodeletBaseConfig.html"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a> &nbsp; Codelet: Configuration: <u>Debugging and diagnostics</u></h2>

   <TABLE ALIGN="center" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
      <TD><b><u>Func</u></b></TD>
      <TD><b><u>Name</u></b></TD>
      <TD><b><u>Description</u></b></TD>
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
   </TR></TABLE>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
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
      <p>Default value for {@code pkglist_online_attempts_per_url} when not provided--Equal to {@code 5}.</p>

    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final int DEFAULT_ONLINE_PKGLST_ATTEMPTS = 5;
   /**
      <p>Default value for {@code pkglist_online_attempts_per_url} when not provided--Equal to {@code 500}.</p>

    * @see  #PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS
    */
   public static final int DEFAULT_ONLINE_PKGLST_SLEEP_MILLS = 500;
   /**
      <p>Default value for {@code unique_jd_class_target_init_capacity} when not provided--Equal to {@code 100}.</p>

    * @see  #UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY
    */
   public static final int DEFAULT_JD_TARGET_CLASS_MAP_INIT_CAPACITY = 100;
   /*
      <p>Should the {@linkplain CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE offline versions} of external library {@code package-list} files be automatically updated?--Equal to {@code "pkglist_auto_refresh_offline__yes_no"}.</p>

      <p>Must be one of two values:<ul>
         <li>{@code "yes"}: Every execution of {@code javadoc.exe} updates the offline files to equal the most recent content of their online counterparts.</li>
         <li>{@code "no"}: Offline files must be manually (created and) updated. If they do not exist, an error occurs.</li>
      </ul>

    * @see  #doAutoUpdateOfflinePackageLists()
   public static final String AUTO_UPDATE_OFFLINE_PACKAGE_LISTS = "pkglist_auto_refresh_offline__yes_no";
    */

   /**
      <p>Directory containing the top-most package for the source code of all example classes--Name is {@code "example_class_src_base_dir"}.</p>

      <p>If this is an example code</p>

      <p><code>com.github.smith.examples.AGoodExample</code></p>

      <p>and the path to its source code is</p>

      <p>{@code C:\java_code\com\github\smith\examples\AGoodExample.java}</p>

      <p>set this to</p>

      <p>{@code "C:\java_code\"}</p>

      <p>The ending file-separator is requried. This may or may not be equal to {@link #ENCLOSING_CLASS_SRC_BASE_DIRS}. And its value may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;.</p>

    * @see  <code><a href="#base_dirs_other"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getExampleSourceBaseDir()
    */
   public static final String EXAMPLE_CLASS_SRC_BASE_DIR = "example_class_src_base_dir";
   /**
      <p>For ignoring all codelets in a single file, or in an entire package--Name is {@code "list_type__black_white_off"}.</p>

      <p><i>({@linkplain #GLOBAL_DEBUG_LEVEL Debugging Codelet} in general)</i></p>

      <p><ul>
         <li>{@code list_type__black_white_off}: Must be one of the following:<ul>
            <li>{@code "black"}: The codelets in any files or packages that match an item in the proper-list, are ignored. Those not matched are processed.</li>
            <li>{@code "white"}: The codelets in any files or packages that match an item in the proper-list, are processed.</li>
            <li>{@code "off"}: All codelets are processed.</li>
         </ul></li>
         <li>{@code list_case__ignore_require_system}: When comparing the fully-qualified name of the enclosing file (or package), should letter case be considered?<ul>
            <li>{@code "ignore"}: Case {@linkplain org.apache.commons.io.IOCase#INSENSITIVE insensitive}</li>
            <li>{@code "require"}: Case ({@linkplain org.apache.commons.io.IOCase#SENSITIVE sensitive})</li>
            <li>{@code "system"}: Case sensitivity is determined by the ({@linkplain org.apache.commons.io.IOCase#SYSTEM operating system})</li>
         </ul></li>
         <li>{@code comma_delimited_proper_list}: The list of wildcard matches that, when the fully-qualified file or package is matched, are ignored or processed (as determined by the black/white setting). Whitelist examples:<ul>
            <li>To process all codelets in all packages: {@code "*"}</li>
            <li>To process codelets only in a single package: {@code "github.com.myname.text.*"}</li>
            <li>To process codelets in one package, plus a subset of files in another: {@code "github.com.myname.text.*,github.com.myname.io.Get*ForPath"}</li>
         </ul>Do not put any dot-postfix after the file names (such as {@code ".java"} or {@code ".html"}). <i>For the overview summary ({@code "overview-summary.html"}), use {@code "OVERVIEW_SUMMARY"}. For package summary's ({@code "package-info.java"} or {@code "package-summary.html"}), use {@code "PACKAGE_SUMMARY"}.</i></li>
         <li>{@code comma_delimited_override_list}: The list of wildcard matches that trump those in the proper-list. This list is only applicable when a file or package is matched by a proper-item. Examples:<ul>
            <li>To process all codelets except those in a single file, using a whitelist (the value of {@code list_case__ignore_require_system} is required, but left out of these examples):

<blockquote><pre>list_type__black_white_off=white
comma_delimited_proper_list=fully.qualified.class.name.of.FileToProcess
comma_delimited_override_list=</pre></blockquote></li>
            <li>Using a blacklist:
<blockquote><pre>list_type__black_white_off=black
comma_delimited_proper_list=*
comma_delimited_override_list=*.FileToProcess</pre></blockquote></li>
            <li>To process codelets only in a single package: {@code "github.com.myname.text.*"}</li>
            <li>To process codelets in one package, plus a subset of files in another: {@code "github.com.myname.text.*,github.com.myname.io.Get*ForPath"}</li>
         </ul>Asterisks ({@code '*'}) indicate one-or-more of any character, and question marks ({@code '?'}) mean exactly one of any character. So, if {@code FileToProcess} is a unique filename across all project directories {@code comma_delimited_proper_list} could alternatively be
      <br> &nbsp; &nbsp; {@code *.FileToProcess}
      <br>If it is not unique:
      <br> &nbsp; &nbsp; {@code *.name.of.FileToProcess}</li>
      </ul>These values are processed by <code>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList FilenameBlackWhiteList}.{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#newFromConfigStringVars(String, String, String, String, String, Appendable, Appendable) newFromConfigStringVars}</code></p>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList
    * @see  #getBlackWhiteList()
    * @see  #BLACK_WHITE_LIST_CASE
    * @see  #BLACK_WHITE_PROPER_LIST
    * @see  #BLACK_WHITE_OVERRIDE_LIST
    */
   public static final String BLACK_WHITE_LIST_TYPE = "list_type__black_white_off";
   /**
      <p>If a customizer attempts to make an alteration, but cannot (such as when the find-what text is not found in the example code), should a crash occur, in addition to the warning?--Name is {@code "if_alteration_not_made_crash__yes_no"}.</p>

      <p>Must be one of two values:<ul>
         <li>{@code "no"}: Only warnings will be logged.</li>
         <li>{@code "yes"}: In addition to the warning, and exception is thrown.</li>
      </ul>

    * @see  #doCrashIfAlterationNotMade()
    * @see  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum
    * @see  CustomizationInstructions#getCustomizedBody(CodeletInstance, Iterator) CustomizationInstructions#getCustomizedBody
    */
   public static final String ALTERATION_NOT_MADE_CRASH = "if_alteration_not_made_crash__yes_no";

   /**
      <p>Initial capacity of the map holding the JavaDoc target-class shortcut translators--Name is {@code "unique_jd_class_target_init_capacity"}. When not provided (set to the empty-string: {@code ""}), this defaults to {@link #DEFAULT_JD_TARGET_CLASS_MAP_INIT_CAPACITY}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum#getAllParamSigsForLinkTarget(Class)
    * @see  #getTargetClassMapInitCapacity()
    */
   public static final String UNIQUE_JD_CLASS_TARGET_INIT_CAPACITY = "unique_jd_class_target_init_capacity";
   /**
      <p>When comparing class names against the black/white list, how should case be considered?--Name is {@code "list_case__ignore_require_system"}.</p>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #BLACK_WHITE_LIST_TYPE
    */
   public static final String BLACK_WHITE_LIST_CASE = "list_case__ignore_require_system";
   /**
      <p>List of wildcard matches that determine which files or packages should be recognized--Name is {@code "comma_delimited_proper_list"}.</p>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #BLACK_WHITE_LIST_TYPE
    */
   public static final String BLACK_WHITE_PROPER_LIST = "comma_delimited_proper_list";
   /**
      <p>List of wildcard matches that trump those in the &quot;proper&quot;-list--Name is {@code "comma_delimited_proper_list"}.</p>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #BLACK_WHITE_LIST_TYPE
    */
   public static final String BLACK_WHITE_OVERRIDE_LIST = "comma_delimited_override_list";
   /**
      <p>Comma-separated list of all directories containing the <i>top-most package-directory</i> of all codelet-containing source files--Name is {@code "enclosing_class_src_base_dirs"}.</p>

      <p>If all codelet-containing source code exists in a single directory:</p>

<blockquote><pre>C:\java_code\src\project_overview.html
C:\java_code\src\com\github\xbn\lang\AClass.java
C:\java_code\src\com\github\xbn\examples\lang\Demo_AClass.java
C:\java_code\src\com\github\xbn\examples\test\UnitTest_AClass.java</pre></blockquote>

      <p>Then set this to {@code "C:\java_code\src\"}</p>

      <p>If there are multiple directories:</p>

<blockquote><pre>C:\java_code\supplemental_files\project_overview.html
C:\java_code\src\com\github\xbn\lang\AClass.java
C:\java_code\src\com\github\xbn\examples\lang\Demo_AClass.java
C:\java_code\src\com\github\xbn\examples\test\UnitTest_AClass.java</pre></blockquote>

      <p>Then use
      <br> &nbsp; &nbsp; {@code C:\java_code\src\,C:\java_code\supplemental_files\}
      <br>or set &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot; to {@code "C:\java_code\"}, and use
      <br> &nbsp; &nbsp; {@code ${BASE}src\,${BASE}supplemental_files\}</p>

      <p><b>At least one directory-value is required.</b></p>

      <p>The project-overview file, as <a href="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#overview">configured</a> into JavaDoc, must exist in one of these directories. If its path is
      <br> &nbsp; &nbsp; {@code C:\java_code\src\project_overview.html}
      <br>and {@code C:\java_code\src\} is its enclosing class base-directory, then its fully-qualified name as required by the {@linkplain #BLACK_WHITE_LIST_TYPE blacklist} is {@code "project_overview"}. And in the hand {@linkplain TemplateOverrides template-overrides} configuration file, it is {@code "project_overview.html"}.</p>

      <p>This value may be equal to (or contain) {@link #EXAMPLE_CLASS_SRC_BASE_DIR}.</p>

    * @see  <code><a href="#base_dirs_other"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #BASE_DIR_BASE_DIR
    * @see  #getEnclosingBaseDirList()
    */
   public static final String ENCLOSING_CLASS_SRC_BASE_DIRS = "enclosing_class_src_base_dirs";
   /**
      <p>For use in all other &quot;base_dir&quot; config vars, with &quot;${BASE}&quot;--Name is {@code "base_dir_base_dir"}.</p>

      <p>Only the first use is recognized. If this variable equals
      <br> &nbsp; &nbsp; <code>C:\java_code\</code>
      <br>then
      <br> &nbsp; &nbsp; <code>${BASE}hello${BASE}</code>
      <br>is translated to
      <br> &nbsp; &nbsp; <code>C:\java_code\hello${BASE}</code></p>

    * @see  <code><a href="#base_dirs_other"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #ENCLOSING_CLASS_SRC_BASE_DIRS
    */
   public static final String BASE_DIR_BASE_DIR = "base_dir_base_dir";
   /**
      <p>Name of the sub-directory in which the offline versions of external library {@code "package-list"} files are stored--Equal to {@code "offline_package_lists"}.</p>

      <p>This is located in the Codelet {@linkplain CodeletBootstrap#getCodeletConfigDir() configuration directory}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE
    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final String OFFLINE_PACKAGE_LIST_DIR_NAME = "offline_package_lists";
   /**
      <p>How many attempts should be made to retrieve each online package-list? If zero, offline only--Name is {@code "pkglist_online_attempts_per_url"}.</p>

      <p>Must be an integer zero or greater. If not provided (equal to the empty-string: {@code ""}), this defaults to {@link #DEFAULT_ONLINE_PKGLST_ATTEMPTS}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getAllJavaDocRoots()
    * @see  #getOnlinePackageListAttemptCount()
    */
   public static final String PKGLIST_ONLINE_ATTEMPT_COUNT = "pkglist_online_attempts_per_url";
   /**
      <p>How many milliseconds between each failed attempt?--Name is {@code "pkglist_online_attempt_sleep_mills"}.</p>

      <p>Must be an integer zero or greater. If not provided (equal to the empty-string: {@code ""}), thi defaults to {@link #DEFAULT_ONLINE_PKGLST_SLEEP_MILLS}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getOnlinePackageListAttemptSleepMills()
    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final String PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS = "pkglist_online_attempt_sleep_mills";
   /**
      <p>If all online-retrieval attempts fail, should only a warning be logged, or should Codelet stop execution? (Offline package-lists are always retrieved)--Name is {@code "pkglist_if_online_retrieval_fails__warn_crash"}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #doCrashIfOnlinePackageListFailure()
    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final String PKGLIST_ONLINE_FAILS_BEHAVIOR = "pkglist_if_online_retrieval_fails__warn_crash";
   /**
      <p>When online package-lists are retrieved, should offline package lists be refreshed (or created)?--Name is {@code "pkglist_auto_refresh_offline__yes_no"}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #doAutoUpdateOfflinePackageLists()
    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final String AUTO_UPDATE_OFFLINE_PACKAGE_LISTS = "pkglist_auto_refresh_offline__yes_no";
   /**
      <p>Filename postfix, plus optional dot-extension that follows each offline name.--Name is {@code "pkglist_offline_name_postfix"}.</p>

    * @see  <code><a href="#tmpl_javadoc"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getOfflinePackageListPostfix()
    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final String PKGLIST_OFFLINE_NAME_POSTFIX = "pkglist_offline_name_postfix";
   /**
      <p>Amount that information that should be logged in every taglet-call--Name is {@code "global_debugging__off_12345"}.</p>

      <p>This {@linkplain com.github.xbn.io.DebugLevel#getFromStringOff12345(String, String) must be equal to}<ul>
         <li>{@code "off"}: No automated debugging.</li>
         <li>A digit between one and five: An arbitrary number representing the amount of debugging information that will be output.</li>
      </ul>

      <p>This is the {@linkplain #getGlobalDebugLevel() global debugging level} used by all taglets. An individual taglet may override this by prepending "{@link CodeletInstance#DEBUG_LEVEL_PREFIX_PREFIX [DEBUG_LEVEL_#]}" to its text. If the {@linkplain CodeletInstance#getDebugLevel() taglet's level} is lower than or equal to the global level, it is ignored.</p>

      <p>For very specific control over what is output <i>without having to recompile code</i>, create a &quot;{@link CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE named debugger}&quot;, each of which has a  level-number associated to it. If the actual (global or taglet) debugging level is equal-to-or-higher, it's information is output.</p>

      <h4>What is logged</h4>

      <p>The examples in this section assumes that {@code CodeletBaseConfig} is <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/language/static-import.html">statically imported</a> &quot;en masse&quot;:
      <br> &nbsp; &nbsp; <code>import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;</code></p>

      <p>To output something even if the debugging level is {@code "off"}:</p>

<blockquote><code>{@link #debugln(Object) debugln}(&quot;Important information&quot;);</code></blockquote>

      <p> To output only when global debugging is on (level one or greater):</p>

<blockquote><code>if({@link #isDebugOn(CodeletInstance) isDebugOn}(null))  {
   debugln(&quot;Important information&quot;)
}</code></blockquote>

      <p>To output when global debugging is at least level three:</p>

<blockquote><code>if({@link #isDebugOn(int, CodeletInstance) isDebugOn}(3, null))  {
   debugln(&quot;Important information&quot;)
}</code></blockquote>

      <p>To output when global or taglet debugging is at least three:</p>

<blockquote><code>if({@link #isDebugOn(int, CodeletInstance) isDebugOn}(3, <i>[the-taglet-instance]</i>))  {
   debugln(&quot;Important information&quot;)
}</code></blockquote>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEBUG_DESTINATION
    * @see  #getGlobalDebugLevel()
    * @see  <a href="{@docRoot}/overview-summary.html#xmpl_links_debug">Overview JavaDoc link example</a>
    */
   public static final String GLOBAL_DEBUG_LEVEL = "global_debugging__off_12345";
   /**
      <p>When debugging is on, where should output be written to?--Name is {@code "debug_to__console_path"}.</p>

      <p>This must be one of two values:<ul>
         <li>{@code "console"}: Sets {@link #getDebugAptr() getDebugAptr}{@code ()} to <code>{@link com.github.xbn.io.TextAppenter TextAppenter}.{@link com.github.xbn.io.TextAppenter#CONSOLE CONSOLE}</code></li>
         <li>The path to a writable file: Sets {@code getDebugAptr()} to
         <br> &nbsp; &nbsp; <code>{@link com.github.xbn.io.NewTextAppenterFor NewTextAppenterFor}.{@link com.github.xbn.io.NewTextAppenterFor#file(String, AppendOrOverwrite) file}(dbgAptrStr, {@link com.github.xbn.io.AppendOrOverwrite AppendOrOverwrite}.{@link com.github.xbn.io.AppendOrOverwrite#APPEND APPEND})</code></li>
      </ul>In order to allow debugging at any time, even when the {@linkplain #GLOBAL_DEBUG_LEVEL debugging level} is set to off, this value is required.</p>

    * @see  <code><a href="#debugging"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getDebugAptr()
    */
   public static final String DEBUG_DESTINATION = "debug_to__console_path";
   /**
      <p>Prepended to all {@code "default_*_template_path"} values--Name is {@code "default_template_directory_prefix"}.</p>

      <p>This value may be the empty string ({@code ""}), and may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;</p>

      <p>All default template paths are required, must exist and be readable, and must refer to a valid Codelet template of its type. They may be in any of the following locations, which are searched for in order:<ol>
         <li>A file name, which exists in the same directory as this configuration file.</li>
         <li>A relative path, which exists off of the directory from which the Java Virtual Machine was invoked.</li>
         <li>An absolute path.</li>
      </ol>

      <p>View the locally installed templates:<ul>
         <li><a href="{@docRoot}/../${jd_project_codelet_config_dir}/default_templates/source_code.txt">{@code {@.codelet}}</a></li>
         <li><a href="{@docRoot}/../${jd_project_codelet_config_dir}/default_templates/console_out.txt">{@code {@.codelet.out}}</a></li>
         <li><a href="{@docRoot}/../${jd_project_codelet_config_dir}/default_templates/src_and_out.txt">{@code {@.codelet.and.out}}</a></li>
         <li><a href="{@docRoot}/../${jd_project_codelet_config_dir}/default_templates/file_text.txt">{@code {@.file.textlet}}</a></li>
      </ul>These links assume the default template directory is &quot;{@code {@docRoot}/../${jd_project_codelet_config_dir}/default_templates/}&quot;</p>

    * @see  <code><a href="#tmpl_defaults"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_SRC_CODE_TMPL_PATH
    * @see  #DEFAULT_DOT_OUT_TMPL_PATH
    * @see  #DEFAULT_FILE_TEXT_TMPL_PATH
    * @see  #DEFAULT_AND_OUT_TMPL_PATH
    * @see  #USER_EXTRA_GAPS_CLASS_NAME
    * @see  #USER_TEMPLATE_BASE_DIR
    */
   public static final String DEFAULT_TPML_DIR_PREFIX = "default_template_directory_prefix";
   /**
      <p>Path to the default {@linkplain type.SourceCodeTemplate template} used for (source-code) {@link CodeletType#SOURCE_CODE {@.codelet}} taglets--Name is {@code "default_source_codelet_template_path"}.</p>

    * @see  <code><a href="#tmpl_defaults"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_TPML_DIR_PREFIX
    * @see  #getDefaultSourceCodeTemplatePath()
    */
   public static final String DEFAULT_SRC_CODE_TMPL_PATH = "default_source_codelet_template_path";
   /**
      <p>Path to the default {@linkplain type.ConsoleOutTemplate template} used for {@link CodeletType#CONSOLE_OUT {@.codelet.out}} taglets--Name is {@code "default_dot_out_template_path"}.</p>

    * @see  <code><a href="#tmpl_defaults"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_TPML_DIR_PREFIX
    * @see  #getDefaultConsoleOutTemplatePath()
    */
   public static final String DEFAULT_DOT_OUT_TMPL_PATH = "default_dot_out_template_path";
   /**
      <p>Path to the default {@linkplain type.SourceAndOutTemplate template} used for {@link CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets--Name is {@code "default_and_out_template_path"}.</p>

    * @see  <code><a href="#tmpl_defaults"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_TPML_DIR_PREFIX
    * @see  #getDefaultSourceAndOutTemplatePath()
    */
   public static final String DEFAULT_AND_OUT_TMPL_PATH = "default_and_out_template_path";
   /**
      <p>Path to the default {@linkplain type.FileTextTemplate template} used for {@link CodeletType#FILE_TEXT {@.file.textlet}} taglets--Name is {@code "default_file_textlet_template_path"}.</p>

    * @see  <code><a href="#tmpl_defaults"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_TPML_DIR_PREFIX
    * @see  #getDefaultFileTextTemplatePath()
    */
   public static final String DEFAULT_FILE_TEXT_TMPL_PATH = "default_file_textlet_template_path";
   /**
      <p>Fully-qualified name of the class that defines {@linkplain CustomizationInstructions#orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) alterers} used by all {@linkplain BasicCustomizers pre-built customizers}, and are accessible in <a href="CustomizationInstructions.html#overview">custom customizers</a>--Name is {@code "default_alterers_class_name"}.</p>

      <p>When provided, the class it represents must {@linkplain java.lang.Class#forName(String) exist}, implement {@link com.github.aliteralmind.codelet.alter.DefaultAlterGetter}, and have a no-parameter constructor. When not provided (equal to the empty-string {@code ""}), {@link DefaultDefaultAlterGetter} is used.</p>

    * @see  <code><a href="#base_dirs_other"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getDefaultAlterGetter()
    */
   public static final String DEFAULT_ALTERERS_CLASS_NAME = "default_alterers_class_name";

   /**
      <p>Character placed immediately before gap names--Name is {@code "gap_name_prefix_char"}.</p>

      <p>If not provided, defaults to <code>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_PREFIX_CHAR DEFAULT_PREFIX_CHAR}</code></p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #GAP_NAME_POSTFIX_CHAR
    * @see  #GAP_NAME_LITERAL_PREFIX
    * @see  #getGapCharConfig()
    */
   public static final String GAP_NAME_PREFIX_CHAR = "gap_name_prefix_char";
   /**
      <p>Character placed immediately after gap names--Name is {@code "gap_name_postfix_char"}.</p>

      <p>If not provided, defaults to <code>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_POSTFIX_CHAR DEFAULT_PREFIX_CHAR}</code></p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #GAP_NAME_PREFIX_CHAR
    * @see  #GAP_NAME_LITERAL_POSTFIX
    * @see  #getGapCharConfig()
    */
   public static final String GAP_NAME_POSTFIX_CHAR = "gap_name_postfix_char";
   /**
      <p>Literal representation of the prefix char, for use in between texts--Name is {@code "gap_literal_prefix_char"}.</p>

      <p>If not provided, defaults to <code>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_LITERAL_PREFIX DEFAULT_LITERAL_PREFIX}</code></p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #GAP_NAME_PREFIX_CHAR
    * @see  #GAP_NAME_LITERAL_POSTFIX
    * @see  #getGapCharConfig()
    */
   public static final String GAP_NAME_LITERAL_PREFIX = "gap_literal_prefix_char";
   /**
      <p>Literal representation of the postfix char, for use in between texts--Name is {@code "gap_literal_name_postfix_char"}.</p>

      <p>If not provided, defaults to <code>{@link com.github.aliteralmind.templatefeather.GapCharConfig GapCharConfig}.{@link com.github.aliteralmind.templatefeather.GapCharConfig#DEFAULT_LITERAL_PREFIX DEFAULT_LITERAL_PREFIX}</code></p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #GAP_NAME_POSTFIX_CHAR
    * @see  #GAP_NAME_LITERAL_PREFIX
    * @see  #getGapCharConfig()
    */
   public static final String GAP_NAME_LITERAL_POSTFIX = "gap_literal_name_postfix_char";
   /**
      <p>Fully-qualified name of the class that defines extra gaps that can be placed in Codelet templates--Name is {@code "user_extra_gaps_class"}.</p>

      <p>If there are no extra gaps, set this to the empty-string. Otherwise, the class it represents must {@linkplain java.lang.Class#forName(String) exist}, implement {@link UserExtraGapGetter}, and have a no-parameter constructor.</p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #DEFAULT_TPML_DIR_PREFIX
    * @see  #getUserExtraGapsClassName()
    */
   public static final String USER_EXTRA_GAPS_CLASS_NAME = "user_extra_gaps_class";
   /**
      <p>Directory in which any custom (user-created) templates are stored--Name is {@code "user_template_base_dir"}.</p>

      <p>This is optional and not validated in any way. Its value may include the &quot;{@link #BASE_DIR_BASE_DIR base_dir_base_dir}&quot;.</p>

    * @see  <code><a href="#tmpl_gaps"><img src="{@docRoot}/resources/up_arrow.gif" alt="Up arrow"></a></code>
    * @see  #getCustomTemplateDir()
    */
   public static final String USER_TEMPLATE_BASE_DIR = "user_template_base_dir";
   /**
      <p>Load configuration and get the instance. Call only once.</p>

    * @param  props  May not be {@code null}, and must be a valid {@linkplain CodeletBaseConfig Codelet properties} file.
    * @return  #INSTANCE
    * @exception  IllegalStateException  If configuration was already loaded.
    * @see  com.github.xbn.util.PropertiesUtil
    */
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

      //Named debuggers cannot be used until the named-level map is created!

      int namedDebugLevelsRqd = 0;
      Map<String,DebugLevel> rqdDbgLevelNameMap = NamedDebuggers.
         newMapFromConfigFile(null, rqdDbgLevels_configLineItr,
         "rqdDbgLevels_configLineItr",
         null);    //debug
      namedDebugLevelsRqd = rqdDbgLevelNameMap.size();

debugln("1");
      namedDebugLevels = new NamedDebuggers(rqdDbgLevelNameMap,
         debugLevels_configLineItr,
         "rqdDbgLevelNameMap",
         null);    //debug
      debugln("2");

      //NOW named debuggers can be used.

      if(isDebugOn(null, "zzconfiguration.nameddebuglevels.listallafterload"))  {
         debugln("All named debuggers:" + MapUtil.toString(namedDebugLevels.getMap(), null));
      }

      debugln("3");
      namedDebugLevels.setAllQueriesDebug(
         getDebugApblIfOn(null, "zzconfiguration.nameddebuglevels.eachquery"));

      debugln("4");
      String baseDirBaseRplcmntQuoted = Matcher.quoteReplacement(props.getProperty(BASE_DIR_BASE_DIR, ""));

      debugln("5");
      xmplSrcBaseDir = get1stDollarBASERplcd(
         props.getProperty(EXAMPLE_CLASS_SRC_BASE_DIR, ""),
         baseDirBaseRplcmntQuoted);
      debugln("6");
      String enclosingBaseDirPropVal = props.getProperty(ENCLOSING_CLASS_SRC_BASE_DIRS, "");
      enclosingBaseDirs = null;
      debugln("7");
      if(enclosingBaseDirPropVal.length() == 0)  {
         throw  new IllegalArgumentException("No values for " + ENCLOSING_CLASS_SRC_BASE_DIRS + ". At least one required.");
      }  else  {
         debugln("8");
         enclosingBaseDirs = enclosingBaseDirPropVal.split(",");
         for(int i = 0; i < enclosingBaseDirs.length; i++)  {
            enclosingBaseDirs[i] = get1stDollarBASERplcd(
               enclosingBaseDirs[i], baseDirBaseRplcmntQuoted);
            new PathMustBe().existing().readable().
               getOrCrashIfBad(enclosingBaseDirs[i],
               "Element " + i + " in " + ENCLOSING_CLASS_SRC_BASE_DIRS);
         }
      }
      debugln("9");
      enclosingBaseDirList = Collections.unmodifiableList(Arrays.asList(enclosingBaseDirs));

      char gapNamePreChar = PropertiesUtil.getWithEmptyDefault(props, GAP_NAME_PREFIX_CHAR, Empty.OK, GapCharConfig.DEFAULT_PREFIX_CHAR);
      debugln("10");
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
      <p>Was configuration loaded?.</p>

    * @return  {@code true} If all values loaded successfully.
    */
   public static final boolean wasLoaded()  {
      return  wasLoaded;
   }
   /**
      <p>Path to the default template used for (source-code) {@code {@.codelet}} taglets.</p>

    * @see  #DEFAULT_SRC_CODE_TMPL_PATH
    */
   public static final String getDefaultSourceCodeTemplatePath()  {
      return  defaultSrcTmplPath;
   }
   /**
      <p>Path to the default template used for {@code {@.codelet.out}} taglets.</p>

    * @see  #DEFAULT_DOT_OUT_TMPL_PATH
    */
   public static final String getDefaultConsoleOutTemplatePath()  {
      return  defaultOutTmplPath;
   }
   /**
      <p>Path to the default template used for {@code {@.codelet.and.out}} taglets.</p>

    * @see  #DEFAULT_AND_OUT_TMPL_PATH
    */
   public static final String getDefaultSourceAndOutTemplatePath()  {
      return  defaultSrcAndOutTmplPath;
   }
   /**
      <p>Path to the default template used for {@code {@.file.textlet}} taglets.</p>

    * @see  #DEFAULT_FILE_TEXT_TMPL_PATH
    */
   public static final String getDefaultFileTextTemplatePath()  {
      return  defaultFileTextTmplPath;
   }
   /**
      <p>Write a message to the console and, if debugging is both on and <i>not</i> to the console, writes it via the debug outputter as well.</p>

      <p>This<ol>
         <li>Calls <code>System.out.println(message);</code></li>
         <li>If debugging is both {@linkplain #isDebugOn(CodeletInstance) on} and <i>not</i> {@linkplain #doDebugToConsole() to the console}, this also calls
         <br> &nbsp; &nbsp; {@link #debugln(Object) debugln}{@code (message)}</li>
      </ol>

    * @param  message  <i>Should</i> not be {@code null} or empty.
    * @see  #debugAndToConsole(CodeletInstance, Object)
    */
   public static final void debuglnAndToConsole(CodeletInstance instance, Object message)  {
      System.out.println(message);
      if(isDebugOn(instance)  &&  !doDebugToConsole())  {
         debugln(message);
      }
   }
   /**
      <p>Write a message to the console and, if debugging is both on and <i>not</i> to the console, writes it via the debug outputter as well.</p>

      <p>This<ol>
         <li>Calls <code>System.out.print(message);</code></li>
         <li>If debugging is both {@linkplain #isDebugOn(CodeletInstance) on} and <i>not</i> {@linkplain #doDebugToConsole() to the console}, this also calls
         <br> &nbsp; &nbsp; {@link #debug(Object) debug}{@code (message)}</li>
      </ol>

    * @param  message  <i>Should</i> not be {@code null} or empty.
    * @see  #debuglnAndToConsole(CodeletInstance, Object)
    */
   public static final void debugAndToConsole(CodeletInstance instance, Object message)  {
      System.out.print(message);
      if(isDebugOn(instance)  &&  !doDebugToConsole())  {
         debug(message);
      }
   }
   /**
      <p>Write a message to the debugging output.</p>

    * <p>Equal to
      <br> &nbsp; &nbsp; <code>{@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#appent(Object) appent}(message)</code></p>

    * @see  #debugln(Object)
    */
   public static final void debug(Object message)  {
      getDebugAptr().appent(message);
   }
   /**
      <p>Write a message to the debugging output.</p>

    * <p>Equal to
      <br> &nbsp; &nbsp; <code>{@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#appentln(Object) appentln}(message)</code></p>

    * @see  #debug(Object)
    */
   public static final void debugln(Object message)  {
      getDebugAptr().appentln(message);
   }
   /**
      <p>Is debugging output being written to the console?. This is used only to avoid {@linkplain #debuglnAndToConsole(CodeletInstance, Object) duplicate messages} when {@linkplain #isDebugOn(CodeletInstance) debugging is on}.</p>

    * @see  #getGlobalDebugLevel()
    */
   public static final boolean doDebugToConsole()  {
      return  doDebugToConsole;
   }
   /**
      <p>The amount of debugging, beyond ......logging......, that is written in every taglet call.</p>

    * @see  #GLOBAL_DEBUG_LEVEL
    * @see  #isDebugOn(CodeletInstance) isDebugOn(ci)
    * @see  #isDebugOn(CodeletInstance, String) isDebugOn(ci,s)
    * @see  #getDebugAptr()
    * @see  #doDebugToConsole()
    * @see  #debuglnAndToConsole(CodeletInstance, Object) log
    */
   public static final DebugLevel getGlobalDebugLevel()  {
      return  dbgLevel;
   }
   /**
      <p>Is debugging on, either globally or for a single taglet?.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>{@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#isThisOrAnyOn(DebugLevel...) isThisOrAnyOn}(instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}())</code>
    * @see  #isDebugOn(CodeletInstance, String)
    * @see  #isDebugOn(int, CodeletInstance)
    */
   public static final boolean isDebugOn(CodeletInstance instance)  {
      return  getGlobalDebugLevel().isThisOrAnyOn(getInstanceDbgLevelOrNull(instance));
   }
      private static final DebugLevel getInstanceDbgLevelOrNull(CodeletInstance instance)  {
         return  ((instance == null) ? null
            :  instance.getDebugLevel());
      }
   /**
      <p>Is debugging on and at least a certain level?.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>{@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#isHighestOnAndAtLeast(int, DebugLevel...) isHighestOnAndAtLeast}(min_level, instance.{@link CodeletInstance#getDebugLevel() getGlobalDebugLevel}())</code>
    * @see  #isDebugOn(CodeletInstance)
    */
   public static final boolean isDebugOn(int min_level, CodeletInstance instance)  {
//System.out.println("isDebugOn: global=" + getGlobalDebugLevel() + ", instance=" + getInstanceDbgLevelOrNull(instance) + ", highest-at-least-" + min_level + "=" + getGlobalDebugLevel().isHighestOnAndAtLeast(min_level, getInstanceDbgLevelOrNull(instance)) + "");
      return  getGlobalDebugLevel().isHighestOnAndAtLeast(min_level, getInstanceDbgLevelOrNull(instance));
   }
   /**
      <p>Is debugging on and at most a certain level?.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>({@link #getGlobalDebugLevel() getGlobalDebugLevel}().{@link com.github.xbn.io.DebugLevel#getHighestNumber(DebugLevel...) getHighestNumber}(instance.{@link CodeletInstance#getDebugLevel() getGlobalDebugLevel}()) &lt;= max_level)</code>
    */
   public static final boolean isDebugLevelAtMost(int max_level, CodeletInstance instance)  {
//System.out.println("isDebugLevelAtMost: global=" + getGlobalDebugLevel() + ", instance=" + getInstanceDbgLevelOrNull(instance) + ", highest=" + getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) + ", max_level=" + max_level + ", returning=" + (getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) <= max_level) + "");
      return  (getGlobalDebugLevel().getHighestNumber(getInstanceDbgLevelOrNull(instance)) <= max_level);
   }
   /**
      <p>The debugging outputter.</p>

    * @see  #DEBUG_DESTINATION
    * @see  #getGlobalDebugLevel()
    * @see  #getDebugApblIfOn(CodeletInstance) getDebugApblIfOn
    * @see  #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn
    * @see  #doDebugToConsole()
    */
   public static final TextAppenter getDebugAptr()  {
      return  dbgAptr;
   }
   /**
      <p>If debugging is on, get an appendable.</p>

    * @return  <code>{@link #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn}(0, instance)</code>
    */
   public static final Appendable getDebugApblIfOn(CodeletInstance instance)  {
      return  getDebugApblIfOn(0, instance);
   }
   /**
      <p>If debugging is on, get an appenter.</p>

    * @return  <code>{@link #getDebugAptrIfOn(int, CodeletInstance) getDebugAptrIfOn}(0, instance)</code>
    */
   public static final TextAppenter getDebugAptrIfOn(CodeletInstance instance)  {
      return  getDebugAptrIfOn(0, instance);
   }
   /**
      <p>If debugging is on and at least a certain level, get an appendable.</p>

    * @return  <code>(!{@link #isDebugOn(int, CodeletInstance) isDebugOn}(min_level, instance) ? null
         :  {@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#getAppendable() getAppendable}()))</code>
    * @see  #getDebugApblIfOn(CodeletInstance) getDebugApblIfOn(ci)
    * @see  #getDebugAptrIfOn(int, CodeletInstance) getDebugAptrIfOn(i,ci)
    */
   public static final Appendable getDebugApblIfOn(int min_level, CodeletInstance instance)  {
      return  (!isDebugOn(min_level, instance) ? null
         :  getDebugAptr().getAppendable());
   }
   /**
      <p>If debugging is on and at least a certain level, get an appenter.</p>

    * @return  <code>(!{@link #isDebugOn(int, CodeletInstance) isDebugOn}(min_level, instance) ? null
         :  {@link #getDebugAptr() getDebugAptr}().{@link com.github.xbn.io.TextAppenter#getAppendable() getAppendable}()))</code>
    * @see  #getDebugAptrIfOn(CodeletInstance) getDebugAptrIfOn(ci)
    * @see  #getDebugApblIfOn(int, CodeletInstance) getDebugApblIfOn(i,ci)
    */
   public static final TextAppenter getDebugAptrIfOn(int min_level, CodeletInstance instance)  {
      return  (!isDebugOn(min_level, instance) ? null
         :  getDebugAptr());
   }
   /**
      <p>Immutable map containing all named debug levels (both {@linkplain CodeletBootstrap#NAMED_DEBUGGERS_CONFIG_FILE required} and {@linkplain CodeletBootstrap#CODELET_RQD_NAMED_DBGRS_CONFIG_FILE user-created}).</p>

    * @see  #isDebugOn(CodeletInstance, String)
    * @see  #getDebugApblIfOn(CodeletInstance, String) getDebugApblIfOn(ci,s)
    * @see  #getDebugAptrIfOn(CodeletInstance, String) getDebugAptrIfOn(ci,s)
    */
   public static final NamedDebuggers getNamedDebuggers()  {
      return  namedDebugLevels;
   }
   /**
      <p>Is a named debuggers active?.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#isActive(String, DebugLevel, DebugLevel...) isActive}(name,
      <br> &nbsp; &nbsp; {@link #getGlobalDebugLevel() getGlobalDebugLevel}(), instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</code>
    * @see  #getDebugApblIfOn(CodeletInstance, String)
    * @see  #getDebugAptrIfOn(CodeletInstance, String)
    */
   public static final boolean isDebugOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().isActive(name,
         getGlobalDebugLevel(), getInstanceDbgLevelOrNull(instance));
   }
   /**
      <p>If a named debuggers active, get an appendable.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#getAppendableIfActive(String, TextAppenter, DebugLevel, DebugLevel...) getAppendableIfActive}(name,
      <br> &nbsp; &nbsp; {@link #getDebugAptr() getDebugAptr}(), {@link #getGlobalDebugLevel() getGlobalDebugLevel}(),
      <br> &nbsp; &nbsp; instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</code>
    * @see  #getDebugAptrIfOn(CodeletInstance, String)
    */
   public static final Appendable getDebugApblIfOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().getAppendableIfActive(name,
         getDebugAptr(), getGlobalDebugLevel(),
         getInstanceDbgLevelOrNull(instance));
   }
   /**
      <p>If a named debuggers active, get an appenter.</p>

    * @param  instance  May be {@code null}.
    * @return  <code>{@link #getNamedDebuggers() getNamedDebuggers}().{@link com.github.aliteralmind.codelet.util.NamedDebuggers#getAppenterIfActive(String, TextAppenter, DebugLevel, DebugLevel...) getAppenterIfActive}(name,
      <br> &nbsp; &nbsp; {@link #getDebugAptr() getDebugAptr}(),
      <br> &nbsp; &nbsp; {@link #getGlobalDebugLevel() getGlobalDebugLevel}(), instance.{@link CodeletInstance#getDebugLevel() getDebugLevel}()</code>
    * @see  #getDebugAptrIfOn(CodeletInstance, String)
    */
   public static final TextAppenter getDebugAptrIfOn(CodeletInstance instance, String name)  {
      return  getNamedDebuggers().getAppenterIfActive(name, getDebugAptr(),
         getGlobalDebugLevel(), getInstanceDbgLevelOrNull(instance));
   }
   /**
      <p>Directory containing the top-most package for the source code of all example classes.</p>

    * @see  #EXAMPLE_CLASS_SRC_BASE_DIR
    */
   public static final String getExampleSourceBaseDir()  {
      return  xmplSrcBaseDir;
   }
   /**
      <p>An immutable list of all directories containing the <i>top-most package</i> of codelet-containing source-code.</p>

    * @see  #ENCLOSING_CLASS_SRC_BASE_DIRS
    */
   public static final List<String> getEnclosingBaseDirList()  {
      return  enclosingBaseDirList;
   }
   static final String[] getEnclosingBaseDirs()  {
      return  enclosingBaseDirs;
   }
   /**
      <p>When <i>not</i> using a custom customizer (when using none, or a pre-made processor) how many spaces should all tabs be replaced with?.</p>

    * @see  #DEFAULT_ALTERERS_CLASS_NAME
    */
   public static final DefaultAlterGetter getDefaultAlterGetter()  {
      return  defaultAlterGetter;
   }
   /**
      <p>The character placed immediately before gap names.</p>

    * @see  #GAP_NAME_POSTFIX_CHAR
    */
   public static final GapCharConfig getGapCharConfig()  {
      return  gapCharConfig;
   }
   /**
      <p>The class that defines extra gaps that can be placed in Codelet templates.</p>

    * @see  #USER_EXTRA_GAPS_CLASS_NAME
    */
   public static final String getUserExtraGapsClassName()  {
      return  userExtraGapsClassNm;
   }
   /**
      <p>The directory in which any custom (user-created) templates are stored.</p>

    * @see  #USER_TEMPLATE_BASE_DIR
    */
   public static final String getCustomTemplateDir()  {
      return  customTmplDir;
   }
   /**
      <p>For determining which codelets should be processed or ignored.</p>

    * @see  #BLACK_WHITE_LIST_TYPE
    */
   public static final FilenameBlackWhiteList getBlackWhiteList()  {
      return  blackWhiteList;
   }
   public static final int getTargetClassMapInitCapacity()  {
      return  jdTgtClsMapInitCapacity;
   }
   /**
      <p>If a customizer attempts to make an alteration, but cannot (such as when the find-what text is not found in the example code), what should happen?.</p>

    * @return  <ul>
         <li>{@code true}: If a warning should be logged and an exception should be thrown.</li>
         <li>{@code false}: If only a warning should be logged.</li>
      </ul>
    * @see  #ALTERATION_NOT_MADE_CRASH
    */
   public static final boolean doCrashIfAlterationNotMade()  {
      return  doCrashIfAlterNotMade;
   }
   /**
      <p>How many attempts should be made to retrieve each online package-list? If zero, offline only.</p>

    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final int getOnlinePackageListAttemptCount()  {
      return  onlinePkgLstAttemptCount;
   }
   /**
      <p>How many milliseconds between each failed attempt?.</p>

    * @see  #PKGLIST_ONLINE_ATTEMPT_SLEEP_MILLS
    */
   public static final long getOnlinePackageListAttemptSleepMills()  {
      return  onlinePkgLstAttemptSleepMills;
   }
   /**
      <p>If all online-retrieval attempts fail, should only a warning be logged, or should Codelet stop execution? (Offline package-lists are always retrieved.)</p>

    * @see  #PKGLIST_ONLINE_FAILS_BEHAVIOR
    */
   public static final boolean doCrashIfOnlinePackageListFailure()  {
      return  doCrashIfOnlinePkgLstFails;
   }
   /**
      <p>When online {@code package-list}s are retrieved, should offline package lists be refreshed (or created)?</p>

    * @see  #AUTO_UPDATE_OFFLINE_PACKAGE_LISTS
    */
   public static final boolean doAutoUpdateOfflinePackageLists()  {
      return  doAutoUpdateOfflinePkgLsts;
   }
   /**
      <p>The filename postfix, plus optional dot-extension that follows each offline name.</p>

    * @see  #PKGLIST_OFFLINE_NAME_POSTFIX
    */
   public static final String getOfflinePackageListPostfix()  {
      return  offlinePkgLstNamePost;
   }
   /**
      <p>All external JavaDoc doc roots, for <a href="{@docRoot}/overview-summary.html#xmpl_links">creating links</a>.</p>

    * @see  #PKGLIST_ONLINE_ATTEMPT_COUNT
    */
   public static final AllOnlineOfflineDocRoots getAllJavaDocRoots()  {
      return  allDocRoots;
   }
}
