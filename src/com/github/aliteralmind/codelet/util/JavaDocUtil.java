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
   import  com.github.xbn.util.JavaRegexes;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.regexutil.NewPatternFor;
   import  java.lang.reflect.Constructor;
   import  java.lang.reflect.Field;
   import  java.lang.reflect.Method;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <p>Get urls to function, class, and object targets.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class JavaDocUtil  {
   /**
      <p>Get the url to a class.</p>

    * @return  <code>{@link #appendUrlToClass(StringBuilder, String, Class) appendUrlToClass}((new StringBuilder()), url_toDocRoot, target).toString()</code>
    */
   public static final String getUrlToClass(String url_toDocRoot, Class<?> target)  {
      return  appendUrlToClass((new StringBuilder()), url_toDocRoot, target).toString();
   }
   /**
      <p>Append the url to a class.</p>

    * @param  to_appendTo  May not be {@code null}.
    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.

    * @param  target  May not be {@code null}.
    * @return  <code>to_appendTo.append(url_toDocRoot).
      <br> &nbsp; &nbsp; append(target.{@link java.lang.Class#getName() getName}.replace(".", "/")).append(".html")</code>
    * @see  #getUrlToClass(String, Class)
    */
   public static final StringBuilder appendUrlToClass(StringBuilder to_appendTo, String url_toDocRoot, Class<?> target)  {
      try  {
         return  to_appendTo.append(url_toDocRoot).
            append(target.getName().replace(".", "/")).append(".html");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
   }
   /**
      <p>Get the url to a constructor.</p>

    * @return  <code>{@link #appendUrlToConstructor(StringBuilder, String, Constructor) appendUrlToConstructor}((new StringBuilder()), url_toDocRoot, target).toString()</code>
    */
   public static final String getUrlToConstructor(String url_toDocRoot, Constructor target)  {
      return  appendUrlToConstructor((new StringBuilder()), url_toDocRoot, target).toString();
   }
   /**
      <p>Append the url to a constructor.</p>

    * @param  to_appendTo  May not be {@code null}.
    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.
    * @param  target  May not be {@code null}.
    * @return  {@code to_appendTo}
    * @see  #getUrlToConstructor(String, Constructor) getUrlToConstructor
    * @see  #appendUrlToMethod(StringBuilder, String, Method) appendUrlToMethod
    * @see  #appendUrlToClass(StringBuilder, String, Class) appendUrlToClass
    * @see  #appendClassNameForParams(StringBuilder, Class[], VarArgs) appendClassNameForParams
    */
   public static final StringBuilder appendUrlToConstructor(StringBuilder to_appendTo, String url_toDocRoot, Constructor<?> target)  {
      try  {
         appendUrlToClass(to_appendTo, url_toDocRoot, target.getDeclaringClass());
         to_appendTo.append("#").append(target.getDeclaringClass().getSimpleName()).append("(");
         appendClassNameForParams(to_appendTo, target.getParameterTypes(), VarArgs.getForBoolean(target.isVarArgs())).append(")");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  to_appendTo;
   }
   /**
      <p>Get the url to a function.</p>

    * @return  <code>{@link #appendUrlToConstructor(StringBuilder, String, Constructor) appendUrlToConstructor}((new StringBuilder()), url_toDocRoot, target).toString()</code>
    */
   public static final String getUrlToMethod(String url_toDocRoot, Method target)  {
      return  appendUrlToMethod((new StringBuilder()), url_toDocRoot, target).toString();
   }
   /**
      <p>Append the url to a function.</p>

      <h4>Example</h4>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.util.FunctionConstructorJavaDocLink%_JavaDocUtil()}


    * @param  to_appendTo  May not be {@code null}.
    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.
    * @param  target  May not be {@code null}.
    * @return  {@code to_appendTo}
    * @see  #getUrlToMethod(String, Method) getUrlToMethod
    * @see  #appendUrlToConstructor(StringBuilder, String, Constructor) appendUrlToMethod
    * @see  #appendUrlToClass(StringBuilder, String, Class) appendUrlToClass
    * @see  #appendClassNameForParams(StringBuilder, Class[], VarArgs) appendClassNameForParams
    */
   public static final StringBuilder appendUrlToMethod(StringBuilder to_appendTo, String url_toDocRoot, Method target)  {
      try  {
         appendUrlToClass(to_appendTo, url_toDocRoot, target.getDeclaringClass());
         to_appendTo.append("#").append(target.getName()).append("(");
         appendClassNameForParams(to_appendTo, target.getParameterTypes(), VarArgs.getForBoolean(target.isVarArgs())).append(")");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  to_appendTo;
   }
   /**
      <p>Get the url to a field.</p>

    * @return  <code>{@link #appendUrlToField(StringBuilder, String, Field) appendUrlToField}((new StringBuilder()), url_toDocRoot, target).toString()</code>
    */
   public static final String getUrlToField(String url_toDocRoot, Field target)  {
      return  appendUrlToField((new StringBuilder()), url_toDocRoot, target).toString();
   }
   /**
      <p>Append the url to a field.</p>

    * @return  <code>{@link #appendUrlToClass(StringBuilder, String, Class) appendUrlToClass}(to_appendTo, url_toDocRoot, target.{@link java.lang.reflect.Field#getDeclaringClass() getDeclaringClass}()).
      <br> &nbsp; &nbsp; append(&quot;#&quot;).append(target.{@link java.lang.reflect.Field#getName() Field#getName}())</code>
    * @see  #getUrlToField(String, Field)
    */
   public static final StringBuilder appendUrlToField(StringBuilder to_appendTo, String url_toDocRoot, Field target)  {
      return  appendUrlToClass(to_appendTo, url_toDocRoot, target.getDeclaringClass()).
         append("#").append(target.getName());
   }
   /**
      <p>Get the url from a link-source file's package to doc-root.</p>

    * @return  <code>packageElementMtchr.reset(package_containingLink).{@link java.util.regex.Matcher#replaceAll(String) replaceAll}(&quot;../&quot;)</code>
      <br>Where {@code packageElementMtchr} is initalized to
      <br> &nbsp; &nbsp; <code>{@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(JavaRegexes.IDENTIFIER + &quot;\\.?&quot;).{@link java.util.regex.Pattern#matcher(CharSequence) matcher}(&quot;&quot;)</code>
    */
   public static final String getRelativeUrlToDocRoot(String package_containingLink)  {
      return  packageElementMtchr.reset(package_containingLink).replaceAll("../");
   }
      private static final Matcher packageElementMtchr = Pattern.
         compile(JavaRegexes.IDENTIFIER + "\\.?").matcher("");
   /**
      <p>Get the url from a link-source to the target class file. This always goes entirely down to {@code {@docRoot}}, and then back to the target file.</p>

    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.
    * @param  target  May not be {@code null}.
    * @return  <code>packageElementMtchr.reset(target.{@link java.lang.Class#getName() getName}()).{@link java.util.regex.Matcher#replaceAll(String) replaceAll}(&quot;../&quot;)</code>
      <br>Where {@code packageElementMtchr} is initalized to
      <br> &nbsp; &nbsp; <code>{@link java.util.regex.Pattern Pattern}.{@link java.util.regex.Pattern#compile(String, int) compile}(&quot;.&quot;, Pattern.{@link java.util.regex.Pattern#LITERAL LITERAL}).{@link java.util.regex.Pattern#matcher(CharSequence) matcher}(&quot;&quot;)</code>
    */
   public static final String getUrlToClassViaDocRoot(String url_toDocRoot, Class<?> target)  {
      try  {
         return  url_toDocRoot + dotMtchr.reset(target.getName()).replaceAll("/") + ".html";
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
   }
      private static final Matcher dotMtchr = NewPatternFor.literal(".").matcher("");
   /**
      <p>For the final parameter in a {@link java.lang.reflect.Method#isVarArgs() variable-argument method} or {@link java.lang.reflect.Constructor#isVarArgs() constructor}, that is known to be an array-ellipsis ({@code "..."}), get its JavaDoc-url parameter name.</p>

      <h4>Example</h4>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.util.JavaDocUtilGetEllipsisArrayLastParamXmpl%()}

    * @param  cls  May not be {@code null}.
    * @return  The {@linkplain java.lang.Class#getCanonicalName() canonical class name}, where the final array indicator ({@code "[]"}) is replaced with an ellipsis ({@code "..."}).
    * @exception  IllegalArgumentException  If {@code cls} is not {@linkplain java.lang.Class#isArray() an array}.
    * @see  #appendClassNameForParams(StringBuilder, Class[], VarArgs)
    * @see  java.lang.Class#getCanonicalName() Class#getCanonicalName()
    * @see  java.lang.Class#getCanonicalName()
    */
   public static final String getEllipsisArrayLastParam(Class<?> cls)  {
      try  {
         if(!cls.isArray())  {
            throw  new IllegalArgumentException("cls.isArray() is false.");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(cls, "cls", null, rx);
      }

      //cls.isArray() is true

      //2: Same as "[]".length.

      return  cls.getCanonicalName().substring(0, (cls.getCanonicalName().length() - 2)) + "...";
   }
   /**
      <p>Append the comma-separated list of JavaDoc parameters for a method or constructor.</p>

    * @return  <code>{@link #appendClassNameForParams(StringBuilder, Class[], VarArgs) appendClassNameForParams}((new StringBuilder()), classes, is_varArgs).toString()</code>
    */
   public static final String getClassNameForParams(Class<?>[] classes, VarArgs var_args)  {
      return  appendClassNameForParams((new StringBuilder()), classes, var_args).toString();
   }
   /**
      <p>Append the comma-separated list of JavaDoc parameters for a method or constructor. This does not include the surrounding parentheses.</p>

      <p>This appends the {@linkplain java.lang.Class#getCanonicalName() canonical name} of each class. If {@code is_varArgs} is {@code true}, the final parameter is output with {@link #getEllipsisArrayLastParam(Class) getEllipsisArrayLastParam}.</p>

    * @param  to_appendTo  May not be {@code null}.
    * @param  classes  May not be {@code null}, and {@code is_varArgs} is {@code true}, the final element must be an array (and {@code classes.length} <i>should</i> be non-empty).
    * @param  var_args  If {@link VarArgs#YES YES}, this is a {@link java.lang.reflect.Method#isVarArgs() variable-argument} method or {@link java.lang.reflect.Constructor#isVarArgs() constructor}, meaning the final parameter is an ellipsis array: {@code "..."}. This parameter value may not be {@code null}.
    * @return  {@code to_appendTo}
    * @see  #getClassNameForParams(Class[], VarArgs)
    * @see  #appendUrlToConstructor(StringBuilder, String, Constructor) appendUrlToConstructor
    * @see  #appendUrlToMethod(StringBuilder, String, Method) appendUrlToMethod
    * @see  com.github.xbn.lang.reflect.ReflectRtxUtil#appendClassNames(StringBuilder, String, Class[], String, String) lang.reflect.ReflectRtxUtil#appendClassNames
    */
   public static final StringBuilder appendClassNameForParams(StringBuilder to_appendTo, Class<?>[] classes, VarArgs var_args)  {
      int lenMinus1;
      try  {
         lenMinus1 = classes.length - 1;
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(classes, "classes", null, rx);
      }
      for(int i = 0; i < classes.length; i++)  {
         Class<?> cls = classes[i];

         try  {
            if(var_args.isYes()  &&  i == lenMinus1)  {
               to_appendTo.append(getEllipsisArrayLastParam(cls));
            }  else  {
               to_appendTo.append(cls.getCanonicalName());
            }
         }  catch(RuntimeException rx)  {
            CrashIfObject.nnull(var_args, "var_args", null);
            throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
         }

         if(i < lenMinus1)  {
            to_appendTo.append(", ");
         }
      }
      return  to_appendTo;
   }
   /*
      <p>Creates a new map of all package-lists, for all {@code {@docRoot}}
      urls in a string iterator.</p>

      <p>For each url-item in the iterator, this calls
      <br> &nbsp; &nbsp; <code>{@link #addPackageListToMapFromDocRootUrl(Map, String, Appendable, Appendable) addPackageListToMapFromDocRootUrl}(<i>[the-map]</i>, <i>[the-url]</i>, debugUrls_ifNonNull, debugPkgs_ifNonNull)</code></p>

    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.
    * @return  A new tree-map where each entry's key is a package, and each value is its doc-root url.
   public static final TreeMap<String,String> getPackageListMapFromDocRootUrlIterator(Iterator<String> url_toDocRootItr, Appendable debugUrls_ifNonNull, Appendable debugPkgs_ifNonNull) throws MalformedURLException, IOException  {
      TreeMap<String,String> map = new TreeMap<String,String>();
      try  {
         while(url_toDocRootItr.hasNext())  {
            addPackageListToMapFromDocRootUrl(map, url_toDocRootItr.next(), debugUrls_ifNonNull, debugPkgs_ifNonNull);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(url_toDocRootItr, "url_toDocRootItr", null, rx);
      }
      return  map;
   }
    */
   /*
      <p>Retrieve's the package-list from a url, and adds each package to a map.</p>

    * @return  <code>{@link #addPackageListToMap(Map, Iterator, String, Appendable, Appendable) addPackageListToMap}(package_docRootUrlMap,
      <br> &nbsp; &nbsp; {@link com.github.xbn.text.StringUtil}.{@link com.github.xbn.text.StringUtil#getLineIterator(Object) getLineIterator}(text), url_toDocRoot + &quot;package-list&quot;,
      <br> &nbsp; &nbsp; debugUrls_ifNonNull, debugPkgs_ifNonNull)</code>
      <br>Where {@code text} is
      <br> &nbsp; &nbsp; <code>{@link com.github.xbn.io.IOUtil}.{@link com.github.xbn.io.IOUtil#getWebPageSourceX(String, String) getWebPageSourceX}(url_toDocRoot, null)</code>
   public static final void addPackageListToMapFromDocRootUrl(Map<String,String> package_docRootUrlMap, String url_toDocRoot, IfUrlInaccessible if_inaccessible, Appendable debugUrls_ifNonNull, Appendable debugPkgs_ifNonNull) throws MalformedURLException, IOException  {
      String text = null;
      try  {
         text = IOUtil.getWebPageSourceX(url_toDocRoot, null);
      }  catch(Exception x)  {
         try  {
            if(if_inaccessible.isWarn())  {
               TextAppenter dbgUrlAptr = NewTextAppenterFor.appendableSuppressIfNull(debugUrls_ifNonNull);
               dbgUrlAptr.appentln("Warning: Unable to retrieve package-list from " + url_toDocRoot + ": " + x);
            }  else  {
               throw  x;
            }
         }
      }
      addPackageListToMap(package_docRootUrlMap,
         StringUtil.getLineIterator(text), url_toDocRoot + "package-list",
         debugUrls_ifNonNull, debugPkgs_ifNonNull);
   }
    */
   /*
      <p>Add all items from a url's package-list to the package-list/doc-root-url map. The key is the package name, the value is the url.</p>

    * @param  package_docRootUrlMap  May not be {@code null}.
    * @param  packageList_lineItr  May not be {@code null} or contain any already-existing key in the map.
    * @param  url_toDocRoot  The url to {@code {@docRoot}} for the link target-file. Which must end with a slash {@code "/"}, and <i>should</i> not be {@code null}.. This is also the directory in which the {@code package-list} files exists. For local files, this is a relative directory (such as {@code "../../"}) otherwise this is the full url to the external doc-root directory.
    * @param  debugUrls_ifNonNull  If non-{@code null}, this outputs the url only.
    * @param  debugPkgs_ifNonNull  If non-{@code null}, this outputs the size of the map before any package is added to it, and each package before adding it.
    * @see  #addPackageListToMapFromDocRootUrl(Map, String, Appendable, Appendable)
    * @see  #getPackageListMapFromDocRootUrlIterator(Iterator, Appendable, Appendable)
   public static final void addPackageListToMap(Map<String,String> package_docRootUrlMap, Iterator<String> packageList_lineItr, String url_toDocRoot, Appendable debugUrls_ifNonNull, Appendable debugPkgs_ifNonNull)  {
      try  {
         if(url_toDocRoot.length() < 2  ||  !url_toDocRoot.endsWith("/"))  {
            throw  new IllegalArgumentException("url_toDocRoot (\"" + url_toDocRoot + "\") is less than two characters, or does not end with a url-slash ('/').");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(url_toDocRoot, "url_toDocRoot", null, rx);
      }
      CrashIfString.nullEmpty(url_toDocRoot, "url_toDocRoot", null);
      Objects.requireNonNull(url_toDocRoot, "url_toDocRoot");
      TextAppenter dbgUrlAptr = NewTextAppenterFor.appendableSuppressIfNull(debugUrls_ifNonNull);
      TextAppenter dbgPkgAptr = NewTextAppenterFor.appendableSuppressIfNull(debugPkgs_ifNonNull);
      try  {
         if(dbgUrlAptr != null)  {
            dbgUrlAptr.appentln("addPackageListToMap: url_toDocRoot=\"" + url_toDocRoot + "\"");
         }
         if(debugPkgs_ifNonNull != null)  {
            dbgPkgAptr.appentln(" - package_docRootUrlMap.size()=" + package_docRootUrlMap.size());
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(package_docRootUrlMap, "package_docRootUrlMap", null, rx);
      }

      int addedCount = 1;
      try  {
         while(packageList_lineItr.hasNext())  {
            String pkg = packageList_lineItr.next();

            CrashIfString.nullEmpty(pkg, "pkg", null);

 				if(debugPkgs_ifNonNull != null)  {
               dbgPkgAptr.appentln(" - " + (addedCount++) + ". " + pkg);
            }

            if(package_docRootUrlMap.containsKey(pkg))  {
               throw  new IllegalArgumentException("package \"" + pkg + "\" already exists in package_docRootUrlMap:" + LINE_SEP +
                  MapUtil.appendToString(new StringBuilder(), package_docRootUrlMap));
            }
            package_docRootUrlMap.put(pkg, url_toDocRoot);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(packageList_lineItr, "packageList_lineItr", null, rx);
      }
   }
    */
   private JavaDocUtil()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
