package  com.github.aliteralmind.codelet.taglet;
   import  com.sun.javadoc.Tag;
   import  com.sun.tools.doclets.Taglet;
   import  java.util.Map;

/**
   <P>Demo inline JavaDoc taglet from Sun's <A HREF="http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/taglet/overview.html#inlineexample">Doclet overview</A>. This is used as a template for all {@code com.github.aliteralmind.codelet.taglet} ttaglets.</P>

 	<P>A sample Inline Taglet representing {@code {@underline}}. This tag can be used in any kind of {@link com.sun.javadoc.Doc}. The text is underlined. For example, {@code {@underline UNDERLINE ME}} would be shown as: {@code <u>UNDERLINE ME</u>}.</P>
 *
 * @author Jamie Ho
 * @since 1.4
 */
public class UnderlineTaglet implements Taglet {

    private static final String NAME = "underline";

    /**
     * Return the name of this custom tag.
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
     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map tagletMap) {
       UnderlineTaglet tag = new UnderlineTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

    /**
     * Given the <code>Tag</code> representation of this custom
     * tag, return its string representation.
     * @param tag he <code>Tag</code> representation of this custom tag.
      @return  <CODE>{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor CodletComSunJavadocTagProcessor}.{@link com.github.aliteralmind.codelet.taglet.CodletComSunJavadocTagProcessor#get(Tag) get}(tag)</CODE>
     */
    public String toString(Tag tag) {
       return  "x=" + System.getProperty("x", ":(");
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
