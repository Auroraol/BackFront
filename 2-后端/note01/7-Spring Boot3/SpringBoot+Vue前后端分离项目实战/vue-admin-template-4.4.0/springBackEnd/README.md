
```
springBackEnd
├─ .gitignore
├─ .mvn
│  └─ wrapper
│     ├─ maven-wrapper.jar
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ lfj
   │  │        ├─ common
   │  │        │  └─ vo
   │  │        │     └─ Result.java
   │  │        ├─ config
   │  │        │  ├─ CorsConfig.java
   │  │        │  ├─ MpConfig.java
   │  │        │  ├─ MyRedisConfig.java
   │  │        │  ├─ Properties
   │  │        │  │  ├─ JwtProperties.java
   │  │        │  │  └─ SwaggerProperties.java
   │  │        │  ├─ SwaggerConfig.java
   │  │        │  └─ web
   │  │        │     └─ MyWebConfig.java
   │  │        ├─ controller
   │  │        │  ├─ RoleController.java
   │  │        │  └─ UserController.java
   │  │        ├─ entity
   │  │        │  ├─ XMenu.java
   │  │        │  ├─ XRole.java
   │  │        │  ├─ XRoleMenu.java
   │  │        │  ├─ XUser.java
   │  │        │  └─ XUserRole.java
   │  │        ├─ interceptor
   │  │        │  └─ JwtValidateInterceptor.java
   │  │        ├─ mapper
   │  │        │  ├─ XMenuMapper.java
   │  │        │  ├─ XRoleMapper.java
   │  │        │  ├─ XRoleMenuMapper.java
   │  │        │  ├─ XUserMapper.java
   │  │        │  └─ XUserRoleMapper.java
   │  │        ├─ service
   │  │        │  ├─ impl
   │  │        │  │  ├─ XMenuServiceImpl.java
   │  │        │  │  ├─ XRoleMenuServiceImpl.java
   │  │        │  │  ├─ XRoleServiceImpl.java
   │  │        │  │  ├─ XUserRoleServiceImpl.java
   │  │        │  │  └─ XUserServiceImpl.java
   │  │        │  ├─ XMenuService.java
   │  │        │  ├─ XRoleMenuService.java
   │  │        │  ├─ XRoleService.java
   │  │        │  ├─ XUserRoleService.java
   │  │        │  └─ XUserService.java
   │  │        ├─ SpringBackEndApplication.java
   │  │        └─ util
   │  │           └─ JwtUtil.java
   │  └─ resources
   │     ├─ application.yml
   │     ├─ mapper
   │     │  ├─ XMenuMapper.xml
   │     │  ├─ XRoleMapper.xml
   │     │  ├─ XRoleMenuMapper.xml
   │     │  ├─ XUserMapper.xml
   │     │  └─ XUserRoleMapper.xml
   │     ├─ static
   │     └─ templates
   └─ test
      └─ java
         └─ com
            └─ lfj
               ├─ JwtUtilTest.java
               └─ SpringBackEndApplicationTests.java

```