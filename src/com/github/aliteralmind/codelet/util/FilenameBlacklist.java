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
   import  org.apache.commons.io.IOCase;
/**
   <p>For black-listing file paths or fully-qualified class names.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class FilenameBlacklist extends FilenameBlackWhiteList  {
   /**
      <p>Create a new instance.</p>

    * <p>Equal to
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) super}({@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#BLACK BLACK}, case_sensitivity, proper_items, override_items, dbgAccept_ifNonNull)</code></p>

    * @see  #FilenameBlacklist(FilenameBlackWhiteList, IOCase, Appendable)
    */
   public FilenameBlacklist(IOCase case_sensitivity, String[] proper_items, String[] override_items, Appendable dbgAccept_ifNonNull)  {
      super(BlackOrWhite.BLACK, case_sensitivity, proper_items, override_items, dbgAccept_ifNonNull);
   }
   /**
      <p>Create a new instance from an existing black or white-list.</p>

    * <p>Equal to
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.util.FilenameBlackWhiteList#FilenameBlackWhiteList(FilenameBlackWhiteList, BlackOrWhite, IOCase, Appendable) super}(to_copyListsFrom, {@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#BLACK BLACK}, case_sensitivity, dbgAccept_ifNonNull)</code></p>

    * @see  #FilenameBlacklist(IOCase, String[], String[], Appendable)
    */
   public FilenameBlacklist(FilenameBlackWhiteList to_copyListsFrom, IOCase case_sensitivity, Appendable dbgAccept_ifNonNull)  {
      super(to_copyListsFrom, BlackOrWhite.BLACK, case_sensitivity, dbgAccept_ifNonNull);
   }
}
