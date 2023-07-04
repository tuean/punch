// import adapter from '@sveltejs/adapter-auto';
// import preprocess from "svelte-preprocess"; // 预处理器
// import adapter from '@sveltejs/adapter-static'; // https://kit.svelte.dev/docs/adapter-static
import adapter from '@sveltejs/adapter-node';

import { vitePreprocess } from '@sveltejs/kit/vite';
import { mdsvex } from 'mdsvex';
import mdsvexConfig from './mdsvex.config.js';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	extensions: ['.svelte', ...mdsvexConfig.extensions],
	kit: {
		// adapter: adapter({
		// 	pages: 'build', // 将预渲染页面写入的目录。它默认为`build`.
		// 	assets: 'build', // 资源 打包输出目录包括Svelte-kit输出的js和css
		// 	fallback: 'index.html', // 指定 SPA 模式的后备页面，例如`index.html`或`200.html`或`404.html`。
		// 	precompresses: true,
		// 	strictpermalink: false
		// }),
		adapter: adapter({
			out: 'build',
			precompress: false,
			envPrefix: '',
			polyfill: true
		}),

		// remove this if you're not using comment system
		csp: { mode: 'auto' }
	},
	preprocess: [mdsvex(mdsvexConfig), vitePreprocess()]
};

export default config;
