import { e as error } from './index2-78bfc04e.js';
import { g as getEntries } from './entries-43f50aa4.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

async function load({ params }) {
  const posts = getEntries("posts");
  const authors = getEntries("authors");
  const { slug } = params;
  const post = posts.find((p) => p.slug === slug);
  const author = authors.find((a) => a.name === post.author);
  if (!post) {
    throw error(404, "No post found");
  }
  return {
    // eslint-disable-next-line no-unused-vars
    post,
    author
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 5;
const component = async () => (await import('./_page.svelte-b0264fb1.js')).default;
const server_id = "src/routes/blog/[slug]/+page.server.js";
const imports = ["_app/immutable/nodes/5.7185cf20.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Head.a05bced0.js","_app/immutable/chunks/config.0a36198f.js","_app/immutable/chunks/Author.51a66923.js","_app/immutable/chunks/SocialIcon.392faef0.js","_app/immutable/chunks/theme.14bac314.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=5-7bd6daa6.js.map
