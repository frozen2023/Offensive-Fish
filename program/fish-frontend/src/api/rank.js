import request from '@/utils/request'

// 1.获取排行榜
export const getRankList = () => {
  return request.get('/RankList/selectAllRankList')
}

// 2.插入排行榜
export const insertRankList = (firstPlayerId, secondPlayerId, score) => {
  console.log(firstPlayerId, secondPlayerId, score)
  return request.get('/RankList/insertRankList', {
    params: {
      firstPlayerId,
      secondPlayerId,
      score
    }
  })
}