package project.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AnimalFileManager {
    private final String DIR_NAME = "C:\\workspace\\ij-workspace\\file-dir";

    public String serverFile(MultipartFile multi) throws IOException {
        String serverFileName = createServerFileName(multi.getOriginalFilename());
        multi.transferTo(new File(getFullPath(serverFileName)));
        
        return serverFileName;
    }

    private String getFullPath(String serverFileName) {
        return DIR_NAME + serverFileName;
    }

    private String createServerFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + extractExt(originalFilename);
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
