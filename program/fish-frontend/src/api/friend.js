import request from '@/utils/request.js'
import store from '@/store'

// 1.获取好友列表接口
export const getFriendList = () => {
  const userName = store.getters.loginId
  return request.get('/FriendLink/selectAllFriend', {
    params: {
      userName
    }
  })
}

// 2.添加好友接口
export const addFriend = (userName, friendName) => {
  return request.get('/FriendLink/addFriendLink', {
    params: {
      userName,
      friendName
    }
  })
}

// 3.删除好友接口
export const deleteFriend = (userName, friendName) => {
  return request.get('/FriendLink/deleteFriendLink', {
    params: {
      userName,
      friendName
    }
  })
}