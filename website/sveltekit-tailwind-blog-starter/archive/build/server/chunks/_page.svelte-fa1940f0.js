import { c as create_ssr_component, v as validate_component } from './index-32db5bef.js';
import { T as Title } from './Title-7010d018.js';

const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data } = $$props;
  const resume = data.resume[0];
  console.log(resume);
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `<div class="divide-y divide-gray-200 dark:divide-gray-700"><div class="space-y-2 pt-6 pb-8 md:space-y-5">${validate_component(Title, "Title").$$render(
    $$result,
    {
      title: resume.name_english,
      subtitle: resume.name
    },
    {},
    {}
  )}</div>
	<div class="container py-12"><div class="prose max-w-none pt-10 pb-8 dark:prose-dark"><!-- HTML_TAG_START -->${resume.content}<!-- HTML_TAG_END --></div></div></div>`;
});

export { Page as default };
//# sourceMappingURL=_page.svelte-fa1940f0.js.map
