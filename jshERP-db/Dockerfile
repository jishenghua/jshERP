FROM mariadb:10
COPY jsh_erp.sql /docker-entrypoint-initdb.d/
ENV LANG C.UTF-8
CMD ["mysqld","--character-set-server=utf8mb4","--collation-server=utf8mb4_unicode_ci"]
