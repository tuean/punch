import { c as create_ssr_component, v as validate_component } from './index-32db5bef.js';
import { H as Head } from './Head-03cef478.js';
import { B as Blogs } from './Blogs-77e0d5ce.js';
import './config-85e7a33b.js';
import './Author-e9597d75.js';
import './entries-43f50aa4.js';
import './SocialIcon-d02abf90.js';
import './Title-7010d018.js';
import './stores-25841ccd.js';

const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data } = $$props;
  let posts = data.posts;
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `${validate_component(Head, "Head").$$render($$result, {}, {}, {})}

<div class="pt-12"><h1 class="pb-6 text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">欢迎来到tuean的博客空间
	</h1>
	<p class="text-xl prose-xl text-gray-800 dark:text-gray-400">本站主要记录工作生活中值得纪念的一些总结。
	</p></div>

${validate_component(Blogs, "Blogs").$$render(
    $$result,
    {
      title: "最新文章",
      h2: true,
      posts,
      search: false,
      count: 3
    },
    {},
    {}
  )}`;
});

export { Page as default };
//# sourceMappingURL=_page.svelte-761c44c4.js.map
