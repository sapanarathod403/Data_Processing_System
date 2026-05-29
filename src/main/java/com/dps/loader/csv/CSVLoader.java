package com.dps.loader.csv;

import com.dps.loader.DataLoader;
import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CSVLoader implements DataLoader {
    @Override
    public List<RawRecord> load(File file) {

        // TODO: Implement OpenCSV parsing

        return List.of();
    }
}
