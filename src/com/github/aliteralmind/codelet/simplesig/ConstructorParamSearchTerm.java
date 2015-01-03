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
   import  java.lang.reflect.Constructor;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.reflect.RTNoSuchMethodException;
   import  java.util.List;
/**
   <p>For matching one or more {@code ConstructorSimpleParamSig}.</p>

   <A NAME="xmpl_cnstr"></a><h3>{@link com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm}: Example</h3>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.simplesig.ConstructorParamSearchTermXmpl%()}

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class ConstructorParamSearchTerm extends SimpleParamSigSearchTerm  {
   /**
      <p>Create a new instance.</p>

      <h4>Examples</h4>

<blockquote><pre>()</pre></blockquote>

      <p>A constructor with exactly zero parameters.</p>

<blockquote><pre>(*)</pre></blockquote>

      <p>One or more parameters of any type.</p>

<blockquote><pre>(String, *)</pre></blockquote>

      <p>A {@code java.lang.String}, followed by one or more parameters of any type.</p>

<blockquote><pre>(String, *, int[])</pre></blockquote>

      <p>A {@code java.lang.String} and {@code int} array, with one or more parameters of any type between.</p>

      <p>This first calls
      <br> &nbsp; &nbsp; <code>{@link SimpleParamSigSearchTerm#SimpleParamSigSearchTerm(String, Appendable, Appendable) super}(param_searchTerm)</code></p>

    * @param  param_searchTerm  There may not be any text before the open parenthesis ({@code '('}).
    */
   public ConstructorParamSearchTerm(String param_searchTerm, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      super(param_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull);
      if(getMethodName().length() != 0)  {
         throw  new SimpleParamSigSearchTermFormatException(param_searchTerm, "Constructor shortcut has text before the open-parenthesis ('('): \"" + param_searchTerm + "\"");
      }
   }
   /**
      <p>Does any constructor match?.</p>

    * @return  {@link com.github.aliteralmind.codelet.simplesig.SimpleParamSigSearchTerm#doesMatchAnyProt(List) doesMatchAnyProt}{@code (to_searchList)}*
    * @see  #getFirstMatch(List, CrashIfZero, CrashIfMoreThanOne)
    */
   public boolean doesMatchAny(List<ConstructorSimpleParamSig> to_searchList)  {
      return  doesMatchAnyProt(to_searchList);
   }
   /**
      <p>Get a new list of all matches.</p>

    * @return  {@link com.github.aliteralmind.codelet.simplesig.SimpleParamSigSearchTerm#getAllMatchesProt(List, CrashIfZero) getAllMatchesProt}{@code (to_searchList, crashIf_zero)}*
    * @see  #getFirstMatch(List, CrashIfZero, CrashIfMoreThanOne)
    */
   public List<ConstructorSimpleParamSig> getAllMatches(List<ConstructorSimpleParamSig> to_searchList, CrashIfZero crashIf_zero)  {
      @SuppressWarnings("unchecked")
      List<ConstructorSimpleParamSig> matches = (List<ConstructorSimpleParamSig>)getAllMatchesProt(to_searchList, crashIf_zero);
      return  matches;
   }
   /**
      <p>Get the first and only match.</p>

    * @return  <code>(ConstructorSimpleParamSig){@link SimpleParamSigSearchTerm#getOnlyMatchProt(List) getOnlyMatchProt}(to_searchList)</code>
    * @see  SimpleParamSigSearchTerm#doesMatchOnlyOneProt(List) doesMatchOnlyOneProt*
    * @see  #getFirstMatch(List, CrashIfZero, CrashIfMoreThanOne) getFirstMatch
    * @see  #getAllMatches(List, CrashIfZero) getAllMatches
    */
   public ConstructorSimpleParamSig getOnlyMatch(List<ConstructorSimpleParamSig> to_searchList)  {
      return  (ConstructorSimpleParamSig)getOnlyMatchProt(to_searchList);
   }
   /**
      <p>Get the first match.</p>

    * @return  <code>(ConstructorSimpleParamSig){@link SimpleParamSigSearchTerm#getFirstMatchProt(List, CrashIfZero, CrashIfMoreThanOne) getFirstMatchProt}(to_searchList, crashIf_zero, crashIf_moreThanOne)</code>
    * @see  #doesMatchAny(List)
    * @see  #getAllMatches(List, CrashIfZero)
    * @see  #getFirstMatch(List, CrashIfZero, CrashIfMoreThanOne)
    */
   public ConstructorSimpleParamSig getFirstMatch(List<ConstructorSimpleParamSig> to_searchList, CrashIfZero crashIf_zero, CrashIfMoreThanOne crashIf_moreThanOne)  {
      return  (ConstructorSimpleParamSig)getFirstMatchProt(to_searchList, crashIf_zero, crashIf_moreThanOne);
   }


   /**
      <p>Get the single matching constructor from an all-signature object.</p>

    * @return  <code>(new {@link ConstructorParamSearchTerm#ConstructorParamSearchTerm(String, Appendable, Appendable) ConstructorParamSearchTerm}(param_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull)).
      <br> &nbsp; &nbsp; {@link ConstructorParamSearchTerm#getOnlyMatch(List) getOnlyMatch}(all_sigs.{@link AllSimpleParamSignatures#getConstructorList() getConstructorList}).{@link ConstructorSimpleParamSig#getConstructor() getConstructor}()</code>
    */
   public static final Constructor<?> getConstructorFromAllSigsAndSearchTerm(AllSimpleParamSignatures all_sigs, String param_searchTerm, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
      try  {
         return  (new ConstructorParamSearchTerm(param_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull)).
            getOnlyMatch(all_sigs.getConstructorList()).getConstructor();
      }  catch(RTNoSuchMethodException nsmx)  {
         throw  new RTNoSuchMethodException("Attempting to get java.lang.reflect.Constructor object for JavaDoc link. target_class:" + all_sigs.getContainingClass().getName(), nsmx);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(all_sigs, "all_sigs", null, rx);
      }
   }
}
