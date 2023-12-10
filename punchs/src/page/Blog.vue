<template>
  <div style>
    <div class="divide-y divide-gray-200 dark:divide-gray-700">
      <div class="space-y-2 pt-6 pb-8 md:space-y-5">
        <div class="grid lg:grid-cols-2 gap-4">
          <div>
            <h1 class="text-3xl sm:text-4xl md:text-6xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:leading-10 md:leading-14 capitalize">
              {{ config.blog_search_left }}
            </h1>
          </div>
          <div class="pl-4 border-l-2">
            <div class="relative mb-2">
              <input id="search" name="search" aria-label="Search posts" type="text" placeholder="Search posts"
                     class="block w-full rounded-md border border-gray-300 bg-white px-4 py-2 text-gray-900 focus:border-primary-500 focus:ring-primary-500 dark:border-gray-900 dark:bg-gray-800 dark:text-gray-100">
              <svg @click="search" class="absolute right-3 top-3 h-5 w-5 text-gray-400 dark:text-gray-300"
                   xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>

            <div class="flex flex-wrap">
              <template v-for="tag in tags">
                <div class="mr-5">
                  <a :href="tag_link(tag.tag)"
                     class="mr-3 font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 text-xs">
                    {{ tag.tag }}
                  </a>
                  <a :href="tag_link(tag.tag)"
                     class="-ml-2 text-xs font-semibold uppercase text-gray-600 dark:text-gray-300">
                    ({{ tag.no }})
                  </a>
                </div>
              </template>
            </div>
          </div>
        </div>
      </div>

      <ul>
        <PostItem v-for="post in p" :post="post"/>
      </ul>
    </div>
  </div>

</template>


<script setup>
import PostItem from "../components/PostItem.vue"
// import PostJson from "../post.json"
import config from "../config"
import {inject, ref} from "vue";

const postjson = inject("global_context").value
const posts = postjson.posts
const tags = postjson.tags
const p = ref(posts.slice())

const tag_link = tag_name => "/tags/" + tag_name

const filter = (post, key) => {
  return post.name.includes(key)
}

const search = key => {
  p.value = posts.filter(filter)
}

</script>