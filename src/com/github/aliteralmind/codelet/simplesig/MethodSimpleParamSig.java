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
	import  com.github.xbn.lang.ObjectOrCrashIfNull;
	import  java.lang.reflect.Method;
/**
	<p>Simple-parameter-name signature for a function.</p>

	@see  <a href="package-summary.html#xmpl_method">Example</a>
	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class MethodSimpleParamSig extends SimpleParamNameSignature  {
	/**
		<p>Create a new instance from a method.</p>

		<p>Equal to
		<br/> &nbsp; &nbsp; <code>{@link SimpleParamNameSignature#SimpleParamNameSignature(Member, Class[]) super}(method, method.{@link java.lang.reflect.Method#getParameterTypes() getParameterTypes}())</code></p>

		@param  method  May not be {@code null}.
	 **/
	public MethodSimpleParamSig(Method method)  {
		super(method, ObjectOrCrashIfNull.<Method>get(method, "method").getParameterTypes());
	}
	/**
		<p>The method object.</p>

		@return  <code>(Method){@link SimpleParamNameSignature#getMember() getMember}()*</code>

		@see  #MethodSimpleParamSig(Method)
	 **/
	public Method getMethod()  {
		return  (Method)getMember();
	}
	/**
		@return  <code>{@link #getMethodName() getMethodName}() + {@link SimpleParamNameSignature#getWithParens() getWithParens}()*</code>
	 **/
	public String toString()  {
		return  getMethodName() + getWithParens();
	}
	/**
		@return  <code>{@link #getMethod() getMethod}().{@link java.lang.reflect.Method#getName() getName}()</code>
	 **/
	public String getMethodName()  {
		return  getMethod().getName();
	}
}
