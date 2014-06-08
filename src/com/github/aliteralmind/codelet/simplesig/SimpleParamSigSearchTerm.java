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
package  com.github.aliteralmind.codelet.simplesig;
   import  com.github.xbn.number.LengthInRange;
   import  com.github.xbn.number.NewLengthInRangeFor;
   import  com.github.xbn.array.ArrayUtil;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.reflect.RTNoSuchMethodException;
   import  com.github.xbn.util.JavaRegexes;
   import  com.google.common.base.Joiner;
   import  java.util.ArrayList;
   import  java.util.Arrays;
   import  java.util.Collections;
   import  java.util.List;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>For matching a simple-parameter-name signatures with wildcards ({@code '*'}), indicating one or more of any parameter type.</P>

   @see  <A HREF="http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/overview-summary.html#xmpl_links">Real world use in Codelet</A>
   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public abstract class SimpleParamSigSearchTerm  {
   private static final Matcher IDENTIFIER_MTCHR = Pattern.compile(JavaRegexes.IDENTIFIER + "(?:\\[\\]|\\.\\.\\.)?").matcher("");
   private final List<String> termList;
   private final boolean hasAStar;
   private final int idxFirstStar;
   private final int idxLastStar;
   private final String funcName;
   private final String withParams;
   private final String noParams;
   private TextAppenter dbgAptr;
   private TextAppenter dbgAptrDoesMatch;
   /**
      <P>Create a search term.</P>

      @param  search_termSig  May not be {@code null}, must contain parentheses with zero or more parameters, and may contain a function name before the open paren ({@code '('}). When provided, the function name must be a valid {@linkplain com.github.xbn.util.JavaRegexes#IDENTIFIER Java identifier}. Multiple {@linkplain #getTermList() parameters} are separated by commas, and each is either<UL>
         <LI>The {@linkplain java.lang.Class#getSimpleName() simple name} of the parameter's Java type, with optional array indicators ({@code "[]"}) or, in the last parameter only, ellipses ({@code "..."}) (examples: {@code "int"}, {@code "String"}, {@code "AClass"}), {@code "boolean[]"}, {@code "String..."}), or</LI>
         <LI>An {@linkplain #hasAStar() asterisk} ({@code '*'}), indicating one or more of any type. Two asterisks in a row are not permissible.</LI>
         </UL>The final character must be the close paren ({@code ')'}). Get with {@link #appendToString(StringBuilder) appendToString}.
    **/
   public SimpleParamSigSearchTerm(String search_termSig, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      int openParenIdx = -1;
      try  {
         openParenIdx = search_termSig.indexOf("(");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(search_termSig, "search_termSig", null, rx);
      }

      if(openParenIdx == -1)  {
         throw  new SimpleParamSigSearchTermFormatException(search_termSig, "No open parenthesis ('(').");
      }

      funcName = search_termSig.substring(0, openParenIdx);

      if(search_termSig.charAt(search_termSig.length() - 1) != ')')  {
         throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Last character not a close parenthesis: ')'.");
      }

      String commaList = search_termSig.substring((openParenIdx + 1), (search_termSig.length() - 1));
      dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(debugBasics_ifNonNull);
      dbgAptrDoesMatch = NewTextAppenterFor.appendableUnusableIfNull(dbgDoesMatch_ifNonNull);

      if(commaList.length() == 0)  {
         termList = Collections.unmodifiableList(Arrays.asList(new String[]{}));
         hasAStar = false;
         noParams = "";
         withParams = "()";
         idxFirstStar = -1;
         idxLastStar = -1;
         if(dbgAptr.isUseable())  {
            dbgAptr.appentln("SimpleParamSigSearchTerm: \"" + toString() + "\"");
         }
         return;
      }

      String[] params = commaList.split(",");
      boolean prevIsStar = false;
      boolean hasAStar2 = false;
      int lenMinus1 = params.length - 1;
      for(int i = 0; i < params.length; i++)  {
         String param = params[i].trim();
         if(param.length() == 0)  {
            throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Parameter " + i + " has no characters.");
         }
         if(param.equals("*"))  {
            hasAStar2 =  true;
            if(prevIsStar)  {
               throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Parameters " + (i - 1) + " and " + i + " are both '*'. Asterisks may not be next to each other.");
            }
            prevIsStar = true;
            param = null;
         }  else  {
            prevIsStar = false;
            if(!IDENTIFIER_MTCHR.reset(param).matches())  {
               throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Parameter " + i + " (\"" + param + "\") is not a legal Java identifier (with optional \"[]\" or \"...\" after it).");
            }
            if(param.endsWith("...")  &&  i < lenMinus1)  {
               throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Parameter " + i + " (\"" + param + "\") ends with \"...\", but is not the final parameter.");
            }
         }
         params[i] = param;
      }
      termList = Collections.unmodifiableList(Arrays.asList(params));
      hasAStar = hasAStar2;
      noParams = Joiner.on(", ").useForNull("*").join(termList);
      withParams = "(" + noParams + ")";

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("SimpleParamSigSearchTerm: \"" + toString() + "\"");
      }

      if(!hasAStar)  {
         idxFirstStar = -1;
         idxLastStar = -1;

      }  else  {
         int idxFirstStarTemp = -1;
         for(int i = 0; i < getTermList().size(); i++)  {
            if(getTermList().get(i) == null)  {
               idxFirstStarTemp = i;
               break;
            }
         }
         idxFirstStar = idxFirstStarTemp;

         int idxLastStarTemp = -1;
         for(int i = (getTermList().size() - 1); i >= 0; i--)  {
            if(getTermList().get(i) == null)  {
               idxLastStarTemp = i;
               break;
            }
         }
         idxLastStar = idxLastStarTemp;
      }

   }
   public void setDebug(Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(debugBasics_ifNonNull);
      dbgAptrDoesMatch = NewTextAppenterFor.appendableUnusableIfNull(dbgDoesMatch_ifNonNull);
   }
   public TextAppenter getDebugAptrBasics()  {
      return  dbgAptr;
   }
   public TextAppenter getDebugAptrDoesMatch()  {
      return  dbgAptrDoesMatch;
   }
   /**
      <P>The function name, or the empty string if a constructor.</P>
    **/
   public String getMethodName()  {
      return  funcName;
   }
   /**
      <P>Immutable list of parameter types (their simple names), where asterisks are replaced with {@code null}.</P>

      @see  #SimpleParamSigSearchTerm(String, Appendable, Appendable)
    **/
   public List<String> getTermList()  {
      return  termList;
   }
   /**
      <P>The original search-term parameter list.</P>

      @return  The original search-term list, surrounded by parentheses, with one space following each comma.
      @see  #getNoParams()
    **/
   public String getWithParams()  {
      return  withParams;
   }
   /**
      <P>The original search-term parameter list, excluding the parentheses.</P>

      @see  #getWithParams()
    **/
   public String getNoParams()  {
      return  noParams;
   }
   /**
      <P>Is there an asterisk anywhere in the parameter list?.</P>

      @see  #getTermList()
      @see  #SimpleParamSigSearchTerm(String, Appendable, Appendable)
    **/
   public boolean hasAStar()  {
      return  hasAStar;
   }
   /**
      <P>Does this search-term match an actually-existing constructor/method?.</P>

      @return  <CODE>({@link #getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne) getFirstMatchProt}(to_searchList, crashIf_zero, crashIf_moreThanOne) != null)</CODE>
   public boolean doesMatchAnyProt(List<? extends SimpleParamNameSignature> to_searchList, CrashIfZero crashIf_zero, CrashIfMoreThanOne crashIf_moreThanOne)  {
      return  (getFirstMatchProt(to_searchList, crashIf_zero, crashIf_moreThanOne) != null);
   }
    **/
   /**
      <P>Does any constructor/method match?.</P>

      @return  <CODE>({@link #getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne) getFirstMatchProt}(to_searchList, crashIf_zero, crashIf_moreThanOne) != null)</CODE>
      @see  #getAllMatchesProt(List, CrashIfZero)
    **/
   protected boolean doesMatchAnyProt(List<? extends SimpleParamNameSignature> to_searchList)  {
      return  (getFirstMatchProt(to_searchList, CrashIfZero.NO, CrashIfMoreThanOne.NO) != null);
   }
   /**
      <P>Does exactly one constructor/method match?.</P>

      @return  <CODE>(matchList != null  &&  matchList.size() == 1)</CODE>
      <BR>Where {@code matchList} is
      <BR> &nbsp; &nbsp; <CODE>{@link #getAllMatchesProt(List, CrashIfZero) getAllMatchesProt}(to_searchList, {@link CrashIfZero}.{@link CrashIfZero#NO NO})</CODE>
      @see  #getOnlyMatchProt(List)
    **/
   protected boolean doesMatchOnlyOneProt(List<? extends SimpleParamNameSignature> to_searchList)  {
      List<? extends SimpleParamNameSignature> matchList = getAllMatchesProt(
         to_searchList, CrashIfZero.NO);
      return  (matchList != null  &&  matchList.size() == 1);
   }
   /**
      <P>Get a new list of all matching constructors/methods.</P>

      @param  to_searchList  The actually-existing constructors/methods to search. May not be {@code null}.
      @param  crashIf_zero  If {@code com.github.xbn.lang.reflect.CrashIfZero#YES YES}, then zero matches is unacceptable. May not be {@code null}.
      @return  A new and mutable list containing all matching constructors/methods, or {@code null} if no messages match.
      @exception  RTNoSuchMethodException  If zero matches and {@code crashIf_zero} is {@code YES}.
      @see  #getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne)
    **/
   protected List<? extends SimpleParamNameSignature> getAllMatchesProt(List<? extends SimpleParamNameSignature> to_searchList, CrashIfZero crashIf_zero)  {
      List<SimpleParamNameSignature> matches = null;
      try  {                                                        //Divided by two
         matches = new ArrayList<SimpleParamNameSignature>(to_searchList.size() >> 2);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_searchList, "to_searchList", null, rx);
      }
      for(int i = 0; i < to_searchList.size(); i++)  {
         SimpleParamNameSignature actual = to_searchList.get(i);
         if(doesMatch(actual))  {
            if(dbgAptr.isUseable())  {
               dbgAptr.appentln(this + ": getAllMatchesProt: Match: " + actual);
            }
            matches.add(actual);
         }
      }
      if(crashIf_zero.isYes()  &&  matches.size() == 0)  {
         throw  new RTNoSuchMethodException("[search-term=\"" + toString() + "\", CrashIfZero.YES] " + to_searchList.size() + " methods/constructors in " + to_searchList.get(0).getMember().getDeclaringClass().getName() + " have the requested name, but none match:" + LINE_SEP + AllSimpleParamSignatures.toStringForAllListsInList(" - ", to_searchList, LINE_SEP));
      }
      return  matches;
   }
   /**
      <P>Get the first and only match.</P>

      @return  <CODE>{@link #getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne) getFirstMatchProt}(to_searchList, {@link CrashIfZero}.{@link CrashIfZero#YES YES}, {@link CrashIfMoreThanOne}.{@link CrashIfMoreThanOne#YES YES})</CODE>
      @see  #getAllMatchesProt(List, CrashIfZero) getAllMatchesProt
    **/
   protected SimpleParamNameSignature getOnlyMatchProt(List<? extends SimpleParamNameSignature> to_searchList)  {
      return  getFirstMatchProt(to_searchList, CrashIfZero.YES, CrashIfMoreThanOne.YES);
   }
   /**
      <P>Get the first matching constructor/method.</P>

      @param  to_searchList  The actually-existing constructor/methods to search. May not be {@code null}.
      @param  crashIf_zero  If {@code com.github.xbn.lang.reflect.CrashIfZero#YES YES}, then zero matches is unacceptable. May not be {@code null}.
      @param  crashIf_moreThanOne  If {@code com.github.xbn.lang.reflect.CrashIfMoreThanOne#YES YES}, then more than one match is unacceptable. May not be {@code null}.
      @return  The first matching param-list, or {@code null} if none match.
      @exception  RTNoSuchMethodException  If zero matches and {@code crashIf_zero} is {@code YES}, or more than one constructor/method matches and {@code crashIf_moreThanOne} is {@code YES}
      @see  #doesMatchAnyProt(List) doesMatchAnyProt
    **/
   protected SimpleParamNameSignature getFirstMatchProt(List<? extends SimpleParamNameSignature> to_searchList, CrashIfZero crashIf_zero, CrashIfMoreThanOne crashIf_moreThanOne)  {
      SimpleParamNameSignature firstMatch = null;
      try  {
         for(SimpleParamNameSignature sig : to_searchList)  {
            if(doesMatch(sig))  {
               if(dbgAptr.isUseable())  {
                  dbgAptr.appentln(this + ": doesMatch: Match: " + sig);
               }

               if(crashIf_moreThanOne.isNo())  {
                  return  sig;
               }

               if(firstMatch != null)  {
                  List<? extends SimpleParamNameSignature> matchList = getAllMatchesProt(to_searchList, crashIf_zero);
                  throw  new RTNoSuchMethodException("[shortcut=\"" + toString() + "\", CrashIfMoreThanOne.YES] " + matchList.size() + " methods/constructors in " + firstMatch.getMember().getDeclaringClass().getName() + " match:" + LINE_SEP + AllSimpleParamSignatures.toStringForAllListsInList(" - ", matchList, LINE_SEP));
               }
               firstMatch = sig;
            }
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_searchList, "to_searchList", null, rx);
      }

      maybeCrashIfNoneMatch((firstMatch == null), crashIf_zero, to_searchList);

      return  firstMatch;
   }
      private void maybeCrashIfNoneMatch(boolean true_ifZero, CrashIfZero crashIf_zero, List<? extends SimpleParamNameSignature> to_searchList)  {
         if(crashIf_zero.isYes()  &&  true_ifZero)  {
            throw  new RTNoSuchMethodException("[shortcut=\"" + toString() + "\", CrashIfZero.YES] " + to_searchList.size() + " methods/constructors in " + to_searchList.get(0).getMember().getDeclaringClass().getName() + " have the requested name, but none match:" + LINE_SEP + AllSimpleParamSignatures.toStringForAllListsInList(" - ", to_searchList, LINE_SEP));
         }
      }
   public int indexOfStar()  {
      return  idxFirstStar;
   }
   public int lastIndexOfStar()  {
      return  idxLastStar;
   }
   /**
      <P>Does a constructor/method match?.</P>

      <P><I>This function was a <B>beast</B>.</I></P>

      @param  actual_cnstrMthd  May not be {@code null}.
    **/
   public boolean doesMatch(SimpleParamNameSignature actual_cnstrMthd)  {

      List<String> requiredParams = getTermList();
      List<String> actualParams = actual_cnstrMthd.getParamNameList();
      Object[] rqdParams4DbgOnly = null;
      Object[] actlParams4DbgOnly = null;

      if(getDebugAptrDoesMatch().isUseable())  {
         getDebugAptrDoesMatch().appentln(this + ": doesMatch?");
         getDebugAptrDoesMatch().appentln(this + ":   - " + requiredParams.size() + " search-term params: " + toString());
         getDebugAptrDoesMatch().appentln(this + ":   - " + actualParams.size() + " actual params: " + actual_cnstrMthd);
      }

      try  {
         if(!actual_cnstrMthd.getMethodName().equals(getMethodName()))  {
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":    FALSE: Method names different");
            }
            return  false;
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(actual_cnstrMthd, "actual_cnstrMthd", null, rx);
      }

      if(getDebugAptrDoesMatch().isUseable())  {
         rqdParams4DbgOnly = requiredParams.toArray();
         for(int i = 0; i < rqdParams4DbgOnly.length; i++)  {
            if(rqdParams4DbgOnly[i] == null)  {
               rqdParams4DbgOnly[i] = "*";
            }
         }
         actlParams4DbgOnly = actualParams.toArray();
         getDebugAptrDoesMatch().appentln(this + ":    Method names equal. Analyzing parameters:");
      }

      int rqdIdx = 0;
      int actlIdx = 0;

      while(rqdIdx < requiredParams.size()  &&
             	actlIdx < actualParams.size())  {

         int actlParamsToLeft = actlIdx;
         int actlParamsRemaining = actualParams.size() - actlIdx;

         LengthInRange rqdParamIdxRangeToLeft = ((indexOfStar() != -1  &&
                  indexOfStar() < rqdIdx)
            ?  NewLengthInRangeFor.min(0, null)
            :  new LengthInRange(0, rqdIdx));

         int sizeMinusIdx = requiredParams.size() - rqdIdx;
         LengthInRange rqdParamIdxRangeAtAndToRight = ((lastIndexOfStar() != -1  &&
                  rqdIdx <= lastIndexOfStar())
            ?  NewLengthInRangeFor.min(rqdIdx, null)
            :  new LengthInRange(rqdIdx, requiredParams.size()));

         if(getDebugAptrDoesMatch().isUseable())  {
            getDebugAptrDoesMatch().appentln(this + ":       search-term( " + rqdParamIdxRangeToLeft + ", " + rqdParamIdxRangeAtAndToRight + " ): " +
               ArrayUtil.toStringHighlightElement(rqdParams4DbgOnly, "<<", rqdIdx, ">>"));
            getDebugAptrDoesMatch().appentln(this + ":          actual(" + actlParamsToLeft + ", " + actlParamsRemaining + "):   " +
               ArrayUtil.toStringHighlightElement(actlParams4DbgOnly, "<<", actlIdx, ">>"));
         }

         String rqdParam = requiredParams.get(rqdIdx);

         if(rqdParam == null)  {
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":       Advancing both: Parameter is asterisk ('*')");
            }
            rqdIdx++;
            actlIdx++;
            continue;
         }

         //Current search-term param is not an asterisk.

         int rqdSizeMinusIdx = requiredParams.size() - rqdIdx;
         LengthInRange paramSpotsRemainingInSearchTermRange = ((lastIndexOfStar() != -1  &&
                  lastIndexOfStar() > rqdIdx)  //Not ">=", because this param
                                               //is definitely not an asterisk
            ?  NewLengthInRangeFor.min(rqdSizeMinusIdx, null)
            :  NewLengthInRangeFor.exactly(rqdSizeMinusIdx, null));

         int origActlIdx = actlIdx;
         while(actlIdx < actualParams.size()  &&
               !paramSpotsRemainingInSearchTermRange.isValid(actualParams.size() - actlIdx))  {
            actlIdx++;
         }

         if(origActlIdx != actlIdx)  {
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":       Advancing actual: Remaining parameter spot-count exceeds search-term.");
            }
            continue;
         }

         String actlParam = actualParams.get(actlIdx);

         if(!rqdParam.equals(actlParam))  {
            if(indexOfStar() != -1  &&  indexOfStar() < rqdIdx)  {
               if(getDebugAptrDoesMatch().isUseable())  {
                  getDebugAptrDoesMatch().appentln(this + ":       Advancing actual: Params don't match, but an asterisk exists earlier in the search-term");
               }
               actlIdx++;
               continue;
            }
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":       FALSE: Params don't match, and no asterisk exists earlier in the search-term");
            }
            return  false;
         }

         if(getDebugAptrDoesMatch().isUseable())  {
            getDebugAptrDoesMatch().appentln(this + ":       Advancing both: Params match");
         }
         rqdIdx++;
         actlIdx++;
      }

      if(rqdIdx == requiredParams.size())  {
         if(actlIdx == actualParams.size())  {
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":       TRUE: Matched (all search-terms match all actual)");
            }
            return  true;
         }

         if(requiredParams.size() > 0  &&
               requiredParams.get(requiredParams.size() - 1) == null)  {
            if(getDebugAptrDoesMatch().isUseable())  {
               getDebugAptrDoesMatch().appentln(this + ":       TRUE: At least one actual param remains, but last search-term param is asterisk");
            }
            return  true;
         }

         if(getDebugAptrDoesMatch().isUseable())  {
            getDebugAptrDoesMatch().appentln(this + ":       FALSE: End of search-term reached, actual params remain");
         }
         return  false;
      }

      if(getDebugAptrDoesMatch().isUseable())  {
         getDebugAptrDoesMatch().appentln(this + ":       FALSE: End of actual signature reached, search-term params remain");
      }
      return  false;
   }
      private void debugHighlightParams(Object[] rqd_params, int rqd_idx, Object[] actl_parms, int actl_idx)  {
         if(getDebugAptrDoesMatch().isUseable())  {
            getDebugAptrDoesMatch().appentln(this + ":       search-term: " +
               ArrayUtil.toStringHighlightElement(rqd_params, "<<", rqd_idx, ">>"));
            getDebugAptrDoesMatch().appentln(this + ":          Actual:   " +
               ArrayUtil.toStringHighlightElement(actl_parms, "<<", actl_idx, ">>"));
         }
      }
   /**
      @return  <CODE>{@link #appendToString(StringBuilder) appendToString}(new StringBuilder()).toString()</CODE>
    **/
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   /**
      <P>The original shortcut as provided to the constructor, with a single space following each comma.</P>

      <P>This appends <CODE>{@link #getMethodName() getMethodName}() + {@link #getWithParams() getWithParams}()</CODE>.</P>

      @see  #toString()
      @see  #SimpleParamSigSearchTerm(String, Appendable, Appendable)
    **/
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      try  {
         return  to_appendTo.append(getMethodName()).append(getWithParams());
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }
   }
}
