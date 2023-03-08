<template>
  <a-upload
    accept=".png,.jpg,.pdf"
    :fileList="fileList"
    :remove="handleRemove"
    :beforeUpload="beforeUpload"
    @change="handleChange"
    :disabled="!isEdit"
  >
    <a-button v-show="isEdit">
      <a-icon type="upload" /> 选择图片
    </a-button>
  </a-upload>
</template>
  
  <script>
export default {
  name: "file",
  data () {
    return {
      tstyle: { "color": "#0785fd", "font-weight": "bold", "background-color": "#ececec" },
      fileList: [],
      uploading: false,
      fileNow: {},
      fileId: ''
    }
  },
  props: {
    baseId: {
      default: ''
    },
    refTab: {
      default: null
    },
    isEdit: {
      default: true
    }
  },
  // watch: {
  //   baseId: function () {
  //     if (this.baseId != '') {
  //       var _this = this;
  //       _this.fetch(_this.baseId)
  //     }
  //   },
  //   immediate: true, // watch侦听操作内的函数会立刻被执行
  //   deep: true
  // },
  methods: {
    reset(){
      this.fileList= []
    },
    handleRemove (file) {

      let that = this
      that.$confirm({
        title: '确定删除所选中的附件?',
        content: '当您点击确定按钮后，这些附件将会被彻底删除',
        centered: true,
        onOk () {
          const index = that.fileList.indexOf(file)
          const newFileList = that.fileList.slice()
          console.info(newFileList[index].uid)
          that.$delete('comFile/' + newFileList[index].uid).then((r) => {
            newFileList.splice(index, 1)
            that.fileList = newFileList
            that.uploading = false
            that.$message.success('删除成功.')
          }).catch(() => {
            that.uploading = false
            that.$message.error('删除失败.')
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })




    },
    onChange (date, dateString) {
      console.log(date, dateString);
    },
    beforeUpload (file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png' || file.type==='application/pdf')
      console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf,png,jpg文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!isLt2M) {
        this.$message.error('附件必须小于 20MB!')
      }
      if (isJPG && isLt2M) {
        this.fileNow = file
      }
      return isJPG && isLt2M;
    },
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    handleUpload () {
      const { fileNow } = this
      const formData = new FormData()
      formData.append('file', fileNow)
      formData.append('baseId', this.baseId)
      if (this.refTab !== null) {
        formData.append('refTab', this.refTab)
      }
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/mulUpload', formData).then((r) => {
        let comfile = r.data.data
        this.fileList = [...this.fileList, comfile]
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    },
    fetch (baseId) {
      this.fileList = []
      let params = {baseId: baseId, refTab: this.refTab}
      this.$get('comFile/getAllFiles', params).then((r) => {
        let data = r.data.data
        console.info(data)
        if (data != null) {
          data.forEach(element => {
            element.url =this.$baseUrl+ element.url
          });
          this.fileList = [...data]
        }
      })
    },
    cancelAudit () {
      this.$emit("setFileId", this.fileId, this.fileUrl)
    },

  }
}
  </script>
  
  <style>
</style>