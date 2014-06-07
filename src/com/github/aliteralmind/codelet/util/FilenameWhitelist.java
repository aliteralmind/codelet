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
   import  com.github.xbn.array.CrashIfArray;
   import  com.github.xbn.array.NullContainer;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.lang.CrashIfObject;
   import  java.util.Arrays;
   import  java.util.Collections;
   import  java.util.List;
   import  java.util.Objects;
   import  org.apache.commons.io.FilenameUtils;
   import  org.apache.commons.io.IOCase;
/**
   <P>For white-listing file paths or fully-qualified class names.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FilenameWhitelist extends FilenameBlackWhiteList  {
   /**
      <P>Create a new instance.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) super}({@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE WHITE}, case_sensitivity, proper_items, override_items, dbgAccept_ifNonNull)</CODE></P>

      @see  #FilenameWhitelist(FilenameBlackWhiteList, IOCase, Appendable)
    **/
   public FilenameWhitelist(IOCase case_sensitivity, String[] proper_items, String[] override_items, Appendable dbgAccept_ifNonNull)  {
      super(BlackOrWhite.WHITE, case_sensitivity, proper_items, override_items, dbgAccept_ifNonNull);
   }
   /**
      <P>Create a new instance from an existing white or black-list.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#FilenameBlackWhiteList(FilenameBlackWhiteList, BlackOrWhite, IOCase, Appendable) super}(to_copyListsFrom, {@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE WHITE}, case_sensitivity, dbgAccept_ifNonNull)</CODE></P>

      @see  #FilenameWhitelist(IOCase, String[], String[], Appendable)
    **/
   public FilenameWhitelist(FilenameBlackWhiteList to_copyListsFrom, IOCase case_sensitivity, Appendable dbgAccept_ifNonNull)  {
      super(to_copyListsFrom, BlackOrWhite.WHITE, case_sensitivity, dbgAccept_ifNonNull);
   }
}
