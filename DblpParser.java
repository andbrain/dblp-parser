//
// Copyright (c)2015, dblp Team (University of Trier and
// Schloss Dagstuhl - Leibniz-Zentrum fuer Informatik GmbH)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// (1) Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//
// (2) Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// (3) Neither the name of the dblp team nor the names of its contributors
// may be used to endorse or promote products derived from this software
// without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL DBLP TEAM BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//

// Anderson notes:
// dataset downloaded from https://dblp.uni-trier.de/faq/How+can+I+download+the+whole+dblp+dataset
// schema -> https://dblp.uni-trier.de/faq/16154937.html
// saving only title of each publication
// dataset release: dblp-2019-04-01.xml
// dtd of xml schema: dblp-2017-08-29.dtd
// java classes: mmdb-2019-04-29.jar
//
// to execute this code:
// javac -cp mmdb-2019-04-29.jar DblpParser.java
// java -Xmx8G -cp mmdb-2019-04-29.jar:. DblpParser dblp-2019-04-01.xml dblp-2017-08-29.dtd

import java.io.IOException;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

import org.dblp.mmdb.Field;
import org.dblp.mmdb.Person;
import org.dblp.mmdb.PersonName;
import org.dblp.mmdb.Publication;
import org.dblp.mmdb.RecordDb;
import org.dblp.mmdb.RecordDbInterface;
import org.dblp.mmdb.TableOfContents;
import org.xml.sax.SAXException;


@SuppressWarnings("javadoc")
class DblpParser {

    public static void main(String[] args) {

        // we need to raise entityExpansionLimit because the dblp.xml has millions of entities
        System.setProperty("entityExpansionLimit", "10000000");

        if (args.length != 2) {
            System.err.format("Usage: java %s <dblp-xml-file> <dblp-dtd-file>\n", DblpParser.class.getName());
            System.exit(0);
        }
        String dblpXmlFilename = args[0];
        String dblpDtdFilename = args[1];

        System.out.println("building the dblp main memory DB ...");
        RecordDbInterface dblp;
        try {
            dblp = new RecordDb(dblpXmlFilename, dblpDtdFilename, false);
        }
        catch (final IOException ex) {
            System.err.println("cannot read dblp XML: " + ex.getMessage());
            return;
        }
        catch (final SAXException ex) {
            System.err.println("cannot parse XML: " + ex.getMessage());
            return;
        }
        System.out.format("MMDB ready: %d publs, %d pers\n\n", dblp.numberOfPublications(), dblp.numberOfPersons());

        Collection<Publication> pubs = dblp.getPublications();

        System.out.println("number of pubs: " + pubs.size());
        Collection<Field> f;
        try{
          FileWriter fw = new FileWriter("dblp.txt");
          for(Publication p: pubs){
            f = p.getFields("title");
            for(Field fi: f){
              // System.out.println("Title: " + fi.value());
              fw.write(fi.value() + "\n");
            }
          }
          fw.close();
        }
        catch (final IOException ex) {
          System.err.println("cannot write dblp.txt: " + ex.getMessage());
          return;
        }

        System.out.println("done.");
    }


}
