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
   import  com.github.aliteralmind.codelet.CodeletFormatException;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.TagletProcessor;
   import  com.sun.javadoc.Doc;
   import  com.sun.javadoc.SourcePosition;
   import  com.sun.javadoc.Tag;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
/**
   <P>The interface between Java {@code com.sun.javadoc.*} and {@code com.github.aliteralmind.codelet.*}. The only dependencies on {@code com.sun.javadoc.*} are in this {@code com.github.aliteralmind.codelet.taglet} package. This is done in the interest of <A HREF="http://stackoverflow.com/questions/23138806/how-to-make-inline-taglets-which-require-com-sun-more-cross-platform-is-there">minimizing the dependency</A> on {@code com.sun.javadoc}, which is not as cross-platform as Java itself.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum CodletComSunJavadocTagProcessor  {
   INSTANCE;
   /*
      To avoid configuration from being loaded repeatedly.
    */
   private static TagletProcessor FIRST_TAGLET_PROC_INSTANCE;
   /**
      <P>Passes all taglet information to the codelet processor (without any references to {@code com.sun.javadoc}) and returns the text that replaces the taglet in the JavaDoc.</P>

     	@return  <CODE>(new {@link com.github.aliteralmind.codelet.TagletProcessor#TagletProcessor(CodeletInstance) TagletProcessor}(instance)).{@link com.github.aliteralmind.codelet.TagletProcessor#get() get}()</CODE></P>

     	<P>where<UL>
     		<LI>{@code instance} is a
<BLOCKQUOTE><PRE>new {@link com.github.aliteralmind.codelet.CodeletInstance#CodeletInstance(String, String, String, File, int, String, String) CodeletInstance}(tag.{@link com.sun.javadoc.Tag#name() name}().substring(1),
   {@link com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil}.{@link com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil#getEnclosingPackageName(Tag) getEnclosingPackageName}(tag),
   ComSunJavaDocUtil.{@link com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil#getEnclosingSimpleName(Tag, IncludePostClassName) getEnclosingSimpleName}(tag, {@link IncludePostClassName}.{@link IncludePostClassName#NO NO}),
   pos.{@link com.sun.javadoc.SourcePosition#file() file}(), pos.{@link com.sun.javadoc.SourcePosition#line() line}(), tag.{@link com.sun.javadoc.Tag#text() text}(),
   ComSunJavaDocUtil.{@link com.github.aliteralmind.codelet.taglet.ComSunJavaDocUtil#getRelativeUrlToDocRoot(Tag) getRelativeUrlToDocRoot}(tag))</PRE></BLOCKQUOTE></LI>
     		<LI>{@code holder} is <CODE>tag.{@link com.sun.javadoc.Tag#holder() holder}()</CODE>, and</LI>
     		<LI>{@code pos} is <CODE>holder.{@link com.sun.javadoc.Doc#position() position}()</CODE></LI>
     	</UL>{@code name}().substring(1)} is to eliminate the trailing at-sign ({@code '@'}). Don't understand what that's part of it.</P>

     	@param  tag  May not be {@code null}.
     	@exception  RuntimeException  If the taglet is not successfully processed, for any reason. Get the causing exception with {@link java.lang.RuntimeException#getCause() getCause}{@code ()}.
    **/
   public static final String get(Tag tag)  {
      Doc holder = tag.holder();
      SourcePosition pos = holder.position();
      String namePostAtSign = tag.name().substring(1);
      CodeletInstance instance = new CodeletInstance(namePostAtSign,
         ComSunJavaDocUtil.getEnclosingPackageName(tag),
         ComSunJavaDocUtil.getEnclosingSimpleName(tag, IncludePostClassName.NO),
         pos.file(), pos.line(), tag.text(),
         ComSunJavaDocUtil.getRelativeUrlToDocRoot(tag));

      try  {
         TagletProcessor tproc = (new TagletProcessor(instance));
         if(FIRST_TAGLET_PROC_INSTANCE == null)  {
            /*
               To avoid configuration from being loaded repeatedly. This block is not synchronized, because it doesn't matter which instance is held--just that *an* instance is held.
             */
            FIRST_TAGLET_PROC_INSTANCE = tproc;
         }
         return  tproc.get();
      }  catch(CodeletFormatException | ClassNotFoundException |
                  NoSuchMethodException | NoSuchFileException |
                  AccessDeniedException | InterruptedException x)  {
         throw  new RuntimeException(x);
      }
   }
}
