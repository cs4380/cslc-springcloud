<template>
  <div id="barSimple" ref="barSimple" style="height:30vh" />
</template>

<script>
export default {
  name: 'BarSimple',
  data() {
    return {}
  },
  mounted() {
    const barSimple = this.$echarts.init(this.$refs.barSimple, 'light')
    const options = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'line'
        }
      },
      legend: {
        data: ['用户总数', '今日注册', '活跃用户']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'value'
        }
      ],
      yAxis: [
        {
          type: 'category',
          axisTick: { show: false },
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }
      ],
      series: [
        {
          name: '用户总数',
          type: 'bar',
          label: {
            normal: {
              show: true,
              position: 'inside'
            }
          },
          data: [200, 170, 240, 244, 200, 220, 210]
        },
        {
          name: '今日注册',
          type: 'bar',
          stack: '总量',
          label: {
            normal: {
              show: true
            }
          },
          data: [320, 302, 341, 374, 390, 450, 420]
        },
        {
          name: '活跃用户',
          type: 'bar',
          stack: '总量',
          label: {
            normal: {
              show: true,
              position: 'left'
            }
          },
          data: [-120, -132, -101, -134, -190, -230, -210]
        }
      ]
    }
    barSimple.setOption(options)
  }
}
</script>
