package com.tinydoc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IDataManager {

    File generateBackup() throws IOException;

    void applyRestore(MultipartFile file) throws IOException;
}
