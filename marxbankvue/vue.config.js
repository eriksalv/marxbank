module.exports = {
  // https://cli.vuejs.org/config/#devserver
  devServer: {
    port: 3000,
    proxy: {
      "": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
      },
    },
  },
};
