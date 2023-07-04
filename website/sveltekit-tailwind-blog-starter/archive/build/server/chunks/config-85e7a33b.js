const config = {
  website: "沪ICP备2021014470号-1",
  title: "Tuean (un)official blog",
  author: "钟啸天",
  headerTitle: "Tuean (un)official blog",
  description: "Tuean (un)official blog, created with Sveltekit and Tailwind.css",
  language: "en-us",
  theme: "dark",
  // system, dark or light
  domain: "http://www.piedpiper.com/",
  siteUrl: "https://pied-piper-blog.netlify.app",
  siteRepo: "https://github.com/akiarostami/sveltekit-tailwind-blog-starter",
  siteLogo: "/tuean.jpg",
  repoLogo: "/icon-512.png",
  // image: '/img/avatar.png',
  email: "tuean_z@163.com",
  github: "https://github.com/tuean",
  twitter: "https://twitter.com/piedpiperplc",
  facebook: "https://www.facebook.com/piedpipersv",
  youtube: "https://www.youtube.com/watch?v=p3RwX06wcBs",
  linkedin: "https://www.linkedin.com/company/pied-piper-plc/",
  locale: "en-US",
  primaryColor: "#06a261",
  // supports buttondown, convertkit, emailoctopus, klaviyo, mailchimp, revue
  // use false or null to disable newsletter
  // check .env.example for settings needed values for each service
  newsletter: "mailchimp",
  multiuser: true
};
const user = {
  name: "admin",
  // avatar value can be removed for image
  avatar: "/tuean.jpg",
  // twitter value can be removed for no link to twitter
  twitter: "https://twitter.com/piedpiperplc"
};
const navLinks = [
  { href: "/blog", title: "Blog" },
  // { href: '/projects', title: 'Projects' },
  { href: "/resume", title: "Resume" },
  { href: "/about", title: "About" }
];
const comment = {
  provider: "giscus",
  giscus: {
    // Visit the link below, and follow the steps in the 'configuration' section
    // https://giscus.app/
    repo: "akiarostami/sveltekit-tailwind-blog-starter",
    reposId: "R_kgDOIen4kw",
    category: "Sample Site Comments",
    categoryId: "DIC_kwDOIen4k84CS9tX",
    mapping: "pathname",
    // supported options: pathname, url, title
    reactionsEnabled: "1",
    // Emoji reactions: 1 = enable / 0 = disable
    // Send discussion metadata periodically to the parent window: 1 = enable / 0 = disable
    metadata: "0",
    // theme example: light, dark, dark_dimmed, dark_high_contrast
    // Place the comment box above the comments. options: bottom, top
    inputPosition: "bottom",
    // Choose the language giscus will be displayed in. options: en, es, zh-CN, zh-TW, ko, ja etc
    lang: "en",
    dataStrict: "0",
    loading: "lazy",
    // theme example: light, dark, preferred_color_scheme, light_high_contrast, dark_high_contrast
    // light_protanopia, dark_protanopiam, light_tritanopia, dark_tritanopia, dark_dimmed, transparent_dark
    theme: "light",
    // theme when dark mode
    darkTheme: "dark",
    themeURL: ""
  },
  utterances: {
    // Visit the link below, and follow the steps in the 'configuration' section
    // https://utteranc.es/
    repo: "akiarostami/sveltekit-tailwind-blog-starter",
    issueTerm: "pathname",
    // supported options: pathname, url, title
    label: "Comment 💬",
    // label (optional): Comment 💬
    // theme example: github-light, github-dark, preferred-color-scheme
    // github-dark-orange, icy-dark, dark-blue, photon-dark, boxy-light, gruvbox-dark"
    theme: "github-light",
    // theme when dark mode
    darkTheme: "github-dark"
  }
};

export { comment as a, config as c, navLinks as n, user as u };
//# sourceMappingURL=config-85e7a33b.js.map
