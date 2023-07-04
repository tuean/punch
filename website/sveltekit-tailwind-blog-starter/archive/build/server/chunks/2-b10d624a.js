import { g as getEntries } from './entries-43f50aa4.js';
import { e as error } from './index2-78bfc04e.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

async function load() {
  const posts = getEntries("posts");
  if (!posts) {
    throw error(404, "No post found");
  }
  return {
    // eslint-disable-next-line no-unused-vars
    posts
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 2;
const component = async () => (await import('./_page.svelte-761c44c4.js')).default;
const server_id = "src/routes/+page.server.js";
const imports = ["_app/immutable/nodes/2.c0556a1c.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Head.a05bced0.js","_app/immutable/chunks/config.0a36198f.js","_app/immutable/chunks/Blogs.42473e52.js","_app/immutable/chunks/Author.51a66923.js","_app/immutable/chunks/SocialIcon.392faef0.js","_app/immutable/chunks/Title.497171a0.js","_app/immutable/chunks/stores.4aa23369.js","_app/immutable/chunks/singletons.9307c28c.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=2-b10d624a.js.map
