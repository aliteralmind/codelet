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
   import  com.github.xbn.analyze.alter.ReturnValueUnchanged;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.neederneedable.AbstractNeedable;
   import  com.github.xbn.neederneedable.Needer;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.aliteralmind.codelet.linefilter.LineFilter;
   import  com.github.aliteralmind.codelet.linefilter.LineObject;
   import  java.util.Arrays;
/**
   <P>For <A HREF="{@docRoot}/com/github/xbn/chain/Needable.html#indirect">indirectly</A> configuring a {@link com.github.aliteralmind.codelet.linefilter.LineFilter LineFilter}.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class LineFilter_CfgForNeeder<O,L extends LineObject<O>,M extends LineFilter<O,L>,R extends Needer> extends AbstractNeedable<M,R> implements LineFilter_Fieldable<O,L>   {
   public ValueAlterer<L,O>    avBlockStart   ;
   public ValueAlterer<L,O>    avBlockEnd     ;
   public boolean              bBlockEndRqd   ;
   public ValueAlterer<L,O>    avLine         ;
   public String               name           ;
   public boolean              bBlockMarksIncl;
   public LineFilter<O,L>[] aSubModes      ;
   public Object               oXtraErrInfo   ;
   public Appendable           apblDebug      ;
   public int                  subLevelNum    ;
   public boolean              doKeepBlkActv  ;
   public boolean              doKeepLnActv   ;
   public boolean              doKeepInactv   ;
//internal
   private static final LineFilter[] aBLM_EMPTY = new LineFilter[0];
//constructors...START
   /**
      <P>Create a new instance, for the root-mode only.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE> <!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#LineFilter_CfgForNeeder(R, int, java.lang.String)">this</A>(needer, 0, {@link com.github.aliteralmind.codelet.linefilter.LineFilter LineFilter}.{@link com.github.aliteralmind.codelet.linefilter.LineFilter#DUMMY_NAME_FOR_ROOT_MODE DUMMY_NAME_FOR_ROOT_MODE})</CODE></P>
    **/
   public LineFilter_CfgForNeeder(R needer)  {
      this(needer, 0, LineFilter.DUMMY_NAME_FOR_ROOT_MODE);
   }
   /**
      <P>Create a new instance for a sub-mode.</P>

      <P>This<OL>
         <LI>Calls <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/analyze/validate/ValueValidator_CfgForNeeder.html#ValueValidator_CfgForNeeder(boolean, boolean, R)">super</A>(true, true, needer)</CODE></LI>
         <LI>Sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getSubLevel() getSubLevel}{@code ()}* to {@code sub_subLevelNum}</LI>
         <LI>Sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getName() getName}{@code ()}* to {@code name}.</LI>
         <LI>Calls {@link #resetBLMCFN() resetBLMCFN}{@code ()}</LI>
      </OL></P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#LineFilter_CfgForNeeder(R, int, java.lang.String)">this</A>(R)</CODE>
    **/
   public LineFilter_CfgForNeeder(R needer, int sub_subLevelNum, String name)  {
      super(true, true, needer);
      subLevelNum = sub_subLevelNum;
      this.name = name;
      resetBLMCFN();
   }
   /**
      <P>Reset configuration so the mode is never active.</P>

      <P>Equal to {@link #resetBLMCFN() resetBLMCFN}{@code ()}</P>

      @return  <I>{@code this}</I>
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> reset()  {
      resetBLMCFN();
      return  this;
   }
   /**
      <P>Reset configuration specific to this {@code LineFilter_CfgForNeeder}.</P>

      <P>This<UL>
         <LI>Sets the block {@link #start(ValueAlterer) start}, {@link #end(BlockEnd, ValueAlterer) optional end}, and {@link #line(ValueAlterer) line} alterers each to (a duplicate of):
         <BR> &nbsp; &nbsp; <CODE>{@link #start(ValueAlterer) start}({@link com.github.xbn.analyze.alter.ReturnValueUnchanged ReturnValueUnchanged}.&lt;L,O&gt;{@link com.github.xbn.analyze.alter.ReturnValueUnchanged#newForOnEachCallSetWasAlteredTo(boolean) newForOnEachCallSetWasAlteredTo}(false))</CODE></LI>
         <LI>Sets block-marks as {@link #blockMarksInclusive() inclusive}, {@link #noSubModes() zero sub-modes}, and no {@link #extraErrInfo(Object) extra error information}.</LI>
         <LI>{@linkplain #ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines) Declares} that both active blocks and lines should be discarded, and inactive lines should be kept </LI>
      </UL></P>
    **/
   protected final void resetBLMCFN()  {
      start(ReturnValueUnchanged.<L,O>newForOnEachCallSetWasAlteredTo(false));
      end(BlockEnd.OPTIONAL, ReturnValueUnchanged.<L,O>newForOnEachCallSetWasAlteredTo(false));
      line(ReturnValueUnchanged.<L,O>newForOnEachCallSetWasAlteredTo(false));
      noSubModes();
      blockMarksInclusive();
      extraErrInfo(null);
      ifBlockOrLineOrNotActive(ActiveBlockLines.DISCARD, ActiveSingleLines.DISCARD, InactiveLines.KEEP);
   }
