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
package  com.github.aliteralmind.codelet.alter;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.util.JavaDocUtil;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.regexutil.RegexReplacer;
   import  com.github.xbn.regexutil.z.RegexReplacer_Cfg;
   import  java.lang.reflect.Constructor;
   import  java.lang.reflect.Field;
   import  java.lang.reflect.Method;
   import  java.util.regex.Pattern;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <p>Convenience functions for creating {@code com.github.xbn.regexutil.}{@link com.github.xbn.regexutil.RegexReplacer RegexReplacer}s that replace a class, constructor, function, or object name with a clickable JavaDoc link.</p>

   @see  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum NewJDLinkForWordOccuranceNum
 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class NewJavaDocLinkReplacerFor  {
   /**
      <p>Changes a class name to a JavaDoc link.</p>

    * @param  instance  May not be {@code null}.
    * @param  target  May not be {@code null}.
    * @return  <code>{@link #newReplaceWordOnlyWith(String, String, Appendable) newReplaceWordOnlyWith}(
         <br/> &nbsp; &nbsp; target.{@link java.lang.reflect.Class#getName() getName}(), link, dbgDest_ifNonNull)</code>
      <br/>Where the {@code link}'s url is
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.util.JavaDocUtil JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getUrlToClass(String, Class) getUrlToClass}(instance.{@link CodeletInstance#getRelativeUrlToDocRoot() getRelativeUrlToDocRoot}(), target)</code>
      <br/>and display is the class's {@linkplain java.lang.Class#getSimpleName() simple name}.
    */
   public static final RegexReplacer cclass(CodeletInstance instance, Class target, Appendable dbgDest_ifNonNull)  {
      String link = null;
      try  {
         link = "<A HREF=\"" +
            JavaDocUtil.getUrlToClass(
               getDocRootUrlToTargetClass(instance, target),
               target) +
            "\">" + target.getSimpleName() + "</a>";
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(instance, "instance", null);
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  newReplaceWordOnlyWith(target.getSimpleName(), link, dbgDest_ifNonNull);
   }
   /**
      <p>A new replacer for replacing the first occurance of a whole word with a link.</p>

    * @return  <code>new {@link com.github.xbn.regexutil.z.RegexReplacer_Cfg#RegexReplacer_Cfg() RegexReplacer_Cfg}().
      <br/> &nbsp; &nbsp; com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#direct(String, Object)({@link java.util.regex.Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(&quot;\\b&quot; + word_findWhat + &quot;\\b([ \\t]*\\()&quot;), link_rplcWith + &quot;$1&quot;).
      <br/> &nbsp; &nbsp; {@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#debugTo(Appendable) debugTo}(dbgDest_ifNonNull).{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#first() first}().{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#build() build}()</code>
    * @see  #newReplaceWordOnlyWith(String, String, Appendable) newReplaceWordOnlyWith
    */
   public static final RegexReplacer newReplaceCnstrFuncNameOpenParenWith(String word_findWhat, String link_rplcWith, Appendable dbgDest_ifNonNull)  {
      return  new RegexReplacer_Cfg().
         direct(Pattern.compile("\\b" + word_findWhat + "\\b([ \\t]*\\()"), link_rplcWith + "$1").
         debugTo(dbgDest_ifNonNull).first().build();
   }
   /**
      <p>A new replacer for replacing the first occurance of a class or field name with a link.</p>

    * @return  <code>new {@link com.github.xbn.regexutil.z.RegexReplacer_Cfg#RegexReplacer_Cfg() RegexReplacer_Cfg}().
      <br/> &nbsp; &nbsp; com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#direct(String, Object)({@link java.util.regex.Pattern}.{@link java.util.regex.Pattern#compile(String) compile}(&quot;\\b&quot; + word_findWhat + &quot;\\b&quot;), link_rplcWith).
      <br/> &nbsp; &nbsp; {@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#debugTo(Appendable) debugTo}(dbgDest_ifNonNull).{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#first() first}().{@link com.github.xbn.regexutil.z.RegexReplacer_CfgForNeeder#build() build}()</code>
    * @see  #newReplaceCnstrFuncNameOpenParenWith(String, String, Appendable) newReplaceCnstrFuncNameOpenParenWith
    */
   public static final RegexReplacer newReplaceWordOnlyWith(String word_findWhat, String link_rplcWith, Appendable dbgDest_ifNonNull)  {
      return  new RegexReplacer_Cfg().
         direct(Pattern.compile("\\b" + word_findWhat + "\\b"), link_rplcWith).
         debugTo(dbgDest_ifNonNull).first().build();
   }
   /**
      <p>Changes a constructor name to a JavaDoc link.</p>
    */
   public static final RegexReplacer constructor(CodeletInstance instance, Constructor<?> target, Appendable dbgDest_ifNonNull)  {
      String className = null;
      String link = null;
      try  {
         className = target.getDeclaringClass().getSimpleName();
         link = "<A HREF=\"" +
            JavaDocUtil.getUrlToConstructor(
               getDocRootUrlToTargetClass(instance, target.getDeclaringClass()),
               target) +
            "\">" + className + "</a>";
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(instance, "instance", null);
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  newReplaceCnstrFuncNameOpenParenWith(
         className, link, dbgDest_ifNonNull);
   }
   /**
      <p>Changes a function name to a JavaDoc link.</p>

    * @param  instance  May not be {@code null}.
    * @param  target  May not be {@code null}.
    * @return  <code>{@link #newReplaceCnstrFuncNameOpenParenWith(String, String, Appendable) newReplaceCnstrFuncNameOpenParenWith}(
         <br/> &nbsp; &nbsp; target.{@link java.lang.reflect.Method#getName() getName}(), link, dbgDest_ifNonNull)</code>
      <br/>Where the {@code link}'s url is
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.util.JavaDocUtil JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getUrlToMethod(String, Method) getUrlToMethod}(instance.{@link CodeletInstance#getRelativeUrlToDocRoot() getRelativeUrlToDocRoot}(), target)</code>
      <br/>and display is the method's name.
    */
   public static final RegexReplacer method(CodeletInstance instance, Method target, Appendable dbgDest_ifNonNull)  {
      String link = null;
      try  {
         link = "<A HREF=\"" +
            JavaDocUtil.getUrlToMethod(
               getDocRootUrlToTargetClass(instance, target.getDeclaringClass()),
               target) +
            "\">" + target.getName() + "</a>";
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(instance, "instance", null);
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  newReplaceCnstrFuncNameOpenParenWith(
         target.getName(), link, dbgDest_ifNonNull);
   }
   /**
      <p>Changes a field name (an object existing in another class) to a JavaDoc link.</p>

    * @param  instance  May not be {@code null}.
    * @param  target  May not be {@code null}.
    * @return  <code>{@link #newReplaceWordOnlyWith(String, String, Appendable) newReplaceWordOnlyWith}(target.{@link java.lang.reflect.Field#getName() getName}(), link, dbgDest_ifNonNull)</code>
      <br/>Where the {@code link}'s url is
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.util.JavaDocUtil JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getUrlToField(String, Field) getUrlToField}(instance.{@link CodeletInstance#getRelativeUrlToDocRoot() getRelativeUrlToDocRoot}(), target)</code>
      <br/>and display is the field's name.
    */
   public static final RegexReplacer field(CodeletInstance instance, Field target, Appendable dbgDest_ifNonNull)  {
      String link = null;
      try  {
         link = "<A HREF=\"" +
            JavaDocUtil.getUrlToField(
               getDocRootUrlToTargetClass(instance, target.getDeclaringClass()),
               target) +
            "#" + target.getName() + "\">" +
            target.getName() + "</a>";
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(instance, "instance", null);
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      return  newReplaceWordOnlyWith(target.getName(), link, dbgDest_ifNonNull);
   }
   private NewJavaDocLinkReplacerFor()  {
      throw  new IllegalStateException("Do not instantiate");
   }
   /**
      <p>Get the {@code {@docRoot}} url for a package, which may be internal or {@linkplain com.github.aliteralmind.codelet.CodeletBootstrap#EXTERNAL_DOC_ROOT_URL_FILE external}.</p>

    * @param  instance  May not be {@code null}.
    * @param  target  The class being linked to. May not be {@code null}, and must be in a package (<code>target.{@link java.lang.Class#getPackage() getPackage}().{@link java.lang.reflect.Package#getName() getName}()</code> must be non-empty).
    * @return  If <code>{@link com.github.aliteralmind.codelet.CodeletBaseConfig}.{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getAllJavaDocRoots() getAllJavaDocRoots}().{@link com.github.aliteralmind.codelet.util.AllOnlineOfflineDocRoots#getPkgToUrlMap() getPkgToUrlMap}().{@link java.util.Map#get(Object) get}(target.{@link java.lang.Class#getPackage() getPackage}().{@link java.lang.reflect.Package#getName() getName}())</code> is<ul>
         <li>non-{@code null}: The value returned from the map.</li>
         <li>{@code null}: <code>instance.{@link CodeletInstance#getRelativeUrlToDocRoot() getRelativeUrlToDocRoot}()</code></li>
      </ul>
    */
   public static final String getDocRootUrlToTargetClass(CodeletInstance instance, Class<?> target)  {
      String pkgName = null;
      try  {
         pkgName = target.getPackage().getName();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(target, "target", null, rx);
      }
      if(pkgName.length() == 0)  {
         throw  new IllegalArgumentException("target class has no package: " + target.getName());
      }
      String url = getAllJavaDocRoots().getPkgToUrlMap().get(pkgName);
      return  ((url != null) ? url : instance.getRelativeUrlToDocRoot());
   }
}
