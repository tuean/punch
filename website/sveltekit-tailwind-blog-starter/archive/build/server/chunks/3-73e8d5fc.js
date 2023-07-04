import { g as getEntries } from './entries-43f50aa4.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

const authors = getEntries("authors");
async function load() {
  return {
    // eslint-disable-next-line no-unused-vars
    authors
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 3;
const component = async () => (await import('./_page.svelte-d3d72998.js')).default;
const server_id = "src/routes/about/+page.server.js";
const imports = ["_app/immutable/nodes/3.9fb4ffca.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Head.a05bced0.js","_app/immutable/chunks/config.0a36198f.js","_app/immutable/chunks/Title.497171a0.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=3-73e8d5fc.js.map
