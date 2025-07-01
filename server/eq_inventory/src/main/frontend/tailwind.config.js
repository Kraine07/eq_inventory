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
        "color-1-40":"#041C3266",
        "color-2":"#04293A",
        "color-2-40":"#04293A66",
        "color-3":"#064663",
        "color-3-40":"#06466366",
        "color-4":"#ECB365",
        "color-4-40": "#ECB36566",
        "modal-background":"#04293AEA",


        // "color-1":"#EBFFFB",
        // "color-1-40":"#EBFFFB66",
        // "color-2":"#7EFAFF",
        // "color-2-40":"#7EFAFF66",
        // "color-3":"#13ABC4",
        // "color-3-40":"#13ABC466",
        // "color-4":"#3161A3",
        // "color-4-40":"#3161A366",
        // "modal-background":"#04293AEA",
      },
      boxShadow: (theme) => ({
        "app-shadow": `0 0 12px ${theme('colors.color-1-40')}`,
      }),
    },
  },
  plugins: [
    require("tailwind-scrollbar"),

  ],
}

