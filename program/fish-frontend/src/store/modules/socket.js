export default {
  namespaced: true,
  state () {
    return {
      socket: null,
      roomId: null,
    }
  },
  mutations: {
    setSocket (state, socket) {
      state.socket = socket
    },
    setRoomId (state, roomId) {
      state.roomId = roomId
    },
  },
  actions: {
  },
  getters: {}
}