//self-returning setters...START
   /**
      <P>Set information to append to error messages.</P>

      @param  info  If non-{@code null}, this object's {@code toString()} is appended to error messages. Get with {@link com.github.xbn.lang.ExtraErrInfoable#getExtraErrInfo() getExtraErrInfo}{@code ()}*
      @return  <I>{@code this}</I>
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> extraErrInfo(Object info)  {
      oXtraErrInfo = info;
      return  this;
   }
   /**
      <P>Set debugging.</P>

      @param  dest_ifNonNull  When non-{@code null}, this is the destination to write debugging output (and debugging is turned {@link com.github.xbn.io.Debuggable#isDebugOn() on}). Get with {@link com.github.xbn.io.Debuggable#getDebugApbl() getDebugApbl}{@code ()}* and {@link com.github.xbn.io.Debuggable#getDebugAptr() getDebugAptr}{@code ()}*.
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> debugTo(Appendable dest_ifNonNull)  {
      apblDebug = dest_ifNonNull;
      return  this;
   }
   /**
      <P>Set the start-mark alterer.</P>

      @param  alterer  May not be {@code null}. A <I>duplicate</I> of this object is stored. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterStart() getAlterStart}{@code ()}*.
      @return  <I>{@code this}</I>
      @see  #end(BlockEnd, ValueAlterer) end(bei,av)
      @see  #line(ValueAlterer) line(av)
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> start(ValueAlterer<L,O> alterer)  {
      avBlockStart = alterer;
      return  this;
   }
   /**
      <P>Set block marks as inclusive.</P>

      @return  <CODE>{@link #blockMarksInclusive(boolean) blockMarksInclusive}(true)</CODE>
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> blockMarksInclusive()  {
      return  blockMarksInclusive(true);
   }
   /**
      <P>Set block marks as exclusive.</P>

      @return  <CODE>{@link #blockMarksInclusive(boolean) blockMarksInclusive}(false)</CODE>
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> blockMarksExclusive()  {
      return  blockMarksInclusive(false);
   }
   /**
      <P>Declare if the block marks are inclusive or exclusive.</P>

      @param  are_inclusive  If<UL>
         <LI>{@code true}: Inclusive. The block starts on the same line as the start-mark, and ends on the same line as the end mark.</LI>
         <LI>{@code false}: Exclusive. The block starts on the line after the start-mark, and ends on the line before the end mark. This implies that the start and end marks must be on different lines.</LI>
      </UL>Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#areBlockMarksInclusive() areBlockMarksInclusive}{@code ()}*
      @see  #blockMarksInclusive()
      @see  #blockMarksExclusive()
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> blockMarksInclusive(boolean are_inclusive)  {
      bBlockMarksIncl = are_inclusive;
      return  this;
   }

   /**
      <P>Set the end-mark alterer and declare it as required.</P>

      @return  {@link #end(BlockEnd, ValueAlterer) end}{@code ({@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd BlockEnd}.{@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd#REQUIRED REQUIRED}, alterer)}
   public LineFilter_CfgForNeeder<O,L,M,R> requiredEnd(ValueAlterer<L,O> alterer)  {
      return  end(BlockEnd.REQUIRED, alterer);
   }
    **/
   /**
      <P>Set the end-mark alterer and declare it as optional.</P>

      @return  {@link #end(BlockEnd, ValueAlterer) end}{@code ({@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd BlockEnd}.{@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd#OPTIONAL OPTIONAL}, alterer)}
   public LineFilter_CfgForNeeder<O,L,M,R> optionalEnd(ValueAlterer<L,O> alterer)  {
      return  end(BlockEnd.OPTIONAL, alterer);
   }
    **/
   /**
      <P>Set the end-mark alterer, and declare if its optional or required.</P>

      @param  alterer  May not be {@code null}. A <I>duplicate</I> of this object is stored. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterEnd() getAlterEnd}{@code ()}*.
      @param  end_is  May not be {@code null}. If {@code BlockEnd.}{@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd#REQUIRED REQUIRED}, the block {@link com.github.aliteralmind.codelet.linefilter.LineFilter#isBlockEndLine() end-mark mark} must exist before the next block's {@link com.github.aliteralmind.codelet.linefilter.LineFilter#isBlockEndLine() start-mark} is found (or the {@code com.github.aliteralmind.codelet.linefilter.LineFilter#declareAllLinesAnalyzed() end of input} is reached). This sets {@link com.github.aliteralmind.codelet.linefilter.LineFilter#isEndRequired() isEndRequired}{@code ()}* to <CODE>end_is.{@link com.github.aliteralmind.codelet.linefilter.z.BlockEnd#isRequired() isRequired}()</CODE>.
      @see  #start(ValueAlterer) start(av)
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> end(BlockEnd end_is, ValueAlterer<L,O> alterer)  {
      ValueAlterer<L,O> av = alterer;
      avBlockEnd = av;
      try  {
         bBlockEndRqd = end_is.isRequired();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(end_is, "end_is", null, rx);
      }
      return  this;
   }
   /**
      <P>Set the line alterer.</P>

      @param  alterer  May not be {@code null}. A <I>duplicate</I> of this object is stored. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getAlterLine() getAlterLine}{@code ()}*.
      @see  #start(ValueAlterer) start(av)
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> line(ValueAlterer<L,O> alterer)  {
      avLine = (ValueAlterer<L,O>)alterer;
      return  this;
   }
   /**
      <P>Which lines should be kept: Active block-lines, single-line entities, and/or inactive lines?.</P>

      <P>These settings are used only by the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) line iterator}. They are ignored by <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/text/line/LineFilter.html#getActiveLine(L)">getActiveLine</A>(L)</CODE>. <I>If <CODE>{@link com.github.aliteralmind.codelet.linefilter.LineFilter LineFilter}.{@link com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) activeLineIterator}</CODE> will not be used, then these settings are ignored.</I></P>

      @param  ifBlk_active  May not be {@code null}. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#doKeepBlockActive() doKeepBlockActive}{@code ()}.
      @param  ifLn_active  May not be {@code null}. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#doKeepLineActive() doKeepLineActive}{@code ()}.
      @param  if_inactive  May not be {@code null}. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#doKeepInactive() doKeepInactive}{@code ()}.
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> ifBlockOrLineOrNotActive(ActiveBlockLines ifBlk_active, ActiveSingleLines ifLn_active, InactiveLines if_inactive)  {
      try  {
         doKeepBlkActv = ifBlk_active.doKeep();
         doKeepLnActv = ifLn_active.doKeep();
         doKeepInactv = if_inactive.doKeep();
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(ifBlk_active, "ifBlk_active", null);
         CrashIfObject.nnull(ifLn_active, "ifLn_active", null);
         throw  CrashIfObject.nullOrReturnCause(if_inactive, "if_inactive", null, rx);
      }
      return  this;
   }
   /**
      <P>Declare this mode has no sub-modes.</P>

      @return  {@link #setSubModes(LineFilter[]) setSubModes}{@code (new LineFilter<O,L>[] {})}
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> noSubModes()  {
      @SuppressWarnings("unchecked")
      LineFilter<O,L>[] ablm = (LineFilter<O,L>[])aBLM_EMPTY;
      return  setSubModes(ablm);
   }
   /**
      <P>Set all direct-child sub-modes.</P>

      @param  sub_modes  May not be {@code null}, and must contain only unique and non-null elements, each having a {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getSubLevel() sub-level} equal to <CODE>this.{@link com.github.aliteralmind.codelet.linefilter.LineFilter#getSubLevel() getSubLevel}()* + 1</CODE>. If empty, there are no sub-modes. Get with {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getSubCount() getSubCount}{@code ()}* and {@link com.github.aliteralmind.codelet.linefilter.LineFilter#getSub(String) getSub}{@code (s)}.
      @see  #noSubModes()
    **/
   public LineFilter_CfgForNeeder<O,L,M,R> setSubModes(LineFilter<O,L>[] sub_modes)  {
      @SuppressWarnings("unchecked")
      LineFilter<O,L>[] ablm = (LineFilter<O,L>[])new LineFilter[sub_modes.length];
      aSubModes = ablm;
      for(int i = 0; i < sub_modes.length; i++)  {
 			aSubModes[i] = sub_modes[i];
      }
      return  this;
   }
   public LineFilter_CfgForNeeder<O,L,M,R> chainID(boolean do_setStatic, Object id)  {
      setChainID(do_setStatic, id);
      return  this;
   }
