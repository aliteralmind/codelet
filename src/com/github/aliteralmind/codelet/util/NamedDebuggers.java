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
package  com.github.aliteralmind.codelet.util;
   import  com.github.xbn.io.DebugLevel;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.Null;
   import  com.github.xbn.list.MapUtil;
   import  com.github.xbn.util.JavaRegexes;
   import  java.util.Collections;
   import  java.util.Iterator;
   import  java.util.Map;
   import  java.util.NoSuchElementException;
   import  java.util.TreeMap;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <P>Collection of names referring to specific debugging tasks, each associated to an arbitrary numeric level.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <CODE><A HREF="http://codelet.aliteralmind.com">http://codelet.aliteralmind.com</A></CODE>, <CODE><A HREF="https://github.com/aliteralmind/codelet">https://github.com/aliteralmind/codelet</A></CODE>
 **/
public class NamedDebuggers  {
   private final Map<String,DebugLevel> nameLvlMap;
   private TextAppenter dbgAptrAllQueries;
   /**
      <P>Create a new instance from the path of a configuration file.</P>

      <P>This sets {@link #getMap() getMap}{@code ()} to an {@linkplain java.util.Collections#unmodifiableMap(Map) immutable version} of</P>

<BLOCKQUOTE>NamedDebuggers.{@link #newMapFromConfigFile(Map, String, String, Appendable) newMapFromConfigFile}(map_toAddToIfNonNull, configFile_path, path_varName,
<BR> &nbsp; &nbsp; debugAll_ifNonNull)</BLOCKQUOTE>

   	@see  #NamedDebuggers(Map, Iterator, String, Appendable)
    **/
   public NamedDebuggers(Map<String,DebugLevel> map_toAddToIfNonNull, String configFile_path, String path_varName, Appendable debugAll_ifNonNull)  {
      Map<String,DebugLevel> nameLvlMap2 = NamedDebuggers.
         newMapFromConfigFile(map_toAddToIfNonNull, configFile_path, path_varName,
            debugAll_ifNonNull);
      nameLvlMap = Collections.unmodifiableMap(nameLvlMap2);
      setAllQueriesDebug(null);
   }
   /**
      <P>Create a new instance from a configuration file's line iterator.</P>

      <P>This sets {@link #getMap() getMap}{@code ()} to an {@linkplain java.util.Collections#unmodifiableMap(Map) immutable version} of</P>

<BLOCKQUOTE>NamedDebuggers.{@link #newMapFromConfigFile(Map, Iterator, String, Appendable) newMapFromConfigFile}(map_toAddToIfNonNull, configFile_path, path_varName,
<BR> &nbsp; &nbsp; debugAll_ifNonNull)</BLOCKQUOTE>

   	@see  #NamedDebuggers(Map, String, String, Appendable)
    **/
   public NamedDebuggers(Map<String,DebugLevel> map_toAddToIfNonNull, Iterator<String> configFile_lineItr, String itr_VarName, Appendable debugAll_ifNonNull)  {
      Map<String,DebugLevel> nameLvlMap2 = NamedDebuggers.newMapFromConfigFile(map_toAddToIfNonNull, configFile_lineItr, itr_VarName, debugAll_ifNonNull);
      nameLvlMap = Collections.unmodifiableMap(nameLvlMap2);
      setAllQueriesDebug(null);
   }
   /**
      <P>Unmodifiable name-to-level map.</P>
    **/
   public Map<String,DebugLevel> getMap()  {
      return  nameLvlMap;
   }
   /**
      <P>Set debugging for outputting all {@code isActive} queries. This is useful for determining levels that may be unused.</P>

      @param  debugEachQuery_ifNonNull  Get with {@link #getAllQueriesDebugAptr() getAllQueriesDebugAptr}{@code ()}.
      @see  #isActive(String, DebugLevel, DebugLevel...) isActive
    **/
   public void setAllQueriesDebug(Appendable debugEachQuery_ifNonNull)  {
      dbgAptrAllQueries = NewTextAppenterFor.appendableUnusableIfNull(debugEachQuery_ifNonNull);
   }
   /**
      <P>Get the text appenter for debugging each query.</P>

      @return  A non-{@code null} appenter.
      @see  #setAllQueriesDebug(Appendable)
    **/
   public TextAppenter getAllQueriesDebugAptr()  {
      return  dbgAptrAllQueries;
   }
   /**
      <P>Is a named level active?.</P>

      <P>If {@linkplain #getAllQueriesDebugAptr() each-query debugging} is on, this outputs {@code name}, regardless the value returned by this function.</P>

      @param  name  Must be an existing name.
      @param  actual_level1  The actual debugging level is the highest level in this or any of the elements in {@code actualLevels_2AndUp}. May not be {@code null}.
      @param  actualLevels_2AndUp  If multiple levels make up &quot;the level&quot; (the {@linkplain com.github.xbn.io.DebugLevel#getHighestLevel(DebugLevel...) highest one} between all of them), these are those additional level elements.
      @return  If <CODE>{@link #getMap() getMap}().{@link java.util.Map#get(Object) get}(name)</CODE> is<UL>
         <LI>{@code null}: {@code false}</LI>
         <LI><CODE>{@link com.github.xbn.io.DebugLevel#OFF OFF}</CODE>: {@code true}</LI>
      </UL>Otherwise:
      <BR> &nbsp; &nbsp; <CODE>(getMap}().get(name).<!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="http://docs.oracle.com/javase/7/docs/apijava/lang/Comparable.html#compareTo(T)">compareTo</A>(actualLevel) &lt;= 0)</CODE>
      <BR>Where {@code actualLevel} is equal to
      <BR> &nbsp; &nbsp; <CODE>actual_level1.{@link com.github.xbn.io.DebugLevel#getHighestLevel(DebugLevel...) getHighestLevel}(actualLevels_2AndUp)</CODE>
      @exception  NoSuchElementException  If <CODE>{@link #getMap() getMap}().{@link java.util.Map#containsKey(Object)}(name)</CODE> is {@code false}.
    **/
   public boolean isActive(String name, DebugLevel actual_level1, DebugLevel... actualLevels_2AndUp)  {
      if(!getMap().containsKey(name))  {
         throw  new NoSuchElementException("name=\"" + name + "\"");
      }

      if(dbgAptrAllQueries.isUseable())  {
         dbgAptrAllQueries.appentln("NamedDebuggers.isActive query for \"" + name + "\".");
      }

      DebugLevel nameLevel = getMap().get(name);
      if(nameLevel == null)  {
         return  false;
      }
      if(nameLevel.isOff())  {
         return  true;
      }
      DebugLevel actualLevel = actual_level1.getHighestLevel(actualLevels_2AndUp);

      return  (nameLevel.compareTo(actualLevel) <= 0);  //nameLevel <= actualLevel
   }
   /**
      <P>If a named-level is active, get an appenter for it.</P>

      @return  <CODE>({@link #isActive(String, DebugLevel, DebugLevel...) isActive}(name, actual_level1, actualLevels_2AndUp)
      <BR> &nbsp; &nbsp; ? &nbsp;debug_aptr : null)</CODE>
      @see  #getAppendableIfActive(String, TextAppenter, DebugLevel, DebugLevel...)
    **/
   public TextAppenter getAppenterIfActive(String name, TextAppenter debug_aptr, DebugLevel actual_level1, DebugLevel... actualLevels_2AndUp)  {
      return  (isActive(name, actual_level1, actualLevels_2AndUp)
         ?  debug_aptr : null);
   }
   /**
      <P>If a named-level is active, get an appendable for it.</P>

      @return  <CODE>({@link #isActive(String, DebugLevel, DebugLevel...) isActive}(name, actual_level1, actualLevels_2AndUp)
      <BR> &nbsp; &nbsp; ? &nbsp;debug_aptr.{@link TextAppenter#getAppendable() getAppendable}() : null)</CODE>
      @see  #getAppendableIfActive(String, TextAppenter, DebugLevel, DebugLevel...)
    **/
   public Appendable getAppendableIfActive(String name, TextAppenter debug_aptr, DebugLevel actual_level1, DebugLevel... actualLevels_2AndUp)  {
      return  (isActive(name, actual_level1, actualLevels_2AndUp)
         ?  debug_aptr.getAppendable() : null);
   }
   /**
      <P>Creates a new map associated level names to level numbers, as read in from a configuration text file.</P>

      @return  <CODE>{@link #newMapFromConfigFile(Map, Iterator, String, Appendable) newMapFromConfigFile}(map_toAddToIfNonNull,
         <BR> &nbsp; &nbsp; {@link PlainTextFileUtil}.{@link PlainTextFileUtil#getLineIterator(String, String) getLineIterator}(configFile_path, path_varName),
         <BR> &nbsp; &nbsp; path_varName, debugAll_ifNonNull)</CODE>
    **/
   public static final Map<String,DebugLevel> newMapFromConfigFile(Map<String,DebugLevel> map_toAddToIfNonNull, String configFile_path, String path_varName, Appendable debugAll_ifNonNull)  {
      return  newMapFromConfigFile(map_toAddToIfNonNull,
         PlainTextFileUtil.getLineIterator(configFile_path, path_varName),
         path_varName, debugAll_ifNonNull);
   }
   /**
      <P>Creates a new map associated level names to level numbers, as read in from a configuration text file.</P>

      <H4>Configuration file format</H4>

      <P>Overall:<UL>
         <LI>Whitespace at the ends of lines is permissible.</LI>
         <LI>Lines (after trimming) that start with a hash ({@code '#'}) are ignored.</LI>
         <LI>Empty lines are permissible.</LI>
      </UL></P>

      <P>Each line is in the format</P>

<BLOCKQUOTE><PRE><I>[level-name]</I>=<I>[level-number]</I></PRE></BLOCKQUOTE>

      <P><UL>
         <LI><I>[level-name]</I> is the descriptive name of the level. Must be non-{@code null}, and must be in the same format as a valid Java package name--that is, it must match
         <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.util.JavaRegexes}.{@link com.github.xbn.util.JavaRegexes#PACKAGE_NAME PACKAGE_NAME}</CODE>
         <BR>Names must be unique.</LI>
         <LI><I>[level-number]</I> is a number between {@code -1} and {@code 5}, inclusive.<UL>
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
         </UL>When a named level is output, it is &quot;{@linkplain #isActive(String, DebugLevel, DebugLevel...) active}&quot;.</LI>
      </UL></P>

      <H4>Example config file</H4>

<BLOCKQUOTE><PRE>### Possible values are -1, 0, 1, 2, 3, 4, 5
BasicCustomizers.eliminateCmtBlocksPkgLineAndPkgReferences.alterers=3
BasicCustomizers.lineRange.alterers.eliminateIndentationOrNull=5
BasicCustomizers.lineRange.alterers=3
BasicCustomizers.lineRange.filter=1
BasicCustomizers.lineRange.template=2
CodeletTemplateBase.newTemplateFromPath.templateparseandfill=3
CodeletTemplateBase.templateparseandfill=3
ConsoleOutProcessor.consoleoutputfromexamplecodestringsig=1
SourceAndOutProcessor.template=2
SourceCodeProcessor.progress=1
TagletOfTypeProcessor.classcustomizersplit=2
TagletOfTypeProcessor.getCustomizerSigFromString=1
TagletOfTypeProcessor.newInstructionsForDefaults.tabtospacealterer=3
TagletOfTypeProcessor.newInstructionsForDefaults.templateparseandfill=3
TagletProcessor.codeletfound=0
configuration.allvaluessummary=1
configuration.progress=0
configuration.templateoverrides.allentriespostloaded=3</PRE></BLOCKQUOTE>

      @param  map_toAddToIfNonNull  If non-{@code null}, the lines read in from the configuration file are added to this map. Must allow <!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="http://docs.oracle.com/javase/7/docs/api/java/util/Map.html#put(K, V)">{@code put}</A>, and may not contain any names in the configuration file.
      @param  configFile_lineItr  May not be {@code null}, and must refer to a validly-formatted configuration file.
      @param  itr_VarName  Descriptive name of {@code configFile_lineItr}. <I>Should</I> not be {@code null} or empty.
      @param  debugAll_ifNonNull  If non-{@code null}, each line is output via this, before being added to the map. The current size of the map is also output.
      @exception  NamedDebuggerFormatException  If a line is badly formatted or contains illegal values.
      @exception  BadDuplicateException  If two lines contain the same name.
   	@exception  UnsupportedOperationException  If {@code put} is unsupported by {@code map_toAddToIfNonNull}.
      @see  #newMapFromConfigFile(Map, String, String, Appendable)
   	@see  #NamedDebuggers(Map, String, String, Appendable)
   	@see  #NamedDebuggers(Map, Iterator, String, Appendable)
    **/
   public static final Map<String,DebugLevel> newMapFromConfigFile(Map<String,DebugLevel> map_toAddToIfNonNull, Iterator<String> configFile_lineItr, String itr_VarName, Appendable debugAll_ifNonNull)  {
      TextAppenter dbgAll = NewTextAppenterFor.appendableUnusableIfNull(debugAll_ifNonNull);
      Map<String,DebugLevel> map = ((map_toAddToIfNonNull == null)
         ?  new TreeMap<String,DebugLevel>()
         :  map_toAddToIfNonNull);

      try  {
         int lineNum = 0;
         while(configFile_lineItr.hasNext())  {
            lineNum++;
            String line = configFile_lineItr.next().trim();

            if(line.length() == 0  ||  line.charAt(0) == '#')  {
               continue;
            }

            if(dbgAll.isUseable())  {
               dbgAll.appentln("[" + itr_VarName + ":" + lineNum + ", mapsize=" + map.size() + "] About to add \"" + line + "\".");
            }

            String[] nameLevel = line.split("=");
            if(nameLevel.length != 2)  {
               throw  new NamedDebuggerFormatException("[" + itr_VarName + ":" + lineNum + ", mapsize=" + map.size() + "] Does not contain exactly one equals sign ('='). line=\"" + line + "\"");
            }
            String name = nameLevel[0];
            if(!pkgMtchr.reset(name).matches())  {
               throw  new NamedDebuggerFormatException("[" + itr_VarName + ":" + lineNum + ", mapsize=" + map.size() + "] Name (\"" + name + "\") does not match JavaRegexes.PACKAGE_NAME. line=\"" + line + "\"");
            }
            DebugLevel level = null;
            try  {
               level = DebugLevel.getFromString012345OrNeg1ForNull(nameLevel[1], null);
            }  catch(NamedDebuggerFormatException iax)  {
               throw  new NamedDebuggerFormatException("[" + itr_VarName + ":" + lineNum + ", mapsize=" + map.size() + "] Level number (\"" + nameLevel[1] + "\") is not valid: \"" + line + "\"", iax);
            }

            try  {
               MapUtil.putOrCrashIfContainsKey(map, itr_VarName, name, Null.BAD,
                  "[debug-item-name]", level, Null.OK, "[debug-item-level]");
            }  catch(RuntimeException rx)  {
               throw  new NamedDebuggerFormatException("itr_VarName=" + itr_VarName + ", line=\"" + line + "\", name=" + name + ", level=" + level, rx);
            }
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(configFile_lineItr, itr_VarName, null, rx);
      }
      return  map;
   }
      private static final Matcher pkgMtchr = Pattern.compile(JavaRegexes.PACKAGE_NAME).matcher("");
}
