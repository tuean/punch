---
title: antdesign-form-validator
date: 2021-08-31 19:24:03
tags:
  - react
  - ant-design
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/bbc58c3d998d4f389fdae649334873f7.jpeg
---

### react & antd 相关记录

#### form表单自定义错误
```jsx
<Form.Item
  rules=[{
  	required: true,
  	message: "error"
  }, {
    validator: this.validator.bind(this)
  }]>
	<Input />
</Form.Item>
```

```js
validator(rule, value, callback) {
  if (this.state.error_msg != null) {
		callback(error_msg)
    return
  }
  
  callback() // important
}
```