//self-returning setters...END
   public String toString()  {
      return  super.toString() + ", getSubLevel()=" + getSubLevel() + ", getModeName()=" + getModeName() + ", areBlockMarksInclusive()=" + areBlockMarksInclusive() + ", isEndRequired()=" + isEndRequired() + ", getAlterStart()=[" + getAlterStart() + "], getAlterEnd()=[" + getAlterEnd() + "], getDebugApbl()=[" + getDebugApbl() + "], getAlterLine()=[" + getAlterLine() + "], getSubModeArray()=" + Arrays.toString(getSubModeArray());
   }
   /**
      <P>Create a new {@code LineFilter} as configured.</P>

      @return  <CODE>(M)(new xbn.text.line.LineFilter#LineFilter(LineFilter_Fieldable)&lt;O,L&gt;(this))</CODE>
    **/
   public M build()  {
      @SuppressWarnings("unchecked")
      M m = (M)(new LineFilter<O,L>(this));
      return  m;
   }
   public LineFilter_CfgForNeeder<O,L,M,R> startConfigReturnNeedable(R needer)  {
      @SuppressWarnings("unchecked")  //See xbn.neederneedable.Needer.startConfig(Class)
      Class<M> cblmo = (Class<M>)(Class)LineFilter.class;
      startConfig(needer, cblmo);
      return  this;
   }
   public LineFilter_CfgForNeeder<O,L,M,R> startConfigReturnNeedable(R needer, Class<M> needed_class)  {
      startConfigReturnNeedable(needer, needed_class);
      return  this;
   }
   /**
      <P>Sets the fully-configured object into the {@code Needer}, and returns control back to the needer-chain.</P>

      @return  <CODE>{@link com.github.xbn.neederneedable.AbstractNeedableWithSubs#endCfgWithNeededReturnNeeder(Object) endCfgWithNeededReturnNeeder}({@link #build() build}())</CODE>
    **/
   public R endCfg()  {
      return  endCfgWithNeededReturnNeeder(build());
   }
//getters...START
   public boolean isEndRequired()  {
      return  bBlockEndRqd;
   }
   public ValueAlterer<L,O> getAlterStart()  {
      return  avBlockStart;
   }
   public ValueAlterer<L,O> getAlterEnd()  {
      return  avBlockEnd;
   }
   public boolean areBlockMarksInclusive()  {
      return  bBlockMarksIncl;
   }
   public ValueAlterer<L,O> getAlterLine()  {
      return  avLine;
   }
   public Object[] getSubModeArray()  {
      return  aSubModes;
   }
   public String getModeName()  {
      return  name;
   }
   public Object getExtraErrInfo()  {
      return  oXtraErrInfo;
   }
   public Appendable getDebugApbl()  {
      return  apblDebug;
   }
   public int getSubLevel()  {
      return  subLevelNum;
   }
   public boolean doKeepBlockActive()  {
      return  doKeepBlkActv;
   }
   public boolean doKeepLineActive()  {
      return  doKeepLnActv;
   }
   public boolean doKeepInactive()  {
      return  doKeepInactv;
   }
//getters...END
}
