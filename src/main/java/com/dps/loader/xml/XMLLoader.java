package com.dps.loader.xml;

import com.dps.loader.DataLoader;
import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class XMLLoader implements DataLoader {
    @Override
    public List<RawRecord> load(File file) {

        // TODO: Implement JAXB parsing

        return List.of();
    }
}
