import { g as getEntries } from './entries-43f50aa4.js';
import { e as error } from './index2-78bfc04e.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

async function load() {
  const projects = getEntries("projects");
  if (!projects) {
    throw error(404, "No project found");
  }
  return {
    // eslint-disable-next-line no-unused-vars
    projects
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 6;
const component = async () => (await import('./_page.svelte-b06075e5.js')).default;
const server_id = "src/routes/projects/+page.server.js";
const imports = ["_app/immutable/nodes/6.c31380b3.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Title.497171a0.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=6-5002ca5f.js.map
