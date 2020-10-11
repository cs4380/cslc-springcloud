<template>
  <div class="dashboard">
    <el-row :gutter="45" class="main-top">
      <el-col :span="6">
        <div class="top-grid-content" style="background-color:#00c0ef">
          <div>用户总数</div>
          <div style="font-size:20px;margin-top:15px">153,123</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="top-grid-content" style="background-color:#dd4b39">
          <div>今日注册</div>
          <div style="font-size:20px;margin-top:15px">240</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="top-grid-content" style="background-color:#00a65a">
          <div>活跃用户</div>
          <div style="font-size:20px;margin-top:15px">100,123</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="top-grid-content" style="background-color:#f39c12">
          <div>关注数</div>
          <div style="font-size:20px;margin-top:15px">53,123</div>
        </div>
      </el-col>
    </el-row>
    <div class="main">
      <areaStack />
    </div>

    <el-row :gutter="35" class="main-bottom">
      <el-col :span="8">
        <div class="main-bottom-content">
          <scatter />
        </div>
      </el-col>
      <el-col :span="8">
        <div class="main-bottom-content">
          <pieRoseType />
        </div>
      </el-col>
      <el-col :span="8">
        <div class="main-bottom-content">
          <barSimple />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import areaStack from './components/areaStack'
import scatter from './components/scatter'
import pieRoseType from './components/pieRoseType'
import barSimple from './components/barSimple'

export default {
  name: 'Dashboard',
  components: {
    areaStack,
    scatter,
    pieRoseType,
    barSimple
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  height: calc(100vh - 50px);
  width: 100%;
  background-color: #f0f2f5;
}
.main-top {
  padding: 40px 15px 30px 15px;
}
.top-grid-content {
  font-size: 35px;
  color: #fff;
  text-align: center;
  padding-top: 15px;
  border-radius: 8px;
  min-height: 120px;
}
.main {
  margin: 0px 15px;
  background-color: #fff;
}
.main-bottom {
  padding: 30px 15px;
}
.main-bottom-content {
  background-color: #fff;
}
</style>
