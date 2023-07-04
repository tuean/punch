package com.tuean.cache;

import com.tuean.entity.MarkdownFile;
import com.tuean.file.MarkdownFileReader;
import com.tuean.file.PropertiesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PostCache implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PostCache.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MarkdownFileReader reader = new MarkdownFileReader();
        setFiles(reader.allMarkdownFiles(PropertiesFileReader.getConfig().getMarkdownPath()));
        logger.info("post cache load complete");
    }


    private List<MarkdownFile> files;

    public void setFiles(List<MarkdownFile> files) {
        this.files = files;
        sort();
    }

    private void sort() {
        if (this.files == null) return;
        this.files.sort(Comparator.comparing(MarkdownFile::getLastModified).reversed());
    }

    public MarkdownFile markdownFileFromTemp(String fileId) {
        if (fileId == null) return null;
        return this.files.stream().filter(n -> fileId.equals(n.getId())).findFirst().orElse(null);
    }

    public List<MarkdownFile> list(Integer pageSize, Integer pageRow) {
        if (this.files.size() < 1) return null;
        int start = (pageRow - 1) * pageSize, end = pageRow * pageSize - 1;
        return end >= this.files.size() ?
                this.files.subList(start, this.files.size() - 1) :
                this.files.subList(start, end);
    }



}
