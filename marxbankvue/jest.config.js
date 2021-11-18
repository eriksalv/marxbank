/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  clearMocks: true,
  coverageDirectory: "coverage",
  coverageProvider: "v8",
  collectCoverage: true,
  collectCoverageFrom: [
    "**/src/**/*.{js,vue,ts}",
    "!**/types.ts",
    "!**/src/shims-vue.d.ts",
    "!**/src/main.ts",
    "!**/node_modules/**",
    "!**/*.config.js",
    "!**/coverage/**",
  ],
  moduleFileExtensions: ["js", "json", "ts", "node", "vue"],
  transform: {
    "^.+\\.js$": "babel-jest",
    ".*\\.(vue)$": "vue3-jest",
  },
};
