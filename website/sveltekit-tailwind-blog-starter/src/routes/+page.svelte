<script>
	import Head from '$lib/components/layout/Head.svelte';
	import Blogs from '$lib/components/Blogs.svelte';

	export let data;
	let posts = data.posts;
</script>

<Head />

<div class="pt-12">
	<h1
		class="pb-6 text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"
	>
		欢迎来到tuean的博客空间
	</h1>
	<p class="text-xl prose-xl text-gray-800 dark:text-gray-400">
		本站主要记录工作生活中值得纪念的一些总结。
	</p>
</div>

<Blogs title="最新文章" h2 {posts} search={false} count={3} />
