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
   import  com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor;
   import  com.github.aliteralmind.codelet.linefilter.TextLineAlterer;
   import  java.util.LinkedHashMap;
/**
   <P>The default {@code DefaultAlterGetter} used, when none is explicitely configured.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class DefaultDefaultAlterGetter implements DefaultAlterGetter  {
   private static final LinkedHashMap<String,TextLineAlterer> map = newMap();
      private static final LinkedHashMap<String,TextLineAlterer> newMap()  {
         LinkedHashMap<String,TextLineAlterer> map = new LinkedHashMap<String,TextLineAlterer>(1);
         map.put("escape_html", NewTextLineAltererFor.escapeHtml());
         return  map;
      }
   public DefaultDefaultAlterGetter()  {
   }
   /**
      <P>Returns a map with a single entry, whose key is {@code "escape_html"}, and whose value is <CODE>{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor}.{@link com.github.aliteralmind.codelet.linefilter.NewTextLineAltererFor#escapeHtml() escapeHtml}()</CODE>.</P>
    **/
   public LinkedHashMap<String,TextLineAlterer> getForSourceCodelet()  {
      return  map;
   }
   /**
      <P>Returns a map with a single entry, whose key is {@code "escape_html"}, and whose value is <CODE>NewTextLineAltererFor.escapeHtml()</CODE>.</P>

      @return  {@link #getForSourceCodelet()}
    **/
   public LinkedHashMap<String,TextLineAlterer> getForCodeletDotOut()  {
      return  getForSourceCodelet();
   }
   /*
      <P>Returns a map with a single entry, whose key is {@code "escape_html"}, and whose value is <CODE>NewTextLineAltererFor.escapeHtml()</CODE>.</P>

      @return  {@link #getForSourceCodelet()}
   public LinkedHashMap<String,TextLineAlterer> getForCodeletAndOut()  {
      return  getForSourceCodelet();
   }
    */
   /**
      <P>Returns a map with a single entry, whose key is {@code "escape_html"}, and whose value is <CODE>NewTextLineAltererFor.escapeHtml()</CODE>.</P>

      @return  {@link #getForSourceCodelet()}
    **/
   public LinkedHashMap<String,TextLineAlterer> getForFileTextlet()  {
      return  getForSourceCodelet();
   }
}
