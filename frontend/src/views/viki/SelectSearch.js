class ExampleSeach extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: '',
            options: []
        }
    }
    fetchData= async (userAccount) => {
        this.$get("sdlBUser/search", {
          userAccount,
        }).then((r) => {
          this.userData = r.data;
        });
      }
    handleSearch = async (value) => {
        if (value) {
            // 加载远程数据
            let res = await fetchData(value)
            this.setState({
                options: res
            })
        } else {
            this.setState({
                options: []
            })
        }
    }
    
    handleChange = value => {
        this.setState({
            value: value
        })
        // 下面一行代码是关键,在被getFieldDecorator包装后，会绑定一个onChange事件，
        // 接收的第一个参数将作为当前formItem的value
        this.props.onChange(value)
    }
    
    render() {
        const opts = options.map(ele => <Option key={ele.userAccount}>{ele.userAccountName}</Option>)
        return (
          <Select
            showSearch
            value={this.state.value}
            defaultActiveFirstOption={false}
            showArrow={false}
            filterOption={false}
            onSearch={this.handleSearch}
            onChange={this.handleChange}
            notFoundContent={null}
          >
            {opts}
          </Select>
        )
    }
}

export default ExampleSeach;