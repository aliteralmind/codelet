package  com.github.aliteralmind.codelet.taglet;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.sun.javadoc.Tag;
   import  com.sun.tools.doclets.Taglet;
   import  java.util.Map;
/**
   <P>Inline JavaDoc taglet for displaying the source code of a class (usually example code). This tag can be used in any kind of {@link com.sun.javadoc.Doc}.</P>

   <P>The only custom code in this class is the {@link #NAME} field and {@link #toString(Tag) toString} function.</P>

   <P>Jamie Ho's <A HREF="http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/taglet/overview.html#inlineexample">UnderlineTaglet</A> was the template used to create this file.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>.
 **/
public class CodeletTaglet implements Taglet {
   /*
      To avoid configuration from being loaded repeatedly.
      See: http://stackoverflow.com/questions/23914909/how-to-prevent-configuration-file-from-repeatedly-reloading-holding-it-statical
    */
   private static ClassLoader clsLdr = null;
   /**
      <P>The name of this taglet, which appears immediately after the <CODE>&#123;&#64;</CODE>--Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.CodeletType CodeletType}.{@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE SOURCE_CODE}.{@link com.github.aliteralmind.codelet.CodeletType#getName() getName}()</CODE></P>
    **/
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
     * Register this Taglet.

     	<H3><I>Why is the map parameter type-erased? What generics does it need?</I></H3>

     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map tagletMap) {
       CodeletTaglet tag = new CodeletTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

    /**
      <P>Given the taglet input of a source-code file (as a fully-qualified class name), this returns the source code for that class, its lines potentially filtered and altered.</P>

     	@param tag The <code>Tag</code> representation of this custom tag.
      @return  <CODE>{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor CodletComSunJavadocTagProcessor}.{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor#get(Tag) get}(tag)</CODE>
     */
    public String toString(Tag tag) {
       if(clsLdr == null)  {
         /*
            To avoid configuration from being loaded repeatedly.
            See: http://stackoverflow.com/questions/23914909/how-to-prevent-configuration-file-from-repeatedly-reloading-holding-it-statical
          */
          clsLdr = this.getClass().getClassLoader();
       }
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
