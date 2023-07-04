const manifest = {
	appDir: "_app",
	appPath: "_app",
	assets: new Set(["admin/config.yml","admin/index.html","favicon.ico","icon-128.png","icon-16.png","icon-256.png","icon-32.png","icon-512.png","img/dinesh.jpg","img/gilfoyle.jpg","img/logo.png","img/piperchat.jpg","img/pipernet.jpg","img/richard.png","img/thebox.png","img/theplatform.jpg","logo.png","logo.svg","tuean.ico","tuean.jpg"]),
	mimeTypes: {".yml":"text/yaml",".html":"text/html",".ico":"image/vnd.microsoft.icon",".png":"image/png",".jpg":"image/jpeg",".svg":"image/svg+xml"},
	_: {
		client: {"start":"_app/immutable/entry/start.e6a1b1d9.js","app":"_app/immutable/entry/app.d90af8dd.js","imports":["_app/immutable/entry/start.e6a1b1d9.js","_app/immutable/chunks/index.789cc631.js","_app/immutable/chunks/singletons.9307c28c.js","_app/immutable/entry/app.d90af8dd.js","_app/immutable/chunks/index.789cc631.js"],"stylesheets":[],"fonts":[]},
		nodes: [
			() => import('./chunks/0-5ca0a3de.js'),
			() => import('./chunks/1-13e876bf.js'),
			() => import('./chunks/2-b10d624a.js'),
			() => import('./chunks/3-73e8d5fc.js'),
			() => import('./chunks/4-f1f38597.js'),
			() => import('./chunks/5-7bd6daa6.js'),
			() => import('./chunks/6-5002ca5f.js'),
			() => import('./chunks/7-49baaac7.js'),
			() => import('./chunks/8-92f90638.js')
		],
		routes: [
			{
				id: "/",
				pattern: /^\/$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 2 },
				endpoint: null
			},
			{
				id: "/about",
				pattern: /^\/about\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 3 },
				endpoint: null
			},
			{
				id: "/api/newsletter",
				pattern: /^\/api\/newsletter\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./chunks/_server-6317d6d9.js')
			},
			{
				id: "/blog",
				pattern: /^\/blog\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 4 },
				endpoint: null
			},
			{
				id: "/blog/[slug]",
				pattern: /^\/blog\/([^/]+?)\/?$/,
				params: [{"name":"slug","optional":false,"rest":false,"chained":false}],
				page: { layouts: [0,], errors: [1,], leaf: 5 },
				endpoint: null
			},
			{
				id: "/og",
				pattern: /^\/og\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./chunks/_server-4ed993c7.js')
			},
			{
				id: "/projects",
				pattern: /^\/projects\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 6 },
				endpoint: null
			},
			{
				id: "/resume",
				pattern: /^\/resume\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 7 },
				endpoint: null
			},
			{
				id: "/tags/[tag]",
				pattern: /^\/tags\/([^/]+?)\/?$/,
				params: [{"name":"tag","optional":false,"rest":false,"chained":false}],
				page: { layouts: [0,], errors: [1,], leaf: 8 },
				endpoint: null
			}
		],
		matchers: async () => {
			
			return {  };
		}
	}
};

const prerendered = new Set(["/rss.xml","/sitemap.xml"]);

export { manifest, prerendered };
//# sourceMappingURL=manifest.js.map
