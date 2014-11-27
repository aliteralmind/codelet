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
package  com.github.aliteralmind.codelet.util;
/**
	<P>Should the offline file be refreshed from its online counterpart (or created if it doesn't exist)?.</P>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public enum RefreshOffline  {
	/**
		<P>YYY.</P>

		@see  #NO
		@see  #isYes()
	 **/
	YES,
	/**
		<P>YYY.</P>

		@see  #YES
		@see  #isNo()
	 **/
	NO;
	/**
		<P>Is this {@code RefreshOffline} equal to {@code YES}?.</P>

		@return  <CODE>this == {@link #YES}</CODE>

		@see  #isNo()
	 **/
	public final boolean isYes()  {
		return  this == YES;
	}
	/**
		<P>Is this {@code RefreshOffline} equal to {@code NO}?.</P>

		@return  <CODE>this == {@link #NO}</CODE>
		@see  #isYes()
	 **/
	public final boolean isNo()  {
		return  this == NO;
	}
	/**
		<P>Return {@code RefreshOffline.YES} if the flag is {@code true}, or {@code NO} if {@code false}.</P>

		@return  <CODE>(flag ? {@link #YES} : {@link #NO})</CODE>
	 **/
	public static final RefreshOffline getForBoolean(boolean flag)  {
		return  (flag ? YES : NO);
	}
};
