
# Jeneral-Template-Resolver

![Version](https://img.shields.io/badge/version-1.1-blue) ![Status](https://img.shields.io/badge/status-active-brightgreen) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

The **Jeneral-Template-Resolver** allows you to specify templates with placeholder values and replace these placeholders with concrete values provided in dedicated files. This tool reads each line from a placeholder-specific file to generate personalized text outputs.

---

## Features
- Replace placeholder values in templates effortlessly.
- Supports multiple value files, iterating over each line to populate placeholders.
- Easily manage template variations by providing different value files.

---

## How It Works

1. **Define Your Template**  
   Specify placeholders within your template, such as `$myPlaceholder$`.

2. **Create a Value File**  
   For each placeholder, create a file with the same name as the placeholder (e.g., `myPlaceholder`), where each line represents a concrete value.

3. **Generate Output**  
   The resolver reads from these files and substitutes each placeholder, line-by-line.

---

## Example

### Single Placeholder

Suppose you have the following **template**:

```plaintext
$p1$ says hello!
```

And a **value file** named `p1` containing:

```plaintext
Alice
Bob
Charlie
```

#### Result
The output will be generated as:

```plaintext
Alice says hello!
Bob says hello!
Charlie says hello!
```
### Multiple Placeholders

If multiple placeholders exist, each placeholder will be replaced in an iterative manner based on its corresponding value file.

Suppose you have the following **template**:

```plaintext
$p1$ says hello to $p2$
```

And a **first value file** named `p1` containing:

```plaintext
Alice
Bob
Charlie
```

And a **second value file** named `p2` containing:

```plaintext
David
Edward
```

#### Result
The output will be generated as:

```plaintext
Alice says hello to David
Alice says hello to Edward
Bob says hello to David
Bob says hello to Edward
Charlie says hello to David
Charlie says hello to Edward
```

### Placeholder array values

For a single line of concrete values, you can specify an array of value variations. Each value is still defined in a single line but multiple variation in the single line are seperated by a " | " symbol

Suppose you have the following **template**:

```plaintext
$p1[0]$ and $p1[1]$
```

And a **value file** named `p1` containing:

```plaintext
My name is Alice|I would like to talk to Bob
I am an HTTP web server|I can't make coffee, I am a teapot
```

Notice the "|" pipe symbol splitting the variations of the same value line


#### Result
The output will be generated as:

```plaintext
My name is Alice and I love talking to Bob
I am an HTTP web server and I can't make coffee, I am a teapot
```

---

## File Naming and Placeholder Conventions

- Placeholders in the template must follow the syntax `$placeholderName$`.
- Each placeholder must have an associated file with the same name as the placeholder (without the `$` symbols).
  - **Example**: For `$greeting$`, the file should be named `greeting`.

---

## Requisites

To to run the program, you have to have Java 8 (1.8) or above installed
1. Visit [Oracle website](https://www.java.com/download/manual.jsp)
2. Download the correct version for your OS
3. Install Java
4. Verify that Java version is 8 (1.8) or above ([Guide](https://www.javatpoint.com/how-to-verify-java-version))

---

## Installation

To install **Jeneral-Template-Resolver**, download the latest binary from the [releases page](https://github.com/giovanni-grieco/Jeneral-Template-Resolver/releases).

1. Visit the [Releases](https://github.com/giovanni-grieco/Jeneral-Template-Resolver/releases) page.
2. Download the jar.

---

## Usage

Use the resolver by specifying the template file and the directory containing value files.

### Example Command

```bash
java -jar mod-config-generator.jar -t path/to/template_file -v path/to/value_file1,path/to/value_file2...
```

---

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Submit a pull request with a detailed description of your changes.

---

## License

This project is licensed under the GPL v3 License.
