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
package  com.github.aliteralmind.codelet.alter;
   import  com.github.xbn.linefilter.TextLineAlterer;
   import  java.util.LinkedHashMap;
/**
   <P>Defines alterers used by all pre-built customizers, which are also accessible in custom customizers. The fully-qualified class name of your implementation is specified in the {@link com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_ALTERERS_CLASS_NAME default_alterers_class_name} configuration variable.</P>

   <P><B>Implementation requirements:</B><UL>
      <LI>All functions must return a non-{@code null} map, where no key is {@code null} or empty, and no value is {@code null}.</LI>
      <LI>There must be a no-parameter constructor.</LI>
   </UL>

   <P>Alterations are made in the same order as they are returned by the iterator. When used by {@linkplain com.github.aliteralmind.codelet.BasicCustomizers pre-built customizers}, these default alterations take place after all others (they are added to the end of the {@linkplain com.github.aliteralmind.codelet.CustomizationInstructions#orderedAlterers(Appendable, NullElement, ExpirableElements, MultiAlterType, TextLineAlterer...) alter-array}).</P>

   <P>It is strongly recommended that at least the {@linkplain com.github.xbn.linefilter.NewTextLineAltererFor#escapeHtml() escape html alterer} be returned by all {@code getFor} functions. This is in fact the only alterer returned by all functions in {@link DefaultDefaultAlterGetter}.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public interface DefaultAlterGetter  {
   /**
   	<P>Default alterers for (source-code) {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE {@.codelet}} taglets, when {@linkplain DefaultAlterGetter configured}.</P>

   	<P>This is also used by {@code {@.codelet.and.out}} taglets.</P>

      @return  A non-{@code null} map, where no key is {@code null} or empty, and no value is {@code null}. Each value <I>should</I> be unique.
   	@see  #getForCodeletDotOut()
   	@see  #getForFileTextlet()
    **/
   LinkedHashMap<String,TextLineAlterer> getForSourceCodelet();
   /**
      <P>Default alterers for {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} taglets, when {@linkplain DefaultAlterGetter configured}.</P>

   	<P>This is also used by {@code {@.codelet.and.out}} taglets.</P>

      @return  A non-{@code null} map, where no key is {@code null} or empty, and no value is {@code null}. Each value <I>should</I> be unique.
      @see  #getForSourceCodelet()
    **/
   LinkedHashMap<String,TextLineAlterer> getForCodeletDotOut();
   /*
      <P>Default alterers for {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglets, when {@linkplain DefaultAlterGetter configured}.</P>

      @return  A non-{@code null} map, where no key is {@code null} or empty, and no value is {@code null}. Each value <I>should</I> be unique.
      @see  #getForSourceCodelet()
   LinkedHashMap<String,TextLineAlterer> getForCodeletAndOut();
    */
   /**
      <P>Default alterers for {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}} taglets, when {@linkplain DefaultAlterGetter configured}.</P>

      @return  A non-{@code null} map, where no key is {@code null} or empty, and no value is {@code null}. Each value <I>should</I> be unique.
      @see  #getForSourceCodelet()
    **/
   LinkedHashMap<String,TextLineAlterer> getForFileTextlet();
}
