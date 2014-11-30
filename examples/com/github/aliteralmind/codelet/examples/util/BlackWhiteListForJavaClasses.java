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
package  com.github.aliteralmind.codelet.examples.util;
   import  com.github.aliteralmind.codelet.util.FilenameBlacklist;
   import  com.github.aliteralmind.codelet.util.FilenameWhitelist;
   import  org.apache.commons.io.IOCase;
/**
   <p>Uses <code>com.github.aliteralmind.codelet.util.{@link com.github.aliteralmind.codelet.util.FilenameBlacklist}</code> and {@link com.github.aliteralmind.codelet.util.FilenameWhitelist} to exclude or include specific classes within a package and its sub-packages.</p>

   <p>{@code java com.github.aliteralmind.codelet.examples.util.BlackWhiteListForJavaClasses}</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class BlackWhiteListForJavaClasses  {
   public static final void main(String[] ignored)  {

      FilenameBlacklist blackList = new FilenameBlacklist(IOCase.INSENSITIVE,
         //Propers:
            (new String[]{"xbn.io.*", "xbn.text.*"}),
         //Overrides:
            (new String[]{"xbn.text.line.*", "xbn.io.IOUtil.java"}),
         null);  //Debugging. on=System.out, off=null
      System.out.println(blackList.doAccept("xbn.io.IOUtil.java"));
      System.out.println(blackList.doAccept("xbn.io.TextAppenter.java"));
      System.out.println(blackList.doAccept("xbn.list.listify.Listify"));
      System.out.println(blackList.doAccept("xbn.text.UtilString.java"));
      System.out.println(blackList.doAccept("xbn.text.line.LineFilter.java"));

      System.out.println();

      FilenameWhitelist whiteList = new FilenameWhitelist(blackList, IOCase.INSENSITIVE,
         null);  //Debugging. On=System.out, off=null
      System.out.println(whiteList.doAccept("xbn.io.IOUtil.java"));
      System.out.println(whiteList.doAccept("xbn.io.TextAppenter.java"));
      System.out.println(whiteList.doAccept("xbn.list.listify.Listify"));
      System.out.println(whiteList.doAccept("xbn.text.UtilString.java"));
      System.out.println(whiteList.doAccept("xbn.text.line.LineFilter.java"));
   }
}
