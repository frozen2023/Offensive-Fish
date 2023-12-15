// 编写当点击开始游戏按钮，进入游戏页面时，页面的参数是否正确
const { createApp } = require('vue')
const { shallowMount } = require('@vue/test-utils')
const roomType = require('@/views/play/createRoom.vue').default

global.createApp = createApp
global.shallowMount = shallowMount

describe('roomType.vue', () => {
  it('should have correct parameters when the "Start Game" button is clicked', () => {
    // 模拟点击 "Start Game" 按钮
    const app = createApp(roomType)
    const wrapper = shallowMount(app)
    const startGameButton = wrapper.find('.home_body_middle_out_begin')
    startGameButton.trigger('click')

    // 获取游戏页面的参数
    const gamePageParams = wrapper.vm.$route.params
    console.log(gamePageParams)
  })
})