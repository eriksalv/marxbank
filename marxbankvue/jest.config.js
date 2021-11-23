/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  clearMocks: true,
  coverageDirectory: "coverage",
  coverageProvider: "v8",
  collectCoverage: false,
  collectCoverageFrom: [
    "**/src/**/*.{js,vue,ts}",
    "!**/types.ts",
    "!**/src/shims-vue.d.ts",
    "!**/src/main.ts",
    "!**/node_modules/**",
    "!**/*.config.js",
    "!**/coverage/**",
    "!**/index.ts",
    "!**/service/**"
  ],
  moduleFileExtensions: ["js", "json", "ts", "node", "vue"],
  transform: {
    "^.+\\.js$": "babel-jest",
    ".*\\.(vue)$": "vue3-jest",
  },
};
