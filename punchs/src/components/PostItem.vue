<template>
  <li class="py-12">
    <article>
        <div class="space-y-2 xl:grid xl:grid-cols-4 xl:items-baseline xl:space-y-0">
          <dl class="whitespace-nowrap text-sm font-medium leading-5">
            <dt class="sr-only">Author</dt>
            <dd class="text-base font-bold leading-6 text-gray-600 dark:text-gray-300">{{post.author}}</dd>

            <dt class="sr-only">Published on</dt>
            <dd class="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
              <time datetime="{{post.published}}">{{post.publish_date}}</time>
            </dd>
          </dl>

          <div class="space-y-5 xl:col-span-3">
            <div class="space-y-6">
              <div>
                <h2 class="text-2xl font-bold leading-8 tracking-tight">
                  <a :href="post_path" class="text-gray-900 dark:text-gray-100">{{post.name}}</a>
                </h2>
                <div class="flex flex-wrap">

                </div>
              </div>
            </div>
          </div>
        </div>
    </article>
  </li>
</template>

<script setup>
import { toRefs, defineProps } from 'vue';

const props = defineProps({
  post: Object
})

const post = toRefs(props)
const post_path = "/blog/" + post.name
</script>