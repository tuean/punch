<template>
  <li class="py-12">
    <article>
        <div class="space-y-2 xl:grid xl:grid-cols-4 xl:items-baseline xl:space-y-0">
          <dl class="whitespace-nowrap text-sm font-medium leading-5">
            <dt class="sr-only">Author</dt>
            <dd class="text-base font-bold leading-6 text-gray-600 dark:text-gray-300">{{post.author}}</dd>

            <dt class="sr-only">Published on</dt>
            <dd class="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
              <time datetime="{{post.published}}">{{post.publishDate}}</time>
            </dd>
          </dl>

          <div class="space-y-5 xl:col-span-3">
            <div class="space-y-6">
              <div>
                <h2 class="text-2xl font-bold leading-8 tracking-tight">
                  <a :href="post_path" class="text-gray-900 dark:text-gray-100">{{post.name}}</a>
                </h2>
                <div class="flex flex-wrap">
                  <a v-for="tag in post.tags" :href="tag_link(tag)" class="mr-3 font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 text-sm">
                    {{tag}}
                  </a>
                </div>
              </div>

              <div class="text-base font-medium leading-6">
                <a :href="post_path" class="text-primary-500 hover:text-primary-600 dark:hover:text-primary-400">
                    {{config.link_text}}
                </a>
              </div>
            </div>
          </div>
        </div>
    </article>
  </li>
</template>

<script setup>
import { toRefs, defineProps } from 'vue';
import config from './../config';

const props = defineProps({
  post: Object
})

const post = props.post
const post_path = "/blog/" + post.name
const tag_link = (tag) => {
  return "/tags/" + tag
}
</script>