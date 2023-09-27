package com.example.jwttest.utils;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwttest.entity.JsonResult;
import com.example.jwttest.entity.UserVo;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

		private static final String SECRET = "!Q@W#E$R^Y&U";
		//token签发者
		private static final String ISSUSRE = "HZSTYGZPT";
		//token过期时间(有效期)
		public static final Long EXPIRE_DATE = 60 * 1000*60L;

		/**
		 *  生成token
		 * @param userVo
		 * @return
		 */
		public static String Token(UserVo userVo){
			//当前时间
			Date now = new Date();
			//创建过期时间
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DATE,7);  //7天过期
			//1. header
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			String token = JWT.create()
					.withIssuer(ISSUSRE)
					.withIssuedAt(now)
					.withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
					.withClaim("userName", userVo.getName())   // 存放user中的内容,UserTokenDTO中不带有敏感信息，如password字段不会出现在token中
					.withClaim("userId", userVo.getId())
					.sign(algorithm);
			return token;
		}

		/**
		 *  生成token
		 * @param userVo
		 * @param expireDate 过期时间
		 * @return
		 */
//		public static String Token(UserVo userVo, Long expireDate){
//			//当前时间
//			Date now = new Date();
//			//创建过期时间
//			Date expire = new Date(now.getTime() + expireDate);
//			//1. header
//			Algorithm algorithm = Algorithm.HMAC256(SECRET);
//			String token = JWT.create()
//					.withIssuer(ISSUSRE)
//					.withIssuedAt(now)
//					.withExpiresAt(expire)
//					.withClaim("userName", userVo.getName())
//					.withClaim("userId", userVo.getId())
//					.sign(algorithm);
//			return token;
//		}


		// 个人一般使用这个
		/**
		 *  生成token
		 * @param data  任意自定义对象
		 * @param expireDate 过期时间
		 * @return
		 */
		public static <T> String Token(T data, Long expireDate){
			//当前时间
			Date now = new Date();
			//创建过期时间
			Date expire = new Date(now.getTime() + expireDate);
			//1. header
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			String token = JWT.create()
					.withIssuer(ISSUSRE)
					.withIssuedAt(now)
					.withExpiresAt(expire)
					.withSubject(JSON.toJSONString(data))
					.sign(algorithm);
			return token;
		}

		/* 调用
		*UserVo user1 = new UserVo();
		*user1.setName("神神叨叨");
		*user1.setId(100);
		* 设置UserVo的相关属性
		*Long expireDate = 1000 * 60 * 60 * 24L; // 设置为1天过期时间
		*String token = jwtUtil.Token(user1, expireDate);
		* */

		/**
		 *  生成token
		 * @param map
		 * @return
		 */
		public static String createToken(Map<String,String> map){
			//创建过期时间
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DATE,7);  //7天过期

			//创建builder对象
			JWTCreator.Builder builder = JWT.create();
			//遍历map
			map.forEach((k,v)->{
				builder.withClaim(k,v);
			});
			String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SECRET));
			return token;
		}

//		/**
//		 *  验证token
//		 *  验证过程中如果有异常，则抛出；
//		 *  如果没有,则返回 DecodedJWT 对象来获取用户信息;
//		 * @param token
//		 */
		public static JsonResult verifyToken(String token, String username){
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			try {
				JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("userName", username).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(token);
				// DecodedJWT类中封装函数可以token中存放的信息
				return new JsonResult();
			}catch (SignatureVerificationException e) {
				//验证的签名不一致
				throw new SignatureVerificationException(algorithm);
			}catch (TokenExpiredException e){
				throw new TokenExpiredException("token is alreadey expired");
			}catch (AlgorithmMismatchException e){
				throw new AlgorithmMismatchException("签名算法不匹配");
			}catch (InvalidClaimException e){
				throw new InvalidClaimException("校验的claims内容不匹配");
			}catch (Exception e){
				e.printStackTrace();
			}
			return new JsonResult("2000","用户和jwt-token校验失败");
		}

		/**
		 *  验证token
		 *  验证过程中如果有异常，则抛出；
		 *  如果没有,则返回 DecodedJWT 对象来获取用户信息;
		 * @param token
		 */
		public static DecodedJWT verify(String token){
			return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
		}

		/**
		 *
		 * @return 返回指定对象
		 * **/
		public static <T> T verify(String token, Class<T> clazz){
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
			String subject = decodedJWT.getSubject();
			// 将JWT主体转换为指定类型的对象并返回
			return JSON.parseObject(subject, clazz);
		}
}