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
package  com.github.aliteralmind.codelet.util;
   import  com.github.xbn.array.Duplicates;
   import  com.github.xbn.array.NullContainer;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.io.IOUtil;
   import  com.github.xbn.io.NewPrintWriterToFile;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.io.RTIOException;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.keyed.SimpleNamed;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.list.CrashIfList;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.xbn.text.StringUtil;
   import  com.github.xbn.util.IfError;
   import  com.github.xbn.util.JavaRegexes;
   import  java.io.IOException;
   import  java.io.PrintWriter;
   import  java.util.ArrayList;
   import  java.util.Arrays;
   import  java.util.Collections;
   import  java.util.Iterator;
   import  java.util.List;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>Represents the <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linkpackagelist">{@code package-list}</A> for a single external Java library, including a duplicate offline file that is automatically updated from it.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class OnlineOfflineDocRoot extends SimpleNamed  {
   private final String urlDir;
   private final String path;
   private final List<String> pkgList;
   /**
      <P>Create a new instance from a url, offline path, and package list.</P>

      @param  name  Descriptive name of the external library. May not be {@code null} or empty, and must contain only letters, digits, and underscores. Get with {@link com.github.xbn.keyed.SimpleNamed#getName() getName}{@code ()}*
      @param  url_toDocRoot  The url directory in which the <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linkpackagelist">{@code package-list}</A> file exists. <I>Should</I> be a valid url and end with a slash ({@code '/'}). Get with {@link #getUrlDir() getUrlDir}{@code ()}.
      @param  offline_path  The full path of the offline duplicate of the {@code package-list} file. <I>Should</I> be a valid represent a text file that is both readable and writable. Get with {@link #getPath() getPath}{@code ()}
      @param  package_list  The list of packages in the external library, as found in {@code package-list}. May not be {@code null}, empty, and its elements may not be {@code null}, empty, or duplicate. Get (a duplicate of this list) with {@link #getPackageList() getPackageList}{@code ()}.
      @see  #newFromOnline(String, String, String, int, long, IfError, RefreshOffline, Appendable, Appendable) newFromOnline
      @see  #newFromOffline(String, String, String, Appendable, Appendable) newFromOffline
    **/
   public OnlineOfflineDocRoot(String name, String url_toDocRoot, String offline_path, List<String> package_list)  {
      this(false, name, url_toDocRoot, offline_path, package_list);
      CrashIfList.bad(package_list, "package_list", NullContainer.BAD, 1, null, NullElement.BAD, 1, null, Duplicates.BAD);
   }
      /**
         <P>Avoids calling CrashIfList.bad when this OnlineOfflineDocRoot is internally created.</P>
       **/
      private OnlineOfflineDocRoot(boolean ignored, String name, String url_toDocRoot, String offline_path, List<String> package_list)  {
         super(name);
         CrashIfString.nullEmpty(url_toDocRoot, "url_toDocRoot", null);
         CrashIfString.nullEmpty(offline_path, "offline_path", null);
         urlDir = url_toDocRoot;
         path = offline_path;
         try  {
            pkgList = Collections.<String>unmodifiableList(package_list);
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(package_list, "package_list", null, rx);
         }
      }
   /**
      <P>Url of the directory in which the {@code package-list} file exists.</P>

      @return  A non-{@code null} url, ending with a slash ({@code '/'}).
      @see  #getPath()
    **/
   public String getUrlDir()  {
      return  urlDir;
   }
   /**
      <P>Full path to the offline duplicate of the {@code package-list} file.</P>

      @return  A non-{@code null} path, including the file-name.
      @see  #getUrlDir()
    **/
   public String getPath()  {
      return  path;
   }
   /**
      <P>An immutable list of all package names.</P>
    **/
   public List<String> getPackageList()  {
      return  pkgList;
   }
   /**
      <P>Overwrites or creates offline file with the current package list.</P>

      <P>This overwrites the current {@linkplain #getPath() offline file} with the contents of the {@linkplain #getPackageList() package list}.</P>

      @param  debug_ifNonNull  If non-{@code null}, the destination for progress debugging.
      @param  dbgError_ifNonNull  If non-{@code null}, the destination for the error debugging. If {@code if_error.WARN}, this parameter may not be {@code null}.
      @exception  RTFileNotFoundException  If a {@link java.io.FileNotFoundException FileNotFoundException} is thrown when trying to open the file. The original exception (for this and all exceptions thrown by this function) is accessible with {@link java.lang.Throwable#getCause() getCause}{@code ()}.
      @exception  RTIOException  If an {@link java.io.IOException IOException} is thrown when trying to open the file.
      @exception  SecurityException  If the file is not writable.
      @exception  RuntimeException  If the file is successfully opened, but cannot be written to.
      @see  #getPackageList()
    **/
   public void refreshOffline(Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  {
      PrintWriter pw = null;

      if(debug_ifNonNull != null)  {
         NewTextAppenterFor.appendable(debug_ifNonNull).
            appentln("refreshOffline:" + toString());
      }

      pw = new NewPrintWriterToFile().overwrite().autoFlush().build(getPath());

      for(String pkg : getPackageList())  {
         try  {
            pw.println(pkg);
         }  catch(Exception x)  {
            throw  new RuntimeException("getPath()=\"" + getPath() + "\"", x);
         }
      }
   }
   /**
      @return  <CODE>{@link #appendToString(StringBuilder) appendToString}(new StringBuilder()).toString()</CODE>
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
         to_appendTo.append(getName()).append("(").
            append(getPackageList().size()).append(" package");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }
      if(getPackageList().size() > 1)  {
         to_appendTo.append("s");
      }
      to_appendTo.append("): ").append(getUrlDir());

      return  to_appendTo;
   }
   /**
      <P>Create a new instance from an online {@code package-list}.</P>

      @param  refresh_offline  If {@link com.github.aliteralmind.codelet.util.RefreshOffline#YES YES}, then this ends by calling
      <BR> &nbsp; &nbsp; <CODE><I>[the-new-OnlineOfflineDocRoot]</I>.{@link #refreshOffline(Appendable, Appendable) refreshOffline}(debug_ifNonNull, dbgError_ifNonNull)</CODE>
      <BR>This parameter may not be {@code null}.
      @return  <CODE>new {@link #OnlineOfflineDocRoot(String, String, String, List) OnlineOfflineDocRoot}(url_toDocRoot, offline_path,
      <BR> &nbsp; &nbsp; {@link #newPackageListFromOnline(String, int, long, IfError, Appendable, Appendable) newPackageListFromOnline}(offline_path,
      <BR> &nbsp; &nbsp; &nbsp; &nbsp; error_attemptCount, error_sleepMills, if_error, debug_ifNonNull, dbgError_ifNonNull))</CODE>
      <BR>If the package list cannot be retrieved, and {@code if_error.}{@code com.github.xbn.util.IfError#WARN WARN}, this returns {@code null}.
      <BR> &nbsp; &nbsp;
      @see  #newFromOffline(String, String, String, Appendable, Appendable) newFromOffline
    **/
   public static final OnlineOfflineDocRoot newFromOnline(String name, String url_toDocRoot, String offline_path, int error_attemptCount, long error_sleepMills, IfError if_error, RefreshOffline refresh_offline, Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  throws InterruptedException  {
      List<String> pkgList = newPackageListFromOnline(url_toDocRoot,
         error_attemptCount, error_sleepMills, if_error, debug_ifNonNull, dbgError_ifNonNull);
      if(pkgList == null)  {
         return  null;
      }
      OnlineOfflineDocRoot docRoot = new OnlineOfflineDocRoot(false, name,
         url_toDocRoot, offline_path, pkgList);
      try  {
         if(refresh_offline.isYes())  {
            docRoot.refreshOffline(debug_ifNonNull, dbgError_ifNonNull);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(refresh_offline, "refresh_offline", null, rx);
      }
      return  docRoot;
   }
   /**
      <P>Create a new instance from an offline {@code package-list}.</P>

      @return  <CODE>new {@link #OnlineOfflineDocRoot(String, String, String, List) OnlineOfflineDocRoot}(url_toDocRoot, offline_path,
      <BR> &nbsp; &nbsp; {@link #newPackageListFromOffline(String, Appendable, Appendable) newPackageListFromOnline}(offline_path, if_error, debug_ifNonNull, dbgError_ifNonNull))</CODE>
      @see  #newFromOnline(String, String, String, int, long, IfError, RefreshOffline, Appendable, Appendable) newFromOnline
    **/
   public static final OnlineOfflineDocRoot newFromOffline(String name, String url_toDocRoot, String offline_path, Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  {
      return  new OnlineOfflineDocRoot(false, name, url_toDocRoot, offline_path,
         newPackageListFromOffline(offline_path, debug_ifNonNull, dbgError_ifNonNull));
   }
   /**
      <P>Create a package list from an offline {@code package-list} file.</P>

      @param  pkgList_path  The full path to the local package-list file. Must represent a file that is  readable and writable (writability is not verified until the offline file is {@link #refreshOffline(Appendable, Appendable) refreshed}), and a valid <A HREF="http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#linkpackagelist">{@code package-list}</A>.
      @param  debug_ifNonNull  If non-{@code null}, the destination for progress debugging.
      @param  dbgError_ifNonNull  If non-{@code null}, the destination for the error debugging.
      @exception  RuntimeException  If opening or reading the file fails. The original exception is accessible with {@link java.lang.Throwable#getCause() getCause}{@code ()}.
      @see  #newFromOffline(String, String, String, Appendable, Appendable) newFromOffline
      @see  #newPackageListFromOnline(String, int, long, IfError, Appendable, Appendable) newPackageListFromOnline
    **/
   public static final List<String> newPackageListFromOffline(String pkgList_path, Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  {
      if(debug_ifNonNull != null)  {
         NewTextAppenterFor.appendable(debug_ifNonNull).
            appentln("newPackageListFromOffline:\"" + pkgList_path + "\"");
      }
      Iterator<String> lineItr = null;
      try  {
         lineItr = PlainTextFileUtil.getLineIterator(pkgList_path, "pkgList_path");
      }  catch(Exception x)  {
         String msg = "Unable to retrieve package-list from pkgList_path=\"" + pkgList_path + "\"";
         NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).
            appentln(msg + ": " + x);

         throw  new RuntimeException(msg, x);
      }
      try  {
         return  newPkgListFromLineItr(lineItr);
      }  catch(PackageListRetrievalFailedException plfx)  {
         String msg = "pkgList_path=\"" + pkgList_path + "\"";
         NewTextAppenterFor.appendableSuppressIfNull(dbgError_ifNonNull).
            appentln(msg + ": " + plfx);
         throw  new PackageListRetrievalFailedException(msg, plfx);
      }
   }
   /**
      <P>Create a package list from an online {@code package-list}.</P>

      @param  url_toDocRoot  The url directory in which a valid <A HREF="http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/javadoc.html#package-list">{@code package-list}</A> file exists. Must end with a slash ({@code '/'}).
      @param  error_attemptCount  The number of attempts to make when retrieving the {@code package-list} fails. May not be less than one.
      @param  error_sleepMills  The number of milliseconds to {@linkplain java.lang.Thread#sleep(long) sleep} between each attempt. May not be less than zero.
      @param  if_error  If {@link com.github.xbn.util.IfError#WARN WARN} then, after all attempts fail, the error is logged to {@code dbgError_ifNonNull}. If {@link com.github.xbn.util.IfError#CRASH CRASH} a {@code RuntimeException} is also thrown. This parameter may not be {@code null}.
      @param  dbgError_ifNonNull  The destination for the error-warning message. If {@code if_error.WARN}, may not be {@code null}.
      @return  If the {@code package-list}<UL>
         <LI>Was successfully retrieved: A non-{@code null} list containing all its packages.</LI>
         <LI>Could not be retrieved and {@code if_error.WARN}: {@code null}</LI>
      </UL>
      @exception  RuntimeException  If any error occurs when retrieving the {@code package-list}. The causing error is accessible with {@link java.lang.Throwable#getCause() getCause}{@code ()}.
      @exception  RTIOException  If using {@code dbgError_ifNonNull} fails.
      @see  #newFromOnline(String, String, String, int, long, IfError, RefreshOffline, Appendable, Appendable) newFromOnline
      @see  #newPackageListFromOffline(String, Appendable, Appendable) newPackageListFromOffline
    **/
   public static final List<String> newPackageListFromOnline(String url_toDocRoot, int error_attemptCount, long error_sleepMills, IfError if_error, Appendable debug_ifNonNull, Appendable dbgError_ifNonNull)  throws InterruptedException  {
      TextAppenter dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(debug_ifNonNull);
      TextAppenter dbgErrAptr = null;
      try  {
         dbgErrAptr = if_error.newAptrForApblCrashIfWarn(dbgError_ifNonNull, "dbgError_ifNonNull");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(if_error, "if_error", null, rx);
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("Loading package-list from url(if_error=" + if_error + "): \"" + url_toDocRoot + "\"");
      }
      String text = null;
      String errorMsg = null;
      Exception x = null;
      while(error_attemptCount > 0)  {
         try  {
            text = IOUtil.getWebPageSourceX(url_toDocRoot + "package-list", null);

            if(text != null)  {
               return  newPkgListFromLineItr(StringUtil.getLineIterator(text));
            }
         }  catch(Exception x2)  {
            errorMsg = "Failure loading package list from url: \"" + url_toDocRoot + "\"";
            x = x2;
            dbgErrAptr.appentln(errorMsg + ": " + x2);
         }

         error_attemptCount--;

         if(error_attemptCount > 0)  {
            dbgErrAptr.appentln("newPackageListFromOnline(if_error=" + if_error + "):\"" + url_toDocRoot + "\"");
            dbgErrAptr.appentln("Pausing " + error_sleepMills + ". Attempts remaining=" + error_attemptCount);
            Thread.sleep(error_sleepMills);
         }
      }

      //If if_error is null, that will have been caught by printWarning_crashIfApblNull
      if(if_error.isCrash())  {
         throw  new PackageListRetrievalFailedException(errorMsg, x);
      }
      return  null;
   }
      private static final List<String> newPkgListFromLineItr(Iterator<String> line_itr)  {
         List<String> pkgList = new ArrayList<String>(10);
         int lineNum = 1;
         while(line_itr.hasNext())  {
            String pkg = line_itr.next();
            if(!pkgMtchr.reset(pkg).matches())  {
               throw  new IllegalArgumentException("Line " + lineNum + " is not a valid package: \"" + pkg + "\"");
            }
            pkgList.add(pkg);
            lineNum++;
         }
         Collections.sort(pkgList);
         return  pkgList;
      }
      private static final Matcher pkgMtchr = Pattern.compile(JavaRegexes.PACKAGE_NAME).matcher("");
}
