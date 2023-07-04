<script>
	import Title from '$lib/components/Title.svelte';

	export let data;
	const resume = data.resume[0];
	console.log(resume)
</script>

<div class="divide-y divide-gray-200 dark:divide-gray-700">
	<div class="space-y-2 pt-6 pb-8 md:space-y-5">
		<Title title={resume.name_english} subtitle={resume.name} />

	</div>
	<div class="container py-12">
		<div class="prose max-w-none pt-10 pb-8 dark:prose-dark">
			{@html resume.content}
		</div>
	</div>
</div>

