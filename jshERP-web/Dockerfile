# 使用Nginx镜像作为基础镜像
FROM nginx


# 将前端应用程序静态文件复制到Nginx默认网站目录
COPY dist/ /usr/share/nginx/html/

COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露Nginx的默认端口（84端口）
EXPOSE 84
# 启动Nginx服务
CMD ["nginx", "-g", "daemon off;"]
