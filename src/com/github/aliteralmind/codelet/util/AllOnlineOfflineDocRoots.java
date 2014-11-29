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
	import  com.github.xbn.io.NewTextAppenterFor;
	import  com.github.xbn.lang.BadDuplicateException;
	import  com.github.xbn.lang.CrashIfObject;
	import  com.github.xbn.util.IfError;
	import  java.util.Collections;
	import  java.util.Iterator;
	import  java.util.Map;
	import  java.util.TreeMap;
	import  java.util.regex.Pattern;
	import  static com.github.xbn.lang.XbnConstants.*;
/**
	<p>Collection of {@code package-list}s from all external Java libraries used by a project, for mapping a package name to its JavaDoc document root url--even if that online {@code package-list} is inaccessible. This information is the equivalent of {@code javadoc.exe}'s <a href="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#link">{@code -link}</a> and {@code -linkoffline} options.</p>

	<p><i>While it may be possible to read in the values of {@code -link} and {@code -linkoffline}, as passed into {@code javadoc.exe}, doing so would make Codelet more dependant on {@code com.sun.javadoc}, which is against its goal of <a href="http://stackoverflow.com/questions/23138806/how-to-make-inline-taglets-which-require-com-sun-more-cross-platform-is-there">minimizing dependencies</a> on this non-standard package.</i></p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class AllOnlineOfflineDocRoots  {
	private final Map<String,OnlineOfflineDocRoot> nameToRootMap;
	private final Map<String,OnlineOfflineDocRoot> urlToRootMap ;
	private final Map<String,String>               pkgToUrlMap  ;
	/**
		<p>An immutable map whose key is the doc-root's {@linkplain OnlineOfflineDocRoot#getName() name}, and value is its {@code OnlineOfflineDocRoot}.</p>

		@see  #getPkgToUrlMap()
	 **/
	public Map<String,OnlineOfflineDocRoot> getNameToRootMap()  {
		return  nameToRootMap;
	}
	/**
		<p>An immutable map whose key is the doc-root's {@linkplain OnlineOfflineDocRoot#getUrlDir() url}, and value is its {@code OnlineOfflineDocRoot}.</p>

		@see  #getPkgToUrlMap()
	 **/
	public Map<String,OnlineOfflineDocRoot> getUrlToRootMap()  {
		return  urlToRootMap;
	}
	/**
		<p>An immutable map whose key is a {@linkplain OnlineOfflineDocRoot#getPackageList() package}, and value is its document root url.</p>

		@see  #getUrlToRootMap()
		@see  #getNameToRootMap()
	 **/
	public Map<String,String> getPkgToUrlMap()  {
		return  pkgToUrlMap;
	}
	/**
		@return  <code>{@link #appendToString(StringBuilder) appendToString}(new StringBuilder()).toString()</code>
	 **/
	public String toString()  {
		return  appendToString(new StringBuilder()).toString();
	}
	/**
		@param  to_appendTo May not be {@code null}.
		@see  #toString()
	 **/
	public StringBuilder appendToString(StringBuilder to_appendTo)  {
		try  {
			to_appendTo.append("All doc-roots:").append(LINE_SEP);
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
		}

		for (Map.Entry<String,OnlineOfflineDocRoot> entry : getNameToRootMap().entrySet())  {
			to_appendTo.append(" - ").append(entry.getValue()).append(LINE_SEP);
		}

		return  to_appendTo;
	}
	/**
		<p>Create a new {@code AllOnlineOfflineDocRoots} from a configuration file, where each line contains two items: the doc-root's offline file path and online document-root url.</p>

		<p>Each line in a configuration file is in the format</p>

<blockquote><pre>[offline_file_path] [url]</pre></blockquote>

		<p>Where<ul>
			<li><code>[offline_file_path]</code> is the full path of the locally-stored file. This must exist, and be both readable and writable.</li>
			<li><code>[url]</code> is the url to the JavaDoc document root ({@code "{@docRoot}"}) for an external Java library. Must end with a slash ({@code '/'}), and must contain the library's {@code "package-list"} file.</li>
		</ul>The file-path and url are separated with at least one space or tab.</p>

		<p>(Lines starting with {@code '#'} are ignored. Empty lines are not allowed.)</p>

		<h4>Example</h4>

		<p>If both {@code offlineName_prefixPath} and {@code offlineName_postfix} are {@code null}:</p>

<blockquote>{@code C:\java_code\config\javadoc_offline_package_lists\java.txt   http://docs.oracle.com/javase/7/docs/api/}</blockquote>

		<p>An equivalent is to set<ul>
			<li>{@code offlineName_prefixPath} to {@code "C:\java_code\config\javadoc_offline_package_lists\"}</li>
			<li>and {@code offlineName_postfix} to {@code ".txt"}</li>
		</ul>and then the config line can be</p>

<blockquote>{@code java   http://docs.oracle.com/javase/7/docs/api/}</blockquote>

		<p>Steps for each line in {@code line_itr}:<ol>
			<li>If {@code online_attemptCount} is<ul>
				<li>Greater than zero: This creates the {@code OnlineOfflineDocRoot} with
				<br/> &nbsp; &nbsp; <code>{@link OnlineOfflineDocRoot}.{@link OnlineOfflineDocRoot#newFromOnline(String, String, String, int, long, IfError, RefreshOffline, Appendable, Appendable) newFromOnline}(name, url, path, online_attemptCount, online_sleepMills, if_error, refresh_offline, debug_ifNonNull, dbgError_ifNonNull)</code></li>
				<li>Equal to zero (<i>or if retrieving from online fails, and {@code if_error.}{@link com.github.xbn.util.IfError#WARN WARN}</i>): This creates the {@code OnlineOfflineDocRoot} with
				<br/> &nbsp; &nbsp; <code>OnlineOfflineDocRoot.{@link OnlineOfflineDocRoot#newFromOffline(String, String, String, Appendable, Appendable) newFromOffline}(name, url, path, debug_ifNonNull, dbgError_ifNonNull)</code></li>
			</ul></li>
		</ol></p>

		@param  line_itr  May not be {@code null}, and <i>should</i> have at least one item. All offline paths must be unique, and all urls must be unique.
		@param  offlineName_prefixPath  If non-{@code null}, this is the base directory appended to each offline name, as described above. Setting this to {@code null} is the same as the empty string ({@code ""}).
		@param  offlineName_postfix  If non-{@code null}, this is the postfix appended to each offline name.
		@param  refresh_offline  When {@code online_attemptCount} is greater than zero, should the offline {@code package-list} be refreshed from the online version? If {@code online_attemptCount} is zero, this parameter is ignored.
		@see  com.github.aliteralmind.codelet.CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE
	 **/
	public static final AllOnlineOfflineDocRoots newFromConfigLineIterator(Iterator<String> line_itr, String offlineName_prefixPath, String offlineName_postfix, int online_attemptCount, long online_sleepMills, RefreshOffline refresh_offline, IfError if_error, Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  throws InterruptedException  {

		Map<String,OnlineOfflineDocRoot> nameToRootMap = new TreeMap<String,OnlineOfflineDocRoot>();
		Map<String,OnlineOfflineDocRoot> urlToRootMap = new TreeMap<String,OnlineOfflineDocRoot>();
		Map<String,String> pkgToUrlMap = new TreeMap<String,String>();

		int lineNum = 1;
		try  {
			while(line_itr.hasNext())  {
				String line = line_itr.next();
				if(line.startsWith("#"))  {
					continue;
				}
				String[] nameUrl = oneOrMoreSpcTabPtrn.split(line, 0);
				if(nameUrl.length != 2)  {
					String msg = "[line=" + lineNum + "] Line has " + nameUrl.length + " parts. Must have two: \"" + line + "\"";
					NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).
							appentln(msg);
					throw  new IllegalArgumentException(msg);
				}
				String name = nameUrl[0];
				String url = nameUrl[1];
				String path = offlineName_prefixPath + nameUrl[0] + offlineName_postfix;

				OnlineOfflineDocRoot docRoot = null;

				if(online_attemptCount > 0)  {
					docRoot = OnlineOfflineDocRoot.newFromOnline(name, url, path, online_attemptCount, online_sleepMills, if_error, refresh_offline, debug_ifNonNull, dbgError_ifNonNull);
				}

				if(docRoot == null)  {
					//Either retrieving online failed, or online_attemptCount is zero
					NewTextAppenterFor.appendableSuppressIfNull(debug_ifNonNull).appentln("Online package-list not retrieved. Retrieving offline version.");
					docRoot = OnlineOfflineDocRoot.newFromOffline(name, url, path, debug_ifNonNull, dbgError_ifNonNull);
				}

				if(nameToRootMap.containsKey(name))  {
					String msg = "[line=" + lineNum + "] Duplicate name: \"" + line + "\"";
					NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).appentln(msg);
					throw  new BadDuplicateException(msg);
				}

				if(urlToRootMap.containsKey(url))  {
					String msg = "[line=" + lineNum + "] Duplicate url: \"" + url + "\"";
					NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).appentln(msg);
					throw  new BadDuplicateException(msg);
				}

				for(String pkg : docRoot.getPackageList())  {
					if(pkgToUrlMap.containsKey(pkg))  {
						String msg = "[line=" + lineNum + "] Duplicate package: \"" + pkg + "\". In both " + docRoot.getName() + " and " + urlToRootMap.get(pkgToUrlMap.get(pkg)).getName() + ".";
						if(dbgError_ifNonNull != null)  {
							NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).appentln(msg);
						}
						throw  new BadDuplicateException(msg);
					}
					pkgToUrlMap.put(pkg, url);
					pkgToUrlMap.put(pkg, docRoot.getUrlDir());
				}

				nameToRootMap.put(name, docRoot);
				urlToRootMap.put(url, docRoot);

				lineNum++;
			}
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(line_itr, "line_itr", null, rx);
		}
		return  new AllOnlineOfflineDocRoots(
			Collections.unmodifiableMap(nameToRootMap),
			Collections.unmodifiableMap(urlToRootMap),
			Collections.unmodifiableMap(pkgToUrlMap));
	}
		private static final Pattern oneOrMoreSpcTabPtrn = Pattern.compile("[ \t]+");
		private AllOnlineOfflineDocRoots(Map<String,OnlineOfflineDocRoot> nameToRoot_map, Map<String,OnlineOfflineDocRoot> urlToRoot_map, Map<String,String> pkgToUrl_map)  {
			nameToRootMap = nameToRoot_map;
			urlToRootMap  = urlToRoot_map;
			pkgToUrlMap   = pkgToUrl_map;
		}
}