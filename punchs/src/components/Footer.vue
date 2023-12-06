<template>
  <footer>
    <div class="mt-16 flex flex-col items-center border-t pt-6 gap-2">
      <div class="mb-3 flex space-x-2">
        <a class="flex gap-x-2 text-gray-500 transition hover:text-primary-600" :href="mailTo">
          <span class="sr-only">mail</span>
          <svg
              width="24px"
              height="24px"
              stroke-width="1.5"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              color="currentColor"
          >
            <path
                d="M7 9l5 3.5L17 9"
                stroke="currentColor"
                stroke-width="1.5"
                stroke-linecap="round"
                stroke-linejoin="round"
            />
            <path
                d="M2 17V7a2 2 0 012-2h16a2 2 0 012 2v10a2 2 0 01-2 2H4a2 2 0 01-2-2z"
                stroke="currentColor"
                stroke-width="1.5"
            />
          </svg
          >
        </a>

        <a class="flex gap-x-2 text-gray-500 transition hover:text-primary-600" :href="footer.github">
          <span class="sr-only">github</span>
          <svg
              width="24px"
              height="24px"
              stroke-width="1.5"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              color="currentColor"
          >
            <path
                d="M16 22.027v-2.87a3.37 3.37 0 00-.94-2.61c3.14-.35 6.44-1.54 6.44-7a5.44 5.44 0 00-1.5-3.75 5.07 5.07 0 00-.09-3.77s-1.18-.35-3.91 1.48a13.38 13.38 0 00-7 0c-2.73-1.83-3.91-1.48-3.91-1.48A5.07 5.07 0 005 5.797a5.44 5.44 0 00-1.5 3.78c0 5.42 3.3 6.61 6.44 7a3.37 3.37 0 00-.94 2.58v2.87M9 20.027c-3 .973-5.5 0-7-3"
                stroke="currentColor"
                stroke-width="1.5"
                stroke-linecap="round"
                stroke-linejoin="round"
            />
          </svg
          >
        </a>
      </div>

      <div class="mb-4 flex space-x-2 text-sm text-gray-500 dark:text-gray-400">
        <a :href="footer.filling_link">{{footer.filling}}</a>
        <a href="/">{{config.title}}</a>
        <span>{{footer.author}}</span>
      </div>
    </div>
  </footer>

</template>

<script setup>
import config from "../config"

const footer = config.footer;
const mailTo = "mailto:" + footer.email;

</script>