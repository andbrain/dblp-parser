# Parser of DBLP dataset.
This program parses the DBLP dataset, extracting only title of each publication and output it line-by-line in a file.

## Beforehand

### Download
- dataset from https://dblp.uni-trier.de/faq/How+can+I+download+the+whole+dblp+dataset
- dataset release: dblp-2019-04-01.xml

## Run

### Compile java parser
> javac -cp mmdb-2019-04-29.jar DblpParser.java

### Execute
> java -Xmx8G -cp mmdb-2019-04-29.jar:. DblpParser dblp-2019-04-01.xml dblp-2017-08-29.dtd

## Result

The output will be a file "dblp.txt".
