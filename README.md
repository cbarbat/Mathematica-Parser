# Mathematica-Parser
This is an open source parser for Mathematica and/or the Wolfram Language written in Java.

This work is heavily based on an effort by Patrick Scheibe, who wrote an IntelliJ IDEA Plugin for the Mathematica language.
I was very impressed by Patrick's work and decided not only to fork it but also make a stand-alone parser which doesn't depend on IDEA anymore.
This is not exactly true, because the Mathematica.flex file still has to be processed using the JFlex provided with IntelliJ IDEA.
Maybe someone has an idea how to make it work using the standard JFlex?

If you want to play with it, I would suggest using IntelliJ IDEA Community Edition and installing the plugins JFlex and Grammar-Kit.
This is because I set this up as an IDEA project, which you can try to import and use.

In the directory src you find a Main.java file, which you can call when compilation is finished.
You can give filenames as arguments on the command line when calling Main.
These files must contain valid Mathematica expressions. They will be parsed and the program prints error messages if there are any problems and if not, then it prints the types of the highest expressions in the file.

You can use this (recursive descent and Pratt) parser as basis for other developments.
