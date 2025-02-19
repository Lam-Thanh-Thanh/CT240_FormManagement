/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        myDarkNavy: "#212A3E",
        myLightNavy: "#394867",
        myGray: "#9BA4B5",
        myLightGray: "#F1F6F9",
      },
    },
    fontFamily: {
      Roboto: "Roboto",
      Georama: "Georama",
    },
  },
  plugins: [],
};
