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
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `${validate_component(Head, "Head").$$render($$result, { title: data.tag }, {}, {})}

${validate_component(Blogs, "Blogs").$$render(
    $$result,
    {
      title: data.tag,
      posts: data.posts,
      search: false
    },
    {},
    {}
  )}`;
});

export { Page as default };
//# sourceMappingURL=_page.svelte-d42ffda7.js.map
