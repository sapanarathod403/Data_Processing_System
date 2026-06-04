package com.dps;

import com.dps.config.ValidationConfiguration;
import com.dps.loader.csv.CSVLoader;
import com.dps.loader.json.JSONLoader;
import com.dps.loader.xml.XMLLoader;
import com.dps.model.RawRecord;
import com.dps.model.ValidationReport;
import com.dps.validation.ValidationEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.dps.loader.csv.CSVLoader;
import com.dps.loader.json.JSONLoader;
import com.dps.loader.xml.XMLLoader;
import com.dps.model.RawRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dps.config.ValidationConfiguration;
import com.dps.model.ValidationReport;
import com.dps.validation.ValidationEngine;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DpsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpsSystemApplication.class, args);
		testLoader();
	}
	private static void testLoader() {

		ValidationEngine<RawRecord> validationEngine =
				ValidationConfiguration.buildRawRecordValidator();

		Map<String, File> files = Map.of(
				"CSV", new File("src/main/resources/data/Input.csv"),
				"JSON", new File("src/main/resources/data/Input.json"),
				"XML", new File("src/main/resources/data/Input.xml")
		);

		for (Map.Entry<String, File> entry : files.entrySet()) {

			String fileType = entry.getKey();
			File file = entry.getValue();

			log.info("========== {} LOADER ==========", fileType);

			List<RawRecord> records;

			switch (fileType) {
				case "CSV":
					records = new CSVLoader().load(file);
					break;

				case "JSON":
					records = new JSONLoader().load(file);
					break;

				case "XML":
					records = new XMLLoader().load(file);
					break;

				default:
					throw new IllegalArgumentException(
							"Unsupported file type: " + fileType);
			}

			for (RawRecord record : records) {

				log.info("Loaded Record : {}", record);

				ValidationReport report =
						validationEngine.validate(
								String.valueOf(record.getOrderId()),
								record);

				if (report.getErrors().isEmpty()) {

					log.info(
							"Record {} passed validation",
							record.getOrderId());

				} else {

					log.warn(
							"Record {} failed validation. Errors={}, Warnings={}",
							report.getRecordId(),
							report.getTotalErrors(),
							report.getTotalWarnings());

					log.info(
							"ValidationReport {{ recordId={}, totalErrors={}, totalWarnings={} }}",
							report.getRecordId(),
							report.getTotalErrors(),
							report.getTotalWarnings());

					report.getErrors().forEach(error ->
							log.info(
									"  [{}] {} | field={} | {}",
									error.getSeverity(),
									error.getErrorCode(),
									error.getField(),
									error.getMessage()));
				}
			}
		}
	}
}
