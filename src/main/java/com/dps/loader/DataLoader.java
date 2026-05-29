package com.dps.loader;

import com.dps.model.RawRecord;

import java.io.File;
import java.util.List;
public interface DataLoader {
    List<RawRecord> load(File file);
}
