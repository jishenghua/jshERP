FROM maven:3.8.5-jdk-11-slim AS build

WORKDIR /usr/src/app

RUN apt-get update && apt-get install -y \
  nodejs \
  npm \
  && rm -rf /var/lib/apt/lists/* \
  && npm install -g yarn

# copy backend files
COPY ./jshERP-boot/src /usr/src/app/jshERP-boot/src
COPY ./jshERP-boot/pom.xml /usr/src/app/jshERP-boot/

# copy frontend files
COPY ./jshERP-web/public /usr/src/app/jshERP-web/public
COPY ./jshERP-web/src /usr/src/app/jshERP-web/src
COPY ./jshERP-web/yarn.lock ./jshERP-web/package.json \
  ./jshERP-web/vue.config.js ./jshERP-web/babel.config.js \
  ./jshERP-web/idea.config.js \
  /usr/src/app/jshERP-web/

# build backend
RUN cd /usr/src/app/jshERP-boot && \
  mvn -N wrapper:wrapper && \
  unset MAVEN_CONFIG && \
  ./mvnw install -DskipTests
RUN mv /usr/src/app/jshERP-boot/target/jshERP.jar /usr/src/app/app.jar

# build frontend
RUN cd /usr/src/app/jshERP-web/ && \
  yarn install --pure-lockfile && \
  yarn build
RUN mv /usr/src/app/jshERP-web/dist /usr/src/app/web


# app image
FROM openjdk:11-jdk-slim AS app

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/app.jar /usr/src/app/app.jar
CMD ["java", "-jar", "app.jar"]


# web image
FROM nginx:1.20.2-alpine AS web

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/web /usr/share/nginx/html
