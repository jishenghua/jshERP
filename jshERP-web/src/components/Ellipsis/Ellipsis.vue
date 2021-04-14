<script>
  export default {
    name: 'Ellipsis',
    props: {
      prefixCls: {
        type: String,
        default: 'ant-pro-ellipsis'
      },
      tooltip: {
        type: Boolean,
        default: true,
      },
      length: {
        type: Number,
        default: 25,
      },
      lines: {
        type: Number,
        default: 1
      },
      fullWidthRecognition: {
        type: Boolean,
        default: false
      }
    },
    methods: {},
    render() {
      const { tooltip, length } = this.$props
      let text = ''
      // 处理没有default插槽时的特殊情况
      if (this.$slots.default) {
        text = this.$slots.default.map(vNode => vNode.text).join('')
      }
    }
  }
</script>