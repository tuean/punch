import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import Home from '../page/Home.vue'
import MainPage from '../components/MainPage.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'MainPage.vue',
      component: MainPage
    },
    {
      path: '/blogs',
      name: 'blog.vue',
      component: () => import("../page/Blog.vue")
    },
    {
      path: "/blog/:post",
      name: "post.vue",
      component: () => import("../page/Post.vue")
    },
    {
      path: "/tags/:tag",
      name: "tag.vue",
      component: () => import("../page/Tag.vue")
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: "/404",
      name: "404.vue",
      component: () => import("../page/404.vue")
    }
  ]
})

export default router
