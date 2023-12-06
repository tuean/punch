<template>
  <div class="mx-auto max-w-3xl px-4 sm:px-6 xl:max-w-5xl xl:px-0">
    <div class="flex h-screen flex-col justify-between">
      <Header />
      <div class="mb-auto">
        <RouterView />
      </div>
      <Footer />
    </div>
  </div>

</template>

<script setup>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { RouterView } from 'vue-router'


</script>