package com.tinydoc.service.impl;

import com.tinydoc.service.IDataManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DataManagerImpl implements IDataManager {


    @Override
    public File generateBackup() throws IOException {
        String bat = "cmd.exe /C start C:\\\\tinydoc\\\\backup.bat";
        Runtime.getRuntime().exec(bat);
        return new File("C:\\oraclexe\\app\\oracle\\admin\\xe\\dpdump\\BACKUP.DMP");
    }

    @Override
    public void applyRestore(MultipartFile file) throws IOException {
        String bat = "cmd.exe /C start C:\\\\tinydoc\\\\clean.bat";
        Runtime.getRuntime().exec(bat);
        StringBuilder sb = new StringBuilder();
        sb.append("C:\\oraclexe\\app\\oracle\\admin\\xe\\dpdump")
                .append(File.separator)
                .append(file.getOriginalFilename());

        byte[] fileByte = file.getBytes();
        Path path = Paths.get(sb.toString());
        Files.write(path, fileByte);


    }
}
