/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["../resources/templates/**/*.{html,js}"],
  layers: ['components'],

  theme: {
    extend: {
      colors: {
        "app-white":"#D9D9D9",
        "app-white-40":"#D9D9D966",
        "color-1":"#041C32",
        "color-2":"#04293A",
        "color-3":"#064663",
        "color-4":"#ECB365",
        "modal-background":"#04293AEA",
    },
    },
  },
  plugins: [],
}

