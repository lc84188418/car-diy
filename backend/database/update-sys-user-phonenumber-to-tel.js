// MongoDB 脚本：将 sys_user 集合的 phonenumber 字段重命名为 tel
// 使用方法：mongosh cardiy update-sys-user-phonenumber-to-tel.js

// 切换到 cardiy 数据库
use cardiy;

// 将 phonenumber 字段重命名为 tel
db.sys_user.updateMany(
  { phonenumber: { $exists: true } },
  { $rename: { "phonenumber": "tel" } }
);

// 显示更新结果
print("字段重命名完成！");
print("已更新的文档数量：" + db.sys_user.countDocuments({ tel: { $exists: true } }));

// 验证：显示一个示例文档
print("\n示例文档（更新后）：");
var sample = db.sys_user.findOne({ tel: { $exists: true } });
if (sample) {
  printjson({
    userId: sample.userId,
    userName: sample.userName,
    tel: sample.tel,
    email: sample.email
  });
} else {
  print("未找到包含 tel 字段的文档");
}

