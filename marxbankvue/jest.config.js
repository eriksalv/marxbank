/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'jsdom',
  clearMocks: true,
  coverageDirectory: "coverage",
  coverageProvider: "v8",
  moduleFileExtensions: [
    "js",
    "json",
    "ts",
    "node",
    "vue"
  ],
  transform: {
    "^.+\\.js$": "babel-jest",
    ".*\\.(vue)$": "vue3-jest"
  }
};