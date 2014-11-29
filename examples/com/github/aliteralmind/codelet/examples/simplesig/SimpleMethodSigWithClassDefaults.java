/*license*\
   XBN-Java: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.examples.simplesig;
	import  com.github.xbn.lang.reflect.InvokeMethodWithRtx;
	import  java.lang.reflect.Method;
	import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
/**
	<p>Demonstration of <code>com.github.aliteralmind.codelet.simplesig.{@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature}</code>, with no class name (or package name) in the string signature--potential default classes are directly provided.</p>

	<p>{@code java com.github.aliteralmind.codelet.examples.simplesig.SimpleMethodSigWithClassDefaults}</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://xbnjava.aliteralmind.com">{@code http://xbnjava.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/xbnjava">{@code https://github.com/aliteralmind/xbnjava}</a>
 **/
public class SimpleMethodSigWithClassDefaults  {
	public static final void main(String[] ignored)  {

		String strSig = "getStringForBoolInt(false, 3)";

		SimpleMethodSignature simpleSig = null;
		try  {
			simpleSig = SimpleMethodSignature.newFromStringAndDefaults(
				String.class, strSig, null,
				new Class[]{Object.class, SimpleMethodSigWithClassDefaults.class, SimpleMethodSignature.class},
				null);         //debug (on=System.out, off=null)
		}  catch(ClassNotFoundException cnfx)  {
			throw  new RuntimeException(cnfx);
		}

		Method m = null;
		try  {
			m = simpleSig.getMethod();
		}  catch(NoSuchMethodException nsmx)  {
			throw  new RuntimeException(nsmx);
		}

		m.setAccessible(true);

		Object returnValue = new InvokeMethodWithRtx(m).sstatic().
			parameters(simpleSig.getParamValueObjectList().toArray()).invokeGetReturnValue();

		System.out.println(returnValue);
	}
	public static final String getStringForBoolInt(Boolean b, Integer i)  {
		return  "b=" + b + ", i=" + i;
	}
}