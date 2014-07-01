**Codelet**: Automated insertion of *already unit-tested* example code (its source code, console output, and input text-files) into JavaDoc using inline taglets--Codelet makes it possible to have *always accurate documentation*.

Scroll down for examples.

Full documentation is available at [`http://codelet.aliteralmind.com`](http://codelet.aliteralmind.com), including additional examples.

[*Installation instructions*](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/overview-summary.html#install)

---

#Codelet Taglet Summary#

###`{@.codelet}`###

Replaced with all source-code lines from an example code's Java file.

**Example:** `{@.codelet fully.qualified.examples.ExampleClass}`

###`{@.codelet.out}`###

Replaced with all source-code lines from an example code's Java file.

**Example:** `{@.codelet.out fully.qualified.examples.ExampleClass}`

###`{@.codelet.and.out}`###

Prints both source-code and its output.

**Example:** `{@.codelet.and.out fully.qualified.examples.ExampleClass}`

###`{@.file.textlet}`###

Replaced with all lines in a plain-text file, such as for displaying an example code's input.

**Example:** `{@.file.textlet fully\qualified\examples\doc-files\input.txt}`

---

#Examples#

  - **No customizations:** Display all source code without change. *(This is the example class used throughout this documentation)*
  - **Eliminate the package declaration and all multi-line comments**
  - Display only a portion of the lines in the source code: A **code snippet**
  - **Advanced customization**: Change function names to **clickable JavaDoc** links (this [advanced example](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/overview-summary.html#xmpl_links) is only available in the [Codelet overview](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/overview-summary.html#overview_description)

---

##Codelet: Example: *No customizer*##

This first example demonstrates a codelet with no customizer, displaying all lines, without change, followed by  its output.

*(This is the example class used throughout this documentation.)*

**The taglet:**

    {@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo}

**Output (between the horizontal rules):**

---
###Example###

    /*license*\
       Codelet: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

       This software is dual-licensed under the:
       - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
       - Apache Software License (ASL) version 2.0.

       Either license may be applied at your discretion. More information may be found at
       - http://en.wikipedia.org/wiki/Multi-licensing.

       The text of both licenses is available in the root directory of this project, under the names &quot;LICENSE_lgpl-3.0.txt&quot; and &quot;LICENSE_asl-2.0.txt&quot;. The latest copies may be downloaded at:
       - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
       - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
    \*license*/
    package  com.github.aliteralmind.codelet.examples.adder;
    /**
       <P>Demonstration of {@code com.github.aliteralmind.codelet.examples.adder.Adder}.</P>

       <P>{@code java com.github.aliteralmind.codelet.examples.AdderDemo}</P>

       @since  0.1.0
       @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF=&quot;http://codelet.aliteralmind.com&quot;>{@code http://codelet.aliteralmind.com}</A>, <A HREF=&quot;https://github.com/aliteralmind/codelet&quot;>{@code https://github.com/aliteralmind/codelet}</A>
     **/
    public class AdderDemo  {
       public static final void main(String[] ignored)  {

          Adder adder = new Adder();
          System.out.println(adder.getSum());

          adder = new Adder(5, -7, 20, 27);
          System.out.println(adder.getSum());
       }
    }

**Output:**

    0
    45

---

##Codelet: Example: *Hello Codelet!* A basic use##

This displays the above example class, but eliminates its package-declaration line and all multi-line comment blocks.

**The taglet:**

    {@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo%eliminateCommentBlocksAndPackageDecl()}

**Output (between the horizontal rules):**

---
###Example###

    public class AdderDemo  {
       public static final void main(String[] ignored)  {

          Adder adder = new Adder();
          System.out.println(adder.getSum());

          adder = new Adder(5, -7, 20, 27);
          System.out.println(adder.getSum());
       }
    }

**Output:**

    0
    45

---

This is a [simple, self-contained, compilable example](http://sscce.org), which your users can copy, paste, compile, and run.

The [customizer](./com/github/aliteralmind/codelet/CustomizationInstructions.html#overview) portion, which follows the [percent sign](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/com/github/aliteralmind/codelet/CodeletInstance.html#CUSTOMIZER_PREFIX_CHAR) (`'%'`)

    eliminateCommentBlocksAndPackageDecl()

[eliminates](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/com/github/aliteralmind/codelet/BasicCustomizers.html#eliminateCommentBlocksAndPackageDecl(com.github.aliteralmind.codelet.CodeletInstance, com.github.aliteralmind.codelet.CodeletType)) all multi-line comments, including the license block and all JavaDoc blocks, and the package declaration line.

(This uses the [default template](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/com/github/aliteralmind/codelet/CodeletBaseConfig.html#DEFAULT_AND_OUT_TMPL_PATH), which can be edited directly. A different template can be assigned to <!-- GENERIC PARAMETERS FAIL IN @link -->[a single taglet](./com/github/aliteralmind/codelet/CustomizationInstructions.html#template(T)) by creating a custom customizer. The [template overrides](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/com/github/aliteralmind/codelet/TemplateOverrides.html) configuration file allows a different template to assigned to all taglets in a single file or entire package.)

    {@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo%eliminateCommentBlocksAndPackageDecl()}

   is basically equivalent to all of the following:

     {@.codelet com.github.aliteralmind.codelet.examples.adder.AdderDemo%eliminateCommentBlocksAndPackageDecl()}
     {@.codelet.out com.github.aliteralmind.codelet.examples.adder.AdderDemo}</PRE>

   and

     {@.file.textlet examples\com\github\aliteralmind\codelet\examples\adder\AdderDemo.java%eliminateCommentBlocksAndPackageDecl()}
     {@.codelet.out com.github.aliteralmind.codelet.examples.adder.AdderDemo}</PRE>

*([View the  above.](http://aliteralmind.com/docs/computer/programming/codelet/documentation/javadoc/com/github/aliteralmind/codelet/examples/TrivialTestClassForFileTextletWithCustomizer.html))*

and

     {@.file.textlet C:\java_code\my_library\examples\com\github\aliteralmind\codelet\examples\adder\AdderDemo.java%eliminateCommentBlocksAndPackageDecl()}
     {@.codelet.out com.github.aliteralmind.codelet.examples.adder.AdderDemo}</PRE>

##Codelet: Example: *Displaying a &quot;code snippet&quot;*##

An alternative to a fully-working example is to display only a portion of the example code's source, using the <a href="com/github/aliteralmind/codelet/BasicCustomizers.html#lineRange(com.github.aliteralmind.codelet.CodeletInstance, com.github.aliteralmind.codelet.CodeletType, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.String)">`lineRange`</a> customizer:

**The taglet:**

    {@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo%lineRange(1, false, "Adder adder", 2, false, "println(adder.getSum())", "^ &nbsp; &nbsp; &nbsp;")}

**Output (between the horizontal rules):**

---
###Example###

    Adder adder = new Adder();
    System.out.println(adder.getSum());

    adder = new Adder(5, -7, 20, 27);
    System.out.println(adder.getSum());

**Output:**

    0
    45

---
It starts with (the line containing) `"Adder adder"`, and ends with the *second* `"println(adder.getSum())"`. This also eliminates the extra indentation, which in this case is six spaces.

The `false` parameters indicate the strings are literal. `true` treats them as [regular expressions](http://stackoverflow.com/questions/22937618/reference-what-does-this-regex-mean/22944075#22944075).
