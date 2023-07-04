import { e as error } from './index2-78bfc04e.js';
import { g as getEntries, s as slug } from './entries-43f50aa4.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

function slugsArray(tags) {
  return tags?.map((t) => slug(t)) || [];
}
async function load({ params }) {
  const { tag } = params;
  const posts = getEntries("posts");
  const filteredPosts = posts.filter((p) => slugsArray(p.tags).includes(tag));
  if (!filteredPosts) {
    throw error(404, "No post found");
  }
  return {
    // eslint-disable-next-line no-unused-vars
    tag,
    posts: filteredPosts
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 8;
const component = async () => (await import('./_page.svelte-d42ffda7.js')).default;
const server_id = "src/routes/tags/[tag]/+page.server.js";
const imports = ["_app/immutable/nodes/8.a16f16c2.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Head.a05bced0.js","_app/immutable/chunks/config.0a36198f.js","_app/immutable/chunks/Blogs.42473e52.js","_app/immutable/chunks/Author.51a66923.js","_app/immutable/chunks/SocialIcon.392faef0.js","_app/immutable/chunks/Title.497171a0.js","_app/immutable/chunks/stores.4aa23369.js","_app/immutable/chunks/singletons.9307c28c.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=8-92f90638.js.map
