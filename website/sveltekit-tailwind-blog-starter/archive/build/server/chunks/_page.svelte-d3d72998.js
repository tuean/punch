import { c as create_ssr_component, v as validate_component, a as add_attribute } from './index-32db5bef.js';
import { H as Head } from './Head-03cef478.js';
import { c as config } from './config-85e7a33b.js';
import { T as Title } from './Title-7010d018.js';

const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data } = $$props;
  data.authors;
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `${validate_component(Head, "Head").$$render($$result, { title: "About" }, {}, {})}

<div class="divide-y divide-gray-200 dark:divide-gray-700"><div class="space-y-2 pt-6 pb-8 md:space-y-5">${validate_component(Title, "Title").$$render($$result, { title: "About" }, {}, {})}</div>
	<div class="items-start space-y-2 xl:grid xl:grid-cols-3 xl:gap-x-8 xl:space-y-0"><div class="flex flex-col items-center py-6"><img${add_attribute("src", config.repoLogo, 0)} alt="logo" class="h-64 w-64">





</div>
		<div class="prose max-w-none pt-8 pb-8 dark:prose-dark xl:col-span-2">



			本网站项目克隆自： <a href="https://github.com/akiarostami/sveltekit-tailwind-blog-starter">sveltekit-tailwind-blog-starter</a>.
			<br>
			感谢项目所有的开发人员与贡献者。
		</div></div>





















</div>`;
});

export { Page as default };
//# sourceMappingURL=_page.svelte-d3d72998.js.map
