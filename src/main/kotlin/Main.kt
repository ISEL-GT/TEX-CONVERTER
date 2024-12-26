package com.github.iselg1

import jdk.tools.jlink.internal.plugins.DefaultCompressPlugin.FILTER
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

// Sets the default argument values for the program
var OUTPUT_DIR = "./out"
var OVERLEAF_PATH = "LSDreport 2/vhdl"
var SOURCE_DIR_PATH = "C:\\dev\\Quartus\\SEM1.LSD.AV2.ARITHMETIC-LOGIC-UNIT-REMAPPED"

fun main(args: Array<String>) {

    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }

    // Clears out the output directory
    File(OUTPUT_DIR).deleteRecursively()

    for (file in File(SOURCE_DIR_PATH).walkTopDown()) {

        // Gets the relative path stemming from the source
        val relativePath = file.absolutePath.replace(SOURCE_DIR_PATH, "").replace("\\", "/")
        if (relativePath.isEmpty() || file.isDirectory || (file.extension != FILTER && FILTER.isNotEmpty())) continue

        // Gets the header for the file from the relative path
        val tidyRelativePath = if (relativePath.startsWith("/")) relativePath.substring(1) else relativePath
        val header = getHeaderFor(tidyRelativePath)
        val conversionFile = File("$OUTPUT_DIR$relativePath.tex")

        Files.createDirectories(Path(conversionFile.parent))
        conversionFile.createNewFile()

        // Writes the new data to the file
        try {
            conversionFile.appendText(header + "\n")
            conversionFile.appendText(file.readText() + "\n")
            conversionFile.appendText("\\end{lstlisting}\n" + "\\clearpage")
            println("\\include{$OVERLEAF_PATH${relativePath}.tex}")
        }
        catch (exception: IOException) {
            println("Could not access ${file.name}")
            conversionFile.delete()
        }

        copyAllResourcesToOutput()
    }
}

/**
 * Gets the header customised for the given file.
 * @param file The file we want to write a header for in the format FILENAME.EXTENSION
 * @return The header to write to the converted file
 */
fun getHeaderFor(file: String) : String{
    return "\\subsection{${file.replace("_", "\\_")}}\n" +
            "\\begin{lstlisting}"
}

/**
 * Copies every resource file into an output/include folder
 */
fun copyAllResourcesToOutput() {

    // Checks if the resources folder exists and gets its path
    val resourcesUrl = {}::class.java.classLoader.getResource("") ?: throw IllegalStateException("Resources not found")
    val resourcesPath = Paths.get(resourcesUrl.toURI()).toFile()

    // Checks if the output folder exists, and creates it if not
    val outputFolder = File(OUTPUT_DIR)
    outputFolder.deleteRecursively()
    Files.createDirectories(Path(OUTPUT_DIR))

    // Iterates over every file in the resources path and copies it into the output resources
    for (resourceFile in resourcesPath.walkTopDown()) {

        if (!resourceFile.isFile || resourceFile.extension != "tex") continue;
        Files.copy(resourceFile.toPath(), File(outputFolder, resourceFile.name).toPath())
    }
}