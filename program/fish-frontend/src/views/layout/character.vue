<template>
  <div class="character">
    <div class="character_left">
      <div class="character_left_card" v-for="(role, index) in roles" :key="role.id" @click="choose(role.id, index)">
        <div class="card_wrap" ref="roleTab" :style="role.isSelected ? tabSelecte : tabNotSelect">
          <div class="card_wrap_img"></div>
          <div class="card_wrap_name">{{ role.name }}</div>
        </div>
      </div>
    </div>
    <div class="character_right">
      <div class="right_head">
        <div class="right_head_title">鱼鱼属性</div>
      </div>
      <div class="right_body">
        <div class="right_body_left">
          <div class="right_body_left_card">
            <label for="health">生命值</label>
            <div class="right_body_left_health attribute" id="health">{{ nowDisplayRole.health }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="defense">防御力</label>
            <div class="right_body_left_defense attribute" id="defense">{{ nowDisplayRole.defense }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="speed">速度</label>
            <div class="right_body_left_speed attribute" id="speed">{{ nowDisplayRole.speed }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="radiation">辐射值</label>
            <div class="right_body_left_radiation attribute" id="radiation">{{ nowDisplayRole.radiation }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="occlusion">咬合力</label>
            <div class="right_body_left_occlusion attribute" id="occlusion">{{ nowDisplayRole.occlusion }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="feeding">食性</label>
            <div class="right_body_left_feeding attribute" id="feeding">{{ nowDisplayRole.feeding }}</div>
          </div>
          <div class="right_body_left_card">
            <label for="endurance">耐力</label>
            <div class="right_body_left_endurance attribute" id="endurance">{{ nowDisplayRole.endurance }}</div>
          </div>
        </div>
        <div class="right_body_right">
          <div class="right_body_right_head">
            <div class="right_body_right_head_title">技能</div>
          </div>
          <div class="right_body_right_body">
            <div class="right_body_right_body_desc">{{ nowDisplayRole.variation }}</div>
            <div class="right_body_right_body_skill">{{ nowDisplayRole.ability }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Roles from '@/dicts/static_roles.js'
export default {
  name: 'character',
  components: {},
  data () {
    return {
      roles: Roles.roles,
      tabSelecte: {
        backgroundColor: 'rgba(120, 170, 242, 1)'
      },
      tabNotSelect: {
        backgroundColor: 'rgba(255, 255, 255, 1)'
      }
    }
  },
  computed: {
    nowDisplayRole () {
      return this.roles.find((item) => item.isSelected)
    }
  },
  watch: {},
  methods: {
    choose(id, idx) {
      this.roles.forEach((item) => {
        item.isSelected = false
      })
      this.roles.find((item) => item.id === id).isSelected = true
    }
  },
  created () {
    this.roles[0].isSelected = true
  },
}
</script>
<style scoped lang='less'>
.character {
  display: flex;
  flex-direction: row;
  padding: 0 8vw;
  width: 100vw;
  height: 100vh;
  &_left {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    flex: 1.2;
    height: 100%;
    padding: 20vh 0;
    &_card {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 80%;
      height: 40%;
      background-color: rgba(255, 255, 255, 1);
      &:first-child {
        border-top-left-radius: 0.8vw;

      }
      &:last-child {
        border-bottom-left-radius: 0.8vw;
      }
    }
  }
  &_right {
    flex: 5;
    height: 100%;
  }
}
.card {
  &_wrap {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 90%;
    width: 93%;
    border-top-left-radius: 0.8vw;
    border-bottom-left-radius: 0.8vw;
    background-color: rgba(255, 255, 255, 1);
    &_img {
      height: 77%;
      width: 100%;
    }
    &_name {
      .pxfont(30);
      font-weight: 550;
    }
  }
}
.right {
  &_head {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    height: 20%;
    width: 100%;
    &_title {
      padding: 2vh 7vw;
      letter-spacing: 0.3vw;
      border-top-left-radius: 1vw;
      border-top-right-radius: 1vw;
      font-weight: 550;
      .pxfont(50);
      background-color: rgba(120, 170, 242, 0.8);
    }
  }
  &_body {
    display: flex;
    flex-direction: row;
    height: 75%;
    width: 100%;
    &_left {
      display: flex;
      flex-direction: column;
      align-items: center;
      border-bottom-left-radius: 1vw;
      flex: 1;
      padding: 1.5vh 1.5vw;
      height: 100%;
      background-color: rgba(120, 170, 242, 0.8);
      &_card {
        display: flex;
        margin-bottom: 4%;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        height: 10%;
        width: 100%;
        border-radius: 1vw;
        background-color: rgba(255, 255, 255, 1);
        &:last-child {
          margin-bottom: 0;
        }
        label {
          font-weight: 550;
          margin-left: 1.5vw;
          .pxfont(36);
        }
        .attribute {
          margin-right: 1.5vw;
          .pxfont(36)
        }
      }
    }
    &_right {
      flex: 2.5;
      padding: 1.5vh 0.5vw;
      padding-right: 1.5vw;
      height: 100%;
      background-color: rgba(120, 170, 242, 0.8);
      border-top-right-radius: 1vw;
      border-bottom-right-radius: 1vw;
      &_head {
        display: flex;
        flex-direction: row;
        align-items: center;
        height: 15%;
        width: 100%;
        &_title { 
          margin-left: 1.5vw;
          font-weight: 550;
          .pxfont(40);
          padding: 1vh 3vw;
          background-color: rgba(255, 255, 255, 1);
          border-radius: 1vw;
          letter-spacing: 0.5vw;
        }
      }
      &_body {
        display: flex;
        flex-direction: column;
        height: 85%;
        width: 100%;
        &_desc, &_skill {
          .pxfont(28);
          background-color: rgba(255, 255, 255, 1);
          border-radius: 1vw;
          padding: 1vh 0.5vw;
          line-height: 7vh;
        }
        &_desc {
          margin-bottom: 5%;
        }
      }
    }
  }
}
</style>
