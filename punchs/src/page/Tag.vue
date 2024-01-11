<template>
  <div style>
    <div class="divide-y divide-gray-200 dark:divide-gray-700">
      <ul>
        <PostItem v-for="postItem in filteredPosts" :key="postItem.name" :postItem="postItem"/>
      </ul>
    </div>
  </div>
</template>

<script setup>
import PostItem from "@/components/PostItem.vue";
import config from "../config"
import { computed, inject, ref, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const tag = route.params.tag;

const postjson = inject("global_context").value
const postItems = postjson.posts
const p = ref(postItems.slice())

const tag_link = tag_name => "/tags/" + tag_name


const filteredPosts = computed(() => {
  return p.value.filter((postItem) =>
      postItem.tags.includes(tag.toLowerCase())
  );
});

</script>