/*license*\
   Codelet

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

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
   import  com.github.xbn.array.CrashIfArray;
   import  com.github.xbn.array.Duplicates;
   import  com.github.xbn.array.NullContainer;
   import  com.github.xbn.array.NullElement;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.regexutil.IgnoreCase;
   import  com.github.xbn.text.CrashIfString;
   import  com.github.xbn.util.DefaultValueFor;
   import  com.github.xbn.util.EnumUtil;
   import  java.util.Arrays;
   import  java.util.Collections;
   import  java.util.List;
   import  java.util.Objects;
   import  java.util.Properties;
   import  java.util.regex.Pattern;
   import  org.apache.commons.io.FilenameUtils;
   import  org.apache.commons.io.IOCase;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>For black- or white-listing file paths or fully-qualified class names, with overrides. This is based on <CODE>org.apache.commons.io.{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(String, String, IOCase)</CODE>, with the exception paths and all wildcard patterns must be non-{@code null} and non-empty.</P>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.util.BlackWhiteListForJavaClasses:eliminateCmtBlocksPkgLineAndPkgReferences(true, true, false)}

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FilenameBlackWhiteList  {
   private static final String[] EMPTY_STRING_ARRAY = {};
   private final boolean      isWhitelist       ;
   private final IOCase       caseSensitivity   ;
   private final List<String> propersImtblList  ;
   private final List<String> overridesImtblList;
   private final TextAppenter dbgAptr           ;
   /**
      <P>Create a new instance.</P>

      @param  type  May not be {@code null}. Get with {@link #isWhitelist() isWhitelist}{@code ()}.
      @param  case_sensitivity  May not be {@code null}. Get with {@link #getCaseSensitivity() getCaseSensitivity}{@code ()}.
      @param  proper_items  The wildcard-match strings that define this black-or-white list. May not be {@code null} or empty, or contain {@code null} or duplicate elements.
      @param  override_items  If non-{@code null} and non-empty, the wildcard-match strings that should trump any proper items. When non-{@code null}, may not contain any {@code null} or duplicate elements, and each item <I>should</I> be a valid subset of those items in {@code proper_items}.
      @see  #FilenameBlackWhiteList(FilenameBlackWhiteList, BlackOrWhite, IOCase, Appendable)
    **/
   public FilenameBlackWhiteList(BlackOrWhite type, IOCase case_sensitivity, String[] proper_items, String[] override_items, Appendable dbgAccept_ifNonNull)  {
      Objects.requireNonNull(case_sensitivity, "case_sensitivity");
      try  {
         isWhitelist = type.isWhite();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(type, "type", null, rx);
      }
      CrashIfArray.bad(proper_items, "proper_items", NullContainer.BAD, 1, null, NullElement.BAD, 1, null, Duplicates.BAD);
      CrashIfArray.bad(override_items, "override_items", NullContainer.BAD, 0, null, NullElement.BAD, 1, null, Duplicates.BAD);
      caseSensitivity    = case_sensitivity;
      propersImtblList   = Collections.unmodifiableList(Arrays.asList(proper_items));

      if(override_items == null)  {
         override_items = EMPTY_STRING_ARRAY;
      }
      overridesImtblList = Collections.unmodifiableList(Arrays.asList(override_items));
      dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(dbgAccept_ifNonNull);
   }
   /**
      <P>Create a new instance from the lists in an existing {@code FilenameBlackWhiteList}.</P>

      @param  to_copyItemsFrom  May not be {@code null}.
      @param  type  May not be {@code null}. Get with {@link #isWhitelist() isWhitelist}{@code ()}.
      @param  case_sensitivity  May not be {@code null}. Get with {@link #getCaseSensitivity() getCaseSensitivity}{@code ()}.
      @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable)
    **/
   public FilenameBlackWhiteList(FilenameBlackWhiteList to_copyItemsFrom, BlackOrWhite type, IOCase case_sensitivity, Appendable dbgAccept_ifNonNull)  {
      Objects.requireNonNull(case_sensitivity, "case_sensitivity");
      try  {
         isWhitelist = type.isWhite();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(type, "type", null, rx);
      }
      caseSensitivity    = case_sensitivity;
      propersImtblList   = to_copyItemsFrom.propersImtblList;
      overridesImtblList = to_copyItemsFrom.overridesImtblList;
      dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(dbgAccept_ifNonNull);
   }
   /**
      <P>Is this a whitelist?.</P>

      @return  <UL>
         <LI>{@code true}: {@linkplain #isMatchedByProper(String) Proper matching} paths, when not {@linkplain #isMatchedByOverride(String) overridden}, are {@linkplain #doAccept(String) accepted}</LI>
         <LI>{@code false}: Proper matching paths, when not overridden, are not accepted.</LI>
      </UL>
      @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable)
    **/
   public boolean isWhitelist()  {
      return  isWhitelist;
   }
   /**
      <P>Is case ignored, required, or system-determined?.</P>

      @return  <UL>
         <LI><CODE>{@link org.apache.commons.io.IOCase}.{@link org.apache.commons.io.IOCase#INSENSITIVE INSENSITIVE}</CODE>: If case is ignored.</LI>
         <LI><CODE>IOCase.{@link org.apache.commons.io.IOCase#SENSITIVE SENSITIVE}</CODE>: If case is not ignored.</LI>
         <LI><CODE>IOCase.{@link org.apache.commons.io.IOCase#SYSTEM SYSTEM}</CODE>: If the underlying operation system determines case sensitivity.</LI>
      </UL>
      @see  #isMatchedByProper(String) isMatchedByProper(s)
      @see  #isMatchedByOverride(String) isMatchedByOverride(s)
      @see  org.apache.commons.io.FileNameUtils#wildcardMatchOnSystem(String, String)
    **/
   public IOCase getCaseSensitivity()  {
      return  caseSensitivity;
   }
   /**
      <P>The unmodifiable list of wildcard items that, when matched--and not overridden--are either acceptable (white-listed) or unacceptable (black-listed).</P>

      @see  #getImmutableOverrideList()
      @see  #isMatchedByProper(String) isMatchedByProper(s)
      @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList(bow,ioc,s[],s[])
    **/
   public List<String> getImmutableProperList()  {
      return  propersImtblList;
   }
   /**
      <P>The unmodifiable list of wildcard items that, when matched, override the &quot;proper&quot; finding. This is ignored when no proper item is matched.</P>

      @see  #getImmutableProperList()
      @see  #isMatchedByOverride(String) isMatchedByOverride(s)
      @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList(bow,ioc,s[],s[])
    **/
   public List<String> getImmutableOverrideList()  {
      return  overridesImtblList;
   }
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      try  {
         return  to_appendTo.append(BlackOrWhite.getBlackIfTrue(!isWhitelist())).
            append(" (").append(getCaseSensitivity()).append("), propers=").append(Arrays.toString(getImmutableProperList().toArray())).append(", overrides=").append(Arrays.toString(getImmutableOverrideList().toArray()));
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }
   }
   /**
      <P>Is the path (or fully-qualified class name) accepted?.</P>

      @param  path  May not be {@code null} or empty.
      @return  <TABLE ALIGN="left" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
         <TD>{@code isMatchedByProper(path)}</TD>
         <TD>{@code isMatchedByOverride(path)}</TD>
         <TD>{@code isWhitelist()}</TD>
         <TD><B><U>Returned</U></B></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD><B>{@code false}</B></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD><B>{@code true}</B></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD>{@code true}</TD>
         <TD><B>{@code true}</B></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD>{@code false}</TD>
         <TD><B>{@code false}</B></TD>
      </TR><TR>
         <TD>{@code false}</TD>
         <TD><I>n/a</I></TD>
         <TD>{@code true}</TD>
         <TD><B>{@code false}</B></TD>
      </TR><TR>
         <TD>{@code false}</TD>
         <TD><I>n/a</I></TD>
         <TD>{@code false}</TD>
         <TD><B>{@code true}</B></TD>
      </TR></TABLE>
      @see  org.apache.commons.io.FilenameUtils
    **/
   public boolean doAccept(String path)  {
      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("doAccept? path=\"" + path + "\"");
      }
      CrashIfString.nullEmpty(path, "path", null);

      boolean isMatchedByProper = isMatchedByProper(path);
      boolean isMatchedByOverride = isMatchedByOverride(path);

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("  - isMatchedByProper(path)=" + isMatchedByProper);
         dbgAptr.appentln("  - isMatchedByOverride(path)=" + isMatchedByOverride);
         dbgAptr.appentln("  - isWhitelist()=" + isWhitelist());
      }

      boolean doAccept = false;
      if(isMatchedByProper)  {
         doAccept = (isMatchedByOverride
            ?  !isWhitelist()
            :  isWhitelist());

      }  else  {
         doAccept = (!isWhitelist());
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("  - " + (doAccept ? "ACCEPTED" : "unacceptable"));
      }

      return  doAccept;
   }
   /**
      <P>Does the path match a main item?.</P>

      <P>For each item in the {@linkplain #getImmutableProperList() proper-list}, this calls</P>

<BLOCKQUOTE><PRE>{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(path, <I>[proper-item]</I>, {@link #getCaseSensitivity() getCaseSensitivity}())</PRE></BLOCKQUOTE>

      <P>If this returns {@code true} for any element, <I>this</I> function returns {@code true}. If it returns {@code false} for every element, <I>this</I> function returns {@code false}.</P>

      @see  #doAccept(String)
    **/
   public boolean isMatchedByProper(String path)  {
      return  isMatchedByProper(false, path);
   }
      private boolean isMatchedByProper(boolean doCrashIfPath_nullEmpty, String path)  {
         if(doCrashIfPath_nullEmpty)  {
            CrashIfString.nullEmpty(path, "path", null);
         }
         for(String proper : getImmutableProperList())  {
            if(FilenameUtils.wildcardMatch(path, proper, getCaseSensitivity()))  {
               return  true;
            }
         }
         return  false;
      }

   /**
      <P>Does the path match an override item?. This should only be used after a proper item is matched.</P>

      <P>For each item in the {@linkplain #getImmutableOverrideList() override-list}, this calls</P>

<BLOCKQUOTE><PRE>{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(path, <I>[proper-item]</I>, {@link #getCaseSensitivity() getCaseSensitivity}())</PRE></BLOCKQUOTE>

      <P>If this returns {@code true} for any element, <I>this</I> function returns {@code true}. If it returns {@code false} for every element, <I>this</I> function returns {@code false}.</P>

      @see  #doAccept(String)
    **/
   public boolean isMatchedByOverride(String path)  {
      return  isMatchedByOverride(false, path);
   }
      private boolean isMatchedByOverride(boolean doCrashIfPath_nullEmpty, String path)  {
         if(doCrashIfPath_nullEmpty)  {
            CrashIfString.nullEmpty(path, "path", null);
         }
         for(String override : getImmutableOverrideList())  {
            if(FilenameUtils.wildcardMatch(path, override, getCaseSensitivity()))  {
               return  true;
            }
         }
         return  false;
      }
   /**
    	@return  <CODE>true</CODE> If {@code to_compareTo} is non-{@code null}, a {@code FilenameBlackWhiteList}, and all its fields {@linkplain #areFieldsEqual(FilenameBlackWhiteList) are equal}. This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).
    **/
   @Override
   public boolean equals(Object to_compareTo)  {
      //Check for object equality first, since it's faster than instanceof.
      if(this == to_compareTo)  {
         return  true;
      }
      if(!(to_compareTo instanceof FilenameBlackWhiteList))  {
         //to_compareTo is either null or not an FilenameBlackWhiteList.
         //java.lang.Object.object(o):
         // "For any non-null reference value x, x.equals(null) should return false."
         //See the bottom of this class for a counter-argument (which I'm not going with).
         return  false;
      }

      //Safe to cast
      FilenameBlackWhiteList o = (FilenameBlackWhiteList)to_compareTo;

      //Finish with field-by-field comparison.
      return  areFieldsEqual(o);
   }
   /**
      <P>Are <I>all</I> fields equal?.</P>

      @param  to_compareTo  May not be {@code null}.
    **/
   public boolean areFieldsEqual(FilenameBlackWhiteList to_compareTo)  {
      try  {
         return  (to_compareTo.isWhitelist() == isWhitelist()  &&
            to_compareTo.getCaseSensitivity() == getCaseSensitivity()  &&
            to_compareTo.getImmutableProperList().equals(getImmutableProperList())  &&
            to_compareTo.getImmutableOverrideList().equals(getImmutableOverrideList()));
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_compareTo, "to_compareTo", null, rx);
      }
   }
   /**
      <P>Creates a new white-or-blacklist from a set of properties.</P>

      <P>All {@code "Name"} parameters must be the name of existing properties in {@code props}.</P>

      @param  props  May not be {@code null}, and must contain properties named as in the {@code "*Name"} parameters.
      @return  <CODE>{@link #newFromConfigStringVars(String, String, String, String, String, Appendable, Appendable) newFromConfigStringVars}(black_white_off, ignore_require_system, separator, separated_propers, separated_overrides, dbgLoading_ifNonNull, dbgAccept_ifNonNull)</CODE>
      <BR>Where all variables (except {@code separator}) are the values of the properties with an empty-string default. Such as
      <BR> &nbsp; &nbsp; <CODE>String ignore_require_system = props.getProperty(ignore_require_systemName, &quot;&quot;)</CODE>
    **/
   public static final FilenameBlackWhiteList newFromProperties(Properties props, String separator, String black_white_offName, String ignore_require_systemName, String separated_propersName, String separated_overridesName, Appendable dbgLoading_ifNonNull, Appendable dbgAccept_ifNonNull)  {
      TextAppenter dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(dbgLoading_ifNonNull);
      String black_white_off = null;
      try  {
         black_white_off = props.getProperty(black_white_offName, "");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(props, "props", null, rx);
      }
      String ignore_require_system = props.getProperty(ignore_require_systemName, "");
      String separated_propers = props.getProperty(separated_propersName, "");
      String separated_overrides = props.getProperty(separated_overridesName, "");

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("FilenameBlackWhiteList.newFromProperties:");
         dbgAptr.appentln(" - " + black_white_offName + "=" + black_white_off);
         dbgAptr.appentln(" - " + ignore_require_systemName + "=" + ignore_require_system);
         dbgAptr.appentln(" - " + separated_propersName + "=" + separated_propers);
         dbgAptr.appentln(" - " + separated_overridesName + "=" + separated_overrides);
      }

      try  {
         return  newFromConfigStringVars(black_white_off, ignore_require_system, separator, separated_propers, separated_overrides, dbgLoading_ifNonNull, dbgAccept_ifNonNull);
      }  catch(RuntimeException rx)  {
         throw  new RuntimeException("separator=\"" + separator + "\", " + black_white_offName + "=\"" + black_white_off + "\"" + LINE_SEP + " - " + ignore_require_systemName + "=\"" + ignore_require_system + "\"" + LINE_SEP + " - " + separated_propersName + "=\"" + separated_propers + "\"" + LINE_SEP + " - " + separated_overridesName + "=\"" + separated_overrides + "\"", rx);
      }
   }
   /**
      <P>Creates a new white-or-blacklist from a set of string-variables as found in a configuration text file.</P>

      @param  black_white_off  Is this a {@linkplain com.github.aliteralmind.codelet.util.BlackOrWhite#BLACK blacklist}, {@linkplain com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE whitelist}, or nothing? Must be {@code "black"}, {@code "white"}, or {@code "off"} (the case of this parameter's value is ignored). If {@code "off"}, this function <I><B>returns</B></I> a new {@code FilenameBlackWhiteList} that {@linkplain #newForAcceptAll(Appendable) accepts everything}.
      @param  ignore_require_system  Should case be {@linkplain org.apache.commons.io.IOCase#INSENSITIVE ignored}, {@linkplain org.apache.commons.io.IOCase#SENSITIVE <I>not</I> ignored}, or determined by the operating {@linkplain org.apache.commons.io.IOCase#SYSTEM system}? Must be {@code "ignore"}, {@code "require"}, or {@code "system"} (the case of <I>this parameter's value</I> is ignored).
      @param  separator  The character used to separate each proper and override value. Such as a comma ({@code ","}), {@linkplain com.github.xbn.lang.XbnConstants#LINE_SEP line-separator} ({@code "\r\n"}), or tab ({@code "\t"}). May not be {@code null}, empty, or contain any letters, digits, underscores ({@code '_'}), question-marks ({@code '?'}), or asterisks ({@code '*'}).
      @param  separated_propers  The separated list of &quot;proper&quot; items. Must be separated by {@code separator}, and otherwise must conform to the restrictions for the {@link #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) proper_items} constructor parameter.
      @param  separated_overrides  The separated list of override items. Must be non-{@code null} (if none, this must be the empty string: {@code ""}), separated by {@code separator}, and otherwise must conform to the restrictions for the {@code override_items} constructor parameter.
      @see  #newFromProperties(Properties, String, String, String, String, String, Appendable, Appendable) newFromProperties
    **/
   public static final FilenameBlackWhiteList newFromConfigStringVars(String black_white_off, String ignore_require_system, String separator, String separated_propers, String separated_overrides, Appendable dbgLoading_ifNonNull, Appendable dbgAccept_ifNonNull)  {
      TextAppenter dbgAptr = NewTextAppenterFor.appendableUnusableIfNull(dbgLoading_ifNonNull);

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln("FilenameBlackWhiteList newFromConfigStringVars:");
      }

      try  {
         if(black_white_off.toLowerCase().equals("off"))  {
            if(dbgAptr.isUseable())  {
               dbgAptr.appentln(" - newForAcceptAll(dbgAccept_ifNonNull). DONE");
            }
            return  newForAcceptAll(dbgAccept_ifNonNull);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(black_white_off, "black_white_off", null, rx);
      }

      BlackOrWhite bw = EnumUtil.toValueWithNullDefault(black_white_off, "black_white_off", IgnoreCase.YES, DefaultValueFor.NOTHING, BlackOrWhite.BLACK);

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln(" - BlackOrWhite." + bw);
      }

      IOCase ioc = null;
      try  {
         switch(ignore_require_system.toUpperCase())  {
            case  "IGNORE":  ioc = IOCase.INSENSITIVE;  break;
            case  "REQUIRE":  ioc = IOCase.SENSITIVE;  break;
            case  "SYSTEM":  ioc = IOCase.SYSTEM;  break;
            default:
               throw  new IllegalArgumentException("ignore_require_system.toUpperCase() (\"" + ignore_require_system.toUpperCase() + "\") does not equal \"IGNORE\", \"REQUIRE\", or \"SYSTEM\".");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(ignore_require_system, "ignore_require_system", null, rx);
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln(" - IOCase." + ioc);
      }

      CrashIfString.nullEmpty(separator, "separator", null);
      if(Pattern.compile("[?*\\w]").matcher(separator).find())  {
         throw  new IllegalArgumentException("separator (\"" + separator + "\") contains an illegal character.");
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln(" - separator valid: \"" + separator + "\"");
      }

      String[] propers = null;
      try  {
         propers = separated_propers.split(separator);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(separated_propers, "separated_propers", null, rx);
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln(" - Propers: " + Arrays.toString(propers));
      }

      String[] overrides = null;
      try  {
         if(separated_overrides.length() == 0)  {
            overrides = EMPTY_STRING_ARRAY;
         }  else  {
            overrides = separated_overrides.split(separator);
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(separated_overrides, "separated_overrides", null, rx);
      }

      if(dbgAptr.isUseable())  {
         dbgAptr.appentln(" - Overrides: " + Arrays.toString(overrides) + "...DONE");
      }

      return  new FilenameBlackWhiteList(bw, ioc, propers, overrides, dbgAccept_ifNonNull);
   }
   /**
      <P>A new instance that accepts everything.</P>

      @return  <CODE>new {@link #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList}({@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE WHITE}, {@link org.apache.commons.io.IOCase IOCase}.{@link org.apache.commons.io.IOCase#INSENSITIVE INSENSITIVE}, (new String[]{&quot;*&quot;}), (new String[]{}), dbgAccept_ifNonNull)</CODE>.

    **/
   public static final FilenameBlackWhiteList newForAcceptAll(Appendable dbgAccept_ifNonNull)  {
      return  (new FilenameBlackWhiteList(BlackOrWhite.WHITE, IOCase.INSENSITIVE, (new String[]{"*"}), EMPTY_STRING_ARRAY, dbgAccept_ifNonNull));
   }
}