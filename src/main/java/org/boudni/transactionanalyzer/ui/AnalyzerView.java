package org.boudni.transactionanalyzer.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Route("analyzer")
public class AnalyzerView extends VerticalLayout {
    public AnalyzerView(){
        setSizeFull();

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".csv");

        upload.addSucceededListener(event -> {
            try (InputStream inputStream = buffer.getInputStream();
                 Reader reader = new InputStreamReader(inputStream)) {

                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(reader);

                // TODO: CSVRecord → Java-Objekte umwandeln
                // TODO: Grid mit Daten füllen

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
