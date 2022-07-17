FROM nginx:1.15.3-alpine

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

COPY dist/dist.zip /home

RUN cd /home && unzip dist.zip -d . && cd -

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]