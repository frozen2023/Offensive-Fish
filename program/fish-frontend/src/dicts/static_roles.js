export default {
  roles: [
    { id: 1, name: '金枪鱼', health: '1113', defense: '42', 
      speed: '1马赫', radiation: '6', occlusion: '256', feeding: '杂食', 
      endurance: '300',
      img: require('@/assets/images/1.png'),
      isSelected: false, 
      variation: '变异之处：这种金枪鱼变异出令人惊叹的超高速度。它们的身体线条更加流线型，鳍和鳍尾变得更大。',
      ability: '特殊能力：雷速金枪鱼具有闪电一般的加速能力，能够在水中迅速穿梭。它们可以迅速追赶其他鱼，或者逃脱捕食者。此外，它们可以在快速移动时创建出引力漩涡，吸引附近的小鱼，为自己提供更多的食物来源。',
    },
    { id: 2, name: '鳐鱼', health: '1313', defense: '60', 
      speed: '0.5马赫', radiation: '7', occlusion: '190', feeding: '杂食', 
      endurance: '400',
      img: require('@/assets/images/2.png'),
      isSelected: false,
      variation: '变异之处：这种鳐鱼由于核污染而发光，身体覆盖着发光的鳞片。它们的扁平体形使它们擅长在海底滑行，并且拥有特殊的治疗能力。', 
      ability: '特殊能力：光线鳐鱼可以发出闪亮的光束，使周围的水域一片明亮。这可以用来吸引其他鱼，使猎食更容易。此外，它可以治疗他的小伙伴。',
    },
  ]

}