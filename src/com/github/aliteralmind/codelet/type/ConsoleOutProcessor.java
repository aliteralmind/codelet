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
package  com.github.aliteralmind.codelet.type;
   import  com.github.xbn.text.StringUtil;
   import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CustomizationInstructions;
   import  com.github.aliteralmind.codelet.TagletOfTypeProcessor;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Reads a {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} taglet and outputs its replacement text.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class ConsoleOutProcessor extends TagletOfTypeProcessor<ConsoleOutTemplate>  {
   /**
      <P>Create a new instance from an {@code CodeletInstance}.</P>

      <P>Equal to <CODE>{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#TagletOfTypeProcessor(CodeletType, CodeletInstance) super}(CodeletType.CONSOLE_OUT, instance)</CODE>


      <P>This<OL>
         <LI>Gets the {@linkplain #getExampleCodeStringSig() example code's string signature} from the instance.</LI>
         <LI>Gets the {@linkplain com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getForMainFromStringSig(Object, Appendable) simple signature} from that</LI>
         <LI>{@linkplain com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#invokeGetMainOutputNoExtraParams(String) Invokes and captures} the output from its <A HREF="http://docs.oracle.com/javase/tutorial/getStarted/application/index.html#MAIN">{@code main} function</A></LI>
         <LI>Returns a {@linkplain com.github.xbn.text.StringUtil#getLineIterator(Object) line iterator} for it.</LI>
      </OL></P>

    **/
   public ConsoleOutProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
      super(CodeletType.CONSOLE_OUT, instance);

      String output = SimpleMethodSignature.getForMainFromStringSig(
            getExampleCodeStringSig(),
            getDebugApblIfOn(null,
               "zzConsoleOutProcessor.consoleoutputfromexamplecodestringsig")).
         invokeGetMainOutputNoExtraParams("Executing example code");
      Iterator<String> sysDotOutLineItr = StringUtil.getLineIterator(output);

      CustomizationInstructions<ConsoleOutTemplate> instructions =
         getCustomizationInstructions(CodeletType.CONSOLE_OUT);

      String sysDotOutCustomized = instructions.getCustomizedBody(instance, sysDotOutLineItr);

      ConsoleOutTemplate template = getTemplateFromInstructionsOverrideOrDefault(
         instructions);

      String finalOutput = template.fillBody(sysDotOutCustomized).getRendered(instance);
      setFullyProcessedOutput(finalOutput);
   }
}
