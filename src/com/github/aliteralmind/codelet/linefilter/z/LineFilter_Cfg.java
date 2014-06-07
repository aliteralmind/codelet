/*license*\
   Codelet: http://codelet.aliteralmind.com

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
   import  com.github.aliteralmind.codelet.linefilter.LineFilter;
   import  com.github.xbn.neederneedable.DummyForNoNeeder;
   import  com.github.xbn.analyze.validate.z.ValueValidator_Cfg;
/**
   <P>For <A HREF="{@docRoot}/com/github/xbn/chain/Needable.html#direct">directly</A> configuring a {@link com.github.aliteralmind.codelet.linefilter.LineFilter LineFilter}.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class LineFilter_Cfg<O,L extends LineObject<O>> extends LineFilter_CfgForNeeder<O,L,LineFilter<O,L>,DummyForNoNeeder>   {
   /**
      <P>Create a new {@code LineFilter_Cfg} for the root-mode only.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="LineFilter_CfgForNeeder.html#LineFilter_CfgForNeeder(R)">super</A>(null)</CODE></P>
      @see  #LineFilter_Cfg(int, String) this(i,s)
    **/
   public LineFilter_Cfg()  {
      super(null);
   }
   /**
      <P>Create a new {@code LineFilter_Cfg} for a sub-mode (which will be added to another already-existing mode).</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="LineFilter_CfgForNeeder.html#LineFilter_CfgForNeeder(R, int, java.lang.String)">super</A>(null, sub_levelNum, name)</CODE></P>
      @see  #LineFilter_Cfg() this()
    **/
   public LineFilter_Cfg(int sub_levelNum, String name)  {
      super(null, sub_levelNum, name);
   }
}
