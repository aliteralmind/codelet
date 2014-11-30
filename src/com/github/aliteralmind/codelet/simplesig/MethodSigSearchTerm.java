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
   import  java.lang.reflect.Method;
   import  com.github.xbn.lang.reflect.RTNoSuchMethodException;
   import  com.github.xbn.lang.CrashIfObject;
   import  java.util.List;
/**
   <p>Search term for methods.</p>
   <A NAME="xmpl_method"></a><h3>{@link com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm}: Example</h3>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.simplesig.MethodSigSearchTermXmpl%()}

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class MethodSigSearchTerm extends SimpleParamSigSearchTerm  {
   /**
      <p>Create a new instance.</p>

      <h4>Examples</h4>

<blockquote><pre>doSomething()</pre></blockquote>

      <p>A function named {@code "doSomething"} with exactly zero parameters.</p>

<blockquote><pre>doSomething(*)</pre></blockquote>

      <p>One or more parameters of any type.</p>

<blockquote><pre>doSomething(String, *)</pre></blockquote>

      <p>A {@code java.lang.String}, followed by one or more parameters of any type.</p>

<blockquote><pre>doSomething(String, *, int...)</pre></blockquote>

      <p>A {@code java.lang.String} and {@code int}-ellipsis, with one or more parameters of any type between.</p>

      <p>This first calls
      <br/> &nbsp; &nbsp; <code>{@link SimpleParamSigSearchTerm#SimpleParamSigSearchTerm(String, Appendable, Appendable) super}(search_termSig)</code></p>

      @param  search_termSig  The function name may not be empty.
    **/
   public MethodSigSearchTerm(String search_termSig, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      super(search_termSig, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull);
      if(getMethodName().length() == 0)  {
         throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Method shortcut has no method name: \"" + search_termSig + "\"");
      }
   }
   /**
      <p>Does any match?.</p>

      @param  all_methods  May not be {@code null}.
      @return  If
      <br/> &nbsp; &nbsp; <code>all_methods.{@link AllSimpleParamSignatures#getMethodMap() getMethodMap}().get({@link SimpleParamSigSearchTerm#getMethodName() getMethodName}())</code>
      <br/>returns a non-null list, this returns <code>{@link SimpleParamSigSearchTerm#doesMatchAnyProt(List) doesMatchAnyProt}(<i>[the-list]</i>)*</code>
      @see  #doesMatchOnlyOne(AllSimpleParamSignatures) doesMatchOnlyOne
    **/
   public boolean doesMatchAny(AllSimpleParamSignatures all_methods)  {
      List<MethodSimpleParamSig> methodsWithNameList = null;
      try  {
         methodsWithNameList = all_methods.getMethodMap().get(getMethodName());
         if(methodsWithNameList == null)  {
            return  false;
         }
         return  doesMatchAnyProt(methodsWithNameList);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_methods, "all_methods", null, rx);
      }
   }
   /**
      <p>Does exactly one match?.</p>

      @param  all_methods  May not be {@code null}.
      @return  If
      <br/> &nbsp; &nbsp; <code>all_methods.{@link AllSimpleParamSignatures#getMethodMap() getMethodMap}().get({@link SimpleParamSigSearchTerm#getMethodName() getMethodName}())</code>
      <br/>returns a non-null list, this returns <code>{@link SimpleParamSigSearchTerm#doesMatchOnlyOneProt(List) doesMatchOnlyOneProt}(<i>[the-list]</i>)*</code>
      @see  #doesMatchAny(AllSimpleParamSignatures) doesMatchAny
    **/
   public boolean doesMatchOnlyOne(AllSimpleParamSignatures all_methods)  {
      List<MethodSimpleParamSig> methodsWithNameList = null;
      try  {
         methodsWithNameList = all_methods.getMethodMap().get(getMethodName());
         if(methodsWithNameList == null)  {
            return  false;
         }
         return  doesMatchOnlyOneProt(methodsWithNameList);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_methods, "all_methods", null, rx);
      }
   }
   /**
      <p>Get a new list of all matches.</p>

      @param  all_methods  May not be {@code null}.
      @param  crashIf_zero  If {@code com.github.xbn.lang.reflect.CrashIfZero#YES YES}, then zero matches is unacceptable. May not be {@code null}.
      @return  {@linkplain SimpleParamSigSearchTerm#getAllMatchesProt(List, CrashIfZero) all matches}, or {@code null} if {@linkplain #doesMatchAny(AllSimpleParamSignatures) no matches}
      @exception  RTNoSuchMethodException  If zero matches and {@code crashIf_zero} is {@code YES}.
    **/
   public List<MethodSimpleParamSig> getAllMatches(AllSimpleParamSignatures all_methods, CrashIfZero crashIf_zero)  {
      List<MethodSimpleParamSig> methodsWithNameList = null;
      try  {
         methodsWithNameList = all_methods.getMethodMap().get(getMethodName());
         if(methodsWithNameList == null)  {
            if(crashIf_zero.isNo())  {
               return  null;
            }  else  {
               throw  new RTNoSuchMethodException("getMethodName()=\"" + getMethodName() + "\"");
            }
         }
         @SuppressWarnings("unchecked")
         List<MethodSimpleParamSig> matches = (List<MethodSimpleParamSig>)getAllMatchesProt(
            methodsWithNameList, crashIf_zero);
         return  matches;
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_methods, "all_methods", null, rx);
      }
   }
   /**
      <p>Get the first and only match.</p>

      @return  <code>{@link #getFirstMatch(AllSimpleParamSignatures, CrashIfZero, CrashIfMoreThanOne) getFirstMatch}(all_methods, {@link CrashIfZero}.{@link CrashIfZero#YES YES}, {@link CrashIfMoreThanOne CrashIfMoreThanOne}.{@link CrashIfMoreThanOne#YES YES})</code>
      @see  #doesMatchOnlyOne(AllSimpleParamSignatures) doesMatchOnlyOne
      @see  #getFirstMatch(AllSimpleParamSignatures, CrashIfZero, CrashIfMoreThanOne) getFirstMatch
      @see  #getAllMatches(AllSimpleParamSignatures, CrashIfZero) getOnlyMatch
    **/
   public MethodSimpleParamSig getOnlyMatch(AllSimpleParamSignatures all_methods)  {
      return  getFirstMatch(all_methods, CrashIfZero.YES, CrashIfMoreThanOne.YES);
   }
   /**
      <p>Get the first match.</p>

      @param  all_methods  May not be {@code null}.
      @param  crashIf_zero  If {@code com.github.xbn.lang.reflect.CrashIfZero#YES YES}, then zero matches is unacceptable. May not be {@code null}.
      @return  The {@linkplain SimpleParamSigSearchTerm#getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne) first match}, or {@code null} if no matches.
      @exception  RTNoSuchMethodException  If zero matches and {@code crashIf_zero} is {@code YES}.
      @see  #getOnlyMatch(AllSimpleParamSignatures) getOnlyMatch
    **/
   public MethodSimpleParamSig getFirstMatch(AllSimpleParamSignatures all_methods, CrashIfZero crashIf_zero, CrashIfMoreThanOne crashIf_moreThanOne)  {
      List<MethodSimpleParamSig> methodsWithNameList = null;
      try  {
         methodsWithNameList = all_methods.getMethodMap().get(getMethodName());
         if(methodsWithNameList == null)  {
            if(crashIf_zero.isNo())  {
               return  null;
            }  else  {
               throw  new RTNoSuchMethodException("getMethodName()=\"" + getMethodName() + "\"");
            }
         }
         return  (MethodSimpleParamSig)getFirstMatchProt(methodsWithNameList, crashIf_zero, crashIf_moreThanOne);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_methods, "all_methods", null, rx);
      }
   }
   /**
      <p>Get the single matching method from an all-signature object.</p>

      @return  <code>(new {@link #MethodSigSearchTerm(String, Appendable, Appendable) MethodSigSearchTerm}(sig_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull)).
      <br/> &nbsp; &nbsp; {@link MethodSigSearchTerm#getOnlyMatch(AllSimpleParamSignatures) getOnlyMatch}(all_sigs).{@link MethodSimpleParamSig#getMethod() getMethod}()</code>
    **/
   public static final Method getMethodFromAllSigsAndSearchTerm(AllSimpleParamSignatures all_sigs, String sig_searchTerm, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      try  {
         return  (new MethodSigSearchTerm(sig_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull)).
         getOnlyMatch(all_sigs).getMethod();
      }  catch(RTNoSuchMethodException nsmx)  {
         throw  new RTNoSuchMethodException("Attempting to get java.lang.reflect.Method object for JavaDoc link. target_class:" + all_sigs.getContainingClass().getName(), nsmx);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_sigs, "all_sigs", null, rx);
      }
   }
}
