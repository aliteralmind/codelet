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
package  com.github.aliteralmind.codelet;
   import  com.github.aliteralmind.codelet.type.SourceAndOutProcessor;
   import  com.github.aliteralmind.codelet.type.FileTextProcessor;
   import  com.github.aliteralmind.codelet.type.ConsoleOutProcessor;
   import  com.github.aliteralmind.codelet.type.SourceCodeProcessor;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  com.github.xbn.lang.CrashIfObject;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Generates the output for a single Codelet of any type. This class--and this entire package--knows nothing of {@code com.sun}. This is the middleman between {@code com.sun} and {@link com.github.aliteralmind.codelet.TagletOfTypeProcessor}.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TagletProcessor  {
   private static final CodeletBootstrap bootstrap = TagletProcessor.initCodelet();
      private static final CodeletBootstrap initCodelet()  {
         try  {
            return  CodeletBootstrap.loadConfigGetInstance();
         }  catch(Exception x)  {
            throw  new IllegalStateException("Attempting to load Codelet configuration.", x);
         }
      }

   private final String fullyProcessed;
   /**
      <P>Create a new instance. Once constructed, the taglet's output may be obtained with {@link #get() get}{@code ()}.</P>

      <P>This<OL>
         <LI>If this is the first encountered codelet-taglet, this {@linkplain CodeletBootstrap loads all configuration}.</LI>
         <LI>If the enclosing file is {@linkplain CodeletBaseConfig#BLACK_WHITE_LIST_TYPE black-listed}, {@link #get() get}{@code ()} is set to <CODE>instance.{@link com.github.aliteralmind.codelet.CodeletInstance#getFullOriginalTaglet() getFullOriginalTaglet}()</CODE>, and this <I><B>exits</B></I>.</LI>
         <LI>Otherwise, this constructs a new {@linkplain TagletOfTypeProcessor processor}, based on the instance's {@linkplain CodeletInstance#getType() type}:<UL>
            <LI>{@link com.github.aliteralmind.codelet.type.SourceCodeProcessor#SourceCodeProcessor(CodeletInstance) type.SourceCodeProcessor}</LI>
            <LI>{@link com.github.aliteralmind.codelet.type.ConsoleOutProcessor#ConsoleOutProcessor(CodeletInstance) type.ConsoleOutProcessor}</LI>
            <LI>{@link com.github.aliteralmind.codelet.type.FileTextProcessor#FileTextProcessor(CodeletInstance) FileTextProcessor}</LI>
            <LI>{@link com.github.aliteralmind.codelet.type.SourceAndOutProcessor#SourceAndOutProcessor(CodeletInstance) SourceAndOutProcessor}</LI>
         </UL>and sets {@code get()} to its {@linkplain TagletOfTypeProcessor#getFullyCustomizedOutput() fully-customized text}.</LI>
      </OL></P>

      @param  instance  May not be {@code null}.
      @exception  IllegalStateException  If {@code instance} is of an unknown type (this is protection against a new type being added).
      @exception  ClassNotFoundException  Depending on the tag being used, and its format, if:<UL>
         <LI>The example class specified in the taglet does not exist (according to <CODE>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}</CODE>)</LI>
         <LI>The <I>explicitly specified</I> customizer class does not exist.</LI>
      </UL>
      @exception  NoSuchMethodException  If the customizer function does not exist, either in the explicitly specified or <A HREF="CustomizationInstructions.html#requirements">default classes</A>, or does not meet its requirements.
      @exception  NoSuchFileException  If the source-code or plain-text file does not exist.
      @exception  AccessDeniedException  If the file exists, but cannot be read.
    **/
   public TagletProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException, InterruptedException  {

      if(!bootstrap.wasLoaded())  {
         throw  new IllegalStateException("CodeletBootstrap.wasLoaded() is false.");
      }

      if(isDebugOn(instance, "zzTagletProcessor.codeletfound"))  {
         debugln("Codelet found: " + instance);
      }

      if(!getBlackWhiteList().doAccept(instance.getEnclosingFullyQualifiedName()))  {
         if(isDebugOn(instance, "zzTagletProcessor.codeletblacklisted"))  {
            debugln("   Codelet blacklisted: " + instance);
         }
         fullyProcessed = "<I>[CODELET-BLACKLISTED]-</I>" + instance.getFullOriginalTaglet();
         return;
      }

      TagletOfTypeProcessor processor = null;
      try  {
         if(instance.getType().isSourceCode())  {
            processor = new SourceCodeProcessor(instance);
         }  else if(instance.getType().isConsoleOut())  {
            processor = new ConsoleOutProcessor(instance);
         }  else if(instance.getType().isFileText())  {
            processor = new FileTextProcessor(instance);
         }  else if(instance.getType().isSourceAndOut())  {
            processor = new SourceAndOutProcessor(instance);
         }  else  {
            throw  new IllegalStateException("Unknown taglet type: " + instance.getType().getName());
         }
      }  catch(Exception x)  {
         throw  new RuntimeException("Attempting to process taglet: " + instance.toString(), x);
      }

      fullyProcessed = processor.getFullyCustomizedOutput();

      getDebugAptr().flushRtx();
   }
   /**
      <P>Get the taglet's already-rendered output text.</P>

      @return  <CODE><I>[the-{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor processor}]</I>.{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#getFullyCustomizedOutput() getFullyCustomizedOutput}()</CODE>
      @see  #TagletProcessor(CodeletInstance)
    **/
   public String get()  {
      return  fullyProcessed;
   }
}
