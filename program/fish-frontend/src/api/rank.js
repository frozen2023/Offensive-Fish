import request from '@/utils/request'

//1.获取排行榜
export const getRankList = () => {
  return request.get('/Rank/getRankList')
}