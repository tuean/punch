package com.tuean.controller;

import com.tuean.annotation.Api;
import com.tuean.annotation.ApiJson;
import com.tuean.annotation.RequestParam;
import com.tuean.cache.PostCache;
import com.tuean.entity.MarkdownFile;
import com.tuean.exception.BadRequestException;
import com.tuean.helper.context.Ctx;
import com.tuean.helper.context.Inject;

import java.util.List;

@Ctx
@Api
public class PostController {

    @Inject
    PostCache postCache;

    @ApiJson(path = "/post/info")
    public MarkdownFile markdownFile(@RequestParam("id") String id) {
        return postCache.markdownFileFromTemp(id);
    }

    @ApiJson(path = "/post/list")
    public List<MarkdownFile> list(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageRow") Integer pageRow) {
        if (pageSize == null || pageRow == null || pageSize < 1 || pageRow < 1) throw new BadRequestException();
        return postCache.list(pageSize, pageRow);
    }

}
