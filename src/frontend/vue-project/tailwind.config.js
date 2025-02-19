/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        // //purple #dbd8f5 #ada3f9 #9364ee #504482   #534f6c
        // //gray #f0eff5 #d7d6e4
        // //yellow #ffae45
        // //blue #81bafe
        // // pink #ea356e
        // myPurple1: "#dbd8f5",
        // myPurple2: "#ada3f9",
        // myPurple3: "#9364ee",
        // myPurple4: "#504482 ",
        // myPurple5: "#534f6c",
        // myGray1: "#f0eff5",
        // myGray2: "#d7d6e4",
        // myYellow1: "#ffae45",
        // myPink1: "#ea356e",
        // myBlue1: "#81bafe",

        myDarkNavy: "#212A3E",
        myLightNavy: "#394867",
        myGray: "#9BA4B5",
        myLightGray: "#F1F6F9",
      },
    },
    fontFamily: {
      Roboto: ["Roboto, sans-serif"],
      Atkinson: ["Atkinson Hyperlegible Next"],
    },
  },
  plugins: [],
};
