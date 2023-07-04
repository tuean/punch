package com.tuean.controller;

import com.tuean.cache.PostCache;
import com.tuean.entity.MarkdownFile;
import com.tuean.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostCache postCache;

    @RequestMapping(value = "/post/info")
    public MarkdownFile markdownFile(@RequestParam("id") String id) {
        return postCache.markdownFileFromTemp(id);
    }

    @RequestMapping(value = "/post/list")
    public List<MarkdownFile> list(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageRow") Integer pageRow) {
        if (pageSize == null || pageRow == null || pageSize < 1 || pageRow < 1) throw new BadRequestException();
        return postCache.list(pageSize, pageRow);
    }

}
