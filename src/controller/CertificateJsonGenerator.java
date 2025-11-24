package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Certificate;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

/**
 * Utility class to generate JSON files for certificates.
 * Each certificate is saved in the "certificates" folder with its ID as filename.
 */
public class CertificateJsonGenerator {

    // Gson instance with LocalDate adapter registered
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    /**
     * Generates a JSON file for the given certificate.
     * @param certificate The certificate object to serialize.
     */
    public void generateJsonCertificate(Certificate certificate) {
        try {
            // Ensure certificates folder exists
            File folder = new File("certificates");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // File path: certificates/<certificateId>.json
            String filename = "certificates/" + certificate.getCertificateId() + ".json";
            try (FileWriter writer = new FileWriter(filename)) {
                gson.toJson(certificate, writer);
            }

            System.out.println("Certificate JSON generated: " + filename);

        } catch (Exception e) {
            System.err.println("Error generating certificate JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}