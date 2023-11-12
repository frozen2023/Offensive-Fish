const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: './',
  css: {
    // 全局配置 utils.scs，详细配置参考 vue-cli 官网
    loaderOptions: {
      less: {
        additionalData: `@import "@/assets/style/utils.less";`,
      },
    },
  }
})
