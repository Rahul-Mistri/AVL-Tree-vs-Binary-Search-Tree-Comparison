JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc


default:
	$(JAVAC) -d $(BINDIR) $(SRCDIR)/*.java $<

clean:
	rm $(BINDIR)/*.class
	rm -Rf doc

doc:
	javadoc  -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

runAVL:
	java -cp $(BINDIR) PowerAVLApp

runBST:
	java -cp $(BINDIR) PowerBSTApp
