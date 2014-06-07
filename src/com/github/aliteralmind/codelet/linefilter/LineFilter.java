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
package  com.github.aliteralmind.codelet.linefilter;
   import  com.github.aliteralmind.codelet.linefilter.z.LineFilter_Fieldable;
   import  com.github.xbn.analyze.AbstractAnalyzer;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.analyze.validate.ValidatorComposer;
   import  com.github.xbn.io.RTIOException;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.keyed.KeyComparator;
   import  com.github.xbn.keyed.Named;
   import  com.github.xbn.keyed.SimpleNamed;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.ObjectOrCrashIfNull;
   import  com.github.xbn.lang.RuleType;
   import  com.github.xbn.text.padchop.NewVzblPadChopFor;
   import  com.github.xbn.text.padchop.VzblPadChop;
   import  com.github.xbn.util.EnumUtil;
   import  com.github.xbn.util.copyval.ValueCopier;
   import  com.github.xbn.util.itr.AbstractIterator;
   import  com.github.xbn.util.itr.IteratorUtil;
   import  java.io.IOException;
   import  java.util.ArrayList;
   import  java.util.Arrays;
   import  java.util.Iterator;
   import  java.util.List;
   import  java.util.Map;
   import  java.util.Objects;
   import  java.util.Set;
   import  java.util.TreeMap;
   import  java.util.TreeSet;
   import  org.apache.commons.lang3.StringUtils;
   import  static com.github.xbn.lang.CrashIfBase.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>Iterates through a series of <I>things</I> (most commonly, the lines in a text- or source-code file), determining which to keep and which to discard--this is done by defining blocks (such as multi-line comments) and single-line entities (such as single-line comments). Blocks are based on their start and end lines, both of which can be optionally altered to eliminate the mark itself.</P>

   <a name="cfg"/><H3>Builder Configuration: {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_Cfg LineFilter_Cfg}</H3>

   <P><UL>
      <LI><B>Block:</B><UL>
         <LI><B>Inclusivity:</B> {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}{@code ()}, {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksInclusive(boolean) blockMarksInclusive}{@code (b)}</LI>
         <LI><CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#start(ValueAlterer) start}(av)</CODE></LI>
         <LI><CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#end(BlockEnd, ValueAlterer) end}(be, av)</CODE></LI>
      </UL></LI>
      <LI><B>Line and sub-modes:</B> <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#line(ValueAlterer) line}(av)</CODE>, {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}{@code ()}, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#setSubModes(LineFilter[]) setSubModes}(blm)</CODE></LI>
      <LI><B>Other:</B> {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines) ifBlockOrLineOrNotActive}{@code (iba,ila,iia)}, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#debugTo(Appendable) debugTo}(apbl)</CODE>, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#extraErrInfo(Object) extraErrInfo}(o)</CODE>, {@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive() blockMarksExclusive}{@code ()}, <CODE>{@link com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#chainID(boolean, Object) chainID}(b,o)</CODE></LI>
   </UL></P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class LineFilter<O,L extends LineObject<O>> extends AbstractAnalyzer implements Named, Iterable<LineFilter<O,L>>  {
//config: immutable
   private final ValueAlterer<L,O> blkStartAlterer  ;   //Block start-mark
   private final ValueAlterer<L,O> blkEndAlterer    ;   //Block end-mark
   private final boolean           isBlkEndRqd      ;   //Block end-mark is required
   private final boolean           areBlkMarksIncl  ;   //Block marks are inclusive
   private final ValueAlterer<L,O> lineAlterer      ;   //Single-line mark
   private final Map<String,LineFilter<O,L>> mpSub;//All sub-modes
   private final Set<String>       subModeNameSet   ;   //The name of all sub-modes
   private final SimpleNamed       nmdBsc           ;   //The name of this mode
   private final int               subLvlNum        ;   //0 for root, 1 for children,
                                                        //   2 for grand-children, ...
   private final boolean           doKeepBlkActv    ;
   private final boolean           doKeepLnActv     ;
   private final boolean           doKeepInactv     ;
//state
   private LineFilter<O,L>         actvSub          ;   //The currently-active sub-mode.
   private LineFilter<O,L>         deepestActvSub   ;   //The grand-est active sub-mode
                                                        //   (w/ greatest level number)
   private int                     justNlzdLineNum  ;   //Just-analyzed line number
   private O                       activeLineBody   ;   //The text of the *active* line
                                                        //   (either part of a block or
                                                        //    single-line marked)
   private int                     actvBlkStartLnNum;   //Line number of the currently
                                                        //   *active* block's start-mark
   private boolean                 isBlkStartLn     ;   //Block start-mark on this line
   private boolean                 isBlkEndLn       ;   //Block end-mark on this line
   private boolean              wasInclStartOnPrevLn;   //A(n inclusive) block start-mark
//public
   public static final String DUMMY_NAME_FOR_ROOT_MODE = "ROOT_MODE";
   private static final VzblPadChop VPC_DBG = NewVzblPadChopFor.trimEscChopWithDDD(true, null, 50);
   /**
      <P>Create a new instance from a fieldable.</P>

      <P>This ends by {@link #resetStateBLM() resetting state} and {@link #resetModeBLM() mode}</LI>
      </OL></P>

      @param  fieldable  May not be {@code null}, and its fields must conform to all restrictions as documented in the <A HREF="#cfg">builder's setter functions</A>
      @see  #LineFilter(LineFilter) this(blm)
    **/
   public LineFilter(LineFilter_Fieldable<O,L> fieldable)  {
      super();

      nmdBsc = new SimpleNamed(fieldable.getModeName(), "fieldable.getModeName()");
      subLvlNum = fieldable.getSubLevel();
      blkStartAlterer = fieldable.getAlterStart().getObjectCopy();
      blkEndAlterer = fieldable.getAlterEnd().getObjectCopy();
      lineAlterer = fieldable.getAlterLine().getObjectCopy();
      doKeepBlkActv = fieldable.doKeepBlockActive();
      doKeepLnActv = fieldable.doKeepLineActive();
      doKeepInactv = fieldable.doKeepInactive();

      if(subLvlNum < 0)  {
         throw  new IllegalArgumentException("fieldable.getSubLevel() (" + subLvlNum + ") is less than zero.");
      }

      mpSub = setSubMapFromObjArray(fieldable.getSubModeArray(), "fieldable.getSubModeArray()");
      subModeNameSet = mpSub.keySet();

      for(String sNm : subModeNameSet)  {
         try  {
            if(sNm.length() == 0  ||  sNm.equals(DUMMY_NAME_FOR_ROOT_MODE))  {
               throw  new IllegalArgumentException(sSUB_MD_NAME_ERR_MSG_PRE + sNm + "\"");
            }
         }  catch(NullPointerException npx)  {
            CrashIfObject.nullOrReturnCause(sNm, "[A sub-mode's getName()]", sSUB_MD_NAME_ERR_MSG_PRE + sNm + "\"", npx);
         }

         LineFilter<O,L> blmSub = mpSub.get(sNm);
         try  {
            if(!blmSub.getName().equals(sNm))  {
               throw  new IllegalArgumentException("mp_subModes.get(\"" + sNm + "\").getName()=\"" + blmSub.getName().equals(sNm) + "\". Key must be same as mode-name.");
            }
         }  catch(RuntimeException rx)  {
            Objects.requireNonNull(blmSub, "mp_subModes.get(\"" + sNm + "\")");
            throw  CrashIfObject.nullOrReturnCause(blmSub.getName(), "mp_subModes.get(\"" + sNm + "\").getName()", null, rx);
         }

         if(blmSub.getSubLevel() != (getSubLevel() + 1))  {
            throw  new IllegalArgumentException("Element in fieldable.getSubModeArray() named \"" + blmSub.getName() + "\" has a bad getSubLevel() value (" + blmSub.getSubLevel() + "). It must be (this.getSubLevel() + 1) --> (" + (this.getSubLevel() + " + " + 1) + ") --> " + (this.getSubLevel() + 1) + ".");
         }
                                      //blmSub: Defensive copy
         mpSub.put(sNm, new LineFilter<O,L>(blmSub));
      }

      areBlkMarksIncl = fieldable.areBlockMarksInclusive();
      isBlkEndRqd = fieldable.isEndRequired();

      resetStateBLM();
      resetModeBLM();

      setDebug(fieldable.getDebugApbl(), (fieldable.getDebugApbl() != null));
   }
      private static final <O,L extends LineObject<O>> Map<String,LineFilter<O,L>> setSubMapFromObjArray(Object[] sub_modes, String array_name)  {
         Map<String,LineFilter<O,L>> mpSubs = new TreeMap<String,LineFilter<O,L>>();
         if(sub_modes == null)  {
            return  mpSubs;
         }

         int iSbCount = -1;
         try  {
            iSbCount = sub_modes.length;
         }  catch(RuntimeException rx)  {
            CrashIfObject.nullOrReturnCause(sub_modes, array_name, null, rx);
         }
         for(int i = 0; i < sub_modes.length; i++)  {
            LineFilter<O,L> blmSub = null;
            try  {
               @SuppressWarnings("unchecked")
               LineFilter<O,L> blmSub2 = (LineFilter<O,L>)sub_modes[i];
               blmSub = blmSub2;
            }  catch(ClassCastException ccx)  {
               throw  new ClassCastException(array_name + "[" + i + "] is not a LineFilter<O,L>. " + array_name + "[" + i + "].getClass().getName()=" + sub_modes[i].getClass().getName());
            }
            String sNm = null;
            try  {
               sNm = blmSub.getName();
            }  catch(RuntimeException rx)  {
               CrashIfObject.nullOrReturnCause(blmSub, array_name + "[" + i + "]", null, rx);
            }
            if(mpSubs.containsKey(sNm))  {
               throw  new IllegalStateException(array_name + "[" + i + "].getName()=\"" + sNm + "\". A sub-mode with that name already added.");
            }
            mpSubs.put(sNm, blmSub);
         }
         return  mpSubs;
      }
      private static final String sSUB_MD_NAME_ERR_MSG_PRE = "Sub-mode's must have a name that is non-null, non-empty, and not equal to LineFilter.DUMMY_NAME_FOR_ROOT_MODE: \"" + DUMMY_NAME_FOR_ROOT_MODE + "\". [sub-mode].getName()=\"";

   /**
      <P>Create a new instance as a duplicate of another.</P>

      <P>This<OL>
         <LI>Calls {@link com.github.xbn.analyze.AbstractAnalyzer#AbstractAnalyzer(Analyzer) AbstractAnalyzer}{@code (to_copy)}</LI>
         <LI>Duplicates all configuration and state from {@code to_copy}. All {@link #getSub(String) sub-modes} are fully duplicated.</LI>
      </OL></P>

      @param  to_copy  May not be {@code null}.
      @see  #getObjectCopy()
      @see  #LineFilter(LineFilter_Fieldable) this(fieldable)
    **/
   public LineFilter(LineFilter<O,L> to_copy)  {
      super(to_copy);

      subLvlNum = to_copy.getSubLevel();
      blkStartAlterer = getAVCopy(to_copy.getAlterStart(), "to_copy.getAlterStart()");
      blkEndAlterer = getAVCopy(to_copy.getAlterEnd(), "to_copy.getAlterEnd()");
      lineAlterer = getAVCopy(to_copy.getAlterLine(), "to_copy.getAlterLine()");
      doKeepBlkActv = to_copy.doKeepBlockActive();
      doKeepLnActv = to_copy.doKeepLineActive();
      doKeepInactv = to_copy.doKeepInactive();

      isBlkEndRqd = to_copy.isBlkEndRqd;
      areBlkMarksIncl = to_copy.areBlkMarksIncl;

      mpSub = new TreeMap<String,LineFilter<O,L>>();
      Iterator<LineFilter<O,L>> subItr = to_copy.iterator();
      while(subItr.hasNext())  {
         LineFilter<O,L> sub = subItr.next();
         mpSub.put(sub.getName(), new LineFilter<O,L>(sub));
      }
      subModeNameSet = new TreeSet<String>();
      subModeNameSet.addAll(to_copy.subModeNameSet);
      actvSub = to_copy.getActiveSub();
      deepestActvSub = to_copy.getActiveDeepest();

      nmdBsc = new SimpleNamed(to_copy.nmdBsc.getName());

      //state
      activeLineBody       = to_copy.getActiveLine();
      actvBlkStartLnNum    = to_copy.getActiveBlockStartLineNum();
      justNlzdLineNum      = to_copy.getLineNumber();
      isBlkStartLn         = to_copy.isBlockStartLine();
      isBlkEndLn           = to_copy.isBlockEndLine();
      wasInclStartOnPrevLn = to_copy.wasStartMarkOnPrevLine();
   }
      private static final <L extends LineObject,O> ValueAlterer<L,O> getAVCopy(ValueAlterer<L,O> alterer, String alter_name)  {
         @SuppressWarnings("unchecked")
         ValueAlterer<L,O> av2 = (ValueAlterer<L,O>)ObjectOrCrashIfNull.
            <ValueAlterer>getCopy(alterer, ValueAlterer.class, alter_name);
         return  av2;
      }
   /**
      <P>Get the currently-active sub-mode that is a direct-child of this one. It is only possible for a sub-mode to be active inside an active block in its immediate-parent mode.</P>

      @return  <UL>
         <LI>A non-{@code null} {@code LineFilter} whose sub-level is equal to <CODE>(this.{@link #getSubLevel() getSubLevel}() + 1)</CODE></LI>
         <LI>{@code null}: If no sub-modes are active.</LI>
      </UL>
      @see  #getActiveDeepest()
    **/
   public LineFilter<O,L> getActiveSub()  {
      return  actvSub;
   }
   /**
      <P>Get the currently-active sub-mode whose level is the farthest away from (greater than) this one.</P>

      @return  <UL>
         <LI>A non-{@code null} {@code LineFilter} whose sub-level is greater than <CODE>this.{@link #getSubLevel() getSubLevel}()</CODE>. It may or may not be the same sub-mode as {@link #getActiveSub() getActiveSub}{@code ()}.</LI>
         <LI>{@code null}: If no sub-modes are active.</LI>
      </UL>
      @see  #getActiveSub()
    **/
   public LineFilter<O,L> getActiveDeepest()  {
      return  deepestActvSub;
   }
   /**
      <P>How many levels away from the root mode is this {@code LineFilter}?</P>

      @return  <UL>
         <LI>CrashIfZero: If this is the root mode.</LI>
         <LI>One: If this is a direct child sub-mode of the root mode.</LI>
         <LI>And up.</LI>
      </UL>
      @see  #getActiveSub()
      @see  #getActiveDeepest()
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_Cfg#LineFilter_Cfg(int, String) LineFilter_Cfg(i,s)
    **/
   public int getSubLevel()  {
      return  subLvlNum;
   }
   /**
      <P>Should lines in a block be kept?. This setting is used only by the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) line iterator}. It is ignored by <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/text/line/LineFilter.html#getActiveLine(L)">getActiveLine</A>(L)</CODE>.</P>

      @return  <UL>
         <LI>{@code true}: The line should be kept.</LI>
         <LI>{@code true}: The line should be discarded.</LI>
      </UL>
      @see  #doKeepLineActive()
      @see  #doKeepInactive()
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#ifBlockOrLineOrNotActive(ActiveBlockLines, ActiveSingleLines, InactiveLines)
    **/
   public boolean doKeepBlockActive()  {
      return  doKeepBlkActv;
   }
   /**
      <P>Should lines that are single-line entities be kept?. This setting is used only by the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) line iterator}.</P>

      @see  #doKeepBlockActive()
    **/
   public boolean doKeepLineActive()  {
      return  doKeepLnActv;
   }
   /**
      <P>Should lines that are not part of a block, and not a single-line entity be kept?. This setting is used only by the {@linkplain com.github.aliteralmind.codelet.linefilter.LineFilter#activeLineIterator(Iterator, Appendable) line iterator}.</P>

      @see  #doKeepBlockActive()
    **/
   public boolean doKeepInactive()  {
      return  doKeepInactv;
   }
   /**
      <P>Iterator for all direct-child sub-modes.</P>

      @return  An iterator for all sub-modes whose {@link #getSubLevel() level} is equal to
      <BR> &nbsp; &nbsp; {@code (this.getSubLevel() - 1)}</CODE>
      @see  #activeLineIterator(Iterator, Appendable)
    **/
   public Iterator<LineFilter<O,L>> iterator()  {
      return  (new ItrAllSubs<O,L>(this, subModeNameSet));
   }
   /**
      <P>Returns lines that are either active or inactive, and explicitely configured to be kept. This is a filter that keeps only the lines as configured by<UL>
         <LI>{@link #doKeepBlockActive() doKeepBlockActive}{@code ()},</LI>
         <LI>{@link #doKeepLineActive() doKeepLineActive}{@code ()}, and</LI>
         <LI>{@link #doKeepInactive() doKeepInactive}{@code ()}.</LI>
      </UL></P>

      <P>Although sub-modes always exist within <I>active</I> blocks (and are therefore returned when {@code doKeepBlockActive()} is true), this iterator does not directly recognize sub-modes.</P>

      @see  #isActive()
      @see  #isBlockActive()
      @see  #isLineActive()
      @see  #iterator()
      @see  com.github.aliteralmind.codelet.linefilter.LineObjectIterator
    **/
   public LineObjectIterator<O,L> activeLineIterator(Iterator<O> line_itr, Appendable dbgEachLine_ifNonNull)  {
      return  (new ItrAllLines<O,L>(this, line_itr, dbgEachLine_ifNonNull));
   }
   /**
      <P>Reset state, in preparation for analyzing the next &quot;line&quot;.</P>

      <P>This calls<OL>
         <LI><CODE>{@link com.github.xbn.analyze.AbstractAnalyzer super}.{@link com.github.xbn.analyze.AbstractAnalyzer#resetState() resetState}()</CODE></LI>
         <LI><CODE>{@link #resetStateBLM() resetStateBLM}()</CODE></LI>
      </OL></P>
      @see  #resetCounts()
    **/
   public void resetState()  {
      super.resetState();
      resetStateBLM();
   }
   /**
      <P>Reset state, so the next &quot;line&quot; can be analyzed.</P>

      <P>This calls<OL>
         <LI><CODE>{@link com.github.xbn.analyze.AbstractAnalyzer super}.{@link com.github.xbn.analyze.AbstractAnalyzer#resetCounts() resetCounts}()</CODE></LI>
         <LI><CODE>{@link #resetCountsBLM() resetCountsBLM}()</CODE></LI>
      </OL></P>
      @see  #resetState()
    **/
   public void resetCounts()  {
      super.resetCounts();
      resetCountsBLM();
   }
   /**
      <P>Reset state specific to this {@code LineFilter}.</P>

      @see  #resetCounts()
    **/
   protected final void resetCountsBLM()  {
      getAlterStart().resetCounts();
      getAlterEnd().resetCounts();
      getAlterLine().resetCounts();
      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().resetCounts();
      }
   }
   /**
      <P>Reset state specific to this {@code LineFilter}.</P>

      <P>This<OL>
         <LI>Sets the following to {@code false}: <CODE>{@link #isLineActive() isLineActive}{@code ()}</CODE>, <CODE>{@link #isBlockActive() isBlockActive}{@code ()}</CODE>, <CODE>{@link #isBlockStartLine() isBlockStartLine}{@code ()}</CODE>, <CODE>{@link #isBlockEndLine() isBlockEndLine}{@code ()}</CODE>, <CODE>{@link #wasStartMarkOnPrevLine() wasStartMarkOnPrevLine}{@code ()}</CODE></LI>
         <LI>Sets {@link #getLineNumber() getLineNumber}{@code ()} to {@code -1}</LI>
      </OL></P>

      @see  #resetState()
    **/
   protected void resetStateBLM()  {
      getAlterStart().resetState();
      getAlterEnd().resetState();
      getAlterLine().resetState();
      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().resetState();
      }
   }
   /**
      <P>Declare that the last line was analyzed.</P>

      <P>This calls<OL>
         <LI>{@link #declareParentBlockClosed() declareParentBlockClosed}{@code ()}</LI>
         <LI>{@link #declareAllLinesAnalyzed() declareAllLinesAnalyzed}{@code ()} for all {@link #getSub(String) sub-modes}</LI>
         <LI>{@link #resetMode() resetMode}{@code ()}</LI>
      </OL></P>

      <P>Equal to
      <BR> &nbsp; &nbsp; {@link #resetMode() resetMode}{@code ()}</P>

      @exception  InvalidBlockException  If a block is still open and {@link #isEndRequired() isEndRequired}{@code ()} is {@code true}.
    **/
   public void declareAllLinesAnalyzed()  {
      if(!isBlockEndLine()  &&         //If the final line is block-end
            isBlockActive()  &&  isEndRequired())  {
         throw  new InvalidBlockException(getXMsg(getModePrefix() + "End of input reached, but a block is still open (isEndRequired() is true)." + LINE_SEP + "this.toString()=" + toString(), getExtraErrInfo()));
//					" - getAlterStart(): " + getAlterStart() + LINE_SEP +
//					" - getAlterEnd(): " + getAlterEnd(), getExtraErrInfo()));
      }

      declareParentBlockClosed();

      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().declareAllLinesAnalyzed();
      }

      resetMode();
   }
   /**
      <P>Reset this mode to as if no lines were ever analyzed.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; {@link #resetModeBLM() resetModeBLM}{@code ()}</P>

      @see  #declareAllLinesAnalyzed()
      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>
    **/
   public void resetMode()  {
      resetModeBLM();
   }
   /**
      <P>Reset modes specific to this {@code LineFilter}.</P>

      <P>This<OL>
         <LI>Sets the following to {@code null}: {@link #isLineActive() isLineActive}{@code ()}, {@link #isBlockActive() isBlockActive}{@code ()}, {@link #isBlockStartLine() isBlockStartLine}{@code ()}, {@link #isBlockEndLine() isBlockEndLine}{@code ()}, {@link #wasStartMarkOnPrevLine() wasStartMarkOnPrevLine}{@code ()}, {@link #getActiveSub() getActiveSub}{@code ()}, {@link #getActiveDeepest() getActiveDeepest}{@code ()}</LI>
         <LI>Sets {@link #getLineNumber() getLineNumber}{@code ()} to {@code 0}</LI>
         <LI>And calls {@code resetMode()} for all {@link #getSub(String) sub-modes}.</LI>
      </OL></P>

      @see  #resetState()
    **/
   protected void resetModeBLM()  {
      activeLineBody       = null;
      actvBlkStartLnNum    = -1;
      justNlzdLineNum      = 0;
      isBlkStartLn         = false;
      isBlkEndLn           = false;
      wasInclStartOnPrevLn = false;
      actvSub              = null;
      deepestActvSub       = null;

      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().resetMode();
      }
   }
   /**
      <P>The validator that detects the block-start marker.</P>

      @return  A non-{@code null} validator that returns {@code true} when the block start-mark is found.
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#start(ValueAlterer) LineFilter_Cfg#start(av)
      @see  #getAlterEnd()
      @see  #getAlterLine()
      @see  #areBlockMarksInclusive()
    **/
   public ValueAlterer<L,O> getAlterStart()  {
      return  blkStartAlterer;
   }
   /**
      <P>The validator that detects the block-end marker.</P>

      @return  A non-{@code null} validator that returns {@code true} when the block end-mark is found.
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#end(BlockEnd, ValueAlterer) LineFilter_Cfg#end(av,b)
      @see  #getAlterStart()
      @see  #isEndRequired()
    **/
   public ValueAlterer<L,O> getAlterEnd()  {
      return  blkEndAlterer;
   }
   /**
      <P>Must blocks be ended?.</P>

      @return  <UL>
         <LI>{@code true}:  A block end-mark is required in order for a block to be successfully closed.</LI>
         <LI>{@code false}:  The block end-mark is optional. The end of input (or the end of the super-mode's block in which the current block exists) causes a block mode to be successfully closed.</LI>
      </UL>
      @see  #getAlterEnd()
    **/
   public boolean isEndRequired()  {
      return  isBlkEndRqd;
   }
   /**
      <P>Are the block start and end-marks considered part of the active block?.</P>

      @return  <UL>
         <LI>{@code true}: The marks are inclusive, meaning a block is {@link #isBlockActive() active} on the same line as its {@link #getActiveBlockStartLineNum() start line}, and inactive on the first line <I>after</I> its {@link #getAlterEnd() end-line}.
         <LI>{@code false}: The marks are exclusive, meaning only lines <I>between</I> the start and end-marks are active. This implies that, when exclusive, the {@linkplain #isBlockStartLine() start} and {@linkplain #isBlockStartLine() end line}s may not be (on) the same line.</LI>
      </UL>
      @see  #getAlterStart() start-mark
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksInclusive()
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#blockMarksExclusive()
    **/
   public boolean areBlockMarksInclusive()  {
      return  areBlkMarksIncl;
   }
   /**
      <P>The validator that detects the single-line marker. This validator only analyzes lines when the block is not currently {@link #isBlockActive() active}.</P>

      @return  A non-{@code null} validator that returns {@code true} when the single-line mark is found.
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#line(ValueAlterer) LineFilter_Cfg#line(av)
      @see  #getAlterStart()
    **/
   public ValueAlterer<L,O> getAlterLine()  {
      return  lineAlterer;
   }
   /**
      <P>Get a new set containing the name of all sub-modes.</P>

      @return  The {@link java.util.Map#keySet() key set} from the internal {@link java.util.Map map} of {@link #getSub(String) sub-modes}.
      @see  #getSub(String) getSub(s)
    **/
   public Set<String> getModeSubNameSet()  {
      return  new TreeSet<String>(subModeNameSet);
   }
   /**
      <P>Does a sub-mode exist with a particular name?.</P>

      @param  mode_name  The name of the sub-mode to find. May not be {@code null}.(..........?)
      @return  <CODE><I>[the internal {@link java.util.Map map} of {@link #getSub(String) sub-modes}]</I>.{@link java.util.Map#containsKey(Object) containsKey}(mode_name)</CODE>
      @see  #getSub(String) getSub(s)
    **/
   public boolean containsSub(String mode_name)  {
try  {
   mpSub.containsKey(null);
   System.out.println("mpSub.containsKey(null) does NOT throw an exception.");
}  catch(RuntimeException rx)  {
   System.out.println("mpSub.containsKey(null) DOES throw an exception.");
}
      return  mpSub.containsKey(mode_name);
   }
   /**
      <P>Is a particular sub-mode active?.</P>

      @return  <CODE>{@link #getSub(String) getSub}(mode_name).{@link #isActive() isActive}()</CODE>
    **/
   public boolean isSubModeActive(String mode_name)  {
      return  getSub(mode_name).isActive();
   }
   /**
      <P>Get a sub-mode from its name.</P>

      @see  #isSubModeActive(String) isSubModeActive(s)
      @see  #containsSub(String) containsSub(s)
      @see  #getModeSubNameSet()
      @see  #getSubCount()
      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_CfgForNeeder#setSubModes(LineFilter[]) CfgForNeeder#setSubModes(blm[])
    **/
   public LineFilter<O,L> getSub(String mode_name)  {
      LineFilter<O,L> blm = mpSub.get(mode_name);
      if(blm == null)  {
         throw  new IllegalArgumentException("containsSub(mode_name) is false. mode_name=\"" + mode_name + "\"");
      }
      return  blm;
   }
   /**
      <P>The number of direct-child sub-modes.</P>

      @return  The number of sub-modes that have a level equal to <CODE>(this.{@link #getSubLevel() getSubLevel}() - 1)</CODE>
      @see  #getSub(String) getSub(s)
      @see  #hasSubMode()
    **/
   public int getSubCount()  {
      return  mpSub.size();
   }
   /**
      <P>Get the name of this mode.</P>

      @see  com.github.aliteralmind.codelet.linefilter.z.LineFilter_Cfg#LineFilter_Cfg(int, String) LineFilter_Cfg(i,s)
    **/
   public String getName()  {
      return  nmdBsc.getName();
   }
   /**
      <P>Is there at least one sub-mode?.</P>

      @return  <CODE>({@link #getSubCount() getSubCount}() > 0)</CODE>
    **/
   public boolean hasSubMode()  {
      return  (getSubCount() > 0);
   }
   /**
      <P>Is this mode currently active?.</P>

      @return  <CODE>({@link #isBlockActive() isBlockActive}() &nbsp;|| &nbsp;{@link #isLineActive() isLineActive}())</CODE>
    **/
   public boolean isActive()  {
      return  (isBlockActive()  ||  isLineActive());
   }
   /**
      <P>Was the just-analyzed line valid according to the line-validator?.</P>

      @return  <CODE>(!{@link #isBlockActive() isBlockActive}() &nbsp;&amp;&amp; &nbsp;{@link #getActiveLine() getActiveLine}() != null)</CODE>
    **/
   public boolean isLineActive()  {
      return  (!isBlockActive()  &&  getActiveLine() != null);
   }
   /**
      <P>Was the just-analyzed line part of a block?.</P>

      @return  <CODE>({@link #getActiveBlockStartLineNum() getActiveBlockStartLineNum}() != -1)</CODE>
    **/
   public boolean isBlockActive()  {
      return  (getActiveBlockStartLineNum() != -1);
   }
   /**
      <P>The just analyzed line, if it is active.</P>

      @return  The same value as returned by the most recent call to  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>, or {@code null} if {@code getActiveLine(L)} was never called, or {@link #resetMode() resetMode}{@code ()} was more recently called.
      @see  #getActiveBlockStartLineNum()
      @see  #isBlockActive()
      @see  #isBlockStartLine()
      @see  #isBlockEndLine()
      @see  #isLineActive()
    **/
   public O getActiveLine()  {
      return  activeLineBody;
   }
   /**
      <P>The line number of the currently-active block's start-line.</P>

      @return  <UL>
         <LI>If a block is currently {@link #isBlockActive() active}: A number greater than zero, at which {@link #isBlockStartLine() isBlockStartLine}{@code ()} was {@code true}.</LI>
         <LI>Otherwise: {@code -1}.</LI>
      </UL>
      @see  #isBlockActive()
      @see  #getActiveLine()
    **/
   public int getActiveBlockStartLineNum()  {
      return  actvBlkStartLnNum;
   }
   /**
      <P>Was the just-analyzed line a block start-or-end line?.</P>

      @return  <CODE>({@link #isBlockStartLine() isBlockStartLine}() &nbsp;|| &nbsp;{@link #isBlockEndLine() isBlockEndLine}())</CODE>
    **/
   public boolean isBlockStartOrEndLine()  {
      return  (isBlockStartLine()  ||  isBlockEndLine());
   }
   /**
      <P>Set any extra error information.</P>

      <P>This calls {@link com.github.xbn.lang.ExtraErrInfoable#setExtraErrInfo(Object) setExtraErrInfo}{@code (info)} for the block {@link #getAlterStart() start} and {@link #getAlterStart() end} alterers, {@link #getAlterStart() line} alterer, and for every {@link #getSub(String) sub-mode}.</P>
    **/
   public void setExtraErrInfo(Object info)  {
      getAlterStart().setExtraErrInfo(info);
      getAlterEnd().setExtraErrInfo(info);
      getAlterLine().setExtraErrInfo(info);
      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().setExtraErrInfo(info);
      }
   }
   /**
      <P>The just-analyzed line-number.</P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>
    **/
   public int getLineNumber()  {
      return  justNlzdLineNum;
   }
   /**
      <P>Was the just-analyzed line a block-start marker?.</P>

      @see  #isBlockEndLine()
      @see  #getActiveBlockStartLineNum()
      @see  #isBlockStartOrEndLine()
    **/
   public boolean isBlockStartLine()  {
      return  isBlkStartLn;
   }
   /**
      <P>Was the just-analyzed line a block-end marker?.</P>

      @see  #isBlockStartLine()
    **/
   public boolean isBlockEndLine()  {
      return  isBlkEndLn;
   }
   /**
      <P>Called by the parent mode, to inform sub-modes that the block in which they exist has been closed--either because the block-end marker was found, or the last line was analyzed. Sub-modes are only active inside a parent's {@link #isBlockActive() block mode}.</P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>
      @see  #declareAllLinesAnalyzed()
    **/
   protected void declareParentBlockClosed()  {
      if(isDebugOn()) { getDebugAptr().appent(getDebugPrefix()).appentln(": declareParentBlockClosed(o)"); }

      if(isBlockEndLine())  {  //If the final line is block-end
         return;
      }

      if(!isBlockActive())  {
         return;
      }
      if(isEndRequired())  {
         throw  new IllegalStateException(getXMsg(getModePrefix() + "Parent block is closed, but isBlockActive() and isEndRequired() are both true.", getExtraErrInfo()));
      }

      actvBlkStartLnNum = -1;
      isBlkStartLn = false;
      isBlkEndLn = false;

      if(getActiveSub() != null)  {
         getActiveSub().declareParentBlockClosed();
      }
   }
   /**
      <P>Was the previously-analyzed line a block-start line?. If block marks {@link #areBlockMarksInclusive() are inclusive}, then the about-to be analyzed line is the first active line in the block.</P>

      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>
    **/
   public boolean wasStartMarkOnPrevLine()  {
      return  wasInclStartOnPrevLn;
   }
   /**
      <P>Get the currently-active line--if it is a block-start or -end line, or a single-line entity, it may be altered.</P>

      @return  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(LineObject)">getActiveLine</A>((L)new <!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="SimpleLineObject.html#SimpleLineObject(int, O)">SimpleLineObject</A>&lt;O&gt;(lineNum, body))</CODE>
    **/
   public O getActiveLine(int lineNum, O body)  {
      @SuppressWarnings("unchecked")
      L l = (L)new SimpleLineObject<O>(lineNum, body);
      return  getActiveLine(l);
   }
   /**
      <P>Get the currently-active line--if it is a block-start or -end line, or a single-line entity, it may be altered.</P>

      <P>After the last line is analyzed, call {@link #declareAllLinesAnalyzed() declareAllLinesAnalyzed}{@code ()}.</P>

      @param  line_obj  May not be {@code null}, and its {@link com.github.aliteralmind.codelet.linefilter.LineObject#getNumber() line number} must be greater than ({@link #getLineNumber() getLineNumber}{@code ()}.
      @return  If {@code line_obj} is a<UL>
         <LI>{@link #isBlockStartLine() the first line in a block}:
         <BR> &nbsp; &nbsp; <CODE>{@link #getAlterStart() getAlterStart}().<!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="{@docRoot}/com/github/xbn/analyze/alter/ValueAlterer.html#getAltered(V, A)">getAltered</A>(line_obj, line_object.{@link com.github.aliteralmind.codelet.linefilter.LineObject#getBody() getBody}())</CODE></LI>
         <LI>{@link #isBlockStartLine() part of a block} but not the start-or-end line: {@code line_object.getBody()}</CODE></LI>
         <LI>{@link #isBlockEndLine() the last line in a block}:
         <BR> &nbsp; &nbsp; <CODE>{@link #getAlterEnd() getAlterEnd}().getAltered(line_obj, line_object.getBody())</CODE>
         <BR><I>(All sub-modes are also informed that their {@link #declareParentBlockClosed() parent block has closed}.)</I></LI>
         <LI>{@link #isLineActive() a single-line entity}: <CODE>{@link #getAlterLine() getAlterLine}().getAltered(line_obj, line_object.getBody())</CODE></LI>
      </UL>Otherwise, the line is not active, and this returns {@code null}.
      @exception  IllegalStateException  If this line is part of a block, and more than one {@link #getSubCount() direct-child} sub-mode is active inside of it. Use {@link #getAllActiveSubModesBad() getAllActiveSubModesBad}{@code ()} for diagnostics.
      @exception  InvalidBlockException  If<UL>
         <LI>The block-end line is {@link #isEndRequired() required}, this line is a block-start line, and the previous block was not closed ({@link #getActiveBlockStartLineNum() getActiveBlockStartLineNum}{@code ()} is not {@code -1}).</LI>
         <LI>This is (contains) both the block-start and end marks, and block marks are {@linkplain #areBlockMarksInclusive() exclusive}.</LI>
         <LI>A block-end mark is found, but no block was started.</LI>
      </UL>
      @see  #wasStartMarkOnPrevLine()
      @see  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(int, O)">getActiveLine</A>(i,O)</CODE>
    **/
   public O getActiveLine(L line_obj)  {
      resetState();
      try  {
         if(getLineNumber() >= line_obj.getNumber())  {
            throw  new IllegalArgumentException("getLineNumber()=" + getLineNumber() + ". line_obj.getNumber() must be greater-than " + getLineNumber() + ", but is actually " + line_obj.getNumber() + ".");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(line_obj, "line_obj", null, rx);
      }

      justNlzdLineNum = line_obj.getNumber();

//		if(isDebugOn()) { getDebugAptr().appent(getDebugPrefix()).appent("line_obj.getBody()=[").appent(VPC_DBG.get(line_obj.getBody())).appentln("]"); }

      if(isBlkEndLn)  {
         //The previous line was a block end-mark. The block is now
         //inactive, whether or not bounds are inclusive
         actvBlkStartLnNum = -1;
      }
      isBlkStartLn = false;
      isBlkEndLn = false;
      activeLineBody = null;

      if(wasStartMarkOnPrevLine())  {
         actvBlkStartLnNum = line_obj.getNumber();
      }

      O oLtrdLn = getAlterStart().getAltered(line_obj, line_obj.getBody());
      if(getAlterStart().wasAltered())  {
         activeLineBody = oLtrdLn;
         if(isBlockActive()  &&  isEndRequired())  {
            throw  new InvalidBlockException(getModePrefix() + "Block start-marker found before previous block closed (isEndRequired() is true, getActiveBlockStartLineNum()=" + getActiveBlockStartLineNum() + "). getAlterStart().getAltered(line_obj, line_obj.getBody())=\"" + VPC_DBG.get(activeLineBody) + "\"" + LINE_SEP +
               " - line_obj=[" + line_obj + "]" + LINE_SEP +
               " - getAlterStart(): " + getAlterStart() + LINE_SEP +
               " - getAlterEnd(): " + getAlterEnd());
         }

         isBlkStartLn = true;

         if(areBlockMarksInclusive())  {
            actvBlkStartLnNum = line_obj.getNumber();
            return  updateSubsDebugGetDeepestActvLn(line_obj);
         }
         wasInclStartOnPrevLn = true;  //In the *next* call, this'll be "previous"
      }

      //isLineActive() is false

      //Either:
      // - The inclusive start-mark is NOT on this line, or
      // - The exclusive start-mark may or may not be on this line

      oLtrdLn = getAlterEnd().getAltered(line_obj, line_obj.getBody());
      if(getAlterEnd().wasAltered())  {
         activeLineBody = oLtrdLn;
         if(wasStartMarkOnPrevLine())  {  //THIS line ("prev" in next call to this function)
            throw  new InvalidBlockException(getModePrefix() + "Start and end bounds are exclusive but both exist on this line." + LINE_SEP +
               " - line_obj=[" + line_obj + "]" + LINE_SEP +
               " - getAlterStart(): " + getAlterStart() + LINE_SEP +
               " - getAlterEnd(): " + getAlterEnd());
         }

         if(!isBlockActive())  {
            throw  new InvalidBlockException(getModePrefix() + "Block end-marker found, but isBlockActive() is false." + LINE_SEP +
               " - line_obj=[" + line_obj + "]" + LINE_SEP +
               " - getAlterEnd(): " + getAlterEnd());
         }

         //Only the end condition is on this line

         isBlkEndLn = true;
         for(String sNm : subModeNameSet)  {
            getSub(sNm).declareParentBlockClosed();
         }

         if(!areBlockMarksInclusive())  {
            actvBlkStartLnNum = -1;
         }

         if(actvBlkStartLnNum != -1)  {
            return  updateSubsDebugGetDeepestActvLn(line_obj);
         }
      }

      if(isBlockActive())  {
         activeLineBody = line_obj.getBody();
         return  updateSubsDebugGetDeepestActvLn(line_obj);
      }

      //isBlockActive() is false
      //wasStartMarkOnPrevLine() may be true or false (as set in THIS call)
      //isLineActive() is false

      oLtrdLn = getAlterLine().getAltered(line_obj, line_obj.getBody());
      if(getAlterLine().wasAltered())  {
         activeLineBody = oLtrdLn;
         debugActiveLine();
      }

      return  activeLineBody;
   }
      private O updateSubsDebugGetDeepestActvLn(L line_obj)  {
         assert  (getActiveLine() != null) : "getActiveLine() is null!";
         actvSub = null;
         deepestActvSub = null;
         LineFilter<O,L> blmActvSub2 = null;
         LineFilter<O,L> blmActvDpst2 = null;
         int iActv = 0;
         for(String sNm : subModeNameSet)  {
            LineFilter<O,L> blmSub = getSub(sNm);
            blmSub.getActiveLine(line_obj);  //Ignoring return value
            if(blmSub.isActive())  {
               blmActvSub2 = blmSub;
               iActv++;
            }
         }
         if(iActv > 1)  {
            throw  new IllegalStateException(iActv + " sub-modes active. See getAllActiveSubModesBad().");
         }

         actvSub = blmActvSub2;
         deepestActvSub = actvSub;
         if(deepestActvSub != null)  {
            while(deepestActvSub.getActiveSub() != null)  {
               deepestActvSub = deepestActvSub.getActiveSub();
            }
         }
         debugActiveLine();

         return  ((getActiveDeepest() == null) ? getActiveLine()
            :  getActiveDeepest().getActiveLine());
      }
      private void debugActiveLine()  {
         if(!isDebugOn())  {
            return;
         }
         appendActiveDebugLine(getDebugAptr().getTextAppender(), this, true, false);

         if(!isBlockStartOrEndLine())  {
            return;
         }
         if(getActiveSub() != null)  {
            getDebugAptr().appent(getActiveSub().getDebugPrefix());

            if(getActiveSub() != getActiveDeepest())  {
               getDebugAptr().appent(", deepest: \"").appent(getActiveDeepest().getName()).appent("\", lvl=").appent(getActiveDeepest().getSubLevel());
            }
            getDebugAptr().appentln();
         }
      }
   /**
      <P>Diagnostics when more than one direct-child sub-mode is active. A sub-mode may only be active inside of its parent's active block. Only one {@link #getSubCount() direct-child} sub-mode may be active at a time.</P>

      @return  A list of all active direct-child sub-modes, which contains at least two elements.
      @exception  IllegalStateException  If one or no sub-modes are active.
    **/
   public List<LineFilter<O,L>> getAllActiveSubModesBad()  {
      ArrayList<LineFilter<O,L>> alblmActvSubs = new ArrayList<LineFilter<O,L>>(2);
      for(String sNm : subModeNameSet)  {
         LineFilter<O,L> blmSub = getSub(sNm);
         if(blmSub.isActive())  {
            alblmActvSubs.add(blmSub);
         }
      }
      if(alblmActvSubs.size() < 2)  {
         throw  new IllegalStateException(((getActiveSub() == null) ? 0 : 1) + " sub-mode(s) active. Use getActiveSub(). (Use this function only when this.getActiveLine(lo) throws an IllegalStateException stating more than one sub-mode is active.)");
      }
      return  alblmActvSubs;
   }
   /**
      @return  <CODE>true</CODE> If <CODE>to_compareTo</CODE> is non-<CODE>null</CODE>, a <CODE>LineFilter</CODE>, and <CODE>{@link #areFieldsEqual(LineFilter) areFieldsEqual}((LineFilter)to_compareTo)</CODE> is <CODE>true</CODE>. <I>This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).</I>
    **/
   @Override
   public boolean equals(Object to_compareTo)  {
      //Check for object equality first, since it's faster than instanceof.
      if(this == to_compareTo)  {
         return  true;
      }
      if(!(to_compareTo instanceof LineFilter))  {
         //to_compareTo is either null or not an LineFilter.
         //java.lang.Object.object(o):
         //   "For any non-null reference value x, x.equals(null) should return false."
         //See the bottom of this class for a counter-argument (which I'm not going with).
         return  false;
      }

      //Safe to cast
      LineFilter o = (LineFilter)to_compareTo;

      //Finish with field-by-field comparison.
      @SuppressWarnings("unchecked")            //WHY is this okay?
      LineFilter<O,L> blo = (LineFilter<O,L>)o;
      return  areFieldsEqual(blo);
   }
   /**
      <P>Diagnostics for the currently-active (just analyzed) line. It is recommended to call this immediately after each call to  <CODE><!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="#getActiveLine(L)">getActiveLine</A>(L)</CODE>. This is done automatically when {@link com.github.xbn.io.Debuggable#isDebugOn() debugging} is on.</P>

      @param  to_appendTo  May not be {@code null}.
      @param  mode_toDebug  May not be {@code null}.
      @param  do_lines  If {@code true}, {@link #isLineActive() active lines} are debugged.
      @param  do_blockMids  If {@code true} then all active lines between the start and end marks are debugged. If {@code false}, only start and end lines are debugged.
      @return  {@code to_appendTo} If the just-analyzed line is not active (or is active, but is suppressed by {@code do_lines} or {@code do_blockMids}).
    **/
   public static final <O,L extends LineObject<O>> Appendable appendActiveDebugLine(Appendable to_appendTo, LineFilter<O,L> mode_toDebug, boolean do_lines, boolean do_blockMids)  {
      return  appendActiveDebugLine("", to_appendTo, mode_toDebug, do_lines, do_blockMids);
   }
      private static final <O,L extends LineObject<O>> Appendable appendActiveDebugLine(String debug_prefix, Appendable to_appendTo, LineFilter<O,L> mode_toDebug, boolean do_lines, boolean do_blockMids)  {
         try  {
            if(!mode_toDebug.isActive())  {
               return  to_appendTo;
            }

            if(mode_toDebug.isLineActive()  &&  do_lines)  {
               mode_toDebug.appendDebugPrefix(to_appendTo);
               return  to_appendTo.append(debug_prefix).append("LINE").append(LINE_SEP);
            }

            if(mode_toDebug.getActiveSub() != null)  {
               appendActiveDebugLine(debug_prefix, to_appendTo, mode_toDebug.getActiveSub(), do_lines, do_blockMids);
            }  else if(do_blockMids)  {
               mode_toDebug.appendDebugPrefix(to_appendTo);
               to_appendTo.append(debug_prefix).append("BLOCK");
               if(mode_toDebug.isBlockStartLine())  {
                  to_appendTo.append(debug_prefix).append("START");
               }

               if(mode_toDebug.isBlockEndLine())  {
                  to_appendTo.append(debug_prefix).append("END");
               }

               to_appendTo.append(LINE_SEP);

            }  else  if(mode_toDebug.isBlockStartLine())  {
               mode_toDebug.appendDebugPrefix(to_appendTo);
               to_appendTo.append(debug_prefix).append("BLOCKSTART");
               if(mode_toDebug.isBlockEndLine())  {
                  to_appendTo.append(debug_prefix).append("END");
               }

               to_appendTo.append(LINE_SEP);

            }  else if(mode_toDebug.isBlockEndLine())  {
               mode_toDebug.appendDebugPrefix(to_appendTo);
               to_appendTo.append(debug_prefix).append("     END").append(LINE_SEP);
            }

            return  to_appendTo;

         }  catch(RuntimeException rx)  {
            Objects.requireNonNull(to_appendTo, "to_appendTo");
            throw  CrashIfObject.nullOrReturnCause(mode_toDebug, "mode_toDebug", null, rx);
         }  catch(IOException iox)  {
            throw  new RTIOException(iox);
         }
      }
      private String getModePrefix()  {
         return  appendModePrefix((new StringBuilder())).toString();
      }
      private Appendable appendModePrefix(Appendable to_appendTo)  {
         return  appendPrefix("", to_appendTo);
      }
      private String getDebugPrefix()  {
         return  appendDebugPrefix((new StringBuilder())).toString();
      }
      private Appendable appendDebugPrefix(Appendable to_appendTo)  {
         return  appendPrefix("<LF> ", to_appendTo);
      }
      private Appendable appendPrefix(String prefixfix, Appendable to_appendTo)  {
         try  {
            to_appendTo.append(prefixfix);
            for(int i = 0; i < getSubLevel(); i++)  {
               to_appendTo.append("   ");
            }

            to_appendTo.append("mode=[");

            if(getName().equals(DUMMY_NAME_FOR_ROOT_MODE))  {
               to_appendTo.append("root");
            }  else  {
               to_appendTo.append(getName());
            }

            to_appendTo.append(",").append("lvl=").append(new Integer(getSubLevel()).toString()).append(",line=").append(new Integer(getLineNumber()).toString());
            return  to_appendTo.append("]: ");
         }  catch(IOException iox)  {
            throw  new RTIOException(iox);
         }
      }
   public boolean areFieldsEqual(LineFilter<O,L> to_compareTo)  {

      //Light
      boolean b = getSubCount() == to_compareTo.getSubCount()  &&
         isEndRequired() == to_compareTo.isEndRequired();

      if(!b)  {
         return  b;
      }

      //Moderate
      b = getAlterStart().equals(to_compareTo.getAlterStart())  &&
         getAlterEnd().equals(to_compareTo.getAlterEnd())  &&
         getAlterLine().equals(to_compareTo.getAlterLine());

      if(!b)  {
         return  b;
      }

      //Heavy
      Iterator<String> itrNm = to_compareTo.subModeNameSet.iterator();
      while(itrNm.hasNext())  {
         String sNm = itrNm.next();
         LineFilter<O,L> blm = to_compareTo.getSub(sNm);

         if(!containsSub(sNm))  {
            return  false;
         }

         LineFilter<O,L> blmSubThis = getSub(sNm);

         if(!blmSubThis.equals(blm))  {
            return  false;
         }
      }
      return  true;
   }
   /**
      @return  {@link #getName() getName}{@code ()}
    **/
   public String getKey()  {
      return  getName();
   }
/*
   public ValueCopier<String> getKeyCopier()  {
      return  nmdBsc.getKeyCopier();
   }
 */
   /**
      <P>Get a duplicate of this <CODE>LineFilter</CODE>.</P>

      @return  <CODE>(new {@link #LineFilter(LineFilter) LineFilter}&lt;O,L&gt;(this))</CODE>
    **/
   public LineFilter<O,L> getObjectCopy()  {
      return  (new LineFilter<O,L>(this));
   }
   public StringBuilder appendRules(StringBuilder to_appendTo)  {
      return  appendRules(to_appendTo, true);
   }
      private StringBuilder appendRules(StringBuilder to_appendTo, boolean do_useInfoPost)  {
         to_appendTo.append("areBlockMarksInclusive()=").append(areBlockMarksInclusive()).append(", isEndRequired()=").append(isEndRequired());

         if(do_useInfoPost)  {
            to_appendTo.append(". For sub-mode information, use appendToString().");
         }

         return  to_appendTo;
      }
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      try  {
         to_appendTo.append(this.getClass().getName()).append(": ").append("Rules=<<<");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }

      appendRules(to_appendTo, false).append(">>>").append(LINE_SEP).
         append("doKeepBlockActive()=" + doKeepBlockActive() + ", doKeepLineActive()=" + doKeepLineActive() + ", doKeepInactive()=" + doKeepInactive());

      if(!getName().equals(DUMMY_NAME_FOR_ROOT_MODE))  {
         to_appendTo.append("name=\"").append(getName()).append("\", ");
      }
      to_appendTo.append("level=").append(getSubLevel()).append(", ");

      if(!isActive())  {
         to_appendTo.append("inactive");
      }  else if(isBlockActive())  {
         to_appendTo.append("block active: [start line num=").append(getActiveBlockStartLineNum()).
            append(isBlockStartLine() ? ", this-line-is-start" : "").
            append(isBlockEndLine() ? ", this-line-is-end" : "").append("]");

      }  else  {
         to_appendTo.append("line active");
      }

      if(getSubCount() > 0)  {
         if(getActiveSub() != null)  {
            to_appendTo.append(", active-sub=[" + getActiveSub().getName() + " (lvl" + getActiveSub().getSubLevel() + "), deepest=" + getActiveDeepest().getName() + " (" + getActiveDeepest().getSubLevel() + ")]");
         }  else  {
            to_appendTo.append(", no-active-subs");
         }
         to_appendTo.append(", All sub names=" + Arrays.toString(subModeNameSet.toArray()));
      }  else  {
         to_appendTo.append(", getSubCount()=0");
      }

      to_appendTo.append(LINE_SEP).append(" > getAlterStart()=");
         getAlterStart().appendToString(to_appendTo).append(LINE_SEP).
            append(" >> getAlterEnd()=");
      getAlterEnd().appendToString(to_appendTo).append(LINE_SEP).
         append(" >>> getAlterLine()=");
      getAlterLine().appendToString(to_appendTo).append(LINE_SEP);

      return  to_appendTo;
   }

   /**
      <P>Set debugging for use in block and line alterers, in <I>{@code this}</I> and all the sub-mode.</P>

      <P>This calls<OL>
         <LI><CODE>{@link #getAlterStart() getAlterStart}().{@link com.github.xbn.io.Debuggable#setDebug(Appendable, boolean) setDebug}(destination, is_on)</CODE></LI>
         <LI><CODE>{@link #getAlterEnd() getAlterEnd}().setDebug(destination, is_on)</CODE></LI>
         <LI><CODE>{@link #getAlterLine() getAlterLine}().setDebug(destination, is_on)</CODE></LI>
      </OL></P>
    **/
   public void setDebugAllAltersThisModeOnly(Appendable destination, boolean is_on)  {
      getAlterStart().setDebug(destination, is_on);
      getAlterEnd().setDebug(destination, is_on);
      getAlterLine().setDebug(destination, is_on);
   }
   /**
      <P>Turn debugging on or off.</P>

      <P>This calls<OL>
         <LI><CODE>{@link com.github.xbn.analyze.AbstractAnalyzer super}.{@link com.github.xbn.analyze.AbstractAnalyzer#setDebugOn(boolean) setDebugOn}(destination, is_on)</CODE></LI>
         <LI>And, for every {@link #getSub(String) sub-mode}: {@code setDebugOn(is_on)}</LI>
      </OL></P>
    **/
   public void setDebugOn(boolean is_on)  {
      super.setDebugOn(is_on);
      Iterator<LineFilter<O,L>> itrSub = iterator();
      while(itrSub.hasNext())  {
         itrSub.next().setDebugOn(is_on);
      }
   }
