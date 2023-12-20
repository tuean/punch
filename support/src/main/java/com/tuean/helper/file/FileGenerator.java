package com.tuean.helper.file;

import com.tuean.config.Environment;
import com.tuean.entity.MarkdownFile;
import com.tuean.entity.blog.Context;
import com.tuean.entity.blog.PostItem;
import com.tuean.entity.blog.Tag;
import com.tuean.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FileGenerator {

    private static Logger logger = LoggerFactory.getLogger(FileGenerator.class);

    public static Context generate(List<MarkdownFile> mds) {
        List<PostItem> postItems = mds.stream().map(t -> {
            try {
                return Util.convertFile2PostItem(t);
            } catch (Exception var) {
                logger.error("covert markdown file error: {}", t.getFileName(), var);
                return null;
            }
        }).filter(Objects::nonNull)
                .sorted(Comparator.comparing(PostItem::getPublishDate).reversed())
                .toList();

        int size = Integer.parseInt(Environment.getProperty("blog.post.recommend.size"));
        List<PostItem> recommend = postItems.stream()
                .sorted(Comparator.comparing(PostItem::getPublishDate).reversed())
                .limit(size).toList();

        Map<String, Integer> tagCount = new HashMap<>();
        postItems.forEach(post -> {
            List<String> tags = post.getTags();
            if (CollectionUtils.isEmpty(tags)) return;
            tags.forEach(tag -> tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1));
        });
        List<Tag> tags = tagCount.keySet().stream().map(k -> new Tag(k, tagCount.get(k))).toList();

        Context context = new Context(recommend, postItems, tags);

        return context;
    }

}
