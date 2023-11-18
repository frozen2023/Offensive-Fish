import request from '@/utils/request'
import store from '@/store'

// 1. 创建房间 
export const createRoom = () => {
  return request.get('/Room/createNewRoom', {
    params: {
      userName: store.getters.loginId
    }
  })
}

// 2.销毁房间
export const destroyRoom = (roomId) => {
  return request.get('/Room/ExitRoom', {
    params: {
      roomId
    }
  })
}

// 3.进入房间
export const enterRoomByCode = (code) => {
  return request.get('/Room/entryRoom', {
    params: {
      code,
      playerId: store.getters.loginId
    }
  })
}