/*
   public static final <O,L extends LineObject<O>> RuleType getRuleTypeFromFields(LineFilter_Fieldable<O,L> fieldable)  {
      RuleType ertStart = null;
      RuleType ertEnd = null;
      RuleType ertLine = null;
      try  {
         ertStart = fieldable.getAlterStart().getCondition().getRuleType();
         ertEnd = fieldable.getAlterEnd().getCondition().getRuleType();
         ertLine = fieldable.getAlterLine().getCondition().getRuleType();
      }  catch(RuntimeException rx)  {
         Objects.requireNonNull(fieldable, "fieldable");
         Objects.requireNonNull(fieldable.getAlterStart(), "fieldable.getAlterStart()");
         Objects.requireNonNull(fieldable.getAlterEnd(), "fieldable.getAlterEnd()");
         throw  CrashIfObject.nullOrReturnCause(fieldable.getAlterLine(), "fieldable.getAlterLine()", null, rx);
      }

      int iM = EnumUtil.getHasCount(RuleType.IMPOSSIBLE, ertStart, ertEnd, ertLine);
      int iU = EnumUtil.getHasCount(RuleType.UNRESTRICTED, ertStart, ertEnd, ertLine);

      return  ((iM == 3) ? RuleType.IMPOSSIBLE
         :  ((iU == 3) ? RuleType.UNRESTRICTED : RuleType.RESTRICTED));
   }
 */
}
class ItrAllSubs<O,L extends LineObject<O>> extends AbstractIterator<LineFilter<O,L>>  {
   private final LineFilter<O,L> blmParent;
   private final Iterator<String>   itrNm;
   public ItrAllSubs(LineFilter<O,L> blm_parent, Set<String> st_subNames)  {
      blmParent = blm_parent;
      itrNm = st_subNames.iterator();
   }
   public boolean hasNext()  {
      return  itrNm.hasNext();
   }
   public LineFilter<O,L> next()  {
      try  {
         return  blmParent.getSub(itrNm.next());
      }  catch(RuntimeException rx)  {
         IteratorUtil.crashIfNoSuchElement(itrNm);
         throw  rx;
      }
   }
}
class ItrAllLines<O,L extends LineObject<O>> extends AbstractIterator<L> implements LineObjectIterator<O,L>  {
   private final LineFilter<O,L> mode;
   private final Iterator<O> lineItr;
   private final TextAppenter dbgEachLnAptr;
   private L nextLnObj;
   private int lineNum;
   private final VzblPadChop VPC_DBG = NewVzblPadChopFor.trimEscChopWithDDD(true, null, 50);
   public ItrAllLines(LineFilter<O,L> mode, Iterator<O> line_itr, Appendable dbgEachLine_ifNonNull)  {
      this.mode = mode;
      lineItr = line_itr;
      lineNum = mode.getLineNumber();
      nextLnObj = null;
      dbgEachLnAptr = NewTextAppenterFor.appendableUnusableIfNull(dbgEachLine_ifNonNull);
      hasNext();
   }
   public boolean hasNext()  {
      if(dbgEachLnAptr.isUseable()) { dbgEachLnAptr.appentln("<LF>  hasNext()..."); }

      if(nextLnObj != null)  {
         if(dbgEachLnAptr.isUseable()) { dbgEachLnAptr.appentln("<LF>    Next line is ready for retrieval. Use next(): [" + nextLnObj + "]"); }
         return  true;
      }
      while(lineItr.hasNext())  {
         O line = lineItr.next();
         O actvLn = mode.getActiveLine(++lineNum, line);

         if(dbgEachLnAptr.isUseable()) {
            dbgEachLnAptr.appentln("<LF>     [line " + lineNum + "]: block-active=" + mode.isBlockActive() + " (" + (mode.doKeepBlockActive() ? "KEEP" : "DISCARD") + "), line-active=" + mode.isLineActive() + " (" + (mode.doKeepLineActive() ? "KEEP" : "DISCARD") + "), inactive:" + (mode.doKeepInactive() ? "KEEP" : "DISCARD") + " line-text=\"" + VPC_DBG.get(line) + "\"");
         }

         if(mode.isActive())  {
            assert  (actvLn != null) : "mode.isActive() is true but mode.getActiveLine(line) returned null: " + mode;

            if((mode.isBlockActive()  &&  mode.doKeepBlockActive())  ||
                  (mode.isLineActive()  &&  mode.doKeepLineActive()))  {
               @SuppressWarnings("unchecked")
               L lo = (L)(new SimpleLineObject<O>(lineNum, actvLn));
               nextLnObj = lo;
               if(dbgEachLnAptr.isUseable()) { dbgEachLnAptr.appentln("<LF>     Block- or line-active, and DO keep it. Returning true"); }
               return  true;
            }

         }  else if(mode.doKeepInactive())  {
            assert  (actvLn == null) : "mode.isActive() is false but mode.getActiveLine(line) returned non-null: " + mode;

            @SuppressWarnings("unchecked")
            L lo = (L)(new SimpleLineObject<O>(lineNum, line));
            nextLnObj = lo;

            if(dbgEachLnAptr.isUseable()) { dbgEachLnAptr.appentln("<LF>     Inactive, and DO keep inactive. Returning true"); }

            return  true;
         }

      }
      if(dbgEachLnAptr.isUseable()) { dbgEachLnAptr.appentln("<LF>    No more lines in line_itr. Returning false."); }
      return  false;
   }
   public L next()  {
      crashIfNoNext();
      L lineObj = nextLnObj;
      nextLnObj = null;
      return  lineObj;
   }
}
