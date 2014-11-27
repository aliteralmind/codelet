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
	<P>Search term for methods.</P>
	<A NAME="xmpl_method"></A><H3>{@link com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm}: Example</H3>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.simplesig.MethodSigSearchTermXmpl%()}

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class MethodSigSearchTerm extends SimpleParamSigSearchTerm  {
	/**
		<P>Create a new instance.</P>

		<H4>Examples</H4>

<BLOCKQUOTE><PRE>doSomething()</PRE></BLOCKQUOTE>

		<P>A function named {@code "doSomething"} with exactly zero parameters.</P>

<BLOCKQUOTE><PRE>doSomething(*)</PRE></BLOCKQUOTE>

		<P>One or more parameters of any type.</P>

<BLOCKQUOTE><PRE>doSomething(String, *)</PRE></BLOCKQUOTE>

		<P>A {@code java.lang.String}, followed by one or more parameters of any type.</P>

<BLOCKQUOTE><PRE>doSomething(String, *, int...)</PRE></BLOCKQUOTE>

		<P>A {@code java.lang.String} and {@code int}-ellipsis, with one or more parameters of any type between.</P>

		<P>This first calls
		<BR> &nbsp; &nbsp; <CODE>{@link SimpleParamSigSearchTerm#SimpleParamSigSearchTerm(String, Appendable, Appendable) super}(search_termSig)</CODE></P>

		@param  search_termSig  The function name may not be empty.
	 **/
	public MethodSigSearchTerm(String search_termSig, Appendable debugBasics_ifNonNull, Appendable dbgDoesMatch_ifNonNull)  {
		super(search_termSig, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull);
		if(getMethodName().length() == 0)  {
			throw  new SimpleParamSigSearchTermFormatException(search_termSig, "Method shortcut has no method name: \"" + search_termSig + "\"");
		}
	}
	/**
		<P>Does any match?.</P>

		@param  all_methods  May not be {@code null}.
		@return  If
		<BR> &nbsp; &nbsp; <CODE>all_methods.{@link AllSimpleParamSignatures#getMethodMap() getMethodMap}().get({@link SimpleParamSigSearchTerm#getMethodName() getMethodName}())</CODE>
		<BR>returns a non-null list, this returns <CODE>{@link SimpleParamSigSearchTerm#doesMatchAnyProt(List) doesMatchAnyProt}(<I>[the-list]</I>)*</CODE>
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
		<P>Does exactly one match?.</P>

		@param  all_methods  May not be {@code null}.
		@return  If
		<BR> &nbsp; &nbsp; <CODE>all_methods.{@link AllSimpleParamSignatures#getMethodMap() getMethodMap}().get({@link SimpleParamSigSearchTerm#getMethodName() getMethodName}())</CODE>
		<BR>returns a non-null list, this returns <CODE>{@link SimpleParamSigSearchTerm#doesMatchOnlyOneProt(List) doesMatchOnlyOneProt}(<I>[the-list]</I>)*</CODE>
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
		<P>Get a new list of all matches.</P>

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
		<P>Get the first and only match.</P>

		@return  <CODE>{@link #getFirstMatch(AllSimpleParamSignatures, CrashIfZero, CrashIfMoreThanOne) getFirstMatch}(all_methods, {@link CrashIfZero}.{@link CrashIfZero#YES YES}, {@link CrashIfMoreThanOne CrashIfMoreThanOne}.{@link CrashIfMoreThanOne#YES YES})</CODE>
		@see  #doesMatchOnlyOne(AllSimpleParamSignatures) doesMatchOnlyOne
		@see  #getFirstMatch(AllSimpleParamSignatures, CrashIfZero, CrashIfMoreThanOne) getFirstMatch
		@see  #getAllMatches(AllSimpleParamSignatures, CrashIfZero) getOnlyMatch
	 **/
	public MethodSimpleParamSig getOnlyMatch(AllSimpleParamSignatures all_methods)  {
		return  getFirstMatch(all_methods, CrashIfZero.YES, CrashIfMoreThanOne.YES);
	}
	/**
		<P>Get the first match.</P>

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
		<P>Get the single matching method from an all-signature object.</P>

		@return  <CODE>(new {@link #MethodSigSearchTerm(String, Appendable, Appendable) MethodSigSearchTerm}(sig_searchTerm, debugBasics_ifNonNull, dbgDoesMatch_ifNonNull)).
		<BR> &nbsp; &nbsp; {@link MethodSigSearchTerm#getOnlyMatch(AllSimpleParamSignatures) getOnlyMatch}(all_sigs).{@link MethodSimpleParamSig#getMethod() getMethod}()</CODE>
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
