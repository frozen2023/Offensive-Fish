import { getRoomInfoByLocal } from '@/utils/storage.js'

export default {
  namespaced: true,
  state () {
    return {
      socket: null,
      roomId: null,
      mapId: '0',
      roomInfo: getRoomInfoByLocal(),
      masterRoleId: '0',
      playerRoleId: '0',
      chatMessages: [],
      scores: {
        master: 0,
        player: 0,
      }
    }
  },
  mutations: {
    setSocket (state, socket) {
      state.socket = socket
    },
    setRoomId (state, roomId) {
      state.roomId = roomId
    },
    setMap(state, map) {
      state.mapId = map
    },
    setMyRole(state, roleId) {
      state.masterRoleId = roleId
    },
    setPlayerRole(state, roleId) {
      state.playerRoleId = roleId
    },
    setChatMessages(state, messagesObj) {
      state.chatMessages.push(messagesObj)
    },
    setScore(state, { role, score }) {
      state.scores[role] = score
    },
  }
}