<template>
  <div id="areaStack" ref="areaStack" style="height:40vh;" />
</template>

<script>
export default {
  name: 'AreaStack',
  data() {
    return {}
  },
  mounted() {
    const areaStack = this.$echarts.init(this.$refs.areaStack, 'light')
    const options = {
      title: {
        text: ''
      },
      legend: {
        data: ['活跃用户', '注册用户']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '注册用户',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: [120, 90, 101, 134, 290, 230, 210]
        },
        {
          name: '活跃用户',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: [220, 101, 310, 234, 90, 330, 310]
        }
      ]
    }

    areaStack.setOption(options)
  }
}
</script>
