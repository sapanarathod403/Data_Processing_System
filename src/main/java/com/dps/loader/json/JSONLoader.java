package com.dps.loader.json;

import com.dps.loader.DataLoader;
import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class JSONLoader implements DataLoader {
    @Override
    public List<RawRecord> load(File file) {

        // TODO: Implement Jackson parsing

        return List.of();
    }
}
