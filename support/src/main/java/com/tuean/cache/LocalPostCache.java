package com.tuean.cache;

import com.tuean.annotation.InitMethod;
import com.tuean.annotation.Value;
import com.tuean.entity.MarkdownFile;
import com.tuean.file.MarkdownFileReader;
import com.tuean.helper.context.Ctx;
import com.tuean.helper.context.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

@Ctx
public class LocalPostCache {

    private static final Logger logger = LoggerFactory.getLogger(LocalPostCache.class);

    @Inject
    MarkdownFileReader reader;

    @Value("markdown.path") private String markdownPath;

    @InitMethod
    public void onApplicationEvent() {
        setFiles(reader.allMarkdownFiles(markdownPath));
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
