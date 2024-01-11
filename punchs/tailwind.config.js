/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');
const colors = require('tailwindcss/colors');


module.exports = {
  safelist:[
    {
      pattern: /hljs+/,
    }
  ],
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx,json}'],
  darkMode: 'media', // or 'media' or 'class'
  theme: {
    extend: {
      spacing: {
        '9/16': '56.25%'
      },
      lineHeight: {
        11: '2.75rem',
        12: '3rem',
        13: '3.25rem',
        14: '3.5rem'
      },
      fontFamily: {
        title: ['Saira', ...defaultTheme.fontFamily.sans],
        body: ['Open Sans', ...defaultTheme.fontFamily.sans]
      },
      colors: {
        primary: colors.green,
        gray: colors.neutral
      },
    },
  },
  plugins: [

  ]
}

