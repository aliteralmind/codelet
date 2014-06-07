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
package  com.github.aliteralmind.codelet.taglet;
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
   <P>Utilities related to {@code com.sun.javadoc}.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class ComSunJavaDocUtil  {
//Propietary:
   /**
      <P>Get the relative url whose value is equivalent to {@code {@docRoot}}</P>

      @return  <CODE>{@link com.github.aliteralmind.codelet.util.JavaDocUtil JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getRelativeUrlToDocRoot(String) getRelativeUrlToDocRoot}({@link #getEnclosingPackageName(Tag) getEnclosingPackageName}(tag))</CODE>
   public static final String getRelativeUrlToDocRoot(Tag tag)  {
      return  JavaDocUtil.getRelativeUrlToDocRoot(getEnclosingPackageName(tag));
   }
    **/
//Generic:
   private static final Matcher packageElementMtchr = Pattern.compile("[\\p{L}_\\p{Sc}][\\p{L}\\p{N}_\\p{Sc}]*\\.?").matcher("");
   /**
      <P>Get the relative url whose value is equivalent to {@code {@docRoot}}</P>

      @return  <CODE>packageElementMtchr.reset({@link #getEnclosingPackageName(Tag) getEnclosingPackageName}(tag)).{@link java.util.regex.Matcher#replaceAll(String) replaceAll}(&quot;../&quot;)</CODE>
      <BR>Where {@code packageElementMtchr} is initialized to
      <BR> &nbsp; &nbsp; <CODE>Pattern.{@link java.util.regex.Pattern#compile(String) compile}(&quot;[\\p{L}_\\p{Sc}][\\p{L}\\p{N}_\\p{Sc}]*\\.?&quot;).{@link java.util.regex.Pattern#matcher(CharSequence) matcher}(&quot;&quot;)</CODE>
      @see  <CODE><A HREF="http://stackoverflow.com/questions/4079268/custom-taglet-with-reference-to-docroot">http://stackoverflow.com/questions/4079268/custom-taglet-with-reference-to-docroot</A></CODE>
    **/
   public static final String getRelativeUrlToDocRoot(Tag tag)  {
      return  packageElementMtchr.reset(getEnclosingPackageName(tag)).replaceAll("../");
   }
   /**
      <P>Get the package of the tag-containing JavaDoc page.</P>

      @return  If {@link #getPackageDoc(Tag) getPackageDoc}{@code (tag)} is<UL>
         <LI>{@code null}: {@code ""}</LI>
         <LI>non-{@code null}: Its {@linkplain com.sun.javadoc.PackageDoc#name() name}.</LI>
      </UL>
    **/
   public static final String getEnclosingPackageName(Tag tag)  {
      PackageDoc pkgDoc = getPackageDoc(tag);
      return  ((pkgDoc == null) ? "" : pkgDoc.name());
   }
   /**
      <P>Get the tag-containing JavaDoc page's post-package name.</P>

      @param  tag  May not be {@code null}.
      @param  include_postClassName  If the taglet is in the class JavaDoc-block, The classes generics, if any, are included in its simple name. If the taglet is in a function's JavaDoc-block, its name and parameters are included. If this parameter is {@link IncludePostClassName#YES YES}, the generics or function name, if any, are included in the returned value. If {@link IncludePostClassName#NO NO}, they are excluded. This parameter may not be {@code null}.
      @return  If the tag's {@linkplain #getEnclosingPackageName(Tag) enclosing package name}<UL>
         <LI>Has no characters: {@code "OVERVIEW_SUMMARY"}</LI>
         <LI>Non empty and its name<UL>
            <LI>Has no characters: {@code "PACKAGE_SUMMARY"}</LI>
            <LI>Non-empty: The name</LI>
         </UL></LI>
      </UL>
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
      <P>Get the object containing the &quot;package&quot; of the tag-containing JavaDoc page.</P>

      <P>This function was written by <A HREF="http://stackoverflow.com">Stack Overflow</A> user <A HREF="http://stackoverflow.com/users/547546/chad-retz">Chad Retz</A>.</P>-

      @return  If the tag's {@linkplain com.sun.javadoc.Tag#holder() holder }<UL>
         <LI>is a {@link com.sun.javadoc.ProgramElementDoc}: Its {@linkplain com.sun.javadoc.ProgramElementDoc#containingPackage() containing package}.</LI>
         <LI>is a {@link com.sun.javadoc.PackageDoc}: {@code tag}.</LI>
         <LI>Otherwise: {@code null}</LI>
      </UL>
      @see  <CODE><A HREF="http://chadretz.wordpress.com/2010/12/19/mathml-inside-javadoc-using-mathjax-and-a-custom-taglet/">http://chadretz.wordpress.com/2010/12/19/mathml-inside-javadoc-using-mathjax-and-a-custom-taglet/</A></CODE>
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
