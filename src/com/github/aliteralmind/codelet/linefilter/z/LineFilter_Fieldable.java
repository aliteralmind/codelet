/*license*\
   Codelet

   Copyright (c) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.linefilter.z;
   import  com.github.aliteralmind.codelet.linefilter.LineObject;
   import  java.util.Map;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.lang.z.GetExtraErrInfo_Fieldable;
   import  com.github.xbn.io.z.GetDebugApbl_Fieldable;
/**
   <P>YYY</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public interface LineFilter_Fieldable<O,L extends LineObject<O>> extends GetExtraErrInfo_Fieldable, GetDebugApbl_Fieldable  {
   int getSubLevel();
   ValueAlterer<L,O> getAlterStart();
   ValueAlterer<L,O> getAlterEnd();
   boolean isEndRequired();
   boolean areBlockMarksInclusive();
   ValueAlterer<L,O> getAlterLine();
   Object[] getSubModeArray();
   String getModeName();
   boolean doKeepBlockActive();
   boolean doKeepLineActive();
   boolean doKeepInactive();
}
