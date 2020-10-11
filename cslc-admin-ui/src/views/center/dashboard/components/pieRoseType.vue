<template>
  <div id="pieRoseType" ref="pieRoseType" style="height:30vh" />
</template>

<script>
export default {
  name: 'PieRoseType',
  data() {
    return {}
  },
  mounted() {
    const pieRoseType = this.$echarts.init(this.$refs.pieRoseType, 'light')
    const options = {
      title: {
        text: ''
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        x: 'center',
        y: 'bottom',
        data: [
          '用户总数',
          '用户注册数',
          '用户活跃数',
          '关注数',
          '模拟数1',
          '模拟数2'
        ]
      },
      calculable: true,
      series: [
        {
          name: '模拟数据',
          type: 'pie',
          radius: [20, 110],
          center: ['50%', '60%'],
          roseType: 'radius',
          label: {
            normal: {
              show: false
            },
            emphasis: {
              show: true
            }
          },
          lableLine: {
            normal: {
              show: false
            },
            emphasis: {
              show: true
            }
          },
          data: [
            { value: 35, name: '用户总数' },
            { value: 20, name: '用户注册数' },
            { value: 15, name: '用户活跃数' },
            { value: 25, name: '关注数' },
            { value: 15, name: '模拟数1' },
            { value: 12, name: '模拟数2' }
          ]
        }
      ]
    }
    pieRoseType.setOption(options)
  }
}
</script>
