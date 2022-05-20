FROM node:12 AS build
WORKDIR /app
COPY . .
RUN yarn install
RUN yarn build

FROM nginx:1.18-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf