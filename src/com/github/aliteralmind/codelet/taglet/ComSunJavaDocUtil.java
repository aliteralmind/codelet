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
package  com.github.aliteralmind.codelet.taglet;
   import  com.sun.tools.doclets.Taglet;
   import  java.util.Map;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.aliteralmind.codelet.util.JavaDocUtil;
   import  com.github.xbn.util.JavaRegexes;
   import  com.sun.javadoc.ClassDoc;
   import  com.sun.javadoc.Doc;
   import  com.sun.javadoc.PackageDoc;
   import  com.sun.javadoc.ProgramElementDoc;
   import  com.sun.javadoc.Tag;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <p>Generically-useful utilities related to {@code com.sun.javadoc}.</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class ComSunJavaDocUtil  {
   /**
      <p>Register a Taglet as required by JavaDoc.</p>

      <h3><i>(Why is the map parameter type-erased? What generics does it need?)</i></h3>

      <p>Steps<ol>
         <li>If a taglet with the name equal to <code>taglet.{@link com.sun.javadoc.Taglet#getName() getName}()</code> already exists in the map, remove it.</li>
         <li>{@code taglet} is <a href="http://docs.oracle.com/javase/7/docs/api/java/util/Map#put(K,">V) added</a> to the map, with its name as the key.</li>
      </ol></p>

      @param  taglet  The taglet to register. May not be {@code null}.
      @param map  The map to register this tag to. May not be {@code null}.
      @since  0.1.1
    */
   @SuppressWarnings({"unchecked", "rawtypes"})
   public static void registerNewTagletInstance(Taglet taglet, Map map)  {
      try  {
         final String name = taglet.getName();
         final Taglet alreadyRegisteredTaglet = (Taglet)map.get(name);
         if (alreadyRegisteredTaglet != null) {
            map.remove(name);
         }
         map.put(name, taglet);
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(taglet, "taglet", null);
         throw  CrashIfObject.nullOrReturnCause(map, "map", null, rx);
      }
/*
      FileTextletTaglet tag = new FileTextletTaglet();
      Taglet t = (Taglet) map.get(tag.getName());
      if (t != null) {
         map.remove(tag.getName());
      }
      map.put(tag.getName(), tag);
 */
   }

   /**
      <p>Get the relative url whose value is equivalent to {@code {@docRoot}}.</p>

      @return  <code>{@link com.github.aliteralmind.codelet.util.JavaDocUtil JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getRelativeUrlToDocRoot(String) getRelativeUrlToDocRoot}({@link #getEnclosingPackageName(Tag) getEnclosingPackageName}(tag))</code>
    **/
   public static final String getRelativeUrlToDocRoot(Tag tag)  {
      return  JavaDocUtil.getRelativeUrlToDocRoot(getEnclosingPackageName(tag));
   }
   /*
   private static final Matcher packageElementMtchr = Pattern.compile("[\\p{L}_\\p{Sc}][\\p{L}\\p{N}_\\p{Sc}]*\\.?").matcher("");
      <p>Get the relative url whose value is equivalent to {@code {@docRoot}}. This is a generic (self-contained) version of {@link #getRelativeUrlToDocRoot(Tag) getRelativeUrlToDocRoot}--they both do the same thing.</p>

      @return  <code>packageElementMtchr.reset({@link #getEnclosingPackageName(Tag) getEnclosingPackageName}(tag)).{@link java.util.regex.Matcher#replaceAll(String) replaceAll}(&quot;../&quot;)</code>
      <br/>Where {@code packageElementMtchr} is initialized to
      <br/> &nbsp; &nbsp; <code>Pattern.{@link java.util.regex.Pattern#compile(String) compile}(&quot;[\\p{L}_\\p{Sc}][\\p{L}\\p{N}_\\p{Sc}]*\\.?&quot;).{@link java.util.regex.Pattern#matcher(CharSequence) matcher}(&quot;&quot;)</code>
      @see  <code><a href="http://stackoverflow.com/questions/4079268/custom-taglet-with-reference-to-docroot">http://stackoverflow.com/questions/4079268/custom-taglet-with-reference-to-docroot</a></code>
   public static final String getRelativeUrlToDocRootGeneric(Tag tag)  {
      return  packageElementMtchr.reset(getEnclosingPackageName(tag)).replaceAll("../");
   }
    */
   /**
      <p>Get the package of the tag-containing JavaDoc page.</p>

      @return  If {@link #getPackageDoc(Tag) getPackageDoc}{@code (tag)} is<ul>
         <li>{@code null}: {@code ""}</li>
         <li>non-{@code null}: Its {@linkplain com.sun.javadoc.PackageDoc#name() name}.</li>
      </ul>
    **/
   public static final String getEnclosingPackageName(Tag tag)  {
      PackageDoc pkgDoc = getPackageDoc(tag);
      return  ((pkgDoc == null) ? "" : pkgDoc.name());
   }
   /**
      <p>Get the tag-containing JavaDoc page's post-package name.</p>

      @param  tag  May not be {@code null}.
      @param  include_postClassName  If the taglet is in the class JavaDoc-block, The classes generics, if any, are included in its simple name. If the taglet is in a function's JavaDoc-block, its name and parameters are included. If this parameter is {@link IncludePostClassName#YES YES}, the generics or function name, if any, are included in the returned value. If {@link IncludePostClassName#NO NO}, they are excluded. This parameter may not be {@code null}.
      @return  If the tag's {@linkplain #getEnclosingPackageName(Tag) enclosing package name}<ul>
         <li>Has no characters: {@code "OVERVIEW_SUMMARY"}</li>
         <li>Non empty and its name<ul>
            <li>Has no characters: {@code "PACKAGE_SUMMARY"}</li>
            <li>Non-empty: The name</li>
         </ul></li>
      </ul>
    **/
   public static final String getEnclosingSimpleName(Tag tag, IncludePostClassName include_postClassName)  {
      String pkg = getEnclosingPackageName(tag);
      if(pkg.length() == 0)  {
//System.out.println("getEnclosingSimpleName.1 OVERVIEW_SUMMARY");
         return  "OVERVIEW_SUMMARY";
      }

      String name = tag.holder().toString().substring(pkg.length());
//System.out.println("getEnclosingSimpleName.1a tag.holder()=\"" + tag.holder() + "\"\n   - without package=\"" + name+ "\"");


      if(name.length() == 0)  {
//System.out.println("getEnclosingSimpleName.2 PACKAGE_SUMMARY");
         return  "PACKAGE_SUMMARY";
      }

      if(name.charAt(0) == '.')  {
         name = name.substring(1);
      }

      try  {
         if(include_postClassName.isYes())  {
//System.out.println("getEnclosingSimpleName.2a " + name);
            return  name;
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(include_postClassName, "include_postClassName", null, rx);
      }

      //include_postClassName.NO

      name = oneOrMoreWordCharsAtInputStartMtchr.reset(name).replaceFirst("$1");

//System.out.println("getEnclosingSimpleName.3 " + name);
      return  name;

/*
      try  {
         if(include_postClassName.isYes())  {
System.out.println("getEnclosingSimpleName.3 " + name);
            return  name;
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(include_postClassName, "include_postClassName", null, rx);
      }

      int idxGreaterThan = name.indexOf('<');
      int idxOpenParen = name.indexOf('(');

      //Must check for parens first, because it is possible for generics to be
      //inside parameters!
      if(idxOpenParen == -1)  {
         if(idxGreaterThan == -1)  {
System.out.println("getEnclosingSimpleName.4 " + name);
            return  name;
         }
         name = name.substring(0, idxGreaterThan);
System.out.println("getEnclosingSimpleName.5 " + name);
         return  name;

      }
      //Return name up-through-but-not-including the dot-func-signature

      name = name.substring(0, name.lastIndexOf('.', idxOpenParen));
System.out.println("getEnclosingSimpleName.6 " + name);
      return  name;
 */

   }
      private static final Matcher oneOrMoreWordCharsAtInputStartMtchr = Pattern.compile("^(\\w+).*$").matcher("");
   /**
      <p>Get the object containing the &quot;package&quot; of the tag-containing JavaDoc page.</p>

      <p>This function was written by <a href="http://stackoverflow.com">Stack Overflow</a> user <a href="http://stackoverflow.com/users/547546/chad-retz">Chad Retz</a>.</p>-

      @return  If the tag's {@linkplain com.sun.javadoc.Tag#holder() holder }<ul>
         <li>is a {@link com.sun.javadoc.ProgramElementDoc}: Its {@linkplain com.sun.javadoc.ProgramElementDoc#containingPackage() containing package}.</li>
         <li>is a {@link com.sun.javadoc.PackageDoc}: {@code tag}.</li>
         <li>Otherwise: {@code null}</li>
      </ul>
      @see  <code><a href="http://chadretz.wordpress.com/2010/12/19/mathml-inside-javadoc-using-mathjax-and-a-custom-taglet/">http://chadretz.wordpress.com/2010/12/19/mathml-inside-javadoc-using-mathjax-and-a-custom-taglet/</a></code>
    **/
   public static final PackageDoc getPackageDoc(Tag tag) {
      Doc holder = tag.holder();
      if (holder instanceof ProgramElementDoc) {
         return ((ProgramElementDoc) holder).containingPackage();
      } else if (holder instanceof PackageDoc) {
         return (PackageDoc) holder;
      } else {
         return  null;
      }
   }
}
