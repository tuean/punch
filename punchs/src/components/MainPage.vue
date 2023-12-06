<template>
  <div class="pt-12">
    <h1 class="pb-6 text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">
      {{ welcome }}
    </h1>
    <p class="text-xl prose-xl text-gray-800 dark:text-gray-400">
      {{welcome_sub}}
    </p>
  </div>

  <div class="divide-y divide-gray-200 dark:divide-gray-700">
    <div class="space-y-2 pt-6 pb-8 md:space-y-5">
      <h2 class="text-2xl sm:text-3xl md:text-5xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:leading-10 md:leading-14 capitalize">
        {{latest_post}}
      </h2>
    </div>
  </div>
</template>

<script setup>
import config from "../config"

const welcome = config.welcome;
const welcome_sub = config.welcome_sub;
const latest_post = config.latest_post;
</script>