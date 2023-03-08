// 角色枚举
const RoleEnum = {
  // 名称 --> 值，用于常量
  'ADMIN': 1, // 管理员
  'YWC': 14, // 医务处
  'FSK': 12, // 放射科
  'GR': 8, // 个人
  'KSZR': 11, // 科室主任
  'YKYS': 6, // 眼科医生
  'FSKGR': 13, // 放射科个人
  'HFK': 16, // 行风建设
  'MYK': 17, // 证件

  // 值 -> 描述， 用于转换
  '1': '管理员',
  '14': '医务处',
  '12': '放射科',
  '8': '个人',
  '11': '科室主任',
  '6': '眼科医生',
  '13': '放射科个人',
  '16': '行风建设',
  '17': '证件',

  // 集合，用于下拉框
  List: [
    { name: "ADMIN", value: 1, description: "管理员", spellCode: "", strokeCode: "" },
    { name: "YWC", value: 14, description: "医务处", spellCode: "", strokeCode: "" },
    { name: "FSK", value: 12, description: "放射科", spellCode: "", strokeCode: "" },
    { name: "GR", value: 8, description: "医务处", spellCode: "", strokeCode: "" },
    { name: "KSZR", value: 11, description: "放射科", spellCode: "", strokeCode: "" },
    { name: "YKYS", value: 6, description: "眼科医生", spellCode: "", strokeCode: "" },
    { name: "FSKGR", value: 6, description: "放射科个人", spellCode: "", strokeCode: "" },
    { name: "HFK", value: 16, description: "行风建设", spellCode: "", strokeCode: "" },
    { name: "MYK", value: 17, description: "证件", spellCode: "", strokeCode: "" },
  ],
};
export default RoleEnum
