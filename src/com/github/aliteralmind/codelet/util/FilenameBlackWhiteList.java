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
   <p>For black- or white-listing file paths or fully-qualified class names, with overrides. This is based on <code>org.apache.commons.io.{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(String, String, IOCase)</code>, with the exception paths and all wildcard patterns must be non-{@code null} and non-empty.</p>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.util.BlackWhiteListForJavaClasses%eliminateCommentBlocksAndPackageDecl()}

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class FilenameBlackWhiteList  {
   private static final String[] EMPTY_STRING_ARRAY = {};
   private final boolean      isWhitelist       ;
   private final IOCase       caseSensitivity   ;
   private final List<String> propersImtblList  ;
   private final List<String> overridesImtblList;
   private final TextAppenter dbgAptr           ;
   /**
      <p>Create a new instance.</p>

    * @param  type  May not be {@code null}. Get with {@link #isWhitelist() isWhitelist}{@code ()}.
    * @param  case_sensitivity  May not be {@code null}. Get with {@link #getCaseSensitivity() getCaseSensitivity}{@code ()}.
    * @param  proper_items  The wildcard-match strings that define this black-or-white list. May not be {@code null} or empty, or contain {@code null} or duplicate elements.
    * @param  override_items  If non-{@code null} and non-empty, the wildcard-match strings that should trump any proper items. When non-{@code null}, may not contain any {@code null} or duplicate elements, and each item <i>should</i> be a valid subset of those items in {@code proper_items}.
    * @see  #FilenameBlackWhiteList(FilenameBlackWhiteList, BlackOrWhite, IOCase, Appendable)
    */
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
      <p>Create a new instance from the lists in an existing {@code FilenameBlackWhiteList}.</p>

    * @param  to_copyItemsFrom  May not be {@code null}.
    * @param  type  May not be {@code null}. Get with {@link #isWhitelist() isWhitelist}{@code ()}.
    * @param  case_sensitivity  May not be {@code null}. Get with {@link #getCaseSensitivity() getCaseSensitivity}{@code ()}.
    * @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable)
    */
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
      <p>Is this a whitelist?.</p>

    * @return  <ul>
         <li>{@code true}: {@linkplain #isMatchedByProper(String) Proper matching} paths, when not {@linkplain #isMatchedByOverride(String) overridden}, are {@linkplain #doAccept(String) accepted}</li>
         <li>{@code false}: Proper matching paths, when not overridden, are not accepted.</li>
      </ul>
    * @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable)
    */
   public boolean isWhitelist()  {
      return  isWhitelist;
   }
   /**
      <p>Is case ignored, required, or system-determined?.</p>

    * @return  <ul>
         <li><code>{@link org.apache.commons.io.IOCase}.{@link org.apache.commons.io.IOCase#INSENSITIVE INSENSITIVE}</code>: If case is ignored.</li>
         <li><code>IOCase.{@link org.apache.commons.io.IOCase#SENSITIVE SENSITIVE}</code>: If case is not ignored.</li>
         <li><code>IOCase.{@link org.apache.commons.io.IOCase#SYSTEM SYSTEM}</code>: If the underlying operation system determines case sensitivity.</li>
      </ul>
    * @see  #isMatchedByProper(String) isMatchedByProper(s)
    * @see  #isMatchedByOverride(String) isMatchedByOverride(s)
    * @see  org.apache.commons.io.FilenameUtils#wildcardMatchOnSystem(String, String)
    */
   public IOCase getCaseSensitivity()  {
      return  caseSensitivity;
   }
   /**
      <p>The unmodifiable list of wildcard items that, when matched--and not overridden--are either acceptable (white-listed) or unacceptable (black-listed).</p>

    * @see  #getImmutableOverrideList()
    * @see  #isMatchedByProper(String) isMatchedByProper(s)
    * @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList(bow,ioc,s[],s[])
    */
   public List<String> getImmutableProperList()  {
      return  propersImtblList;
   }
   /**
      <p>The unmodifiable list of wildcard items that, when matched, override the &quot;proper&quot; finding. This is ignored when no proper item is matched.</p>

    * @see  #getImmutableProperList()
    * @see  #isMatchedByOverride(String) isMatchedByOverride(s)
    * @see  #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList(bow,ioc,s[],s[])
    */
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
      <p>Is the path (or fully-qualified class name) accepted?.</p>

    * @param  path  May not be {@code null} or empty.
    * @return  <TABLE ALIGN="left" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
         <TD>{@code isMatchedByProper(path)}</TD>
         <TD>{@code isMatchedByOverride(path)}</TD>
         <TD>{@code isWhitelist()}</TD>
         <TD><b><u>Returned</u></b></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD><b>{@code false}</b></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD><b>{@code true}</b></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD>{@code true}</TD>
         <TD><b>{@code true}</b></TD>
      </TR><TR>
         <TD>{@code true}</TD>
         <TD>{@code false}</TD>
         <TD>{@code false}</TD>
         <TD><b>{@code false}</b></TD>
      </TR><TR>
         <TD>{@code false}</TD>
         <TD><i>n/a</i></TD>
         <TD>{@code true}</TD>
         <TD><b>{@code false}</b></TD>
      </TR><TR>
         <TD>{@code false}</TD>
         <TD><i>n/a</i></TD>
         <TD>{@code false}</TD>
         <TD><b>{@code true}</b></TD>
      </TR></TABLE>
    * @see  org.apache.commons.io.FilenameUtils
    */
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
      <p>Does the path match a main item?.</p>

      <p>For each item in the {@linkplain #getImmutableProperList() proper-list}, this calls</p>

<blockquote><pre>{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(path, <i>[proper-item]</i>, {@link #getCaseSensitivity() getCaseSensitivity}())</pre></blockquote>

      <p>If this returns {@code true} for any element, <i>this</i> function returns {@code true}. If it returns {@code false} for every element, <i>this</i> function returns {@code false}.</p>

    * @see  #doAccept(String)
    */
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
      <p>Does the path match an override item?. This should only be used after a proper item is matched.</p>

      <p>For each item in the {@linkplain #getImmutableOverrideList() override-list}, this calls</p>

<blockquote><pre>{@link org.apache.commons.io.FilenameUtils FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String, IOCase) wildcardMatch}(path, <i>[proper-item]</i>, {@link #getCaseSensitivity() getCaseSensitivity}())</pre></blockquote>

      <p>If this returns {@code true} for any element, <i>this</i> function returns {@code true}. If it returns {@code false} for every element, <i>this</i> function returns {@code false}.</p>

    * @see  #doAccept(String)
    */
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
    	@return  <code>true</code> If {@code to_compareTo} is non-{@code null}, a {@code FilenameBlackWhiteList}, and all its fields {@linkplain #areFieldsEqual(FilenameBlackWhiteList) are equal}. This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).
    */
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
      <p>Are <i>all</i> fields equal?.</p>

    * @param  to_compareTo  May not be {@code null}.
    */
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
      <p>Creates a new white-or-blacklist from a set of properties.</p>

      <p>All {@code "Name"} parameters must be the name of existing properties in {@code props}.</p>

    * @param  props  May not be {@code null}, and must contain properties named as in the {@code "*Name"} parameters.
    * @return  <code>{@link #newFromConfigStringVars(String, String, String, String, String, Appendable, Appendable) newFromConfigStringVars}(black_white_off, ignore_require_system, separator, separated_propers, separated_overrides, dbgLoading_ifNonNull, dbgAccept_ifNonNull)</code>
      <br/>Where all variables (except {@code separator}) are the values of the properties with an empty-string default. Such as
      <br/> &nbsp; &nbsp; <code>String ignore_require_system = props.getProperty(ignore_require_systemName, &quot;&quot;)</code>
    */
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
      <p>Creates a new white-or-blacklist from a set of string-variables as found in a configuration text file.</p>

    * @param  black_white_off  Is this a {@linkplain com.github.aliteralmind.codelet.util.BlackOrWhite#BLACK blacklist}, {@linkplain com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE whitelist}, or nothing? Must be {@code "black"}, {@code "white"}, or {@code "off"} (the case of this parameter's value is ignored). If {@code "off"}, this function <i><b>returns</b></i> a new {@code FilenameBlackWhiteList} that {@linkplain #newForAcceptAll(Appendable) accepts everything}.
    * @param  ignore_require_system  Should case be {@linkplain org.apache.commons.io.IOCase#INSENSITIVE ignored}, {@linkplain org.apache.commons.io.IOCase#SENSITIVE <i>not</i> ignored}, or determined by the operating {@linkplain org.apache.commons.io.IOCase#SYSTEM system}? Must be {@code "ignore"}, {@code "require"}, or {@code "system"} (the case of <i>this parameter's value</i> is ignored).
    * @param  separator  The character used to separate each proper and override value. Such as a comma ({@code ","}), {@linkplain com.github.xbn.lang.XbnConstants#LINE_SEP line-separator} ({@code "\r\n"}), or tab ({@code "\t"}). May not be {@code null}, empty, or contain any letters, digits, underscores ({@code '_'}), question-marks ({@code '?'}), or asterisks ({@code '*'}).
    * @param  separated_propers  The separated list of &quot;proper&quot; items. Must be separated by {@code separator}, and otherwise must conform to the restrictions for the {@link #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) proper_items} constructor parameter.
    * @param  separated_overrides  The separated list of override items. Must be non-{@code null} (if none, this must be the empty string: {@code ""}), separated by {@code separator}, and otherwise must conform to the restrictions for the {@code override_items} constructor parameter.
    * @see  #newFromProperties(Properties, String, String, String, String, String, Appendable, Appendable) newFromProperties
    */
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
      <p>A new instance that accepts everything.</p>

    * @return  <code>new {@link #FilenameBlackWhiteList(BlackOrWhite, IOCase, String[], String[], Appendable) FilenameBlackWhiteList}({@link com.github.aliteralmind.codelet.util.BlackOrWhite BlackOrWhite}.{@link com.github.aliteralmind.codelet.util.BlackOrWhite#WHITE WHITE}, {@link org.apache.commons.io.IOCase IOCase}.{@link org.apache.commons.io.IOCase#INSENSITIVE INSENSITIVE}, (new String[]{&quot;*&quot;}), (new String[]{}), dbgAccept_ifNonNull)</code>.

    */
   public static final FilenameBlackWhiteList newForAcceptAll(Appendable dbgAccept_ifNonNull)  {
      return  (new FilenameBlackWhiteList(BlackOrWhite.WHITE, IOCase.INSENSITIVE, (new String[]{"*"}), EMPTY_STRING_ARRAY, dbgAccept_ifNonNull));
   }
}
