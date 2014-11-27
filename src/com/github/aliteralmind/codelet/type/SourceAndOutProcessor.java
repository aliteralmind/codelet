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
	import  com.github.aliteralmind.codelet.CustomizationInstructions;
	import  com.github.aliteralmind.codelet.CodeletInstance;
	import  com.github.aliteralmind.codelet.CodeletType;
	import  com.github.aliteralmind.codelet.TagletOfTypeProcessor;
	import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
	import  java.nio.file.AccessDeniedException;
	import  java.nio.file.NoSuchFileException;
	import  java.util.Iterator;
	import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Reads a {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_AND_OUT {@.codelet.and.out}} taglet and outputs its replacement text.</P>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class SourceAndOutProcessor extends TagletOfTypeProcessor<SourceAndOutTemplate>  {
	/**
		<P>Create a new instance from an {@code CodeletInstance}.</P>

		<P>Equal to <CODE>{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#TagletOfTypeProcessor(CodeletType, CodeletInstance) super}(CodeletType.SOURCE_AND_OUT, instance)</CODE>
	 **/
	public SourceAndOutProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
		super(CodeletType.SOURCE_AND_OUT, instance);

		Iterator<String> srcLineItr = getSourceCodeLineIterator(instance);
		CustomizationInstructions<SourceAndOutTemplate> instructions =
			getCustomizationInstructions(CodeletType.SOURCE_CODE);
		String customizedSourceCode = instructions.getCustomizedBody(instance, srcLineItr);

		//Only one template for source and out together, and it must come from
		//the (potential) source-code customizer. This allows custom templates
		//to be set.
		SourceAndOutTemplate template = getTemplateFromInstructionsOverrideOrDefault(
			instructions);

		//Output
			String consoleOut = SimpleMethodSignature.getForMainFromStringSig(
					getExampleCodeStringSig(),
					getDebugApblIfOn(instance,
						"zzConsoleOutProcessor.consoleoutputfromexamplecodestringsig")).
				invokeGetMainOutputNoExtraParams("Executing example code");

			Iterator<String> consoleOutLineItr = StringUtil.getLineIterator(consoleOut);

			instructions = newInstructionsForDefaults(
				new CustomizationInstructions<SourceAndOutTemplate>(
						instance, CodeletType.CONSOLE_OUT));
			String outputCustomized = instructions.getCustomizedBody(instance, consoleOutLineItr);


		String finalOutput = template.
			fillSourceCodeBody(customizedSourceCode).
			fillConsoleOutputBody(outputCustomized).
			getRendered(instance);

		setFullyProcessedOutput(finalOutput);
	}
}