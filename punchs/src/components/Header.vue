<template>
  <header class="flex items-center justify-between py-10">
    <div>
      <a href="/" aria-label="Tuean's blog">
        <div class="flex items-center justify-between">
          <div class="mr-3">
            <img src="/tuean.ico" alt="Logo" class="h-10 w-auto"/>
          </div>
          <div class="hidden text-4xl font-semibold sm:block font-title">{{ title }}</div>
        </div>
      </a>
    </div>

    <div class="flex items-center text-base leading-5">
      <template v-for="item in links">
        <a :href="item.path" class="p-1 font-medium text-gray-900 dark:text-gray-100 sm:p-4">{{ item.title }}</a>
      </template>
    </div>
  </header>

</template>

<script setup>
import config from '../config'

const title = config.title;
const links = config.links;
</script>