package com.tuean.helper.file;

import com.tuean.entity.MarkdownFile;
import com.tuean.entity.blog.Context;
import com.tuean.entity.blog.Post;
import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileGenerator {

    private static Logger logger = LoggerFactory.getLogger(FileGenerator.class);

    public Context generate(List<MarkdownFile> mds) {
        List<Post> posts = mds.stream().map(t -> {
            try {
                return Util.convertFile2Post(t);
            } catch (Exception var) {
                logger.error("covert markdown file error: {}", t.getFileName(), var);
                return null;
            }
        }).filter(Objects::nonNull).toList();

        Map<String, Integer> tagCount = new HashMap<>();

    }

}
