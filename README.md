# Jeneral-Template-Resolver
It allows you to specify templates, with placeholder values, and allows for those placeholder values to be replaced with concrete values.

The template resolver will replace the placeholder with concrete values reading each line of the value file.

The value file has to have the same name of the placeholder. For example, if a placeholder is called \$myPlaceholder\$ then the value file has to be called myPlaceholder
Each value file line is relative placeholder concrete value.

For example if the template is:
\$p1\$ says hello!

and the value "p1" file has a single line:
Alice

the result will be: Alice says hello!

If more lines are provided in the p1 value file then it will iterate on all the different values and generate concrete result from the template

For example if the p1 has: Alice, Bob, Charlie

then the following result would be: Alice says hello!, Bob says hello!, Charlie says hello!

When more than one placeholder is provided in the template, more than one value file has to be provided.
This implies that the program will generate all the possible combinations of the values provided.

For example if the template is:
\$p1\$ says hello to \$p2\$

And the p1 value file is:
Alice Bob Eve

And the p2 value file is:
Charlie David

then the program will generate the following combinations:

Alice says hello to Charlie, Alice says hello to David, Bob says hello to Charlie, Bob says hello to David, Eve says hello to Charlie, Eve says hello to David

Templates can also support array values. A single value file line can have multiple variations for example

Name: \$p1[0]\$   Surname: \$p1[1]\$

and the value file is:
Alice|Axe,
Bob|Boggs

the final result will be:

Name: Alice  Surname: Axe 

Name: Bob  Surname: Boggs

## Usage
```shell script
java -jar Jeneral-Template-Resolver.jar -t template.txt -v valuesFile1,valuesFile2
```
