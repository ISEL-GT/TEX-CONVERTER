package com.github.iselgt

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path


fun main(args: Array<String>) {

    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val sourcePath = argsMap.getOrDefault("--source", ".")
    val outputDirectoryPath = argsMap.getOrDefault("--out", "./out/")
    val overleafPath = argsMap.getOrDefault("--overleaf-path", "vhdl/")
    val filter = argsMap.getOrDefault("--filter", "")

    // Clears out the output directory
    File(outputDirectoryPath).deleteRecursively()

    for (file in File(sourcePath).walkTopDown()) {

        // Gets the relative path stemming from the source
        val relativePath = file.absolutePath.replace(sourcePath, "").replace("\\", "/")
        if (relativePath.isEmpty() || file.isDirectory || (file.extension != filter && filter.isNotEmpty())) continue

        // Gets the header for the file from the relative path
        val tidyRelativePath = if (relativePath.startsWith("/")) relativePath.substring(1) else relativePath
        val header = getHeaderFor(tidyRelativePath)
        val conversionFile = File("$outputDirectoryPath$relativePath.tex")

        Files.createDirectories(Path(conversionFile.parent))
        conversionFile.createNewFile()

        // Writes the new data to the file
        try {
            conversionFile.appendText(header + "\n")
            conversionFile.appendText(file.readText() + "\n")
            conversionFile.appendText("\\end{lstlisting}\n" + "\\clearpage")
            println("\\include{$overleafPath${relativePath}.tex}")
        }
        catch (exception: IOException) {
            println("Could not access ${file.name}")
            conversionFile.delete()
        }
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