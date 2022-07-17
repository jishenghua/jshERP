FROM nginx:1.15.3-alpine

RUN mkdir -p /etc/nginx/logs

COPY nginx/nginx.conf /etc/nginx/nginx.conf

RUN mkdir -p /home/jshERP

COPY dist/dist.zip /home/jshERP

RUN cd /home/jshERP && unzip dist.zip -d . && mv dist jshERP-web

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]