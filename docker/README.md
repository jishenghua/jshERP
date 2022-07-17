> jshERP docker 版

### build

#### jshERP-boot

```
cd jshERP-boot

mvn package -Dmaven.test.skip

cp target/jshERP.jar ../docker/boot
```

#### jshERP-web

```
yarn build

zip dist.zip dist/*

cp dist.zip ../docker/web
```


### Reference

https://gitee.com/jishenghua/JSH_ERP