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
/**
	<p>Should the constructor list be immutable?.</p>

	@see  AllSimpleParamSignatures#newConstructorList(Class, Declared, Sorted) AllSimpleParamSignatures#newConstructorList
	@author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <code><a href="http://codelet.jeffyepstein.com">http://codelet.jeffyepstein.com</a></code>, <code><a href="https://github.com/aliteramind/codelet">https://github.com/aliteramind/codelet</a></code>
 **/
public enum Immutable  {
	/**
		<p>YYY.</p>

		@see  #NO
		@see  #isYes()
	 **/
	YES,
	/**
		<p>YYY.</p>

		@see  #YES
		@see  #isNo()
	 **/
	NO;
	/**
		<p>Is this {@code Immutable} equal to {@code YES}?.</p>

		@return  <code>this == {@link #YES}</code>

		@see  #isNo()
	 **/
	public final boolean isYes()  {
		return  this == YES;
	}
	/**
		<p>Is this {@code Immutable} equal to {@code NO}?.</p>

		@return  <code>this == {@link #NO}</code>
		@see  #isYes()
	 **/
	public final boolean isNo()  {
		return  this == NO;
	}
	/**
		<p>Return {@code Immutable.YES} if the flag is {@code true}, or {@code NO} if {@code false}.</p>

		@return  <code>(flag ? {@link #YES} : {@link #NO})</code>
	 **/
	public static final Immutable getForBoolean(boolean flag)  {
		return  (flag ? YES : NO);
	}
};