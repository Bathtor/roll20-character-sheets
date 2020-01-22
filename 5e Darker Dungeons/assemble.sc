#!/usr/bin/env amm

import sys.process._
import ammonite.ops._
import ammonite.ops.ImplicitWd._

val htmlFile: Path = pwd / "CharacterSheet.html";
val jsFile: Path = pwd / "SheetWorkers.js";
val pcFile: Path = pwd / "PCSheet.html";
val npcFile: Path = pwd / "NPCSheet.html";
val templatesFile: Path  = pwd / "RollTemplates.html";
val targetFile: Path = pwd / "5e Darker Dungeons.html";

rm! targetFile

val htmlData = read(htmlFile);
val jsData = read(jsFile);
val npcData = read(npcFile);
val pcData = read(pcFile);
val templatesData = read(templatesFile);
println("Read all data.");
val inserted = htmlData
.replaceAllLiterally("<!-- ###INSERT NPC SHEET### //-->", s"""
		$npcData
		""")
	.replaceAllLiterally("<!-- ###INSERT PC SHEET### //-->", s"""
		$pcData
		""")
	.replaceAllLiterally("<!-- ###INSERT ROLLTEMPLATES### //-->", s"""
		$templatesData
		""")
	.replaceAllLiterally("<!-- ###INSERT SCRIPT### //-->", s"""
		<script type="text/worker">
		$jsData
		</script>
		""");
write(targetFile, inserted);
println(s"Output written to $targetFile");
%%("/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl", targetFile)
