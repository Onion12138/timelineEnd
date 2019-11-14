package com.ecnu.timeline.config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@Slf4j
public class FileMultipartResolver extends CommonsMultipartResolver {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {

        String encoding = super.determineEncoding(request);

        fileStorageConfig.setSession(request.getSession());

        FileUpload fileUpload = prepareFileUpload(encoding);

        fileUpload.setProgressListener(fileStorageConfig);

        try {
            List<FileItem> fileItems = ((ServletFileUpload)fileUpload).parseRequest(request);

            return super.parseFileItems(fileItems,encoding);
        }catch (FileUploadException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
