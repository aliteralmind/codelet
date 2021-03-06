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
package  com.github.aliteralmind.codelet.taglet;
   import  com.github.aliteralmind.codelet.CodeletBootstrap;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.sun.javadoc.Tag;
   import  com.sun.tools.doclets.Taglet;
   import  java.util.Map;
/**
   <p>Inline JavaDoc taglet for displaying the source code of a class (usually example code). This tag can be used in any kind of {@link com.sun.javadoc.Doc}.</p>

   <p>The only custom code in this class is the {@link #NAME} field and {@link #toString(Tag) toString} function.</p>

   <p>Jamie Ho's <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/taglet/overview.html#inlineexample">UnderlineTaglet</a> was the template used to create this file.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>.
 **/
public class CodeletTaglet implements Taglet {
   private static final CodeletBootstrap BOOTSTRAP = CodeletBootstrap.INSTANCE;
   /*
      To avoid configuration from being loaded repeatedly.
      See: http://stackoverflow.com/questions/23914909/how-to-prevent-configuration-file-from-repeatedly-reloading-holding-it-statical
   private static ClassLoader clsLdr = null;
    */
   /**
      <p>The name of this taglet, which appears immediately after the <code>&#123;&#64;</code>--Equal to
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE SOURCE_CODE}.{@link com.github.aliteralmind.codelet.CodeletType#getName() getName}()</code></p>
    */
   public static final String NAME = CodeletType.SOURCE_CODE.getName();
    /**
     * Return the name of this custom tag.

     @return  #NAME
     */
    public String getName() {
        return NAME;
    }

    /**
     * @return true since this tag can be used in a field
     *         doc comment
     */
    public boolean inField() {
        return true;
    }

    /**
     * @return true since this tag can be used in a constructor
     *         doc comment
     */
    public boolean inConstructor() {
        return true;
    }

    /**
     * @return true since this tag can be used in a method
     *         doc comment
     */
    public boolean inMethod() {
        return true;
    }

    /**
     * @return true since this tag can be used in an overview
     *         doc comment
     */
    public boolean inOverview() {
        return true;
    }

    /**
     * @return true since this tag can be used in a package
     *         doc comment
     */
    public boolean inPackage() {
        return true;
    }

    /**
     * @return true since this
     */
    public boolean inType() {
        return false;
    }

    /**
     * Will return true since this is an inline tag.
     * @return true since this is an inline tag.
     */

    public boolean isInlineTag() {
        return true;
    }
   /**
      <p>Register this Taglet.</p>

    * <p>Equal to
      <br/> &nbsp; &nbsp; <code>{@link ComSunJavaDocUtil}.{@link ComSunJavaDocUtil#registerNewTagletInstance(Taglet, Map) registerNewTagletInstance}(new {@link #CodeletTaglet() CodeletTaglet}(), taglet_map)</code></p>
    */
   @SuppressWarnings({"unchecked", "rawtypes"})
   public static void register(Map taglet_map) {
      ComSunJavaDocUtil.registerNewTagletInstance(new CodeletTaglet(), taglet_map);
   }

    /**
      <p>Given the taglet input of a source-code file (as a fully-qualified class name), this returns the source code for that class, its lines potentially filtered and altered.</p>

     	@param tag The <code>Tag</code> representation of this custom tag.
    * @return  <code>{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor CodletComSunJavadocTagProcessor}.{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor#get(Tag) get}(tag)</code>
     */
    public String toString(Tag tag) {
         /*
       if(clsLdr == null)  {
            To avoid configuration from being loaded repeatedly.
            See: http://stackoverflow.com/questions/23914909/how-to-prevent-configuration-file-from-repeatedly-reloading-holding-it-statical
          clsLdr = this.getClass().getClassLoader();
       }
          */
       return  CodletComSunJavadocTagProcessor.get(tag);
    }

    /**
     * This method should not be called since arrays of inline tags do not
     * exist.  Method {@link #toString(Tag)} should be used to convert this
     * inline tag to a string.
     * @param tags the array of <code>Tag</code>s representing of this custom tag.
     */
    public String toString(Tag[] tags) {
        return null;
    }
}
