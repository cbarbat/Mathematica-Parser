#Mathematica (Wolfram Language) Parser

This is an open source *Pratt parser* written in *Java* that can parse *Mathematica* and/or
the *Wolfram Language* syntax.

##![Documentation][doc-image] Documentation

This work is heavily based on an effort by Patrick Scheibe, who wrote an *IntelliJ IDEA Plugin*
for the *Mathematica* language. I was very impressed by Patrick's parser and decided not only
to fork it but to also make a stand-alone parser which doesn't depend on *IDEA* code anymore.

My parser differs however significantly from Patrick's parser. I hope to have improved some parselets, e.g.:

- **DerivativeParselet**

- **ListParselet**

- **SymbolParselet**

- **TagSetParselet**

- **PrefixBlankParselet**

- **PrefixBlankSequenceParselet**

- **PrefixBlankNullSequenceParselet**

I also deleted the three infix blank/blank sequence/blank null sequence expression parselets
as they are not needed anymore.

If you want to play with this project, I would suggest using *IntelliJ IDEA Community Edition*.
This is because I set this up as an *IDEA* project, which you can try to use.

In the directory src you find a Main.java file, which you can call when compilation is finished.
You can give filenames as arguments on the command line when calling Main.
These files must contain valid *Mathematica* expressions.
They will be parsed and the program prints a sort of abstract syntax tree or full form of the
expressions or error messages if there are any problems.

You can use this ***Pratt parser*** as basis for other developments.

##![Development][dev-image] Development [![Build Status](https://travis-ci.org/cbarbat/Mathematica-Parser.svg?branch=develop)](https://travis-ci.org/cbarbat/Mathematica-Parser)

### Code

Use *IntelliJ IDEA Community Edition*. This project is setup as a Maven project and uses the
Maven JFlex plugin.

If you want to crawl through the code, you can use the
[develop branch](https://github.com/cbarbat/Mathematica-Parser/tree/develop). It has the
newest version of the code.

### To do

Here is some food for thought:

- the two **Span** parselets are missing/commented out and must still be implemented

- add support for boxes syntax like \\!\\(1+2\\)

- make a lot of JUnit tests to see where this parser is identical to *Mathematica* and where it differs


##![Contact][con-image] Credits, Contact and Licensing

###Credits

This project would not have been possible without the pioneering work of Patrick Scheibe:

- [halirutan](https://github.com/halirutan): provided his *IntelliJ IDEA Plugin* as open source.

###Contact

You can contact me by mail. My address can be seen by evaluating this in *Mathematica*:

    Uncompress["1:eJxTTMoPChZmYGBITszJzNNLSixKSixxKE9N0ktJBQByuwjB"]

###Licensing

The general license for this parser is the [MIT License](https://github.com/cbarbat/Mathematica-Parser/blob/develop/LICENSE).

Copyright © 2013 Patrick Scheibe & 2016 Calin Barbat

----

<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> and licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>

[doc-image]: http://i.stack.imgur.com/erf8e.png
[dev-image]: http://i.stack.imgur.com/D9G2G.png
[bug-image]: http://i.stack.imgur.com/K4fGd.png
[con-image]: http://i.stack.imgur.com/tCbmW.png
