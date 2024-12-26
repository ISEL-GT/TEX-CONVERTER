<p align="center">
  <img src="https://www.isel.pt/sites/default/files/001_imagens_isel/Logotipos/logo_ISEL_principal_Branco.png" alt="ISEL Logo" width="70%">
</p>

## FILE TO TEX CONVERTER

### Summary
> This program takes any given source and converts it into a `.tex` compatible file, to be included in LaTEX projects.

---

### Usage
Firstly, download the [tex-converter.exe](https://github.com/ISEL-GT/TEX-CONVERTER/releases) file from the downloads page. Afterwards, run the following command (basic usage):

```
$ java -jar tex-converter-jar --source /path/to/source/directory --output /path/to/output/directory
```
This program will convert every file in the source directory, copying its relative location to the source and outputting a replica of the source directory structure with the converted files.

#### Options:

- `--source <path>`: Path to the source directory (defaults to the current working directory).
- `--output <path>`: Path to the output directory where the converted `.tex` files will be saved (defaults to `./out/`).
- `--filter <extension>`: Optionally specify a file extension to filter the files to convert (e.g., `--filter vhdl` to only convert `.vhdl` files).
- `--overleaf-path <path>`: Path where the LaTeX files will be included from in Overleaf (defaults to `vhdl/`).

The converter will then generate `.tex` files for each file in the source directory, and you will be able to include them in your LaTeX project.

**NOTE:** 
- The `--filter` option is used to specify which file extensions should be converted. If not provided, all file types will be considered.
- The `--overleaf-path` option helps adjust the inclusion path for Overleaf projects.

If not specified, the default values will be used:
- `--source` defaults to the current working directory.
- `--output` defaults to `./out/`.
- `--filter` defaults to no filter (all files are considered).
- `--overleaf-path` defaults to `vhdl/`.
- 
---

### Contacts

| Contributor        | Email                      |
|--------------------|----------------------------|
| Alexandre Silva    | alexandresilva.coding@gmail.com     |


