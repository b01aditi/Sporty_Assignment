package framework;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

public final class ArtifactUtils {
    private ArtifactUtils() {}

    public static void cleanArtifactsDir() {
        Path artifacts = Paths.get(System.getProperty("user.dir"), Config.ARTIFACT_DIR).normalize();

        System.out.println("[ArtifactUtils] Cleaning artifacts folder: " + artifacts);

        try {
            if (Files.exists(artifacts)) {
                Files.walk(artifacts)
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                                System.out.println("[ArtifactUtils] Deleted: " + path);
                            } catch (IOException e) {
                                // Do NOT swallow—print so you know exactly why it's not deleting
                                System.out.println("[ArtifactUtils] FAILED to delete: " + path + " -> " + e.getMessage());
                            }
                        });
            }
            Files.createDirectories(artifacts);
        } catch (IOException e) {
            throw new RuntimeException("Failed cleaning artifacts dir: " + artifacts, e);
        }
    }
}
