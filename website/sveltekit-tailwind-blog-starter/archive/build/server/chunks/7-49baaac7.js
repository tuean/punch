import { g as getEntries } from './entries-43f50aa4.js';
import { e as error } from './index2-78bfc04e.js';
import './index-32db5bef.js';
import './config-85e7a33b.js';

async function load() {
  const resume = getEntries("resume");
  if (!resume) {
    throw error(404, "No project found");
  }
  return {
    // eslint-disable-next-line no-unused-vars
    resume
  };
}

var _page_server = /*#__PURE__*/Object.freeze({
  __proto__: null,
  load: load
});

const index = 7;
const component = async () => (await import('./_page.svelte-fa1940f0.js')).default;
const server_id = "src/routes/resume/+page.server.js";
const imports = ["_app/immutable/nodes/7.ee4273a1.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/Title.497171a0.js"];
const stylesheets = [];
const fonts = [];

export { component, fonts, imports, index, _page_server as server, server_id, stylesheets };
//# sourceMappingURL=7-49baaac7.js.map
