<template>

  <div style>
    <div class="mx-auto max-w-3xl px-4 sm:px-6 xl:max-w-5xl xl:px-0">
      <article>
        <div class="xl:divide-y xl:divide-gray-200 xl:dark:divide-gray-700">
          <header class="pt-6 xl:pb-6">
            <div class="space-y-1 text-center">
              <div>
                <h1 class="text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-5xl md:leading-14">
                  {{postItem.title}}
                </h1>
              </div>
              <dl class="space-y-10">
                <div>
                  <dt class="sr-only">Published on</dt>
                  <dt class="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
                    <time :datetime="postItem.publishDate">{{postItem.publishDate}}</time>
                  </dt>
                </div>
              </dl>
            </div>
          </header>

          <div class="divide-y divide-gray-200 pb-8 dark:divide-gray-700 xl:grid xl:grid-cols-4 xl:gap-x-6 xl:divide-y-0" style="grid-template-rows: auto 1fr;">
            <dl class="pt-6 pb-10 xl:border-b xl:border-gray-200 xl:pt-11 xl:dark:border-gray-700">
              <dt class="sr-only">Authors</dt>
              <dd>
                <ul class="flex justify-center space-x-8 sm:space-x-12 xl:block xl:space-x-0 xl:space-y-8">
                  <li class="flex items-center space-x-2">
                    <img :src="config.avatar" v-if="config.avatar !== ''" width="38px" height="38px" alt="avatar" class="h-10 w-10 rounded-lg">
                    <dl class="whitespace-nowrap text-sm font-medium leading-5">
                      <dt class="sr-only">Author</dt>
                      <dd class="text-base font-bold leading-6 text-gray-600 dark:text-gray-300">
                        {{postItem.author}}
                      </dd>
                    </dl>
                  </li>
                </ul>
              </dd>
            </dl>

            <div class="divide-y divide-gray-200 dark:divide-gray-700 xl:col-span-3 xl:row-span-2 xl:pb-0">
              <div class="prose max-w-none pt-10 pb-8 dark:prose-dark">
                <div v-html="content_html"></div>
              </div>
            </div>

            <footer class="">
              <div class="divide-gray-200 text-sm font-medium leading-5 dark:divide-gray-700 xl:col-start-1 xl:row-start-2 xl:divide-y">
                <div class="py-4 xl:py-8">
                  <h2 class="text-xs uppercase tracking-wide text-gray-500 dark:text-gray-400">
                    Tags
                  </h2>
                  <div class="flex flex-wrap">
                    <template v-for="tag in postItem.tags">
                      <a :href="tag_link(tag)" class="mr-3 font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 text-sm">
                        {{ tag }}
                      </a>
                    </template>
                  </div>
                </div>
<!--                <div class="flex justify-between py-4 xl:block xl:space-y-8 xl:py-8">-->
<!--                  <div>-->
<!--                    <h2 class="text-xs uppercase tracking-wide text-gray-500 dark:text-gray-400">-->
<!--                      Previous Article-->
<!--                    </h2>-->
<!--                    <div class="text-primary-500 hover:text-primary-600 dark:hover:text-primary-400">-->
<!--                      <a href="/blog/webworker-reconnecting-websocket">-->
<!--                        webworker-reconnecting-websocket-->
<!--                      </a>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                </div>-->
              </div>
              <div class="pt-4 xl:pt-8">
                <a href="/blogs" class="text-primary-500 hover:text-primary-600 dark:hover:text-primary-400">
                  ← Back to the blog
                </a>
              </div>
            </footer>
          </div>
        </div>
      </article>
    </div>
  </div>

</template>


<script setup>
import { useRoute } from 'vue-router';
import {inject, ref} from 'vue';
import config from "../config"
import markdownit from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/github-dark.min.css'

const route = useRoute();
const postTitle = route.params.post;

const postjson = inject("global_context").value
const posts = postjson.posts
const postItem = posts.find(postItem => postItem.title === postTitle)
console.log("postItem", posts, postItem);

const md = markdownit({
  breaks: true,
  typographer: true,
  highlight: function (str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return '<pre class="hljs"><code>' +
            hljs.highlight(lang, str, true).value +
            '</code></pre>';
      } catch (__) {}
    }

    return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>';
  }
})

const content = ref({})
let postApi = config.post_detail_api + postTitle
try {
  const response = await fetch(postApi);
  content.value = await response.json();
  console.log("load post success", content)
} catch (err) {
  console.log("load post error", err)
}

const content_html = md.render(content.value.content);
console.log('content_html', content_html)

const tag_link = tag => "/tags/" + tag

</script>

<style>
.hljs {
  background-color: #262626;
  margin-bottom: 1.25em;
  overflow-y: hidden;
  overflow-x: scroll;
}

code {
  //white-space : pre-wrap !important;

}
</style>