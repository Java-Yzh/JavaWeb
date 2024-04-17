<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>随机菜品</title>
</head>
<body>

<a href="user/exit">[退出系统]</a>
<h1>欢迎来到巴蜀名香</h1>
<h2>选择困难证福音!!!</h2>
<input type="button" value="随机菜品" onclick="food()">
<br>
<a href="/food2/dish/list">查看菜单</a>

<script type="text/javascript">
  function food() {
    const ok = window.confirm("亲，确认要生成随机菜品吗");
    if (ok) {
      document.location.href = "/food2/random";
    }
  }
</script>

</body>
</html>