
package com.ecnu.timeline.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@ConfigurationProperties(prefix = "file")
@Data
@Component
@Slf4j
//功能二合一
public class FileStorageConfig implements ProgressListener {

    private String uploadDir;

    private HttpSession session;

    public void setSession(HttpSession session){
        this.session=session;
//        log.info("percent = {}",0);

        //begin,may be long for this period time.
//        session.setAttribute("uploadStatus",fileUploadProgressDTO);
    }

    @Override
    public void update(long l, long l1, int i) {  //long bytesRead, long contentLength, int item
        // 不做其他---可加文件上传进度
    }
}
